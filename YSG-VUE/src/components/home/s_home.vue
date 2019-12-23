<template>
	<div class="home">
		<section class="index">
			<!--页面遮罩-->
			<div class="mark" v-show="markFlag" @click="hideSlide"></div>
			<!--导航菜单-->
      <div class="home-bnt-left" @click="goBack">
          <span class="home-back"></span>
      </div>
      <div class="home-bnt-right" @click="showMenu">
          <span class="back-menu"></span>
      </div>
			<!--banner-->
			<scroller ref="scroller">
				<section class="index_top" :style="{backgroundImage: 'url(' + homeData.indexBackground + ')'}">
					<span class="logo"></span>
					<h4 class="user_name" v-if="fullName">{{fullName }}</h4>
					<h4 class="user_name" v-else><router-link :to="{path:'/loginforguest',query:{pageType:'s_home'}}">Visitor</router-link></h4>
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
            <!--@click="goNext(shortcutList[0].key)" <img style="width: .4rem;vertical-align: middle;display: inline-block;" src="../../assets/images/service-btn.png" alt="">-->
						<button type="button" v-if="isZH">快捷服务</button>
            <button type="button" v-if="!isZH">Services</button>
					</div>
				</section>
				<!--快捷功能-->
				<section class="index_content">
					<div class="swiper-container">
						<div class="swiper-wrapper">
							<div class="swiper-slide" v-for="(item,index) in shortcutList" @click="goNext(item.key)">
								<img :src="item.imgSrc" style="width:30px;height:30px;">
								<p>{{item.title}}</p>
							</div>
						</div>
             <!-- Add Arrows -->
            <div v-show="shortcutList.length>4" class="swiper-button-next"></div>
            <div v-show="shortcutList.length>4" class="swiper-button-prev"></div>
					</div>
				</section>
				<!--酒店详情-->
				<section class="app_content" style="background: #fff;padding-bottom: 1rem;">
					<h4>{{ hotelDetail.name }}</h4>
          <div v-if="hotelDetail.robot_pic != ''" class="map_content">
						<img :src="hotelDetail.robot_pic" @click="goLocation(2)">
					</div>
          <!--成都物业曹操专车
          <div v-if="hotelid == 22" class="map_content">
						<img src="../../assets/caocao.jpg" @click="goCaocao()" style="border:1px solid black;"/>
					</div>-->
          <!--广告功能start-->
          <div style="margin-top: 0.5rem;display:none;" id="advDiv">
            <div style="background:#f6f6f6;width:100%;height:8px;display:block;"></div>
            <div style="display:flex;border-bottom:4px solid #f6f6f6;" v-for="(item,index) in advList" @click="goAdvDetail(item.id,item.typeid,item.type)">
              <div style="width: 107px; padding: 6px 16px 8px 16px;display:block;">
                <img :src="item.pic" style="width:75px;height:52px;"/>
              </div>
              <div style="flex-grow:1;padding: 6px 0px 8px;font-family:PingFangSC-Regular;font-size:14px;color:#4a4a4a;text-align:left;" v-if="isZH">
                {{item.name_lang1}}{{item.introduct_lang1}}<font style="font-family:PingFangSC-Regular;font-size:12px;color:#9b9b9b;margin-left:10px;">{{item.updatetime}}</font>
              </div>
              <div style="flex-grow:1;padding: 6px 0px 8px;font-family:PingFangSC-Regular;font-size:14px;color:#4a4a4a;text-align:left;" v-if="!isZH">
                {{item.name_lang2}}{{item.introduct_lang2}}<font style="font-family:PingFangSC-Regular;font-size:12px;color:#9b9b9b;margin-left:10px;">{{item.updatetime}}</font>
              </div>
            </div>
            <div style="background:#f6f6f6;width:100%;height:4px;display:block;"></div>
          </div>
          <!--广告功能end-->
					<div class="map_content" @click="goLocation(1)">
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
               <div v-show="shortcutList.length>5" class="swiper-button-next"></div>
    <div v-show="shortcutList.length>5" class="swiper-button-prev"></div>
						</div>
              <!-- <div class="swiper-pagination"></div> -->
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
									<img slot="icon" src="../../assets/images/icon-bike.png" v-if="item.introduct == 'Bicycle' || item.introduct == '自行车'|| item.introduct=='自転車'">
									<img slot="icon" src="../../assets/images/icon-car.png" v-else-if="item.introduct == 'Self-Driving' || item.introduct == '租车自驾服务' || item.introduct=='赁车自'">
									<img slot="icon" src="../../assets/images/icon-taxi.png" v-else-if="item.introduct == 'Taxi' || item.introduct == '出租车'|| item.introduct=='タクシー'">
									<img slot="icon" src="../../assets/images/icon-bus.png" v-else-if="item.introduct == 'Public bus' || item.introduct == '公交'|| item.introduct=='バス'">
									<img slot="icon" src="../../assets/images/icon-tbus.png" v-else-if="item.introduct == 'Subway' || item.introduct == '地铁'|| item.introduct=='地下鉄'">

									<span slot="left">{{item.introduct}}</span>
								</yd-cell-item>
							</yd-cell-group>

						</div>
					</section>

				</section>
			</scroller>
		</section>
	    <yd-actionsheet :items="myItems2" v-model="tabLanShow2" v-if="hotelid ==10"></yd-actionsheet>
    <yd-actionsheet :items="myItems" v-model="tabLanShow2" v-else></yd-actionsheet>
		<!--底部菜单、个人中心、 设置项-->
		<v-foot></v-foot>
		<v-menu v-show="menuFlag"></v-menu>
		<v-guestCenter v-show="centerFlag"></v-guestCenter>

	</div>
