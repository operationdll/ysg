<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar title="Notice" fixed>
            <router-link to="/home" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="news_content">
            <p></p>
            <ul>
                <li v-for="item in newsList">
                    <router-link :to="{path:'/home/newsDetail', query:{newsid:item.msgId}}">
                        <img :src="item.pic" alt="">
                        <p>{{item.msg}}</p>
                    </router-link>
                </li>
            </ul>
        </div>
    </div>
</template>
<style>
.webview{width: 100%;height: 100%;position: absolute;top:0;left:0;}
#photo{width: 100%;height: 100%;}
.common_nav_style .m-navbar{z-index: 200}
.news_content{padding: .3rem;}
.news_content img{width: 100%;height: 3rem;}
.news_content p{font-size: .24rem;color: #666;padding: .2rem 0;}
.news_content li{border-bottom: 1px solid #eee;margin-bottom: .2rem;}
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				newsList:{}

			};
		},
        created:function () {
            this.panoramicSrc = 'http://'+this.$route.query.panoramic;

	        //调用消息接口
	        let params = {
		        hotelid: localStorage.HOTELID,
		        groupid: localStorage.groupid,
		        platform:localStorage.platform,
		        identity:localStorage.identity
	        }
	        this.$store.dispatch('getNews', params).then((res) => {
		        if(res.code == 0){
                    this.newsList = res.data.data;

		        }else{
			        this.$dialog.toast({mes: res.msg, timeout: 1000});
		        }
	        })


        },
		methods: {

		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components:{

        },
		computed: {
		},
	};
</script>
