<template>
  <div class="aside-left-wrapper">
    <div class="aside-left-content">
      <div class="menu-container">
        <MenuSection
          title="视图控制"
          :data="routeData"
          :active-index="activeView"
          @select="handleViewSelect"
          class="view-section"
        >
        </MenuSection>

        <MenuSection
          title="分类标签"
          :data="categories"
          :active-index="activeCategory"
          @select="handleCategorySelect"
          class="category-section"
        >
        </MenuSection>
      </div>

      <div class="button-container">
        <IconButton
          type="icon-text"
          text="新建任务"
          icon="@/assets/images/Frame_1_59.png"
          @click="handleNewTask"
        />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import MenuSection from "@/components/MenuSection.vue";
import IconButton from "@/components/IconButton.vue";

const emit = defineEmits(["viewChange", "categoryClick", "newTask"]);

const activeView = ref("1");
const activeCategory = ref("");

const routeData = ref([
  {
    id: 1,
    name: "今日视图",
    icon: new URL("@/assets/images/Frame_1_11.png", import.meta.url).href,
  },
  {
    id: 2,
    name: "月度计划",
    icon: new URL("@/assets/images/Frame_1_17.png", import.meta.url).href,
  },
  {
    id: 3,
    name: "年度回顾",
    icon: new URL("@/assets/images/Frame_1_23.png", import.meta.url).href,
  },
]);

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
  activeCategory.value = key;
  const category = categories.value.find((cat) => cat.id === parseInt(key));
  if (category) {
    emit("categoryClick", category);
  }
};

const handleNewTask = () => {
  emit("newTask");
};
</script>

<style scoped lang="scss">
.aside-left-wrapper {
  width: var(--aside-left-width);
  height: 100%;
  min-height: 100%;
  border-right: 0.0625rem solid rgba(158, 174, 199, 0.15);
  box-sizing: border-box;
  display: flex;
  flex-direction: column;

  .aside-left-content {
    width: 100%;
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    gap: 0.5rem;
    padding: var(--content-padding);
    box-sizing: border-box;
  }

  .button-container {
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
