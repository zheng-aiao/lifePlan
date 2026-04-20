<template>
  <Transition name="fade">
    <div v-if="visible" class="time-picker-popup" @click="handleClose">
      <div class="popup-content" @click.stop>
        <div class="popup-header">
          <h3 class="popup-title">选择预约时段</h3>
          <button class="popup-close" @click="handleClose">✕</button>
        </div>
        <div class="popup-body">
          <div class="date-info">
            {{ currentDate.getFullYear() }}年{{
              (currentDate.getMonth() + 1).toString().padStart(2, '0')
            }}月{{ currentDate.getDate().toString().padStart(2, '0') }}日 -
            {{ ['周日', '周一', '周二', '周三', '周四', '周五', '周六'][currentDate.getDay()] }}
          </div>

          <div class="time-picker-container">
            <div class="time-picker-column">
              <h4 class="time-column-title">起始时间</h4>
              <div class="time-options">
                <div
                  v-for="time in filteredTimeOptions"
                  :key="'start-' + time"
                  class="time-option"
                  :class="{
                    selected: isTimeSelected('start', time),
                    disabled: !isTimeSelectable('start', time),
                  }"
                  @click="selectTime('start', time)"
                >
                  {{ time }}
                  <span v-if="isTimeSelected('start', time)" class="selected-icon">✓</span>
                </div>
              </div>
            </div>

            <div class="time-picker-column">
              <h4 class="time-column-title">结束时间</h4>
              <div class="time-options">
                <div
                  v-for="time in filteredTimeOptions"
                  :key="'end-' + time"
                  class="time-option"
                  :class="{
                    selected: isTimeSelected('end', time),
                    disabled: !isTimeSelectable('end', time),
                  }"
                  @click="selectTime('end', time)"
                >
                  {{ time }}
                  <span v-if="isTimeSelected('end', time)" class="selected-icon">✓</span>
                </div>
              </div>
            </div>
          </div>

          <div class="time-duration" v-if="selectedStartTime && selectedEndTime">
            <span>时长：{{ calculateDuration() }}</span>
          </div>
        </div>

        <div class="popup-footer">
          <button class="footer-btn cancel-btn" @click="handleClose">取消</button>
          <button
            class="footer-btn confirm-btn"
            @click="handleConfirm"
            :disabled="!selectedStartTime || !selectedEndTime"
          >
            确定预约
          </button>
        </div>
      </div>
    </div>
  </Transition>
</template>

<script setup>
import { ref, computed } from 'vue';

// 定义组件属性
const props = defineProps({
  visible: {
    type: Boolean,
    default: false,
  },
  timeFollow: {
    type: Boolean,
    default: false, // 时间跟随标识, true只可选择当前时间之后的时间，默认为false
  },
  task: {
    type: Object,
    required: false,
    default: () => ({}), // 传入原始任务对象，用于复制任务属性
  },
});

// 定义组件事件
const emit = defineEmits(['close', 'confirm']);

// 组件状态
const selectedStartTime = ref('');
const selectedEndTime = ref('');
const currentDate = ref(new Date());

// 生成时间选项列表（从7:00到23:30，间隔30分钟）
const generateTimeOptions = () => {
  const options = [];
  for (let hour = 7; hour < 24; hour++) {
    // 添加整点
    options.push(`${hour.toString().padStart(2, '0')}:00`);
    // 添加半点
    if (hour < 23) {
      options.push(`${hour.toString().padStart(2, '0')}:30`);
    }
  }
  return options;
};

const timeOptions = ref(generateTimeOptions());

// 根据timeFollow属性过滤时间选项
const filteredTimeOptions = computed(() => {
  if (!props.timeFollow) return timeOptions.value;

  // 获取当前时间
  const now = new Date();
  const currentHour = now.getHours();
  const currentMinute = now.getMinutes();
  const currentTimeStr = `${currentHour.toString().padStart(2, '0')}:${currentMinute.toString().padStart(2, '0')}`;

  // 只返回当前时间之后的选项
  return timeOptions.value.filter((time) => time > currentTimeStr);
});

// 处理时间选择
const selectTime = (type, time) => {
  if (type === 'start') {
    selectedStartTime.value = time;
    // 如果结束时间早于或等于新的开始时间，清空结束时间
    if (selectedEndTime.value && selectedEndTime.value <= time) {
      selectedEndTime.value = '';
    }
  } else {
    selectedEndTime.value = time;
    // 如果开始时间晚于或等于新的结束时间，清空开始时间
    if (selectedStartTime.value && selectedStartTime.value >= time) {
      selectedStartTime.value = '';
    }
  }
};

// 检查时间是否可选择
const isTimeSelectable = (type, time) => {
  if (type === 'start') {
    return !selectedEndTime.value || time < selectedEndTime.value;
  } else {
    return !selectedStartTime.value || time > selectedStartTime.value;
  }
};

