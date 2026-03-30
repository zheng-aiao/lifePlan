export const yearlyTasks = [
  {
    id: 1,
    title: '完成品牌设计规范指南',
    category: '工作',
    progress: 75,
    color: 'rgba(74, 64, 224, 1)',
  },
  {
    id: 2,
    title: '阅读 4 本深度学习书籍',
    category: '学习',
    progress: 25,
    color: 'rgba(248, 160, 16, 1)',
  },
  {
    id: 3,
    title: '半程马拉松训练营',
    category: '健身',
    progress: 60,
    color: 'rgba(0, 105, 71, 1)',
  },
];

export const monthlyTasks = [
  {
    id: 1,
    title: '阅读 4 本深度学习书籍',
    category: '学习',
    progress: 25,
    color: 'rgba(248, 160, 16, 1)',
  },
  {
    id: 2,
    title: '半程马拉松训练营',
    category: '健身',
    progress: 60,
    color: 'rgba(0, 105, 71, 1)',
  },
];

export const temporaryTasks = [
  {
    id: 1,
    title: '阅读 4 本深度学习书籍',
    category: '学习',
    progress: 25,
    color: 'rgba(248, 160, 16, 1)',
  },
  {
    id: 2,
    title: '半程马拉松训练营',
    category: '健身',
    progress: 60,
    color: 'rgba(0, 105, 71, 1)',
  },
];

export const dailyTasks = [
  {
    id: 1,
    title: '深度设计冲刺: 品牌重塑',
    timeRange: '07:00 - 09:30',
    status: 'in-progress',
    statusText: '进行中',
    actualDuration: '2h 30min',
    subTasks: [
      { text: '色彩心理学研究', completed: false },
      { text: '构建原子化组件库', completed: true },
      { text: '高保真交互原型', completed: false },
    ],
    activities: [
      {
        time: '07:45',
        type: 'interruption',
        typeText: '干扰',
        description: '突发团队电话会议 (15min)',
      },
      { time: '08:10', type: 'delay', typeText: '延迟', description: '资产渲染等待' },
    ],
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 2,
    title: '产品策略同步会议',
    timeRange: '10:00 - 12:00',
    status: 'completed',
    statusText: '已完成',
    actualDuration: '1h 30min',
    subTasks: [],
    activities: [],
    borderColor: 'rgba(248, 160, 16, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },

  {
    id: 4,
    title: '午休与恢复',
    timeRange: '12:30 - 13:30',
    status: 'pending',
    statusText: '待开始',
    actualDuration: '0min',
    subTasks: [{ text: '冥想 15 分钟', completed: false }],
    activities: [],
    borderColor: 'rgba(0, 105, 71, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },

  {
    id: 6,
    title: '设计稿评审',
    timeRange: '14:30 - 17:00',
    status: 'delayed',
    statusText: '已延迟',
    actualDuration: '0min',
    subTasks: [
      { text: '评审首页设计', completed: false },
      { text: '整理反馈意见', completed: false },
    ],
    activities: [
      { time: '15:35', type: 'delay', typeText: '延迟', description: '等待设计稿最终确认' },
    ],
    borderColor: 'rgba(249, 115, 22, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
    opacity: 0.8,
  },
  {
    id: 7,
    title: '文档整理',
    timeRange: '17:30 - 19:00',
    status: 'pending',
    statusText: '待开始',
    actualDuration: '0min',
    subTasks: [
      { text: '更新 API 文档', completed: false },
      { text: '整理会议纪要', completed: false },
    ],
    activities: [],
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 8,
    title: '运动健身',
    timeRange: '19:00 - 20:00',
    status: 'pending',
    statusText: '待开始',
    actualDuration: '0min',
    subTasks: [
      { text: '跑步 30 分钟', completed: false },
      { text: '拉伸放松', completed: false },
    ],
    activities: [],
    borderColor: 'rgba(0, 105, 71, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 9,
    title: '个人项目开发',
    timeRange: '20:30 - 22:00',
    status: 'pending',
    statusText: '待开始',
    actualDuration: '0min',
    subTasks: [
      { text: '学习新技术', completed: false },
      { text: '写技术博客', completed: false },
    ],
    activities: [],
    borderColor: 'rgba(74, 64, 224, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
  },
  {
    id: 10,
    title: '明日计划',
    timeRange: '22:30 - 23:00',
    status: 'pending',
    statusText: '待开始',
    actualDuration: '0min',
    subTasks: [{ text: '整理待办事项', completed: false }],
    activities: [],
    borderColor: 'rgba(148, 163, 184, 1)',
    bgColor: 'rgba(255, 255, 255, 1)',
    opacity: 0.6,
  },
];

export const getDailyTasksByDate = (date) => {
  return dailyTasks;
};
