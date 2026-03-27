<template>
  <BaseDialog
    v-model="visible"
    title="延时任务"
    size="large"
    :confirm-text="'确认延时'"
    :confirm-icon="Timer"
    @confirm="handleConfirm"
    @cancel="handleCancel"
    @close="handleClose"
  >
    <template #headerExtra>
      <div class="task-info">
        <el-icon class="task-icon"><Document /></el-icon>
        <span class="task-name">{{ taskTitle }}</span>
      </div>
    </template>

    <div class="delay-content">
      <div class="top-section">
        <h4 class="section-title">时间线对比</h4>
        <div class="timeline-visual">
          <div class="timeline-track">
            <div class="timeline-node start-node">
              <div class="node-point">
                <div class="node-inner"></div>
              </div>
              <div class="node-label">{{ originalStartTime }}</div>
            </div>

            <div class="timeline-line solid-line"></div>

            <div class="timeline-node end-node">
              <div class="node-point">
                <div class="node-inner"></div>
              </div>
              <div class="node-label">{{ originalEndTime }} (原定)</div>
            </div>

            <template v-if="delayHistory.length > 0">
              <div
                v-for="(delay, index) in delayHistory"
                :key="index"
                class="timeline-delay-segment"
              >
                <div class="delay-line-container">
                  <div class="delay-badge">
                    <span>+{{ delay.duration }}min</span>
                  </div>
                  <div class="timeline-line dashed-line"></div>
                </div>
                <div class="timeline-node delay-node">
                  <div class="node-point">
                    <div class="node-inner"></div>
                  </div>
                  <div class="node-label">{{ delay.time }}</div>
                </div>
              </div>
            </template>

            <div v-if="selectedQuickDelay || form.endTime" class="timeline-delay-segment">
              <div class="delay-line-container">
                <div class="delay-badge active">
                  <span>{{ delayText }}</span>
                </div>
                <div class="timeline-line dashed-line active"></div>
              </div>
              <div class="timeline-node new-end-node">
                <div class="node-point">
                  <div class="node-inner"></div>
                </div>
                <div class="node-label">{{ newEndTime }} (新时间)</div>
              </div>
            </div>

            <div
              v-if="!selectedQuickDelay && !form.endTime"
              class="timeline-line fade-out-line"
            ></div>
          </div>
        </div>
      </div>

      <div class="bottom-section">
        <div class="left-section">
          <div class="quick-delay-section">
            <h4 class="section-title">快速延时</h4>
            <div class="quick-buttons">
              <div
                v-for="option in quickDelayOptions"
                :key="option.value"
                class="quick-btn"
                :class="{ active: selectedQuickDelay === option.value }"
                @click="selectQuickDelay(option.value)"
              >
                <el-icon><Plus /></el-icon>
                <span>{{ option.label }}</span>
              </div>
            </div>
          </div>

          <div class="custom-time-section">
            <h4 class="section-title">自定义时间</h4>
            <div class="time-display-container">
              <div class="time-display-left">
                <div class="time-label">新结束时间</div>
                <div class="time-value">{{ newEndTime }}</div>
              </div>
              <div class="time-divider"></div>
              <div class="time-display-right">
                <div class="time-label">延迟时长</div>
                <div class="time-value">{{ durationText }}</div>
              </div>
              <div class="time-picker-btn" @click="showTimePicker">
                <el-icon><ArrowRight /></el-icon>
              </div>
            </div>
          </div>
        </div>

        <div class="right-section">
          <div class="reason-section">
            <h4 class="section-title">延时原因</h4>
            <div class="reason-input-wrapper">
              <el-input
                v-model="form.reason"
                type="textarea"
                :rows="6"
                placeholder="请输入此次更改的简短原因（例如：客户会议超时、优先处理 Q2 报告等）..."
                class="reason-input"
                maxlength="200"
                show-word-limit
              />
            </div>
            <div class="reason-hint">
              <el-icon><InfoFilled /></el-icon>
              <span>此原因将记录在您的每日洞察报告中。</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </BaseDialog>
</template>

<script setup>
import { ref, reactive, watch, computed } from 'vue';
import { Timer, Plus, ArrowRight, Document, InfoFilled } from '@element-plus/icons-vue';
import BaseDialog from '@/components/BaseDialog.vue';

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  currentTimeRange: {
    type: String,
    default: '',
  },
  taskTitle: {
    type: String,
    default: '',
  },
});

const emit = defineEmits(['update:modelValue', 'confirm', 'cancel']);

const visible = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val),
});

