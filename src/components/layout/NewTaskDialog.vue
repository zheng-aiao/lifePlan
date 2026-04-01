<template>
  <BaseDialog
    v-model="dialogVisible"
    title="新建任务"
    width="900px"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    class="new-task-dialog"
  >
    <div class="form-content">
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-position="top"
        class="task-form"
      >
        <div class="form-container">
          <div class="form-row task-type-row">
            <el-form-item label="任务类型" prop="taskType" class="form-item">
              <div class="task-type-selector">
                <div
                  v-for="type in taskTypes"
                  :key="type.value"
                  :class="['type-option', { active: formData.taskType === type.value }]"
                  @click="formData.taskType = type.value"
                >
                  <span class="type-icon">{{ type.icon }}</span>
                  <span class="type-name">{{ type.label }}</span>
                </div>
              </div>
            </el-form-item>
          </div>

          <div class="form-row">
            <el-form-item label="任务标题" prop="title" class="form-item">
              <el-input
                v-model="formData.title"
                placeholder="请输入任务标题"
                maxlength="50"
                show-word-limit
                class="form-input"
              />
            </el-form-item>
          </div>

          <div class="form-row">
            <el-form-item label="任务描述" prop="description" class="form-item">
              <el-input
                v-model="formData.description"
                type="textarea"
                placeholder="请输入任务描述（选填）"
                maxlength="200"
                show-word-limit
                :rows="3"
                class="form-textarea"
              />
            </el-form-item>
          </div>

          <div class="form-row">
            <el-form-item label="标签" prop="tags" class="form-item">
              <div class="tags-wrapper">
                <div class="tags-container">
                  <el-tag
                    v-for="(tag, index) in formData.tags"
                    :key="index"
                    closable
                    @close="removeTag(index)"
                    class="tag-item"
                  >
                    {{ tag }}
                  </el-tag>
                  <el-input
                    v-if="showTagInput"
                    ref="tagInputRef"
                    v-model="inputTag"
                    size="small"
                    class="tag-input"
                    @keyup.enter="addTag"
                    @blur="addTag"
                  />
                  <el-button
                    v-else-if="formData.tags.length < 5"
                    class="add-tag-btn"
                    @click="showTagInput = true"
                  >
                    + 添加标签
                  </el-button>
                </div>
                <span class="tags-hint">最多添加5个标签</span>
              </div>
            </el-form-item>
          </div>

          <div class="form-row priority-row">
            <el-form-item label="优先级" prop="priority" class="form-item">
              <div class="priority-selector">
                <div
                  v-for="item in priorities"
                  :key="item.value"
                  :class="[
                    'priority-option',
                    item.value,
                    { active: formData.priority === item.value },
                  ]"
                  @click="formData.priority = item.value"
                >
                  <span class="priority-dot"></span>
                  <span class="priority-label">{{ item.label }}</span>
                </div>
              </div>
            </el-form-item>
          </div>

          <div class="form-row">
            <el-form-item label="计划时间" prop="planTime" class="form-item plan-time-item">
              <div class="plan-time-card">
                <div class="plan-time-content">
                  <template v-if="formData.taskType === 'year'">
                    <el-date-picker
                      v-model="formData.planTime.year"
                      type="year"
                      placeholder="选择年份"
                      :disabled-date="yearDisabledDate"
                      class="plan-time-picker"
                    />
                  </template>
                  <template v-else-if="formData.taskType === 'month'">
                    <el-date-picker
                      v-model="formData.planTime.monthRange"
                      type="monthrange"
                      range-separator="至"
                      start-placeholder="开始月份"
                      end-placeholder="结束月份"
                      :disabled-date="monthDisabledDate"
                      class="plan-time-picker"
                    />
                  </template>
                  <template v-else>
                    <el-date-picker
                      v-model="formData.planTime.dayRange"
                      type="datetimerange"
                      range-separator="至"
                      start-placeholder="开始时间"
                      end-placeholder="结束时间"
                      :disabled-hours="dayDisabledHours"
                      class="plan-time-picker"
                    />
                  </template>
                </div>
                <div class="plan-time-icon">
                  <el-icon><Calendar /></el-icon>
                </div>
              </div>
            </el-form-item>
          </div>

          <div class="form-row">
            <el-form-item label="关联父级任务" prop="parentTaskId" class="form-item">
              <el-select
                v-model="formData.parentTaskId"
                placeholder="未选择父级任务"
                clearable
                class="parent-task-select"
              >
                <el-option
                  v-for="task in parentTaskList"
                  :key="task.id"
                  :label="task.title"
                  :value="task.id"
                >
                  <div class="parent-task-option">
                    <span class="task-type-icon">{{ getTaskTypeIcon(task.type) }}</span>
                    <span class="task-title">{{ task.title }}</span>
                    <el-tag :type="getTagType(task.type)" size="small" class="task-type-tag">
                      {{ getTaskTypeName(task.type) }}
                    </el-tag>
                  </div>
                </el-option>
              </el-select>
            </el-form-item>
          </div>
        </div>
      </el-form>
    </div>
    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleCancel" class="cancel-button">取消</el-button>
        <el-button
          type="primary"
          @click="handleSubmit"
          :loading="submitting"
          class="confirm-button"
        >
          确认创建
        </el-button>
      </div>
    </template>
  </BaseDialog>
