import Vue from 'vue'
import './plugins/axios'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/element.js'
import './js/date'
import './assets/iconfont/ic1/iconfont.css'
import './assets/iconfont/ic2/iconfont.css'
import './assets/iconfont/iconfont.css'
import * as echarts from 'echarts';
import qs from 'qs'



Vue.prototype.$qs = qs

Vue.prototype.$echarts = echarts;

Vue.config.productionTip = false


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
