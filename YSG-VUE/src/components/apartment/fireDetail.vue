<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <v-back v-bind:title="info.floor"></v-back>
        <section class="resolve-box" v-html="content"></section>
    </div>
</template>
<style>
    .viewer-button{top:-20px;background:none;}
</style>

<script>
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    import back from "../back";

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
            });
             //一级页面falg
            isHomePage(0)
        },
        components: {
            "v-back": back
        }
	};
</script>
