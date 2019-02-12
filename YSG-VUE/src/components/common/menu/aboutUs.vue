<template>
	<div class="common_nav_style">
		<div class="nav_mark"></div>
		<yd-navbar title="ABOUT US" fixed>
				<span slot="left" class="back" @click="goBack"></span>
		</yd-navbar>
		<section class="resolve-box" v-html="content">
			<!--<iframe id="iframe_detail" :src="info.article"  frameborder="0" ></iframe>-->
		</section>
	</div>
</template>

<style>

</style>
<script type="text/babel">
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                content:''
            };
        },
        created:function () {
            let _this =this;
            this.$store.dispatch('showLoading')
            let params = {
                paramtype: 'get',
                id:1
			}
            this.$store.dispatch('groupDetail',params).then(function(res){

                var ssrBase = ''
                $.get(ssrBase+res.data.about,function (res) {
                    _this.content = res;
                    _this.$store.dispatch('hideLoading')
                })
			});

			$(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        },
        methods: {
        	//判断用户是否登录
			goBack:function () {
				let flag = localStorage.menuFlag;
				switch (flag){
					//已登录首页
					case 'home':
						this.$router.push('/home')
						break;
					//未登录首页
					case 'unlogin':
						this.$router.push('/unlogin')
						break;
					//引导页
					case 'guide':
						this.$router.push('/guide')
						break;
					//查询特殊首页
					case 's_home':
						this.$router.push('/s_home')
						break;
				}

			}

        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components:{

        },
    };
</script>
