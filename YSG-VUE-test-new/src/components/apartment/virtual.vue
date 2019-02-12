<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="title" fixed>
            <router-link :to="{path:'/home', query:{pageType: pageType}}" slot="left" v-if="stateFlag == '1'">
                <span class="back"></span>
            </router-link>
            <router-link :to="{path:'/s_home',query:{hotelid:hotelid}}" slot="left" v-if="stateFlag == '2'">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="webview">
            <iframe class="setIframe"  :src="panoramicSrc" frameborder="0" scrolling="yes"></iframe>
        </div>
    </div>
</template>
<style>
.setIframe{width: 100%;height: 100%;margin-top: 1.4rem;}
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				panoramicSrc:'',
				pageType:'',
                title:'',
                stateFlag:''
			};
		},
        created:function () {
            this.stateFlag = localStorage.stateFlag
            this.panoramicSrc = 'http://'+this.$route.query.src;
            this.pageType = this.$route.query.pageType;
            this.title = this.$route.query.title;
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
