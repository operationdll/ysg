/**
 * 中英文翻译
 */import * as types from '../mutation-types'
import API from '../../config/Resource'

const state = {
    // translateLanguageList:[],
}
const actions = {
    getActivityTagList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getActivityTagList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getActivityList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getActivityList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    ActivityOrder: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.ActivityOrder(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getNoticTagList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getNoticTagList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getNoticList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getNoticList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getShoppingTagList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getShoppingTagList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getShoppingList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getShoppingList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getShoppingOrder: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getShoppingOrder(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getLifeTypeList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getLifeTypeList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getLifeList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getLifeList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getFeedbackList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getFeedbackList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getFeedback: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getFeedback(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    addFeedbackResult: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.addFeedbackResult(data).then((data) => {
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
    // },


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