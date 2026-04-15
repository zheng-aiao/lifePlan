<template>
  <button
    type="button"
    :class="['base-button', { 'icon-only': iconBtn }]"
    :disabled="disabled"
    @click="handleClick"
  >
    <!-- 只显示图标：iconBtn=true -->
    <!-- 只显示文字：textBtn=true -->
    <!-- 显示图标+文字：默认情况（iconBtn=false且textBtn=false） -->
    <span v-if="!textBtn" class="iconfont" v-html="btnData.icon"></span>
    <span v-if="!iconBtn" class="text">{{ label || btnData.label }}</span>
  </button>
</template>

<script setup>
import { computed } from 'vue';
import { iconData } from '@/assets/fonts/iconData.js';

const props = defineProps({
  type: {
    type: String,
    default: 'add',
  },
  label: {
    type: String,
    default: '',
  },
  iconBtn: {
    type: Boolean,
    default: false,
  },
  textBtn: {
    type: Boolean,
    default: false,
  },
  disabled: {
    type: Boolean,
    default: false,
  },
});

const emit = defineEmits(['click']);

const btnData = computed(() => {
  return iconData[props.type] || { icon: '', label: '' };
});

const handleClick = (e) => {
  emit('click', e);
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.base-button {
  @include flexCenter;
  gap: pxToRem(8);
  @include fontStyle(4);
  padding: pxToRem(12) pxToRem(24);
  border: none;
  border-radius: pxToRem(8);
  background-color: var(--background);
  cursor: pointer;
  transition: all 0.3s ease;

  &:hover:not(:disabled) {
    background-color: var(--darkColor);
  }

  &:disabled {
    opacity: 0.5;
    cursor: not-allowed;
  }

  .iconfont {
    font-size: pxToRem(16);
    line-height: 1;
    color: var(--textColor-1);
  }

  .text {
    white-space: nowrap;
    color: var(--textColor-1);
  }

  &.icon-only {
    padding: pxToRem(0);
    width: auto;
    min-width: auto;
  }
}
</style>
