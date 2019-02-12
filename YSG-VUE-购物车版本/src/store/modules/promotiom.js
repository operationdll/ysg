/**
 * 促销优惠
 */import * as types from '../mutation-types'
import API from '../../config/Resource'

const state = {

}
const actions = {

    getPromotionTagList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getPromotionTagList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getPromotionList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getPromotionList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getNewsTagList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getNewsTagList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getNewsList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getNewsList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
	getGroupNoticeList: function ({commit}, data) {
		return new Promise((resolve, reject) => {
			API.getGroupNoticeList(data).then((data) => {
				resolve(data)
			}, (error) => {
				reject(error)
			})
		})
	},
    getGroupNewsTagList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getGroupNewsTagList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getGroupNewsList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getGroupNewsList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getGroupActivityTagList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getGroupActivityTagList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getGroupActivityList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getGroupActivityList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },


}
const mutations = {
    // [types.TRANSLATELANGUAGELIST] (state,data) {
    //     state.translateLanguageList=data;
    // }

}
const getters = {
    // language: state => state.language
}

export default {
    state,
    actions,
    mutations,
    getters
}