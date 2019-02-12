import Vue from 'vue'
import Router from 'vue-router'

/**
 * 通用模块
 */
import guestCenter from '../components/common/menu/guestCenter.vue' //next
import error from '../components/404.vue' //next

/**
 * 首页
 */
import home from '../components/home/home.vue'
import unlogin from '../components/home/n_home.vue'
import s_home from '../components/home/s_home.vue'
import guide from '../components/home/guide.vue'
import appartmentDetail from '../components/apartment/appartmentDetail.vue'
import virtual from '../components/apartment/virtual.vue'
import help from '../components/common/menu/help.vue'
import aboutUs from '../components/common/menu/aboutUs.vue'
import appartmentStyle from '../components/apartment/appartmentStyle.vue'
import photo from '../components/apartment/photo.vue'
import service from '../components/home/shortcut/service.vue'
import news from '../components/mycenter/news.vue'
import notice from '../components/mycenter/notice.vue'
import newsDetail from '../components/mycenter/newsDetail.vue'
import addShowingOrder from '../components/apartment/addShowingOrder.vue'
import article from '../components/apartment/article.vue'
import gsm from '../components/mycenter/gsm.vue'
import facilities from '../components/apartment/facilities.vue'
import booking from '../components/apartment/booking.vue'
import fireDetail from '../components/apartment/fireDetail.vue'
import homebooking from '../components/apartment/homebooking.vue'
import trafficDetail from '../components/apartment/trafficDetail.vue'
import invoices from '../components/mycenter/invoices.vue'
import invoicesHeader from '../components/mycenter/invoicesHeader.vue'
import invoicesManage from '../components/mycenter/invoicesManage.vue'
import helpDetail from '../components/common/menu/helpDetail.vue'
import order from '../components/mycenter/order.vue'

/**
 * 搜索
 */
import search from '../components/search/search.vue'
import searchResult from '../components/search/searchResult.vue'

/**
 * 登录模块
 */
import loginForGuest from '../components/login/loginForGuest.vue'
import loginForEmployee from '../components/login/loginForEmployee.vue'

/**
 * 快捷功能
 */
import phone from '../components/function/phone.vue'
import airport from '../components/function/airport.vue'
import airportInfo from '../components/function/airportInfo.vue'
import translate from '../components/function/translate.vue'
import airinfo from '../components/function/airinfo.vue'
import research from '../components/function/research.vue'
/**
 *社区生活
 */
import communityLife from '../components/communityLife/communityLife.vue'
import lifeDetail from '../components/communityLife/lifeDetail.vue'
import propertyActivity from '../components/communityLife/propertyActivity.vue'
import propertyDetail from '../components/communityLife/propertyDetail.vue'
import opinionSurvey from '../components/communityLife/opinionSurvey.vue'
import opinionDetail from '../components/communityLife/opinionDetail.vue'
import shopping from '../components/communityLife/shopping.vue'
import shoppingDetail from '../components/communityLife/shoppingDetail.vue'
import buy from '../components/communityLife/buy.vue'
import ascottLife from '../components/communityLife/ascottLife.vue'
import apply from '../components/communityLife/apply.vue'
import feedback from '../components/communityLife/feedbackList.vue'
import feedbackDetail from '../components/communityLife/feedbackDetail.vue'
import ssr from '../components/communityLife/ssr.vue'
import mybill from '../components/mycenter/mybil.vue'
import lifeStyle from '../components/communityLife/lifeStyle.vue'
import lifeStyleDetail from '../components/communityLife/lifeStyleDetail.vue'
import ssrDetail from '../components/communityLife/ssrDetail.vue'
/**
 *促销优惠
 */
import promotiom from '../components/promotiom/promotiom.vue'
import promotionDetail from '../components/promotiom/promotionDetail.vue'
/**
 * 本地攻略
 */
import raiders from '../components/localRaiders/raiders.vue'
import raidersDetail from '../components/localRaiders/raidersDetail.vue'
import pdf from '../components/common/pdf.vue'
import judge from '../components/judge/judge.vue'
import judgeInput from '../components/judge/judgeInput.vue'
/**
 * 本地攻略
 */
import map from '../components/map/map.vue'



Vue.use(Router)

