<template>
  <div class="header-wrapper">
    <div class="header-content">
      <div class="header-vector"></div>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.header-wrapper {
  position: absolute;
  left: 0;
  top: 0;
  display: flex;
  flex-direction: row;
  justify-content: flex-start;
  align-items: center;
  padding: 0.75rem 2rem 0.75rem 2rem;
  width: 120rem;
  height: 4rem;
  box-sizing: border-box;
}

.header-content {
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
}

.header-vector {
  position: absolute;
  inset: -1.5625% -0.10416666666666667% -4.6875% -0.10416666666666667%;
  background-image: url(@/assets/images/Frame_1_244.png);
  background-size: 100% 100%;
  background-repeat: no-repeat;
  width: 100%;
  height: 100%;
}
</style>
