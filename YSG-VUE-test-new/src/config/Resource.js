/**
 * Created by lx on 17/6/2.
 * 处理接口地址
 */
import axios from 'axios'
import store from '../store/index'
import router from '../router'
// 配置图片的基本路径
localStorage.identity  = 'test'
axios.defaults.baseURL = 'http://118.190.164.26:80/'
// axios.defaults.headers.common['authToken'] = localStorage.TOKEN

// 添加请求拦截器
axios.interceptors.request.use(function (config) {
	//打开loading 遮罩层
	store.dispatch('showLoading');
	return config;
}, function (error) {
    router.push('/error')
    store.dispatch('hideLoading');
	return Promise.reject(error);
});
// 添加响应拦截器
axios.interceptors.response.use(function (response) {
	// 关闭loading 遮罩层
	store.dispatch('hideLoading');
	return response;
}, function (error) {
	router.push('/error')
    store.dispatch('hideLoading');
	return Promise.reject(error);
});

//统一配置该模块下的 API 路径
const Hello = {
	helloApi: '/appimg/getAvailableAppImg'
}

//首页接口
const Home = {
    groupListUrl: '/group/getGroupList',
	groupDetailUrl: '/group/getGroupDetail',
	groupListbyIdUrl:'/hotellist/getEffectiveHotelList',
	homeUrl:'/hotellist/getHotelListDetail',
	hotelDetailUrl:'/hotellist/getHotelDetail',
	tabLanguageUrl:'/user/updateUserLang',
	newsUrl:'/push/getUserMsgList',
	showingorderUrl:'/showingorder/addShowingOrder',
	roomResListUrl:'/roomres/getHotelRoomTypeResList',
	rssUrl:'Rss/getHotelRssList',
	myBillUrl:'/UserBill/getUserBillList',
	helpUrl:'/Help/getGroupHelp',
	orderUrl:'/shoppingorder/getShoppingOrderList',
	changeOrderUrl:'/shoppingorder/changeOrderStatus',
	appUpdateUrl:'/AppVersion/getLatestAppVersionByPlatform',
	appStartUrl:'/appStartmsg/getEffectiveAppStartmsg'
}

//登录接口
const Login = {
	LoginUrl: '/user/login',
	staffLoginUrl:'/staff/login',
	getUsersUrl:'/staff/getStaffInfoByToken'
}

//功能
//1、翻译
const Translate = {
	//翻译结果获取
	translate:'/translate/translate',
	//翻译语言列表获取
    translateLanguageList:"/Translate/translateLanguageList"
}
//2、电话接口
const Phone = {
	hotelTelList:'/tel/getHotelTelList'
}

//促销优惠
const Promotion = {
	//物业促销标签获取
    PromotionTagList:'/PromotionTag/getTagList',
	//物业促销列表获取
	PromotionList:'/promotion/getPromotionList',
	//物业新闻标签获取
	NewsTagList:'/NewsTag/getTagList',
	//物业新闻列表
	NewsList:'/news/getNewsList',
	//集团新闻标签获取
	GroupNewsTagList:'/GroupNewsTag/getTagList',
	//集团新闻列表
	GroupNewsList:'/GroupNews/getGroupNewsList',
	//集团活动标签列表
	GroupActivityTagList:'/GroupActivityTagList/getActivityTagList',
	//集团活动
    GroupActivityList:'/GroupActivity/getEffectiveActivityList',
	//集团通知
	GroupNoticeList:'/GroupNotice/getNoticList',
}
//社区生活
const Community = {
	//获取物业活动标签列表
	ActivityTagList:'/ActivityTag/getActivityTagList',
	//获取物业活动列表
	ActivityList:'/activity/getEffectiveActivityList',
	//物业活动报名
	ActivityOrder:'/activityOrder/addActivityOrder',
	//获取物业通知标签列表
	NoticTagList:'/NoticTag/getTagList',
	//获取物业通知列表
	NoticList:'/notic/getNoticList',
	//体验式购物标签列表
	ShoppingTagList:'/ShoppingTag/getShoppingTagList',
	//体验式购物列表
	ShoppingList:'/shopping/getShoppingList',
	//体验式购物提交订单
	ShoppingOrder:'/shoppingOrder/addShoppingOrder',
	//雅诗阁生活分类列表
	LifeTypeList:'/lifetype/getLifeTypeList',
	//雅诗阁生活列表
	LifeList:'/life/getLifeList',
	//意见反馈  物业调查问卷列表
	FeedbackList:'/FeedbackList/getFeedbackList',
	//物业调查问卷信息
	Feedback:'/feedback/getFeedbackList',
	//提交物业调查问卷
	FeedbackResult:'/feedbackResult/addFeedbackResult',

}
//本地攻略
const Raiders = {
	//本地攻略类型列表
	PoiTypeList:'/poitype/getPoiTypeList',
	//本地攻略标签列表
	PoiTagList:'/PoiTag/getPoiTagList',
	//本地攻略列表
	PoiList:'/poi/getPoiList',
}

//评论
const Judge = {
    getPromotionCommentList:'/Comment/getPromotionCommentList', //促销
    getPoiCommentList:'/Comment/getPoiCommentList', //本地攻略
    getLifeCommentList:'/Comment/getLifeCommentList', //雅诗阁生活
	commitJudge:'/Comment/createPoiComment'
}

