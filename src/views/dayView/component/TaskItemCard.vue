<template>
  <div class="task-item-card" :style="{ borderLeftColor: task.color }">
    <div class="task-header">
      <div class="task-header-left">
        <p class="task-title">{{ task.title }}</p>
        <BaseTag>{{ task.category }}</BaseTag>
      </div>
      <div class="task-header-right">
        <div v-if="task.plannedStartTime || task.plannedEndTime" class="task-time">
          <span class="iconfont time-icon" v-html="'&#xe74f;'"></span>
          <span class="time-text">{{ formatTimeRange }}</span>
        </div>
      </div>
    </div>
    <div class="task-content">
      <div class="task-progress">
        <div class="progress-bar">
          <div
            class="progress-fill"
            :style="{
              width: task.progress + '%',
              backgroundColor: task.color,
            }"
          ></div>
        </div>
        <p class="percentage-text">{{ task.progress }}%</p>
      </div>
      <div class="task-actions">
        <BaseButton
          class="action-btn"
          type="detail"
          iconBtn
          @click.stop="showDetail = true"
        ></BaseButton>
        <BaseButton
          v-if="task.taskStatus === 4"
          class="action-btn"
          type="assgin"
          iconBtn
          @click.stop="showTimePicker = true"
        ></BaseButton>
      </div>
    </div>

    <!-- 详情弹窗组件 -->
    <TaskDetailDialog
      :visible="showDetail"
      :task="props.task"
      @close="showDetail = false"
      @update="handleTaskUpdate"
    />

    <TimePickerDialog
      :visible="showTimePicker"
      :task="props.task"
      @close="showTimePicker = false"
      @confirm="handleTimeConfirm"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import BaseTag from '@/components/common/BaseTag.vue';
import BaseButton from '@/components/common/BaseButton.vue';
import TimePickerDialog from '@/views/dayView/dialog/TimePickerDialog.vue';
import TaskDetailDialog from '@/views/dayView/dialog/TaskDetailDialog.vue';

const props = defineProps({
  task: {
    type: Object,
    required: true,
  },
});

defineEmits(['addSubTask', 'addSubTaskWithTime']);

const showDetail = ref(false);
const showTimePicker = ref(false);

import bizService from '@/utils/bizService';
import eventBus from '@/utils/eventBus';
import { combineDateAndTime, getCurrentDateString } from '@/utils/dateUtil';

// 处理时间选择确认
const handleTimeConfirm = async (timeRange) => {
  if (timeRange && timeRange.length === 2) {
    const [startTime, endTime] = timeRange;

    try {
      // 调用后端重新分配任务接口，传递任务ID和新的开始结束时间
      const response = await bizService.task.reallocateTask(props.task.id, startTime, endTime);

      // 发送任务创建成功的事件，用于刷新界面
      eventBus.emit('taskCreated', getCurrentDateString());

      console.log('任务重新分配成功:', response);
    } catch (error) {
      console.error('任务重新分配失败:', error);
      // 可以在这里添加错误提示给用户
    }

    showTimePicker.value = false;
  }
};

// 处理任务更新
const handleTaskUpdate = () => {
  // 通知父组件刷新任务数据
  eventBus.emit('taskUpdated', props.task.id);
};

const formatTimeRange = computed(() => {
  const { plannedStartTime, plannedEndTime, taskType } = props.task;

  if (!plannedStartTime && !plannedEndTime) return '';

  const formatDate = (dateStr) => {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return dateStr;
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${month}.${day}`;
  };

  const startDate = formatDate(plannedStartTime);
  const endDate = formatDate(plannedEndTime);

  // 年任务(1)和月任务(2)显示日期范围
  if (taskType === 1 || taskType === 2) {
    if (startDate && endDate) {
      return `${startDate}-${endDate}`;
    }
    return startDate || endDate;
  }

  // 日任务(3)显示当日日期
  if (taskType === 3) {
    return startDate || endDate || '';
  }

  return '';
});
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.task-item-card {
  @include wh(100%, pxToRem(70));
  @include flexCenter(center, center, true);
  padding: pxToRem(12) pxToRem(16);
  border-radius: pxToRem(8);
  box-shadow: 0 pxToRem(1) pxToRem(2) 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
  border-left: pxToRem(4) solid;
  position: relative;
  flex-shrink: 0;
  overflow: hidden;
  gap: pxToRem(12);

  .task-header {
    @include wh(100%, pxToRem(24));
    @include flexCenter(space-between, center);
    position: relative;

    .task-header-left {
      @include flexCenter(flex-start, center);
      gap: pxToRem(8);
      min-width: 0;

      .task-title {
        @include fontStyle(2);
        color: var(--textColor-1);
        margin: 0;
        flex: 1;
        min-width: 0;
        @include oneLineTextHidden;
      }
    }

    .task-header-right {
      flex-shrink: 0;

      .task-time {
        @include flexCenter(center, center);
        gap: pxToRem(4);

        .time-icon {
          font-size: pxToRem(10);
        }

        .time-text {
          font-size: pxToRem(12);
          color: rgba(104, 120, 143, 1);
          font-weight: 500;
        }
      }
    }
  }

  .task-content {
    @include wh(100%, pxToRem(16));
    @include flexCenter(space-between, center);
    gap: pxToRem(20);

    .task-progress {
      flex: 1;
      @include flexCenter(space-between, center);
      gap: pxToRem(16);

      .progress-bar {
        flex: 1;
        @include wh(100%, pxToRem(6));
        border-radius: pxToRem(9999);
        background-color: rgba(220, 233, 255, 1);
        position: relative;
        overflow: hidden;

        .progress-fill {
          @include wh(100%, 100%);
          position: absolute;
          left: 0;
          top: 0;
          border-radius: pxToRem(9999);
        }
      }

      .percentage-text {
        font-size: pxToRem(12);
        font-weight: 500;
        color: rgba(104, 120, 143, 1);
        margin: 0;
        flex-shrink: 0;
      }
    }

    .task-actions {
      @include flexCenter(center, center);
      gap: pxToRem(8);
      flex-shrink: 0;

      .action-btn {
        @include whrem(24, 24);
        @include flexCenter;
        border: none;
        border-radius: pxToRem(6);
        background: rgba(245, 250, 255, 1);
        cursor: pointer;
        transition: all 0.2s ease;

        &:hover {
          background: rgba(74, 64, 224, 0.1);
          transform: scale(1.1);
        }
      }
    }
  }
}
</style>
