<template>
  <BaseDialog
    v-model="dialogVisible"
    size="normal"
    title="新增子项任务"
    @confirm="handleConfirm"
    @cancel="handleCancel"
    @close="handleCancel"
  >
    <div class="dialog-body">
      <div class="task-path">
        <span class="parent-task">{{ taskTitle }}</span>
        <el-icon class="separator-icon"><ArrowRight /></el-icon>
        <span class="current-task">新增子项任务</span>
      </div>

      <div class="form-section">
        <div class="form-label">子任务名称</div>
        <el-input v-model="subTaskName" placeholder="例如：设计首页高保真原型" class="task-input" />
      </div>

      <div class="form-section">
        <div class="form-label">优先级</div>
        <div class="priority-options">
          <div
            v-for="option in priorityOptions"
            :key="option.value"
            class="priority-item"
            :class="{ active: selectedPriority === option.value }"
            :style="{ backgroundColor: option.bgColor }"
            @click="selectedPriority = option.value"
          >
            <div class="priority-dot" :style="{ backgroundColor: option.color }"></div>
            <span class="priority-text" :style="{ color: option.textColor }">{{
              option.label
            }}</span>
          </div>
        </div>
      </div>
    </div>
  </BaseDialog>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { ArrowRight } from '@element-plus/icons-vue';
import { ElMessage } from 'element-plus';
import BaseDialog from '@/components/common/BaseDialog.vue';
import bizService from '@/utils/bizService';

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  taskTitle: {
    type: String,
    default: '',
  },
  subTaskGroup: {
    type: String,
    default: '',
  },
});

const emit = defineEmits(['update:modelValue', 'confirm', 'cancel']);

const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value),
});

const subTaskName = ref('');
const selectedPriority = ref('important');
const isLoading = ref(false);
const priorityOptions = [
  {
    value: 'urgent',
    label: '紧急',
    color: 'rgba(180, 19, 64, 1)',
    textColor: 'rgba(180, 19, 64, 1)',
    bgColor: 'rgba(247, 75, 109, 0.1)',
    borderColor: 'rgba(247, 75, 109, 0.3)',
  },
  {
    value: 'important',
    label: '重要',
    color: 'rgba(74, 64, 224, 1)',
    textColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(151, 149, 255, 0.1)',
    borderColor: 'rgba(151, 149, 255, 1)',
  },
  {
    value: 'normal',
    label: '一般',
    color: 'rgba(248, 160, 16, 1)',
    textColor: 'rgba(248, 160, 16, 1)',
    bgColor: 'rgba(248, 160, 16, 0.1)',
    borderColor: 'rgba(248, 160, 16, 0.3)',
  },
  {
    value: 'low',
    label: '低',
    color: 'rgba(105, 246, 184, 1)',
    textColor: 'rgba(105, 246, 184, 1)',
    bgColor: 'rgba(105, 246, 184, 0.1)',
    borderColor: 'rgba(105, 246, 184, 0.3)',
  },
];

watch(dialogVisible, (newVal) => {
  if (newVal) {
    subTaskName.value = '';
    selectedPriority.value = 'important';
    isLoading.value = false;
  }
});

const handleCancel = () => {
  emit('cancel');
};

const handleConfirm = async () => {
  if (!subTaskName.value.trim()) {
    return;
  }

  isLoading.value = true;
  try {
    // 优先级映射：将前端字符串转换为后端整数值
    const priorityMap = {
      urgent: 4, // 紧急
      important: 3, // 重要
      normal: 2, // 一般
      low: 1, // 低
    };

    const subTaskData = {
      subTaskGroup: props.subTaskGroup,
      title: subTaskName.value,
      priority: priorityMap[selectedPriority.value] || 2, // 默认一般优先级
      sortOrder: 0,
    };

    await bizService.subTask.createSubTask(subTaskData);
    ElMessage.success('子任务添加成功');
    emit('confirm');
  } catch (error) {
    console.error('添加子任务失败:', error);
    ElMessage.error('添加子任务失败，请稍后重试');
  } finally {
    isLoading.value = false;
  }
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;
.dialog-body {
  display: flex;
  flex-direction: column;
  gap: pxToRem(16);
  padding: pxToRem(12) pxToRem(20);
  height: 100%;
  box-sizing: border-box;
}

.task-path {
  display: flex;
  flex-direction: row;
  gap: pxToRem(4);
  align-items: center;

  .parent-task {
    font-size: pxToRem(10);
    font-family: 'Inter-Medium';
    font-weight: 700;
    letter-spacing: pxToRem(0.5);
    line-height: pxToRem(14);
    text-transform: uppercase;
    color: rgba(104, 120, 143, 1);
  }

  .separator-icon {
    font-size: pxToRem(6);
    color: rgba(158, 174, 199, 1);
  }

  .current-task {
    font-size: pxToRem(10);
    font-family: 'Inter-Medium';
    font-weight: 700;
    letter-spacing: pxToRem(0.5);
    line-height: pxToRem(14);
    text-transform: uppercase;
    color: rgba(77, 93, 115, 1);
  }
}

.form-section {
  display: flex;
  flex-direction: column;
  gap: pxToRem(4);
}

.form-label {
  font-size: pxToRem(11);
  font-family: 'Inter-Medium';
  font-weight: 700;
  letter-spacing: pxToRem(0.5);
  line-height: pxToRem(16);
  text-transform: uppercase;
  color: rgba(77, 93, 115, 1);
}

.task-input {
  :deep(.el-input__wrapper) {
    background-color: rgba(234, 241, 255, 1);
    border-radius: pxToRem(6);
    border: none;
    box-shadow: none;
    padding: pxToRem(8) pxToRem(12);
  }

  :deep(.el-input__inner) {
    font-size: pxToRem(13);
    font-family: 'Alibaba PuHuiTi-Regular';
    font-weight: 500;
    color: rgba(32, 48, 68, 1);

    &::placeholder {
      color: rgba(158, 174, 199, 1);
    }
  }
}

.priority-options {
  display: flex;
  flex-direction: row;
  gap: pxToRem(8);
}

.priority-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: pxToRem(4);
  justify-content: center;
  align-items: center;
  padding: pxToRem(8) 0;
  border-radius: pxToRem(6);
  cursor: pointer;
  transition: all 0.2s;
  border: pxToRem(1) solid transparent;

  &.active {
    border-color: rgba(74, 64, 224, 0.3);
  }

  &:hover:not(.active) {
    opacity: 0.8;
  }

  .priority-dot {
    width: pxToRem(8);
    height: pxToRem(8);
    border-radius: 50%;
  }

  .priority-text {
    font-size: pxToRem(11);
    font-family: 'Inter-Medium';
    font-weight: 700;
    line-height: pxToRem(16);
    text-align: center;
  }
}
</style>
