<template>
  <div class="aside-left">
    <div v-for="(category, index) in categories" :key="category.type" class="task-category-section">
      <div class="section-header" @click="toggleSection(index)">
        <div class="section-title">
          <span>{{ category.title }}</span>
        </div>
        <div class="section-info">
          <BaseTag>{{ getTimeTag(category.type) }}</BaseTag>
          <BaseButton
            class="section-toggle"
            :type="expandedSections[index] ? 'bottom' : 'top'"
            iconBtn
            @click.stop="handleAddSubTask"
          />
        </div>
      </div>
      <div v-if="expandedSections[index]" class="section-content">
        <TaskCardList :type="category.type" :date="currentDate" />
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted, onUnmounted } from 'vue';
import BaseButton from '@/components/common/BaseButton.vue';
import TaskCardList from '@/views/dayView/component/TaskCardList.vue';
import BaseTag from '@/components/common/BaseTag.vue';
import eventBus from '@/utils/eventBus';

// 任务组别配置
const categories = ref([
  { title: '年度任务', type: 1 },
  { title: '月度任务', type: 2 },
  { title: '未完成日任务', type: 4 },
]);

// 展开状态管理
const expandedSections = ref(
  categories.value.map((_, index) => index === categories.value.length - 1)
); // 默认只展开最后一个（未完成日任务）

// 当前日期
const currentDate = ref(new Date().toISOString().split('T')[0]);

// 切换展开/折叠状态
const toggleSection = (index) => {
  // 实现同一时刻只有一个任务列表能打开
  expandedSections.value = expandedSections.value.map(
    (_, i) => i === index && !expandedSections.value[i]
  );
};

// 处理任务创建成功事件
const handleTaskCreated = (date) => {
  if (date) {
    currentDate.value = date;
  }
};

// 生成时间标签
const getTimeTag = (type) => {
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const week = Math.ceil(now.getDate() / 7);

  switch (type) {
    case 1:
      return `${year}年`;
    case 2:
      return `${month}月`;
    case 4:
      return `第${week}周`;
  }
};

// 组件挂载时注册事件监听
onMounted(() => {
  eventBus.on('taskCreated', handleTaskCreated);
});

// 组件卸载时移除事件监听
onUnmounted(() => {
  eventBus.off('taskCreated', handleTaskCreated);
});
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.aside-left {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: pxToRem(16);
  padding: pxToRem(20) pxToRem(16);
  background-color: rgba(244, 246, 255, 1);
  height: 100%;
  overflow: hidden;
}

.task-category-section {
  width: 100%;
  display: flex;
  flex-direction: column;
  min-height: 0;

  // 只有展开的任务列表才占据剩余空间
  &:has(.section-content) {
    flex: 1;
  }
}

.section-header {
  @include flexCenter(space-between, center);
  padding: pxToRem(10) pxToRem(16);
  background-color: rgba(255, 255, 255, 1);
  border-radius: pxToRem(8);
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 pxToRem(1) pxToRem(2) 0 rgba(0, 0, 0, 0.05);

  &:hover {
    background-color: rgba(240, 242, 255, 1);
  }

  .section-title {
    span {
      @include fontStyle(4);
      color: var(--textColor-1);
      font-weight: 500;
    }
  }

  .section-info {
    @include flexCenter(center, center);
    gap: pxToRem(10);
  }
}

.section-content {
  flex: 1;
  width: 100%;
  overflow-y: auto;
  animation: slideDown 0.3s ease;
  @include scrollBarStyle(var(--violet));
  min-height: 0;
}

@keyframes slideDown {
  from {
    opacity: 0;
    transform: translateY(-10px);
  }
  to {
    opacity: 1;
    transform: translateY(0);
  }
}
</style>
