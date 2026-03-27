<template>
  <BaseDialog
    v-model="visible"
    title="任务反馈"
    width="900px"
    height="600px"
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
.feedback-content {
  display: flex;
  gap: 32px;
  padding: 16px 40px;
  height: 431px;
  position: relative;

  .content-left {
    width: 323px;
    display: flex;
    flex-direction: column;

    .status-header {
      display: flex;
      flex-direction: column;
      gap: 8px;
      padding-bottom: 24px;

      .header-row {
        display: flex;
        flex-direction: row;
        gap: 8px;
        align-items: center;

        .header-indicator {
          width: 4px;
          height: 20px;
          border-radius: 12px;
          background-color: rgba(74, 64, 224, 1);
        }

        .header-title {
          font-size: 16px;
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          font-weight: 700;
          line-height: 24px;
          color: rgba(32, 48, 68, 1);
          margin: 0;
        }
      }

      .header-description {
        .description-text {
          font-size: 12px;
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          font-weight: 400;
          line-height: 19.5px;
          color: rgba(77, 93, 115, 1);
          margin: 0;
        }
      }
    }

    .status-selectors {
      display: flex;
      flex-direction: column;
      gap: 10px;

      .type-option {
        width: 100%;
        height: 68px;
        background-color: rgba(234, 241, 255, 1);
        border-radius: 8px;
        padding: 14px;
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
          width: 40px;
          height: 40px;
          background-color: rgba(255, 255, 255, 1);
          border-radius: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
          box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
          flex-shrink: 0;

          .option-icon {
            color: rgba(180, 19, 64, 1);
            font-size: 20px;
          }
        }

        .option-content {
          flex: 1;
          display: flex;
          flex-direction: column;
          padding-left: 12px;

          .option-title {
            font-size: 14px;
            font-weight: 700;
            color: rgba(32, 48, 68, 1);
            line-height: 20px;
            font-family: 'WenQuanYi Zen Hei', sans-serif;
          }

          .option-description {
            font-size: 10px;
            font-weight: 400;
            color: rgba(77, 93, 115, 1);
            line-height: 15px;
            font-family: 'WenQuanYi Zen Hei', sans-serif;
          }
        }

        .option-radio {
          width: 16px;
          height: 16px;
          position: relative;
          flex-shrink: 0;

          .radio-border {
            width: 16px;
            height: 16px;
            border: 1px solid rgba(158, 174, 199, 1);
            border-radius: 12px;
            position: absolute;
            top: 0;
            left: 0;
          }

          .radio-dot {
            width: 8px;
            height: 8px;
            background-color: rgba(74, 64, 224, 1);
            border-radius: 12px;
            position: absolute;
            top: 4px;
            left: 4px;
            opacity: 0;
            transition: opacity 0.2s ease;
          }
        }
      }
    }
  }

  .content-right {
    flex: 1;
    width: 465px;
    display: flex;
    flex-direction: column;

    .right-header {
      display: flex;
      justify-content: space-between;
      align-items: flex-end;
      height: 24px;
      margin-bottom: 24px;

      .section-title {
        font-size: 14px;
        font-weight: 600;
        color: rgba(32, 48, 68, 1);
        margin: 0;
        font-family: 'Inter', sans-serif;
        line-height: 14px;
      }

      .word-count-badge {
        display: flex;
        align-items: center;
        gap: 6px;
        padding: 4px 10px;
        background-color: rgba(255, 255, 255, 0.9);
        border-radius: 12px;
        border: 1px solid rgba(0, 0, 0, 0.05);
        box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);

        .dot-indicator {
          width: 6px;
          height: 6px;
          background-color: rgba(74, 64, 224, 1);
          border-radius: 12px;
        }

        .count-text {
          font-size: 10px;
          font-weight: 700;
          color: rgba(77, 93, 115, 1);
          font-family: 'Inter', sans-serif;
          line-height: 15px;
          letter-spacing: -0.5px;
        }
      }
    }

    .textarea-wrapper {
      flex: 1;
      margin-bottom: 12px;

      .feedback-textarea {
        width: 100%;
        height: 296px;
        background-color: rgba(234, 241, 255, 1);
        border-radius: 16px;
        border: none;
        padding: 19px 20px;

        :deep(.el-textarea__inner) {
          height: 100% !important;
          min-height: 100% !important;
          background-color: transparent;
          border: none;
          resize: none;
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          font-size: 14px;
          line-height: 22.75px;
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
        gap: 12px;
        padding: 12px;
        background-color: rgba(151, 149, 255, 0.1);
        border-radius: 8px;
        border: 1px solid rgba(74, 64, 224, 0.05);

        .tips-icon {
          font-size: 10px;
          color: rgba(74, 64, 224, 1);
          flex-shrink: 0;
        }

        .tips-text {
          font-size: 11px;
          font-weight: 700;
          color: rgba(26, 0, 153, 1);
          font-family: 'WenQuanYi Zen Hei', sans-serif;
          line-height: 15.13px;

          .tips-label {
            font-weight: 700;
          }
        }
      }
    }
  }
}

:deep(.el-dialog) {
  width: 900px !important;
  height: 600px !important;
  border-radius: 32px;
  box-shadow: 0 8px 32px rgba(32, 48, 68, 0.08);
  overflow: hidden;
}

:deep(.el-dialog__header) {
  padding: 32px 40px;
  border-bottom: 1px solid rgba(220, 233, 255, 0.4);

  .el-dialog__title {
    font-size: 20px;
    font-weight: 600;
    color: rgba(32, 48, 68, 1);
    font-family: 'Inter', sans-serif;
  }
}

:deep(.el-dialog__body) {
  padding: 0;
  height: calc(100% - 160px);
}

:deep(.el-dialog__footer) {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  padding: 24px 40px;
  background-color: rgba(234, 241, 255, 0.2);
  border-top: 1px solid rgba(220, 233, 255, 1);

  .el-button {
    border-radius: 12px;
    font-family: 'WenQuanYi Zen Hei', sans-serif;
    font-weight: 700;
    font-size: 14px;
    line-height: 20px;

    &--primary {
      background-color: rgba(74, 64, 224, 1);
      border: none;
      padding: 10px 32px;
      box-shadow:
        0 2px 4px rgba(74, 64, 224, 0.2),
        0 4px 6px rgba(74, 64, 224, 0.2);

      &:hover {
        background-color: rgba(64, 54, 214, 1);
      }
    }

    &--default {
      color: rgba(77, 93, 115, 1);
      border: none;
      padding: 10px 24px;

      &:hover {
        color: rgba(67, 83, 105, 1);
        background-color: rgba(244, 246, 255, 0.5);
      }
    }
  }
}
</style>
