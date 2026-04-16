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
          type="add"
          iconBtn
          @click.stop="$emit('addSubTask', task)"
        ></BaseButton>
        <BaseButton
          class="action-btn"
          type="detail"
          iconBtn
          @click.stop="showDetail = true"
        ></BaseButton>
      </div>
    </div>
    <!-- 详情悬浮弹窗 -->
    <Transition name="fade">
      <div v-if="showDetail" class="detail-popup" @click="showDetail = false">
        <div class="popup-content" @click.stop>
          <div class="popup-header">
            <h3 class="popup-title">{{ task.title }}</h3>
            <button class="popup-close" @click="showDetail = false">✕</button>
          </div>
          <div class="popup-body">
            <div class="detail-item">
              <span class="detail-label">类别</span>
              <span class="detail-value">{{ task.category }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">进度</span>
              <span class="detail-value">{{ task.progress }}%</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">开始时间</span>
              <span class="detail-value">{{ task.plannedStartTime || '未设置' }}</span>
            </div>
            <div class="detail-item">
              <span class="detail-label">结束时间</span>
              <span class="detail-value">{{ task.plannedEndTime || '未设置' }}</span>
            </div>
            <div v-if="task.description" class="detail-item">
              <span class="detail-label">描述</span>
              <p class="detail-description">{{ task.description }}</p>
            </div>
          </div>
        </div>
      </div>
    </Transition>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import BaseTag from '@/components/common/BaseTag.vue';
import BaseButton from '@/components/common/BaseButton.vue';

const props = defineProps({
  task: {
    type: Object,
    required: true,
  },
});

defineEmits(['addSubTask']);

const showDetail = ref(false);

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

/* 详情弹窗样式 */
.detail-popup {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  @include flexCenter;
  z-index: 1000;
  padding: pxToRem(20);

  .popup-content {
    @include wh(auto, auto);
    min-width: pxToRem(320);
    max-width: pxToRem(400);
    background: #fff;
    border-radius: pxToRem(12);
    box-shadow: 0 pxToRem(12) pxToRem(24) rgba(0, 0, 0, 0.15);
    overflow: hidden;

    .popup-header {
      @include flexCenter(space-between, center);
      padding: pxToRem(16) pxToRem(20);
      border-bottom: pxToRem(1) solid rgba(0, 0, 0, 0.06);

      .popup-title {
        @include fontStyle(4);
        color: rgba(32, 48, 68, 1);
        margin: 0;
      }

      .popup-close {
        @include whrem(24, 24);
        @include flexCenter;
        border: none;
        background: rgba(0, 0, 0, 0.05);
        border-radius: pxToRem(6);
        cursor: pointer;
        font-size: pxToRem(14);
        color: rgba(77, 93, 115, 0.6);

        &:hover {
          background: rgba(0, 0, 0, 0.1);
        }
      }
    }

    .popup-body {
      padding: pxToRem(16) pxToRem(20);

      .detail-item {
        @include flexCenter(flex-start, center, true);
        gap: pxToRem(8);
        padding: pxToRem(8) 0;
        border-bottom: pxToRem(1) dashed rgba(0, 0, 0, 0.06);

        &:last-child {
          border-bottom: none;
        }

        .detail-label {
          @include whrem(60);
          font-size: pxToRem(13);
          color: rgba(139, 154, 181, 1);
          flex-shrink: 0;
        }

        .detail-value {
          font-size: pxToRem(13);
          color: rgba(32, 48, 68, 1);
          flex: 1;
        }

        .detail-description {
          flex: 1;
          font-size: pxToRem(13);
          color: rgba(32, 48, 68, 1);
          line-height: 1.6;
          margin: 0;
          white-space: pre-wrap;
        }
      }
    }
  }
}

/* 弹窗过渡动画 */
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}

.fade-enter-active .popup-content,
.fade-leave-active .popup-content {
  transition: transform 0.2s ease;
}

.fade-enter-from .popup-content,
.fade-leave-to .popup-content {
  transform: scale(0.95);
}
</style>
