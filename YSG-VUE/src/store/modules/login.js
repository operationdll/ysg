
import API from '../../config/Resource'
import * as types from '../mutation-types'

const state = {
	userDetail:{}
}

const actions = {
	//集团列表接口
	doLogin: function ({commit},data) {
		return new Promise((resolve, reject) => {
			API.loginGuest(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	loginStaff: function ({commit},data) {
		return new Promise((resolve, reject) => {
			API.loginStaff(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	getUsers:function ({commit},data) {
		return new Promise((resolve, reject) => {
			API.getUsers(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	}
}

const mutations = {
	[types.LOGIN] (state, data) {
		state.userDetail = data
	},

}

const getters = {
	userDetail: state => state.userDetail
}

export default {
	state,
	actions,
	mutations,
	getters
}