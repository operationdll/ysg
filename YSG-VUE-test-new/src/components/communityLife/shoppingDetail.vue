<template>
<div class="property">
	<div class="nav_mark"></div>
	<yd-navbar title="SHOPPING DETAIL" fixed>
		<router-link to="/shopping" slot="left">
			<span class="back" ></span>
		</router-link>
	</yd-navbar>
	<section class="iframe-box">
		<iframe id="iframe_detail" :src="detail.detail"  frameborder="0" ></iframe>
	</section>

	<ul class="pdf-video" :class="{ 'one': isOne, 'two': isTwo}" v-show="btnFlag">
		<li @click="toPDF" v-show="pdfFlag"></li>
		<li @click="toVideo" v-show="videoFlag"></li>
	</ul>
</div>
</template>
<style>
</style>
<script type="text/babel">
//	import pdf from 'vue-pdf'

    export default {
        data() {
            return {
                detail:{},
	            dataList:[],
	            pdfShow:false,
	            videoShow:false,
	            isOne:false,
	            isTwo:false,
	            pdfFlag:false,
	            videoFlag:false,
	            btnFlag:false
            };
        },
        created:function () {
			let id = this.$route.query.id;

	        let params = {'hotelid':localStorage.HOTELID,'lang':localStorage.LANGUAGE};
	        this.$store.dispatch('getShoppingList', params).then((res) => {
		        this.dataList = res.data.data.list;
		        for(var item in this.dataList){
		        	if(id == this.dataList[item].id){
				        this.detail = this.dataList[item]
			        }
		        }

		        if(this.detail.pdf && this.detail.video){
			        this.btnFlag = true;
			        this.pdfFlag = true;
			        this.videoFlag = true;
			        this.isTwo = true;
		        }else if(this.detail.pdf){
			        this.btnFlag = true;
			        this.pdfFlag = true;
			        this.isOne = true;
		        }else if(this.detail.video){
			        this.btnFlag = true;
			        this.videoFlag = true;
			        this.isOne = true;
		        }
	        }).catch((res) => {
	        });
//
        },
        methods: {
	        toPDF:function () {
		        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			        openFile(this.detail.pdf)
		        }else{
			        openPdf(this.detail.pdf)
		        }

	        },
	        toVideo: function () {
		        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			        openFile(this.detail.video)
		        }else{
			        openVideo(this.detail.video)
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
        mounted() {

        },
        components:{



        }
    };
</script>
