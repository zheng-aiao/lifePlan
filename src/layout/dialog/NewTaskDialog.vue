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
    <BaseTab v-model="formData.taskType" :options="taskTypes" />

    <div class="form-content">
      <div class="form-item">
        <div class="form-label">
          <span>任务标题</span>
          <span class="char-count">{{ formData.title.length }}/50</span>
        </div>
        <BaseInput
          v-model="formData.title"
          placeholder="给你的目标起个好名字..."
          maxlength="50"
          class="form-input"
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
            <span>计划时间</span>
          </div>
          <BaseDate v-model="formData.planTime" :task-type="formData.taskType" />
        </div>

        <div class="form-item form-item-half">
          <div class="form-label">
            <span>父任务</span>
            <span class="optional-label">(可选)</span>
          </div>
          <BaseDropdown
            v-model="formData.parentId"
            :options="parentTaskList"
            placeholder="选择父任务..."
          />
        </div>
      </div>

      <div class="form-item">
        <div class="form-label">
          <span>任务详情</span>
          <span class="char-count">{{ formData.description.length }}/200</span>
        </div>
        <BaseInput
          v-model="formData.description"
          type="textarea"
          placeholder="添加更多细节或步骤..."
          maxlength="200"
          :rows="3"
          class="form-textarea"
        />
      </div>
    </div>
  </BaseDialog>
</template>

<script setup>
import { ref, reactive, computed, watch } from 'vue';
import { ElMessage } from 'element-plus';
import { Calendar, Check } from '@element-plus/icons-vue';
import BaseDialog from '@/components/common/BaseDialog.vue';
import BaseTab from '@/components/common/BaseTab.vue';
import TagSelect from '@/layout/component/TagSelect.vue';
import BaseDate from '@/components/common/BaseDate.vue';
import BaseDropdown from '@/components/common/BaseDropdown.vue';
import BaseInput from '@/components/common/BaseInput.vue';
import bizService from '@/utils/bizService';

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
  planTime: null,
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
  formData.planTime = null;
  formData.parentId = null;
};

const handleSubmit = async () => {
  if (!formData.title.trim()) {
    ElMessage.warning('请输入任务标题');
    return;
  }

  // 验证日任务的时间范围
  if (formData.taskType === 3 && formData.planTime && formData.planTime.length === 2) {
    const [startTime, endTime] = formData.planTime;
    if (startTime && endTime) {
      const [startHour, startMinute] = startTime.split(':').map(Number);
      const [endHour, endMinute] = endTime.split(':').map(Number);

      if (endHour < startHour || (endHour === startHour && endMinute < startMinute)) {
        ElMessage.warning('结束时间必须在开始时间之后');
        return;
      }
    }
  }

  submitting.value = true;

  let plannedStartTime = null;
  let plannedEndTime = null;

  if (formData.planTime && formData.planTime.length === 2) {
    const [start, end] = formData.planTime;
    if (formData.taskType === 1) {
      plannedStartTime = `${start}-01T00:00:00`;
      const [year, month] = end.split('-');
      const lastDay = new Date(parseInt(year), parseInt(month), 0).getDate();
      plannedEndTime = `${end}-${lastDay}T23:59:59`;
    } else if (formData.taskType === 2) {
      plannedStartTime = `${start}T00:00:00`;
      plannedEndTime = `${end}T23:59:59`;
    } else if (formData.taskType === 3) {
      const now = new Date();
      const year = now.getFullYear();
      const month = String(now.getMonth() + 1).padStart(2, '0');
      const day = String(now.getDate()).padStart(2, '0');
      plannedStartTime = `${year}-${month}-${day}T${start}:00`;
      plannedEndTime = `${year}-${month}-${day}T${end}:00`;
    }
  }

  const submitData = {
    title: formData.title,
    taskType: formData.taskType,
    description: formData.description,
    category: formData.tags.length > 0 ? formData.tags[0].name : null,
    taskPriority: formData.priority,
    parentId: formData.parentId,
    plannedStartTime,
    plannedEndTime,
  };

  try {
    const res = await bizService.task.createTask(submitData);
    ElMessage.success('任务创建成功');
    emit('success', res.data);
    dialogVisible.value = false;
    resetForm();
  } catch (error) {
    console.error('创建任务失败:', error);
  } finally {
    submitting.value = false;
  }
};

watch(
  () => formData.taskType,
  () => {
    formData.planTime = null;
  }
);
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.form-content {
  display: flex;
  flex-direction: column;
  gap: pxToRem(24);
  padding: pxToRem(24) 0;
}

.form-row {
  display: flex;
  gap: pxToRem(24);
}

.form-item {
  display: flex;
  flex-direction: column;
  gap: pxToRem(12);

  .form-input,
  .form-textarea {
    @include formItemStyle;
  }
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

.priority-radio-group {
  display: flex;
  gap: pxToRem(16);
  width: 100%;

  .priority-label {
    font-size: pxToRem(14);
    font-weight: 500;
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
  }
}
</style>