</template>

<script setup>
import { ref, reactive, computed, watch, nextTick } from 'vue';
import { ElMessage } from 'element-plus';
import { Calendar } from '@element-plus/icons-vue';
import BaseDialog from '@/components/BaseDialog.vue';

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

const formRef = ref(null);
const tagInputRef = ref(null);
const submitting = ref(false);
const showTagInput = ref(false);
const inputTag = ref('');

const formData = reactive({
  taskType: 'day',
  title: '',
  description: '',
  tags: [],
  priority: 'medium',
  planTime: {
    year: new Date().getFullYear().toString(),
    monthRange: [],
    dayRange: [],
  },
  parentTaskId: null,
});

const formRules = computed(() => ({
  title: [
    { required: true, message: '任务标题不能为空', trigger: 'blur' },
    { min: 1, max: 50, message: '任务标题不能超过 50 个字', trigger: 'blur' },
  ],
  description: [{ max: 200, message: '任务描述不能超过 200 个字', trigger: 'blur' }],
  priority: [{ required: true, message: '请选择任务优先级', trigger: 'change' }],
  planTime: [{ required: true, message: '请选择计划时间', trigger: 'change' }],
}));

const taskTypes = [
  { value: 'year', label: '年任务', icon: '📅' },
  { value: 'month', label: '月任务', icon: '📆' },
  { value: 'day', label: '日任务', icon: '📋' },
];

const priorities = [
  { value: 'high', label: '高' },
  { value: 'medium', label: '中' },
  { value: 'low', label: '低' },
];

const parentTaskList = ref([
  { id: '1', title: '年度学习计划', type: 'year' },
  { id: '2', title: '3 月阅读计划', type: 'month' },
  { id: '3', title: '今天晨跑', type: 'day' },
]);

const validateField = async (field) => {
  try {
    await formRef.value.validateField(field);
  } catch (error) {
    console.error('Validation error:', error);
  }
};

const getTagType = (taskType) => {
  const typeMap = {
    year: '',
    month: 'success',
    day: 'warning',
  };
  return typeMap[taskType] || '';
};

const addTag = () => {
  if (inputTag.value.trim()) {
    if (formData.tags.length >= 5) {
      ElMessage.warning('最多只能添加 5 个标签');
      return;
    }
    if (formData.tags.includes(inputTag.value.trim())) {
      ElMessage.warning('标签不能重复');
      return;
    }
    formData.tags.push(inputTag.value.trim());
  }
  inputTag.value = '';
  showTagInput.value = false;
};

const removeTag = (index) => {
  formData.tags.splice(index, 1);
};

const getTaskTypeIcon = (type) => {
  const iconMap = {
    year: '📅',
    month: '📆',
    day: '📋',
  };
  return iconMap[type] || '📋';
};

const getTaskTypeName = (type) => {
  const nameMap = {
    year: '年任务',
    month: '月任务',
    day: '日任务',
  };
  return nameMap[type] || '日任务';
};

const yearDisabledDate = (time) => {
  const currentYear = new Date().getFullYear();
  const selectedYear = time.getFullYear();
  return selectedYear < currentYear - 10 || selectedYear > currentYear + 10;
};

const monthDisabledDate = (time) => {
  const now = new Date();
  now.setHours(0, 0, 0, 0);
  return time.getTime() < now.getTime();
};

const dayDisabledHours = () => {
  const now = new Date();
  const currentHour = now.getHours();
  const disabledHours = [];
  for (let i = 0; i < currentHour; i++) {
    disabledHours.push(i);
  }
  return disabledHours;
};

const handleCancel = () => {
  dialogVisible.value = false;
  resetForm();
};

const resetForm = () => {
  formData.taskType = 'day';
  formData.title = '';
  formData.description = '';
  formData.tags = [];
  formData.priority = 'medium';
  formData.planTime = {
    year: new Date().getFullYear().toString(),
    monthRange: [],
    dayRange: [],
  };
  formData.parentTaskId = null;
  if (formRef.value) {
    formRef.value.clearValidate();
  }
};

