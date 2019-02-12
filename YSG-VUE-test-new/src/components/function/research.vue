<template>
    <div class="research">
        <div class="nav_mark"></div>
        <yd-navbar title="Research" fixed>
           <span slot="left" class="back" @click="goBack"></span>
        </yd-navbar>
        <div class="webview">
            <iframe class="iframe-style" :src="serviceUrl" frameborder="0" scrolling="yes"></iframe>
        </div>
    </div>
</template>
<style>
    .iframe-style{width: 100%;height: 100%;padding-top: 1.5rem;}
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				serviceUrl:'',
			};
		},
		created:function () {
            let _this = this;
            let params;
            if(localStorage.TOKEN){
                params = {'token':localStorage.TOKEN,'hotelid':localStorage.HOTELID,lang:localStorage.LANGUAGE};
            } else {
                params = {'hotelid':localStorage.HOTELID,lang:localStorage.LANGUAGE};
            } 
 
            this.$store.dispatch('getUrl', params).then(function (res) { 
                // alert(JSON.stringify(res))
                _this.serviceUrl = res.data.data.surveyurl;
                // alert(_this.serviceUrl);
            });

		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)

     
        },

        methods:{
            goBack: function(){
                let _this = this;
               if(this.$route.query.page == 'unlogin' || localStorage.spage == 1){
                    this.$router.push('s_home')
                }else{
                    this.$router.push('home')
                }
            }
        }
	};
</script>
