import axios from 'axios';
import { ElMessage } from 'element-plus';

// 创建 axios 实例
const service = axios.create({
  baseURL: '/api',
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
});

// 请求拦截器
service.interceptors.request.use(
  (config) => {
    // 可以在这里添加 token 等认证信息
    return config;
  },
  (error) => {
    console.error('请求错误:', error);
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (response) => {
    const res = response.data;

    // 检查响应码
    if (res.code !== 200) {
      ElMessage.error(res.message || '操作失败');
      return Promise.reject(new Error(res.message || '操作失败'));
    }

    return res;
  },
  (error) => {
    console.error('响应错误:', error);

    // 处理网络错误
    if (error.message.includes('timeout')) {
      ElMessage.error('请求超时，请稍后重试');
    } else if (error.message.includes('404')) {
      ElMessage.error('请求的资源不存在');
    } else if (error.message.includes('500')) {
      ElMessage.error('服务器内部错误');
    } else {
      ElMessage.error('网络错误，请检查网络连接');
    }

    return Promise.reject(error);
  }
);

export default service;
