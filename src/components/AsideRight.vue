<template>
  <div class="aside-right-wrapper">
    <div class="recent-highlights">
      <div class="highlights-header">
        <p class="highlights-title">{{ "Recent Highlights" }}</p>
      </div>
      <div class="highlights-container">
        <div class="highlight-item" v-for="(item, index) in highlights" :key="index">
          <div class="highlight-overlay" :style="{ backgroundColor: item.bgColor }">
            <div class="overlay-container">
              <div class="overlay-icon" :style="{ backgroundImage: `url(${item.icon})` }"></div>
            </div>
          </div>
          <div class="highlight-content">
            <div class="content-title">
              <p class="title-text">{{ item.title }}</p>
            </div>
            <div class="content-desc">
              <p class="desc-text">{{ item.description }}</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const highlights = ref([
  {
    title: '这是你三月以来的最佳表现',
    description: '完成 30 小时深度办公',
    bgColor: 'rgba(248, 160, 16, 0.3)',
    icon: new URL('@/assets/images/Frame_1_195.png', import.meta.url).href
  },
  {
    title: '解锁"三月之星"',
    description: '完成 30 小时深度办公',
    bgColor: 'rgba(248, 160, 16, 0.3)',
    icon: new URL('@/assets/images/Frame_1_195.png', import.meta.url).href
  }
])
</script>

<style scoped>
.aside-right-wrapper {
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1.25rem;
  align-items: center;
  padding: 1.25rem 1rem 1.25rem 1rem;
  width: 19.25rem;
  height: 63.5rem;
  box-sizing: border-box;
  border-left: 0.0625rem solid rgba(158, 174, 199, 0.1);
  background-color: rgba(255, 255, 255, 0.5);
  backdrop-filter: blur(1.5rem);
}

.recent-highlights {
  width: 17.1875rem;
  height: 18.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  padding: 1.25rem 1rem 1.25rem 1rem;
  border-radius: 1rem 1rem 1rem 1rem;
  background-color: rgba(255, 255, 255, 1);
  box-sizing: border-box;
}

.highlights-header {
  width: 100%;
  padding-bottom: 0.5rem;
}

.highlights-title {
  font-size: 0.875rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  letter-spacing: 0.0875rem;
  line-height: 1.25rem;
  text-transform: uppercase;
  color: rgba(32, 48, 68, 1);
  margin: 0;
}

.highlights-container {
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.highlight-item {
  width: 100%;
  height: 3rem;
  display: flex;
  flex-direction: row;
  gap: 1rem;
  align-items: center;
  box-sizing: border-box;
}

.highlight-overlay {
  width: 3rem;
  height: 3rem;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  flex-shrink: 0;
  box-sizing: border-box;
}

.overlay-container {
  width: 1.125rem;
  height: 1.125rem;
  display: flex;
  flex-direction: column;
}

.overlay-icon {
  width: 1.125rem;
  height: 1.125rem;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}

.highlight-content {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 0.0625rem;
}

.content-title {
  width: 100%;
}

.title-text {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  line-height: 1rem;
  color: rgba(32, 48, 68, 1);
  margin: 0;
}

.content-desc {
  width: 100%;
}

.desc-text {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  line-height: 0.9375rem;
  color: rgba(77, 93, 115, 1);
  margin: 0;
}
</style>
