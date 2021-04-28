import axios from 'axios'
import router from '@/router/router'
import qs from 'qs'
import merge from 'lodash/merge'

const http = axios.create({
    timeout: 1000 * 30,
    withCredentials: true,
    headers: {
        'Content-Type': 'application/json; charset=utf-8'
    }
})

/**
 * 请求拦截
 */
http.interceptors.request.use(config => {
    // Vue.cookie.get('token');// 请求头带上token
    var token = sessionStorage.getItem('token')
    if (token) {
        config.headers['token'] = token
    }
    return config
}, error => {
    return Promise.reject(error)
})

/**
 * 响应拦截
 */
http.interceptors.response.use(response => {
    // eslint-disable-next-line no-undef
    if (response.data && response.data.code === 401) { // 401, token失效
        router.push({name: 'login'})
    }
    var headers = response.headers

    if (headers && headers.token) {
        sessionStorage.setItem('token', headers.token)
        // console.log("token update:",headers.token)
        // Vue.cookie.set("token",headers.token);
        // console.log(Vue.cookie.get("token"))
        // document.cookie = "token=" + headers.token;
    }
    return response
}, error => {
    return Promise.reject(error)
})

/**
 * 请求地址处理
 * @param {*} actionName action方法名称
 */
http.adornUrl = (actionName) => {
    // 非生产环境 && 开启代理, 接口前缀统一使用[/proxyApi/]前缀做代理拦截!
    return (process.env.NODE_ENV !== 'production' && process.env.OPEN_PROXY ? '/proxyApi/' : window.SITE_CONFIG.baseUrl) + actionName
}

/**
 * get请求参数处理
 * @param {*} params 参数对象
 * @param {*} openDefaultParams 是否开启默认参数?
 */
http.adornParams = (params = {}, openDefaultParams = true) => {
    const defaults = {
        't': new Date().getTime()
    };
    return openDefaultParams ? merge(defaults, params) : params
}

/**
 * post请求数据处理
 * @param {*} data 数据对象
 * @param {*} openDefaultParams 是否开启默认数据?
 * @param {*} contentType 数据格式
 *  json: 'application/json; charset=utf-8'
 *  form: 'application/x-www-form-urlencoded; charset=utf-8'
 */
http.adornData = (data = {}, openDefaultParams = true, contentType = 'json') => {
    const defaults = {
        't': new Date().getTime()
    };
    data = openDefaultParams ? merge(defaults, data) : data
    return contentType === 'json' ? JSON.stringify(data) : qs.stringify(data)
}

export default axios;
