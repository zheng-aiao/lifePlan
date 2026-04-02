<template>
  <el-dialog
    v-model="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    append-to-body
  >
    <div class="dialog-container">
      <div class="dialog-header">
        <div class="header-content">
          <h3 class="dialog-title">{{ title }}</h3>
          <div v-if="$slots.headerExtra" class="header-extra">
            <slot name="headerExtra"></slot>
          </div>
        </div>
        <button class="close-btn" @click="handleClose">
          <el-icon><Close /></el-icon>
        </button>
      </div>

      <div class="dialog-body" :style="bodyStyle">
        <slot></slot>
      </div>

      <div class="dialog-footer">
        <slot name="footer">
          <div class="footer-buttons">
            <el-button v-if="showCancel" class="cancel-btn" @click="handleCancel">
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
    </div>
  </el-dialog>
</template>

<script setup>
import { computed, watch } from 'vue';
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
  size: {
    type: String,
    default: 'normal',
    validator: (value) => ['large', 'normal'].includes(value),
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
  bodyStyle: {
    type: Object,
    default: () => ({}),
  },
});

const emit = defineEmits(['update:modelValue', 'confirm', 'cancel', 'close']);

// 不同尺寸的缩放比例（相对于large尺寸）
const sizeScale = {
  large: 1, // large: 100%
  normal: 0.667, // normal: 66.7% (600/900, 400/600)
};

const updateRootVars = () => {
  const root = document.documentElement;
  const styles = getComputedStyle(root);
  const scale = sizeScale[props.size] || 1;

  // 从-base变量读取原始px值（不会被修改），按比例缩放后设置到使用变量
  const widthPx = parseFloat(styles.getPropertyValue('--dialog-width-base').trim()) * scale;
  const heightPx = parseFloat(styles.getPropertyValue('--dialog-height-base').trim()) * scale;
  const headerHeightPx =
    parseFloat(styles.getPropertyValue('--dialog-header-height-base').trim()) * scale;
  const footerHeightPx =
    parseFloat(styles.getPropertyValue('--dialog-footer-height-base').trim()) * scale;

  root.style.setProperty('--dialog-width', widthPx);
  root.style.setProperty('--dialog-height', heightPx);
  root.style.setProperty('--dialog-header-height', headerHeightPx);
  root.style.setProperty('--dialog-footer-height', footerHeightPx);
};

watch(
  () => props.modelValue,
  (newVal) => {
    if (newVal) {
      updateRootVars();
    }
  }
);

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
@use '@/assets/scss/rules' as *;

.dialog-container {
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 100%;
}

.dialog-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background: linear-gradient(180deg, rgba(244, 246, 255, 1) 0%, rgba(255, 255, 255, 1) 100%);
  border-bottom: 1px solid rgba(220, 233, 255, 1);
  padding: 0 pxToRem(24);
  height: calc(var(--dialog-header-height) / 16 * 1rem);
  flex-shrink: 0;

  .header-content {
    display: flex;
    align-items: center;
    gap: pxToRem(12);
    flex: 1;

    .dialog-title {
      font-size: pxToRem(16);
      font-weight: 600;
      color: rgba(32, 48, 68, 1);
      margin: 0;
    }

    .header-extra {
      display: flex;
      align-items: center;
    }
  }

  .close-btn {
    width: pxToRem(32);
    height: pxToRem(32);
    border: none;
    background: transparent;
    cursor: pointer;
    display: flex;
    align-items: center;
    justify-content: center;
    border-radius: pxToRem(8);
    transition: all 0.2s ease;
    padding: 0;

    &:hover {
      background-color: rgba(244, 246, 255, 1);

      .el-icon {
        color: rgba(74, 64, 224, 1);
      }
    }

    .el-icon {
      font-size: pxToRem(16);
      color: rgba(158, 174, 199, 1);
      transition: color 0.2s ease;
    }
  }
}

.dialog-body {
  flex: 1;
  display: flex;
  flex-direction: column;
  background-color: #fff;
  position: relative;
  padding: 0 pxToRem(24);
  overflow: auto;
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  border-top: 1px solid rgba(220, 233, 255, 1);
  background-color: #fff;
  padding: 0 pxToRem(24);
  height: calc(var(--dialog-footer-height) / 16 * 1rem);
  flex-shrink: 0;

  .footer-buttons {
    display: flex;
    align-items: center;
    gap: pxToRem(12);
    height: 100%;

    .cancel-btn {
      min-width: pxToRem(76);
      height: pxToRem(40);
      border-radius: pxToRem(12);
      font-size: pxToRem(14);
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
      min-width: pxToRem(100);
      height: pxToRem(40);
      border-radius: pxToRem(12);
      font-size: pxToRem(14);
      font-weight: 700;
      color: rgba(244, 241, 255, 1);
      background: linear-gradient(135deg, rgba(74, 64, 224, 1) 0%, rgba(61, 48, 212, 1) 100%);
      border: none;
      box-shadow: 0px pxToRem(8) pxToRem(24) rgba(32, 48, 68, 0.12);
      display: flex;
      align-items: center;
      justify-content: center;
      gap: pxToRem(8);

      &:hover {
        background: linear-gradient(135deg, rgba(59, 48, 191, 1) 0%, rgba(48, 36, 180, 1) 100%);
        transform: translateY(-pxToRem(1));
        box-shadow: 0px pxToRem(12) pxToRem(28) rgba(32, 48, 68, 0.16);
      }

      .btn-icon {
        font-size: pxToRem(14);
      }
    }
  }
}
</style>
