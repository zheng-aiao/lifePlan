# 框架

技术栈：  Vue + element Plus + vue-router + vuex

样式： 语言使用scss, 响应式布局，rem单位+媒体查询

# 注意事项

- 1.优先使用element-ui组件库封装组件，减少使用原生html标签封装
- 2\. vue组件的 样式语言优先使用scss, 样式嵌套优先编写，减少平铺编写
- 3\. 尺寸单位 统一使用pxToRem()函数转换, 组件中引入方式如下

```Vue
<style scoped lang="scss">
@import '@/assets/styles/_mixins.scss';
</style>
```

