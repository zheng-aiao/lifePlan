<template>
  <div class="task-card">
    <div class="card-header">
      <div class="header-left">
        <p class="title-text">{{ title }}</p>
      </div>
      <div class="header-right">
        <BaseTag>{{ timeTag }}</BaseTag>
      </div>
    </div>
    <div class="tasks-container">
      <TaskItemCard v-for="task in tasks" :key="task.id" :task="task" />
    </div>
  </div>
</template>

<script setup>
import { computed, ref, onMounted, watch, onUnmounted } from 'vue';
import bizService from '@/utils/bizService';
import BaseTag from '@/components/common/BaseTag.vue';
import TaskItemCard from '@/views/dayView/component/TaskItemCard.vue';
import eventBus from '@/utils/eventBus';

const props = defineProps({
  title: {
    type: String,
    default: '年度任务',
  },
  type: {
    type: String,
    default: 'year',
  },
  date: {
    type: String,
    required: true,
  },
});

// 任务数据
const tasks = ref([]);

// 任务类型映射
const taskTypeMap = {
  year: 1,
  month: 2,
  day: 3,
};

// 加载指定类型的任务数据
const loadTasksByType = async () => {
  const taskType = taskTypeMap[props.type];
  if (!taskType) return;

  try {
    let response;
    // 日任务（taskType=3）获取未完成的任务
    if (taskType === 3) {
      response = await bizService.task.getIncompleteDailyTasks();
    } else {
      // 年任务和月任务使用原有接口
      response = await bizService.task.getTasksByTypeAndDate(taskType, props.date);
    }

    if (response.data && response.data) {
      tasks.value = response.data.map((task) => ({
        id: task.id,
        title: task.title,
        category: task.category,
        progress: task.taskProgress,
        plannedStartTime: task.plannedStartTime,
        plannedEndTime: task.plannedEndTime,
        taskType: task.taskType,
        color: getCategoryColor(task.category),
      }));
    }
  } catch (error) {
    console.error(`加载${props.type}任务失败:`, error);
  }
};

const timeTag = computed(() => {
  const now = new Date();
  const year = now.getFullYear();
  const month = now.getMonth() + 1;
  const week = Math.ceil(now.getDate() / 7);

  switch (props.type) {
    case 'year':
      return `${year}年`;
    case 'month':
      return `${month}月`;
    default:
      return `第${week}周`;
  }
});

const getCategoryColor = (category) => {
  switch (category) {
    case '工作':
      return 'rgba(151, 149, 255, 1)';
    case '学习':
      return 'rgba(248, 160, 16, 1)';
    case '健身':
      return 'rgba(105, 246, 184, 1)';
    default:
      return 'rgba(151, 149, 255, 1)';
  }
};

// 组件挂载时加载任务数据
onMounted(() => {
  loadTasksByType();

  // 监听任务创建成功事件，重新加载任务数据
  eventBus.on('taskCreated', loadTasksByType);
});

// 监听日期变化，重新加载任务数据
watch(
  () => props.date,
  () => {
    loadTasksByType();
  }
);

// 组件卸载时移除事件监听
onUnmounted(() => {
  eventBus.off('taskCreated', loadTasksByType);
});
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;

.task-card {
  flex: 1;
  @include flexCenter(flex-start, center, true);
  gap: pxToRem(20);
  @include wh(100%, pxToRem(310));
  padding: pxToRem(20) pxToRem(16);
  background-color: rgba(248, 250, 252, 1);
  border-radius: pxToRem(8);

  .card-header {
    @include wh(100%, pxToRem(20));
    @include flexCenter(space-between, center);
    position: relative;
    flex-shrink: 0;

    .header-left {
      .title-text {
        @include fontStyle(4);
        @include oneLineTextHidden;
        color: var(--textColor-1);
      }
    }
  }

  .tasks-container {
    @include wh(100%, pxToRem(240));
    @include flexCenter(flex-start, center, true);
    gap: pxToRem(15);
    position: relative;
    flex-shrink: 0;
    overflow: auto;
    @include scrollBarStyle(var(--violet));
  }
}
</style>
