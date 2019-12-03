<template>
<div class="property">
	<div class="nav_mark"></div>
    <v-back v-bind:title="title" style="position:fixed;"></v-back>
	<section class="resolve-box" v-html="content">
		<!--<iframe id="iframe_detail" :src="info.article"  frameborder="0" ></iframe>-->
	</section>
</div>
</template>
<style>
</style>
<script>
    import { mapState } from 'vuex'
    import back from "../../back";

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
        components: {
            "v-back": back
        }
    };
</script>
