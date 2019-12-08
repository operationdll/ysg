import API from "../../config/Resource";
import * as types from "../mutation-types";

const state = {
  groupList: {},
  groupListbyId: {},
  home: {},
  hotelDetail: {},
  appStart: {}
};

const actions = {
  //集团列表接口
  getGroupList: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.groupList(data).then(
        res => {
          commit(types.GROUPLIST, res.data.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //根据集团id 获取物业列表
  getGroupListById: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.groupListbyId(data).then(
        res => {
          // alert(res.data.data)
          // console.log('res',res.data.data.list) 
          commit(types.GROUPLISTBYID, res.data.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //根据物业ID获取物业信息
  getHotelListById: function({ commit }, data) { 
    return new Promise((resolve, reject) => {
      API.getHotelLis(data).then(
        res => {  
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //根据集团id 获取物业列表
  getRoomResList: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.roomResList(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //根据物业id查询物业基本信息
  getHome: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.home(data).then(
        res => {
          commit(types.HOME, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //根据物业id查询物业详情信息
  showingorder: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.showingorder(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //提交预约信息
  getHotelDetail: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.getHotelDetail(data).then(
        res => {
          commit(types.HOTELDETAIL, res.data.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //临时使用调取启动图
  helloApi: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.helloApi(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //切换语言
  tabLanguage: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.tabLanguage(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //获取新闻
  getNews: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.news(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  getRss: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.rss(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  getMyBill: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.myBill(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  help: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.help(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  groupDetail: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.groupDetail(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  getOrderList: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.getOrderList(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  changeOrder: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.changeOrder(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  appUpdate: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.appUpdate(data).then(
        res => {
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  appStart: function({ commit }, data) {
    return new Promise((resolve, reject) => {
      API.appStart(data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  getJudge: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.getJudge(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  saveJudge: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.commitJudge(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  viewOrder: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.viewOrder(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  deleteOrder: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.deleteOrder(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  isSetPin: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.isSetPin(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  setPin: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.setPin(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  checkPin: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.checkPin(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  getShoppingHotelList: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.getShoppingHotelList(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  getStaffOrderList: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.getStaffOrderList(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  updateOrderProductById: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.updateOrderProductById(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //获取员工消息信息
  getStaffAppMsgList: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.getStaffAppMsgList(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //获取购物柜参数信息
	getShoppingBoxDetail: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.getShoppingBoxDetail(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //物业统计
	hotelStatistics: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.hotelStatistics(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //机器人控制位置集合
	getPositionList: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.getPositionList(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //机器人控制送物
  deliverRobot: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.deliverRobot(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //机器人控制取物
  robotSend: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.robotSend(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //机器人控制召唤/引领
  callRobot: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.callRobot(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //机器人控制返回充电桩
	robotBack: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.robotBack(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //获取首页广告列表
  getHomeAdv: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.getHomeAdv(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
  //曹操专车获取签名
	createCCSign: function({ commit }, url, data) {
    return new Promise((resolve, reject) => {
      API.createCCSign(url, data).then(
        res => {
          commit(types.APPSTART, res.data);
          resolve(res.data);
        },
        error => {
          reject(error);
        }
      );
    });
  },
};

const mutations = {
  [types.GROUPLIST](state, data) {
    state.groupList = data;
  },
  [types.GROUPLISTBYID](state, data) {
    state.groupListbyId = data;
  },
  [types.HOME](state, data) {
    state.home = data;
  },
  [types.HOTELDETAIL](state, data) {
    state.hotelDetail = data;
  },
  [types.APPSTART](state, data) {
    state.appStart = data;
  }
};

const getters = {
  groupList: state => state.groupList,
  groupListbyId: state => state.groupListbyId,
  hotelDetail: state => state.hotelDetail,
  home: state => state.home,
  appStart: state => state.appStart
};

export default {
  state,
  actions,
  mutations,
  getters
};
