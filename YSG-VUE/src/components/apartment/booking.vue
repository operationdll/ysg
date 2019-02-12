<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar title="BOOKING" fixed>
            <router-link :to="{path:'/unlogin'}" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="webview">
            <iframe id="photo" :src="bookUrl" frameborder="0" scrolling="yes"></iframe>
        </div>
    </div>
</template>
<style>
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				bookUrl:''
			};
		},
        created:function () {
            this.bookUrl = this.$route.query.bookUrl;
            
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
