<template>
    <div class="appartment_style">
        <div class="nav_mark"></div>
        <yd-navbar title="SERVICE" fixed>
            <router-link to="/home" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="webview"style="margin-top: .35rem;">
            <iframe class="iframeStyle"  :src="serviceUrl" frameborder="0" scrolling="yes"></iframe>
        </div>

    </div>
</template>
<style>
.iframeStyle{width: 100%;height: 100%;padding-top: 1rem;}
.m-navbar{z-index: 800;}
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				panoramicSrc:'',
                serviceUrl:''
			};
		},
        created:function () {
            this.serviceUrl = localStorage.serviceUrl;
        },
		methods: {
			goHome:function () {
                this.$router.push('/home')
			}
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