const form = reactive({
  startTime: null,
  endTime: null,
  duration: 60,
  reason: '',
});

const selectedQuickDelay = ref(null);
const timePickerRef = ref(null);
const delayHistory = ref([]);

const quickDelayOptions = [
  { label: '15分', value: 15 },
  { label: '30分', value: 30 },
  { label: '1小时', value: 60 },
  { label: '2小时', value: 120 },
];

const originalStartTime = computed(() => {
  if (!props.currentTimeRange) return '';
  const [start] = props.currentTimeRange.split('-');
  return start;
});

const originalEndTime = computed(() => {
  if (!props.currentTimeRange) return '';
  const [, end] = props.currentTimeRange.split('-');
  return end;
});

const newEndTime = computed(() => {
  if (!form.endTime) return '';
  const hours = form.endTime.getHours();
  const minutes = form.endTime.getMinutes();
  return `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}`;
});

const durationText = computed(() => {
  if (!form.startTime || !form.endTime) return '0分';
  const startMinutes = form.startTime.getHours() * 60 + form.startTime.getMinutes();
  const endMinutes = form.endTime.getHours() * 60 + form.endTime.getMinutes();
  const duration = endMinutes - startMinutes;

  if (duration <= 0) return '0分';
  if (duration >= 60) {
    const hours = Math.floor(duration / 60);
    const mins = duration % 60;
    return mins > 0 ? `${hours}小时${mins}分` : `${hours}小时`;
  }
  return `${duration}分`;
});

const delayText = computed(() => {
  if (!selectedQuickDelay.value) return '延后 0分钟';
  const minutes = selectedQuickDelay.value;
  if (minutes >= 60) {
    const hours = Math.floor(minutes / 60);
    return `延后 ${hours}小时`;
  }
  return `延后 ${minutes}分钟`;
});

watch(
  () => props.modelValue,
  (newVal) => {
    if (newVal && props.currentTimeRange) {
      const [start, end] = props.currentTimeRange.split('-');
      if (start) {
        const [startHour, startMin] = start.split(':').map(Number);
        const now = new Date();
        form.startTime = new Date(
          now.getFullYear(),
          now.getMonth(),
          now.getDate(),
          startHour,
          startMin
        );
        form.endTime = new Date(
          now.getFullYear(),
          now.getMonth(),
          now.getDate(),
          startHour,
          startMin
        );
      }
      form.reason = '';
      form.duration = 60;
      selectedQuickDelay.value = null;
    }
  }
);

const selectQuickDelay = (value) => {
  selectedQuickDelay.value = value;
  if (form.startTime) {
    const hours = form.startTime.getHours();
    const minutes = form.startTime.getMinutes();
    const totalMinutes = hours * 60 + minutes + value;
    const endHours = Math.floor(totalMinutes / 60);
    const endMinutes = totalMinutes % 60;
    form.endTime = new Date(
      form.startTime.getFullYear(),
      form.startTime.getMonth(),
      form.startTime.getDate(),
      endHours,
      endMinutes
    );
  }
};

const showTimePicker = () => {
  if (timePickerRef.value) {
    timePickerRef.value.handleOpen();
  }
};

const handleEndTimeChange = (value) => {
  form.endTime = value;
};

const formatTime = (date) => {
  if (!date) return '';
  return `${date.getHours().toString().padStart(2, '0')}:${date.getMinutes().toString().padStart(2, '0')}`;
};

const handleConfirm = () => {
  if (!form.startTime || !form.endTime) {
    return;
  }

  const newTimeRange = `${formatTime(form.startTime)}-${formatTime(form.endTime)}`;
  emit('confirm', {
    timeRange: newTimeRange,
    reason: form.reason,
    duration: durationText.value,
  });
  visible.value = false;
};

const handleCancel = () => {
  visible.value = false;
};

const handleClose = () => {
  form.reason = '';
  selectedQuickDelay.value = null;
};
</script>

<style scoped lang="scss">
.task-info {
  display: flex;
  align-items: center;
  gap: 8px;

  .task-icon {
    font-size: 16px;
    color: rgba(74, 64, 224, 1);
  }

  .task-name {
    font-size: 14px;
    color: rgba(104, 120, 143, 1);
    font-weight: 500;
  }
}

