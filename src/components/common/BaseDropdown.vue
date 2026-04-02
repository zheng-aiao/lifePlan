<template>
  <div class="parent-task-selector">
    <el-select
      :model-value="modelValue"
      :placeholder="placeholder"
      :clearable="clearable"
      class="parent-select"
      @update:model-value="$emit('update:modelValue', $event)"
    >
      <el-option v-for="task in options" :key="task.id" :label="task.title" :value="task.id">
        <div class="parent-option">
          <span class="parent-title">{{ task.title }}</span>
          <el-tag :type="getTaskTagType(task.taskType)" size="small">
            {{ getTaskTypeName(task.taskType) }}
          </el-tag>
        </div>
      </el-option>
    </el-select>
  </div>
</template>

<script setup>
import { ArrowDown } from '@element-plus/icons-vue';

const props = defineProps({
  modelValue: {
    type: [String, Number, null],
    default: null,
  },
  options: {
    type: Array,
    default: () => [],
  },
  placeholder: {
    type: String,
    default: '请选择...',
  },
  clearable: {
    type: Boolean,
    default: true,
  },
});

defineEmits(['update:modelValue']);

const getTaskTagType = (taskType) => {
  const typeMap = {
    1: '',
    2: 'success',
    3: 'warning',
  };
  return typeMap[taskType] || '';
};

const getTaskTypeName = (taskType) => {
  const nameMap = {
    1: '年任务',
    2: '月任务',
    3: '日任务',
  };
  return nameMap[taskType] || '日任务';
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.parent-task-selector {
  height: pxToRem(40);
  background: rgba(234, 241, 255, 0.7);
  border-radius: pxToRem(8);
  box-shadow: none;
  border: none;
  padding: pxToRem(12) pxToRem(16);
  position: relative;
  display: flex;
  align-items: center;

  .parent-select {
    width: 100%;
  }

  .dropdown-icon {
    position: absolute;
    right: pxToRem(16);
    font-size: pxToRem(12);
    color: rgba(77, 93, 115, 1);
    pointer-events: none;
  }
}

.parent-option {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;

  .parent-title {
    font-size: pxToRem(14);
    color: rgba(77, 93, 115, 1);
  }
}
</style>
