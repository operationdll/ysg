<template>
<div class="property">
	<div class="nav_mark"></div>
	<yd-navbar :title="info.title" fixed>
			<span class="back" @click="goBack" slot="left"></span>
		<!--<router-link :to="{path:'/judge',query:{judgeFlag:'activity',info:info}}" slot="right">-->
			<!--<span class="judge"></span>-->
		<!--</router-link>-->
	</yd-navbar>

	<section class="resolve-box" v-html="content"></section>
	<section class="foot_btn_box">
			<button class="foot_btn" type="button" @click="toApply">{{language.community.signUp}}</button>
	</section>

</div>
</template>
<style>
.iframe_style{width: 100%;height: 11rem;}
.foot_btn_box{position: fixed;left:0;bottom: 0;width: 100%;background: none;}
.foot_btn_box .foot_btn{width: 100%;height: 1rem;color: #fff;background: #f0c366;border: none;font-size: .3rem;}
</style>
<script type="text/babel">
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                info:{},
                srcUrl:'',
				content: ''
            };
        },
        created:function () {
            this.info = this.$route.query.info;
            this.$store.dispatch('showLoading')
            var ssrBase = ''
            let _this = this;
            if(this.info.article) {
                $.get(ssrBase+this.info.article,function (res) {
                    _this.content = res;
                    _this.$store.dispatch('hideLoading')
                })
			} else {
                this.$store.dispatch('hideLoading')
			}

            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        },
        methods: {
            toApply: function () {
                this.$router.push({path:'/apply',query:{info:this.info,flag:1}});
            },
			goBack: function () {
                this.$router.push({path:'/propertyActivity'});
//				window.history.go(-1)
            }
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components:{

        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        }
    };
</script>
