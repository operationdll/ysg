// /**
//  * hello 模块状态管理
//  */
//
// import  '../../config/http'
// import * as types from '../mutation-types'
//
// const state = {
// 	helloData: []
// }
//
// const actions = {
// 	commitHello: function ({commit}) {
// 		return new Promise((resolve, reject) => {
// 			API.helloApi(data).then((response) => {
// 				commit(types.HELLO, response.data.data)
// 				resolve(data)
// 			}, (error) => {
// 				reject(error)
// 			})
// 		})
// 	}
// }
//
// const mutations = {
// 	[types.HELLO] (state, data) {
// 		state.helloData = data
// 	}
// }
//
// const getters = {
// 	helloData: state => state.helloData,
// }
//
// export default {
// 	state,
// 	actions,
// 	mutations,
// 	getters
// }z