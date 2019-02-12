<template>
<div class="property">
	<div class="nav_mark"></div>
	<yd-navbar :title="title" fixed>
		<router-link to="/promotion" slot="left">
			<span class="back" ></span>
		</router-link>
	</yd-navbar>
	<section class="promotiom_list s-list" style="height: 16rem;">
          <scroller>
			  <iframe id="iframe_detail" :src="url"  frameborder="0" style="height: 16rem;width:100%;"></iframe>
          </scroller>
      </section>
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
                url:'',
	            title:''
            };
        },
        created:function () {
			this.url = this.$route.query.url;
			this.title = this.$route.query.title;
			$(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        },
        methods: {
        },
        mounted:function () {
        },
        components:{
        }
    };
</script>
