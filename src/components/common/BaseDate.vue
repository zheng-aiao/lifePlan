<template>
  <div class="plan-time-picker">
    <div class="time-picker-content">
      <template v-if="taskType === 1">
        <el-date-picker
          v-model="selectedValue"
          type="monthrange"
          class="time-picker"
          format="YYYY/MM"
          value-format="YYYY-MM"
          range-separator="-"
          start-placeholder="开始月份"
          end-placeholder="结束月份"
          unlink-panels
        />
      </template>
      <template v-else-if="taskType === 2">
        <el-date-picker
          v-model="selectedValue"
          type="daterange"
          class="time-picker"
          format="YYYY/MM/DD"
          value-format="YYYY-MM-DD"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          unlink-panels
        />
      </template>
      <template v-else-if="taskType === 3">
        <div class="day-time-wrapper">
          <span class="current-date">{{ currentDate }}</span>
          <div class="time-picker">
            <el-time-picker
              v-model="selectedValue"
              is-range
              format="HH:mm"
              value-format="HH:mm"
              range-separator="-"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              :picker-options="timePickerOptions"
              unlink-panels
            />
          </div>
        </div>
      </template>
      <div v-else></div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, computed } from 'vue';

const props = defineProps({
  taskType: {
    type: Number,
    default: 3,
    validator: (value) => [1, 2, 3].includes(value),
  },
  modelValue: {
    type: [Array, null],
    default: null,
  },
});

const emit = defineEmits(['update:modelValue']);

const selectedValue = ref(props.modelValue);

const currentDate = computed(() => {
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const day = now.getDate();
  return `${year}年${month}月${day}日`;
});

// 时间选择器选项，用于限制结束时间必须在开始时间之后
const timePickerOptions = computed(() => {
  return {
    // 开始时间完全不限制
    startPickerOptions: {
      // 明确设置不限制小时、分钟、秒
      disabledHours: () => [],
      disabledMinutes: () => [],
      disabledSeconds: () => [],
    },
    // 结束时间必须大于等于开始时间
    endPickerOptions: {
      // 禁用早于开始时间的小时
      disabledHours: () => {
        if (!selectedValue.value || !selectedValue.value[0]) {
          return [];
        }
        const [startHour] = selectedValue.value[0].split(':').map(Number);
        const disabledHours = [];
        for (let i = 0; i < startHour; i++) {
          disabledHours.push(i);
        }
        return disabledHours;
      },
      // 当小时相同时，禁用早于开始时间的分钟
      disabledMinutes: (selectedHour) => {
        if (!selectedValue.value || !selectedValue.value[0]) {
          return [];
        }
        const [startHour, startMinute] = selectedValue.value[0].split(':').map(Number);
        if (selectedHour !== startHour) {
          return [];
        }
        const disabledMinutes = [];
        for (let i = 0; i < startMinute; i++) {
          disabledMinutes.push(i);
        }
        return disabledMinutes;
      },
      // 当小时和分钟都相同时，禁用早于开始时间的秒
      disabledSeconds: (selectedHour, selectedMinute) => {
        if (!selectedValue.value || !selectedValue.value[0]) {
          return [];
        }
        const [startHour, startMinute, startSecond = 0] = selectedValue.value[0]
          .split(':')
          .map(Number);
        if (selectedHour !== startHour || selectedMinute !== startMinute) {
          return [];
        }
        const disabledSeconds = [];
        for (let i = 0; i < startSecond; i++) {
          disabledSeconds.push(i);
        }
        return disabledSeconds;
      },
    },
  };
});

watch(selectedValue, (newVal) => {
  emit('update:modelValue', newVal);
});

watch(
  () => props.modelValue,
  (newVal) => {
    selectedValue.value = newVal;
  }
);

watch(
  () => props.taskType,
  () => {
    selectedValue.value = null;
    emit('update:modelValue', null);
  }
);
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.plan-time-picker {
  display: flex;
  align-items: center;
  width: 100%;
  padding: pxToRem(12) pxToRem(16);
  background: rgba(234, 241, 255, 1);
  border-radius: pxToRem(8);
  border-left: pxToRem(4) solid rgba(74, 64, 224, 1);
  box-sizing: border-box;
  gap: pxToRem(16);

  .time-picker-content {
    flex: 1;
    min-width: 0;

    .time-picker {
      width: 100%;

      :deep(.el-input__wrapper) {
        background: transparent;
        box-shadow: none;
        padding: 0;
        border: none;
      }

      :deep(.el-input__inner) {
        font-size: pxToRem(14);
        font-weight: 500;
        color: rgba(32, 48, 68, 1);

        &::placeholder {
          color: rgba(139, 154, 181, 1);
        }
      }

      :deep(.el-range-input) {
        font-size: pxToRem(14);
        font-weight: 500;
        color: rgba(32, 48, 68, 1);

        &::placeholder {
          color: rgba(139, 154, 181, 1);
        }
      }

      :deep(.el-range-separator) {
        font-size: pxToRem(14);
        font-weight: 500;
        color: rgba(77, 93, 115, 1);
        padding: 0 pxToRem(4);
      }

      :deep(.el-input__prefix),
      :deep(.el-input__suffix),
      :deep(.el-input__suffix-inner) {
        display: none;
      }

      :deep(.el-range__icon) {
        display: none;
      }

      :deep(.el-input__icon) {
        display: none;
      }
    }
  }
}

.day-time-wrapper {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  gap: pxToRem(16);

  .current-date {
    flex: 1;
    font-size: pxToRem(14);
    font-weight: 500;
    color: rgba(32, 48, 68, 1);
    white-space: nowrap;
  }

  .time-picker {
    flex: 1;
  }
}
</style>
