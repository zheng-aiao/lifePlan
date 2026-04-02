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
