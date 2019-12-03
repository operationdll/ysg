<template>
  <div>
    <div class="search" style="height: 100%" id="section5">
      <div class="nav_mark"></div>
       <span style="display:flex;margin-top:.4rem;float:left;z-index:101;position: fixed;margin-left:5.5rem;font-size:.3rem;width:2rem;height:1rem;justify-content: center;align-items: center;" @click="isProperty = true">
           <div style="color:#afafaf;" v-if="isZH">选择物业</div>
           <div style="color:#afafaf;" v-if="!isZH">Property</div>
      </span>
      <popup-picker :data="properties" :show="isProperty" :columns="1" @on-hide="isProperty = false" @on-change="onPropertyChange"></popup-picker>
      <v-back v-bind:title="title" style="position:fixed;z-index:100;margin-top:-.5rem"></v-back>
      <div style="width:100%;height:120px;margin-top:1.2rem;">
            <div style="position:absolute;left:30px;top:104px;font-size:0.4rem;font-family:Avenir-Heavy;color:#ffffff;">{{hName}}</div>
            <img :src="hUrl" width="100%" height="100%">
      </div>
      <section class="g-flexview" style="background:white;">
        <section class="g-scrollview">
          <div id="orderDiv" class="m-list list-theme4">
            <ul class="type-buy">
              <li v-for="(order, index) in ordDataList" style="border-bottom:0px;">
                <table border="0" width="100%">
                    <tr>
                        <td>
                            <div style="font-size:20px;font-family:PingFangSC-Semibold;color:#f0c366;">
                                RMB {{order.price}} <font style="color:#4a4a4a;font-size:14px;font-family: PingFangSC-Light;">{{order.products[0].title}}</font><font style="color:#4a4a4a;font-size:14px;font-family: PingFangSC-Light;" v-if="order.products.length>1">...</font>
                            </div>
                        </td>
                        <td>
                            &nbsp;
                        </td>
                        <td style="text-align: right;">
                            <div style="font-size:18px;color:#f0c366;">
                                <a href="#" @click="delOrder(order.id,$event)">X</a>
                            </div>
                        </td>
                    </tr>
                </table>
                <table border="0" width="100%" @click="ordDetail(order)">
                    <tr>
                        <td colspan="3">
                            <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                {{order.created_at}}
                            </div>
                        </td>
                    </tr>
                    <tr style="border-bottom:1px solid #f0f0f0;">
                        <td>
                            <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                NO. {{order.id_show}}
                            </div>
                        </td>
                        <td colspan="2" style="text-align: right;">
                            <div style="font-size:14px;font-family:PingFangSC-Semibold;color:#4a4a4a;">
                                {{order.status}} 
                            </div>
                        </td>
                    </tr>
                     <tr>
                        <td colspan="3">
                            <div style="font-size:14px;font-family:PingFangSC-Regular;color:#f0c366;text-align:center;" v-if="isZH">
                                更多
                            </div>
                            <div style="font-size:14px;font-family:PingFangSC-Regular;color:#f0c366;text-align:center;" v-if="!isZH">
                                MORE
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <div style="display:block;background:#f8f8f8;height:5px;"></div>
                        </td>
                    </tr>
                </table>
              </li>
              <li style="border-bottom:0px;">
                  <div style="text-align:center;height:136px;display:none;" class="divData" v-if="isZH">没有更多数据</div>
                  <div style="text-align:center;height:136px;display:none;" class="divData" v-if="!isZH">No more data</div>
              </li>
            </ul>
          </div>
        </section>
      </section>
    </div>
    <div class="mask-black-dialog" id="section6" style="display:none;height:100%">   
        <div class="m-confirm" style="height:90%;overflow-y: scroll;">
            <ul class="type-buy" style="width:100%;padding:0px;">
                <li>
                    <table border="0" width="100%">
                        <tr>
                            <td rowspan="3" width="5%">
                                &nbsp;
                            </td>
                            <td colspan="2">
                                <div style="font-size:20px;font-family:PingFangSC-Semibold;color:#f0c366;">
                                    RMB {{orderTotal}}
                                </div>
                            </td>
                            <td rowspan="3" width="5%">
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                    {{orderDate}}
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                    NO. {{orderNo}}
                                </div>
                            </td>
                            <td style="text-align: right;">
                                <div style="font-size:14px;font-family:PingFangSC-Semibold;color:#4a4a4a;">
                                    {{orderStatus}} 
                                </div>
                            </td>
                        </tr>

                    </table>
                </li>
                <li v-for="(obj, index) in details">
                    <table border="0" width="100%">
                        <tr>
                            <td width="5%" rowspan="2">
                                &nbsp;
                            </td>
                            <td rowspan="2" width="90px;">
                                <img :src="obj.pic" style="width:79px;height:79px;"/>
                            </td>
                            <td>
                               <div style="font-size:18px;font-family:Avenir-Roman;color:#4a4a4a;">
                                   {{obj.title}}
                               </div>
                            </td>
                            <td width="5%" rowspan="2">
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                             <td>
                                 <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                    {{numStr}}:{{obj.num}} RMB {{obj.price}}</br><font color="red">{{obj.status}}</font>
                                </div>
                            </td>
                        </tr>
                    </table>
                </li>
            </ul></br></br>
        </div>
        <div>
            <div style="text-align: center; font-size: 16px; font-family: PingFangSC-Regular; color: rgb(240, 195, 102); position: absolute; left: 7.5%; bottom: 5%; background: #fafafa; width: 85%; height: 30px;" @click="ordClose()" v-if="isZH">
                X 关闭
            </div>
            <div style="text-align: center; font-size: 16px; font-family: PingFangSC-Regular; color: rgb(240, 195, 102); position: absolute; left: 7.5%; bottom: 5%; background: #fafafa; width: 85%; height: 30px;" @click="ordClose()" v-if="!isZH">
                X CLOSE
            </div>
        </div>
    </div>
  </div>
