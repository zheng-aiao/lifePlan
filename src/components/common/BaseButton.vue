<template>
  <button
    type="button"
    :class="['base-button', { 'icon-only': iconBtn }]"
    :disabled="disabled"
    @click="handleClick"
  >
    <span class="iconfont" v-html="btnData.icon"></span>
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
  display: flex;
  align-items: center;
  justify-content: center;
  gap: pxToRem(8);
  padding: pxToRem(12) pxToRem(24);
  border: none;
  border-radius: pxToRem(8);
  background-color: var(--background);
  color: var(--textColor-1);
  font-size: pxToRem(14);
  font-weight: 400;
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
  }

  .text {
    white-space: nowrap;
  }

  &.icon-only {
    padding: pxToRem(0);
    width: auto;
    min-width: auto;
  }
}
</style>
