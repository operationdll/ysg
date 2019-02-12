/**
 * 中英文翻译
 */

import * as types from '../mutation-types'
import API from '../../config/Resource'

const state = {
}
const actions = {

    getPoiTypeList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getPoiTypeList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getPoiTagList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getPoiTagList(data).then((data) => {
                resolve(data)

            }, (error) => {
                reject(error)
            })
        })
    },
    getPoiList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getPoiList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    JudgeList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.JudgeList(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    commitJudge: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.commitJudge(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    }

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