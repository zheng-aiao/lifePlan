<template>
  <Transition name="fade">
    <div v-if="visible" class="detail-popup" @click="$emit('close')">
      <div class="popup-content" @click.stop>
        <!-- 弹窗头部 -->
        <div class="popup-header">
          <div class="header-left">
            <h3 class="popup-title">{{ task.title }}</h3>
            <div class="header-tags">
              <span class="tag category-tag">{{ task.category }}</span>
              <span class="tag priority-tag">{{ getPriorityText(task.taskPriority) }}</span>
            </div>
          </div>
          <div class="header-right">
            <span class="header-date">{{ formatHeaderDate }}</span>
          </div>
        </div>

        <!-- 弹窗主体内容 -->
        <div class="popup-body">
          <div class="detail-content">
            <p class="detail-text">{{ task.description || '暂无任务详情' }}</p>
          </div>
        </div>

        <!-- 弹窗底部操作 -->
        <div class="popup-footer">
          <button class="btn-edit">编辑任务</button>
          <button class="btn-close" @click="$emit('close')">关闭</button>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import { computed } from 'vue';

const props = defineProps({
  visible: {
    type: Boolean,
    default: false,
  },
  task: {
    type: Object,
    required: true,
  },
});

defineEmits(['close']);

// 获取优先级文本
const getPriorityText = (priority) => {
  const priorityMap = {
    1: '低优先级',
    2: '中优先级',
    3: '高优先级',
  };
  return priorityMap[priority] || '未设置';
};

// 弹窗头部日期格式化
const formatHeaderDate = computed(() => {
  const { plannedStartTime, plannedEndTime, taskType } = props.task;

  if (!plannedStartTime && !plannedEndTime) return '未设置';

  const formatShortDate = (dateStr) => {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return '';
    const month = date.getMonth() + 1;
    const day = date.getDate();
    return `${month}.${day}`;
  };

  const formatTime = (dateStr) => {
    if (!dateStr) return '';
    const date = new Date(dateStr);
    if (isNaN(date.getTime())) return '';
    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${hours}:${minutes}`;
  };

  // 年任务(1)和月任务(2)显示日期范围
  if (taskType === 1 || taskType === 2) {
    const startDate = formatShortDate(plannedStartTime);
    const endDate = formatShortDate(plannedEndTime);

    // 获取年份
    let year = '';
    if (plannedStartTime) {
      const date = new Date(plannedStartTime);
      if (!isNaN(date.getTime())) {
        year = date.getFullYear();
      }
    } else if (plannedEndTime) {
      const date = new Date(plannedEndTime);
      if (!isNaN(date.getTime())) {
        year = date.getFullYear();
      }
    }

    const datePart = startDate && endDate ? `${startDate}-${endDate}` : startDate || endDate || '';
    const yearPart = year ? ` | ${year}` : '';

    return datePart + yearPart || '未设置';
  }

  // 日任务(3)显示时间范围和日期
  if (taskType === 3) {
    const startTime = formatTime(plannedStartTime);
    const endTime = formatTime(plannedEndTime);
    const date = formatShortDate(plannedStartTime) || formatShortDate(plannedEndTime);

    const timePart = startTime && endTime ? `${startTime}-${endTime}` : '';

    if (timePart && date) {
      return `${timePart} | ${date}`;
    }
    return date || timePart || '未设置';
  }

  return '未设置';
});
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

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
    min-width: pxToRem(360);
    max-width: pxToRem(520);
    width: 90%;
    height: auto;
    background: #fff;
    border-radius: pxToRem(16);
    box-shadow: 0 pxToRem(12) pxToRem(32) rgba(0, 0, 0, 0.15);
    overflow: hidden;
    @include flexCenter(center, false, true);
    gap: pxToRem(12);
    padding: 0 pxToRem(24) 0 pxToRem(24);

    .popup-header {
      @include flexCenter(space-between, center);
      padding: pxToRem(24) 0 pxToRem(12) 0;
      background: rgba(255, 255, 255, 1);

      .header-left {
        flex: 1;

        .popup-title {
          font-size: pxToRem(20);
          font-weight: 600;
          color: rgba(32, 48, 68, 1);
          margin: 0 0 pxToRem(8) 0;
          line-height: 1.4;
        }

        .header-tags {
          @include flexCenter(flex-start, center);
          gap: pxToRem(8);

          .tag {
            @include wh(auto, pxToRem(22));
            @include flexCenter;
            padding: 0 pxToRem(10);
            border-radius: pxToRem(4);
            font-size: pxToRem(12);
            font-weight: 500;

            &.category-tag {
              background: rgba(208, 222, 255, 1);
              color: rgba(74, 64, 224, 1);
            }

            &.priority-tag {
              background: rgba(255, 238, 226, 1);
              color: rgba(255, 102, 0, 1);
            }
          }
        }
      }

      .header-right {
        flex-shrink: 0;

        .header-date {
          font-size: pxToRem(13);
          color: rgba(104, 120, 143, 1);
          font-weight: 500;
          white-space: nowrap;
        }
      }
    }

    .popup-body {
      max-height: pxToRem(320);
      min-height: pxToRem(120);
      overflow-y: auto;
      background: rgba(250, 252, 255, 1);

      flex: 1;

      .detail-content {
        border-radius: pxToRem(12);
        padding: pxToRem(16);

        .detail-text {
          font-size: pxToRem(14);
          color: rgba(32, 48, 68, 1);
          line-height: 1.8;
          margin: 0;
          white-space: pre-wrap;
          word-break: break-all;
        }
      }
    }

    .popup-footer {
      @include flexCenter(flex-end, center);
      gap: pxToRem(12);
      padding: pxToRem(16) 0;
      border-top: pxToRem(1) solid rgba(0, 0, 0, 0.06);
      background: rgba(255, 255, 255, 1);

      .btn-edit {
        @include whrem(88, 36);
        @include flexCenter;
        border: none;
        background: transparent;
        border-radius: pxToRem(6);
        cursor: pointer;
        font-size: pxToRem(14);
        color: rgba(104, 120, 143, 1);
        font-weight: 500;
        transition: all 0.2s ease;

        &:hover {
          color: rgba(74, 64, 224, 1);
        }
      }

      .btn-close {
        @include whrem(88, 36);
        @include flexCenter;
        border: none;
        background: rgba(74, 64, 224, 1);
        border-radius: pxToRem(6);
        cursor: pointer;
        font-size: pxToRem(14);
        color: #fff;
        font-weight: 500;
        transition: all 0.2s ease;

        &:hover {
          background: rgba(74, 64, 224, 0.9);
        }
      }
    }
  }
}
</style>
