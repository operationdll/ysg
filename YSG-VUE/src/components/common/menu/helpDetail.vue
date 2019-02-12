<template>
<div class="property">
	<div class="nav_mark"></div>
	<yd-navbar :title="title" fixed>
		<router-link to="/help" slot="left">
			<span class="back" ></span>
		</router-link>
	</yd-navbar>
	<section class="resolve-box" v-html="content">
		<!--<iframe id="iframe_detail" :src="info.article"  frameborder="0" ></iframe>-->
	</section>
</div>
</template>
<style>
</style>
<script>
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                info:{},
                title:'',
				content:''
            };
        },
        created:function () {
            this.$store.dispatch('showLoading')
			this.info = this.$route.query.url
			this.title = this.$route.query.title

            var ssrBase = ''
			let _this = this;
            $.get(ssrBase+this.info,function (res) {
				_this.content = res;
                _this.$store.dispatch('hideLoading')
            });

            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        },
        methods: {
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components:{
        }
    };
</script>
