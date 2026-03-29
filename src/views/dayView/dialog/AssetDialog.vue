<template>
  <BaseDialog
    v-model="visible"
    title="任务反馈"
    size="large"
    :confirm-text="'提交反馈'"
    @confirm="handleConfirm"
    @cancel="handleCancel"
    @close="handleClose"
  >
    <div class="feedback-content">
      <!-- 左侧列 - 状态选择 -->
      <div class="content-left">
        <!-- 标题区域 -->
        <div class="status-header">
          <div class="header-row">
            <div class="header-indicator"></div>
            <p class="header-title">今日状态</p>
          </div>
          <div class="header-description">
            <p class="description-text">这一刻的感受如何？反馈有助于优化未来的时间分配。</p>
          </div>
        </div>

        <!-- 状态选择器 -->
        <div class="status-selectors">
          <div
            v-for="type in feedbackTypes"
            :key="type.value"
            class="type-option"
            :class="{ active: selectedType === type.value }"
            @click="selectType(type.value)"
          >
            <div class="option-icon-wrapper">
              <div class="option-icon">
                <el-icon :size="20"><component :is="type.icon" /></el-icon>
              </div>
            </div>
            <div class="option-content">
              <div class="option-title">{{ type.label }}</div>
              <div class="option-description">{{ type.description }}</div>
            </div>
            <div class="option-radio">
              <div class="radio-border"></div>
              <div v-if="selectedType === type.value" class="radio-dot"></div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧列 - 详细日志 -->
      <div class="content-right">
        <div class="right-header">
          <h4 class="section-title">详细反馈</h4>
          <div class="word-count-badge">
            <div class="dot-indicator"></div>
            <span class="count-text">{{ form.feedback.length }} / 500 字</span>
          </div>
        </div>

        <div class="textarea-wrapper">
          <el-input
            v-model="form.feedback"
            type="textarea"
            :rows="12"
            placeholder="在此记录您的洞察、阻碍或明天的改进建议..."
            class="feedback-textarea"
            maxlength="500"
          />
        </div>

        <div class="tips-section">
          <div class="tips-content">
            <el-icon class="tips-icon"><InfoFilled /></el-icon>
            <span class="tips-text"
              ><span class="tips-label">提示:</span>
              诚实的记录是成长的基石。即使是负面反馈也是优化的重要数据点。</span
            >
          </div>
        </div>
      </div>
    </div>
  </BaseDialog>
</template>

<script setup>
import { ref, reactive, watch } from 'vue';
import { Check, Warning, Clock, Star, InfoFilled } from '@element-plus/icons-vue';
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
  timeRange: {
    type: String,
    default: '',
  },
  actualDuration: {
    type: String,
    default: '',
  },
});

const emit = defineEmits(['update:modelValue', 'confirm', 'cancel']);

const visible = ref(props.modelValue);

const form = reactive({
  feedback: '',
  type: '',
  attachments: [],
});

const selectedType = ref(null);

const feedbackTypes = [
  {
    label: '完成',
    value: 'completed',
    icon: Check,
    description: '任务已顺利完成',
  },
  {
    label: '挑战',
    value: 'challenge',
    icon: Warning,
    description: '遇到阻碍，需要重新审视策略',
  },
  {
    label: '延期',
    value: 'delayed',
    icon: Clock,
    description: '时间不足，需要更多时间',
  },
  {
    label: '其他',
    value: 'other',
    icon: Star,
    description: '其他情况或建议',
  },
];

const selectType = (value) => {
  selectedType.value = value;
  form.type = value;
};

const handleConfirm = () => {
  emit('confirm', {
    feedback: form.feedback,
    type: form.type,
    attachments: form.attachments,
  });
  visible.value = false;
};

const handleCancel = () => {
  visible.value = false;
  emit('cancel');
};

