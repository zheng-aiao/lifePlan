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
        <BaseButton
          type="icon-text"
          text="新建任务"
          icon="@/assets/images/Frame_1_59.png"
          @click="handleNewTask"
        />
      </div>
    </div>

    <NewTaskDialog v-model="showNewTaskDialog" @success="handleTaskCreated" />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import MenuSection from '@/layout/component/MenuSection.vue';
import BaseButton from '@/components/common/BaseButton.vue';
import NewTaskDialog from '@/layout/dialog/NewTaskDialog.vue';
import eventBus from '@/utils/eventBus';

const emit = defineEmits(['viewChange', 'categoryClick', 'newTask', 'taskCreated']);

const activeView = ref('1');
const activeCategory = ref('');
const showNewTaskDialog = ref(false);

const routeData = ref([
  {
    id: 1,
    name: '今日视图',
    icon: new URL('@/assets/images/Frame_1_11.png', import.meta.url).href,
  },
  {
    id: 2,
    name: '月度计划',
    icon: new URL('@/assets/images/Frame_1_17.png', import.meta.url).href,
  },
  {
    id: 3,
    name: '年度回顾',
    icon: new URL('@/assets/images/Frame_1_23.png', import.meta.url).href,
  },
]);

const categories = ref([
  {
    id: 1,
    name: '健身',
    icon: new URL('@/assets/images/Frame_1_45.png', import.meta.url).href,
  },
  {
    id: 2,
    name: '学习',
    icon: new URL('@/assets/images/Frame_1_39.png', import.meta.url).href,
  },
  {
    id: 3,
    name: '工作',
    icon: new URL('@/assets/images/Frame_1_33.png', import.meta.url).href,
  },
  {
    id: 4,
    name: '写作',
    icon: new URL('@/assets/images/Frame_1_51.png', import.meta.url).href,
  },
]);

const handleViewSelect = (key) => {
  activeView.value = key;
  emit('viewChange', key);
};

const handleCategorySelect = (key) => {
  activeCategory.value = key;
  const category = categories.value.find((cat) => cat.id === parseInt(key));
  if (category) {
    emit('categoryClick', category);
  }
};

const handleNewTask = () => {
  showNewTaskDialog.value = true;
  emit('newTask');
};

const handleTaskCreated = (taskData) => {
  console.log('任务创建成功:', taskData);
  emit('taskCreated', taskData);
  // 使用事件总线通知dayView组件刷新数据
  eventBus.emit('taskCreated', taskData);
};
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;
.aside-left-wrapper {
  width: var(--aside-left-width);
  height: 100%;
  min-height: 100%;
  border-right: pxToRem(1) solid rgba(158, 174, 199, 0.15);
  box-sizing: border-box;
  display: flex;
  flex-direction: column;

  .aside-left-content {
    width: 100%;
    flex: 1;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    gap: pxToRem(8);
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
