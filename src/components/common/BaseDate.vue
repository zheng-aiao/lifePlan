<template>
  <div class="plan-time-picker">
    <div class="time-picker-content">
      <template v-if="taskType === 'year'">
        <el-date-picker
          v-model="planTime.year"
          type="month"
          class="time-picker"
          format="YYYY-MM"
          value-format="YYYY-MM"
          placeholder="选择年月"
        />
      </template>
      <template v-else-if="taskType === 'month'">
        <el-date-picker
          v-model="planTime.month"
          type="date"
          class="time-picker"
          format="YYYY/MM/DD"
          value-format="YYYY-MM-DD"
          placeholder="选择日期"
        />
      </template>
      <template v-else>
        <el-time-picker
          v-model="planTime.day"
          class="time-picker"
          format="HH:mm"
          value-format="HH:mm:ss"
          placeholder="选择时间"
        />
      </template>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue';

const props = defineProps({
  taskType: {
    type: String,
    default: 'day',
    validator: (value) => ['year', 'month', 'day'].includes(value),
  },
  modelValue: {
    type: Object,
    default: () => ({
      year: '',
      month: '',
      day: '',
    }),
  },
});

const emit = defineEmits(['update:modelValue']);

const planTime = ref({
  year: props.modelValue.year || '',
  month: props.modelValue.month || '',
  day: props.modelValue.day || '',
});

const todayDate = computed(() => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  return `${year}/${month}/${day}`;
});

watch(
  planTime,
  (newVal) => {
    emit('update:modelValue', newVal);
  },
  { deep: true }
);

watch(
  () => props.modelValue,
  (newVal) => {
    planTime.value = {
      year: newVal.year || '',
      month: newVal.month || '',
      day: newVal.day || '',
    };
  },
  { deep: true }
);
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.plan-time-picker {
  display: flex;
  align-items: center;
  width: 100%;
  padding: pxToRem(16px);
  background: #eaf1ff;
  border-radius: pxToRem(8px);
  border-left: pxToRem(4px) solid #4a40e0;
  box-sizing: border-box;
  gap: pxToRem(16px);
  .time-picker-content {
    flex: 1;
    min-width: 0;
    .time-picker {
      width: 100%;
    }
  }
}
</style>
