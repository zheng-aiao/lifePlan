<template>
  <header class="header-wrapper">
    <div class="left-container">
      <div class="logo-container">
        <img src="@/assets/images/header/title.png" class="logo-image" />
      </div>
    </div>

    <div class="search-container">
      <el-input
        v-model="searchQuery"
        placeholder="搜索任务、笔记或档案..."
        class="search-input"
      >
        <template #prefix>
          <img src="@/assets/images/header/search.png" class="search-icon" />
        </template>
      </el-input>
    </div>

    <div class="right-container">
      <el-button class="header-button" circle>
        <img src="@/assets/images/header/setting.png" class="button-icon" />
      </el-button>
      <el-button class="header-button" circle>
        <img src="@/assets/images/header/message.png" class="button-icon" />
      </el-button>
      <div class="vertical-border"></div>
      <el-avatar class="user-avatar">
        <img src="@/assets/images/header/avatar.png" class="avatar-image" />
      </el-avatar>
    </div>
  </header>
</template>

<script setup>
import { ref } from "vue";

const searchQuery = ref("");
</script>

<style scoped lang="scss">

.header-wrapper {
  height: var(--header-height);
  width: 100vw;
  background-color: rgba(255, 255, 255, 0.8);
  backdrop-filter: blur(0.75rem);
  box-shadow: 0 0.0625rem 0.125rem rgba(0, 0, 0, 0.05);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 2rem;
  position: fixed;
  top: 0;
  left: 0;
  z-index: 100;
  box-sizing: border-box;

  .left-container {
    display: flex;
    align-items: center;
    width: 13.9375rem;
    flex-shrink: 0;

    .logo-container {
      width: 3.6875rem;
      height: 1.75rem;
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
    margin: 0 2rem;
    max-width: 42rem;
    min-width: 0;

    .search-input {
      width: 100%;
      max-width: 42rem;
      height: 2.5rem;
      border-radius: 0.625rem;
      background-color: rgba(234, 241, 255, 1);
      border: none;
    }

    .search-icon {
      width: 1.125rem;
      height: 1.125rem;
      margin-left: 1rem;
      margin-right: 0.5rem;
    }
  }

  .right-container {
    display: flex;
    align-items: center;
    gap: 1.5rem;
    width: 12.5rem;
    justify-content: flex-end;
    flex-shrink: 0;

    .header-button {
      width: 2.25rem;
      height: 2.25rem;
      border-radius: 50%;
      background-color: transparent;
      border: none;
      display: flex;
      align-items: center;
      justify-content: center;

      .button-icon {
        width: 1.25rem;
        height: 1.25rem;
        object-fit: contain;
      }
    }

    .vertical-border {
      width: 0.0625rem;
      height: 2.5rem;
      background-color: rgba(158, 174, 199, 0.2);
    }

    .user-avatar {
      width: 2.5rem;
      height: 2.5rem;
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
  font-size: 1rem;
  font-family: "Alibaba PuHuiTi-Regular";
}

:deep(.el-input__inner) {
  font-size: 1rem;
  font-family: "Alibaba PuHuiTi-Regular";
  color: #323031;
}
</style>
