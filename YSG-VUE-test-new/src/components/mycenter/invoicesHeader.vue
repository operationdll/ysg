<template>
    <div class="invoice">
        <div class="nav_mark"></div>
        <yd-navbar title="INVOICES" fixed>
               <router-link to="/invoices" slot="left">
                <span class="back" ></span>
            </router-link>
        </yd-navbar>
        <div class="webview">
            <iframe class="iframe-style" :src="tokenSrc" frameborder="0" scrolling="yes"></iframe>
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
			// tokenSrc:'',
			};
		},
			created:function () {
		    let _this = this
            // 获取token
            var dataStr = JSON.stringify({"source":"YSG","otId":"1947364"});
            dataStr = 'dataMap=' + dataStr;
            $.ajax({
                type:'post',
                url:'https://bird.ioliu.cn/v1/?url=http://www.xfplink.cn/issp/ApiService?method=xforceplus.sys.qToken',
                async:false,
                data:dataStr,
                dataType:'jsonp',
                success:function(json){
                    console.log('data—>',json.token);
                    _this.tokenSrc ='http://www.xfplink.cn/wx_invoiceTitle_confirm.html?u='+json.token+'&m=YSG&b=X80';
                    console.log(_this.tokenSrc);
                   //获取发票抬头

                },
                error:function () {
                }
            });

		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        methods:{
   
        },



       
		created:function () {
		    // let _this = this
            // // 获取token
            // var dataStr = JSON.stringify({"source":"ascott","otId":"1947364"});
            // dataStr = 'dataMap=' + dataStr;
            // $.ajax({
            //     type:'post',
            //     url:'https://bird.ioliu.cn/v1/?url=http://www.e-fp.cn/issp/ApiService?method=xforceplus.sys.qToken',
            //     async:false,
            //     data:dataStr,
            //     dataType:'jsonp',
            //     success:function(json){
            //         console.log('data—>',json.token);
            //         _this.tokenSrc = 'http://www.e-fp.cn/wx_invoiceTitle_confirm.html?u='+json.token
            //        //获取发票抬头

            //     },
            //     error:function () {
            //     }
            // });

		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
	};
</script>
