<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar title="VIRTUAL TOUR" fixed>
            <router-link :to="{path:'/appartmentStyle',query:{index:tabIndex}}" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="webview">
            <iframe id="photo" :src="panoramicSrc" frameborder="0" scrolling="yes"></iframe>
        </div>
    </div>
</template>
<style>
.webview{width: 100%;height: 100%;position: absolute;top:0;left:0;}
#photo{width: 100%;height: 100%;margin-top: 1.5rem;}
.common_nav_style .m-navbar{z-index: 200}
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
	        this.tabIndex = this.$route.query.index;
            this.panoramicSrc = 'http://'+this.$route.query.panoramic;
            
            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }
	};
</script>
