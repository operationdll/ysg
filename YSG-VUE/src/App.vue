<template>
  <div id="app" style="height: 100%;">
      <div class="loading" v-show="loadFlag">
          <div class="loading_box"><img src="./assets/loading.svg"></div>
      </div>
      <div class="appStart" v-show="link">
          <div>
              <div class="adslideshow-container">
                <div class="admySlides adfade"  v-for="(obj, index2) in adlist">
                    <img :src="obj.pic" style="width:100%" @click="adDetail(obj.url)">
                    <div class="adtext">{{obj.msg}}</div>
                </div>
            </div>
            <div style="text-align:center">
                <span class="addot" v-for="(obj, index2) in adlist"></span>
            </div>
            <div class="app-close" @click="closeApp"></div>
          </div>
      </div>
      <transition name="slide-fade">
        <router-view></router-view> 
      </transition>
  </div>
</template>
<script>
import { lookup } from "dns";

var slideIndex = 0;

function plusSlides(n) {
    showSlides(slideIndex += n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("admySlides");
    var dots = document.getElementsByClassName("addot");
    if (n > slides.length) {slideIndex = 1}    
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";  
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].classList.remove("adactive");
    }
    slides[slideIndex-1].style.display = "block";  
    dots[slideIndex-1].className += " adactive";
}
</script>
<script>
import { mapGetters } from "vuex";
import { mapState } from "vuex";
export default {
  name: "app",
  data() {
    return {
      showAd: false,
      link: false,
      slideIndex:0,
      myVar:null,
      adlist:[]
    };
  },
  created() {
    let _this = this;
    if (!localStorage.indentity) {
      this.indentity();
    }

    if (localStorage.HOTELID) {
      let params = {
        groupid: localStorage.groupid,
        hotelid: localStorage.HOTELID,
        platform: localStorage.platform,
        identity: localStorage.indentity
      };
      this.$store.dispatch("appStart", params).then(function(res) {
        if(res.code==0){
          if(res.data.list.length>0){
            _this.adlist=res.data.list;
            _this.myVar = setInterval(function(){
                _this.link = true;
                _this.plusSlides(1);
            },2000);
          }
        }else{
          console.log("广告请求失败."+res.msg);
        }
        setTimeout(() => {
              this.updateApp();
        }, 5000);
      });
    }else{
        setTimeout(() => {
            this.updateApp();
        }, 5000);
    }
  },
  mounted: function() {
    //友盟统计
    const script = document.createElement("script");
    script.src =
      "https://s19.cnzz.com/z_stat.php?id=1264559087&web_id=1264559087";
    script.language = "JavaScript";
    document.body.appendChild(script);
    window.addEventListener("scroll", this.onScroll);
  },
  watch: {
    $route() {
      if (window._czc) {
        let location = window.location;
        let contentUrl = location.pathname + location.hash;
        let refererUrl = "/";
        window._czc.push(["_trackPageview", contentUrl, refererUrl]);
      }
    }
  },
  methods: {
    goDetail: function() {
      let _this = this;
      if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        openFile(_this.appData.url);
      } else {
        openUrl(_this.appData.url, " ");
      }
    },
    closeApp: function() {
      this.link = false;
      clearInterval(this.myVar);
    },
    indentity: function() {
      var chars = [
        "0",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "A",
        "B",
        "C",
        "D",
        "E",
        "F",
        "G",
        "H",
        "I",
        "J",
        "K",
        "L",
        "M",
        "N",
        "O",
        "P",
        "Q",
        "R",
        "S",
        "T",
        "U",
        "V",
        "W",
        "X",
        "Y",
        "Z"
      ];
      var res = "";
      for (var i = 0; i < 10; i++) {
        var id = Math.ceil(Math.random() * 35);
        res += chars[id];
      }
      localStorage.indentity = res;
      console.log("kkkkkkk", res);
    },
    updateApp: function() {
      let _this = this;
      // 获取当前版本号
      let params = {
        platform: localStorage.platform,
        groupid: localStorage.groupid
      };
      this.$store.dispatch("appUpdate", params).then(function(res) {
        _this.versionData = res.data;
        console.log(res.data);
        // 版本校验
        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
          //判断是否第一次安装
          if(localStorage.isFirstLoad === undefined){
              localStorage.isFirstLoad = "loaded";
              return;
          }
          let params = {
            isMust: _this.versionData.forced, //是否强制更新
            doneTitle: "去升级", //确定按钮
            cancelTitle: "取消", // 取消按钮
            newDesc: "已是最新版本", //如果是最新版本提示文字
            link:
              "itms-apps://itunes.apple.com/cn/app/jie-zou-da-shi/id这是id?mt=8",
            desc: _this.versionData.description, //升级描述
            version: _this.versionData.version, //版本号
            versionLocal: localStorage.version //本地版本号
          };
          updataApp(params);
        } else {
          let params = {
            isMust: _this.versionData.forced, //是否强制更新
            doneTitle: "去升级", //确定按钮
            cancelTitle: "取消", // 取消按钮
            newDesc: "已是最新版本", //如果是最新版本提示文字
            link: "https://storage.easyiservice.com/download/ascott.apk", //'http://a.app.qq.com/o/simple.jsp?pkgname=com.ysg.yashige',
            desc: _this.versionData.description, //升级描述
            version: _this.versionData.version //版本号
          };
          checkVersion(JSON.stringify(params));
        }
      });
    },
    onScroll() {
      this.top =
        window.pageYOffset ||
        document.documentElement.scrollTop ||
        document.body.scrollTop;
    },
    plusSlides(n) {
        this.showSlides(this.slideIndex += n);
    },
    showSlides(n) {
        var i;
        var slides = document.getElementsByClassName("admySlides");
        var dots = document.getElementsByClassName("addot");
        if (n > slides.length) {this.slideIndex = 1}    
        if (n < 1) {this.slideIndex = slides.length}
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";  
        }
        for (i = 0; i < dots.length; i++) {
            dots[i].classList.remove("adactive");
        }
        slides[this.slideIndex-1].style.display = "block";  
        dots[this.slideIndex-1].className += " adactive";
    },
    adDetail: function(url) {
       var headStr = url.substring(0, 4);
       clearInterval(this.myVar);
       this.link = false;
       if(headStr=="http"){
          if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
            openSafariUrl(url);
          } else {
            getSystemBrowser(url);
          }
       }else{
          this.$router.push(url);
       }
    }
  },
  computed: {
    ...mapGetters(["loadFlag", "direction", "appStart"])
  }
};
</script>
<style lang="less">
@import "~vux/src/styles/reset.less";
#app {
  background: #fff;
}
body,
html {
  width: 100%;
  height: 100%;
  overflow-x: hidden;
  position: fixed;
}
.col-1 {
  float: left;
  width: 10%;
  min-height: 0.1rem;
}
.col-2 {
  float: left;
  width: 20%;
  min-height: 0.1rem;
}
.col-3 {
  float: left;
  width: 30%;
  min-height: 0.1rem;
}
.col-4 {
  float: left;
  width: 40%;
  min-height: 0.1rem;
}
.col-5 {
  float: left;
  width: 50%;
  min-height: 0.1rem;
}
.col-6 {
  float: left;
  width: 60%;
  min-height: 0.1rem;
}
.col-7 {
  float: left;
  width: 70%;
  min-height: 0.1rem;
}
.col-8 {
  float: left;
  width: 80%;
  min-height: 0.1rem;
}
.col-9 {
  float: left;
  width: 90%;
  min-height: 0.1rem;
}
.m-navbar.navbar-fixed {
  top: 20px !important;
}
.nav_mark {
  position: fixed;
  width: 100%;
  height: 22px;
  top: 0;
  left: 0;
  background: #fff;
  z-index: 100;
}
.back {
  display: block;
  width: 0.36rem;
  height: 0.36rem;
  background: url("assets/images/icon_back.png") center no-repeat;
  background-size: 0.2rem;
}
.loading {
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  z-index: 9999;
}
.loading > div {
  position: absolute;
  width: 2rem;
  height: 2rem;
  background: rgba(0, 0, 0, 0.2);
  border-radius: 0.1rem;
  top: 30%;
  left: 50%;
  margin-left: -1rem;
  text-align: center;
}
.loading > div img {
  width: 0.8rem;
  margin: 0 auto;
  margin-top: 0.6rem;
}
.vux-pop-out-enter-active,
.vux-pop-out-leave-active,
.vux-pop-in-enter-active,
.vux-pop-in-leave-active {
  will-change: transform;
  transition: all 500ms;
  backface-visibility: hidden;
  height: 100%;
  perspective: 1000;
}
.vux-pop-out-enter {
  opacity: 0;
  transform: translate3d(-100%, 0, 0);
}
.vux-pop-out-leave-active {
  opacity: 0;
  transform: translate3d(100%, 0, 0);
}
.vux-pop-in-enter {
  opacity: 0;
  transform: translate3d(100%, 0, 0);
}
.vux-pop-in-leave-active {
  opacity: 0;
  transform: translate3d(-100%, 0, 0);
}
[v-cloak] {
  display: none;
}

