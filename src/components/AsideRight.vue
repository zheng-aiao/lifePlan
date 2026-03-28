<template>
  <el-aside class="aside-right-wrapper">
    <el-card class="recent-highlights-card">
      <template #header>
        <div class="highlights-header">
          <p class="highlights-title">{{ "Recent Highlights" }}</p>
        </div>
      </template>
      <div class="highlights-container">
        <el-row
          v-for="(item, index) in highlights"
          :key="index"
          class="highlight-item"
        >
          <el-col
            :span="6"
            class="highlight-overlay"
            :style="{ backgroundColor: item.bgColor }"
          >
            <img :src="item.icon" class="overlay-icon" />
          </el-col>
          <el-col :span="18" class="highlight-content">
            <div class="content-title">
              <p class="title-text">{{ item.title }}</p>
            </div>
            <div class="content-desc">
              <p class="desc-text">{{ item.description }}</p>
            </div>
          </el-col>
        </el-row>
      </div>
    </el-card>
  </el-aside>
</template>

<script setup>
import { ref } from "vue";

const highlights = ref([
  {
    title: "这是你三月以来的最佳表现",
    description: "完成 30 小时深度办公",
    bgColor: "rgba(248, 160, 16, 0.3)",
    icon: new URL("@/assets/images/Frame_1_195.png", import.meta.url).href,
  },
  {
    title: '解锁"三月之星"',
    description: "完成 30 小时深度办公",
    bgColor: "rgba(248, 160, 16, 0.3)",
    icon: new URL("@/assets/images/Frame_1_195.png", import.meta.url).href,
  },
]);
</script>

<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';

.aside-right-wrapper {
  width: var(--aside-right-width);
  height: 100% !important;
  max-height: 100%;
  border-left: pxToRem(1) solid rgba(158, 174, 199, 0.1);
  background-color: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(pxToRem(24));
  padding: var(--content-padding);
  box-sizing: border-box;
  flex-shrink: 0;

  .recent-highlights-card {
    width: 100%;
    border-radius: pxToRem(16);
    box-shadow: none;

    .highlights-header {
      width: 100%;
      padding-bottom: pxToRem(8);

      .highlights-title {
        font-size: pxToRem(14);
        font-family: "Alibaba PuHuiTi-Regular";
        font-weight: 400;
        letter-spacing: pxToRem(1.4);
        line-height: pxToRem(20);
        text-transform: uppercase;
        color: rgba(32, 48, 68, 1);
        margin: 0;
      }
    }

    .highlights-container {
      width: 100%;
      display: flex;
      flex-direction: column;
      gap: pxToRem(16);

      .highlight-item {
        width: 100%;
        height: pxToRem(48);
        align-items: center;

        .highlight-overlay {
          width: pxToRem(48);
          height: pxToRem(48);
          display: flex;
          justify-content: center;
          align-items: center;
          border-radius: pxToRem(8);
          flex-shrink: 0;

          .overlay-icon {
            width: pxToRem(18);
            height: pxToRem(18);
          }
        }

        .highlight-content {
          flex: 1;
          display: flex;
          flex-direction: column;
          gap: pxToRem(1);
          padding-left: pxToRem(16);

          .content-title {
            width: 100%;

            .title-text {
              font-size: pxToRem(12);
              font-family: "Alibaba PuHuiTi-Regular";
              font-weight: 400;
              line-height: pxToRem(16);
              color: rgba(32, 48, 68, 1);
              margin: 0;
            }
          }

          .content-desc {
            width: 100%;

            .desc-text {
              font-size: pxToRem(10);
              font-family: "Alibaba PuHuiTi-Regular";
              font-weight: 400;
              line-height: pxToRem(15);
              color: rgba(77, 93, 115, 1);
              margin: 0;
            }
          }
        }
      }
    }
  }
}

:deep(.el-card__body) {
  padding: pxToRem(16);
}
</style>
