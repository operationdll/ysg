<template>
<div class="property">
	<div class="nav_mark"></div>
	<yd-navbar :title="data.name" fixed>
		<router-link to="/raiders" slot="left">
			<span class="back"></span>
		</router-link>
		<!--<router-link :to="{path:'/judge',query:{judgeFlag:'raides'}}" slot="right">-->
			<!--<span class="judge"></span>-->
		<!--</router-link>-->
	</yd-navbar>
	<section class="resolve-box" v-html="content"></section>
	<div class="location-icon" @click="openMap"></div>
	<ul class="pdf-video" :class="{ 'one': isOne, 'two': isTwo}">
		<li @click="toPDF" v-show="pdfFlag"></li>
		<li @click="toVideo" v-show="videoFlag"></li>
	</ul>
</div>
</template>
<style>
	.location-icon{position: fixed;width: 1rem;height: 1rem;bottom: 2.5rem;right:0;background: url("../../assets/images/icon-location.png")center no-repeat;background-size: .9rem;}
	.judge{display: block;width: .5rem;height: .5rem;background: url("../../assets/images/icon_tips.png")center no-repeat;background-size: .5rem;}
</style>
<script type="text/babel">
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                data:{},
				srcUrl:'',
	            pdfShow:false,
	            videoShow:false,
	            isOne:false,
	            isTwo:false,
	            pdfFlag:false,
	            videoFlag:false,
	            btnFlag:false,
				content:''
            }
        },
        created:function () {
			this.data = this.$route.query.data
			console.log('hhhhh',this.data)
            this.$store.dispatch('showLoading')
	        if(this.data.pdf && this.data.video){
		        this.btnFlag = true
		        this.pdfFlag = true
		        this.videoFlag = true
		        this.isTwo = true
	        }else if(this.data.pdf){
		        this.btnFlag = true
		        this.pdfFlag = true
		        this.isOne = true
	        }else if(this.data.video){
		        this.btnFlag = true
		        this.videoFlag = true
		        this.isOne = true
	        }

            var ssrBase = 'https://bird.ioliu.cn/v1/?url='
            let _this = this;
	        if (this.data.detail) {
                $.get(ssrBase+this.data.detail,function (res) {
                    _this.content = res;
                    _this.$store.dispatch('hideLoading')
                })
			} else {
                this.$store.dispatch('hideLoading')
			}

        },
        methods: {
	        toPDF:function () {
		        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			        openFile(this.data.pdf)
		        }else{
			        openPdf(this.data.pdf)
		        }
	        },
	        toVideo: function () {
		        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			        openFile(this.data.video)
		        }else{
			        openVideo(this.data.video)
		        }
	        },
			openMap: function () {
                openMap(this.data.lng,this.data.lat)
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
