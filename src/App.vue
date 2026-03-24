<template>
  <div class="app">
    <nav class="app-nav">
      <div class="nav-container">
        <div class="nav-links">
          <router-link to="/day" class="nav-link">日计划</router-link>
          <router-link to="/month" class="nav-link">月计划</router-link>
          <router-link to="/year" class="nav-link">年计划</router-link>
        </div>
      </div>
    </nav>
    <router-view />
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.app {
  width: 100%;
  min-height: 100vh;
  font-family: Arial, sans-serif;
  background-color: #f5f7fa;
}

.app-nav {
  background-color: #409eff;
  color: white;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.nav-container {
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 20px;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 60px;
}

.nav-links {
  display: flex;
  gap: 30px;
}

.nav-link {
  color: white;
  text-decoration: none;
  padding: 8px 16px;
  border-radius: 4px;
  transition: background-color 0.3s;
  font-size: 16px;
  font-weight: 500;
}

.nav-link:hover {
  background-color: rgba(255, 255, 255, 0.2);
}

.router-link-active {
  background-color: rgba(255, 255, 255, 0.3);
  font-weight: bold;
}

@media (max-width: 768px) {
  .nav-container {
    padding: 0 10px;
  }

  .nav-links {
    gap: 15px;
  }

  .nav-link {
    font-size: 14px;
    padding: 6px 12px;
  }
}
</style>