export default new Router({
    routes: [
	    {path: '/2',name: 'translate',component: translate}, //测试使用
        {path: '/',
            redirect:to => {
	            if(localStorage.TOKEN){
		            //如果已登录跳转至已登录首页
		            return { path: '/home'}
	            }else{
		            //如果未登录跳转至未登录首页
		            return { path: '/unlogin'}
	            }
            }
        },//引导页
        {path: '/guide',name: 'guide',component: guide},//首次引导页
        {path: '/error',name: 'error',component: error},//首次引导页
        {path: '/home',name: 'home',component: home,meta:{allowBack: false}},//首页
        {path: '/gsm',name: 'gsm',component: gsm},//gsm
        {path: '/home/trafficDetail',name: 'trafficDetail',component: trafficDetail},//交通信息
        {path: '/ssr',name: 'ssr',component: ssr},//定于
        {path: '/mybill',name: 'mybill',component: mybill},//账单
        {path: '/invoices',name: 'invoices',component: invoices},//发表管理
        {path: '/invoicesHeader',name: 'invoicesHeader',component: invoicesHeader},//发表抬头管理
        {path: '/invoicesManage',name: 'invoicesManage',component: invoicesManage},//发表管理
        {path: '/booking',name: 'booking',component: booking},//预定
        {path: '/facilities',name: 'facilities',component: facilities},//酒店设施
        {path: '/s_home',name: 's_home',component: s_home},//未登陆首页2
        {path: '/home/service',name: 'service',component: service},//服务
        {path: '/home/news',name: 'news',component: news},//消息
        {path: '/home/notice',name: 'notice',component: notice},//通知
        {path: '/home/article',name: 'article',component: article},//客房物品
        {path: '/home/news/newDetail',name: 'newsDetail',component: newsDetail},//消息
        {path: '/home/virtual',name: 'virtual',component: virtual},//全景图
        {path: '/home/homebooking',name: 'homebooking',component: homebooking},//全景图
        {path: '/addShowingOrder',name: 'addShowingOrder',component: addShowingOrder},//预约
        {path: '/appartmentDetail',name: 'appartmentDetail',component: appartmentDetail},//物业详情
        {path: '/appartmentStyle',name: 'appartmentStyle',component: appartmentStyle},//房型详情
        {path: '/fireDetail',name: 'fireDetail',component: fireDetail},//房型详情
        {path: '/photo',name: 'photo',component: photo},//物业详情
        {path: '/unlogin',name: 'unlogin',component: unlogin},//未登陆首页
        {path: '/search',name: 'search',component: search},//搜索
        {path: '/search/searchResult',name: 'searchResult',component: searchResult},//搜索结果
        {path: '/loginforguest',name: 'loginForGuest',component: loginForGuest},//用户登录
        {path: '/loginForEmployee',name: 'loginForEmployee',component: loginForEmployee},//员工登陆
        {path: '/phone',name: 'phone',component: phone},//常用电话
        {path: '/airinfo',name: 'airinfo',component: airinfo},//航班信息
        {path: '/research',name: 'research',component: research},//客户调查表 
        {path: '/airport',name: 'airport',component: airport},//航班信息
        {path: '/airportInfo',name: 'airportInfo',component: airportInfo},//航班信息查询列表
        {path: '/translate',name: 'translate',component: translate},//翻译
        {path: '/communityLife',name: 'communityLife',component: communityLife,meta:{allowBack: false}},//社区生活
	    {path: '/propertyDetail',name: 'propertyDetail',component: propertyDetail},//社区生活
	    {path: '/lifeStyle',name: 'lifeStyle',component: lifeStyle},//生活服务
	    {path: '/lifeStyleDetail',name: 'lifeStyleDetail',component: lifeStyleDetail},//生活服务详情
        {path: '/propertyActivity',name: 'propertyActivity',component: propertyActivity},//物业活动
        {path: '/opinionSurvey',name: 'opinionSurvey',component: opinionSurvey},//物业通知
        {path: '/opinionDetail',name: 'opinionDetail',component: opinionDetail},//物业通知详情
        {path: '/shopping',name: 'shopping',component: shopping},//体验式购物
        {path: '/shopping/shoppingDetail',name: 'shoppingDetail',component: shoppingDetail},//购物详情
        {path: '/buy',name: 'buy',component: buy},//购买
        {path: '/ascottLife',name: 'ascottLife',component: ascottLife},//雅诗阁生活
        {path: '/lifeDetail',name: 'lifeDetail',component: lifeDetail},//雅诗阁生活
        {path: '/promotion',name: 'promotiom',component: promotiom,meta:{allowBack: false}},//促销优惠
        {path: '/promotionDetail',name: 'promotionDetail',component: promotionDetail},//促销优惠详情
        {path: '/raiders',name: 'raiders',component: raiders,meta:{allowBack: false}},//本地攻略
        {path: '/raidersDetail',name: 'raidersDetail',component: raidersDetail},//本地攻略
        {path: '/apply',name: 'apply',component: apply},//物业活动报名
        {path: '/pdf',name: 'pdf',component: pdf},//物业活动报名
        {path: '/feedback',name: 'feedback',component: feedback},//物业调查问卷
        {path: '/feedbackDetail',name: 'feedbackDetail',component: feedbackDetail},//物业调查问卷详情
        {path: '/help',name: 'help',component: help},//help
        {path: '/aboutUs',name: 'aboutUs',component: aboutUs},//aboutUs
        {path: '/life/lifeStyleDetail',name: 'lifeStyleDetail',component: lifeStyleDetail},//生活详情
        {path: '/ssr/ssrDetail',name: 'ssrDetail',component: ssrDetail},//订阅详情
        {path: '/helpDetail',name: 'helpDetail',component: helpDetail},//帮助详情
        {path: '/order',name: 'order',component: order},//订单详情
        {path: '/judge',name: 'judge',component: judge},//评论列表
        {path: '/judgeInput',name: 'judgeInput',component: judgeInput},//评论
        {path: '/map',name: 'map',component: map},//评论
    ]
})
