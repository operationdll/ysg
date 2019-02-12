<template>
    <div class="box">

        <transition name="fade">
            <div class="mark" v-show="markFlag"  @click="hideSlide"></div>
        </transition>
        <div class="guide">
            <yd-navbar class="guide_title" title="">
                <span class="user" @click="goLogin" slot="left"></span>

                <router-link to="#" slot="right">
                    <span class="menu" @click="showSlide"></span>
                </router-link>
            </yd-navbar>

            <scroller>
                <section class="guide_bottom">
                    <div class="search_box">
                        <input type="text" value="Find Your Residence" @click="goSearch">
                        <i></i>
                    </div>
                    <div class="txt">
                        <p>TRAVEL SHOULD TAKE YOU PLACE <br> NOT JUST PHYSICALLY</p>
                        <h4>BUT MENTALLY</h4>
                        <div class="slide">
                            <i id="slideBar"></i>
                        </div>
                    </div>
                </section>
            </scroller>

        </div>

        <actionsheet v-model="tabLanShow" :menus="menus" @on-click-menu="hideAction" show-cancel></actionsheet>
        <v-menu v-show="menuShow"></v-menu>
    </div>
</template>

<style>
    .guide{position:absolute;top:0;left:0;width: 100%;height: 100%;background: url("../../assets/images/guide_bg.jpg")center no-repeat;background-size: cover;z-index: 2;box-shadow: 4px 0px 8px 0 rgba(0,0,0,.3);overflow: hidden;}
    .guide header{background: none!important;}
    .guide_title{margin-top: 20px;}
    .guide>div:first-child{z-index: 100;}
    .guide .m-navbar:after{border: none;}
    .guide .user{display: inline-block;width: .4rem;height: .5rem;background: url('../../assets/images/icon_user.png')center no-repeat;background-size: .38rem;}
    .guide .menu{display: inline-block;width: .4rem;height: .5rem;background: url('../../assets/images/icon_menu.png')center no-repeat;background-size: .38rem;}
    .guide .logo_guide{position:relative;}
    .guide .logo_guide img{width: 2.8rem;margin: 0 auto;}
    .guide .guide_bottom{padding: .36rem;margin-top: 7.5rem;}
    .guide .guide_bottom .txt{text-align: center;padding-top: 1rem;font-size: .28rem;color: #fff;line-height: .36rem;}
    .guide .guide_bottom .txt h4{color: #efb562;}
    .guide .search_box{padding: .35rem 0;border-bottom: 1px solid #fff;}
    .guide .search_box input{font-size: .28rem;border: none;width: 80%;color: #fff;}
    .guide .search_box input::-webkit-input-placeholder{color: #fff;}
    .guide .search_box input:-moz-placeholder {color: #fff;}
    .guide .search_box input::-moz-placeholder {color: #fff;}
    .guide .search_box i{float: right;width: .4rem;height: .4rem;background: url("../../assets/images/icon_search.png")center no-repeat;background-size: .4rem;}
    .guide .slide i{display: inline-block;width: .5rem;height: .8rem;background: url("../../assets/images/icon_slide@2x.png")center no-repeat;background-size: .5rem;}
    .guide .slide{margin-top: .4rem;}

    #slideBar
    {-webkit-animation-name: fadeIn; /*动画名称*/
        -webkit-animation-duration: 2s; /*动画持续时间*/
        -webkit-animation-iteration-count:  infinite; /*动画次数*/
        -webkit-animation-delay: 0s; /*延迟时间*/
    }

    @-webkit-keyframes fadeIn {
        0% {
            opacity: 0.5; /*初始状态 透明度为0*/
        }
        50% {
            opacity: 1; /*中间状态 透明度为0*/
        }
        100% {
            opacity: 0.5; /*结尾状态 透明度为1*/
        }
    }

</style>
<script>
	import AlloyFinger from 'alloyfinger'
	import { Actionsheet } from 'vux'
	import { mapGetters } from 'vuex'
    import menu from '../common/menu.vue'
	export default {
		data() {
			return {
				menuFlag:false,
				menus:{
					en:'English',
					zh:'中文'
				},
				markFlag:false,
				menuShow:false
			};
		},
        created:function () {
			//todo
	        localStorage.visit = 1;
	        //设置当前页面标识
	        localStorage.menuFlag = 'guide';
	        //初始化语言
			this.$store.dispatch('firstLanguage');
			
			$(function(){
                $(".navbar-center").css('marginLeft',0);
            });
	    },
        mounted(){
			let _this = this;

	        var alloyTouch = new AlloyTouch({
		        touch:".guide_bottom",//反馈触摸的dom
		        vertical: true,//不必需，默认是true代表监听竖直方向touch
//		        target: target, //运动的对象
		        property: "translateY",  //被运动的属性
		        min: 100, //不必需,运动属性的最小值
		        max: 2000, //不必需,滚动属性的最大值
		        sensitivity: 1,//不必需,触摸区域的灵敏度，默认值为1，可以为负数
		        factor: 1,//不必需,表示触摸位移与被运动属性映射关系，默认值是1
		        step: 45,//用于校正到step的整数倍
		        bindSelf: false,
		        initialValue: 0,
		        change:function(value){  },
		        touchStart:function(evt, value){  },
		        touchMove:function(evt, value){  },
		        touchEnd:function(evt,value){
			        _this.$router.push('/unlogin')

                },
		        tap:function(evt, value){  },
		        pressMove:function(evt, value){  },
		        animationEnd:function(value){  } //运动结束
	        })


        },
		methods: {
			//显示菜单
			showSlide:function () {
				this.menuShow = true;
				if(this.menuFlag == false){
					$('.guide').animate({
						'left':'-5rem',
					},500)
					$('.mark').animate({
						'left':'-5rem',
					},500);
                    this.menuFlag = true;
					this.markFlag = true;
                }
			},
            //跳转首页
            goSearch:function () {

                this.$router.push({path:'/search',query:{searchFlag:'guide'}});
            },
			//抽屉效果收回
            hideSlide:function () {
                if(this.menuFlag == true){
	                $('.guide').animate({
		                'left':0,
	                },500)
	                $('.mark').animate({
		                'left':0,
	                },500);
	                this.menuFlag = false;
	                this.markFlag = false;
                }
            },
            //跳转未登录状态首页
            goHome:function () {


            },
			//跳转至登录页面
			goLogin:function () {
				this.$router.push('/loginforguest')
			},
			hideAction:function (key) {
				//调取切换语言接口
				let params = {
					token: localStorage.TOKEN,
					platform: localStorage.platform,
					identity:localStorage.identity,
					lang:key

				}
				this.$store.dispatch('tabLanguage', params).then((res) => {
					if(res.code == 0){
						//更新本地语言标识
						localStorage.LANGUAGE = key;
						this.$store.dispatch('updateLanguage',key)

					}else{
						this.$dialog.toast({mes: res.msg, timeout: 1000});
					}
				})

			},



		},
		components: {
			'v-menu': menu,
			Actionsheet
		},
        computed: {
			...mapGetters([
				'language',
				'tabLanShow'
			])
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }
	};
</script>
