<template>
  <div class="view-container">
    <div class="day-view">
      <div class="day-view-left">
        <TaskCardList :title="'年度任务'" :type="'year'" :tasks="yearlyTasks" />
        <TaskCardList :title="'月度任务'" :type="'month'" :tasks="monthlyTasks" />
        <TaskCardList :title="'临时任务'" :type="'week'" :tasks="temporaryTasks" />
      </div>
      <div class="day-view-main">
        <DayTaskHandle @updateTasks="handleUpdateTasks" />
        <el-scrollbar ref="scrollbarRef" class="day-scroll-container" @scroll="handleScroll">
          <div class="timeline-content" :style="{ minHeight: `${totalHeight}px` }">
            <!-- 顶部留白区域 -->
            <div class="top-padding-area" :style="{ height: `${topPadding}px` }">
              <div class="time-scale-marker start-marker">开始</div>
              <div class="greeting-text">又是元气满满的一天</div>
            </div>

            <!-- 时间刻度 - 7-23点整点显示 -->
            <div class="time-scale">
              <div
                v-for="hour in 17"
                :key="hour"
                class="time-scale-item"
                :class="{
                  'is-hidden': shouldHideHourTick(hour + 6),
                  'is-task-boundary': isTaskBoundary(hour + 6),
                }"
                :style="{ top: `${getTickPosition(hour - 1)}px` }"
              >
                <span class="time-label">{{ formatHour(hour + 6) }}</span>
                <div class="time-line"></div>
              </div>

              <!-- 展开任务的起止时间刻度 -->
              <div
                v-for="(tick, index) in expandedTaskTicks"
                :key="`expanded-${index}`"
                class="time-scale-item expanded-task-tick"
                :style="{ top: `${tick.position}px` }"
              >
                <span class="time-label">{{ tick.label }}</span>
                <div class="time-line"></div>
              </div>
            </div>

            <!-- 任务列表 -->
            <div class="task-list">
              <div
                v-for="(task, index) in tasks"
                :key="task.id"
                ref="taskRefs"
                class="task-item"
                :class="{ 'is-active': activeTaskIndex === index }"
                :style="getTaskStyle(task, index)"
                @click="setActiveTask(index)"
              >
                <DayTaskCard
                  :task="task"
                  :is-active="activeTaskIndex === index"
                  :base-height="getTaskBaseHeight(task)"
                  :other-tasks="tasks.filter((t) => t.id !== task.id)"
                  @toggleSubTask="(idx) => toggleSubTask(index, idx)"
                  @feedback="handleFeedback(task)"
                  @pause="handlePause(task)"
                  @delay="(newTimeRange, reason) => handleDelay(index, newTimeRange, reason)"
                  @resize="
                    (newTimeRange, newHeight) => handleResize(index, newTimeRange, newHeight)
                  "
                />
              </div>
            </div>

            <!-- 底部留白区域 -->
            <div class="bottom-padding-area" :style="{ height: `${buttomPadding}px` }">
              <div class="ending-text">你真棒，又努力了一天，祝您好梦成真！</div>
              <div class="time-scale-marker end-marker">结束</div>
            </div>
          </div>
        </el-scrollbar>
      </div>
      <div class="day-view-right"><AsideRight /></div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, nextTick } from 'vue';
import AsideRight from '@/components/AsideRight.vue';
import TaskCardList from '@/components/common/TaskCardList.vue';
import DayTaskCard from './component/DayTaskCard.vue';
import DayTaskHandle from './component/DayTaskHandle.vue';
import {
  yearlyTasks,
  monthlyTasks,
  temporaryTasks,
  dailyTasks,
  getDailyTasksByDate,
} from '@/mock/day.js';

// 常量定义
const START_HOUR = 7; // 时间线从7点开始
const END_HOUR = 23; // 时间线到23点结束
const TASK_MIN_HEIGHT = 60; // 半小时任务的最小高度（像素）
const HOUR_HEIGHT = TASK_MIN_HEIGHT * 2; // 1小时的高度（120px）

const scrollbarRef = ref(null);
const taskRefs = ref([]);
const activeTaskIndex = ref(0);
const scrollTop = ref(0);

// 任务数据 - 从 mock 文件导入
const tasks = ref(dailyTasks);

// 视口高度（用于计算上下留白）
const viewportHeight = ref(600);

// 计算总高度（加上下留白，再加上所有展开任务的额外高度）
const totalHeight = computed(() => {
  const contentHeight = (END_HOUR - START_HOUR) * HOUR_HEIGHT;
  const paddingHeight = topPadding.value + buttomPadding.value; // 顶部留白 + 底部留白
  const lastTaskOffset = getTaskOffset(tasks.value.length);
  return contentHeight + paddingHeight + lastTaskOffset;
});

