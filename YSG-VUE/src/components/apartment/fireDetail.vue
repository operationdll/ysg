<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="info.floor" fixed>
            <router-link to="/home" slot="left" v-if="stateFlag == '1'">
                <span class="back"></span>
            </router-link>
            <router-link :to="{path:'/s_home',query:{hotelid:hotelid}}" slot="left" v-if="stateFlag == '2'">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <section class="resolve-box" v-html="content"></section>
    </div>
</template>
<style>
    .viewer-button{top:-20px;background:none;}
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				info:{},
                content:'',
                stateFlag:''
			};
		},
        created:function () {
	        this.info = this.$route.query.info;
            this.stateFlag = localStorage.stateFlag
            var ssrBase = ''
            let _this = this;
            if(this.info.detail){
                $.get(ssrBase+this.info.detail,function (res) {
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
        mounted:function () {
            $(document).ready(function () {
                $('.resolve-box').viewer()
            })
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }
	};
</script>
