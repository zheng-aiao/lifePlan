// 任务状态文本映射
export const taskStatusTextMap = {
  0: '待开始',
  1: '进行中',
  2: '已暂停',
  3: '已完成',
  4: '已放弃',
  5: '已延迟',
};

// 映射任务状态文本
export const mapTaskStatusText = (status) => {
  return taskStatusTextMap[status] || '待开始';
};
