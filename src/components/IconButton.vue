<template>
  <button type="button" class="icon-button" @click="$emit('click')">
    <img :src="icon" class="icon" v-if="icon" />
    <span class="text" v-if="text">{{ text }}</span>
  </button>
</template>

<script setup>
const props = defineProps({
  type: {
    type: String,
    default: "icon-text",
  },
  text: {
    type: String,
    default: "",
  },
  icon: {
    type: String,
    default: "",
  },
});

const emit = defineEmits(["click"]);
</script>

<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';

.icon-button {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: pxToRem(8);
  padding: pxToRem(12) pxToRem(24);
  border: none;
  border-radius: pxToRem(8);
  background-color: rgba(74, 64, 224, 1);
  color: rgba(244, 241, 255, 1);
  font-size: pxToRem(12);
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  letter-spacing: pxToRem(1.2);
  line-height: pxToRem(16);
  text-transform: uppercase;
}
</style>
