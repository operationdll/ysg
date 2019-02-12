<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.myCenter.gsm" fixed>
            <router-link to="/home" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="webview">
            <iframe class="service" :src="gsmSrc" id="photo" frameborder="0" scrolling="yes"></iframe>
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
				gsmSrc:'',
                htmlObj:{}
			};
		},
        created:function () {
            let _this = this;
	        let params = {
		        token:localStorage.TOKEN
	        }
	        this.$store.dispatch('getUsers', params).then(function (res) {
                _this.gsmSrc = res.data.serviceUrl
	        })

        },
		computed: {
			...mapState({
				language: state => state.language.language
			})
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }

	};
</script>
