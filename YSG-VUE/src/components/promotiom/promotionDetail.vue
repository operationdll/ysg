<template>
<div class="property">
	<div class="nav_mark"></div>
	<yd-navbar :title="info.title" fixed>
		<router-link to="/promotion" slot="left">
			<span class="back" ></span>
		</router-link>
		<!--<router-link :to="{path:'/judge',query:{judgeFlag:'promotion',info:info}}" slot="right">-->
			<!--<span class="judge"></span>-->
		<!--</router-link>-->
	</yd-navbar>
	<section class="resolve-box" v-html="content">
		<!--<iframe id="iframe_detail" :src="info.article"  frameborder="0" ></iframe>-->
	</section>
	<ul class="pdf-video" :class="{ 'one': isOne, 'two': isTwo}" v-show="btnFlag">
		<li @click="toPDF" v-show="pdfFlag"></li>
		<li @click="toVideo" v-show="videoFlag"></li>
	</ul>
</div>
</template>
<style>
	.resolve-box{padding: .3rem;padding-top: 1.4rem;font-size: .28rem;background: #fff;}
	.resolve-box img{max-width: 100%;height: auto!important;}
</style>
<script>
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
            this.$store.dispatch('showLoading')
			this.info = this.$route.query.info
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

            var ssrBase = ''
			let _this = this;
            $.get(ssrBase+this.info.article,function (res) {
				_this.content = res;
                _this.$store.dispatch('hideLoading')
            });

			$(function(){
                $(".navbar-center").css('marginLeft',0);
            });
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
