import "./assets/styles/global.css";
import { createApp } from "vue";
import "./styles.css";
import "./assets/styles/font.css";
import App from "./App.vue";
import { router } from "./router";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";

createApp(App).use(router).use(ElementPlus).mount("#app");