const handleSubmit = async () => {
  if (!formRef.value) return;

  try {
    await formRef.value.validate();
    submitting.value = true;

    const submitData = {
      taskType: formData.taskType,
      title: formData.title,
      description: formData.description,
      tags: formData.tags,
      priority: formData.priority,
      planTime: getPlanTimeValue(),
      parentTaskId: formData.parentTaskId,
    };

    console.log('提交数据:', submitData);

    setTimeout(() => {
      ElMessage.success('任务创建成功');
      emit('success', submitData);
      dialogVisible.value = false;
      resetForm();
      submitting.value = false;
    }, 1000);
  } catch (error) {
    console.error('表单验证失败:', error);
    submitting.value = false;
    if (error.fields) {
      const firstField = Object.keys(error.fields)[0];
      const el = document.querySelector(`[prop="${firstField}"]`);
      if (el) {
        el.scrollIntoView({ behavior: 'smooth', block: 'center' });
      }
    }
  }
};

const getPlanTimeValue = () => {
  switch (formData.taskType) {
    case 'year':
      return {
        year: formData.planTime.year,
        monthRange: null,
        dayRange: null,
      };
    case 'month':
      return {
        year: null,
        monthRange: formData.planTime.monthRange,
        dayRange: null,
      };
    case 'day':
      return {
        year: null,
        monthRange: null,
        dayRange: formData.planTime.dayRange,
      };
    default:
      return null;
  }
};

watch(
  () => formData.taskType,
  () => {
    if (formRef.value) {
      formRef.value.clearValidate();
    }
  }
);

watch(showTagInput, (val) => {
  if (val) {
    nextTick(() => {
      tagInputRef.value?.focus();
    });
  }
});
</script>

<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';

.new-task-dialog {
  :deep(.el-dialog__body) {
    padding: 0;
  }
}

.form-content {
  width: 100%;
  max-height: pxToRem(420px);
  overflow-y: auto;
  padding: pxToRem(32px);
  box-sizing: border-box;
}

.task-form {
  width: 100%;
}

.form-container {
  display: flex;
  flex-direction: column;
  gap: pxToRem(24px);
}

.form-row {
  width: 100%;
}

.form-item {
  margin-bottom: 0;

  :deep(.el-form-item__label) {
    font-size: pxToRem(14px);
    font-weight: 700;
    color: #4d5d73;
    line-height: pxToRem(20px);
    letter-spacing: pxToRem(0.7px);
    padding-bottom: pxToRem(12px);
  }
}

.task-type-selector {
  display: flex;
  gap: pxToRem(16px);
}

.type-option {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: pxToRem(16px);
  background: #eaf1ff;
  border-radius: pxToRem(8px);
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;

  &:hover {
    background: #dce4ff;
  }

  &.active {
    background: #eaf1ff;
    border-color: #4a40e0;
  }

  .type-icon {
    font-size: pxToRem(24px);
    margin-bottom: pxToRem(8px);
  }

  .type-name {
    font-size: pxToRem(14px);
    font-weight: 500;
    color: #4d5d73;
  }
}

.form-input,
.form-textarea {
  :deep(.el-input__wrapper),
  :deep(.el-textarea__inner) {
    background: #eaf1ff;
    border-radius: pxToRem(8px);
    box-shadow: none;
    border: none;
    padding: pxToRem(12px) pxToRem(16px);
    min-height: pxToRem(44px);

    &::placeholder {
      color: #8b9ab5;
    }
  }

  :deep(.el-input__inner) {
    font-size: pxToRem(14px);
    color: #4d5d73;
  }
}

.form-textarea {
  :deep(.el-textarea__inner) {
    min-height: pxToRem(88px) !important;
    resize: none;
  }
}

.tags-wrapper {
  display: flex;
  flex-direction: column;
  gap: pxToRem(8px);
}

.tags-container {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: pxToRem(8px);
  min-height: pxToRem(44px);
  padding: pxToRem(10px) pxToRem(16px);
  background: #eaf1ff;
  border-radius: pxToRem(8px);
}

.tag-item {
  background: #4a40e0;
  color: #fff;
  border: none;
  border-radius: pxToRem(4px);
  padding: pxToRem(4px) pxToRem(8px);
  font-size: pxToRem(12px);

  :deep(.el-tag__close) {
    color: #fff;
    background: transparent;

    &:hover {
      background: rgba(255, 255, 255, 0.2);
    }
  }
}

