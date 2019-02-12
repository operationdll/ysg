<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="info.name" fixed>
            <router-link :to="{path:'/home'}" slot="left" v-if="stateFlag == '1'">
                <span class="back"></span>
            </router-link>
            <router-link :to="{path:'/s_home',query:{hotelid:hotelid}}" slot="left" v-if="stateFlag == '2'">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <section class="resolve-box" v-show="showContent" v-html="content"></section>

        <ul class="pdf-video" :class="{ 'one': isOne, 'two': isTwo}" v-show="btnFlag">
            <li @click="toPDF" v-show="pdfFlag"></li>
            <li @click="toVideo" v-show="videoFlag"></li>
        </ul>
    </div>
</template>
<style>
.detail-s{width: 100%;height: 100%;margin-top: 1.5rem;}
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				facData:{},
				info:{},
				pdfShow:false,
				videoShow:false,
				isOne:false,
				isTwo:false,
				pdfFlag:false,
				videoFlag:false,
				btnFlag:false,
                pageFlag:'',
				hotelid:'',
                content:{},
                stateFlag:'',
                showContent:false
			};
		},
        created:function () {
			this.info =  this.$route.query.data
            this.stateFlag = localStorage.stateFlag
            this.$store.dispatch('showLoading')
            var ssrBase = 'https://bird.ioliu.cn/v1/?url='
            let _this = this;
			if(this.info.detail){
                $.get(ssrBase+this.info.detail,function (res) {
                    _this.content = res;
                    _this.$store.dispatch('hideLoading')
                    _this.showContent = true
                })
            } else {
                this.$store.dispatch('hideLoading')
            }


			this.pageFlag = localStorage.menuFlag
	        this.facData = this.$route.query.data;
			if(this.facData.pdf && this.info.video){
				this.btnFlag = true;
				this.pdfFlag = true;
				this.videoFlag = true;
				this.isTwo = true;
			}else if(this.facData.pdf){
				this.btnFlag = true;
				this.pdfFlag = true;
				this.isOne = true;
			}else if(this.facData.video){
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
			}
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }
	};
</script>
