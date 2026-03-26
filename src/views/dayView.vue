<template>
  <div class="day-view-container">
    <div class="main-layout">
      <AsideLeft
        @viewChange="handleViewChange"
        @categoryClick="handleCategoryClick"
        @newTask="handleNewTask"
      />
      <div class="middle-content">
        <div class="content-layout">
          <div class="left-section">
            <LeftContent />
          </div>
          <div class="right-section">
            <RightContent />
          </div>
        </div>
      </div>
      <AsideRight />
    </div>
  </div>
</template>

<script setup>
import AsideLeft from "@/components/AsideLeft.vue";
import AsideRight from "@/components/AsideRight.vue";
import LeftContent from "@/components/LeftContent.vue";
import RightContent from "@/components/RightContent.vue";

const handleViewChange = (view) => {
  console.log("View changed to:", view);
};

const handleCategoryClick = (category) => {
  console.log("Category clicked:", category);
};

const handleNewTask = () => {
  console.log("New task button clicked");
};
</script>

<style scoped>
.day-view-container {
  height: 100%;
  width: 100%;
  overflow: hidden;
}

.main-layout {
  display: flex;
  height: 100%;
  width: 100%;
  background-color: rgba(244, 246, 255, 1);
}

.middle-content {
  flex: 1;
  height: 100%;
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.content-layout {
  display: flex;
  height: 100%;
  gap: var(--content-gap);
  padding: var(--content-padding);
}

.left-section {
  width: var(--left-section-width);
  height: 100%;
  overflow-y: auto;
  flex-shrink: 0;
}

.right-section {
  flex: 1;
  height: 100%;
  overflow-y: auto;
  min-width: 0;
}
</style>
