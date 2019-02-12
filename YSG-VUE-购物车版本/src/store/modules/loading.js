/**
 * hello 模块状态管理
 */
import * as types from '../mutation-types'

const state = {
	loadFlag: false,
	direction:'forward',
	getGroupList:'getGroupList'
}
const actions = {
	showLoading: function ({commit}) {
		commit(types.SHOWLOADING)
	},
	hideLoading: function ({commit}) {
		commit(types.HIDELOADING)
	},
	getDirect:function ({commit}, direction) {
		commit(types.HIDELOADING,direction)
	}
}
const mutations = {
	[types.SHOWLOADING] (state) {
		state.loadFlag = true;
	},
	[types.HIDELOADING] (state) {
		state.loadFlag = false;
	},
	[types.DIRECTION] (state, direction) {
		state.direction = direction;
	},

}
const getters = {
	loadFlag: state => state.loadFlag,
	direction: state => state.direction,
}

export default {
	state,
	actions,
	mutations,
	getters
}