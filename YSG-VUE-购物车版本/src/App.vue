<template>
  <div id="app" style="height: 100%;">
      <div class="loading" v-show="loadFlag">
          <div class="loading_box"><img src="./assets/loading.svg"></div>
      </div>
      <div class="appStart" v-show="link">
          <div>
              <img :src="link" alt="" @click="goDetail">
              <div class="app-close" @click="closeApp"></div>
          </div>
      </div>
      <router-view></router-view>
  </div>
</template>
<script>
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        name: 'app',
        data() {
            return {
                showAd: false,
                link:''
            }
        },
        created() {
            let _this = this
            if(!(localStorage.indentity)) {
                this.indentity()
            }

            if(localStorage.HOTELID){
                let params = {
                    groupid:localStorage.groupid,
                    hotelid:localStorage.HOTELID,
                    platform:localStorage.platform,
                    identity:localStorage.indentity
                }
                this.$store.dispatch('appStart', params).then(function (res) {
                    _this.link = _this.appStart.data.list[0].pic
                })
            }

            this.updataApp()

        },
        mounted:function () {
            //友盟统计
            const script = document.createElement('script')
            script.src = "https://s19.cnzz.com/z_stat.php?id=1264559087&web_id=1264559087"
            script.language = 'JavaScript'
            document.body.appendChild(script)
        },
        watch: {
            '$route' () {
                if (window._czc) {
                    let location = window.location
                    let contentUrl = location.pathname + location.hash
                    let refererUrl = '/'
                    window._czc.push(['_trackPageview', contentUrl, refererUrl])
                }
            }
        },
        methods: {
            goDetail: function () {
                let _this = this
                if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){
                    openFile(_this.appData.url)
                } else {
                    openUrl(_this.appData.url,' ')
                }
            },
            closeApp: function () {
                $('.appStart').hide()
            },
            indentity:function () {
                var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
                var res = "";
                for(var i = 0; i < 10 ; i ++) {
                    var id = Math.ceil(Math.random()*35);
                    res += chars[id];
                }
                localStorage.indentity = res
                console.log('kkkkkkk',res)
            },
            updataApp:function () {
                let _this = this
                // 获取当期那版本号
                let params = {
                    platform: localStorage.platform,
                    groupid: localStorage.groupid
                }
                this.$store.dispatch('appUpdate',params).then(function(res){
                    _this.versionData = res.data
                    console.log(res.data)
                    // 版本校验
                    if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){
                        let params = {
                            isMust:_this.versionData.forced, //是否强制更新
                            doneTitle:'去升级', //确定按钮
                            cancelTitle:'取消', // 取消按钮
                            newDesc:'已是最新版本', //如果是最新版本提示文字
                            link:'itms-apps://itunes.apple.com/cn/app/jie-zou-da-shi/id这是id?mt=8',
                            desc:_this.versionData.description, //升级描述
                            version:_this.versionData.version //版本号
                        }
                        updataApp(params)
                    } else {
                        let params = {
                            isMust:_this.versionData.forced, //是否强制更新
                            doneTitle:'去升级', //确定按钮
                            cancelTitle:'取消', // 取消按钮
                            newDesc:'已是最新版本', //如果是最新版本提示文字
                            link:'itms-apps://itunes.apple.com/cn/app/jie-zou-da-shi/id这是id?mt=8',
                            desc:_this.versionData.description, //升级描述
                            version:_this.versionData.version //版本号
                        }
                        checkVersion( JSON.stringify(params))
                    }
                })
            }
        },
	    computed:{
		    ...mapGetters([
			    'loadFlag',
			    'direction',
                'appStart'
		    ])

	    }
    }
</script>
<style lang="less">
    @import '~vux/src/styles/reset.less';
    #app{background: #fff;}
    body,html{width: 100%;height: 100%;overflow-x: hidden; position: fixed;}
    .col-1{float: left;width: 10%;min-height: .1rem;}
    .col-2{float: left;width: 20%;min-height: .1rem;}
    .col-3{float: left;width: 30%;min-height: .1rem;}
    .col-4{float: left;width: 40%;min-height: .1rem;}
    .col-5{float: left;width: 50%;min-height: .1rem;}
    .col-6{float: left;width: 60%;min-height: .1rem;}
    .col-7{float: left;width: 70%;min-height: .1rem;}
    .col-8{float: left;width: 80%;min-height: .1rem;}
    .col-9{float: left;width: 90%;min-height: .1rem;}
    .m-navbar.navbar-fixed{top:20px!important;}
    .nav_mark{position: fixed;width: 100%;height: 22px;top:0;left:0;background: #fff;z-index: 100;}
    .back{display: block;width: .36rem;height: .36rem;background: url('assets/images/icon_back.png')center no-repeat;background-size: .2rem;}
    .loading{position: fixed;width: 100%;height: 100%;top:0;left:0;z-index: 9999;}
    .loading>div{position:absolute;width: 2rem;height: 2rem;background: rgba(0,0,0,0.2);border-radius: .1rem;top:30%;left:50%;margin-left: -1.0rem;text-align: center;}
    .loading>div img{width: 0.8rem;margin: 0 auto;margin-top: 0.6rem;}
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

    .appStart{position: fixed;top:0;left:0;width: 100%;height: 100%;background: rgba(0,0,0,0.6);z-index: 9999;padding-top: 3rem;}
    .appStart>div{width: 5rem;height: 5rem;background: #fff;margin: 0 auto;position: relative;}
    .app-close{position: absolute;top: 5rem;left:2.2rem;width: .6rem;height: 1.4rem;background: url("assets/images/app-close.png")center no-repeat;background-size: 100%;}
    .appStart img{width: 100%;height: 100%;}
</style>
