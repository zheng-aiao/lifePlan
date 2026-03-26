<template>
  <div class="aside-left-wrapper">
    <div class="aside-left-content">
      <div class="view-control">
        <p class="view-control-text">{{ "视图控制" }}</p>
      </div>
      <div class="view-options">
        <div class="view-option-item">
          <div class="option-item today" @click="handleViewChange('today')">
            <div class="option-icon today-icon"></div>
            <div class="option-text">
              <p class="text-content today-text">{{ "今日" }}</p>
            </div>
          </div>
        </div>
        <div class="view-option-item">
          <div class="option-item" @click="handleViewChange('month')">
            <div class="option-icon month-icon"></div>
            <div class="option-text">
              <p class="text-content">{{ "本月" }}</p>
            </div>
          </div>
        </div>
        <div class="view-option-item">
          <div class="option-item" @click="handleViewChange('year')">
            <div class="option-icon year-icon"></div>
            <div class="option-text">
              <p class="text-content">{{ "年度" }}</p>
            </div>
          </div>
        </div>
      </div>
      <div class="category-label">
        <p class="category-label-text">{{ "分类标签" }}</p>
      </div>
      <div class="category-list">
        <div class="category-item" v-for="category in categories" :key="category.id" @click="handleCategoryClick(category)">
          <div class="category-icon" :style="{ backgroundImage: `url(${category.icon})` }"></div>
          <div class="category-text">
            <p class="category-name">{{ category.name }}</p>
          </div>
        </div>
      </div>
      <div class="new-task-section">
        <div class="new-task-btn" @click="handleNewTask">
          <div class="btn-overlay"></div>
          <div class="btn-icon"></div>
          <p class="btn-text">{{ "新建任务" }}</p>
        </div>
      </div>
    </div>
    <div class="aside-border"></div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

const emit = defineEmits(['viewChange', 'categoryClick', 'newTask'])

const categories = ref([
  { id: 1, name: '健身', icon: new URL('@/assets/images/Frame_1_45.png', import.meta.url).href },
  { id: 2, name: '学习', icon: new URL('@/assets/images/Frame_1_39.png', import.meta.url).href },
  { id: 3, name: '工作', icon: new URL('@/assets/images/Frame_1_33.png', import.meta.url).href },
  { id: 4, name: '写作', icon: new URL('@/assets/images/Frame_1_51.png', import.meta.url).href }
])

const handleViewChange = (view) => {
  emit('viewChange', view)
}

const handleCategoryClick = (category) => {
  emit('categoryClick', category)
}

const handleNewTask = () => {
  emit('newTask')
}
</script>

<style scoped>
.aside-left-wrapper {
  position: relative;
  width: 15rem;
  height: 63.5rem;
  display: flex;
  flex-shrink: 0;
  padding: 0 0.0625rem 0 0;
  box-sizing: border-box;
}

.aside-left-content {
  width: 100%;
  height: 100%;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
  padding: 1rem 1rem 1rem 1rem;
  background-color: rgba(255, 255, 255, 1);
  box-sizing: border-box;
}

.aside-border {
  position: absolute;
  inset: 0;
  border-radius: 0 0 0 0;
  border-width: 0 0.0625rem 0 0;
  border-style: solid;
  pointer-events: none;
  box-sizing: border-box;
  border-color: rgba(158, 174, 199, 0.15000000596046448);
}

.view-control {
  width: 12.5rem;
  height: 2.1875rem;
  overflow: hidden;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.625rem 1rem 0.625rem 1rem;
  box-sizing: border-box;
}

.view-control-text {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0313rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(148, 163, 184, 1);
  width: 10.5rem;
  height: auto;
  position: relative;
  flex-shrink: 0;
  flex-grow: 0;
  margin: 0;
}

.view-options {
  width: 12.5rem;
  height: 10.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0 2rem 0;
  box-sizing: border-box;
}

.view-option-item {
  width: 12.5rem;
  height: 2.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0.5rem 0 0.5rem;
  box-sizing: border-box;
}

.option-item {
  width: 11.5rem;
  height: 2.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0.75rem 1rem 0.75rem 1rem;
  border-radius: 0.25rem 0.25rem 0.25rem 0.25rem;
  box-sizing: border-box;
  cursor: pointer;
  transition: background-color 0.2s;
}

