<template>
<div class="property">
	<div class="nav_mark"></div>
	<v-back v-bind:title="title" home="home"></v-back>
	<section class="promotiom_list s-list" style="height: 12rem;">
          <scroller>
			  <iframe id="iframe_detail" :src="url"  frameborder="0" style="height: 11.77rem;padding:3px;width:100%;"></iframe>
          </scroller>
      </section>
</div>
</template>
<style scoped>
	.resolve-box{padding: .3rem;padding-top: 1.4rem;font-size: .28rem;background: #fff;}
	.resolve-box img{max-width: 100%;height: auto!important;}
</style>
<script>
    import { mapState } from 'vuex'
    import back from "./back";

    export default {
        data() {
            return {
                url:'',
                title:'',
                backUrl:''
            };
        },
        created:function () {
			this.url = this.$route.query.url;
            this.title = this.$route.query.title;
            this.backUrl = this.$route.query.backUrl;
			$(function(){
                $(".navbar-center").css('marginLeft',0);
                $("body").css({"background":"white"});
            });
        },
        methods: {
        },
        mounted:function () {
        },
        components: {
            "v-back": back
        }
    };
</script>
