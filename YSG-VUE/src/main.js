
import Vue from 'vue'
import App from './App'
import router from './router'
import store from './store'
import FastClick from 'fastclick'
import YDUI from 'vue-ydui';
import 'vue-ydui/dist/ydui.rem.css';
import 'vue-ydui/dist/ydui.base.css';
import VueScroller from 'vue-scroller'
import vueScrollBehavior from 'vue-scroll-behavior'
import AMap from 'vue-amap';
import Viewer from 'v-viewer' ;
Vue.use(Viewer)
Vue.use(AMap); 

// Using vueScrollBehavior
Vue.use(vueScrollBehavior, {
	router: router,
	// maxLength: 3,
	// ignore: [/\/b/, /\/m/],
	// delay: 0
  })

AMap.initAMapApiLoader({
    key: '2b5e7c8259b227ddc1b49916a0fed6b3',
    plugin: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor']
});

Vue.use(VueScroller)
Vue.use(YDUI);
// FastClick.attach(document.body)


Vue.directive('elem', {
	inserted: function (el) {
	  el.innerHTML =
		'Distance top height: <b>' + el.offsetTop + '</b>px'
	}
  })

window.wtVue=new Vue({
	el: '#app',
	router,
	store,
	template: '<App/>',
	components: { App }
})


