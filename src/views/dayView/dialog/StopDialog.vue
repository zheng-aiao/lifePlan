<template>
  <BaseDialog
    v-model="visible"
    title="中止任务"
    size="normal"
    :confirm-text="'确认中止'"
    :cancel-text="'取消'"
    @confirm="handleConfirm"
    @cancel="handleCancel"
    @close="handleClose"
  >
    <div class="stop-content">
      <div class="quick-selection-section">
        <div class="section-label">请选择中止原因</div>
        <div class="reason-container">
          <div
            v-for="reason in reasons"
            :key="reason.value"
            class="reason-button"
            :class="{ active: selectedReason === reason.value }"
            @click="selectReason(reason.value)"
          >
            {{ reason.label }}
          </div>
        </div>
      </div>
      <div class="custom-reason-section">
        <div class="section-label">自定义原因</div>
        <div class="textarea-wrapper">
          <el-input
            v-model="form.customReason"
            type="textarea"
            :rows="4"
            placeholder="请输入具体中止原因..."
            class="custom-textarea"
          />
        </div>
      </div>
    </div>
  </BaseDialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue';
import BaseDialog from '@/components/BaseDialog.vue';

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

const visible = ref(props.modelValue);

const form = reactive({
  customReason: '',
  reason: '',
});

const selectedReason = ref(null);

const reasons = [
  { label: '优先级调整', value: 'priority' },
  { label: '需求变更', value: 'requirement' },
  { label: '紧急插单', value: 'urgent' },
  { label: '资源受限', value: 'resource' },
];

const selectReason = (value) => {
  selectedReason.value = value;
  form.reason = value;
};

const handleConfirm = () => {
  emit('confirm', {
    reason: form.reason,
    customReason: form.customReason,
  });
};

const handleCancel = () => {
  emit('cancel');
};

const handleClose = () => {
  form.customReason = '';
  form.reason = '';
  selectedReason.value = null;
};

watch(
  () => props.modelValue,
  (newValue) => {
    visible.value = newValue;
  }
);

watch(visible, (newValue) => {
  emit('update:modelValue', newValue);
});
</script>

<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';
.stop-content {
  display: flex;
  flex-direction: column;
  justify-content: center;
  gap: pxToRem(16);
  padding: pxToRem(8) pxToRem(20);
  height: 100%;
  box-sizing: border-box;

  .quick-selection-section {
    display: flex;
    flex-direction: column;
    gap: pxToRem(8);

    .section-label {
      font-size: pxToRem(10);
      font-family: 'WenQuanYi Zen Hei', sans-serif;
      font-weight: 700;
      letter-spacing: pxToRem(0.8);
      line-height: pxToRem(14);
      text-transform: uppercase;
      color: rgba(77, 93, 115, 1);
    }

    .reason-container {
      display: flex;
      flex-direction: row;
      gap: pxToRem(6);

      .reason-button {
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
        padding: pxToRem(6) pxToRem(12);
        height: pxToRem(32);
        border-radius: pxToRem(8);
        background-color: rgba(234, 241, 255, 1);
        cursor: pointer;
        transition: all 0.2s ease;
        font-size: pxToRem(12);
        font-family: 'WenQuanYi Zen Hei', sans-serif;
        font-weight: 500;
        line-height: pxToRem(18);
        color: rgba(77, 93, 115, 1);
        box-sizing: border-box;

        &:hover {
          background-color: rgba(74, 64, 224, 0.1);
        }

        &.active {
          background-color: rgba(74, 64, 224, 1);
          color: rgba(244, 241, 255, 1);
          box-shadow: 0 pxToRem(1) pxToRem(2) rgba(0, 0, 0, 0.05);
        }
      }
    }
  }

  .custom-reason-section {
    flex: 1;
    display: flex;
    flex-direction: column;
    gap: pxToRem(8);

    .section-label {
      font-size: pxToRem(10);
      font-family: 'WenQuanYi Zen Hei', sans-serif;
      font-weight: 700;
      letter-spacing: pxToRem(0.8);
      line-height: pxToRem(14);
      text-transform: uppercase;
      color: rgba(77, 93, 115, 1);
    }

    .textarea-wrapper {
      flex: 1;
      width: 100%;
      min-height: pxToRem(100);
      border-radius: pxToRem(6);
      background-color: rgba(234, 241, 255, 1);
      padding: pxToRem(12);
      box-sizing: border-box;

      .custom-textarea {
        width: 100%;
        height: 100%;

        :deep(.el-textarea__inner) {
          height: 100% !important;
          min-height: 100% !important;
          background-color: transparent;
          border: none;
          box-shadow: none;
          outline: none;
          resize: none;
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          font-size: pxToRem(12);
          font-weight: 400;
          line-height: pxToRem(18);
          color: rgba(32, 48, 68, 1);
          padding: 0;

          &::placeholder {
            color: rgba(158, 174, 199, 1);
          }
        }
      }
    }
  }
}
</style>
