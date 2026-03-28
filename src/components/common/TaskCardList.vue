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
@import '@/assets/styles/_mixins.scss';
.task-card {
  width: 100%;
  height: pxToRem(310);
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: pxToRem(16);

  .card-header {
    width: 100%;
    height: pxToRem(20);
    position: relative;
    flex-shrink: 0;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    align-items: center;

    .header-left {
      .title-text {
        font-size: pxToRem(14);
        font-family: 'Alibaba PuHuiTi-Regular';
        font-weight: 400;
        display: flex;
        flex-direction: column;
        justify-content: center;
        letter-spacing: pxToRem(1.4);
        line-height: pxToRem(20);
        text-transform: uppercase;
        color: rgba(32, 48, 68, 1);
        margin: 0;
      }
    }

    .header-right {
      .year-badge {
        width: pxToRem(56);
        height: pxToRem(19);
        position: relative;
        flex-shrink: 0;
        display: flex;
        flex-direction: column;
        padding: pxToRem(2) pxToRem(8);
        border-radius: pxToRem(9999);
        background-color: rgba(210, 228, 255, 1);

        .year-text {
          font-size: pxToRem(10);
          font-family: 'Inter-Semi Bold';
          font-weight: 700;
          display: flex;
          justify-content: center;
          line-height: pxToRem(15);
          color: rgba(74, 64, 224, 1);
          margin: 0;
        }
      }
    }
  }

  .tasks-container {
    width: 100%;
    height: pxToRem(280);
    position: relative;
    flex-shrink: 0;
    display: flex;
    flex-direction: column;
    gap: pxToRem(10);
    justify-content: flex-start;
    align-items: center;

    .task-item {
      width: 100%;
      height: pxToRem(60);
      overflow: hidden;
      position: relative;
      flex-shrink: 0;
      display: flex;
      flex-direction: column;
      justify-content: center;
      align-items: center;
      padding: pxToRem(16);
      border-radius: pxToRem(8);
      box-shadow: 0 pxToRem(1) pxToRem(2) 0 rgba(0, 0, 0, 0.05);
      background-color: rgba(255, 255, 255, 1);
      border-left: pxToRem(4) solid;

      .task-content {
        width: calc(100% - pxToRem(32));
        height: pxToRem(43);
        position: relative;
        flex-shrink: 0;
        display: flex;
        flex-direction: column;
        gap: pxToRem(8);

        .task-header {
          width: 100%;
          height: pxToRem(20);
          position: relative;
          flex-shrink: 0;
          display: flex;
          flex-direction: row;
          justify-content: space-between;
          align-items: center;

          .task-title {
            font-size: pxToRem(14);
            font-family: 'Alibaba PuHuiTi-Regular';
            font-weight: 400;
            display: flex;
            flex-direction: column;
            justify-content: center;
            line-height: pxToRem(20);
            color: rgba(32, 48, 68, 1);
            margin: 0;
          }

          .task-category {
            width: pxToRem(36);
            height: pxToRem(19);
            position: relative;
            flex-shrink: 0;
            display: flex;
            flex-direction: column;
            padding: pxToRem(2) pxToRem(8);
            border-radius: pxToRem(9999);

            .category-text {
              font-size: pxToRem(10);
              font-family: 'Alibaba PuHuiTi-Regular';
              font-weight: 400;
              display: flex;
              flex-direction: column;
              justify-content: center;
              line-height: pxToRem(15);
              text-transform: uppercase;
              color: rgba(20, 0, 126, 1);
              margin: 0;
            }
          }
        }

        .task-progress {
          width: 100%;
          height: pxToRem(15);
          position: relative;
          flex-shrink: 0;
          display: flex;
          flex-direction: row;
          gap: pxToRem(12);
          align-items: center;

          .progress-bar {
            flex: 1;
            height: pxToRem(6);
            overflow: hidden;
            position: relative;
            flex-shrink: 0;
            border-radius: pxToRem(9999);
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
            width: pxToRem(21.84);
            height: pxToRem(15);
            position: relative;
            flex-shrink: 0;
            display: flex;
            flex-direction: column;

            .percentage-text {
              font-size: pxToRem(10);
              font-family: 'Inter-Semi Bold';
              font-weight: 700;
              display: flex;
              flex-direction: column;
              justify-content: center;
              line-height: pxToRem(15);
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
