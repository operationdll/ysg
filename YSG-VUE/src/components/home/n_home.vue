<template>
    <div class="home" style="height: 100%;">
        <section class="n_index index_common index" style="height: 100%;">
            <!--遮罩层-->
            <transition name="fade">
                <div class="mark" v-show="markFlag"  @click="hideSlide"></div>
            </transition>
            <!--菜单栏-->
            <yd-navbar title="" class="index_tab" fixed>
                <router-link :to="{path:'/loginforguest',query:{pageType:'n_home'}}" slot="left">
                    <span class="user"></span>
                </router-link>
                <span class="menu" @click="showMenu"  slot="right"></span>
            </yd-navbar>

            <!--主题内容区域-->
            <scroller>
                <section class="index_top"  :style="{backgroundImage: 'url(' + homeData.indexBackground + ')'}">
                    <span class="logo"></span>
                    <h4 class="user_name">
                        <router-link :to="{path:'/loginforguest',query:{pageType:'n_home'}}">Visitor</router-link>
                    </h4>
                    <ul class="weather_date">
                        <li class="date">
                            <div class="left"></div>
                            <div class="right">
                                <p>{{startTime}}</p>
                            </div>
                        </li>
                        <li class="weather">
                            <div class="right"></div>
                            <div class="left">
                                <p>{{weather.weatherEn}}</p>
                                <p>{{weather.temperatureFrom}} ~ {{weather.temperatureTo}}</p>
                            </div>
                        </li>
                    </ul>
                    <div class="smart_home">
                        <router-link :to="{path:'/search', query:{searchFlag:'unlogin'}}">
                            <button type="button">{{language.menu.find}}</button>
                        </router-link>
                    </div>
                </section>
                <section class="map_list un_loginmap" style="margin-top: .5rem;">
                    <ul>
                        <li v-for="item in hotelList">
                            <router-link :to="{path:'/s_home',query:{hotelid:item.hotelId, pageType:'unlogin', page:'unlogin'}}">
                                <div class="pic_show" @click="goHome(item)">
                                    <h4 v-if="langFlag == 'en'">{{item.nameEn}}</h4>
                                    <h4 v-else>{{item.name}}</h4>
                                    <span class="distance" v-if="item.distance">{{item.distance}}km</span>
                                    <img :src="item.listpic">
                                </div>
                            </router-link>
                            <div class="address">
                                <div class="left">
                                    <p>{{item.address}}</p>
                                </div>
                                <div class="right">
                                    <button type="button" @click="openBooking">{{language.apartment.book}}</button>
                                </div>
                            </div>

                        </li>
                    </ul>
                </section>
            </scroller>
            <v-foot></v-foot>
        </section>
        <yd-actionsheet :items="myItems1" v-model="tabLanShow"></yd-actionsheet>
        <v-menu v-show="menuFlag"></v-menu>
        <v-guestCenter v-show="centerFlag"></v-guestCenter>
    </div>
</template>
<style>
.un_loginmap {
  margin-bottom: 1rem;
}
.un_loginmap > li {
  border-bottom: 1px solid #eee;
}
.n_index {
  position: relative;
  z-index: 10;
  box-shadow: 0px 0px 6px 2px rgba(0, 0, 0, 0.3);
  background: #fff;
  transition: left 0.5s;
  left: 0;
  min-height: 13rem;
}
.n_index .user {
  display: inline-block;
  width: 0.4rem;
  height: 0.5rem;
  background: url("../../assets/images/icon_user.png")center no-repeat;
  background-size: 0.38rem;
}
.n_index .menu {
  display: inline-block;
  width: 0.4rem;
  height: 0.5rem;
  background: url("../../assets/images/icon_menu.png")center no-repeat;
  background-size: 0.38rem;
}
.n_index .m-navbar:after {
  border-bottom: none !important;
}
.distance {
  position: absolute;
  top: 0.2rem;
  right: 0.2rem;
  color: #f0c366;
  font-size: 0.28rem;
}
</style>

<script>
import { Actionsheet, Spinner } from "vux";
import { mapState } from "vuex";
import { mapGetters } from "vuex";
import foot from "../foot.vue";
import menu from "../common/menu.vue";
import guestCenter from "../common/menu/guestCenter.vue";
import { getDistance, bubbleSort } from "../../config/mUtils";

