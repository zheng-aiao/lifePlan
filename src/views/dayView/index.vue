<template>
  <div class="view-container">
    <div class="day-view">
      <AsideLeft />
      <div class="day-view-main">
        <DayTaskHandle @updateTasks="handleUpdateTasks" />
        <el-scrollbar ref="scrollbarRef" class="day-scroll-container" @scroll="handleScroll">
          <div class="timeline-content" :style="{ minHeight: `${totalHeight}px` }">
            <!-- 顶部留白区域 -->
            <div class="top-padding-area" :style="{ height: `${topPadding}px` }">
              <div class="time-scale-marker start-marker">开始</div>
              <div class="greeting-text">又是元气满满的一天</div>
            </div>

            <!-- 时间刻度 - 7-23 点整点显示 -->
            <div class="time-scale">
              <!-- 整点刻度 -->
              <div
                v-for="hour in 17"
                :key="hour"
                v-show="!isHourInTaskRange(hour + 6)"
                class="time-scale-item"
                :class="{
                  'is-task-boundary': isTaskBoundary(hour + 6),
                }"
                :style="{ top: `${getTickPosition(hour - 1)}px` }"
              >
                <span class="time-label">{{ formatHour(hour + 6) }}</span>
                <div class="time-line"></div>
              </div>

              <!-- 任务边界刻度（包括整点和非整点） -->
              <div
                v-for="(tick, index) in taskBoundaryTicks"
                :key="`boundary-${index}`"
                class="time-scale-item task-boundary-tick"
                :class="tick.type"
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
import { ref, computed, onMounted, nextTick, onUnmounted } from 'vue';
import AsideLeft from '@/views/dayView/component/AsideLeft.vue';
import AsideRight from '@/views/dayView/component/AsideRight.vue';
import DayTaskCard from './component/DayTaskCard.vue';
import DayTaskHandle from './component/DayTaskHandle.vue';
import { mapTaskStatusText } from '@/emun/constant';
import eventBus from '@/utils/eventBus';
import bizService from '@/utils/bizService';

// 常量定义
const START_HOUR = 7; // 时间线从 7 点开始
const END_HOUR = 23; // 时间线到 23 点结束
const TASK_MIN_HEIGHT = 60; // 半小时任务的最小高度（像素）
const HOUR_HEIGHT = TASK_MIN_HEIGHT * 2; // 1 小时的高度（120px）

const scrollbarRef = ref(null);
const taskRefs = ref([]);
const activeTaskIndex = ref(0);
const scrollTop = ref(0);

// 任务数据 - 从后端加载
const tasks = ref([]);

// 视口高度（用于计算上下留白）
const viewportHeight = ref(600);

// 计算总高度（加上下留白，再加上所有展开任务的额外高度，以及最后一个任务的实际高度）
const totalHeight = computed(() => {
  const contentHeight = (END_HOUR - START_HOUR) * HOUR_HEIGHT;
  const paddingHeight = topPadding.value + buttomPadding.value; // 顶部留白 + 底部留白
  const lastTaskOffset = getTaskOffset(tasks.value.length);

  // 计算最后一个任务的实际高度（如果有任务）
  let lastTaskHeight = 0;
  if (tasks.value.length > 0) {
    const lastTask = tasks.value[tasks.value.length - 1];
    lastTaskHeight = getTaskActualHeight(lastTask, tasks.value.length - 1);
  }

  // 确保总高度至少能容纳所有内容，包括最后一个任务的完整高度
  return Math.max(
    contentHeight + paddingHeight + lastTaskOffset,
    topPadding.value + lastTaskOffset + lastTaskHeight + buttomPadding.value
  );
});

// 获取顶留白高度（各为视口 1/3）
const topPadding = computed(() => {
  return viewportHeight.value / 3;
});

// 获取底部留白高度（为视口高度的 0.5 倍，确保可以滚动到最后的任务）
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
    const actualHeight = getTaskActualHeight(task, i);
    const baseHeight = 120; // 固定基础高度

    // 只考虑展开的任务
    if (actualHeight <= baseHeight) continue;

    const extraHeight = actualHeight - baseHeight;
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

// 计算任务的基础高度（固定高度）
const getTaskBaseHeight = (task) => {
  return TASK_MIN_HEIGHT * 2; // 固定高度：120px（1小时）
};

// 计算任务内容完全展开所需的高度（固定高度）
const calculateTaskContentHeight = (task) => {
  return 300; // 固定展开高度：300px
};

// 获取任务的实际高度（固定高度）
const getTaskActualHeight = (task, index) => {
  // 根据是否是活跃任务决定高度
  // 活跃任务（展开态）：300px
  // 非活跃任务（折叠态）：120px
  return activeTaskIndex.value === index ? 300 : 120;
};