</template>

<style>
</style>
<script>
    import { PopupPicker, XButton } from 'vux'
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    import back from "../back";

    export default {
        data() {
            return {
                ordDataList: [],
                details: [],
                isZH:true,
                title:'订单',
                orderNo:'',
                orderDate:'',
                orderTotal:0.0,
                orderStatus:'',
                numStr:'数量',
                hUrl:'',
                hName:'',
                isProperty:false,
                properties:[]
            }
        },
        created:function () {
            //初始化酒店图片和名称
            this.hUrl = localStorage.HomeInfo.split(';')[0];
            this.hName = localStorage.HomeInfo.split(';')[1];
            //判断显示中/英文
            if(localStorage.LANGUAGE!='zh'){
                this.isZH = false;
                this.title = "Orders";
                this.numStr = "Quantity";
            }
            let _this = this;
            $(function(){
                $(".navbar-center").css('marginLeft',0);
                //获取订单信息
                let ordParams = {
                    token: localStorage.TOKEN,
                    lang:localStorage.LANGUAGE,
                    hotelid:localStorage.HOTELID
                }
                _this.$store.dispatch('viewOrder', ordParams).then(function (res) {
                    if(res.code == 0){
                        _this.ordDataList = res.data;
                        for(let i=0;i<_this.ordDataList.length;i++){
                            // 时间格式转换
                            let date = new Date(Number(_this.ordDataList[i].created_at) * 1000);
                            let Y = date.getFullYear() + '-';
                            let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                            let D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
                            let h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
                            let m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
                            let s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
                            let dateStr = Y+M+D+h+m+s;
                            _this.ordDataList[i].created_at = dateStr;
                        }
                        $(".divData").show();
                    } else {
                        _this.$dialog.toast({mes: res.msg, timeout: 3000});
                    }
                });
                //订单物业
                let params = {
                    token: localStorage.TOKEN
                }
                _this.$store.dispatch('getShoppingHotelList', params).then(function (res) {
                    if(res.code == 0){
                        for(let i=0;i<res.data.length;i++){
                            let name = res.data[i].name_lang1;
                            //判断显示中/英文
                            if(localStorage.LANGUAGE!='zh'){
                                name = res.data[i].name_lang2;
                            }
                            _this.properties.push({name:name, value:res.data[i].id});
                        }
                    }else{
                        if(res.code == 3){
                            _this.$router.replace("/loginforguest");
                            return;
                        }
                        _this.$dialog.toast({mes: res.msg, timeout: 3000});
                    }
                });
            });
        },
        methods: {
            ordClose:function(){
                $("#section6").hide();
                $("#section5").show();
            },
            ordDetail:function(order){
                $("#section5").hide();
                $("#section6").show();
                this.orderNo = order.id_show;
                this.orderDate = order.created_at;
                this.orderTotal = order.price;
                this.orderStatus = order.status;
                this.details = order.products;
            },
            delOrder:function(id,event){
                let target = event.target;
                if(target==null){
                    target = event.srcElement;
                }
                let _this = this;
                let alobj = new alertLanguage();
                let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                let title = obj.title;
                let msg1 = '是否删除?';
                let msg2 = '删除成功!';
                //判断显示中/英文
                if(localStorage.LANGUAGE!='zh'){
                    msg1 = 'Are you sure to delete the order?';
                    msg2 = 'Successfully deleted!';
                }
                let sureBnt = obj.sureBnt;
                let cancelBnt = obj.cancelBnt;
                let dialog = window.YDUI.dialog;
                dialog.confirm(title,msg1, [
                    {
                        txt: sureBnt,
                        color: false,
                        callback: function () {
                            let params = {
                                token: localStorage.TOKEN,
                                orderid:id
                            }
                            _this.$store.dispatch('deleteOrder', params).then(function (res) {
                                if(res.code==0){
                                    _this.$dialog.toast({mes: msg2, timeout: 2000});
                                    $(target).parent().parent().parent().parent().parent().remove();
                                }else{
                                    _this.$dialog.toast({mes: res.msg, timeout: 2000});
                                }
                            });
                        }
                    },
                    {
                        txt: cancelBnt,
                        color: false,
                        callback: function () {

                        }
                    }
                ]);
            },
            onPropertyChange:function (changeKey) {
                let _this = this;
                //获取订单信息
                let ordParams = {
                    token: localStorage.TOKEN,
                    lang:localStorage.LANGUAGE,
                    hotelid:changeKey[0]
                }
                this.$store.dispatch('viewOrder', ordParams).then(function (res) {
                    if(res.code == 0){
                        _this.ordDataList = res.data;
                        for(let i=0;i<_this.ordDataList.length;i++){
                            // 时间格式转换
                            let date = new Date(Number(_this.ordDataList[i].created_at) * 1000);
                            let Y = date.getFullYear() + '-';
                            let M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
                            let D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
                            let h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
                            let m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
                            let s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
                            let dateStr = Y+M+D+h+m+s;
                            _this.ordDataList[i].created_at = dateStr;
                        }
                    } else {
                        _this.$dialog.toast({mes: res.msg, timeout: 3000});
                    }
                });
            }
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components: {
            PopupPicker,
            "v-back": back
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
    };
</script>