const handleClose = () => {
  form.feedback = '';
  form.type = '';
  form.attachments = [];
  selectedType.value = null;
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
.feedback-content {
  display: flex;
  gap: pxToRem(24);
  padding: pxToRem(12) pxToRem(24);
  height: 100%;
  box-sizing: border-box;
  position: relative;

  .content-left {
    width: pxToRem(280);
    display: flex;
    flex-direction: column;

    .status-header {
      display: flex;
      flex-direction: column;
      gap: pxToRem(6);
      padding-bottom: pxToRem(16);

      .header-row {
        display: flex;
        flex-direction: row;
        gap: pxToRem(6);
        align-items: center;

        .header-indicator {
          width: pxToRem(3);
          height: pxToRem(16);
          border-radius: pxToRem(8);
          background-color: rgba(74, 64, 224, 1);
        }

        .header-title {
          font-size: pxToRem(14);
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          font-weight: 700;
          line-height: pxToRem(20);
          color: rgba(32, 48, 68, 1);
          margin: 0;
        }
      }

      .header-description {
        .description-text {
          font-size: pxToRem(11);
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          font-weight: 400;
          line-height: pxToRem(16);
          color: rgba(77, 93, 115, 1);
          margin: 0;
        }
      }
    }

    .status-selectors {
      display: flex;
      flex-direction: column;
      gap: pxToRem(8);

      .type-option {
        width: 100%;
        height: pxToRem(56);
        background-color: rgba(234, 241, 255, 1);
        border-radius: pxToRem(6);
        padding: pxToRem(10);
        display: flex;
        align-items: center;
        cursor: pointer;
        transition: all 0.2s ease;
        position: relative;

        &:hover {
          background-color: rgba(74, 64, 224, 0.1);
        }

        &.active {
          background-color: rgba(74, 64, 224, 0.1);

          .option-icon-wrapper {
            .option-icon {
              color: rgba(74, 64, 224, 1);
            }
          }

          .option-radio {
            .radio-border {
              border-color: rgba(74, 64, 224, 1);
            }

            .radio-dot {
              opacity: 1;
            }
          }
        }

        .option-icon-wrapper {
          width: pxToRem(32);
          height: pxToRem(32);
          background-color: rgba(255, 255, 255, 1);
          border-radius: pxToRem(8);
          display: flex;
          align-items: center;
          justify-content: center;
          box-shadow: 0 pxToRem(1) pxToRem(2) rgba(0, 0, 0, 0.05);
          flex-shrink: 0;

          .option-icon {
            color: rgba(180, 19, 64, 1);
            font-size: pxToRem(16);
          }
        }

        .option-content {
          flex: 1;
          display: flex;
          flex-direction: column;
          padding-left: pxToRem(10);

          .option-title {
            font-size: pxToRem(12);
            font-weight: 700;
            color: rgba(32, 48, 68, 1);
            line-height: pxToRem(16);
            font-family: 'WenQuanYi Zen Hei', sans-serif;
          }

          .option-description {
            font-size: pxToRem(9);
            font-weight: 400;
            color: rgba(77, 93, 115, 1);
            line-height: pxToRem(12);
            font-family: 'WenQuanYi Zen Hei', sans-serif;
          }
        }

        .option-radio {
          width: pxToRem(14);
          height: pxToRem(14);
          position: relative;
          flex-shrink: 0;

          .radio-border {
            width: pxToRem(14);
            height: pxToRem(14);
            border: 1px solid rgba(158, 174, 199, 1);
            border-radius: pxToRem(8);
            position: absolute;
            top: 0;
            left: 0;
          }

          .radio-dot {
            width: pxToRem(6);
            height: pxToRem(6);
            background-color: rgba(74, 64, 224, 1);
            border-radius: pxToRem(8);
            position: absolute;
            top: pxToRem(4);
            left: pxToRem(4);
            opacity: 0;
            transition: opacity 0.2s ease;
          }
        }
      }
    }
  }

  .content-right {
    flex: 1;
    width: pxToRem(400);
    display: flex;
    flex-direction: column;

    .right-header {
      display: flex;
      justify-content: space-between;
      align-items: flex-end;
      height: pxToRem(20);
      margin-bottom: pxToRem(16);

      .section-title {
        font-size: pxToRem(12);
        font-weight: 600;
        color: rgba(32, 48, 68, 1);
        margin: 0;
        font-family: 'Inter', sans-serif;
        line-height: pxToRem(12);
      }

      .word-count-badge {
        display: flex;
        align-items: center;
        gap: pxToRem(4);
        padding: pxToRem(3) pxToRem(8);
        background-color: rgba(255, 255, 255, 0.9);
        border-radius: pxToRem(10);
        border: 1px solid rgba(0, 0, 0, 0.05);
        box-shadow: 0 pxToRem(1) pxToRem(2) rgba(0, 0, 0, 0.05);

        .dot-indicator {
          width: pxToRem(5);
          height: pxToRem(5);
          background-color: rgba(74, 64, 224, 1);
          border-radius: pxToRem(8);
        }

        .count-text {
          font-size: pxToRem(9);
          font-weight: 700;
          color: rgba(77, 93, 115, 1);
          font-family: 'Inter', sans-serif;
          line-height: pxToRem(12);
          letter-spacing: -pxToRem(0.4);
        }
      }
    }

    .textarea-wrapper {
      flex: 1;
      margin-bottom: pxToRem(8);

      .feedback-textarea {
        width: 100%;
        height: 100%;
        background-color: rgba(234, 241, 255, 1);
        border-radius: pxToRem(12);
        border: none;
        padding: pxToRem(14) pxToRem(16);

        :deep(.el-textarea__inner) {
          height: 100% !important;
          min-height: 100% !important;
          background-color: transparent;
          border: none;
          resize: none;
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          font-size: pxToRem(12);
          line-height: pxToRem(18);
          color: rgba(32, 48, 68, 1);
          padding: 0;

          &::placeholder {
            color: rgba(104, 120, 143, 0.5);
          }
        }
      }
    }

    .tips-section {
      .tips-content {
        display: flex;
        align-items: center;
        gap: pxToRem(8);
        padding: pxToRem(8);
        background-color: rgba(151, 149, 255, 0.1);
        border-radius: pxToRem(6);
        border: 1px solid rgba(74, 64, 224, 0.05);

        .tips-icon {
          font-size: pxToRem(10);
          color: rgba(74, 64, 224, 1);
          flex-shrink: 0;
        }

        .tips-text {
          font-size: pxToRem(10);
          font-weight: 700;
          color: rgba(26, 0, 153, 1);
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          line-height: pxToRem(14);

          .tips-label {
            font-weight: 700;
          }
        }
      }
    }
  }
}
</style>
