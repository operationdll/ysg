<template>
  <div class="menux">
      <h2>{{language.menu.title}}</h2>
      <ul>
          <li @click="find">{{language.menu.find}}<i></i></li>
          <li @click="tabLanguage">{{language.menu.language}}<i></i></li>
          <li @click="help">{{language.menu.help}} <i></i></li>
          <li @click="aboutUs">{{language.menu.aboutus}}<i></i></li>
          <li @click="emSign">{{language.menu.emSign}} <i></i></li>
          <li @click="updataApp">{{language.menu.update}}<i></i></li>
      </ul>
  </div>
</template>

<style>
    .menux{position:absolute;top:0;left:0;width:100%;height:100%;padding: 1rem .36rem;background: #fff;z-index: 1;}
    .menux h2{color: #4a4a4a;font-size: .48rem;padding-left: 40%;}
    .menux>ul{margin-top: 1.2rem;}
    .menux>ul li{height: 1rem;line-height: 1rem;padding-left: 40%; border-bottom: 1px solid #eee;font-size: .28rem;color: #404040;}
    .menux>ul li i{float: right;width:.2rem;height: .35rem;background: url("../../assets/images/icon_right.png")center no-repeat;background-size: .2rem; margin-top: .32rem;}
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				languageShow:false,
                menuFlag:'',
                versionData:{}
             };
		},
        created:function () {
			//切换语言菜单显示隐藏控制
	        if( localStorage.TOKEN ){
		        this.languageShow = true;
	        }
	        //获取标识
	        this.menuFlag = localStorage.menuFlag
        },
		methods: {
			tabLanguage:function (key) {
			    if (localStorage.stateFlag == '2') {
                    this.$store.dispatch('changeLanguage2');
                } else {
                    this.$store.dispatch('changeLanguage');
                }

		    },
            help:function () {
                this.$router.push({path:'/help',query:{menuFlag : this.menuFlag}})
            },
            emSign:function () {
                this.$router.push('loginForEmployee');
            },
            aboutUs: function () {
                this.$router.push({path:'/aboutUs',query:{menuFlag : this.menuFlag}})
            },
            find:function () {

	            switch (this.menuFlag){
		            //已登录首页
		            case 'home':
			            this.$router.push({path:'/search',query:{searchFlag:'home'}})
			            break;
		            //未登录首页
		            case 'unlogin':
			            this.$router.push({path:'/search',query:{searchFlag:'unlogin'}})
			            break;
		            //引导页
		            case 's_home':
			            this.$router.push({path:'/search',query:{searchFlag:'s_home'}})
			            break;
	            }

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
                            link:'https://itunes.apple.com/us/app/ascott-iservice/id1268497466?l=zh&ls=1&mt=8',
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
                            link:'https://iservices-iam.the-ascott.com.cn/download/V2',
                            desc:_this.versionData.description, //升级描述
                            version:_this.versionData.version //版本号
                        }
                        checkVersion( JSON.stringify(params))
                    }
                })
            }

		},
		computed: {
			...mapState({
				language: state => state.language.language
			})
		},

	};
</script>
