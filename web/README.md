# web

### 安装脚手架
```
npm install -g @vue/cli   //安装最新版
npm install -g @vue/cli@4.5.15   //指定版本安装
```

## Project setup 构建vue的项目框架
```
npm install
```

### Compiles and hot-reloads for development
```
npm run serve
```

### Compiles and minifies for production
```
npm run build
打包成普通的web项目，这样就可以放到服务器上运行了
打包后的项目index.html为入口文件

打开了index.html文件，发现网页一片空白，打开F12发现console报了几个错误，状态码为404
这是因为引用资源的路径问题，我们只要在的文件在项目目录下的config文件夹中修改index.js文件
build:{...assetsPublicPath:"./"} 
改完重新打包即可
```

### Lints and fixes files 自动修复
```
npm run lint
```

### 查看当前镜像
```
npm get registry
```

### 设置淘宝镜像
```
npm config set registry http://registry.npm.taobao.org/
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).



# 项目用到的
### Ant Design Vue
安装Ant Design Vue
```
npm i --save ant-design-vue
npm i --save ant-design-vue@next    安装最新未发行版本
npm i --save ant-design-vue@3.2.11  指定版本安装
```

### 项目中使用：在项目中注册组件 ，在main.ts文件中
```
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
const app = createApp(App);
app.use(Antd).mount('#app');
```

###  集成Axios
```
npm install axios --save
```

### Typescript 
```
npm install -g typescript  //全局安装TypeScipt
tsc  xxx.ts  //编译
node xxx.ts  //运行
```