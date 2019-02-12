
import API from '../../config/Resource'
import * as types from '../mutation-types'

const state = {
	groupList: {},
    groupListbyId:{},
	home:{},
	hotelDetail:{},
    appStart:{}
}

const actions = {
	//集团列表接口
	getGroupList: function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.groupList(data).then((res) => {
				commit(types.GROUPLIST, res.data.data)
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	//根据集团id 获取物业列表
	getGroupListById: function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.groupListbyId(data).then((res) => {
				// alert(res.data.data)
				// console.log('res',res.data.data.list)
				commit(types.GROUPLISTBYID, res.data.data)

				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	//根据集团id 获取物业列表
	getRoomResList: function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.roomResList(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	//根据物业id查询物业基本信息
	getHome: function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.home(data).then((res) => {
				commit(types.HOME, res.data)
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	//根据物业id查询物业详情信息
	showingorder:function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.showingorder(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	//提交预约信息
	getHotelDetail:function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.getHotelDetail(data).then((res) => {
				commit(types.HOTELDETAIL, res.data.data)
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	//临时使用调取启动图
	helloApi:function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.helloApi(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	//切换语言
	tabLanguage:function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.tabLanguage(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	//获取新闻
	getNews:function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.news(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	getRss:function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.rss(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
	getMyBill:function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.myBill(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
    help:function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.help(data).then((res) => {
				resolve(res.data)
			}, (error) => {
				reject(error)
			})
		})
	},
    groupDetail:function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.groupDetail(data).then((res) => {
                resolve(res.data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getOrderList:function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getOrderList(data).then((res) => {
                resolve(res.data)
            }, (error) => {
                reject(error)
            })
        })
    },
    changeOrder:function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.changeOrder(data).then((res) => {
                resolve(res.data)
            }, (error) => {
                reject(error)
            })
        })
    },
    appUpdate:function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.appUpdate(data).then((res) => {
                resolve(res.data)
            }, (error) => {
                reject(error)
            })
        })
    },
    appStart:function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.appStart(data).then((res) => {
                commit(types.APPSTART, res.data)
                resolve(res.data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getJudge:function ({commit}, url, data) {
        return new Promise((resolve, reject) => {
            API.getJudge(url,data).then((res) => {
                commit(types.APPSTART, res.data)
                resolve(res.data)
            }, (error) => {
                reject(error)
            })
        })
    },
    saveJudge:function ({commit}, url, data) {
        return new Promise((resolve, reject) => {
            API.commitJudge(url,data).then((res) => {
                commit(types.APPSTART, res.data)
                resolve(res.data)
            }, (error) => {
                reject(error)
            })
        })
    }
}

const mutations = {
	[types.GROUPLIST] (state, data) {
		state.groupList = data
	},
	[types.GROUPLISTBYID] (state, data) {
		state.groupListbyId = data

	},
	[types.HOME] (state, data) {
		state.home = data

	},
	[types.HOTELDETAIL] (state, data) {
		state.hotelDetail = data

	},
    [types.APPSTART] (state, data) {
        state.appStart= data

    },
}

const getters = {
	groupList: state => state.groupList,
	groupListbyId: state => state.groupListbyId,
	hotelDetail: state => state.hotelDetail,
	home: state => state.home,
    appStart: state => state.appStart
}

export default {
	state,
	actions,
	mutations,
	getters
}