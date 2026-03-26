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
.menu-section {
  width: 100%;

  .section-label {
    padding: 0.625rem 1rem;
    box-sizing: border-box;

    .label-text {
      font-size: 0.625rem;
      font-family: "Alibaba PuHuiTi-Regular";
      font-weight: 400;
      letter-spacing: 0.0313rem;
      line-height: 0.9375rem;
      text-transform: uppercase;
      color: rgba(148, 163, 184, 1);
      margin: 0;
    }
  }

  .section-menu {
    border: none;
    padding: 0 0.5rem;
    height: 100%;
    display: flex;
    flex-direction: column;

    .menu-item {
      display: flex;
      align-items: center;
      height: 2.75rem;
      margin-bottom: 0.625rem;
      border-radius: 0.25rem;
      transition: background-color 0.2s;

      &:hover {
        background-color: rgba(244, 246, 255, 1) !important;
      }

      .menu-icon {
        width: 1.875rem;
        height: 1.25rem;
        margin-right: 0.75rem;
        flex-shrink: 0;
      }

      .menu-text {
        font-size: 0.75rem;
        font-family: "Alibaba PuHuiTi-Regular";
        font-weight: 400;
        letter-spacing: 0.0375rem;
        line-height: 1rem;
        text-transform: uppercase;
        color: rgba(100, 116, 139, 1);
      }
    }
  }
}
</style>
