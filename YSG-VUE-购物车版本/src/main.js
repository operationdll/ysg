
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import FastClick from 'fastclick'
import YDUI from 'vue-ydui';
import 'vue-ydui/dist/ydui.rem.css';
import 'vue-ydui/dist/ydui.base.css';
import VueScroller from 'vue-scroller'
import AMap from 'vue-amap';
Vue.use(AMap);
AMap.initAMapApiLoader({
    key: '2b5e7c8259b227ddc1b49916a0fed6b3',
    plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor']
});

Vue.use(VueScroller)
Vue.use(YDUI);
// FastClick.attach(document.body)

window.wtVue=new Vue({
	el: '#app',
	router,
	store,
	template: '<App/>',
	components: { App }
})


