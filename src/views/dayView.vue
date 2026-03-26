<template>
  <div class="view-container">
    <div class="day-view">
      <div class="day-view-content">
        <DayLeftContent />
        <DayRightContent />
      </div>
      <div class="day-view-aside"><AsideRight /></div>
    </div>
  </div>
</template>

<script setup>
import AsideRight from "@/components/AsideRight.vue";
import DayLeftContent from "@/components/day/DayLeftContent.vue";
import DayRightContent from "@/components/day/DayRightContent.vue";
</script>

<style scoped lang="scss">
.view-container {
  display: flex;
  height: 100%;
  width: 100%;
  background-color: rgba(244, 246, 255, 1);

  .day-view {
    flex: 1;
    height: 100%;
    display: flex;
    min-width: 0;

    .day-view-content {
      flex: 1;
      display: flex;
      height: 100%;
      gap: var(--content-gap);
      padding: var(--content-padding);
    }
    .day-view-aside {
      width: var(--aside-width);
      height: 100%;
      overflow-y: auto;
      flex-shrink: 0;
    }
  }
}
</style>
