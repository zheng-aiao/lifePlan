import request from './request';

// 任务相关服务
const taskService = {
  // 创建任务
  createTask: (data) => {
    return request.post('/tasks', data);
  },

  // 开始任务
  startTask: (taskId) => {
    return request.post(`/tasks/${taskId}/start`);
  },

  // 暂停任务
  pauseTask: (taskId, reason) => {
    return request.post(`/tasks/${taskId}/pause`, { reason });
  },

  // 恢复任务
  resumeTask: (taskId) => {
    return request.post(`/tasks/${taskId}/resume`);
  },

  // 完成任务
  completeTask: (taskId, feedback) => {
    return request.post(`/tasks/${taskId}/complete`, { feedback });
  },

  // 放弃任务
  abandonTask: (taskId, reason) => {
    return request.post(`/tasks/${taskId}/abandon`, { reason });
  },

  // 延时任务
  delayTask: (taskId, reason) => {
    return request.post(`/tasks/${taskId}/delay`, { reason });
  },

  // 根据id获取任务详情（包含子任务和活动日志）
  getTaskDetailsById: (taskId, params = {}) => {
    return request.get(`/tasks/${taskId}`, { params });
  },

  // 根据日期获取任务详情列表（包含子任务和活动日志）
  getTaskDetailsByDate: (date, params = {}) => {
    return request.get(`/tasks/date/${date}`, { params });
  },

  // 更新任务
  updateTask: (taskId, data) => {
    return request.put(`/tasks/${taskId}`, data);
  },
};

// 子任务相关服务
const subTaskService = {
  // 创建子任务
  createSubTask: (data) => {
    return request.post('/sub-tasks', data);
  },

  // 删除子任务
  deleteSubTask: (subTaskId) => {
    return request.delete(`/sub-tasks/${subTaskId}`);
  },

  // 更新子任务
  updateSubTask: (subTaskId, data) => {
    return request.put(`/sub-tasks/${subTaskId}`, data);
  },

  // 完成子任务
  completeSubTask: (subTaskId) => {
    return request.post(`/sub-tasks/${subTaskId}/complete`);
  },

  // 取消完成子任务
  uncompleteSubTask: (subTaskId) => {
    return request.post(`/sub-tasks/${subTaskId}/uncomplete`);
  },

  // 获取子任务列表
  getSubTasksByGroup: (subTaskGroup) => {
    return request.get('/sub-tasks', { params: { subTaskGroup } });
  },
};

// 任务状态变更相关服务
const taskStatusChangeService = {
  // 获取任务状态变更记录
  getStatusChangesByTaskId: (taskId) => {
    return request.get(`/task-changes/${taskId}`);
  },

  // 创建任务状态变更记录
  createStatusChange: (data) => {
    return request.post('/task-changes', data);
  },
};

// 导出业务服务
export default {
  task: taskService,
  subTask: subTaskService,
  taskStatusChange: taskStatusChangeService,
};