// 判断任务是否展开（根据活跃状态）
const isTaskExpanded = (task, index) => {
  return activeTaskIndex.value === index;
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
    const actualHeight = getTaskActualHeight(tasks.value[i], i);
    const baseHeight = 120; // 固定基础高度
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

// 判断整点刻度是否在任务范围内（应该隐藏）
const isHourInTaskRange = (hour) => {
  return tasks.value.some((task) => {
    const [start, end] = task.timeRange.split('-');
    const startParsed = parseTime(start);
    const endParsed = parseTime(end);

    // 检查该整点是否在任务时间范围内（不包括边界）
    const hourDecimal = hour;
    const startDecimal = startParsed.hour + startParsed.min / 60;
    const endDecimal = endParsed.hour + endParsed.min / 60;

    // 如果整点是任务的开始或结束时间，不隐藏
    if (hourDecimal === startDecimal || hourDecimal === endDecimal) {
      return false;
    }

    // 如果整点在任务范围内，隐藏
    return hourDecimal > startDecimal && hourDecimal < endDecimal;
  });
};

// 判断是否是任务边界时间（整点）
const isTaskBoundary = (hour) => {
  return tasks.value.some((task, index) => {
    const [start, end] = task.timeRange.split('-');
    const startParsed = parseTime(start);
    const endParsed = parseTime(end);

    // 检查该整点是否是任务的开始或结束时间（整点）
    return (
      (startParsed.hour === hour && startParsed.min === 0) ||
      (endParsed.hour === hour && endParsed.min === 0)
    );
  });
};

// 获取所有任务的边界时间（包括整点和非整点）
const taskBoundaryTicks = computed(() => {
  const ticks = [];

  tasks.value.forEach((task, index) => {
    const [start, end] = task.timeRange.split('-');
    const startParsed = parseTime(start);
    const endParsed = parseTime(end);

    // 计算开始时间位置
    const startPosition = getTaskStartPosition(task, index);
    // 计算结束时间位置
    const actualHeight = getTaskActualHeight(task, index);
    const endPosition = startPosition + actualHeight;

    // 添加开始时间刻度（包括整点和非整点）
    ticks.push({
      position: startPosition,
      label: start,
      type: 'start',
    });

    // 添加结束时间刻度（包括整点和非整点）
    ticks.push({
      position: endPosition,
      label: end,
      type: 'end',
    });
  });

  return ticks;
});

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

// 当前日期（使用本地时间）
const formatCurrentDate = () => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  return `${year}-${month}-${day}`;
};

const currentDate = ref(formatCurrentDate());

// 计算当前时间对应的页面位置
const getCurrentTimePosition = () => {
  const now = new Date();
  const currentHour = now.getHours();
  const currentMinute = now.getMinutes();

  console.log('Current time:', currentHour, ':', currentMinute);
  console.log('START_HOUR:', START_HOUR);

  // 计算从7点开始的分钟数
  const minutesFromStart = (currentHour - START_HOUR) * 60 + currentMinute;
  console.log('Minutes from start:', minutesFromStart);

  // 计算对应的像素位置（半小时60px）
  const pixelPosition = topPadding.value + (minutesFromStart / 30) * TASK_MIN_HEIGHT;
  console.log('Top padding:', topPadding.value);
  console.log('Pixel position:', pixelPosition);

  return pixelPosition;
};

// 滚动到当前时间点
const scrollToCurrentTime = () => {
  // 确保视口高度已初始化
  if (!scrollbarRef.value?.wrapRef) {
    console.error('Scrollbar wrapRef not found');
    return;
  }

  // 重新获取视口高度
  viewportHeight.value = scrollbarRef.value.wrapRef.clientHeight;

  // 计算当前时间位置
  const currentTimePosition = getCurrentTimePosition();
  console.log('Current time position:', currentTimePosition);
  console.log('Viewport height:', viewportHeight.value);

  // 计算滚动位置，使当前时间点位于视口中央
  const containerCenter = currentTimePosition - viewportHeight.value / 2;
  console.log('Container center:', containerCenter);

  // 确保滚动位置不小于0
  const scrollPosition = Math.max(0, containerCenter);
  console.log('Scroll position:', scrollPosition);

  // 滚动到指定位置
  scrollbarRef.value.wrapRef.scrollTop = scrollPosition;
};

// 刷新任务数据
const refreshTaskData = async () => {
  await loadTaskDetails(currentDate.value);
  // 刷新后滚动到当前时间点
  nextTick(() => {
    scrollToCurrentTime();
  });
};

// 延时处理
const handleDelay = async (taskIndex, newTimeRange, reason) => {
  const task = tasks.value[taskIndex];
  task.delayedFrom = task.timeRange;
  task.timeRange = newTimeRange;
  if (reason) {
    task.delayReason = reason;
  }
  // 刷新任务数据
  await refreshTaskData();
};

