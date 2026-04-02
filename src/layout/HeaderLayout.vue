<template>
  <header class="header-wrapper">
    <div class="left-container">
      <div class="logo-container">
        <img src="../assets/images/header/title.png" class="logo-image" />
      </div>
    </div>

    <div class="search-container">
      <el-input v-model="searchQuery" placeholder="搜索任务、笔记或档案..." class="search-input">
        <template #prefix>
          <img src="../assets/images/header/search.png" class="search-icon" />
        </template>
      </el-input>
    </div>

    <div class="right-container">
      <el-button class="header-button" circle>
        <img src="../assets/images/header/setting.png" class="button-icon" />
      </el-button>
      <el-button class="header-button" circle>
        <img src="../assets/images/header/message.png" class="button-icon" />
      </el-button>
      <div class="vertical-border"></div>
      <el-avatar class="user-avatar">
        <img src="../assets/images/header/avatar.png" class="avatar-image" />
      </el-avatar>
    </div>
  </header>
</template>

<script setup>
import { ref } from 'vue';

const searchQuery = ref('');
</script>

<style scoped lang="scss">
@use '@/assets/scss/rules' as *;
.header-wrapper {
  height: var(--header-height);
  width: 100vw;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(pxToRem(12));
  box-shadow: 0 pxToRem(1) pxToRem(2) rgba(0, 0, 0, 0.05);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 pxToRem(32);
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
  box-sizing: border-box;

  .left-container {
    display: flex;
    align-items: center;
    width: pxToRem(223);
    flex-shrink: 0;

    .logo-container {
      width: pxToRem(59);
      height: pxToRem(28);
      display: flex;
      align-items: center;
      justify-content: center;

      .logo-image {
        width: 100%;
        height: 100%;
        object-fit: contain;
      }
    }
  }

  .search-container {
    flex: 1;
    display: flex;
    justify-content: center;
    margin: 0 pxToRem(32);
    max-width: pxToRem(672);
    min-width: 0;

    .search-input {
      width: 100%;
      max-width: pxToRem(672);
      height: pxToRem(40);
      border-radius: pxToRem(10);
      background-color: rgba(234, 241, 255, 1);
      border: none;
    }

    .search-icon {
      width: pxToRem(18);
      height: pxToRem(18);
      margin-left: pxToRem(16);
      margin-right: pxToRem(8);
    }
  }

  .right-container {
    display: flex;
    align-items: center;
    gap: pxToRem(24);
    width: pxToRem(200);
    justify-content: flex-end;
    flex-shrink: 0;

    .header-button {
      width: pxToRem(36);
      height: pxToRem(36);
      border-radius: 50%;
      background-color: transparent;
      border: none;
      display: flex;
      align-items: center;
      justify-content: center;

      .button-icon {
        width: pxToRem(20);
        height: pxToRem(20);
        object-fit: contain;
      }
    }

    .vertical-border {
      width: pxToRem(1);
      height: pxToRem(40);
      background-color: rgba(158, 174, 199, 0.2);
    }

    .user-avatar {
      width: pxToRem(40);
      height: pxToRem(40);
      border-radius: 50%;
      background-color: #f0f0f0;

      .avatar-image {
        width: 100%;
        height: 100%;
        object-fit: cover;
        border-radius: 50%;
      }
    }
  }
}

:deep(.el-input__wrapper) {
  background-color: rgba(234, 241, 255, 1) !important;
  border: none !important;
  box-shadow: none !important;
}

:deep(.el-input__placeholder) {
  color: #9eaec7 !important;
  font-size: pxToRem(16);
  font-family: 'Alibaba PuHuiTi-Regular';
}

:deep(.el-input__inner) {
  font-size: pxToRem(16);
  font-family: 'Alibaba PuHuiTi-Regular';
  color: #323031;
}
</style>
