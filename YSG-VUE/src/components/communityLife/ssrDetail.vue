<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar title="SSRDETAIL" fixed>
            <router-link :to="{path:'/ssr'}" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="webview">
            <iframe :src="listData.link" frameborder="0" scrolling="yes">
            </iframe>
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
				panoramicSrc:'',
				listData:''
			};
		},
        created:function () {
            this.listData = this.$route.query.listData;
            
            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        }
	};
</script>
