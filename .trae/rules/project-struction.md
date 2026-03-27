---

## alwaysApply: false

# 文件夹说明

- mock文件夹： 模拟数据存放位置，组件创建用到的模拟数据可在mock文件夹下创建对应js文件，通过引入的方式在组件中使用
- layout文件夹： 全局布局组件存放位置，如header、footer、sidebar等
- view文件夹： 存放路由组件以及业务弹窗组件
  - 不同业务创建对应文件夹，在其下面存放index.vue文件，作为业务组件的入口文件
  - 相关弹窗放到该文件夹下dialog文件夹下，命名为XXXDialog.vue，XXX为业务名称
  - 相关组件放到该文件夹下的components文件夹下

# 使用规范

- 业务组件入口文件: src/views/业务文件夹/index.vue. 业务文件夹名称自定义
- 业务组件弹窗: 放到 src/views/业务文件夹/dialog，命名为XXXDialog.vue，XXX为业务弹窗名称
- 业务组件定制组件: 放到 src/views/业务文件夹/components下，命名为XXXComponent.vue，XXX为业务组件名称
- 公共组件: 放到 src/components/下，命名为XXXComponent.vue，XXX为组件名称。 公共组件可在多个业务组件中优先使用