.appStart {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  z-index: 9999;
  padding-top: 3rem;
}
.appStart > div {
  width: 5rem;
  height: 5rem;
  background: #fff;
  margin: 0 auto;
  position: relative;
}
.app-close {
  position: absolute;
  top: 5rem;
  left: 2.2rem;
  width: 0.6rem;
  height: 1.4rem;
  background: url("assets/images/app-close.png") center no-repeat;
  background-size: 100%;
}
.appStart img {
  width: 100%;
  height: 100%;
}

/* 首页广告滚动样式 */
.admySlides {
  display: none;
}
.adslideshow-container img {
  vertical-align: middle;
  height: 5rem;
}

/* Slideshow container */
.adslideshow-container {
  max-width: 100%;
  position: relative;
  margin: auto;
}

/* The dots/bullets/indicators */
.addot {
  cursor: pointer;
  height: 5px;
  width: 5px;
  margin: 0 2px;
  background-color: #bbb;
  border-radius: 50%;
  display: inline-block;
  transition: background-color 0.6s ease;
}

.adactive {
  background-color: #717171;
}

/* Fading animation */
.adfade {
  -webkit-animation-name: fade;
  -webkit-animation-duration: 1.5s;
  animation-name: fade;
  animation-duration: 1.5s;
}

/* Caption text */
.adtext {
  color: #f2f2f2;
  font-size: 15px;
  padding: 8px 12px;
  position: absolute;
  bottom: 8px;
  width: 100%;
  text-align: center;
}

@-webkit-keyframes adfade {
  from {
    opacity: 0.4;
  }
  to {
    opacity: 1;
  }
}

@keyframes adfade {
  from {
    opacity: 0.4;
  }
  to {
    opacity: 1;
  }
}

/* On smaller screens, decrease text size */
@media only screen and (max-width: 300px) {
  .adtext {font-size: 11px}
}
</style>
<style scoped>
.slide-fade-enter-active {
  transition: all 1.2s ease;
}
.slide-fade-leave-active {
  transition: all .1s cubic-bezier(2.0, 0.5, 0.8, 1.0);
}
.slide-fade-enter, .slide-fade-leave-to{
  opacity: 0;
}
</style>