// 检查时间是否被选中
const isTimeSelected = (type, time) => {
  if (type === 'start') {
    return selectedStartTime.value === time;
  } else {
    return selectedEndTime.value === time;
  }
};

// 计算时间段时长
const calculateDuration = () => {
  if (!selectedStartTime.value || !selectedEndTime.value) return '';

  const [startHour, startMinute] = selectedStartTime.value.split(':').map(Number);
  const [endHour, endMinute] = selectedEndTime.value.split(':').map(Number);

  const startTotalMinutes = startHour * 60 + startMinute;
  const endTotalMinutes = endHour * 60 + endMinute;

  if (endTotalMinutes <= startTotalMinutes) return '';

  const durationMinutes = endTotalMinutes - startTotalMinutes;
  const hours = Math.floor(durationMinutes / 60);
  const minutes = durationMinutes % 60;

  if (hours === 0) return `${minutes}分钟`;
  if (minutes === 0) return `${hours}小时`;
  return `${hours}小时${minutes}分钟`;
};

// 处理关闭弹窗
const handleClose = () => {
  // 重置选择的时间
  selectedStartTime.value = '';
  selectedEndTime.value = '';
  // 触发关闭事件
  emit('close');
};

// 处理确认选择
const handleConfirm = () => {
  if (selectedStartTime.value && selectedEndTime.value) {
    // 传递开始和结束时间给父组件
    emit('confirm', [selectedStartTime.value, selectedEndTime.value]);

    // 关闭弹窗
    emit('close');
  }
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

/* 时间段选择弹窗样式 */
.time-picker-popup {
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
    min-width: pxToRem(600);
    max-width: pxToRem(700);
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

      .date-info {
        font-size: pxToRem(14);
        color: rgba(104, 120, 143, 1);
        margin-bottom: pxToRem(16);
        text-align: center;
      }

      .time-picker-container {
        @include flexCenter(space-between, center);
        gap: pxToRem(24);
        margin-bottom: pxToRem(16);

        .time-picker-column {
          flex: 1;

          .time-column-title {
            font-size: pxToRem(14);
            font-weight: 500;
            color: rgba(32, 48, 68, 1);
            margin: 0 0 pxToRem(8) 0;
            text-align: center;
          }

          .time-options {
            border: pxToRem(1) solid rgba(220, 233, 255, 1);
            border-radius: pxToRem(8);
            overflow: hidden;
            max-height: pxToRem(200);
            overflow-y: auto;
            @include scrollBarStyle(var(--violet));

            .time-option {
              @include wh(100%, pxToRem(40));
              @include flexCenter(space-between, center);
              padding: 0 pxToRem(16);
              font-size: pxToRem(14);
              color: rgba(32, 48, 68, 1);
              cursor: pointer;
              transition: all 0.2s ease;
              border-bottom: pxToRem(1) solid rgba(220, 233, 255, 1);

              &:last-child {
                border-bottom: none;
              }

              &:hover {
                background-color: rgba(240, 242, 255, 1);
              }

              &.selected {
                background-color: rgba(74, 64, 224, 1);
                color: #fff;
                font-weight: 500;

                &:hover {
                  background-color: rgba(54, 44, 204, 1);
                }
              }

              &.disabled {
                color: rgba(187, 199, 214, 1);
                cursor: not-allowed;

                &:hover {
                  background-color: transparent;
                }
              }

              .selected-icon {
                font-size: pxToRem(12);
                font-weight: bold;
              }
            }
          }
        }
      }

      .time-duration {
        text-align: center;
        font-size: pxToRem(13);
        color: rgba(104, 120, 143, 1);
        margin-top: pxToRem(8);
      }
    }

    .popup-footer {
      @include flexCenter(flex-end, center);
      gap: pxToRem(12);
      padding: pxToRem(16) pxToRem(20);
      border-top: pxToRem(1) solid rgba(0, 0, 0, 0.06);

      .footer-btn {
        @include whrem(80, 36);
        @include flexCenter;
        border: pxToRem(1) solid;
        border-radius: pxToRem(6);
        cursor: pointer;
        font-size: pxToRem(14);
        font-weight: 500;
        transition: all 0.2s ease;

        &.cancel-btn {
          border-color: rgba(220, 233, 255, 1);
          background: #fff;
          color: rgba(104, 120, 143, 1);

          &:hover {
            border-color: rgba(151, 149, 255, 1);
            color: rgba(74, 64, 224, 1);
          }
        }

        &.confirm-btn {
          border-color: rgba(74, 64, 224, 1);
          background: rgba(74, 64, 224, 1);
          color: #fff;

          &:hover {
            background: rgba(54, 44, 204, 1);
            border-color: rgba(54, 44, 204, 1);
          }

          &:disabled {
            border-color: rgba(220, 233, 255, 1);
            background: rgba(220, 233, 255, 1);
            color: rgba(187, 199, 214, 1);
            cursor: not-allowed;
          }
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
