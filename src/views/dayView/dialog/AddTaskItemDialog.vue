<template>
  <el-dialog
    v-model="dialogVisible"
    :show-close="false"
    width="600px"
    class="add-task-item-dialog"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
  >
    <div class="dialog-content">
      <div class="dialog-header">
        <div class="header-left">
          <p class="dialog-title">新增子项任务</p>
        </div>
        <div class="header-right" @click="handleCancel">
          <el-icon class="close-icon"><Close /></el-icon>
        </div>
      </div>

      <div class="dialog-body">
        <div class="task-path">
          <span class="parent-task">{{ taskTitle }}</span>
          <el-icon class="separator-icon"><ArrowRight /></el-icon>
          <span class="current-task">新增子项任务</span>
        </div>

        <div class="form-section">
          <div class="form-label">子任务名称</div>
          <el-input
            v-model="subTaskName"
            placeholder="例如：设计首页高保真原型"
            class="task-input"
          />
        </div>

        <div class="form-section">
          <div class="form-label">优先级</div>
          <div class="priority-options">
            <div
              v-for="option in priorityOptions"
              :key="option.value"
              class="priority-item"
              :class="{ active: selectedPriority === option.value }"
              @click="selectedPriority = option.value"
            >
              <div class="priority-dot" :style="{ backgroundColor: option.color }"></div>
              <span class="priority-text" :style="{ color: option.textColor }">{{ option.label }}</span>
            </div>
          </div>
        </div>
      </div>

      <div class="dialog-footer">
        <el-button class="cancel-btn" @click="handleCancel">取消</el-button>
        <el-button class="confirm-btn" type="primary" @click="handleConfirm">立即添加</el-button>
      </div>
    </div>
  </el-dialog>
</template>

<script setup>
import { ref, computed, watch } from 'vue';
import { Close, ArrowRight } from '@element-plus/icons-vue';

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  taskTitle: {
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
  }
});

const handleCancel = () => {
  dialogVisible.value = false;
  emit('cancel');
};

const handleConfirm = () => {
  if (!subTaskName.value.trim()) {
    return;
  }
  emit('confirm', {
    name: subTaskName.value,
    priority: selectedPriority.value,
  });
  dialogVisible.value = false;
};
</script>

<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';

.add-task-item-dialog {
  :deep(.el-dialog) {
    border-radius: pxToRem(8);
    box-shadow: 0 pxToRem(12) pxToRem(48) rgba(0, 15, 33, 0.12);
    border: pxToRem(1) solid rgba(158, 174, 199, 0.2);
  }

  :deep(.el-dialog__header) {
    display: none;
  }

  :deep(.el-dialog__body) {
    padding: 0;
  }
}

.dialog-content {
  display: flex;
  flex-direction: column;
}

.dialog-header {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  padding: pxToRem(16) pxToRem(32);

  .header-left {
    display: flex;
    flex-direction: column;
  }

  .dialog-title {
    font-size: pxToRem(24);
    font-family: 'Alibaba PuHuiTi-Regular';
    font-weight: 800;
    letter-spacing: pxToRem(-0.6);
    line-height: pxToRem(32);
    color: rgba(32, 48, 68, 1);
    margin: 0;
  }

  .header-right {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    cursor: pointer;

    .close-icon {
      font-size: pxToRem(14);
      color: rgba(158, 174, 199, 1);
      transition: color 0.2s;

      &:hover {
        color: rgba(74, 64, 224, 1);
      }
    }
  }
}

.dialog-body {
  display: flex;
  flex-direction: column;
  gap: pxToRem(20);
  padding: pxToRem(15) pxToRem(32) pxToRem(16);
}

