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
                  @toggleSubTask="(idx) => toggleSubTask(index, idx)"
                  @feedback="handleFeedback(task)"
                  @pause="handlePause(task)"
                  @delay="(newTimeRange) => handleDelay(index, newTimeRange)"
                />
              </div>
            </div>

            <!-- 底部留白区域 -->
            <div class="bottom-padding-area" :style="{ height: `${topPadding}px` }">
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
import DayTaskCard from '@/components/day/DayTaskCard.vue';
import DayTaskHandle from '@/components/day/DayTaskHandle.vue';
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

// 计算总高度（加上下留白，各为视口1/3高度）
const totalHeight = computed(() => {
  const contentHeight = (END_HOUR - START_HOUR) * HOUR_HEIGHT;
  const paddingHeight = (viewportHeight.value * 2) / 3; // 上下各1/3，总共2/3视口高度
  return contentHeight + paddingHeight;
});

// 获取顶部/底部留白高度（各为视口1/3）
const topPadding = computed(() => {
  return viewportHeight.value / 3;
});

// 时间刻度位置计算（加上顶部留白偏移）
const getTickPosition = (hourIndex) => {
  return topPadding.value + hourIndex * HOUR_HEIGHT;
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

// 获取任务的实际高度（考虑是否选中）
const getTaskActualHeight = (task, index) => {
  const baseHeight = getTaskBaseHeight(task);
  const contentHeight = calculateTaskContentHeight(task);

  if (activeTaskIndex.value === index && contentHeight > baseHeight) {
    return contentHeight;
  }
  return baseHeight;
};

// 判断任务是否展开（高度超过时长决定的高度）
const isTaskExpanded = (task, index) => {
  const baseHeight = getTaskBaseHeight(task);
  const contentHeight = calculateTaskContentHeight(task);
  return activeTaskIndex.value === index && contentHeight > baseHeight;
};

// 展开任务的起止时间刻度
const expandedTaskTicks = computed(() => {
  const ticks = [];

  tasks.value.forEach((task, index) => {
    if (isTaskExpanded(task, index)) {
      const [start, end] = task.timeRange.split('-');
      const startParsed = parseTime(start);
      const endParsed = parseTime(end);

      const startPosition = getTimePosition(startParsed.hour, startParsed.min);
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

// 判断整点刻度是否应该隐藏（因为被展开的任务覆盖）
const shouldHideHourTick = (hour) => {
  // 检查是否有展开的任务覆盖了该整点时间
  return tasks.value.some((task, index) => {
    if (!isTaskExpanded(task, index)) return false;

    const [start, end] = task.timeRange.split('-');
    const startParsed = parseTime(start);
    const endParsed = parseTime(end);

    const startHour = startParsed.hour + startParsed.min / 60;
    const actualHeight = getTaskActualHeight(task, index);
    const endHour = startHour + actualHeight / HOUR_HEIGHT;

    // 如果该整点时间在展开任务的时间范围内（不包括起止点），则隐藏
    return hour > startHour && hour < endHour;
  });
};

// 判断是否是任务边界时间
const isTaskBoundary = (hour) => {
  return tasks.value.some((task) => {
    const [start, end] = task.timeRange.split('-');
    const startHour = parseInt(start.split(':')[0]);
    const endHour = parseInt(end.split(':')[0]);
    return hour === startHour || hour === endHour;
  });
};

// 任务位置和高度样式
const getTaskStyle = (task, index) => {
  const [start] = task.timeRange.split('-');
  const { hour, min } = parseTime(start);
  const top = getTimePosition(hour, min);
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
const handleDelay = (taskIndex, newTimeRange) => {
  const task = tasks.value[taskIndex];
  task.delayedFrom = task.timeRange;
  task.timeRange = newTimeRange;
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
      width: 20rem;
      padding: var(--content-padding);
    }

    .day-view-main {
      flex: 1;
      display: flex;
      flex-direction: column;

      .day-scroll-container {
        width: 100%;
        height: calc(100% - 3.75rem);

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
            padding-left: 0.75rem;

            .time-scale-marker {
              font-size: 0.75rem;
              font-family: 'Inter-Medium';
              font-weight: 500;
              color: rgba(158, 174, 199, 0.7);
              font-style: italic;
              position: absolute;
              top: 10px;
              left: 0.75rem;
              transform: translateY(0);
              margin-top: 0;
            }

            .greeting-text {
              font-size: 1.25rem;
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
            padding-left: 0.75rem;

            .ending-text {
              font-size: 1rem;
              font-family: 'Alibaba PuHuiTi-Regular';
              font-weight: 400;
              color: rgba(0, 105, 71, 0.6);
              text-align: center;
              padding: 0 1rem;
              align-self: center;
            }

            .time-scale-marker {
              font-size: 0.75rem;
              font-family: 'Inter-Medium';
              font-weight: 500;
              color: rgba(158, 174, 199, 0.7);
              font-style: italic;
              position: absolute;
              bottom: 10px;
              left: 0.75rem;
              transform: translateY(50%);
              margin-bottom: 0;
            }
          }

          // 时间刻度
          .time-scale {
            position: absolute;
            left: 0;
            top: 0;
            width: 5rem;
            height: 100%;

            .time-scale-item {
              position: absolute;
              left: 0;
              right: 0;
              display: flex;
              align-items: flex-start;
              transform: translateY(-50%);

              .time-label {
                font-size: 0.75rem;
                font-family: 'Inter-Medium';
                font-weight: 500;
                color: rgba(158, 174, 199, 1);
                padding-right: 0.75rem;
                line-height: 1;
                white-space: nowrap;
              }

              .time-line {
                flex: 1;
                height: 0.0625rem;
                background-color: rgba(220, 233, 255, 1);
                margin-top: 0.375rem;
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
                  font-size: 0.8125rem;
                }

                .time-line {
                  background-color: rgba(74, 64, 224, 0.6);
                  height: 0.125rem;
                }
              }
            }
          }

          // 任务列表
          .task-list {
            position: absolute;
            left: 6rem;
            top: 0;
            right: 1rem;
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
