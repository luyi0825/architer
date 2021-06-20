import {createApp} from 'vue'
import App from './App.vue'
import router from './router/index_test'
import store from './store'
import installElementPlus from './plugins/element'
import './assets/css/icon.css'
import httpRequest from '@/utils/httpRequest' // api: https://github.com/axios/axios
// 挂载全局

//createApp.prototype.$http = httpRequest // ajax请求方法
const app = createApp(App)
app.config.globalProperties.$http = httpRequest;
installElementPlus(app)
app
    .use(store)
    .use(router)
    .mount('#app')