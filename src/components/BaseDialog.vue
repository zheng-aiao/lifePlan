<template>
  <el-dialog
    v-model="visible"
    :title="showHeader ? '' : title"
    :width="width"
    :close-on-click-modal="closeOnClickModal"
    :close-on-press-escape="closeOnPressEscape"
    :show-close="showClose"
    custom-class="base-dialog"
    append-to-body
    @close="handleClose"
  >
    <div v-if="showHeader" class="dialog-header">
      <div class="header-content">
        <h3 class="dialog-title">{{ title }}</h3>
        <div v-if="$slots.headerExtra" class="header-extra">
          <slot name="headerExtra"></slot>
        </div>
      </div>
      <button v-if="showClose" class="close-btn" @click="handleClose">
        <el-icon><Close /></el-icon>
      </button>
    </div>

    <div class="dialog-body" :style="bodyStyle">
      <slot></slot>
    </div>

    <div v-if="showFooter" class="dialog-footer">
      <slot name="footer">
        <div class="footer-buttons">
          <el-button
            v-if="showCancel"
            class="cancel-btn"
            @click="handleCancel"
          >
            {{ cancelText }}
          </el-button>
          <el-button
            v-if="showConfirm"
            type="primary"
            class="confirm-btn"
            :loading="confirmLoading"
            @click="handleConfirm"
          >
            <el-icon v-if="confirmIcon && !confirmLoading" class="btn-icon">
              <component :is="confirmIcon" />
            </el-icon>
            <span>{{ confirmText }}</span>
          </el-button>
        </div>
      </slot>
    </div>
  </el-dialog>
</template>

<script setup>
import { computed } from 'vue';
import { Close } from '@element-plus/icons-vue';

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: '',
  },
  width: {
    type: [String, Number],
    default: '500px',
  },
  showHeader: {
    type: Boolean,
    default: true,
  },
  showFooter: {
    type: Boolean,
    default: true,
  },
  showClose: {
    type: Boolean,
    default: true,
  },
  showCancel: {
    type: Boolean,
    default: true,
  },
  showConfirm: {
    type: Boolean,
    default: true,
  },
  cancelText: {
    type: String,
    default: '取消',
  },
  confirmText: {
    type: String,
    default: '确认',
  },
  confirmIcon: {
    type: [Object, String],
    default: null,
  },
  confirmLoading: {
    type: Boolean,
    default: false,
  },
  closeOnClickModal: {
    type: Boolean,
    default: false,
  },
  closeOnPressEscape: {
    type: Boolean,
    default: true,
  },
  bodyStyle: {
    type: Object,
    default: () => ({}),
  },
});

const emit = defineEmits(['update:modelValue', 'confirm', 'cancel', 'close']);

const visible = computed({
  get: () => props.modelValue,
  set: (val) => emit('update:modelValue', val),
});

const handleClose = () => {
  emit('close');
  visible.value = false;
};

const handleCancel = () => {
  emit('cancel');
  visible.value = false;
};

const handleConfirm = () => {
  emit('confirm');
};
</script>

<style scoped lang="scss">
:deep(.base-dialog) {
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0px 25px 50px -12px rgba(0, 0, 0, 0.25);
  border: 1px solid rgba(158, 174, 199, 0.1);

  .el-dialog__header {
    display: none;
    padding: 0;
    margin: 0;
  }

  .el-dialog__body {
    padding: 0;
  }

  .el-dialog__footer {
    padding: 0;
  }
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 24px;
  background: linear-gradient(180deg, rgba(244, 246, 255, 1) 0%, rgba(255, 255, 255, 1) 100%);
  border-bottom: 1px solid rgba(220, 233, 255, 1);
}

.header-content {
  display: flex;
  align-items: center;
  gap: 12px;
  flex: 1;
}

.dialog-title {
  font-size: 16px;
  font-weight: 600;
  color: rgba(32, 48, 68, 1);
  margin: 0;
}

.header-extra {
  display: flex;
  align-items: center;
}

.close-btn {
  width: 32px;
  height: 32px;
  border: none;
  background: transparent;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 8px;
  transition: all 0.2s ease;
  padding: 0;
  
  &:hover {
    background-color: rgba(244, 246, 255, 1);
  }
  
  .el-icon {
    font-size: 16px;
    color: rgba(158, 174, 199, 1);
    
    &:hover {
      color: rgba(74, 64, 224, 1);
    }
  }
}

.dialog-body {
  background-color: #fff;
  position: relative;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  padding: 20px 32px;
  border-top: 1px solid rgba(220, 233, 255, 1);
  background-color: #fff;
}

.footer-buttons {
  display: flex;
  gap: 12px;
}

.cancel-btn {
  min-width: 76px;
  height: 40px;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 500;
  color: rgba(77, 93, 115, 1);
  border: none;
  background-color: transparent;
  
  &:hover {
    background-color: rgba(244, 246, 255, 1);
    color: rgba(74, 64, 224, 1);
  }
}

.confirm-btn {
  min-width: 100px;
  height: 40px;
  border-radius: 12px;
  font-size: 14px;
  font-weight: 700;
  color: rgba(244, 241, 255, 1);
  background: linear-gradient(135deg, rgba(74, 64, 224, 1) 0%, rgba(61, 48, 212, 1) 100%);
  border: none;
  box-shadow: 0px 8px 24px rgba(32, 48, 68, 0.12);
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  
  &:hover {
    background: linear-gradient(135deg, rgba(59, 48, 191, 1) 0%, rgba(48, 36, 180, 1) 100%);
    transform: translateY(-1px);
    box-shadow: 0px 12px 28px rgba(32, 48, 68, 0.16);
  }
  
  .btn-icon {
    font-size: 14px;
  }
}
</style>
