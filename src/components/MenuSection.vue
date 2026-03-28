<template>
  <div class="menu-section">
    <div class="section-label">
      <p class="label-text">{{ title }}</p>
    </div>
    <el-menu
      :default-active="activeIndex"
      class="section-menu"
      @select="handleSelect"
    >
      <el-menu-item
        v-for="item in data"
        :key="item.id"
        :index="item.id.toString()"
        class="menu-item"
      >
        <img :src="item.icon" class="menu-icon" />
        <span class="menu-text">{{ item.name }}</span>
      </el-menu-item>
    </el-menu>
  </div>
</template>

<script setup>
const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  activeIndex: {
    type: String,
    default: "",
  },
  data: {
    type: Array,
    default: () => [],
  },
});

const emit = defineEmits(["select"]);

const handleSelect = (key) => {
  emit("select", key);
};
</script>

<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';

.menu-section {
  width: 100%;

  .section-label {
    padding: pxToRem(10) pxToRem(16);
    box-sizing: border-box;

    .label-text {
      font-size: pxToRem(10);
      font-family: "Alibaba PuHuiTi-Regular";
      font-weight: 400;
      letter-spacing: pxToRem(0.5);
      line-height: pxToRem(15);
      text-transform: uppercase;
      color: rgba(148, 163, 184, 1);
      margin: 0;
    }
  }

  .section-menu {
    border: none;
    padding: 0 pxToRem(8);
    height: 100%;
    display: flex;
    flex-direction: column;

    .menu-item {
      display: flex;
      align-items: center;
      height: pxToRem(44);
      margin-bottom: pxToRem(10);
      border-radius: pxToRem(4);
      transition: background-color 0.2s;

      &:hover {
        background-color: rgba(244, 246, 255, 1) !important;
      }

      .menu-icon {
        width: pxToRem(30);
        height: pxToRem(20);
        margin-right: pxToRem(12);
        flex-shrink: 0;
      }

      .menu-text {
        font-size: pxToRem(12);
        font-family: "Alibaba PuHuiTi-Regular";
        font-weight: 400;
        letter-spacing: pxToRem(0.6);
        line-height: pxToRem(16);
        text-transform: uppercase;
        color: rgba(100, 116, 139, 1);
      }
    }
  }
}
</style>
