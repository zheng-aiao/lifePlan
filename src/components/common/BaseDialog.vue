<template>
  <el-dialog
    v-model="visible"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    append-to-body
  >
    <div class="dialog-container">
      <div class="dialog-header">
        <h3 class="dialog-title">{{ title }}</h3>
        <BaseButton type="cancel" iconBtn @click="handleClose">关闭</BaseButton>
      </div>

      <div class="dialog-body" :style="bodyStyle">
        <slot></slot>
      </div>

      <div class="dialog-footer">
        <slot name="footer">
          <div class="footer-buttons">
            <BaseButton
              type="cancel"
              :label="cancelText"
              textBtn
              @click="handleCancel"
              class="cancel-btn"
            ></BaseButton>
            <BaseButton
              type="confirm"
              :label="confirmText"
              textBtn
              @click="handleConfirm"
              class="confirm-btn"
            ></BaseButton>
          </div>
        </slot>
      </div>
    </div>
  </el-dialog>
</template>

<script setup>
import { computed, watch } from 'vue';
import BaseButton from './BaseButton.vue';

const props = defineProps({
  modelValue: {
    type: Boolean,
    default: false,
  },
  title: {
    type: String,
    default: '',
  },
  cancelText: {
    type: String,
    default: '取消',
  },
  confirmText: {
    type: String,
    default: '确认',
  },
  size: {
    type: String,
    default: 'normal',
    validator: (value) => ['large', 'normal'].includes(value),
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
  root.style.setProperty('--dialog-width', widthPx);
  root.style.setProperty('--dialog-height', heightPx);
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
  @include flexCenter(center, false, true);
  @include wh(100%, 100%);
  @include dialogStyle;

  .dialog-header {
    @include flexCenter(space-between, center);
    padding: 0 pxToRem(24);
    height: pxToRem(64);
    border-bottom: 1px solid var(--dividerColor);

    .dialog-title {
      @include fontStyle(1);
    }
  }

  .dialog-body {
    flex: 1;
    @include flexCenter(flex-start, false, true);
    background-color: #fff;
    position: relative;
    padding: 0 pxToRem(24);
    overflow: auto;
  }

  .dialog-footer {
    @include flexCenter(flex-end, center);
    border-top: 1px solid var(--dividerColor);
    padding: 0 pxToRem(24);
    height: pxToRem(64);

    .footer-buttons {
      @include flexCenter(center center);
      gap: pxToRem(12);
      height: 100%;

      .cancel-btn {
        @include cancelBtnColor(background, 1);
        @include fontStyle(5);
        color: var(--textColor-1);
        padding: 0.5rem 1.5rem;
        @include hover {
          background-color: rgb(159, 163, 186);
          color: rgba(74, 64, 224, 1);
        }
      }

      .confirm-btn {
        @include confirmBtnColor(background, 1);
        @include fontStyle(5);

        color: var(--textColor-5);
        padding: 0.5rem 1.5rem;
        @include hover {
          background: linear-gradient(135deg, rgba(59, 48, 191, 1) 0%, rgba(48, 36, 180, 1) 100%);
          color: rgba(255, 255, 245, 0.6);
        }
      }
    }
  }
}
</style>
