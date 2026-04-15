/** * ElInput二次封装组件 * 透明传递所有属性、事件和插槽给原生el-input组件 */
<template>
  <el-input v-bind="$attrs" ref="elInputRef" v-on="$listeners">
    <slot></slot>
    <template v-if="$slots.prefix" #prefix>
      <slot name="prefix"></slot>
    </template>
    <template v-if="$slots.suffix" #suffix>
      <slot name="suffix"></slot>
    </template>
    <template v-if="$slots.prepend" #prepend>
      <slot name="prepend"></slot>
    </template>
    <template v-if="$slots.append" #append>
      <slot name="append"></slot>
    </template>
  </el-input>
</template>
<script setup>
import { ref, defineExpose } from 'vue';

const elInputRef = ref(null);

// 转发el-input的方法
defineExpose({
  focus: () => elInputRef.value?.focus(),
  blur: () => elInputRef.value?.blur(),
  select: () => elInputRef.value?.select(),
  clear: () => elInputRef.value?.clear(),
});
</script>
<style scoped lang="scss">
@use '@/assets/scss/rules' as *;
</style>
