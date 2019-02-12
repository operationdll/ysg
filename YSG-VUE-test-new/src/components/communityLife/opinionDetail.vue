<template>
	<div class="common_nav_style">
		<div class="nav_mark"></div>
		<yd-navbar :title="info.title" fixed>
			<router-link :to="{path:'/opinionSurvey'}" slot="left">
				<span class="back"></span>
			</router-link>
		</yd-navbar>
		<section class="resolve-box" v-html="content"></section>
		<ul class="pdf-video" :class="{ 'one': isOne, 'two': isTwo}" v-show="btnFlag">
			<li @click="toPDF" v-show="pdfFlag"></li>
			<li @click="toVideo" v-show="videoFlag"></li>
		</ul>
	</div>
</template>

<style>
</style>
<script type="text/babel">
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                info:{},
	            pdfShow:false,
	            videoShow:false,
	            isOne:false,
	            isTwo:false,
	            pdfFlag:false,
	            videoFlag:false,
	            btnFlag:false,
                content:''
            };
        },
        created:function () {
            this.info = this.$route.query.info;
            this.$store.dispatch('showLoading')
            var ssrBase = 'https://bird.ioliu.cn/v1/?url='
            let _this = this;
            if (this.info.article) {
                $.get(ssrBase+this.info.article,function (res) {
                    _this.content = res;
                    _this.$store.dispatch('hideLoading')
                })
			} else{
                this.$store.dispatch('hideLoading')
			}

	        if(this.info.pdf && this.info.video){
		        this.btnFlag = true;
		        this.pdfFlag = true;
		        this.videoFlag = true;
		        this.isTwo = true;
	        }else if(this.info.pdf){
		        this.btnFlag = true;
		        this.pdfFlag = true;
		        this.isOne = true;
	        }else if(this.info.video){
		        this.btnFlag = true;
		        this.videoFlag = true;
		        this.isOne = true;
	        }
        },
        methods: {
	        toPDF:function () {
		        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			        openFile(this.info.pdf)
		        }else{
			        openPdf(this.info.pdf)
		        }

	        },
	        toVideo: function () {
		        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			        openFile(this.info.video)
		        }else{
			        openVideo(this.info.video)
		        }
	        },
            closeVideo:function () {
                this.videoFlag = false;
                this.videoUrl = '';

            }

        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components:{

        }
    };
</script>
