<template>
	<div class="property">
		<div class="nav_mark"></div>
		<yd-navbar :title="info.name" fixed>
			<router-link to="/ascottLife" slot="left">
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
	.iframe_style{width: 100%;height: 11rem;}
	.foot_btn_box{position: fixed;left:0;bottom: 0;width: 100%;background: none;}
	.foot_btn_box .foot_btn{width: 100%;height: 1rem;color: #fff;background: #f0c366;border: none;font-size: .3rem;}
</style>
<script type="text/babel">
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                info:{},
                videoUrl:'',
//				sources:[],
                pdfUrl:'',
                videoFlag:false,
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
            if (this.info.detail) {
                $.get(ssrBase+this.info.detail,function (res) {
                    _this.content = res;
                    _this.$store.dispatch('hideLoading')
                })
			} else {
                this.$store.dispatch('hideLoading')
			}

            this.pdfUrl = this.info.pdf;
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
//                this.sources[0].src = '';
		        this.videoFlag = false;
		        this.videoUrl = '';

	        },
	        play() {
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
