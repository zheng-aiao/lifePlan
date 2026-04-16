/** * ElTag二次封装组件 * 透明传递所有属性、事件和插槽给原生el-tag组件 * 默认设置size="small" */
<template>
  <el-tag ref="elTagRef" size="small" v-bind="$attrs" v-on="$listeners" :style="tagStyle">
    <slot></slot>
  </el-tag>
</template>

<script setup>
import { ref, defineProps, defineExpose, computed } from 'vue';

const props = defineProps({
  // 背景色
  id: {
    type: String,
    default: null,
  },
});

const elTagRef = ref(null);

// 根据id查询标签信息的样式
const tagStyle = computed(() => {
  const style = { backgroundColor: 'rgba(151, 149, 255, 1)', color: 'rgba(20, 0, 126, 1)' };
  if (props.id) {
    // style.backgroundColor = getTagColor(props.id);
  }
  return style;
});

// 转发el-tag的常用方法
defineExpose({
  // 获取原生标签元素
  click: () => elTagRef.value?.click(),
  close: () => elTagRef.value?.close(),
});
</script>

<style lang="scss">
@use '@/assets/scss/rules' as *;

.el-tag {
  @include fontStyle(6);
  padding: pxToRem(2) pxToRem(8);
  border-radius: pxToRem(9999);
  word-break: break-all;
  overflow-wrap: break-word;
  white-space: normal;
}
</style>