// 获取顶留白高度（各为视口1/3）
const topPadding = computed(() => {
  return viewportHeight.value / 3;
});

// 获取底部留白高度（为视口高度的0.5倍，确保可以滚动到最后的任务）
const buttomPadding = computed(() => {
  return viewportHeight.value / 2;
});

// 时间刻度位置计算（加上顶部留白偏移，并考虑展开任务的偏移）
const getTickPosition = (hourIndex) => {
  const hour = START_HOUR + hourIndex;
  const basePosition = topPadding.value + hourIndex * HOUR_HEIGHT;

  // 计算在该整点时间之前结束的所有展开任务造成的偏移
  let offset = 0;
  for (let i = 0; i < tasks.value.length; i++) {
    const task = tasks.value[i];
    const taskBaseHeight = getTaskBaseHeight(task);
    const taskActualHeight = getTaskActualHeight(task, i);

    // 只考虑展开的任务
    if (taskActualHeight <= taskBaseHeight) continue;

    const extraHeight = taskActualHeight - taskBaseHeight;
    const [start, end] = task.timeRange.split('-');
    const endParsed = parseTime(end);
    const taskEndHour = endParsed.hour + endParsed.min / 60;

    // 如果任务结束时间在该整点之前，累加偏移
    if (taskEndHour <= hour) {
      offset += extraHeight;
    }
  }

  return basePosition + offset;
};

// 格式化小时显示
const formatHour = (hour) => {
  return `${hour.toString().padStart(2, '0')}:00`;
};

// 解析时间字符串为小时和分钟
const parseTime = (timeStr) => {
  const [hour, min] = timeStr.split(':').map(Number);
  return { hour, min };
};

// 计算时间对应的像素位置（含顶部留白）
const getTimePosition = (hour, minute = 0) => {
  return topPadding.value + (hour - START_HOUR + minute / 60) * HOUR_HEIGHT;
};

// 计算任务时长（小时）
const getTaskDuration = (timeRange) => {
  const [start, end] = timeRange.split('-');
  const startHour = parseTime(start);
  const endHour = parseTime(end);
  const startDecimal = startHour.hour + startHour.min / 60;
  const endDecimal = endHour.hour + endHour.min / 60;
  let duration = endDecimal - startDecimal;
  // 限制在 0.5 - 12 小时之间
  return Math.max(0.5, Math.min(12, duration));
};

// 计算任务的基础高度（由时长决定）
const getTaskBaseHeight = (task) => {
  const duration = getTaskDuration(task.timeRange);
  return Math.max(TASK_MIN_HEIGHT, duration * HOUR_HEIGHT);
};

// 计算任务内容完全展开所需的高度
const calculateTaskContentHeight = (task) => {
  const SUBTASK_ITEM_HEIGHT = 26;
  const HEADER_HEIGHT = 50;
  const TITLE_HEIGHT = 28;
  const QUOTE_HEIGHT = 24;
  const PADDING = 24;

  let height = PADDING + HEADER_HEIGHT + TITLE_HEIGHT;

  if (task.subTasks && task.subTasks.length > 0) {
    height += task.subTasks.length * SUBTASK_ITEM_HEIGHT;
  }

  if (task.quote) {
    height += QUOTE_HEIGHT;
  }

  return height;
};

// 获取任务的实际高度（如果内容高度超过时长高度，直接使用内容高度）
const getTaskActualHeight = (task, index) => {
  const baseHeight = getTaskBaseHeight(task);
  const contentHeight = calculateTaskContentHeight(task);

  // 如果内容高度超过时长决定的高度，直接使用内容高度
  if (contentHeight > baseHeight) {
    return contentHeight;
  }
  return baseHeight;
};

// 判断任务是否展开（高度超过时长决定的高度）
const isTaskExpanded = (task, index) => {
  const baseHeight = getTaskBaseHeight(task);
  const contentHeight = calculateTaskContentHeight(task);
  return contentHeight > baseHeight;
};

// 展开任务的起止时间刻度
const expandedTaskTicks = computed(() => {
  const ticks = [];

  tasks.value.forEach((task, index) => {
    if (isTaskExpanded(task, index)) {
      const [start, end] = task.timeRange.split('-');

      const startPosition = getTaskStartPosition(task, index);
      const actualHeight = getTaskActualHeight(task, index);
      const endPosition = startPosition + actualHeight;

      // 开始时间刻度
      ticks.push({
        position: startPosition,
        label: start,
      });

      // 结束时间刻度（使用实际高度位置）
      ticks.push({
        position: endPosition,
        label: end,
      });
    }
  });

  return ticks;
});