// 子任务切换
const toggleSubTask = async (taskIndex, subTaskIndex) => {
  const task = tasks.value[taskIndex];
  if (task.subTasks && task.subTasks[subTaskIndex]) {
    task.subTasks[subTaskIndex].completed = !task.subTasks[subTaskIndex].completed;
  }
  // 刷新任务数据
  await refreshTaskData();
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

// 格式化时间
const formatTime = (dateTimeStr) => {
  if (!dateTimeStr) return '';
  const date = new Date(dateTimeStr);
  const hours = date.getHours().toString().padStart(2, '0');
  const minutes = date.getMinutes().toString().padStart(2, '0');
  return `${hours}:${minutes}`;
};

// 加载任务详情列表（从后端获取完整数据）
const loadTaskDetails = async (date) => {
  try {
    const response = await bizService.task.getTaskDetailsByDate(date);
    if (response.data && response.data) {
      tasks.value = response.data.map((task) => ({
        ...task,
        statusText: mapTaskStatusText(task.taskStatus),
        timeRange: formatTimeRange(task.plannedStartTime, task.plannedEndTime),
        actualDuration: formatDuration(task.actualDuration),
        borderColor: task.borderColor || 'rgba(74, 64, 224, 1)',
        bgColor: task.bgColor || 'rgba(255, 255, 255, 1)',
        opacity: task.opacity || 1,
        // 子任务列表（后端已返回）
        subTasks: task.subTasks
          ? task.subTasks.map((subTask) => ({
              ...subTask,
              completed: subTask.status === 1,
            }))
          : [],
        // 活动日志列表（后端已返回）
        activities: task.activities
          ? task.activities.map((activity) => {
              const typeMap = {
                0: { type: 'info', typeText: '创建任务' },
                1: { type: 'start', typeText: '开始任务' },
                2: { type: 'pause', typeText: '暂停任务' },
                3: { type: 'resume', typeText: '恢复任务' },
                4: { type: 'complete', typeText: '完成任务' },
                5: { type: 'abandon', typeText: '放弃任务' },
                6: { type: 'delay', typeText: '延时任务' },
              };
              const typeInfo = typeMap[activity.changeType] || { type: 'info', typeText: '信息' };
              return {
                id: activity.id,
                taskId: activity.taskId,
                changeType: activity.changeType,
                type: typeInfo.type,
                typeText: typeInfo.typeText,
                time: formatTime(activity.createdAt),
                description: activity.feedbackContent || typeInfo.typeText,
                userId: activity.userId,
                createdAt: activity.createdAt,
              };
            })
          : [],
      }));
    }
  } catch (error) {
    console.error('加载任务详情失败:', error);
  }
};

// 格式化时间范围
const formatTimeRange = (startTime, endTime) => {
  if (!startTime || !endTime) return '00:00 - 00:00';
  const start = formatTime(startTime);
  const end = formatTime(endTime);
  return `${start} - ${end}`;
};

// 格式化持续时间
const formatDuration = (minutes) => {
  if (!minutes) return '0min';
  const hours = Math.floor(minutes / 60);
  const mins = minutes % 60;
  if (hours > 0) {
    return `${hours}h ${mins}min`;
  }
  return `${mins}min`;
};

const handleUpdateTasks = async (newDate) => {
  console.log('获取该日期的任务详情', newDate);
  currentDate.value = newDate;
  await loadTaskDetails(newDate);
};

// 初始化视口高度并滚动到 7 点位置
onMounted(async () => {
  // 加载任务详情（从后端获取完整数据）
  await loadTaskDetails(currentDate.value);

  nextTick(() => {
    if (scrollbarRef.value?.wrapRef) {
      viewportHeight.value = scrollbarRef.value.wrapRef.clientHeight;
      // 滚动到当前时间点
      scrollToCurrentTime();
    }
  });

  // 监听任务创建成功事件
  eventBus.on('taskCreated', handleTaskCreated);
});

// 组件卸载时移除事件监听
onUnmounted(() => {
  eventBus.off('taskCreated', handleTaskCreated);
});

// 处理任务创建成功事件
const handleTaskCreated = async (date) => {
  console.log('收到任务创建成功事件，日期:', date);
  // 刷新任务数据
  await refreshTaskData();
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;
.view-container {
  @include wh(100%, 100%);
  @include flexCenter;
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
      height: 100%;
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

              &.task-boundary-tick {
                .time-label {
                  color: rgba(74, 64, 224, 1);
                  font-weight: 700;
                  font-size: pxToRem(13);
                }

                .time-line {
                  background-color: rgba(74, 64, 224, 0.6);
                  height: pxToRem(2);
                }

                &.start {
                  .time-line {
                    background-color: rgba(34, 197, 94, 0.6);
                  }

                  .time-label {
                    color: rgba(34, 197, 94, 1);
                  }
                }

                &.end {
                  .time-line {
                    background-color: rgba(249, 115, 22, 0.6);
                  }

                  .time-label {
                    color: rgba(249, 115, 22, 1);
                  }
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
