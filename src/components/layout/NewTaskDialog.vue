<template>
  <BaseDialog
    v-model="dialogVisible"
    title="新建任务"
    size="large"
    :show-cancel="true"
    :show-confirm="true"
    cancel-text="取消"
    confirm-text="确认创建"
    :confirm-icon="Check"
    :confirm-loading="submitting"
    @confirm="handleSubmit"
    @cancel="handleCancel"
    @close="handleClose"
  >
    <template #headerExtra>
      <div class="task-type-tabs">
        <div
          v-for="type in taskTypes"
          :key="type.value"
          :class="['type-tab', { active: formData.taskType === type.value }]"
          @click="formData.taskType = type.value"
        >
          <span class="type-label">{{ type.label }}</span>
        </div>
      </div>
    </template>

    <div class="form-content">
      <div class="form-item">
        <div class="form-label">
          <span>任务标题</span>
          <span class="char-count">{{ formData.title.length }}/50</span>
        </div>
        <el-input
          v-model="formData.title"
          placeholder="给你的目标起个好名字..."
          maxlength="50"
          class="form-input"
        />
      </div>

      <div class="form-item">
        <div class="form-label">
          <span>描述</span>
          <span class="char-count">{{ formData.description.length }}/200</span>
        </div>
        <el-input
          v-model="formData.description"
          type="textarea"
          placeholder="添加更多细节或步骤..."
          maxlength="200"
          :rows="3"
          class="form-textarea"
        />
      </div>

      <div class="form-row">
        <div class="form-item form-item-half">
          <div class="form-label">
            <span>任务标签</span>
          </div>
          <TagSelect v-model="formData.tags" :task-type="formData.taskType" :max-tags="4" />
        </div>

        <div class="form-item form-item-half">
          <div class="form-label">
            <span>优先级</span>
          </div>
          <el-radio-group v-model="formData.priority" class="priority-radio-group">
            <el-radio v-for="item in priorities" :key="item.value" :value="item.value">
              <span class="priority-label" :style="{ color: item.color }">{{ item.label }}</span>
            </el-radio>
          </el-radio-group>
        </div>
      </div>
      <div class="form-row">
        <div class="form-item form-item-half">
          <div class="form-label">
            <span>开始时间</span>
          </div>
          <BaseDateSelect v-model="formData.taskType" />
        </div>

        <div class="form-item form-item-half">
          <div class="form-label">
            <span>结束时间</span>
          </div>
          <BaseDateSelect v-model="formData.taskType" />
        </div>
      </div>

      <div class="form-item">
        <div class="form-label">
          <span>父任务</span>
          <span class="optional-label">(可选)</span>
        </div>
        <div class="parent-task-selector">
          <el-select
            v-model="formData.parentId"
            placeholder="选择父任务..."
            clearable
            class="parent-select"
          >
            <el-option
              v-for="task in parentTaskList"
              :key="task.id"
              :label="task.title"
              :value="task.id"
            >
              <div class="parent-option">
                <span class="parent-title">{{ task.title }}</span>
                <el-tag :type="getTaskTagType(task.taskType)" size="small">
                  {{ getTaskTypeName(task.taskType) }}
                </el-tag>
              </div>
            </el-option>
          </el-select>
          <el-icon class="dropdown-icon"><ArrowDown /></el-icon>
        </div>
      </div>
    </div>
  </BaseDialog>
</template>

<script setup>
import { ref, reactive, computed, watch } from 'vue';
import { ElMessage } from 'element-plus';
import { Calendar, ArrowDown, Check } from '@element-plus/icons-vue';
import BaseDialog from '@/components/BaseDialog.vue';
import TagSelect from '@/components/common/TagSelect.vue';
import BaseDateSelect from '@/components/common/BaseDateSelect.vue';

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(['update:modelValue', 'success']);

const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value),
});

const submitting = ref(false);

const formData = reactive({
  taskType: 3,
  title: '',
  description: '',
  tags: [],
  priority: 2,
  planTime: {
    yearRange: null,
    monthRange: null,
    dayRange: null,
  },
  parentId: null,
});

const taskTypes = [
  { value: 1, label: '年任务' },
  { value: 2, label: '月任务' },
  { value: 3, label: '日任务' },
];

const priorities = [
  { value: 3, label: '高', color: 'rgba(247, 75, 109, 1)' },
  { value: 2, label: '中', color: 'rgba(248, 160, 16, 1)' },
  { value: 1, label: '低', color: 'rgba(105, 246, 184, 1)' },
];

const todayDate = computed(() => {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, '0');
  const day = String(now.getDate()).padStart(2, '0');
  return `${year}/${month}/${day}`;
});

const parentTaskList = ref([
  { id: 1, title: '年度学习计划', taskType: 1 },
  { id: 2, title: '3月阅读计划', taskType: 2 },
  { id: 3, title: '今天晨跑', taskType: 3 },
]);

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

const handleClose = () => {
  dialogVisible.value = false;
  resetForm();
};

const handleCancel = () => {
  dialogVisible.value = false;
  resetForm();
};

const resetForm = () => {
  formData.taskType = 3;
  formData.title = '';
  formData.description = '';
  formData.tags = [];
  formData.priority = 2;
  formData.planTime = {
    yearRange: null,
    monthRange: null,
    dayRange: null,
  };
  formData.parentId = null;
};