.tag-input {
  width: pxToRem(100px);

  :deep(.el-input__wrapper) {
    background: transparent;
    box-shadow: none;
    padding: 0;
  }
}

.add-tag-btn {
  background: transparent;
  border: 1px dashed #4a40e0;
  color: #4a40e0;
  font-size: pxToRem(12px);
  padding: pxToRem(4px) pxToRem(12px);
  border-radius: pxToRem(4px);

  &:hover {
    background: #eaf1ff;
    border-color: #4a40e0;
    color: #4a40e0;
  }
}

.tags-hint {
  font-size: pxToRem(12px);
  color: #8b9ab5;
}

.priority-selector {
  display: flex;
  gap: pxToRem(16px);
}

.priority-option {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: pxToRem(8px);
  padding: pxToRem(12px) pxToRem(16px);
  background: #eaf1ff;
  border-radius: pxToRem(8px);
  cursor: pointer;
  transition: all 0.3s ease;
  border: 2px solid transparent;

  &:hover {
    background: #dce4ff;
  }

  &.active {
    border-color: #4a40e0;
  }

  .priority-dot {
    width: pxToRem(8px);
    height: pxToRem(8px);
    border-radius: 50%;
  }

  &.high .priority-dot {
    background: #f56c6c;
  }

  &.medium .priority-dot {
    background: #e6a23c;
  }

  &.low .priority-dot {
    background: #67c23a;
  }

  .priority-label {
    font-size: pxToRem(14px);
    font-weight: 500;
    color: #4d5d73;
  }
}

.plan-time-item {
  :deep(.el-form-item__content) {
    line-height: 1;
  }
}

.plan-time-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  padding: pxToRem(16px);
  background: #eaf1ff;
  border-radius: pxToRem(8px);
  border-left: pxToRem(4px) solid #4a40e0;
  box-sizing: border-box;
}

.plan-time-content {
  flex: 1;
}

.plan-time-picker {
  width: 100%;

  :deep(.el-input__wrapper) {
    background: transparent;
    box-shadow: none;
    padding: 0;

    &::after {
      display: none;
    }
  }

  :deep(.el-range-input) {
    font-size: pxToRem(14px);
    color: #4d5d73;

    &::placeholder {
      color: #8b9ab5;
    }
  }

  :deep(.el-range-separator) {
    color: #4d5d73;
  }

  :deep(.el-input__prefix),
  :deep(.el-input__suffix) {
    display: none;
  }
}

.plan-time-icon {
  display: flex;
  align-items: center;
  justify-content: center;
  width: pxToRem(26px);
  height: pxToRem(26px);
  color: #4a40e0;
  font-size: pxToRem(18px);
  margin-left: pxToRem(16px);
}

.parent-task-select {
  width: 100%;

  :deep(.el-input__wrapper) {
    background: #eaf1ff;
    border-radius: pxToRem(8px);
    box-shadow: none;
    border: none;
    padding: pxToRem(12px) pxToRem(16px);
    opacity: 0.7;

    &::placeholder {
      color: #4d5d73;
    }
  }

  :deep(.el-input__inner) {
    font-size: pxToRem(14px);
    color: #4d5d73;
  }
}

.parent-task-option {
  display: flex;
  align-items: center;
  gap: pxToRem(12px);

  .task-type-icon {
    font-size: pxToRem(16px);
  }

  .task-title {
    flex: 1;
    font-size: pxToRem(14px);
    color: #4d5d73;
  }

  .task-type-tag {
    font-size: pxToRem(12px);
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: pxToRem(12px);
  padding: pxToRem(16px) pxToRem(32px);
  border-top: 1px solid #e4e7ed;
}

.cancel-button {
  padding: pxToRem(10px) pxToRem(20px);
  font-size: pxToRem(14px);
  border-radius: pxToRem(6px);

  &:hover {
    color: #4a40e0;
    border-color: #4a40e0;
  }
}

.confirm-button {
  padding: pxToRem(10px) pxToRem(20px);
  font-size: pxToRem(14px);
  border-radius: pxToRem(6px);
  background: #4a40e0;
  border-color: #4a40e0;

  &:hover {
    background: #5b51e8;
    border-color: #5b51e8;
  }
}

@media screen and (max-width: 768px) {
  .form-content {
    padding: pxToRem(20px);
  }

  .task-type-selector {
    flex-direction: column;
  }

  .priority-selector {
    flex-direction: column;
  }

  .plan-time-card {
    flex-direction: column;
    align-items: flex-start;
    gap: pxToRem(12px);
  }

  .plan-time-icon {
    margin-left: 0;
  }
}
</style>
