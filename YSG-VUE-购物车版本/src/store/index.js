/**
 * Created by lx on 17/6/2.
 * 状态管理主入口
 */
import Vue from 'vue'
import Vuex from 'vuex'
import hello from './modules/hello'
import loading from './modules/loading'
import language from './modules/language'
import home from './modules/home'
import login from './modules/login'
import translate from './modules/translate'
import promotion from './modules/promotiom'
import raiders from './modules/raiders'
import community from './modules/community'
Vue.use(Vuex)

export default new Vuex.Store({
	modules: {
		'hello': hello,
		'loading' : loading,
		'language': language,
		'home': home,
		'login':login,
		'translate':translate,
		'promotion':promotion,
		'raiders': raiders,
		'community': community

	}
})