.task-path {
  display: flex;
  flex-direction: row;
  gap: pxToRem(4);
  align-items: center;

  .parent-task {
    font-size: pxToRem(11);
    font-family: 'Inter-Medium';
    font-weight: 700;
    letter-spacing: pxToRem(0.55);
    line-height: pxToRem(16.5);
    text-transform: uppercase;
    color: rgba(104, 120, 143, 1);
  }

  .separator-icon {
    font-size: pxToRem(7);
    color: rgba(158, 174, 199, 1);
  }

  .current-task {
    font-size: pxToRem(11);
    font-family: 'Inter-Medium';
    font-weight: 700;
    letter-spacing: pxToRem(0.55);
    line-height: pxToRem(16.5);
    text-transform: uppercase;
    color: rgba(77, 93, 115, 1);
  }
}

.form-section {
  display: flex;
  flex-direction: column;
  gap: pxToRem(6);
}

.form-label {
  font-size: pxToRem(13);
  font-family: 'Inter-Medium';
  font-weight: 700;
  letter-spacing: pxToRem(0.65);
  line-height: pxToRem(19.5);
  text-transform: uppercase;
  color: rgba(77, 93, 115, 1);
}

.task-input {
  :deep(.el-input__wrapper) {
    background-color: rgba(234, 241, 255, 1);
    border-radius: pxToRem(8);
    border: none;
    box-shadow: none;
    padding: pxToRem(16);
  }

  :deep(.el-input__inner) {
    font-size: pxToRem(15);
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
  gap: pxToRem(12);
}

.priority-item {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: pxToRem(7);
  justify-content: center;
  align-items: center;
  padding: pxToRem(16) 0;
  border-radius: pxToRem(8);
  cursor: pointer;
  transition: all 0.2s;
  border: pxToRem(1) solid transparent;

  &.active {
    border-color: rgba(74, 64, 224, 0.2);
    background-color: rgba(255, 255, 255, 0.002);
  }

  &:hover:not(.active) {
    opacity: 0.8;
  }

  .priority-dot {
    width: pxToRem(10);
    height: pxToRem(10);
    border-radius: 50%;
  }

  .priority-text {
    font-size: pxToRem(13);
    font-family: 'Inter-Medium';
    font-weight: 700;
    line-height: pxToRem(19.5);
    text-align: center;
  }
}

.priority-item[data-priority="urgent"] {
  background-color: rgba(247, 75, 109, 0.1);

  &.active {
    border-color: rgba(247, 75, 109, 0.3);
  }
}

.priority-item[data-priority="important"] {
  background-color: rgba(151, 149, 255, 0.1);

  &.active {
    border-color: rgba(151, 149, 255, 1);
  }
}

.priority-item[data-priority="normal"] {
  background-color: rgba(248, 160, 16, 0.1);

  &.active {
    border-color: rgba(248, 160, 16, 0.3);
  }
}

.priority-item[data-priority="low"] {
  background-color: rgba(105, 246, 184, 0.1);

  &.active {
    border-color: rgba(105, 246, 184, 0.3);
  }
}

.dialog-footer {
  display: flex;
  flex-direction: row;
  gap: pxToRem(12);
  padding: pxToRem(16) pxToRem(32);
  justify-content: flex-end;

  .cancel-btn {
    padding: pxToRem(12) pxToRem(24);
    border-radius: pxToRem(8);
    border: pxToRem(1) solid rgba(158, 174, 199, 0.3);
    background-color: transparent;
    font-size: pxToRem(15);
    font-family: 'Alibaba PuHuiTi-Regular';
    font-weight: 500;
    color: rgba(77, 93, 115, 1);

    &:hover {
      background-color: rgba(244, 246, 255, 1);
      border-color: rgba(158, 174, 199, 0.5);
    }
  }

  .confirm-btn {
    padding: pxToRem(12) pxToRem(24);
    border-radius: pxToRem(8);
    background-color: rgba(74, 64, 224, 1);
    border: none;
    font-size: pxToRem(15);
    font-family: 'Alibaba PuHuiTi-Regular';
    font-weight: 500;
    color: rgba(255, 255, 255, 1);

    &:hover {
      background-color: rgba(94, 84, 244, 1);
    }
  }
}
</style>
