<template>
  <div class="task-card">
    <div class="card-header">
      <div class="header-left">
        <p class="title-text">{{ title }}</p>
      </div>
      <div class="header-right">
        <div class="year-badge">
          <p class="year-text">{{ timeTag }}</p>
        </div>
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
            <div
              class="task-category"
              :style="{ backgroundColor: getCategoryColor(task.category) }"
            >
              <p class="category-text">{{ task.category }}</p>
            </div>
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
import { computed } from 'vue';

const props = defineProps({
  title: {
    type: String,
    default: '年度任务',
  },
  type: {
    type: String,
    default: 'year',
  },
  tasks: {
    type: Array,
    default: () => [],
  },
});

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
</script>

<style scoped lang="scss">
.task-card {
  width: 100%;
  height: 19.375rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;

  .card-header {
    width: 100%;
    height: 1.25rem;
    position: relative;
    flex-shrink: 0;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    .header-left {
      .title-text {
        font-size: 0.875rem;
        font-family: 'Alibaba PuHuiTi-Regular';
        font-weight: 400;
        display: flex;
        flex-direction: column;
        justify-content: center;
        letter-spacing: 0.0875rem;
        line-height: 1.25rem;
        text-transform: uppercase;
        color: rgba(32, 48, 68, 1);
        margin: 0;
      }
    }

    .header-right {
      .year-badge {
        width: 3.5rem;
        height: 1.1875rem;
        position: relative;
        flex-shrink: 0;
        display: flex;
        flex-direction: column;
        padding: 0.125rem 0.5rem 0.125rem 0.5rem;
        border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
        background-color: rgba(210, 228, 255, 1);

        .year-text {
          font-size: 0.625rem;
          font-family: 'Inter-Semi Bold';
          font-weight: 700;
          display: flex;
          justify-content: center;
          line-height: 0.9375rem;
          color: rgba(74, 64, 224, 1);
          margin: 0;
        }
      }
    }
  }

  .tasks-container {
    width: 100%;
    height: 17.5rem;
    position: relative;
    flex-shrink: 0;
    display: flex;
    flex-direction: column;
    gap: 0.625rem;
    justify-content: flex-start;
    align-items: center;

    .task-item {
      width: 100%;
      height: 3.75rem;
      overflow: hidden;
      position: relative;
      flex-shrink: 0;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      padding: 1rem 1rem 1rem 1rem;
      border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
      box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
      background-color: rgba(255, 255, 255, 1);
      border-left: 0.25rem solid;

      .task-content {
        width: calc(100% - 2rem);
        height: 2.6875rem;
        position: relative;
        flex-shrink: 0;
        display: flex;
        flex-direction: column;
        gap: 0.5rem;

        .task-header {
          width: 100%;
          height: 1.25rem;
          position: relative;
          flex-shrink: 0;
          display: flex;
          flex-direction: row;
          justify-content: space-between;
          align-items: center;

          .task-title {
            font-size: 0.875rem;
            font-family: 'Alibaba PuHuiTi-Regular';
            font-weight: 400;
            display: flex;
            flex-direction: column;
            justify-content: center;
            line-height: 1.25rem;
            color: rgba(32, 48, 68, 1);
            margin: 0;
          }

          .task-category {
            width: 2.25rem;
            height: 1.1875rem;
            position: relative;
            flex-shrink: 0;
            display: flex;
            flex-direction: column;
            padding: 0.125rem 0.5rem 0.125rem 0.5rem;
            border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;

            .category-text {
              font-size: 0.625rem;
              font-family: 'Alibaba PuHuiTi-Regular';
              font-weight: 400;
              display: flex;
              flex-direction: column;
              justify-content: center;
              line-height: 0.9375rem;
              text-transform: uppercase;
              color: rgba(20, 0, 126, 1);
              margin: 0;
            }
          }
        }

        .task-progress {
          width: 100%;
          height: 0.9375rem;
          position: relative;
          flex-shrink: 0;
          display: flex;
          flex-direction: row;
          gap: 0.75rem;
          align-items: center;

          .progress-bar {
            flex: 1;
            height: 0.375rem;
            overflow: hidden;
            position: relative;
            flex-shrink: 0;
            border-radius: 624.9375rem 624.9375rem 624.9375rem 624.9375rem;
            background-color: rgba(220, 233, 255, 1);

            .progress-fill {
              height: 100%;
              position: absolute;
              left: 0;
              top: 0;
              bottom: 0;
            }
          }

          .progress-percentage {
            width: 1.365rem;
            height: 0.9375rem;
            position: relative;
            flex-shrink: 0;
            display: flex;
            flex-direction: column;

            .percentage-text {
              font-size: 0.625rem;
              font-family: 'Inter-Semi Bold';
              font-weight: 700;
              display: flex;
              flex-direction: column;
              justify-content: center;
              line-height: 0.9375rem;
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
