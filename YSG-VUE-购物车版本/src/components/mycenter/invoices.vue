<template>
    <div class="invoice">
        <div class="nav_mark"></div>
        <yd-navbar title="INVOICES" fixed>
            <router-link to="/home" slot="left">
                <span class="back" ></span>
            </router-link>
        </yd-navbar>
        <div class="invoice-content">
            <div @click="showHeader"><button type="button">发票抬头</button></div>
            <div><router-link to="/invoicesManage"><button type="button">发票管理</button></router-link></div>
        </div>
    </div>
</template>
<style>
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
                tokenSrc:''
			};
		},
		created:function () {
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        methods: {
            showHeader(){
                let _this = this
                // 获取token
                var dataStr = JSON.stringify({"source":"ascott","otId":"1947364"});
                dataStr = 'dataMap=' + dataStr;
                $.ajax({
                    type:'post',
                    url:'https://bird.ioliu.cn/v1/?url=http://www.e-fp.cn/issp/ApiService?method=xforceplus.sys.qToken',
                    async:false,
                    data:dataStr,
                    dataType:'jsonp',
                    success:function(json){
                        console.log('data—>',json.token);
                        _this.tokenSrc = 'http://www.e-fp.cn/wx_invoiceTitle_confirm.html?u='+json.token
                        //获取发票抬头
                        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                            openFile(_this.tokenSrc)
                        }else{
                            openPdf(_this.tokenSrc)
                        }

                    },
                    error:function () {
                    }
                });


            },
            showManager(){
                if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                    openFile(url)
                }else{
                    openPdf(url)
                }
            },

        }
	};
</script>