const handleSubmit = async () => {
  if (!formData.title.trim()) {
    ElMessage.warning('请输入任务标题');
    return;
  }

  submitting.value = true;

  const submitData = {
    title: formData.title,
    taskType: formData.taskType,
    description: formData.description,
    tags: formData.tags.map((tag) => tag.name),
    taskPriority: formData.priority,
    parentId: formData.parentId,
  };

  if (formData.taskType === 1 && formData.planTime.yearRange) {
    submitData.plannedStartTime = `${formData.planTime.yearRange[0]}-01T00:00:00`;
    submitData.plannedEndTime = `${formData.planTime.yearRange[1].split('-')[0]}-12-31T23:59:59`;
  } else if (formData.taskType === 2 && formData.planTime.monthRange) {
    submitData.plannedStartTime = `${formData.planTime.monthRange[0]}T00:00:00`;
    submitData.plannedEndTime = `${formData.planTime.monthRange[1]}T23:59:59`;
  } else if (formData.taskType === 3 && formData.planTime.dayRange) {
    submitData.plannedStartTime = formData.planTime.dayRange[0];
    submitData.plannedEndTime = formData.planTime.dayRange[1];
  }

  console.log('提交数据:', submitData);

  setTimeout(() => {
    ElMessage.success('任务创建成功');
    emit('success', submitData);
    dialogVisible.value = false;
    resetForm();
    submitting.value = false;
  }, 1000);
};

watch(
  () => formData.taskType,
  () => {
    formData.planTime = {
      yearRange: null,
      monthRange: null,
      dayRange: null,
    };
  }
);
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;
.task-type-tabs {
  display: flex;
  gap: pxToRem(8);

  .type-tab {
    padding: pxToRem(8) pxToRem(16);
    font-size: pxToRem(14);
    font-weight: 500;
    color: rgba(77, 93, 115, 1);
    cursor: pointer;
    border-radius: pxToRem(8);
    background: rgba(234, 241, 255, 1);
    transition: all 0.3s ease;

    &:hover {
      color: rgba(74, 64, 224, 1);
      background: rgba(220, 233, 255, 1);
    }

    &.active {
      color: rgba(244, 241, 255, 1);
      background: rgba(74, 64, 224, 1);
    }
  }
}

.form-content {
  display: flex;
  flex-direction: column;
  gap: pxToRem(24);
  padding: pxToRem(24);
}

.form-row {
  display: flex;
  gap: pxToRem(24);
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: pxToRem(12);
}

.form-item-half {
  flex: 1;
  min-width: 0;
}

.form-label {
  display: flex;
  align-items: center;
  gap: pxToRem(8);
  font-size: pxToRem(14);
  font-weight: 700;
  color: rgba(77, 93, 115, 1);

  .char-count {
    font-size: pxToRem(12);
    font-weight: 400;
    color: rgba(139, 154, 181, 1);
  }

  .optional-label {
    font-size: pxToRem(12);
    font-weight: 400;
    color: rgba(139, 154, 181, 1);
  }
}

.form-input {
  :deep(.el-input__wrapper) {
    background: rgba(234, 241, 255, 1);
    border-radius: pxToRem(8);
    box-shadow: none;
    border: none;
    padding: pxToRem(12) pxToRem(16);

    &.is-focus {
      box-shadow: 0 0 0 1px rgba(74, 64, 224, 1);
    }
  }

  :deep(.el-input__inner) {
    font-size: pxToRem(14);
    color: rgba(77, 93, 115, 1);

    &::placeholder {
      color: rgba(139, 154, 181, 1);
    }
  }
}

.form-textarea {
  :deep(.el-textarea__inner) {
    background: rgba(234, 241, 255, 1);
    border-radius: pxToRem(8);
    box-shadow: none;
    border: none;
    padding: pxToRem(12) pxToRem(16);
    min-height: pxToRem(88) !important;
    resize: none;

    &:focus {
      box-shadow: 0 0 0 1px rgba(74, 64, 224, 1);
    }

    &::placeholder {
      color: rgba(139, 154, 181, 1);
    }
  }
}

.priority-radio-group {
  display: flex;
  gap: pxToRem(16);
  width: 100%;

  .priority-label {
    font-size: pxToRem(14);
    font-weight: 500;
  }
}

.plan-time-picker {
  display: flex;
  align-items: center;
  background: rgba(234, 241, 255, 1);
  border-radius: pxToRem(8);
  border-left: 4px solid rgba(74, 64, 224, 1);
  overflow: hidden;

  .time-picker-left {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: pxToRem(12) pxToRem(16);
    background: rgba(74, 64, 224, 0.08);

    .calendar-icon {
      font-size: pxToRem(18);
      color: rgba(74, 64, 224, 1);
    }
  }

  .time-picker-content {
    flex: 1;
    display: flex;
    align-items: center;
    padding: pxToRem(8) pxToRem(16);
    min-height: pxToRem(44);

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
        padding: 0 pxToRem(8);
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

.day-time-picker {
  display: flex;
  align-items: center;
  width: 100%;
  gap: pxToRem(12);

  .today-date {
    flex: 1;
    font-size: pxToRem(14);
    font-weight: 500;
    color: rgba(32, 48, 68, 1);
    white-space: nowrap;
  }

  .time-picker {
    flex: 1;

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

.parent-task-selector {
  position: relative;
  display: flex;
  align-items: center;

  .parent-select {
    width: 100%;

    :deep(.el-input__wrapper) {
      background: rgba(234, 241, 255, 0.7);
      border-radius: pxToRem(8);
      box-shadow: none;
      border: none;
      padding: pxToRem(12) pxToRem(16);

      &.is-focus {
        box-shadow: 0 0 0 1px rgba(74, 64, 224, 1);
      }
    }

    :deep(.el-input__inner) {
      font-size: pxToRem(14);
      color: rgba(77, 93, 115, 1);

      &::placeholder {
        color: rgba(139, 154, 181, 1);
      }
    }

    :deep(.el-input__suffix) {
      display: none;
    }
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
