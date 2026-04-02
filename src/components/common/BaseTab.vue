<template>
  <div class="base-tab">
    <div
      v-for="item in options"
      :key="item[valueKey]"
      :class="['tab-item', { active: modelValue === item[valueKey] }]"
      @click="handleTabClick(item)"
    >
      <span class="tab-label">{{ item[labelKey] }}</span>
    </div>
  </div>
</template>

<script setup>
const props = defineProps({
  modelValue: {
    type: [String, Number],
    default: '',
  },
  options: {
    type: Array,
    default: () => [],
  },
  labelKey: {
    type: String,
    default: 'label',
  },
  valueKey: {
    type: String,
    default: 'value',
  },
});

const emit = defineEmits(['update:modelValue', 'change']);

const handleTabClick = (item) => {
  emit('update:modelValue', item[props.valueKey]);
  emit('change', item);
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.base-tab {
  display: flex;
  gap: pxToRem(32);
  border-bottom: 1px solid rgba(234, 241, 255, 1);
}

.tab-item {
  padding: pxToRem(16) 0;
  font-size: pxToRem(14);
  font-weight: 500;
  color: rgba(77, 93, 115, 1);
  cursor: pointer;
  border-bottom: 2px solid transparent;
  transition: all 0.3s ease;
  user-select: none;
  margin-bottom: -1px;

  &:hover {
    color: rgba(74, 64, 224, 1);
  }

  &.active {
    font-weight: 700;
    color: rgba(74, 64, 224, 1);
    border-bottom-color: rgba(74, 64, 224, 1);
  }

  .tab-label {
    white-space: nowrap;
    line-height: pxToRem(20);
  }
}
</style>
