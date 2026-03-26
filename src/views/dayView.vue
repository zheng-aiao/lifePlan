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
          <div class="timeline-content">
            <!-- 时间刻度 - 从7点开始 -->
            <div class="time-scale">
              <div
                v-for="hour in 17"
                :key="hour"
                class="time-scale-item"
                :class="{ 'has-task': hasTaskAtHour(hour + 6) }"
              >
                <span class="time-label">{{ formatHour(hour + 6) }}</span>
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
                :style="getTaskPosition(task)"
              >
                <DayTaskCard
                  :task="task"
                  :is-active="activeTaskIndex === index"
                  @toggleSubTask="(idx) => toggleSubTask(index, idx)"
                  @feedback="handleFeedback(task)"
                  @pause="handlePause(task)"
                  @delay="(newTimeRange) => handleDelay(index, newTimeRange)"
                />
              </div>
            </div>
          </div>
        </el-scrollbar>
      </div>
      <div class="day-view-right"><AsideRight /></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';
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

const scrollbarRef = ref(null);
const taskRefs = ref([]);
const activeTaskIndex = ref(0);
const scrollTop = ref(0);

// 时间刻度相关 - 从7点开始，显示17个小时到23点
const START_HOUR = 7;

const formatHour = (hour) => {
  return `${hour.toString().padStart(2, '0')}:00`;
};

const hasTaskAtHour = (hour) => {
  return tasks.value.some((task) => {
    const startHour = parseInt(task.timeRange.split(':')[0]);
    const endHour = parseInt(task.timeRange.split('-')[1].split(':')[0]);
    return hour >= startHour && hour < endHour;
  });
};

// 任务位置计算（基于开始时间）- 从7点开始计算偏移
const getTaskPosition = (task) => {
  const startHour = parseInt(task.timeRange.split(':')[0]);
  const startMinute = parseInt(task.timeRange.split(':')[1].split('-')[0]);
  const top = ((startHour - START_HOUR) + startMinute / 60) * 80; // 每小时80px
  return { top: `${top}px` };
};

// 滚动处理 - 视差效果
const handleScroll = ({ scrollTop: st }) => {
  scrollTop.value = st;

  // 找到当前在视口中心的任务
  const containerCenter = st + 300; // 视口中心位置
  let closestIndex = 0;
  let closestDistance = Infinity;

  taskRefs.value.forEach((el, index) => {
    if (el) {
      const taskTop = parseInt(el.style.top || 0);
      const taskCenter = taskTop + el.offsetHeight / 2;
      const distance = Math.abs(taskCenter - containerCenter);

      if (distance < closestDistance) {
        closestDistance = distance;
        closestIndex = index;
      }
    }
  });

  activeTaskIndex.value = closestIndex;
};

// 任务数据 - 从 mock 文件导入
const tasks = ref(dailyTasks);

// 延时处理
const handleDelay = (taskIndex, newTimeRange) => {
  const task = tasks.value[taskIndex];
  // 保存原时间到 delayedFrom
  task.delayedFrom = task.timeRange;
  // 更新时间
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
  // 从 mock 获取指定日期的任务
  tasks.value = getDailyTasksByDate(newDate);
};
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
          min-height: 1360px; // 17小时 * 80px (从7点到23点)
          padding: 1rem 0;

          // 时间刻度
          .time-scale {
            position: absolute;
            left: 0;
            top: 0;
            width: 5rem;
            height: 100%;

            .time-scale-item {
              height: 80px;
              display: flex;
              align-items: flex-start;
              position: relative;

              .time-label {
                font-size: 0.75rem;
                font-family: 'Inter-Medium';
                font-weight: 500;
                color: rgba(158, 174, 199, 1);
                padding-right: 0.75rem;
                line-height: 1;
              }

              .time-line {
                flex: 1;
                height: 0.0625rem;
                background-color: rgba(220, 233, 255, 1);
                margin-top: 0.375rem;
              }

              &.has-task {
                .time-label {
                  color: rgba(74, 64, 224, 1);
                  font-weight: 600;
                }

                .time-line {
                  background-color: rgba(74, 64, 224, 0.3);
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
