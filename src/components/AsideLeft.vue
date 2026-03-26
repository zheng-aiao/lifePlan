<template>
  <el-aside class="aside-left-wrapper">
    <div class="aside-left-content">
      <div class="view-control">
        <p class="view-control-text">{{ "视图控制" }}</p>
      </div>
      <el-menu
        :default-active="activeView"
        class="view-options-menu"
        @select="handleViewSelect"
      >
        <el-menu-item index="today" class="menu-item today-item">
          <img
            src="@/assets/images/Frame_1_11.png"
            class="menu-icon today-icon"
          />
          <span class="menu-text today-text">{{ "今日" }}</span>
        </el-menu-item>
        <el-menu-item index="month" class="menu-item">
          <img
            src="@/assets/images/Frame_1_17.png"
            class="menu-icon month-icon"
          />
          <span class="menu-text">{{ "本月" }}</span>
        </el-menu-item>
        <el-menu-item index="year" class="menu-item">
          <img
            src="@/assets/images/Frame_1_23.png"
            class="menu-icon year-icon"
          />
          <span class="menu-text">{{ "年度" }}</span>
        </el-menu-item>
      </el-menu>

      <div class="category-label">
        <p class="category-label-text">{{ "分类标签" }}</p>
      </div>
      <el-menu class="category-menu" @select="handleCategorySelect">
        <el-menu-item
          v-for="category in categories"
          :key="category.id"
          :index="category.id.toString()"
          class="menu-item category-item"
        >
          <img :src="category.icon" class="menu-icon category-icon" />
          <span class="menu-text">{{ category.name }}</span>
        </el-menu-item>
      </el-menu>

      <div class="new-task-section">
        <el-button type="primary" class="new-task-btn" @click="handleNewTask">
          <img src="@/assets/images/Frame_1_59.png" class="btn-icon" />
          <span class="btn-text">{{ "新建任务" }}</span>
        </el-button>
      </div>
    </div>
  </el-aside>
</template>

<script setup>
import { ref } from "vue";

const emit = defineEmits(["viewChange", "categoryClick", "newTask"]);

const activeView = ref("today");

const categories = ref([
  {
    id: 1,
    name: "健身",
    icon: new URL("@/assets/images/Frame_1_45.png", import.meta.url).href,
  },
  {
    id: 2,
    name: "学习",
    icon: new URL("@/assets/images/Frame_1_39.png", import.meta.url).href,
  },
  {
    id: 3,
    name: "工作",
    icon: new URL("@/assets/images/Frame_1_33.png", import.meta.url).href,
  },
  {
    id: 4,
    name: "写作",
    icon: new URL("@/assets/images/Frame_1_51.png", import.meta.url).href,
  },
]);

const handleViewSelect = (key) => {
  activeView.value = key;
  emit("viewChange", key);
};

const handleCategorySelect = (key) => {
  const category = categories.value.find((cat) => cat.id === parseInt(key));
  if (category) {
    emit("categoryClick", category);
  }
};

const handleNewTask = () => {
  emit("newTask");
};
</script>

<style scoped>
.aside-left-wrapper {
  width: var(--aside-left-width);
  height: 100%;
  background-color: rgba(255, 255, 255, 1);
  border-right: 0.0625rem solid rgba(158, 174, 199, 0.15);
  box-sizing: border-box;
  flex-shrink: 0;
}

.aside-left-content {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: var(--content-padding);
  box-sizing: border-box;
}

.view-control {
  padding: 0.625rem 1rem;
  box-sizing: border-box;
}

.view-control-text {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  letter-spacing: 0.0313rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(148, 163, 184, 1);
  margin: 0;
}

.view-options-menu {
  border: none;
  padding: 0 0.5rem 2rem 0.5rem;
}

.category-menu {
  border: none;
  padding: 0 0.5rem;
}

.menu-item {
  display: flex;
  align-items: center;
  height: 2.75rem;
  margin-bottom: 0.625rem;
  border-radius: 0.25rem;
  transition: background-color 0.2s;
}

.menu-item:hover {
  background-color: rgba(244, 246, 255, 1) !important;
}

.today-item {
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1) !important;
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

.today-text {
  color: rgba(79, 70, 229, 1);
}

.category-label {
  padding: 0.625rem 1rem;
  box-sizing: border-box;
}

.category-label-text {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  letter-spacing: 0.0313rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(148, 163, 184, 1);
  margin: 0;
}

.new-task-section {
  margin-top: auto;
  padding: 0.625rem 1rem;
  display: flex;
  justify-content: center;
}

.new-task-btn {
  width: 100%;
  height: 3.125rem;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.5rem;
  background-color: rgba(74, 64, 224, 1);
  border: none;
  border-radius: 0.5rem;
  transition:
    transform 0.2s,
    box-shadow 0.2s;
}

.new-task-btn:hover {
  transform: translateY(-1px);
  box-shadow:
    0 0.625rem 0.9375rem -0.1875rem rgba(74, 64, 224, 0.3),
    0 0.25rem 0.375rem -0.25rem rgba(74, 64, 224, 0.3);
  background-color: rgba(74, 64, 224, 0.9);
}

.btn-icon {
  width: 0.5104rem;
  height: 0.5104rem;
  flex-shrink: 0;
}

.btn-text {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  letter-spacing: 0.075rem;
  line-height: 1rem;
  text-transform: uppercase;
  color: rgba(244, 241, 255, 1);
}

:deep(.el-menu-item) {
  height: 2.75rem;
  line-height: 2.75rem;
  margin-bottom: 0.625rem;
  border-radius: 0.25rem;
}

:deep(.el-menu-item.is-active) {
  background-color: rgba(255, 255, 255, 1) !important;
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
}

:deep(.el-menu-item.is-active .menu-text) {
  color: rgba(79, 70, 229, 1);
}
</style>