.option-item:hover {
  background-color: rgba(244, 246, 255, 1);
}

.option-item.today {
  box-shadow: 0 0.0625rem 0.125rem 0 rgba(0, 0, 0, 0.05);
  background-color: rgba(255, 255, 255, 1);
}

.option-icon {
  width: 1.875rem;
  height: 1.25rem;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0.75rem 0 0;
  box-sizing: border-box;
}

.today-icon {
  background-image: url(@/assets/images/Frame_1_11.png);
}

.month-icon {
  background-image: url(@/assets/images/Frame_1_17.png);
}

.year-icon {
  background-image: url(@/assets/images/Frame_1_23.png);
}

.option-text {
  width: 1.575rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.text-content {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0375rem;
  line-height: 1rem;
  text-transform: uppercase;
  color: rgba(100, 116, 139, 1);
  width: 1.575rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  margin: 0;
}

.today-text {
  color: rgba(79, 70, 229, 1);
}

.category-label {
  width: 12.5rem;
  height: 2.1875rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0.625rem 1rem 0.625rem 1rem;
  box-sizing: border-box;
}

.category-label-text {
  font-size: 0.625rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0313rem;
  line-height: 0.9375rem;
  text-transform: uppercase;
  color: rgba(148, 163, 184, 1);
  width: 10.5rem;
  height: auto;
  position: relative;
  flex-shrink: 0;
  flex-grow: 0;
  margin: 0;
}

.category-list {
  width: 12.5rem;
  height: 15rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  gap: 0.625rem;
}

.category-item {
  width: 12.5rem;
  height: 2.75rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0.75rem 1rem 0.75rem 1rem;
  border-radius: 0.25rem 0.25rem 0.25rem 0.25rem;
  box-sizing: border-box;
  cursor: pointer;
  transition: background-color 0.2s;
}

.category-item:hover {
  background-color: rgba(244, 246, 255, 1);
}

.category-icon {
  width: 2rem;
  height: 1.25rem;
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  padding: 0 0.75rem 0 0;
  box-sizing: border-box;
}

.category-text {
  width: 1.575rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
}

.category-name {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.0375rem;
  line-height: 1rem;
  text-transform: uppercase;
  color: rgba(100, 116, 139, 1);
  width: 1.575rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  margin: 0;
}

.new-task-section {
  width: 12.5rem;
  height: 5rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 0.625rem 1rem 0.625rem 1rem;
  box-sizing: border-box;
}

.new-task-btn {
  width: 11.25rem;
  height: 3.125rem;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
  justify-content: center;
  align-items: center;
  padding: 1rem 0 1rem 0;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  background-color: rgba(74, 64, 224, 1);
  box-sizing: border-box;
  cursor: pointer;
  transition: transform 0.2s, box-shadow 0.2s;
}

.new-task-btn:hover {
  transform: translateY(-1px);
  box-shadow: 0 0.625rem 0.9375rem -0.1875rem rgba(74, 64, 224, 0.3),
              0 0.25rem 0.375rem -0.25rem rgba(74, 64, 224, 0.3);
}

.btn-overlay {
  width: calc(100% - 0rem);
  height: calc(100% - 0rem);
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  border-radius: 0.5rem 0.5rem 0.5rem 0.5rem;
  box-shadow:
      0 0.625rem 0.9375rem -0.1875rem rgba(74, 64, 224, 0.2),
      0 0.25rem 0.375rem -0.25rem rgba(74, 64, 224, 0.2);
  background-color: rgba(255, 255, 255, 0.0020000000949949026);
  pointer-events: none;
}

.btn-icon {
  width: 0.5104rem;
  height: 0.5104rem;
  background-image: url(@/assets/images/Frame_1_59.png);
  background-size: 100% 100%;
  background-repeat: no-repeat;
  position: relative;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.btn-text {
  font-size: 0.75rem;
  font-family: "Alibaba PuHuiTi-Regular";
  font-weight: 400;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  letter-spacing: 0.075rem;
  line-height: 1rem;
  text-transform: uppercase;
  color: rgba(244, 241, 255, 1);
  width: 3.3006rem;
  height: 1rem;
  position: relative;
  flex-shrink: 0;
  margin: 0;
}
</style>