</template>
<style scoped>
.home-bnt-left{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2rem;
    border-radius: 1rem;
    margin-top: .2rem;
    margin-left: -.3rem;
    float:left;
    position: fixed;
    z-index: 100;
}

.home-bnt-right{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2rem;
    border-radius: 1rem;
    margin-top: .2rem;
    right: -.3rem;
    float:right;
    position: fixed;
    z-index: 100;
}

.back-menu{
  display: block;
  width: 0.4rem;
  height: 0.5rem;
  background: url(/static/img/icon_menu.cd13389.png)center no-repeat;
  background-size: 0.38rem;
}

.home-back{
  display: block;
  width: 0.6rem;
  height: 0.6rem;
  background: url(../../assets/images/icon_back.png) center no-repeat;
  background-size: 0.3rem;
}
</style>

<script>
import { Actionsheet, Scroller } from "vux";
import { mapState } from "vuex";
import { mapGetters } from "vuex";
import foot from "../foot.vue";
import menu from "../common/menu.vue";
import guestCenter from "../common/menu/guestCenter.vue";
export default {
  data() {
    return {
      menuFlag: false,
      centerFlag: false,
      menuSlide: false,
      centerSlide: false,
      homeData: {},
      fullName: "",
      startTime: "",
      weather: {},
      shortcutList: [], //按钮组
      localshortcutList: [], //本地按钮组
      markFlag: false,
      actionSheet: false,
      lat: "",
      lng: "",
      idType: "",
      myItems: [],
      myItems2: [],
      details: {},
      pageType: "",
      distance: "",
      facList: [],
      facListShow: [],
      sHeightPx: "400px",
      weatherSrc: "",
      hotelid: "",
      pageFlag: false,
      isZH:true,
      advList:[]
    };
  },
  created: function() {
    //统计
    hotelStatistics(this,localStorage.HOTELID);
    //加载页面判断是否需要清理缓存
    h5Version();
    //判断显示中/英文
    if(localStorage.LANGUAGE!='zh'){
        this.isZH = false;
    }
    this.tabLanShow2 = this.tabLanShow;
    //设置当前状态
    localStorage.stateFlag = 2;
    //判断是登录主页还是查询主页
    if (this.$route.query.hotelid) {
      localStorage.searchFlag = "s_home";
      this.hotelid = this.$route.query.hotelid;
      localStorage.HOTELID = this.$route.query.hotelid;
    } else {
      localStorage.searchFlag = "home";
      this.hotelid = localStorage.HOTELID;
      this.pageFlag = true;
    }

    //判断个人中心图标显示
    if (this.$route.query.page == "unlogin" || localStorage.spage == 1) {
      localStorage.spage = 1;
      this.pageFlag = true;
    } else if (this.$route.query.page == "search" || localStorage.spage == 2) {
      localStorage.spage = 2;
      this.pageFlag = true;
    } else {
      this.pageFlag = false;
    }

    if (localStorage.searchFlag == "home") {
      this.pageFlag = false;
    }

    //获取物业详情
    this.fullName = localStorage.FULLNAME;
    //初始化数据
    this.localshortcutList = [
      {
        key: "reservation",
        title: "",
        imgSrc: require("../../assets/images/fastkey/book.png"),
        linkTo: "/home/homebooking"
      },
      {
        key: "tanslate",
        title: "",
        imgSrc: require("../../assets/images/fastkey/translate.png"),
        linkTo: "/translate"
      },
      {
        key: "service",
        title: "",
        imgSrc: require("../../assets/images/fastkey/service.png"),
        linkTo: "/home/service"
      },
      {
        key: "flight",
        title: "",
        imgSrc: require("../../assets/images/fastkey/airport.png"),
        linkTo: "#"
      },
      {
        key: "phone",
        title: "",
        imgSrc: require("../../assets/images/fastkey/phone.png"),
        linkTo: "/phone"
      },
      {
        key: "guestmail",
        title: "",
        imgSrc: require("../../assets/images/fastkey/phone.png"),
        linkTo: "#"
      },
      {
        key: "smarthome",
        title: "",
        imgSrc: require("../../assets/images/fastkey/smart.png"),
        linkTo: "#"
      },
      {
        key: "news",
        title: "",
        imgSrc: require("../../assets/images/fastkey/news-add.png"),
        linkTo: "/ssr"
      },
      {
        key: "bills",
        title: "",
        imgSrc: require("../../assets/images/fastkey/bill.png"),
        linkTo: "/mybill"
      },
      {
        key: "invoice",
        title: "",
        imgSrc: require("../../assets/images/fastkey/receipt.png"),
        linkTo: "/invoices"
      },
      {
        key: "lifeservice",
        title: "",
        imgSrc: require("../../assets/images/fastkey/lifestyle.png"),
        linkTo: ""
      },
      {
        key: "shop",
        title: "",
        imgSrc: require("../../assets/images/fastkey/shopping.png"),
        linkTo: ""
      },
      {
        key: "breakfast",
        title: "",
        imgSrc: require("../../assets/images/fastkey/dining.png"),
        linkTo: "/breakfast"
      },
      {
        key: "supermarket",
        title: "",
        imgSrc: require("../../assets/images/fastkey/group.png"),
        linkTo: ""
      },
      {
        key: "laundry",
        title: "",
        imgSrc: require("../../assets/images/fastkey/washer.png"),
        linkTo: "/laundry"
      },
      {
        key: "voice",
        title: "",
        imgSrc: require("../../assets/images/fastkey/phone.png"),
        linkTo: ""
      }
    ];
    this.myItems = [
      {
        label: "English",
        method: () => {
          this.hideAction("en");
        }
      },
      {
        label: "中文",
        method: () => {
          this.hideAction("zh");
        }
      },
      {
        label: "Cancel",
        method: () => {
          _this.$store.dispatch("changeLanguage");
        }
      }
    ];
    this.myItems2 = [
      {
        label: "English",
        method: () => {
          this.hideAction("en");
        }
      },
      {
        label: "中文",
        method: () => {
          this.hideAction("zh");
        }
      },
      {
        label: "日本語の",
        method: () => {
          this.hideAction("jp");
        }
      },
      {
        label: "Cancel",
        method: () => {
          _this.$store.dispatch("changeLanguage");
        }
      }
    ];
    this.facList = [
      {
        icon: "tv",
        imgSrc: require("../../assets/images/Group 5 Copy@2x.png")
      },
      {
        icon: "restaurant",
        imgSrc: require("../../assets/images/dining room@2x.png")
      },
      { icon: "wifi", imgSrc: require("../../assets/images/wifi@2x.png") },
      { icon: "Gymnasium", imgSrc: require("../../assets/images/gym@2x.png") },
      {
        icon: "swimmingpool",
        imgSrc: require("../../assets/images/icon-swimmingsool.png")
      },
      {
        icon: "residentslounge",
        imgSrc: require("../../assets/images/icon-laug.png")
      },
      {
        icon: "Reading Room",
        imgSrc: require("../../assets/images/icon-reading.png")
      },
      {
        icon: "Children's Play Room",
        imgSrc: require("../../assets/images/icon-child.png")
      },
      {
        icon: "Sauna Room",
        imgSrc: require("../../assets/images/icon-sauan.png")
      },

      { icon: "Yoga studio", imgSrc: require("../../assets/images/yoga.png") },
      {
        icon: "Video Room",
        imgSrc: require("../../assets/images/yingyuan.png")
      },
      { icon: "Massage / SPA", imgSrc: require("../../assets/images/spa.png") },
      {
        icon: "Business Center",
        imgSrc: require("../../assets/images/businesscenter.png")
      },
      {
        icon: "Golf simulator",
        imgSrc: require("../../assets/images/golf.png")
      },
      {
        icon: "Meeting Room",
        imgSrc: require("../../assets/images/meetingroom.png")
      },
      {
        icon: "Outdoor Terrace",
        imgSrc: require("../../assets/images/garden.png")
      }
    ];

    //设置首页标识
    localStorage.menuFlag = "home";
    //获取登录身份
    this.idType = this.$route.query.type;
    //设置底部菜单状态
    localStorage.footFlag = 1;
    //获取当前日期
    var m = new Array(
      "Jan",
      "Feb",
      "Mar",
      "Apr",
      "May",
      "Jun",
      "Jul",
      "Aug",
      "Spt",
      "Oct",
      "Nov",
      "Dec"
    );
    var tDay = new Date();
    this.startTime = m[tDay.getMonth()] + " " + tDay.getDate();
    //初始化语言
    this.$store.dispatch("firstLanguage");
    let params = {
      hotelid: this.hotelid,
      lang: localStorage.LANGUAGE
    };
    let params1 = {
      id: this.hotelid
    };

    this.$store.dispatch("getHotelListById", params1).then(res => {
      if (res.code == 0) {
        let data = res.data.list[0];
        if (data.invoice_id) {
          localStorage.INVOICEID = data.invoice_id;
        } else {
          localStorage.INVOICEID = "";
        }
        //是否显示机器人洗衣功能
        localStorage.WASHING_MACHINE = data.washing_machine;
      }
    });
    this.$store.dispatch("getHome", params).then(res => {
      if (res.code == 0) {
        //全部数据
        this.homeData = this.home.data;
        localStorage.HomeInfo = this.homeData.indexBackground+';'+this.homeData.name;
        //天气数据
        // this.weather = this.home.data.wetherInfo;
        if (true) {
          this.weatherSrc = require("../../assets/images/icon-w-cloud.png");
        }

        //经纬度
        this.lat = this.home.data.lat;
        this.lng = this.home.data.lng;

        //整理按钮结果集
        for (var item in this.home.data.shortcutList) {
          for (var i in this.localshortcutList) {
            if (
              this.home.data.shortcutList[item].key ==
              this.localshortcutList[i].key
            ) {
              this.localshortcutList[i].title = this.home.data.shortcutList[
                item
              ].title;
              this.shortcutList.push(this.localshortcutList[i]);
            }
          }
        }

        $(document).ready(function() {
          setTimeout(function() {
            new Swiper(".index_content .swiper-container", {
              pagination: ".swiper-pagination",
              slidesPerView: 4,
              paginationClickable: true,
              spaceBetween: 0
              // navigation: {
              //   nextEl: ".swiper-button-next",
              //   prevEl: ".swiper-button-prev"
              // }
            });
          }, 300);
        });
      } else {
        this.$dialog.toast({ mes: res.msg, timeout: 1000 });
      }
    });
    var _this = this;
    //获取天气预报信息
    let cityKey='';
    if(localStorage.groupid==3){//测试环境
        if(localStorage.HOTELID==24){//成都
          cityKey='510100';
        }else if(localStorage.HOTELID==6){//北京
          cityKey='110000';
        }else{//广州
          cityKey='440100';
        }
    }else{//生产环境
       if(localStorage.HOTELID==22){//成都
          cityKey='510100';
      }else if(localStorage.HOTELID==28||localStorage.HOTELID==7){//广州
          cityKey='440100';
      }else if(localStorage.HOTELID==2){//上海
          cityKey='310000';
      }else if(localStorage.HOTELID==11){//天津
          cityKey='120000';
      }else if(localStorage.HOTELID==12||localStorage.HOTELID==25){//西安
          cityKey='610100';
      }else if(localStorage.HOTELID==14){//厦门
          cityKey='350200';
      }else if(localStorage.HOTELID==15){//合肥
          cityKey='340100';
      }else if(localStorage.HOTELID==21){//深圳
          cityKey='440300';
      }else if(localStorage.HOTELID==30){//杭州
          cityKey='330100';
      }else{//北京
          cityKey='110000';
      }
    }
    $.ajax({
        url:'https://restapi.amap.com/v3/weather/weatherInfo?key=ad64c616dd8a8664ca79eb7eb0d58dd8&city='+cityKey+'&extensions=all',
        type:'GET',
        async:false,
        timeout:5000,
        dataType:'json',
        success:function(data){
          if(data.status==1){
            _this.weather.weatherEn = '';
            _this.weather.temperatureFrom = data.forecasts[0].casts[0].daytemp+'℃';
            _this.weather.temperatureTo = data.forecasts[0].casts[0].nighttemp+'℃';
          }
        }
    });

    $(document).ready(function() {
      $(".mask-actionsheet").click(function() {
        _this.$store.dispatch("changeLanguage");
      });
      $(".actionsheet-action").click(function() {
        _this.$store.dispatch("changeLanguage");
      });
    });

    //获取物业详情
    let params2 = {
      hotelid: this.hotelid,
      lang: localStorage.LANGUAGE
    };
    this.$store.dispatch("getHotelDetail", params2).then(res => {
      this.details = this.hotelDetail;
      this.lat = this.hotelDetail.lat;
      this.lng = this.hotelDetail.lng;
      // this.hotelDetail.trafficList.reverse();

      localStorage.bookUrl = this.hotelDetail.bookurl;

      //重组图标数据
      for (var item in this.hotelDetail.facilitiesList) {
        for (var i in this.facList) {
          if (
            this.hotelDetail.facilitiesList[item].icon == this.facList[i].icon
          ) {
            this.facList[i].pdf = this.hotelDetail.facilitiesList[item].pdf;
            this.facList[i].video = this.hotelDetail.facilitiesList[item].video;
            this.facList[i].detail = this.hotelDetail.facilitiesList[
              item
            ].detail;
            this.facList[i].name = this.hotelDetail.facilitiesList[item].name;
            this.facListShow.push(this.facList[i]);
          }
        }
      }
      //初始化swiper
      $(document).ready(function() {
        setTimeout(function() {
          var swiper = new Swiper(".equipment .swiper-container", {
            pagination: {
              el: ".swiper-pagination",
              type: "fraction"
            },
            slidesPerView: 5,
            paginationClickable: true,
            spaceBetween: 0
          });
          var swiper3 = new Swiper(".apartment .swiper-container", {
            pagination: ".swiper-pagination",
            slidesPerView: 1.1,
            paginationClickable: true,
            spaceBetween: 15
          });
          var swiper4 = new Swiper(".fire .swiper-container", {
            pagination: ".swiper-pagination",
            slidesPerView: 1.2,
            paginationClickable: true,
            spaceBetween: 15
          });
          var swiper2 = new Swiper(".virtual .swiper-container", {
            pagination: ".swiper-pagination",
            slidesPerView: 3,
            paginationClickable: true,
            spaceBetween: 0
          });
          //跳转到员工订单页面
          let specialUrl = window.location.href.split('#');
          if(specialUrl.length>1){
            _this.$router.push(specialUrl[1]);
            return;
          }
              }, 300);
            });
          });

          //获取首页广告列表
          _this.$store.dispatch("getHomeAdv", {
              hotelid:localStorage.HOTELID
          }).then(res => {
              if (res.code == 0) {
                _this.advList = res.data.list;
                if(_this.advList.length>0){
                  $("#advDiv").show();
                }
              }
          });

          $(function(){
              $(".navbar-center").css('marginLeft',0);
          });
  },
  mounted: function() {
    //一级页面falg
    isHomePage(1);
  },
  methods: {
    //显示菜单
    showMenu: function() {
      this.menuFlag = true; //显示菜单
      this.centerFlag = false; //隐藏个人中心
      $(".index").css({
        position: "absolute",
        height: "100%",
        width: "100%",
        overflow: "hidden",
        "z-index": "350"
      });
      if (this.menuSlide == false) {
        $(".index").css({
          left: "-5rem"
        });
        $("footer").css({
          left: "-5rem"
        });

        $(".mark").css({
          //						'left':'-5rem',
          opacity: 1
        });
        this.menuSlide = true;
        this.markFlag = true;
      }
    },
    //显示个人中心
    showCenter: function() {
      $(".index").css({
        position: "absolute",
        height: "100%",
        width: "100%",
        overflow: "hidden",
        "z-index": "350"
      });
      this.menuFlag = false; //隐藏菜单
      this.centerFlag = true; //显示个人中心
      if (this.centerSlide == false) {
        $(".index").css({
          left: "4.5rem"
        });
        $("footer").css({
          left: "4.5rem"
        });
        $(".mark").css({
          opacity: 1
        });
        this.centerSlide = true;
        this.markFlag = true;
      }
    },
    //点击页面，收起抽屉效果
    hideSlide: function() {
      //如果是菜单展开，收起菜单
      if (this.menuSlide) {
        $(".index").css({
          left: 0
        });
        $("footer").css({
          left: 0
        });
        $(".mark").css({
          opacity: 0
        });
        this.menuSlide = false;
        this.markFlag = false;
        //					this.menuFlag = false;
      }
      //如果是个人中心展开，收起个人中心
      if (this.centerSlide) {
        $(".index").css({
          left: 0
        });
        $("footer").css({
          left: 0
        });
        $(".mark").css({
          opacity: 0
        });
        this.centerSlide = false;
        this.markFlag = false;
        //					this.centerFlag = false;
      }
    },
    hideAction: function(key) {
      let _this = this;
      //更新本地语言标识
      localStorage.LANGUAGE = key;
      let params1 = {
        token: localStorage.TOKEN,
        platform: localStorage.platform,
        identity: localStorage.identity,
        lang: key
      };
      this.$store.dispatch("tabLanguage", params1);
      this.$store.dispatch("updateLanguage", key);
      this.$store.dispatch("changeLanguage");
      //更新首页数据
      let params = {
        hotelid: this.hotelid,
        lang: localStorage.LANGUAGE
      };
      this.$store.dispatch("getHome", params).then(function(res) {
        _this.shortcutList = [];
        //整理按钮结果集
        for (var item in _this.home.data.shortcutList) {
          for (var i in _this.localshortcutList) {
            if (
              _this.home.data.shortcutList[item].key ==
              _this.localshortcutList[i].key
            ) {
              _this.localshortcutList[i].title =
                _this.home.data.shortcutList[item].title;
              _this.shortcutList.push(_this.localshortcutList[i]);
            }
          }
        }
      });

      //更新物业详情数据
      let params2 = {
        hotelid: this.hotelid,
        lang: localStorage.LANGUAGE
      };
      this.$store.dispatch("getHotelDetail", params2);

      if (this.tabLanShow2 == true) {
        this.$store.dispatch("changeLanguage2");
      }

      //跟新自主服务标识
      if(key!='zh'){
        this.isZH = false;
      }else{
        this.isZH = true;
      }
    },
    cancel: function() {
      if (this.tabLanShow2 == true) {
        this.$store.dispatch("changeLanguage2");
      }
    },
    //地图导航
    goLocation: function(val) {
        let _this = this;
        //机器人购物
        if(val===2){
          //北京雅诗阁来福士中心服务公寓 首页机器人服务关闭
          if(localStorage.HOTELID==1){
            return;
          }
          //判断是否登录
          if (localStorage.TOKEN) {
              let goPath = "/shopping";
              if(localStorage.HOTELID==21||localStorage.HOTELID==7){
                    //获取分类
                  let params = {
                      hotelid: localStorage.HOTELID,
                      status: 0,
                      parentid:0,
                      withchild:1
                  }
                  _this.$store.dispatch('getFirstTags', params).then(function (res) {
                      _this.dataList = res.data.data.list;
                      localStorage.NEWTYPE = res.data.data.list[0].id+','+res.data.data.list[0].children[0].id;
                      goPath = "/robotDelivery";
                      //验证pin码并跳转
                      if(localStorage.idType==1){
                        checkPin(_this,{ path: goPath, query: { info: "home" } });
                      }else{
                        this.$router.push({ path: goPath, query: { info: "home" } });
                      }
                  });
              }else{
                //验证pin码并跳转
                if(localStorage.idType==1){
                  checkPin(this,{ path: goPath, query: { info: "home" } });
                }else{
                  this.$router.push({ path: goPath, query: { info: "home" } });
                }
              }
          }else{
            this.$router.replace("/loginforguest");
          }
        }else{
          this.$router.push("/map");
        }
    },
    goNext: function(key) {
      let _this = this;
      switch (key) {
        case "control":
          _this.$router.replace("#");
          break;
        case "reservation":
          let title="预定";
          //判断显示中/英文
          if(localStorage.LANGUAGE!='zh'){
              title="Reservation";
          }
          this.$router.push({path:'/windowOpen',query:{url:localStorage.bookUrl,title:title,backUrl:'/'}});
          break;
        case "tanslate":
          _this.$router.push("/translate");
          break;
        case "service":
          if (localStorage.TOKEN) {
            if (localStorage.idType == 1) {
              if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                openFile(localStorage.serviceUrl);
              } else {
                openUrl(localStorage.serviceUrl, " ");
              }
            } else {
              alert("没有权限进入该模块");
            }
            //							_this.$router.replace('/home/service')
          } else {
            _this.$router.replace("/loginforguest");
          }
          break;
        case "flight":
          _this.$router.push("#");
          break;
        case "phone":
          _this.$router.push("/phone");
          break;
        case "bills":
          _this.$router.push("/mybill");
          break;
        case "invoice":
          if (localStorage.TOKEN) {
            _this.$router.push("/invoices");
          } else {
            _this.$router.replace("/loginforguest");
          }
          break;
        case "news":
          _this.$router.push("/ssr");
          break;
        case "lifeservice":
          _this.$router.push("/lifeStyle");
          break;
        case "shop":
          if (localStorage.TOKEN) {
            _this.$router.push({
              path: "/shopping",
              query: { pageFlag: "s_home" }
            });
          } else {
            _this.$router.replace("/loginforguest");
          }
          break;
        case "breakfast":
          if (localStorage.TOKEN) {
            checkPin(_this,{
              path: "/breakfast",
              query: { pageFlag: "s_home" }
            });
          } else {
            _this.$router.replace("/loginforguest");
          }
          break;
        case "supermarket":
          if (localStorage.TOKEN) {
            _this.$router.push({
                path: "/lifeSohp",
                query: { pageFlag: "home" }
              });
          } else {
            _this.$router.replace("/loginforguest");
          }
          break;
        case "laundry":
            if (localStorage.TOKEN) {
              _this.$router.push({
                  path: "/laundry",
                  query: { pageFlag: "home" }
                });
            } else {
              _this.$router.replace("/loginforguest");
            }
          break;
        case "voice":
            videoFunction();
          break;
      }
    },
    goBack: function() {
      if (this.$route.query.page == "unlogin" || localStorage.spage == 1) {
        this.$router.push("/unlogin");
      } else if (
        this.$route.query.page == "search" ||
        localStorage.spage == 2
      ) {
        this.$router.push("/search");
      }
    },
    goTraffic: function(item) {
      this.$router.push({ path: "/home/trafficDetail", query: { info: item } });
    },
    openBooking: function(url) {
      openBooking(url);
    },
    goAdvDetail(id,typeid,type) {
        let _this = this;
        if(type==1){//本地攻略
          let params = {
            hotelid: localStorage.HOTELID,
            typeid: typeid,
            lang: localStorage.LANGUAGE,
            limit: 3000,
            page: 0
          };
          this.$store.dispatch('showLoading');
          this.$store.dispatch("getPoiList", params).then(res => {
            if(res.data.code===0){
              let data = null;
              for(let key in res.data.data.list){
                if (res.data.data.list[key].id == id){
                  data = res.data.data.list[key];
                  break;
                }
              }
              _this.$store.dispatch('hideLoading');
              _this.$router.push({ path: "/raidersDetail", query: { data: data} });
            }
          });
        }else if(type==2){//物业促销
          let params = {
              hotelid: localStorage.HOTELID,
              lang: localStorage.LANGUAGE,
              tagid:'',
              limit: 3000,
              page: 0
          }
          _this.$store.dispatch('getPromotionList', params).then((res) => {
              let dataList = res.data.data.list;
              let data = {};
              for(var key in dataList){
                  if (dataList[key].id == id){
                      data = dataList[key];
                      break;
                  }
              }
              _this.$router.push({path:'/promotionDetail',query:{info:data}});
          });
        }else if(type==3){//物业新闻
          let params = {
              hotelid: localStorage.HOTELID,
              lang: localStorage.LANGUAGE,
              tagid:'',
              limit: 3000,
              page: 0
          };
          _this.$store.dispatch('getNewsList', params).then((res) => {
              let dataList =  res.data.data.list;
              let data = {};
              for(var key in dataList){
                  if (dataList[key].id == id){
                      data = dataList[key];
                      break;
                  }
              }
              _this.$router.push({path:'/promotionDetail',query:{info:data}});
          });
        }else if(type==4){//活动报名
          let params = {
              hotelid: localStorage.HOTELID,
					    lang: localStorage.LANGUAGE,
              limit: 3000,
              page: 0
				  }
          _this.$store.dispatch('getActivityList', params).then(function (res) {
              let dataList =  res.data.data.list;
              let data = {};
              for(var key in dataList){
                  if (dataList[key].id == id){
                      data = dataList[key];
                      break;
                  }
              }

              let params1 = {
                id:id,
                hotelid: localStorage.HOTELID,
                lang: localStorage.LANGUAGE
              }
              _this.$store.dispatch('getActivityDetail', params1).then(function (res) {
                  data.article = res.data.article;
                  if(res.data.photos.length>0){
                      _this.$router.push({path:'/activity',query:{data:res.data}});
                  }else{
                      _this.$router.push({path:'/propertyDetail',query:{info:data}});
                  }
              });
				  });
        }else if(type==5){//物业通知
          let params = {
              hotelid: localStorage.HOTELID,
              lang: localStorage.LANGUAGE,
              limit: 3000,
              page: 0
          }
          _this.$store.dispatch('getNoticList', params).then(function (res) {
              let dataList =  res.data.data.list;
              let data = {};
              for(var key in dataList){
                  if (dataList[key].id == id){
                      data = dataList[key];
                      break;
                  }
              }
              _this.$router.push({path:'/opinionDetail', query:{info:data}})
          })
        }
    },
    goCaocao(){
      let params = {
              client_id: 'bbd2bdfcaa0ef194',
              sign_key: '16dd4ab835fe87fcf0cb05eb2797b84b',
              ext_user_id: localStorage.HOTELID,
              user_type: 1
          };
      this.$store.dispatch("createCCSign", params).then(res => {
        let sign = res.sign;
        let timestamp = res.timestamp;
        let url = "https://sandbox-mobile.caocaokeji.cn/enterprise-travel/home?client_id="+params.client_id+"&user_type="
        +params.user_type+"&ext_user_id="+params.ext_user_id+"&sign="+sign+"&timestamp="+timestamp;
        this.$router.push({path:'/windowOpen',query:{url:url,title:"Caocao",backUrl:'/'}});
      });
    }
  },
  components: {
    "v-foot": foot,
    "v-menu": menu,
    "v-guestCenter": guestCenter,
    Actionsheet
  },
  computed: {
    ...mapState({
      language: state => state.language.language,
      home: state => state.home.home,
      tabLanShow2: state => state.language.tabLanShow2,
      hotelDetail: state => state.home.hotelDetail
    })
  }
};
</script>
<style>

</style>