// 计算每个任务的累积偏移量（由前面展开的任务造成的额外高度）
const getTaskOffset = (taskIndex) => {
  let offset = 0;
  for (let i = 0; i < taskIndex; i++) {
    const task = tasks.value[i];
    const baseHeight = getTaskBaseHeight(task);
    const actualHeight = getTaskActualHeight(task, i);
    // 如果任务展开，累加额外高度
    if (actualHeight > baseHeight) {
      offset += actualHeight - baseHeight;
    }
  }
  return offset;
};

// 获取任务的开始位置（考虑前面展开任务的影响）
const getTaskStartPosition = (task, index) => {
  const [start] = task.timeRange.split('-');
  const { hour, min } = parseTime(start);
  const basePosition = getTimePosition(hour, min);
  const offset = getTaskOffset(index);
  return basePosition + offset;
};

// 判断整点刻度是否应该隐藏（因为被展开的任务覆盖）
const shouldHideHourTick = (hour) => {
  // 检查是否有展开的任务覆盖了该整点时间
  return tasks.value.some((task, index) => {
    if (!isTaskExpanded(task, index)) return false;

    const [start, end] = task.timeRange.split('-');
    const startParsed = parseTime(start);
    const endParsed = parseTime(end);

    // 使用原始时间范围的小时数（更精确）
    const startHour = startParsed.hour + startParsed.min / 60;
    const actualHeight = getTaskActualHeight(task, index);
    // 计算实际结束时间（基于开始时间和实际高度）
    const endHour = startHour + actualHeight / HOUR_HEIGHT;

    // 如果该整点时间在展开任务的时间范围内（包括起止点），则隐藏
    // 使用一个小误差范围来避免浮点数精度问题
    const epsilon = 0.01;
    return hour + epsilon >= startHour && hour - epsilon <= endHour;
  });
};

// 判断是否是任务边界时间
const isTaskBoundary = (hour) => {
  return tasks.value.some((task, index) => {
    const [start, end] = task.timeRange.split('-');
    const startParsed = parseTime(start);
    const endParsed = parseTime(end);

    // 使用原始时间范围的小时数
    const startHour = startParsed.hour + startParsed.min / 60;
    const actualHeight = getTaskActualHeight(task, index);
    const endHour = startHour + actualHeight / HOUR_HEIGHT;

    // 检查该整点是否接近任务的开始或结束时间（使用误差范围）
    const epsilon = 0.01;
    return Math.abs(hour - startHour) < epsilon || Math.abs(hour - endHour) < epsilon;
  });
};

// 任务位置和高度样式
const getTaskStyle = (task, index) => {
  const top = getTaskStartPosition(task, index);
  const height = getTaskActualHeight(task, index);

  return {
    top: `${top}px`,
    height: `${height}px`,
  };
};

// 设置活跃任务
const setActiveTask = (index) => {
  activeTaskIndex.value = index;
};

// 滚动处理 - 视差效果
const handleScroll = ({ scrollTop: st }) => {
  scrollTop.value = st;

  // 更新视口高度
  if (scrollbarRef.value?.wrapRef) {
    viewportHeight.value = scrollbarRef.value.wrapRef.clientHeight;
  }

  // 找到当前在视口中心的任务
  const containerCenter = st + viewportHeight.value / 2;
  let closestIndex = 0;
  let closestDistance = Infinity;

  taskRefs.value.forEach((el, index) => {
    if (el) {
      const taskTop = parseInt(el.style.top || 0);
      const taskHeight = parseInt(el.style.height || TASK_MIN_HEIGHT);
      const taskCenter = taskTop + taskHeight / 2;
      const distance = Math.abs(taskCenter - containerCenter);

      if (distance < closestDistance) {
        closestDistance = distance;
        closestIndex = index;
      }
    }
  });

  activeTaskIndex.value = closestIndex;
};

// 延时处理
const handleDelay = (taskIndex, newTimeRange, reason) => {
  const task = tasks.value[taskIndex];
  task.delayedFrom = task.timeRange;
  task.timeRange = newTimeRange;
  if (reason) {
    task.delayReason = reason;
  }
};

// 子任务切换
const toggleSubTask = (taskIndex, subTaskIndex) => {
  const task = tasks.value[taskIndex];
  if (task.subTasks && task.subTasks[subTaskIndex]) {
    task.subTasks[subTaskIndex].completed = !task.subTasks[subTaskIndex].completed;
  }
};

// 反馈按钮
const handleFeedback = (task) => {
  console.log('反馈任务:', task.title);
};

// 暂停按钮
const handlePause = (task) => {
  console.log('暂停任务:', task.title);
};

// 处理任务卡片 resize 事件
const handleResize = (taskIndex, newTimeRange, newHeight) => {
  const task = tasks.value[taskIndex];
  task.timeRange = newTimeRange;
  console.log('任务调整大小:', task.title, newTimeRange);
};