function apiGet (url, data) {
    var resultObj = getObj(data);
    return axios.get(url, resultObj)
}

function apiPost (url, data) {
    var resultObj = getObj(data);
	return axios.post(url, resultObj)
}
var getObj = function(params){
    var str = "";
    var time = "";
    $.ajax({
        url:'http://47.93.201.72:80/system/getTime',
        type:'GET', //GET
        async:false,    //或false,是否异步
        timeout:5000,    //超时时间
        dataType:'json',    //返回的数据格式：json/xml/html/script/jsonp/text
        success:function(data,textStatus,jqXHR){
            time = data.data.time;
        }
    });
    params.time = time;
    let p = Object.keys(params).sort();
    for (var k in p){
        str += (p[k]+params[p[k]]);
	}
    str += "CtUyV$8MGoK8u5L*P0Q50T/b8S9iclS*LQqo";
    var sign = md5(str);
    params.sign = sign;
    return params;
}

axios.defaults.transformRequest = [function(data) {
	let ret = Object.keys(data).map(function(key) {
		return encodeURIComponent(key) + '=' + encodeURIComponent(data[key])
	}).join('&')
	return ret
}]

export default {
	grounpList:(query) => apiPost(Home.groupListUrl,query),
	groupListbyId:(query) => apiPost(Home.groupListbyIdUrl,query),
	loginGuest:(query) => apiPost(Login.LoginUrl,query),
	loginStaff:(query) => apiPost(Login.staffLoginUrl,query),
	getUsers:(query) => apiPost(Login.getUsersUrl,query),
    translate:(query) => apiPost(Translate.translate,query),
    translateLanguageList:(query) => apiPost(Translate.translateLanguageList,query),
    home:(query) => apiPost(Home.homeUrl,query),
    news:(query) => apiPost(Home.newsUrl,query),
	showingorder:(query) => apiPost(Home.showingorderUrl,query),
    getHotelTelList:(query) => apiPost(Phone.hotelTelList,query),
	roomResList:(query) => apiPost(Home.roomResListUrl,query),
	rss:(query) => apiPost(Home.rssUrl,query),
	myBill:(query) => apiPost(Home.myBillUrl,query),
    getPromotionTagList:(query) => apiPost(Promotion.PromotionTagList,query),
    getPromotionList:(query) => apiPost(Promotion.PromotionList,query),
    getNewsTagList:(query) => apiPost(Promotion.NewsTagList,query),
    getNewsList:(query) => apiPost(Promotion.NewsList,query),
    getGroupNewsTagList:(query) => apiPost(Promotion.GroupNewsTagList,query),
    getGroupNewsList:(query) => apiPost(Promotion.GroupNewsList,query),
    getGroupActivityTagList:(query) => apiPost(Promotion.GroupActivityTagList,query),
    getGroupActivityList:(query) => apiPost(Promotion.GroupActivityList,query),
	getGroupNoticeList:(query) => apiPost(Promotion.GroupNoticeList,query),
	getPoiTypeList:(query) => apiPost(Raiders.PoiTypeList,query),
	getPoiTagList:(query) => apiPost(Raiders.PoiTagList,query),
	getPoiList:(query) => apiPost(Raiders.PoiList,query),
	
    getActivityTagList:(query) => apiPost(Community.ActivityTagList,query),
    getActivityList:(query) => apiPost(Community.ActivityList,query),
    ActivityOrder:(query) => apiPost(Community.ActivityOrder,query),
    getNoticTagList:(query) => apiPost(Community.NoticTagList,query),
    getNoticList:(query) => apiPost(Community.NoticList,query),
    getShoppingTagList:(query) => apiPost(Community.ShoppingTagList,query),
    getShoppingList:(query) => apiPost(Community.ShoppingList,query),
    getShoppingOrder:(query) => apiPost(Community.ShoppingOrder,query),
    getLifeTypeList:(query) => apiPost(Community.LifeTypeList,query),
    getLifeList:(query) => apiPost(Community.LifeList,query),
    getFeedbackList:(query) => apiPost(Community.FeedbackList,query),
    getFeedback:(query) => apiPost(Community.Feedback,query),
    addFeedbackResult:(query) => apiPost(Community.FeedbackResult,query),
	getHotelDetail:(query) => apiPost(Home.hotelDetailUrl,query), 
	helloApi:(query) => apiPost(Hello.helloApi,query),
	tabLanguage:(query) => apiPost(Home.tabLanguageUrl,query),
    help:(query) => apiPost(Home.helpUrl,query),
    groupDetail:(query) => apiPost(Home.groupDetailUrl,query),
    getOrderList:(query) => apiPost(Home.orderUrl,query),
    changeOrder:(query) => apiPost(Home.changeOrderUrl,query),
    JudgeList:(query) => apiPost(Judge.JudgeList,query),
    commitJudge:(query) => apiPost(query.url,query.data),
    appUpdate:(query) => apiPost(Home.appUpdateUrl,query),
    appStart:(query) => apiPost(Home.appStartUrl,query),
    getJudge:(query) => apiPost(query.url,query.data)
}