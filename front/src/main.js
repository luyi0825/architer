import Vue from 'vue'
import App from './App.vue'
// vuex的实例化对象
import store from './store'
import router from './router/router'
import ElementPlus from 'element-plus';
import 'element-plus/lib/theme-chalk/index.css';
import VueCompositionApi from '@vue/composition-api'
import axios from '@/router/axios';

//放到原型对象中
Vue.prototype.$http = axios;
Vue.use(ElementPlus)
//使用vue3.0
Vue.use(VueCompositionApi);
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')