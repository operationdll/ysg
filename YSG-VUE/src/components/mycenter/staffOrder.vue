<template>
  <div>
    <div class="search" style="height: 100%" id="section5">
      <div class="nav_mark"></div>
      <yd-navbar :title="title" fixed>
        <span class="back" slot="left" @click="ordGoBack()"></span>
        <span style="display:block;width:50%;height:60%;margin-top:10%;margin-right:40%;" slot="right" @click="isProperty = true">
           <div style="color:#afafaf;" v-if="isZH">选择物业</div>
           <div style="color:#afafaf;" v-if="!isZH">Property</div>
           <popup-picker :data="properties" :show="isProperty" :columns="1" @on-hide="isProperty = false" @on-change="onPropertyChange"></popup-picker>
        </span>
      </yd-navbar></br></br></br>
      <section class="g-flexview" style="background:white;">
        <section class="g-scrollview">
          <div id="orderDiv" class="m-list list-theme4">
            <ul class="type-buy">
              <li v-for="(order, index) in ordDataList" style="border-bottom:0px;">
                <table border="0" width="100%" @click="ordDetail(order)">
                    <tr>
                        <td>
                            <div style="font-size:20px;font-family:PingFangSC-Semibold;color:#f0c366;">
                                RMB {{order.price}}
                            </div>
                        </td>
                        <td>
                            &nbsp;
                        </td>
                        <td style="text-align: right;">
                            <div style="font-size:18px;color:#f0c366;">
                               {{order.room_no}}
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3">
                            <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                {{order.created_at}}
                            </div>
                        </td>
                    </tr>
                    <tr>
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
                    <tr v-for="(obj, index) in order.products">
                        <td colspan="3">
                            <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                {{obj.title}}
                            </div>
                        </td>
                    </tr>
                     <tr style="border-top:1px solid #f0f0f0;">
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
            </ul>
          </div>
        </section>
      </section>
      <!--Footer page-->
      <footer class="m-tabbar tabbbar-top-line-color tabbar-fixed" style="color: rgb(240, 195, 102); background-color: rgb(255, 255, 255); font-size: 0.24rem; left: 0px;">
            <div style="width:100%;text-align:center;" id="pageDiv"></div>
      </footer>
      <!--Footer page-->
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
                            <td>
                                <div style="font-size:20px;font-family:PingFangSC-Semibold;color:#f0c366;">
                                    RMB {{orderTotal}}
                                </div>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                            <td style="text-align: right;">
                                <div style="font-size:16px;color:#f0c366;">
                                    {{roomNo}}&nbsp;&nbsp;&nbsp;
                                </div>
                            </td>
                            <td rowspan="3" width="5%">
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3">
                                <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                    {{orderDate}}
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <div style="font-size:14px;font-family:PingFangSC-Light;color:#4a4a4a;">
                                    NO. {{orderNo}}
                                </div>
                            </td>
                            <td style="text-align: right;">
                                <div style="font-size:14px;font-family:PingFangSC-Semibold;color:#4a4a4a;">
                                    {{ordStatus}} 
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
                            <td width="20%">
                                <div class="dropdownDiv" v-if="obj.productStatus < 3">
                                    <button class="bnt1" @click="showMenu(obj)" v-if="isZH">处理</button>
                                    <button class="bnt1" @click="showMenu(obj)" v-if="!isZH" style="width:70px">Process</button>
                                </div>
                                <div class="dropdownDiv" v-if="obj.productStatus > 2">
                                    <button class="bnt1" disabled style="background:rgb(216, 216, 216)" v-if="isZH">处理</button>
                                    <button class="bnt1" disabled style="background:rgb(216, 216, 216);width:70px" v-if="!isZH">Process</button>
                                </div>
                            </td>
                            <td width="5%" rowspan="2">
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                             <td colspan="2">
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
            <div class="dropdown-content">
                <div class="bntOutDiv">
                    <button class="bnt2" @click="proOrder(pid,2,'处理中')" v-if="isZH">处理中</button>
                    <button class="bnt2" @click="proOrder(pid,3,'已完成')" v-if="isZH">已完成</button>
                    <button class="bnt2" @click="proOrder(pid,4,'已取消')" v-if="isZH">已取消</button>
                    <button class="bnt2" @click="proOrder(pid,2,'Processing')" v-if="!isZH">Processing</button>
                    <button class="bnt2" @click="proOrder(pid,3,'Finished')" v-if="!isZH">Finished</button>
                    <button class="bnt2" @click="proOrder(pid,4,'Cancelled')" v-if="!isZH">Cancelled</button>
                    <button class="bnt2" @click="closePro()" v-if="isZH">关闭</button>
                    <button class="bnt2" @click="closePro()" v-if="!isZH">CLOSE</button>
                </div>
            </div>
            <div class="closeBnt" @click="ordClose()" v-if="isZH">
                X 关闭
            </div>
            <div class="closeBnt" @click="ordClose()" v-if="!isZH">
                X CLOSE
            </div>
        </div>
    </div>
  </div>
</template>

<style>
.bnt1 {
    background-color: #f0c366;
    border: none;
    color: white;
    padding: 5px 10px 5px 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 14px;
    cursor: pointer;
    border-radius: 4px;
    width: 48px;
}

