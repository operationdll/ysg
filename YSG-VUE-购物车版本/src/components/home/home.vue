<template>
	<div class="home">
		<section class="index">
			<!--页面遮罩-->
			<div class="mark" v-show="markFlag" @click="hideSlide"></div>
			<!--导航菜单-->
			<yd-navbar title="" class="index_tab">
				<span slot="left" class="back" @click="goBack" v-if="pageFlag"></span>
				<span slot="left" class="user" @click="showCenter" v-else></span>
				<span class="menu" @click="showMenu" slot="right"></span>
			</yd-navbar>
			<!--banner-->
			<scroller ref="scroller">
				<section class="index_top" :style="{backgroundImage: 'url(' + homeData.indexBackground + ')'}">
					<span class="logo"></span>
					<h4 class="user_name">{{fullName ? fullName: 'Visitor'}}</h4>
					<ul class="weather_date">
						<li class="date">
							<div class="left"></div>
							<div class="right">
								<p>{{startTime}}</p>
								<!--<p>The remaining 14 days</p>-->
							</div>
						</li>
						<li class="weather">
							<div class="right">
								<img :src="weatherSrc" alt="">
							</div>
							<div class="left">
								<p>{{weather.weatherEn}}</p>
								<p>{{weather.temperatureFrom}} ~ {{weather.temperatureTo}}</p>
							</div>
						</li>
					</ul>
					<div class="smart_home">
						<button type="button"  @click="goNext(shortcutList[0].key)" ><img style="width: .4rem;vertical-align: middle;display: inline-block;" src="../../assets/images/service-btn.png" alt=""> {{shortcutList[0].title}}</button>
					</div>
				</section>
				<!--快捷功能-->
				<section class="index_content">
					<div class="swiper-container">
						<div class="swiper-wrapper">
							<div class="swiper-slide" v-for="(item,index) in shortcutList" @click="goNext(item.key)">
								<img :src="item.imgSrc">
								<p>{{item.title}}</p>
							</div>
						</div>
					</div>
				</section>
				<!--酒店详情-->
				<section class="app_content" style="background: #fff;padding-bottom: 1rem;">
					<h4>{{ hotelDetail.name }}</h4>
					<div class="map_content" @click="goLocation">
						<img :src="hotelDetail.localpic">
					</div>
					<ul class="add">
						<li class="col-7">
							<p>{{home.address}}</p>
						</li>
						<li class="col-3"> </li>
					</ul>
					<section class="apart_profile">
						<h4>{{language.home.profile}}</h4>
						<P>{{hotelDetail.introduction}}</P>
						<!--<a href="#">more</a>-->
					</section>
					<section class="equipment">
						<div class="swiper-container">
							<div class="swiper-wrapper">
								<div class="swiper-slide" v-for="item in facListShow">
									<router-link :to="{path:'/facilities',query:{data:item}}">
										<img :src="item.imgSrc">
									</router-link>
								</div>
							</div>
						</div>
					</section>
					<section class="virtual">
						<h4><i></i> {{language.home.virtual}}</h4>
						<div class="swiper-container">
							<div class="swiper-wrapper">
								<div class="swiper-slide" v-for="(item,index) in hotelDetail.panoramicList">
									<router-link :to="{path:'/home/virtual', query:{src:item.panoramic, pageType:this.pageType,title:item.title}}"><img :src="item.pic"></router-link>
									<p style="text-align:center;font-size:0.24rem;color:#999;width:90%;overflow:hidden;padding-top:.2rem;">{{item.title}}</p>
								</div>
							</div>
						</div>
					</section>
					<section class="apartment">
						<h4>{{language.home.apartment}}</h4>
						<div class="swiper-container" id="test">
							<div class="swiper-wrapper">
								<div class="swiper-slide" v-for="(item, index) in hotelDetail.roomTypeList">
									<router-link :to="{path:'/appartmentStyle',query:{index:index,bookUrl:hotelDetail.bookurl}}">
										<img :src="item.pic">
									</router-link>
										<h5>{{item.title}}</h5>
										<ul class="apart_style">
											<li class="col-5"><i></i>{{language.apartment.maxPerson}} {{item.personcount}} {{language.apartment.person}}(s)</li>
											<li class="col-5"><i></i>{{item.bedtype}}</li>
										</ul>
										<ul class="apart_util">
											<li>
												<div class="col-5">{{language.apartment.fcount}}:</div>
												<div class="col-5">{{item.roomcount}}</div>
											</li>
											<li>
												<div class="col-5">{{language.apartment.size}}:</div>
												<div class="col-5">{{item.size}}</div>
											</li>
										</ul>
										<ul class="apart_btn">
											<li @click="openBooking(hotelDetail.bookurl)">
												{{language.apartment.book}}
											</li>
											<!--<li class="col-4">RESERVE</li>-->
										</ul>
								</div>

							</div>
						</div>
					</section>
					<section class="fire">
						<h4>{{language.home.fire}}</h4>
						<div class="swiper-container">
							<div class="swiper-wrapper">
								<div class="swiper-slide" v-for="item in hotelDetail.floorList">
									<router-link :to="{path:'/fireDetail', query:{info:item}}">
										<img :src="item.pic">
									</router-link>
								</div>
							</div>
						</div>
					</section>
					<section class="how">
						<h4>{{language.home.how}}</h4>
						<div class="how_phone">
							<yd-cell-group>
								<yd-cell-item arrow v-for="item in hotelDetail.trafficList" @click.native="goTraffic(item)">
									<img slot="icon" src="../../assets/images/icon-bike.png" v-if="item.introduct == 'Bicycle' || item.introduct == '自行车'">
									<img slot="icon" src="../../assets/images/icon-car.png" v-else-if="item.introduct == 'Self-Driving' || item.introduct == '租车自驾服务'">
									<img slot="icon" src="../../assets/images/icon-taxi.png" v-else-if="item.introduct == 'Taxi' || item.introduct == '出租车'">
									<img slot="icon" src="../../assets/images/icon-bus.png" v-else-if="item.introduct == 'Public bus' || item.introduct == '北京公交'">
									<img slot="icon" src="../../assets/images/icon-tbus.png" v-else-if="item.introduct == 'Subway' || item.introduct == '北京地铁'">
									<span slot="left">{{item.introduct}}</span>
								</yd-cell-item>
							</yd-cell-group>

						</div>
					</section>

				</section>
			</scroller>
		</section>
		<yd-actionsheet :items="myItems" v-model="tabLanShow"></yd-actionsheet>
		<!--底部菜单、个人中心、 设置项-->
		<v-foot></v-foot>
		<v-menu v-show="menuFlag"></v-menu>
		<v-guestCenter v-show="centerFlag"></v-guestCenter>
	</div>