export default {
  data() {
    return {
      menuFlag: false,
      centerFlag: false,
      menuSlide: false,
      centerSlide: false,
      homeData: {},
      startTime: "",
      weather: {},
      markFlag: false,
      actionSheet: false,
      lat: "",
      lng: "",
      idType: "",
      hotelList: [],
      distanceFlag: false,
      paramsById: {},
      myItems1: [
        {
          label: "English",
          method: () => {
            this.hideAction("en");
            this.langFlag = "en";
          }
        },
        {
          label: "中文",
          method: () => {
            this.hideAction("zh");
            this.langFlag = "zh";
          }
        }, 
        {
          label: "Cancel",
          method: () => {
            _this.$store.dispatch("changeLanguage");
          }
        }
      ],
      langFlag: "en"
    };
  },
  created: function() {
    var _this = this;
    if (localStorage.lat) {
      this.distanceFlag = true;
    } else {
      this.distanceFlag = false;
    }
    //判断当前语言
    if (localStorage.LANGUAGE == "en") {
      this.langFlag = "en";
    } else if (localStorage.LANGUAGE == "zh") {
      this.langFlag = "zh";
    } else {
      this.langFlag = "jp";
    }
    document.cookie = "test";
    //设置首页标识
    localStorage.menuFlag = "unlogin";
    //设置搜索标识
    localStorage.searchFlag = "unlogin";
    //设置底部菜单状态
    localStorage.footFlag = 1;
    //设置当前状态
    localStorage.stateFlag = 0;
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
    this.startTime = m[tDay.getMonth() + 1] + " " + tDay.getDate();

    //初始化语言
    this.$store.dispatch("firstLanguage");

    //根据集团id获取物业地址，获取未登陆状态首页列表

    if (this.distanceFlag) {
      this.paramsById = {
        groupid: localStorage.groupid,
        // lng: localStorage.lng,
        // lat: localStorage.lat
        lng: localStorage.lng,
        lat: localStorage.lat
      };
    } else {
      this.paramsById = {
        groupid: localStorage.groupid
      };
    }

    this.$store.dispatch("getGroupListById", this.paramsById).then(res => {
      //获取物业列表，重组数据，添加物理距离
      this.hotelList = res.data.list; 
      for (var item in this.hotelList) {
        // this.hotelList[item].distance=parseFloat(getDistance(localStorage.lng,localStorage.lat,res.data.list[item].lat,res.data.list[item].lng)).toFixed(1);
        this.hotelList[item].distance = (parseFloat(
          this.hotelList[item].distance
        ) / 1000
        ).toFixed(1);
      }

      // this.hotelList = bubbleSort(this.hotelList);

      //获取第一个物业的天气
      let params = {
        hotelid: this.hotelList[0].hotelId,
        lang: localStorage.LANGUAGE
      };
      this.$store.dispatch("getHome", params).then(res => {
        if (res.code == 0) {
          //全部数据
          this.homeData = res.data;
          //天气数据
          this.weather = res.data.wetherInfo;
        } else {
          this.$dialog.toast({ mes: res.msg, timeout: 1000 });
        }
      });
    });
    var _this = this;
    $(document).ready(function() {
      $(".mask-actionsheet").click(function() {
        _this.$store.dispatch("changeLanguage");
      });
      $(".m-actionsheet a").click(function() {
        _this.$store.dispatch("changeLanguage");
      });
      //跳转到员工订单页面
    let specialUrl = window.location.href.split('#');
    if(specialUrl.length>1){
      _this.$router.push(specialUrl[1]);
      return;
    }
    });
  },
  mounted: function() {},
  methods: {
    //显示菜单
    showMenu: function() {
      this.menuFlag = true; //显示菜单
      this.centerFlag = false; //隐藏个人中心
      $(".n_index").css({
        position: "absolute",
        height: "100%",
        width: "100%",
        overflow: "hidden"
      });
      if (this.menuSlide == false) {
        $(".n_index").css({
          left: "-5rem"
        });
        $("footer").css({
          left: "-5rem"
        });

        $(".mark").css(
          {
            opacity: 1
          },
          200
        );
        this.menuSlide = true;
        this.markFlag = true;
      }
    },
    //点击页面，收起抽屉效果
    hideSlide: function() {
      $(".n_index").css({
        position: "relative",
        height: "auto",
        width: "auto",
        overflow: "auto"
      });
      //如果是菜单展开，收起菜单
      if (this.menuSlide) {
        $(".n_index").css({
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
      }
    },
    hideAction: function(key) {
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
    },
    goHome: function(data) {
      localStorage.HOTELID = data.hotelId; 
      this.$router.push({
        path: "/home",
        query: { hotelid: data.hotelId, pageType: "unlogin", page: "unlogin" }
      });
    },
    openBooking: function() {
      openBooking(localStorage.bookUrl);
    }
  },
  components: {
    "v-foot": foot,
    "v-menu": menu,
    "v-guestCenter": guestCenter,
    Actionsheet,
    Spinner
  },
  computed: {
    ...mapState({
      language: state => state.language.language,
      tabLanShow: state => state.language.tabLanShow
    })
  }
};
</script>
