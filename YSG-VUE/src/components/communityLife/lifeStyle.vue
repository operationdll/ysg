<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.lifeStyle.title" fixed>
            <router-link :to="{path:'/home'}" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <scroller style="background: #f2f2f2;">
            <section class="life-style">
                <ul>
                    <h4><img src="../../assets/images/icon-lifestyle-1.png" alt=""> {{language.lifeStyle.food}}</h4>
                    <li>
                        <div>
                            <img @click="goDetail('https://h5.ele.me/msite/',1)" src="../../assets/images/icon-life-1.png" alt="">
                            <img  @click="goDetail('https://waimai.baidu.com/',2)"  style="border: 1px solid #eee;border-radius: 10px;" src="../../assets/images/baidu.jpg" alt="">
                        </div>
                    </li>
                    <h4><img src="../../assets/images/icon-lifestyle-2.png" alt=""> {{language.lifeStyle.movie}}</h4>
                    <li>
                        <div>
                            <img @click="goDetail('https://m.iqiyi.com/',3)" src="../../assets/images/icon-life-3.png" alt="">
                            <img @click="goDetail('https://m.v.qq.com/',4)" src="../../assets/images/icon-life-4.png" alt="">
                        </div>
                    </li>
                    <h4><img src="../../assets/images/icon-lifestyle-4.png" alt="">  {{language.lifeStyle.phone}}</h4>
                    <li>
                        <div>
                            <img @click="goDetail('https://h5.m.taobao.com/app/cz/cost.html#/cost',5)" src="../../assets/images/icon-life-5.png" alt="">
                            <img @click="goDetail('https://h5.m.taobao.com/app/cz/cost.html#/flow',6)"src="../../assets/images/icon-life-5.png" alt="">
                        </div>
                    </li>
                    <h4><img src="../../assets/images/icon-lifestyle-5.png" alt="">  {{language.lifeStyle.express}}</h4>
                    <li>
                        <div>
                            <img @click="goDetail('https://m.kuaidi100.com/',7)" style="border: 1px solid #eee;border-radius: 10px;" src="../../assets/images/icon-life-100.png" alt="">
                        </div>
                    </li>
                    <h4><img src="../../assets/images/icon-lifestyle-6.png" alt="">  {{language.lifeStyle.trip}}</h4>
                    <li>
                        <div>
                            <img @click="goDetail('https://common.ofo.so/newdist/',8)" src="../../assets/images/icon-life-ofo.png" alt="">
                            <img @click="goDetail('https://common.diditaxi.com.cn/general/webEntry#/',9)" src="../../assets/images/icon-life-dd.png" alt="">
                        </div>
                    </li>
                </ul>
            </section>
        </scroller>
    </div>
</template>
<style>
.life-style{padding-top: 1.3rem;}
.life-style h4{font-size: .28rem;padding: .2rem .3rem;}
.life-style h4 img{height: .5rem;display: inline-block;vertical-align: middle}
.life-style li{padding: .3rem;overflow: hidden;background: #fff;}
.life-style li img{width: 1rem;}
.life-style li h3{font-size: .36rem;font-weight: normal;}
.life-style li p{font-size: .28rem;color: #6666;margin-top: .2rem;}
.life-style li {text-align: left;}
.life-style li img{width: 1rem;display: inline-block;margin-right: .3rem;}
.life-style li img:last-child{margin-right: 0;}
.life-style li a{padding-right: .3rem;}
</style>
<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				panoramicSrc:'',
				tabIndex:''
			};
		},
		created:function () {
            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
		methods:{
            goDetail: function (url,flag) {
                this.$router.push({path:'/windowOpen',query:{url:url,title:$(".center-title").html(),backUrl:'/lifeStyle'}});
            }
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })

        },
	};
</script>