</template>

<script>
	import {Actionsheet, Scroller} from 'vux'
	import { mapState } from 'vuex'
	import {mapGetters} from 'vuex'
	import foot from '../foot.vue'
	import menu from '../common/menu.vue'
	import guestCenter from '../common/menu/guestCenter.vue'
	export default {
		data() {
			return {
				menuFlag: false,
				centerFlag: false,
				menuSlide: false,
				centerSlide: false,
				homeData: {},
				fullName: '',
				startTime: '',
				weather: {},
				shortcutList: [], //按钮组
				localshortcutList: [],//本地按钮组
				markFlag: false,
				actionSheet: false,
				lat: '',
				lng: '',
				idType: '',
				myItems: [],
				details:{},
				pageType:'',
				distance:'',
				facList:[],
				facListShow:[],
				sHeightPx: '400px',
				weatherSrc:'',
				hotelid:'',
				pageFlag:false
			};
		},
		created: function () {
		    if (localStorage.baseHotelId != localStorage.HOTELID) {
                localStorage.HOTELID = localStorage.baseHotelId
			}
            //设置当前状态
            localStorage.stateFlag = 1;
			//判断是登录主页还是查询主页
			if( this.$route.query.hotelid){
				localStorage.searchFlag = 's_home'
				this.hotelid = this.$route.query.hotelid;
				localStorage.HOTELID = this.$route.query.hotelid;
			}else{
				localStorage.searchFlag = 'home'
				this.hotelid = localStorage.HOTELID;
				this.pageFlag = true;
			}

			//判断个人中心图标显示
			if(this.$route.query.page == 'unlogin' || localStorage.spage == 1){
				localStorage.spage = 1;
				this.pageFlag = true;
			}else if(this.$route.query.page == 'search' || localStorage.spage == 2){
				localStorage.spage = 2;
				this.pageFlag = true;
			}else{
				this.pageFlag = false;

			}

			if(localStorage.searchFlag == 'home'){
				this.pageFlag = false;
			}


			//获取物业详情
			this.fullName = localStorage.FULLNAME;
			//初始化数据
			this.localshortcutList=[
				{key: "reservation", title: '', imgSrc: require('../../assets/images/icon-book.png'), linkTo: '/home/homebooking'},
				{key: "tanslate",title: '',imgSrc: require('../../assets/images/icon-translate.png'),linkTo: '/translate'},
				{key: "service",title: '',imgSrc: require('../../assets/images/icon-service.png'),linkTo: '/home/service'},
				{key: "flight", title: '', imgSrc: require('../../assets/images/icon-airport.png'), linkTo: '#'},
				{key: "phone", title: '', imgSrc: require('../../assets/images/icon-phone.png'), linkTo: '/phone'},
				{key: "guestmail", title: '', imgSrc: require('../../assets/images/icon-phone.png'), linkTo: '#'},
				{key: "smarthome", title: '', imgSrc: require('../../assets/images/icon-smart.png'), linkTo: '#'},
				{key: "news", title: '', imgSrc: require('../../assets/images/icon-news-add.png'), linkTo: '/ssr'},
				{key: "invoice", title: '', imgSrc: require('../../assets/images/icon-bill.png'), linkTo: '/mybill'},
				{key: "lifeservice", title: '', imgSrc: require('../../assets/images/icon-lifestyle.png'), linkTo: ''},
                {key: "shop", title: '', imgSrc: require('../../assets/images/Shopping@2x.png'), linkTo: ''},
			];
			this.myItems=[
				{
					label: 'English',
					method: () => {
						this.hideAction('en')
					}
				},
				{
					label: '中文',
					method: () => {
						this.hideAction('zh')
					}
				},
//                {
//                    label: '日本語の',
//                    method: () => {
//                        this.hideAction('jp')
//                    }
//                },
                {
                    label: 'Cancel',
                    method: () => {
                        _this.$store.dispatch('changeLanguage')
                    }
                }
			];
			this.facList=[
				{icon:'tv',imgSrc:require('../../assets/images/Group 5 Copy@2x.png')},
				{icon:'restaurant',imgSrc:require('../../assets/images/dining room@2x.png')},
				{icon:'wifi',imgSrc:require('../../assets/images/wifi@2x.png')},
				{icon:'Gymnasium',imgSrc:require('../../assets/images/gym@2x.png')},
				{icon:'swimmingpool',imgSrc:require('../../assets/images/icon-swimmingsool.png')},
				{icon:'residentslounge',imgSrc:require('../../assets/images/icon-laug.png')},
				{icon:'Reading Room',imgSrc:require('../../assets/images/icon-reading.png')},
				{icon:"Children's Play Room",imgSrc:require('../../assets/images/icon-child.png')},
				{icon:'Sauna Room',imgSrc:require('../../assets/images/icon-sauan.png')},
			];

			//设置首页标识
			localStorage.menuFlag = 'home'
			//获取登录身份
			this.idType = this.$route.query.type;
			//设置底部菜单状态
			localStorage.footFlag = 1;
			//获取当前日期
			var m = new Array("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Spt", "Oct", "Nov", "Dec");
			var tDay = new Date();
			this.startTime = m[tDay.getMonth()] + ' ' + tDay.getDate()
			//初始化语言
			this.$store.dispatch('firstLanguage');
			let params = {
				hotelid: this.hotelid,
				lang: localStorage.LANGUAGE

			}
			this.$store.dispatch('getHome', params).then((res) => {
				if (res.code == 0) {
					//全部数据
					this.homeData = this.home.data
					//天气数据
					this.weather = this.home.data.wetherInfo;
					if(true){
						this.weatherSrc =  require('../../assets/images/icon-w-cloud.png')
					}

					//经纬度
					this.lat = this.home.data.lat;
					this.lng = this.home.data.lng;

					//整理按钮结果集
					for (var item in this.home.data.shortcutList) {
						for (var i in this.localshortcutList) {
							if (this.home.data.shortcutList[item].key == this.localshortcutList[i].key) {
								this.localshortcutList[i].title = this.home.data.shortcutList[item].title
								this.shortcutList.unshift(this.localshortcutList[i]);
							}
						}
					}

                    $(document).ready(function () {
                        new Swiper('.index_content .swiper-container', {
                            pagination: '.swiper-pagination',
                            slidesPerView: 4,
                            paginationClickable: true,
                            spaceBetween: 0
                        });
                    })


				} else {
					this.$dialog.toast({mes: res.msg, timeout: 1000});
				}

			});
			var _this = this;



			//获取物业详情
			let params2 = {
				hotelid: this.hotelid,
				lang: localStorage.LANGUAGE
			}
			this.$store.dispatch('getHotelDetail', params2).then((res) => {
				this.details= this.hotelDetail
				this.lat = this.hotelDetail.lat;
				this.lng = this.hotelDetail.lng;

				localStorage.bookUrl = this.hotelDetail.bookurl

				//重组图标数据
				for(var item in this.hotelDetail.facilitiesList){
					for(var i in this.facList){
						if(this.hotelDetail.facilitiesList[item].icon == this.facList[i].icon){
							this.facList[i].pdf = this.hotelDetail.facilitiesList[item].pdf
							this.facList[i].video = this.hotelDetail.facilitiesList[item].video
							this.facList[i].detail = this.hotelDetail.facilitiesList[item].detail
                            this.facList[i].name = this.hotelDetail.facilitiesList[item].name
							this.facListShow.push(this.facList[i])
						}
					}
				}
				//初始化swiper
				$(document).ready(function () {
				    setTimeout(function () {
                        var swiper = new Swiper('.equipment .swiper-container', {
                            pagination: '.swiper-pagination',
                            slidesPerView: 6,
                            paginationClickable: true,
                            spaceBetween: 0
                        });
                        var swiper3 = new Swiper('.apartment .swiper-container', {
                            pagination: '.swiper-pagination',
                            slidesPerView: 1.1,
                            paginationClickable: true,
                            spaceBetween: 15
                        });
                        var swiper4 = new Swiper('.fire .swiper-container', {
                            pagination: '.swiper-pagination',
                            slidesPerView: 1.2,
                            paginationClickable: true,
                            spaceBetween: 15
                        });
                        var swiper2 = new Swiper('.virtual .swiper-container', {
                            pagination: '.swiper-pagination',
                            slidesPerView: 3,
                            paginationClickable: true,
                            spaceBetween: 0
                        });
                    },300)

				});

			})


		},
		mounted:function () {
            //一级页面falg
            isHomePage(1)
            //初始化swiper
            $(document).ready(function () {
                $('.mask-actionsheet').click(function () {
                    _this.$store.dispatch('changeLanguage2')
                })
                $('.actionsheet-action').click(function () {
                    _this.$store.dispatch('changeLanguage2')
                })

            })
        },
		methods: {
			//显示菜单
			showMenu: function () {
				this.menuFlag = true; //显示菜单
				this.centerFlag = false; //隐藏个人中心
				$('.index').css({
					'position':'absolute',
					'height': '100%',
					'width': '100%',
					'overflow': 'hidden',
					'z-index':'350'
				})
				if (this.menuSlide == false) {
					$('.index').css({
						'left': '-5rem',
					});
					$('footer').css({
						'left': '-5rem',
					});

					$('.mark').css({
//						'left':'-5rem',
						'opacity': 1
					});
					this.menuSlide = true;
					this.markFlag = true;
				}
			},
			//显示个人中心
			showCenter: function () {
				$('.index').css({
					'position':'absolute',
					'height': '100%',
					'width': '100%',
					'overflow': 'hidden',
					'z-index':'350'
				})
				this.menuFlag = false; //隐藏菜单
				this.centerFlag = true; //显示个人中心
				if (this.centerSlide == false) {
					$('.index').css({
						'left': '4.5rem',
					});
					$('footer').css({
						'left': '4.5rem',
					});
					$('.mark').css({
						'opacity': 1
					});
					this.centerSlide = true;
					this.markFlag = true;
				}
			},
			//点击页面，收起抽屉效果
			hideSlide: function () {
			    let _this = this
				//如果是菜单展开，收起菜单
				if (this.menuSlide) {
					$('.index').css({
						'left': 0,
					});
					$('footer').css({
						'left': 0,
					});
					$('.mark').css({
						'opacity': 0
					});
					this.menuSlide = false;
					this.markFlag = false;
					setTimeout(function () {
                        _this.menuFlag = false;
                    },200)
				}
				//如果是个人中心展开，收起个人中心
				if (this.centerSlide) {
					$('.index').css({
						'left': 0,
					});
					$('footer').css({
						'left': 0,
					});
					$('.mark').css({
						'opacity': 0
					});
					this.centerSlide = false;
					this.markFlag = false;
                    setTimeout(function () {
                        _this.centerFlag = false;
                    },300)
				}

			},
			hideAction: function (key) {
				let _this = this;
				//更新本地语言标识
				localStorage.LANGUAGE = key;
				var lanKey;
				if(key == 'jp'){
				    lanKey = 'en'
				} else {
				    lanKey =key
				}
				this.$store.dispatch('updateLanguage', lanKey);
				this.$store.dispatch('changeLanguage');
				//更新首页数据
				let params = {
					hotelid: this.hotelid,
					lang: localStorage.LANGUAGE

				}
				this.$store.dispatch('getHome', params).then(function (res) {
					_this.shortcutList = [];
					//整理按钮结果集
					for (var item in _this.home.data.shortcutList) {
						for (var i in _this.localshortcutList) {
							if (_this.home.data.shortcutList[item].key == _this.localshortcutList[i].key) {
								_this.localshortcutList[i].title = _this.home.data.shortcutList[item].title
								_this.shortcutList.unshift(_this.localshortcutList[i]);
							}
						}
					}
				});

				//更新物业详情数据
				let params2 = {
					hotelid: this.hotelid,
					lang: localStorage.LANGUAGE
				}
				this.$store.dispatch('getHotelDetail', params2)

			},
			//地图导航
			goLocation:function () {
			    if(localStorage.HOTELID == 1){
			        if(localStorage.TOKEN){
                        this.$router.push('/shopping')
					} else {
                        this.$router.replace('/loginforguest')
					}
				} else {
                    this.$router.push('/map')
				}
			},
			goNext:function (key) {
				let _this = this;
				switch (key){
					case 'control':
						_this.$router.push('#')
						break;
					case 'reservation':
                        if(localStorage.TOKEN){
                            openBooking(localStorage.bookUrl)
                        }else{
                            _this.$router.replace('/loginforguest')
                        }
						break;
					case 'tanslate':
						_this.$router.push('/translate')
						break;
					case 'service':
						if(localStorage.TOKEN){
							if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){
								openFile(localStorage.serviceUrl)
							} else {
								openUrl(localStorage.serviceUrl,' ')
							}
						}else{
							_this.$router.replace('/loginforguest')
						}
						break;
					case 'flight':
						_this.$router.push('#')
						break;
					case 'phone':
						_this.$router.push('/phone')
						break;
					case 'invoice':
						_this.$router.push('/mybill')
						break;
					case 'news':
						_this.$router.push('/ssr')
						break;
                    case 'lifeservice':
                        _this.$router.push('/lifeStyle')
                        break;
                    case 'shop':
                        if(localStorage.TOKEN){
                            _this.$router.push({path:'/shopping',query:{pageFlag:'home'}})
						} else {
                            _this.$router.replace('/loginforguest')
						}

                        break;
				}
			},
			goBack:function () {
				if(this.$route.query.page == 'unlogin' || localStorage.spage == 1){
					this.$router.push('/unlogin')
				} else if(this.$route.query.page == 'search' || localStorage.spage == 2){
					this.$router.push('/search')
				}
			},
			goTraffic:function (item) {
			    this.$router.push({path:'/home/trafficDetail', query:{info:item}})
            },
			openBooking:function (url) {
                openBooking(url)
            }

		},
		components: {
			'v-foot': foot,
			'v-menu': menu,
			'v-guestCenter': guestCenter,
			Actionsheet,
		},
		computed: {
			...mapState({
				language: state => state.language.language,
				home: state => state.home.home,
				tabLanShow:state => state.language.tabLanShow,
				hotelDetail: state => state.home.hotelDetail,
			})

		},
	};
