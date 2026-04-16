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
      <div
        v-for="task in tasks"
        :key="task.id"
        class="task-item"
        :style="{ borderLeftColor: task.color }"
      >
        <div class="task-content">
          <div class="task-header">
            <p class="task-title">{{ task.title }}</p>
            <BaseTag>{{ task.category }}</BaseTag>
          </div>
          <div class="task-progress">
            <div class="progress-bar">
              <div
                class="progress-fill"
                :style="{
                  width: task.progress + '%',
                  backgroundColor: task.color,
                }"
              ></div>
            </div>
            <div class="progress-percentage">
              <p class="percentage-text">{{ task.progress }}%</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, ref, onMounted, watch, onUnmounted } from 'vue';
import bizService from '@/utils/bizService';
import BaseTag from '@/components/common/BaseTag.vue';
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
    const response = await bizService.task.getTasksByTypeAndDate(taskType, props.date);
    if (response.data && response.data) {
      tasks.value = response.data.map((task) => ({
        id: task.id,
        title: task.title,
        category: task.category,
        progress: task.taskProgress,
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
        @include fontStyle(5);
        font-family: 'Alibaba PuHuiTi-Regular';
        letter-spacing: pxToRem(1.4);
        text-transform: uppercase;
        color: rgba(32, 48, 68, 1);
        margin: 0;
        @include oneLineTextHidden;
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
    @include scrollBarStyle();

    .task-item {
      @include wh(100%, pxToRem(70));
      @include flexCenter(center, center, true);
      padding: pxToRem(16);
      border-radius: pxToRem(8);
      box-shadow: 0 pxToRem(1) pxToRem(2) 0 rgba(0, 0, 0, 0.05);
      background-color: rgba(255, 255, 255, 1);
      border-left: pxToRem(4) solid;
      position: relative;
      flex-shrink: 0;
      overflow: hidden;

      .task-content {
        @include wh(calc(100% - pxToRem(32)), pxToRem(43));
        @include flexCenter(flex-start, center, true);
        gap: pxToRem(8);
        position: relative;
        flex-shrink: 0;

        .task-header {
          @include wh(100%, pxToRem(20));
          @include flexCenter(space-between, center);
          position: relative;
          flex-shrink: 0;

          .task-title {
            @include fontStyle(5);
            font-family: 'Alibaba PuHuiTi-Regular';
            color: rgba(32, 48, 68, 1);
            margin: 0;
            flex: 1;
            @include oneLineTextHidden;
          }
        }

        .task-progress {
          @include wh(100%, pxToRem(15));
          @include flexCenter(flex-start, center);
          gap: pxToRem(12);
          position: relative;
          flex-shrink: 0;

          .progress-bar {
            flex: 1;
            @include whrem(0, 6);
            border-radius: pxToRem(9999);
            background-color: rgba(220, 233, 255, 1);
            position: relative;
            flex-shrink: 0;
            overflow: hidden;

            .progress-fill {
              @include wh(100%, 100%);
              position: absolute;
              left: 0;
              top: 0;
            }
          }

          .progress-percentage {
            @include whrem(22, 15);
            @include flexCenter;
            position: relative;
            flex-shrink: 0;

            .percentage-text {
              font-size: pxToRem(10);
              font-family: 'Inter-Semi Bold';
              font-weight: 700;
              color: rgba(104, 120, 143, 1);
              margin: 0;
            }
          }
        }
      }
    }
  }
}
</style>
