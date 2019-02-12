/**
 * 中英文翻译
 */import * as types from '../mutation-types'
import API from '../../config/Resource'

const state = {
    translateLanguageList:[],
    hotelTelList:[{'id':'1','title':'电话','tel':'123'},{'id':'1','title':'电话','tel':'123'}],
}
const actions = {
    translate: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.translate(data).then((data) => {
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    translateLanguageList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.translateLanguageList(data).then((data) => {
                let dic = data.data.data.list;
                let resultArray = [];
                for (var key in dic){
                    resultArray.push({name: dic[key], value: key});
                }
                commit(types.TRANSLATELANGUAGELIST, resultArray)
                resolve(data)
            }, (error) => {
                reject(error)
            })
        })
    },
    getHotelTelList: function ({commit}, data) {
        return new Promise((resolve, reject) => {
            API.getHotelTelList(data).then((data) => {
                commit(types.HOTELTELLIST, data);
                resolve(data);
            }, (error) => {
                reject(error)
            })
        })
    },


}
const mutations = {
    [types.TRANSLATELANGUAGELIST] (state,data) {
        state.translateLanguageList=data;
    },
    [types.HOTELTELLIST] (state,data) {
        state.hotelTelList=data;
    },

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