</script>
<style>
	.index_top{width: 100%;height: 5.6rem;margin-top: -20px;background: url("../../assets/images/index_bg.jpg")center no-repeat;background-size: cover;position: relative;}
	.logo{display: block;width: 2.4rem;height: 3rem;background: url("../../assets/images/logo@2x.png")center no-repeat;background-size: 2.4rem;margin: 0 auto;}
	.user_name{font-size: .6rem;color: #f2c466;font-weight: normal;padding-left: .55rem;}
	.weather_date{padding: 0 .55rem;color: #fff;overflow: hidden;margin-top: 0.2rem;}
	.weather_date>li.date{float: left;font-size: .28rem;width: 30%;}
	.weather_date>li.weather{float: right;font-size: .28rem;width: 70%;}
	.weather_date>li.date .left{float: left;width: .6rem;height: .6rem;background: url("../../assets/images/icon_date.png")center no-repeat;background-size: .6rem;margin-right: .12rem;}
	.weather_date>li.weather .left{float: right;}
	.weather_date>li.weather .right{float: right;width: .7rem;height: .6rem;background-size: .7rem;margin-left: .12rem;}
	.weather_date>li.weather .right img{width: 100%;}
	.smart_home{text-align: center;position: absolute;width: 100%;left:0;bottom: -.45rem;}
	.smart_home button{width: 6.55rem;height: .9rem;background: url("../../assets/images/btn_bg.png")center no-repeat;background-size: cover;border: none;font-size: .36rem;color: #fff;}
	.index_content{padding: 0rem .3rem;background: #fff;padding-top: .7rem;}
	.index_content img{height: .5rem;margin: .1rem auto;}
	.index_content .swiper-slide{text-align: center;}
	.index_content .swiper-container{padding-bottom: .3rem;border-bottom: 1px solid #d4d4d4;}
	.map_list{background: #fff;padding: .3rem;}
	.index{box-shadow: 0px 0px 6px 2px rgba(0,0,0,.3);width: 100%;transition:left .5s;left:0;overflow-x: hidden;}
	.index_tab{position: absolute!important;top:20px;left:0;width: 100%;background: none!important;z-index: 20;}
	.index .m-navbar:after{border-bottom: none;}
	.index .user{display: inline-block;width: 100%;height: .5rem;background: url('../../assets/images/icon_user.png')left center no-repeat;background-size: .38rem;}
	.index .menu{display: inline-block;width: 100%;height: .5rem;background: url('../../assets/images/icon_menu.png')right center no-repeat;background-size: .38rem;}
	.map_list{padding: .3rem;}
	.map_list img{width: 100%;height: 2.5rem;}
	.map_list li:first-child{border-bottom: 1px solid #eee;}
	.pic_show{position: relative;}
	.pic_show h4{position: absolute;left:.2rem;top:.2rem;font-size: .36rem;color: #fff;width: 80%;font-weight: normal;}
	.address{overflow: hidden;margin-top: .34rem;margin-bottom: .34rem;}
	.address .left{float: left;width: 75%;font-size: .28rem;min-height: .5rem;}
	.address .right{float: left;width: 25%;text-align: right;}
	.address .right button{width: 95%;height: .64rem;background: #f0c366;color: #fff;font-size: .28rem;border-radius: .64rem;border: none;}
	.mark{position: absolute;left:0;top:0;width: 100%;height: 100%;z-index: 400;background: rgba(0,0,0,0.1);transition:left .5s;opacity: 0;}
	footer{transition:left .5s;}
	.smart_home i{display: inline-block;width: .3rem;height: .4rem;background: url("../../assets/images/icon_smartlock.png")center no-repeat;background-size: .28rem;vertical-align: middle}
	.ap_detail{font-size: .3rem;color: #666;font-weight: normal;padding: .2rem 0;}
	.appartment .appartment_top{width: 100%;height: 4.7rem;background: url('../../assets/images/index_bg.jpg')center no-repeat;background-size: cover;}
	.app_content{padding: .3rem;}
	.app_content h4{font-size: .4rem;color: #494949;}
	.map_content{width: 100%;height: 2.9rem;margin-top: .5rem;background: #fff;}
	.map_content img{width: 100%;height: 100%;}
	.app_content .add{overflow: hidden;font-size: .28rem;padding: .2rem 0;border-bottom: 1px solid #eee;}
	.app_content .add li{min-height: .5rem;}
	.app_content .add li:last-child{text-align: right;font-size: .36rem;color: #f0c366;}
	.apart_profile{padding: .5rem 0;border-bottom: 1px solid #eee;font-size: .28rem;}
	.apart_profile h4{font-size: .32rem;color: #2b2b2b;font-weight: normal;}
	.equipment{padding: .55rem 0;border-bottom: 1px solid #eee;}
	.equipment img{width: .55rem;margin: 0 auto;}
	.equipment .swiper-slide{text-align: center;}
	.virtual{padding: .35rem 0;border-bottom: 1px solid #eee;}
	.virtual h4{font-size: .32rem;font-weight: normal;color: #f0c366;padding-bottom: .4rem;}
	.virtual h4 i{float: left;width: .5rem;height: .32rem;background: url("../../assets/images/icon_720.png")center no-repeat;background-size: .5rem;margin-top: 3px;margin-right: 5px;}
	.virtual img{width:2.1rem;height: 1.65rem;}
	.apartment{padding-bottom: .55rem;border-bottom: 1px solid #eee;}
	.apartment h4,.fire h4,.how h4{font-size: .32rem;font-weight: normal;padding: .4rem 0;}
	.apartment img{width: 100%;height: 4.36rem;}
	.apartment h5{font-size: .3rem;color: #464646;padding: .2rem 0;}
	.apart_style{overflow: hidden;font-size: .28rem;color: #474747;}
	.apart_style i{float: left;width: .4rem;height: .26rem;margin-top: .1rem;margin-right: .1rem;}
	.apart_style li:first-child i{background: url("../../assets/images/icon_number@2x.png")center no-repeat;background-size: .5rem;}
	.apart_style li:last-child i{background: url("../../assets/images/icon_person@2x.png")center no-repeat;background-size: .5rem;}
	.apart_util{color: #474747;margin-top: .40rem;font-size: .28rem;overflow: hidden;}
	.apart_util>li>div:last-child{text-align: right;color: #2b2b2b;font-weight: 600;}
	.apart_btn{overflow: hidden;border: 1px solid #f0c366;margin-top: .5rem;}
	.apart_btn li{height: .74rem;line-height: .74rem;text-align: center;font-size: .32rem;color: #f0c366;}
	.apart_btn li{background: #f0c366;color: #fff;}
	/*.apart_btn li:last-child{background: #fff;}*/
	.fire{padding: .55rem 0;border-bottom: 1px solid #eee;}
	.fire img{width: 5.5rem;height: 3.7rem;}
	.how p,.hwo>div{font-size: .28rem;color:#4c4c4c;}
	.how_txt{margin-top: .5rem;}
	.how_txt ul li:first-child{color: #404040;font-size: .28rem;}
	.how_txt ul li:last-child{text-align: right;font-size: .28rem;}
	.how_txt p{color: #444;}
	.how_txt ul li:last-child i{display: inline-block;width: .3rem;height: .3rem;vertical-align: middle;margin-right: 5px;}
	.how_phone i{background: url("../../assets/images/icon_phone@2x.png")center no-repeat;background-size: .28rem;}
	.how_location i{background: url("../../assets/images/icon_locale@2x.png")center no-repeat;background-size: .28rem;}
	.appartment header{background:none!important;}
	.appartment .m-navbar:after{border-bottom: none;}
	.appartment .export{display: block;width: .4rem;height: .4rem;background: url("../../assets/images/icon_export.png")center no-repeat;background-size: .35rem;}
	.tabbar-fixed{z-index: 380!important;}
	.how .cell-icon{width: .6rem;}
</style>