<template>
  <div class="task-card">
    <div class="tasks-container">
      <TaskItemCard v-for="task in tasks" :key="task.id" :task="task" />
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, watch, onUnmounted } from 'vue';
import bizService from '@/utils/bizService';
import TaskItemCard from '@/views/dayView/component/TaskItemCard.vue';
import eventBus from '@/utils/eventBus';

const props = defineProps({
  type: {
    type: String,
    default: '',
  },
  date: {
    type: String,
    required: true,
  },
});

// 任务数据
const tasks = ref([]);

// 加载指定类型的任务数据
const loadTasksByType = async () => {
  if (!props.type) return;

  try {
    let response;
    // 日任务（taskType=3）获取未完成的任务
    if (props.type === 4) {
      response = await bizService.task.getIncompleteDailyTasks();
    } else {
      // 年任务和月任务使用原有接口
      response = await bizService.task.getTasksByTypeAndDate(props.type, props.date);
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
        taskStatus: task.taskStatus,
        color: getCategoryColor(task.category),
      }));
    }
  } catch (error) {
    console.error(`加载${props.type}任务失败:`, error);
  }
};

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
  @include wh(100%);
  @include flexCenter;
  background-color: rgba(248, 250, 252, 1);
  border-radius: 0 0 pxToRem(8) pxToRem(8);
  padding: pxToRem(20) pxToRem(16);

  .tasks-container {
    @include wh(100%);
    @include flexCenter(flex-start, center, true);
    gap: pxToRem(15);
    overflow-y: auto;
    @include scrollBarStyle(var(--violet));
    min-height: 0;
  }
}
</style>
