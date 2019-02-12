<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar title="NEWSDETAIL" fixed>
            <router-link :to="{path:'/home/news'}" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="webview">
            <iframe id="photo" :src="url" frameborder="0" scrolling="yes"></iframe>
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
				url:''
			};
		},
		created:function () {

			this.url = this.$route.query.url;
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
	};
</script>
