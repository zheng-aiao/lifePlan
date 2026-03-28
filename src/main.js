import './assets/styles/global.scss';
import './assets/styles/font.css';
import 'element-plus/dist/index.css';
import './assets/styles/element-reset.css';
import { createApp } from 'vue';
import App from './App.vue';
import { router } from './router';
import ElementPlus from 'element-plus';

createApp(App).use(router).use(ElementPlus).mount('#app');