.bnt2 {
    background-color: #f0c366;
    border: 1px solid white;
    color: white;
    padding: 5px 10px 5px 10px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    cursor: pointer;
    border-radius: 4px;
    width: 230px;
    height: 40px;
    margin-top: 0px;
}

.bntOutDiv{
    background:white;
    display:block;
    width:230px;
    margin:auto;
    margin-top: 60%;
}

.closeBnt{
    text-align: center; 
    font-size: 16px; 
    font-family: PingFangSC-Regular; 
    color: rgb(240, 195, 102); 
    position: absolute; 
    left: 7.5%; 
    bottom: 5%; 
    background: #fafafa; 
    width: 85%; 
    height: 30px;
}

.dropdown-content {
    position: absolute;
    display: none;
    background-color: rgba(0,0,0,.4);
    width: 85%;
    height: 90%;
    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
    z-index: 2;
    bottom: 5%;
    left: 7.5%; 
}

.dropdownDiv {
    overflow: auto;
    z-index: 2;
    display:block;
    margin-top: 0px;
}
</style>
<script>
    import { PopupPicker, XButton } from 'vux'
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
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
                orderNum:0,
                numStr:'数量',
                roomNo:'',
                ordStatus:'',
                pid:0,
                isProperty:false,
                properties:[]
            }
        },
        created:function () {
            //判断显示中/英文
            if(localStorage.LANGUAGE!='zh'){
                this.isZH = false;
                this.title = "Orders";
                this.numStr = "Quantity";
            }
            let _this = this;
            $(function(){
                $(".navbar-center").css('marginLeft',0);
                //获取员工可选择物业信息
                let staffHotels = JSON.parse(localStorage.staffHotels);
                //订单物业
                for(let i=0;i<staffHotels.length;i++){
                    let name = staffHotels[i].name_lang1;
                    //判断显示中/英文
                    if(localStorage.LANGUAGE!='zh'){
                        name = staffHotels[i].name_lang2;
                    }
                    _this.properties.push({name:name, value:staffHotels[i].id});
                }
                //获取订单信息
                let changeKey = [staffHotels[0].id];
                _this.onPropertyChange(changeKey);
            });
        },
        methods: {
            closePro:function(){
                $(".dropdown-content").hide();
            },
            ordClose:function(){
                $(".dropdown-content").hide();
                $("#section6").hide();
                $("#section5").show();
            },
            ordGoBack:function(){
                this.$router.push('/home');
            },
            ordDetail:function(order){
                $("#section5").hide();
                $("#section6").show();
                this.orderNo = order.id_show;
                this.orderDate = order.created_at;
                this.orderTotal = order.price;
                this.orderNum = order.num;
                this.details = order.products;
                this.roomNo = order.room_no;
                this.ordStatus = order.status;
            },
            showMenu:function(pid){
                this.pid = pid;
                $(".dropdown-content").show();
            },
            proOrder:function(proObj,proState,textVal){
                let pid = proObj.orders_products_id;
                $(".dropdown-content").hide();
                let alobj = new alertLanguage();
                let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                let sureBnt = obj.sureBnt;
                let cancelBnt = obj.cancelBnt;
                let dialog = window.YDUI.dialog;
                let pwdHtml = '将状态设置为';
                if(localStorage.LANGUAGE!='zh'){
                    pwdHtml = 'Set the status to';
                }
                pwdHtml = pwdHtml +textVal
                let title = obj.title;
                let _this = this;
                dialog.confirm(title,pwdHtml, [
                    {
                        txt: sureBnt,
                        color: false,
                        callback: function () {
                            let params = {
                                id: pid,
                                token: localStorage.TOKEN,
                                status: proState
                            };
                            dialog.loading.open('Loading');
                            _this.$store.dispatch('updateOrderProductById', params).then(function (res) {
                                dialog.loading.close();
                                if(res.code == 0){
                                    proObj.status = textVal;
                                    let rmsg = '处理成功';
                                    //判断显示中/英文
                                    if(localStorage.LANGUAGE!='zh'){
                                        rmsg = "Successful processing";
                                    }
                                    _this.$dialog.toast({mes: rmsg, timeout: 2000});
                                } else {
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
                    hotelid: changeKey[0],
                    lang: localStorage.LANGUAGE,
                    page: 1,
                    limit: 10,
                    token: localStorage.TOKEN
                }
                _this.$store.dispatch('getStaffOrderList', ordParams).then(function (res) {
                    if(res.code == 0){
                        _this.ordDataList = res.data.list;
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
                        if(res.data.total<2){
                            let msg = "没有更多数据";
                            //判断显示中/英文
                            if(localStorage.LANGUAGE!='zh'){
                                msg = "No more data";
                            }
                            let targetHtml=`<div style="width:100%;">
                                                <center>
                                                        `
                                                        +msg+
                                                        `
                                                </center>
                                            </div>`;
                            $('#pageDiv').html(targetHtml);
                        }
                        //是否显示分页信息
                        let pagination = new Pagination(document.getElementById('pageDiv'),{page:1,total:res.data.total,lang:localStorage.LANGUAGE,pageCallBack:function(page){
                            //分页回调函数
                            ordParams.page = page;
                            _this.$store.dispatch('getStaffOrderList', ordParams).then(function (res) {
                                if(res.code == 0){
                                    _this.ordDataList = res.data.list;
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
                        }});
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
            PopupPicker
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
    };
</script>


