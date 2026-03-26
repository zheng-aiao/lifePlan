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
.icon-button {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  padding: 0.75rem 1.5rem;
  border: none;
  border-radius: 0.5rem;
  background-color: rgba(74, 64, 224, 1);
  color: rgba(244, 241, 255, 1);
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  letter-spacing: 0.075rem;
  line-height: 1rem;
  text-transform: uppercase;
}
</style>