const handleUpdateTasks = (newDate) => {
  console.log('获取该日期的任务列表', newDate);
  tasks.value = getDailyTasksByDate(newDate);
};

// 初始化视口高度并滚动到7点位置
onMounted(() => {
  nextTick(() => {
    if (scrollbarRef.value?.wrapRef) {
      viewportHeight.value = scrollbarRef.value.wrapRef.clientHeight;
      // 滚动到7点位置（顶部留白处），再向上偏移10px
      scrollbarRef.value.wrapRef.scrollTop = topPadding.value - 10;
    }
  });
});
</script>

<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';
.view-container {
  display: flex;
  height: 100%;
  width: 100%;
  background-color: rgba(244, 246, 255, 1);

  .day-view {
    flex: 1;
    height: 100%;
    display: flex;
    min-width: 0;
    gap: var(--content-gap);
    padding: var(--content-padding);

    .day-view-left {
      flex: 1;
      padding: var(--content-padding);
    }

    .day-view-main {
      flex: 3;
      display: flex;
      flex-direction: column;
      padding: var(--content-padding);

      .day-scroll-container {
        width: 100%;
        height: calc(100% - pxToRem(60));

        .timeline-content {
          position: relative;
          width: 100%;

          // 顶部留白区域
          .top-padding-area {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            justify-content: center;
            position: relative;
            padding: 0;
            padding-left: pxToRem(12);

            .time-scale-marker {
              font-size: pxToRem(12);
              font-family: 'Inter-Medium';
              font-weight: 500;
              color: rgba(158, 174, 199, 0.7);
              font-style: italic;
              position: absolute;
              top: pxToRem(10);
              left: pxToRem(12);
              transform: translateY(0);
              margin-top: 0;
            }

            .greeting-text {
              font-size: pxToRem(20);
              font-family: 'Alibaba PuHuiTi-Regular';
              font-weight: 400;
              color: rgba(74, 64, 224, 0.6);
              text-align: center;
              align-self: center;
            }
          }

          // 底部留白区域
          .bottom-padding-area {
            position: absolute;
            bottom: 0;
            left: 0;
            right: 0;
            display: flex;
            flex-direction: column;
            align-items: flex-start;
            justify-content: center;
            padding: 0;
            padding-left: pxToRem(12);

            .ending-text {
              font-size: pxToRem(16);
              font-family: 'Alibaba PuHuiTi-Regular';
              font-weight: 400;
              color: rgba(0, 105, 71, 0.6);
              text-align: center;
              padding: 0 pxToRem(16);
              align-self: center;
            }

            .time-scale-marker {
              font-size: pxToRem(12);
              font-family: 'Inter-Medium';
              font-weight: 500;
              color: rgba(158, 174, 199, 0.7);
              font-style: italic;
              position: absolute;
              bottom: pxToRem(10);
              left: pxToRem(12);
              transform: translateY(50%);
              margin-bottom: 0;
            }
          }

          // 时间刻度
          .time-scale {
            position: absolute;
            left: 0;
            top: 0;
            width: pxToRem(80);
            height: 100%;

            .time-scale-item {
              position: absolute;
              left: 0;
              right: 0;
              display: flex;
              align-items: flex-start;
              transform: translateY(-50%);

              .time-label {
                font-size: pxToRem(12);
                font-family: 'Inter-Medium';
                font-weight: 500;
                color: rgba(158, 174, 199, 1);
                padding-right: pxToRem(12);
                line-height: 1;
                white-space: nowrap;
              }

              .time-line {
                flex: 1;
                height: pxToRem(1);
                background-color: rgba(220, 233, 255, 1);
                margin-top: pxToRem(6);
              }

              &.is-hidden {
                display: none;
              }

              &.is-task-boundary {
                .time-label {
                  color: rgba(74, 64, 224, 1);
                  font-weight: 600;
                }

                .time-line {
                  background-color: rgba(74, 64, 224, 0.3);
                }
              }

              &.expanded-task-tick {
                .time-label {
                  color: rgba(74, 64, 224, 1);
                  font-weight: 700;
                  font-size: pxToRem(13);
                }

                .time-line {
                  background-color: rgba(74, 64, 224, 0.6);
                  height: pxToRem(2);
                }
              }
            }
          }

          // 任务列表
          .task-list {
            position: absolute;
            left: pxToRem(96);
            top: 0;
            right: pxToRem(16);
            height: 100%;

            .task-item {
              position: absolute;
              left: 0;
              right: 0;
              transition: all 0.3s ease;
              z-index: 1;
              cursor: pointer;

              &.is-active {
                z-index: 10;
              }
            }
          }
        }
      }
    }

    .day-view-right {
      width: var(--aside-width);
      height: 100%;
      overflow-y: auto;
      flex-shrink: 0;
    }
  }
}
</style>
