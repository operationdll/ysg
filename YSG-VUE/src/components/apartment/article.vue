<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="title" fixed>
            <router-link :to="{path:'/appartmentStyle',query:{index:tabIndex}}" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <section class="resolve-box" v-show="showContent" v-html="content"></section>
        <ul class="pdf-video">
            <li @click="toPDF" v-show="showPdf"></li>
            <li @click="toVideo" v-show="showVideo"></li>
        </ul>
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
				tabIndex:'',
                detail:'',
                pdf:'',
                video:'',
                content:'',
                showContent:false,
                title:this.$route.query.title,
                showPdf: false,
                showVideo:false
			};
		},
		created:function () {
	        this.tabIndex = this.$route.query.index;
	        this.detail = this.$route.query.detail;
	        this.pdf = this.$route.query.pdf;
            this.video = this.$route.query.video;
            this.showPdf = this.pdf==''?false:true;
            this.showVideo = this.video==''?false:true;
            this.$store.dispatch('showLoading')
	        this.panoramicSrc = this.detail;
            var ssrBase = ''
            let _this = this;
            if(this.panoramicSrc){
                $.get(ssrBase+this.panoramicSrc,function (res) {
                    _this.content = res;
                    _this.$store.dispatch('hideLoading')
                    _this.showContent = true
                })
            } else {
                this.$store.dispatch('hideLoading')
            }

            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        },
        methods: {
            toPDF:function () {
                if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                    openFile(this.pdf);
                }else{
                    openPdf(this.pdf);
                }

            },
            toVideo: function () {
                if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                    openFile(this.video);
                }else{
                    openVideo(this.video);
                }
            }
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }
	};
</script>