.delay-content {
  display: flex;
  flex-direction: column;
  gap: 10px;
  min-height: 460px;
  padding: 16px 32px;
  box-sizing: border-box;
  background-color: #fff;
  position: relative;

  &::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: url("data:image/svg+xml,%3Csvg viewBox='0 0 200 200' xmlns='http://www.w3.org/2000/svg'%3E%3Cfilter id='noiseFilter'%3E%3CfeTurbulence type='fractalNoise' baseFrequency='0.9' numOctaves='4' stitchTiles='stitch'/%3E%3C/filter%3E%3Crect width='100%25' height='100%25' filter='url(%23noiseFilter)'/%3E%3C/svg%3E");
    opacity: 0.03;
    pointer-events: none;
  }

  .top-section {
    .section-title {
      font-size: 14px;
      font-weight: 600;
      color: rgba(32, 48, 68, 1);
      margin: 0 0 10px 0;
    }

    .timeline-visual {
      padding: 56px 24px 40px 24px;
      background-color: rgba(220, 233, 255, 0.5);
      border-radius: 8px;

      .timeline-track {
        position: relative;
        height: 24px;
        display: flex;
        align-items: center;
        gap: 0;

        .timeline-node {
          display: flex;
          flex-direction: column;
          align-items: center;
          position: relative;
          flex-shrink: 0;

          .node-point {
            width: 24px;
            height: 24px;
            border-radius: 12px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: rgba(74, 64, 224, 0.2);

            .node-inner {
              width: 12px;
              height: 12px;
              border-radius: 12px;
              background-color: rgba(74, 64, 224, 1);
            }
          }

          .node-label {
            position: absolute;
            top: 28px;
            white-space: nowrap;
            font-size: 11px;
            font-weight: 600;
            color: rgba(77, 93, 115, 1);
            line-height: 16.5px;
          }

          &.start-node {
            .node-point {
              .node-inner {
                background-color: rgba(74, 64, 224, 1);
              }
            }
          }

          &.end-node {
            .node-point {
              background-color: rgba(74, 64, 224, 0.2);

              .node-inner {
                background-color: rgba(74, 64, 224, 1);
              }
            }
          }

          &.delay-node {
            .node-point {
              background-color: rgba(151, 149, 255, 0.2);

              .node-inner {
                background-color: rgba(151, 149, 255, 1);
              }
            }
          }

          &.new-end-node {
            .node-point {
              background-color: rgba(151, 149, 255, 0.2);

              .node-inner {
                background-color: rgba(151, 149, 255, 1);
              }
            }

            .node-label {
              color: rgba(74, 64, 224, 1);
            }
          }
        }

        .timeline-line {
          height: 4px;
          flex: 1;
          min-width: 20px;

          &.solid-line {
            background: linear-gradient(
              90deg,
              rgba(74, 64, 224, 1) 0%,
              rgba(151, 149, 255, 1) 100%
            );
            border-radius: 2px;
          }

          &.dashed-line {
            background: repeating-linear-gradient(
              90deg,
              rgba(74, 64, 224, 1) 0px,
              rgba(74, 64, 224, 1) 6px,
              transparent 6px,
              transparent 10px
            );
            position: relative;

            &.active {
              background: repeating-linear-gradient(
                90deg,
                rgba(74, 64, 224, 1) 0px,
                rgba(74, 64, 224, 1) 6px,
                transparent 6px,
                transparent 10px
              );
            }
          }

          &.fade-out-line {
            background: linear-gradient(
              90deg,
              rgba(151, 149, 255, 0.4) 0%,
              rgba(151, 149, 255, 0) 100%
            );
            border-radius: 0 12px 12px 0;
            flex: 0 0 120px;
          }
        }

        .timeline-delay-segment {
          display: flex;
          align-items: center;
          flex: 1;
          min-width: 80px;

          .delay-line-container {
            position: relative;
            flex: 1;
            display: flex;
            align-items: center;

            .delay-badge {
              position: absolute;
              top: -38px;
              left: 50%;
              transform: translateX(-50%);
              padding: 4px 12px;
              background-color: rgba(74, 64, 224, 1);
              border-radius: 6px;
              box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
              white-space: nowrap;

              span {
                font-size: 10px;
                font-weight: 500;
                color: rgba(255, 255, 255, 1);
                line-height: 15px;
              }

              &.active {
                background-color: rgba(74, 64, 224, 1);
              }
            }

            .timeline-line {
              height: 2px;
              flex: 1;
              background: repeating-linear-gradient(
                90deg,
                rgba(74, 64, 224, 1) 0px,
                rgba(74, 64, 224, 1) 6px,
                transparent 6px,
                transparent 10px
              );
            }
          }
        }
      }
    }
  }

  .bottom-section {
    display: flex;
    gap: 32px;
    flex: 1;

    .left-section {
      flex: 1;
      display: flex;
      flex-direction: column;
      gap: 24px;
      position: relative;
      z-index: 1;

      .section-title {
        font-size: 14px;
        font-weight: 600;
        color: rgba(32, 48, 68, 1);
        margin: 0 0 16px 0;
      }

      .quick-delay-section {
        .section-title {
          margin-bottom: 12px;
        }

        .quick-buttons {
          display: grid;
          grid-template-columns: repeat(2, 1fr);
          gap: 12px;

          .quick-btn {
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 8px;
            height: 40px;
            background-color: rgba(234, 241, 255, 1);
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.2s ease;
            border: none;
            padding: 10px 10px;

            &:hover {
              background-color: rgba(74, 64, 224, 0.1);
            }

            &.active {
              height: 42px;
              background-color: rgba(151, 149, 255, 1);
              box-shadow:
                0 1px 2px rgba(0, 0, 0, 0.05),
                0 0 0 2px rgba(74, 64, 224, 0.2);

              .el-icon {
                color: rgba(20, 0, 126, 1);
              }

              span {
                color: rgba(20, 0, 126, 1);
                font-weight: 700;
              }
            }

            .el-icon {
              font-size: 15px;
              color: rgba(77, 93, 115, 1);
            }

            span {
              font-size: 14px;
              font-weight: 600;
              color: rgba(77, 93, 115, 1);
            }
          }
        }
      }

      .custom-time-section {
        .section-title {
          margin-bottom: 12px;
        }

        .time-display-container {
          display: flex;
          align-items: center;
          padding: 16px;
          background-color: rgba(234, 241, 255, 1);
          border-radius: 8px;
          gap: 16px;

          .time-display-left,
          .time-display-right {
            flex: 1;
            display: flex;
            flex-direction: column;
            gap: 4px;

            .time-label {
              font-size: 10px;
              font-weight: 500;
              color: rgba(77, 93, 115, 1);
              line-height: 15px;
              text-transform: uppercase;
            }

            .time-value {
              font-size: 20px;
              font-weight: 400;
              color: rgba(32, 48, 68, 1);
              line-height: 28px;
            }
          }

          .time-divider {
            width: 1px;
            height: 32px;
            background-color: rgba(158, 174, 199, 0.3);
            flex-shrink: 0;
          }

          .time-picker-btn {
            width: 36px;
            height: 36px;
            display: flex;
            align-items: center;
            justify-content: center;
            background-color: rgba(255, 255, 255, 1);
            border-radius: 4px;
            cursor: pointer;
            box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
            transition: all 0.2s ease;
            flex-shrink: 0;

            &:hover {
              transform: scale(1.05);
              box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            }

            .el-icon {
              font-size: 20px;
              color: rgba(74, 64, 224, 1);
            }
          }
        }

        .hidden-time-picker {
          position: absolute;
          opacity: 0;
          pointer-events: none;
          width: 0;
          height: 0;
        }
      }
    }

    .right-section {
      width: 474px;
      display: flex;
      flex-direction: column;
      position: relative;
      z-index: 1;

      .reason-section {
        height: 100%;
        display: flex;
        flex-direction: column;

        .section-title {
          font-size: 14px;
          font-weight: 600;
          color: rgba(32, 48, 68, 1);
          margin: 0 0 16px 0;
        }

        .reason-input-wrapper {
          flex: 1;
          margin-bottom: 8px;

          .reason-input {
            height: 100%;

            :deep(.el-textarea__inner) {
              border-radius: 8px;
              resize: none;
              padding: 16px;
              font-size: 14px;
              line-height: 1.6;
              min-height: 180px !important;
              border: 1px solid rgba(220, 233, 255, 1);
              background-color: rgba(244, 246, 255, 1);

              &:hover {
                border-color: rgba(74, 64, 224, 0.5);
              }

              &:focus {
                border-color: rgba(74, 64, 224, 1);
                background-color: #fff;
              }

              &::placeholder {
                color: rgba(158, 174, 199, 1);
              }
            }

            :deep(.el-input__count) {
              font-size: 12px;
              color: rgba(158, 174, 199, 1);
              background: transparent;
            }
          }
        }

        .reason-hint {
          display: flex;
          align-items: center;
          gap: 8px;
          padding: 12px 16px;
          background-color: rgba(244, 246, 255, 1);
          border-radius: 8px;

          .el-icon {
            font-size: 16px;
            color: rgba(74, 64, 224, 1);
          }

          span {
            font-size: 12px;
            color: rgba(104, 120, 143, 1);
          }
        }
      }
    }
  }
}
</style>
