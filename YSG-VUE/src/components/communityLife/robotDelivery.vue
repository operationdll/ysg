<template>
  <div>
    <div class="search" style="height: 100%" id="section1">
      <div class="nav_mark"></div>
      <yd-navbar :title="title" fixed>
        <span class="close" slot="left" @click="goBack()"></span>
      </yd-navbar>
      <section class="g-flexview">
        <section class="promotiom-box">
          <h1>{{language.raiders.title}}</h1>
           <table id="dataTable">
                <tr name="wash" v-if="isDZM">
                    <td>
                        <div>
                            <img src="../../assets/images/bgImg/Laundry.jpg" width="100%" height="45%">
                        </div>
                    </td>
                </tr>
                <tr v-if="!isDZM">
                    <td>
                        <div>
                            <img src="../../assets/images/bgImg/LifeSupport.jpeg" width="100%" height="45%">
                        </div>
                    </td>
                </tr>
                <tr>
                    <td style="border-bottom:1px solid #f0f0f0;">
                        <table>
                            <tr>
                                <td v-for="(tags, index) in tagsData" height="40px" valign="bottom">
                                    <div v-if="tags.id==tagIds[1]">
                                        <div style="border-bottom:2px solid #f0c366;height:30px;float:left;color:#4a4a4a" name="tagsDiv">
                                            <a href="#" @click="changeTab(tags.id)">
                                                <div v-if="isZH" style="font-family:Avenir-Heavy;font-size:16px;">
                                                    {{tags.title_lang1}}
                                                </div>
                                                <div v-if="!isZH" style="font-family:Avenir-Heavy;font-size:16px;">
                                                    {{tags.title_lang2}}
                                                </div>
                                            </a>
                                        </div>
                                        <div style="float:left;">&nbsp;&nbsp;&nbsp;</div>
                                    </div>
                                    <div v-if="tags.id!=tagIds[1]">
                                        <div style="color:#afafaf;height:30px;float:left;" name="tagsDiv">
                                            <a href="#" @click="changeTab(tags.id)">
                                                <div v-if="isZH" style="font-family:Avenir-Heavy;font-size:16px;">
                                                    {{tags.title_lang1}}
                                                    
                                                </div>
                                                <div v-if="!isZH" style="font-family:Avenir-Heavy;font-size:16px;">
                                                    {{tags.title_lang2}}
                                                </div>
                                            </a>
                                        </div>
                                        <div style="float:left;">&nbsp;&nbsp;&nbsp;</div>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </section>
        <section class="g-scrollview">
            <ul class="type-buy" style="padding-top: 0.5rem">
              <li v-for="data in dataList" style="border-bottom:1px solid #f0f0f0;">
                <div class="col-4" style="width:2.6rem;">
                  <img  :src="data.pic" alt=""  @click="showProduct(data)" style="border:1px solid #f0f0f0;width:2.6rem;height:2.6rem;">
                </div>
                <div class="col-6" style="width:60%;padding-left:10px;">
                  <div v-if="isZH" style="font-family:Avenir-Roman;font-size:17px;color:#4a4a4a;">{{data.title_lang1}}</div>
                  <div v-if="!isZH" style="font-family:Avenir-Roman;font-size:17px;color:#4a4a4a;">{{data.title_lang2}}</div>
                  <p  style="font-family:PingFangSC-Light;font-size:14px;color:#4a4a4a;">{{data.introduct}}</p>
                  <ul class="s-price">
                    <li class="col-6" style="border:0px">
                        <div style="font-family:Avenir-Medium;font-size:17px;color:#f0c366;">RMB {{data.price}}</div>
                    </li>
                    <li class="col-4" style="border:0px">
                        <button type="button" @click="goDetail(data)" style="border:1px solid #f0c366;width: 1.3rem; height: 0.55rem;font-size:14px;color:#f0c366;font-family:Helvetica;">{{language.community.buy}}</button>
                    </li>
                  </ul>
                </div>
              </li>
            </ul>
            <p class="no_data" v-show="noData">{{language.common.noMoreData}}</p>
            </br></br>
        </section>
      </section>
    </div>
    <!--购物车页面-->
    <div class="property" id="section2" style="display: none;">
      <div class="nav_mark"></div>
      <yd-navbar title="购物车" fixed v-if="isZH">
        <span class="back" slot="left" @click="buyClose()"></span>
        <span style="display:block;width:50%;height:60%;margin-top:10%;margin-right:16%;" slot="right" @click="clearCar()">
           <div style="color:#afafaf;">清空</div>
        </span>
      </yd-navbar>
      <yd-navbar title="Shopping Cart" fixed v-if="!isZH">
        <span class="back" slot="left" @click="buyClose()"></span>
        <span style="display:block;width:50%;height:60%;margin-top:10%;margin-right:16%;" slot="right" @click="clearCar()">
           <div style="color:#afafaf;">Clear</div>
        </span>
      </yd-navbar>
      <section class="g-scrollview"></br></br></br></br>
        <ul class="type-buy" style="margin-top:-20px;" v-for="data in carList">
            <li>
                <div class="col-10" v-if="isZH" style="font-size:16px;">
                    {{data.p.title_lang1}}>
                </div>
                <div class="col-10" v-if="!isZH" style="font-size:16px;">
                    {{data.p.title_lang2}}>
                </div>
            </li>
            <li v-for="p in data.p.c">
                <div style="display:flex;">
                    <div class="col-4" style="width:2.6rem;">
                        <img :src="p.img" alt="" style="border:1px solid #f0f0f0;width:2.4rem;height:2.4rem;">
                    </div>
                    <div class="col-6" style="width:0px;flex-grow:1;word-wrap:break-word;">
                        <table width="100%">
                            <tr>
                                <td align="left" colspan="2">
                                    <h4 v-if="isZH">{{p.title_lang1}}</h4>
                                    <h4 v-if="!isZH">{{p.title_lang2}}</h4>
                                </td>
                                <td align="right">
                                    <img style="width:35%;height:20%;" src="../../assets/images/itemDelete.png" alt="" @click="delItem(data.p.id,p.id)">
                                </td>
                            </tr>
                            <tr>
                                <td colspan="3" style="height: 0.8rem;">&nbsp;</td>
                            </tr>
                            <tr>
                                <td width="20%" align="left">
                                    <h4>X{{p.num}}</h4>
                                </td>
                                <td align="right" width="50%">
                                    <div>
                                        <span class="m-spinner">
                                            <a href="#" @click="delProduct(data.p.id,p.id)"></a>
                                            <a href="#" @click="addProduct(data.p.id,p.id)"></a>
                                        </span>
                                    </div>
                                </td>
                                <td align="right" width="30%">
                                    <h4 style="color:red">￥ {{p.num*p.price}}</h4>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </li>
        </ul>
        <div style="height:88px;">&nbsp;</div>
    </section>
    <section class="buy_foot" style="bottom: 1.1rem;">
        <div class="col-5" v-if="isZH" style="font-size:16px;">
            合计 <font color="red">￥ </font><font color="red" id="ftotal">{{ftotal}}</font>
        </div>
        <div class="col-5" v-if="!isZH" style="font-size:16px;">
            Total <font color="red">￥ </font><font color="red" id="ftotal">{{ftotal}}</font>
        </div>
        <div class="col-5">
            <button type="button" @click="apply" v-if="isZH">提交</button>
            <button type="button" @click="apply" v-if="!isZH">Submit</button>
        </div>
    </section>
    </br></br></br>
    </div>
    <!--订单页面-->
    <div class="property" id="section3" style="display: none;">
      <div class="nav_mark"></div>
      <yd-navbar title="订单" fixed v-if="isZH">
        <span class="back" slot="left" @click="orderClose()"></span>
      </yd-navbar>
      <yd-navbar title="Orders" fixed v-if="!isZH">
        <span class="back" slot="left" @click="orderClose()"></span>
      </yd-navbar>
      <section class="g-scrollview"></br></br></br></br>
            <div style="margin-top:-20px;text-align:center;padding: 0px .3rem 0px .3rem;background:rgb(242, 242, 242);">
                <div style="font-size: 0.32rem;color: rgb(151, 151, 151);height:50px;background:white;line-height:50px">{{orderTitle}}</div>
            </div>
            <div class="divGap">&nbsp;</div>
            <ul class="type-buy bgClo" style="margin-top:0px;" v-for="data in carList">
                <li v-for="p in data.p.c" style="border-bottom: 12px solid rgb(242, 242, 242);">
                <div class="col-4" style="width:2.6rem;">
                    <img :src="p.img" alt="" style="border:1px solid #f0f0f0;width:2.4rem;height:2.4rem;">
                </div>
                <div class="col-6" style="width:60%;">
                    <table width="100%">
                        <tr>
                            <td align="left" colspan="3">
                                <h4 v-if="isZH">{{p.title_lang1}}</h4>
                                <h4 v-if="!isZH">{{p.title_lang2}}</h4>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="3" style="height: 0.8rem;">&nbsp;</td>
                        </tr>
                        <tr>
                            <td width="20%" align="left" valign="bottom">
                                <h4>X{{p.num}}</h4>
                            </td>
                            <td align="right" width="50%" height="52px">
                                &nbsp;
                            </td>
                            <td align="right" width="30%" valign="bottom">
                                <h4 style="color:red">￥ {{p.num*p.price}}</h4>
                            </td>
                        </tr>
                    </table>
                </div>
            </li>
        </ul>
        <div class="divGap">&nbsp;</div>
        <ul class="type-buy" style="margin-top:-20px;">
            <li style="border-bottom:0px;">
                <div class="col-10" v-if="isZH" style="font-size:16px;text-align:right;margin-right:7px;">
                    合计 <font color="red">￥ </font><font color="red" id="ftotal">{{ftotal}}</font>
                </div>
                <div class="col-10" v-if="!isZH" style="font-size:16px;text-align:right;margin-right:7px;">
                    Total <font color="red">￥ </font><font color="red" id="ftotal">{{ftotal}}</font>
                </div>
            </li>
        </ul>
        </br></br></br>
    </section>
      <section class="buy_foot" style="bottom: 1.1rem;">
        <div class="col-5">
          <button type="button" @click="orderClose" v-if="isZH" style="border-right:1px solid white;">取消</button>
          <button type="button" @click="orderClose" v-if="!isZH" style="border-right:1px solid white;">Cancel</button>
        </div>
        <div class="col-5">
          <button type="button" @click="doOrder" v-if="isZH">提交</button>
          <button type="button" @click="doOrder" v-if="!isZH">Submit</button>
        </div>
      </section>
    </div>
    <!--产品详情页面-->
    <div class="property" id="section4" style="display: none;">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.community.shopping" fixed>
            <span class="back" slot="left" @click="productClose()"></span>
        </yd-navbar>
        <section class="resolve-box" v-html="content"></section>
        <section class="buy_foot" style="bottom: 1.1rem;">
            <div class="col-5">
                &nbsp;
            </div>
            <div class="col-5">
                <button type="button" @click="goDetail(productData)" v-if="isZH">加入购物车</button>
                <button type="button" @click="goDetail(productData)" v-if="!isZH">Add to Shopping Cart</button>
            </div>
        </section>
    </div>
    <!--订单功能-->
    <div class="search" style="height: 100%;display:none;" id="section5">
      <div class="nav_mark"></div>
      <yd-navbar :title="title" fixed>
        <span class="back" slot="left" @click="ordGoBack()"></span>
        <span style="display:block;width:50%;height:60%;margin-top:10%;margin-right:40%;" slot="right" @click="isProperty = true">
           <div style="color:#afafaf;" v-if="isZH">选择物业</div>
           <div style="color:#afafaf;" v-if="!isZH">Property</div>
           <popup-picker :data="properties" :show="isProperty" :columns="1" @on-hide="isProperty = false" @on-change="onPropertyChange"></popup-picker>
        </span>
      </yd-navbar></br></br></br>
      <div style="width:100%;height:120px;">
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
                <table border="0" width="100%">
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
                        <td colspan="3" @click="ordDetail(order)">
                            <div style="font-size:14px;font-family:PingFangSC-Regular;color:#f0c366;text-align:center;" v-if="isZH">
                                更多
                            </div>
                            <div style="font-size:14px;font-family:PingFangSC-Regular;color:#f0c366;text-align:center;" v-if="!isZH">
                                MORE
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="3" style="background:#f8f8f8;height:20px;">
                            &nbsp;
                        </td>
                    </tr>
                </table>
              </li>
            </ul>
          </div>
        </section>
        </br></br>
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
                                    {{orderNum}} 
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
    <!--Footer start-->
    <footer class="m-tabbar tabbbar-top-line-color tabbar-fixed" style="color: rgb(240, 195, 102); background-color: rgb(255, 255, 255); font-size: 0.24rem; left: 0px;">
        <a href="#" class="tabbar-item" style="color:#f0c366;" @click="goShopping()">
            <span class="tabbar-icon">
                <img src="/static/images/shopping01.png">
                <span class="tabbar-badge"></span> 
            </span> 
            <span class="tabbar-txt" v-if="isZH">体验购物</span>
            <span class="tabbar-txt" v-if="!isZH">Shopping</span>
        </a>
        <a href="#" class="tabbar-item" @click="goShopCar()">
            <span class="tabbar-icon">
                <img src="/static/images/shopping04.png">
                <span class="tabbar-badge"></span> 
            </span> 
            <span class="tabbar-txt" v-if="isZH">购物车</span>
            <span class="tabbar-txt" v-if="!isZH">Shopping Cart</span>
        </a>
        <a href="#" class="tabbar-item"  @click="goOrder()">
            <span class="tabbar-icon">
                <img src="/static/images/shopping06.png">
                <span class="tabbar-badge"></span> 
            </span> 
            <span class="tabbar-txt" v-if="isZH">查看订单</span>
            <span class="tabbar-txt" v-if="!isZH">Check Order</span>
        </a>
    </footer>
    <!--Footer end-->
  </div>
</template>

<style scoped>
  .buy_foot {
    position: fixed;
    left: 0;
    bottom: 0;
    width: 100%;
    background: none;
    height: 1rem;
    line-height: 1rem;
    text-align: center;
    background: #fff;
  }
  .buy_foot button {
    width: 100%;
    height: 1rem;
    color: #fff;
    background: #f0c366;
    border: none;
    font-size: 0.3rem;
  }
  .buy_detail img {
    width: 100%;
    height: 5rem;
  }
  .buy_txt {
    padding: 0.3rem;
  }
  .buy_txt h4 {
    font-size: 0.28rem;
    color: #333;
  }
  .buy_txt p {
    font-size: 0.24rem;
    color: #666;
  }
  .m-spinner {
    margin-top: 0.2rem;
  }

ul, ul li{
    background: white;
}

.bgClo{
    background: rgb(242, 242, 242);
    padding: 0px .3rem 0px .3rem;
}

.bgClo li div{
    background: white;
}

.bgClo li{
    border: 0px;
}

.divGap{
    background: rgb(242, 242, 242);
    height: 12px;
}
</style>
<script>
    import { PopupPicker, XButton } from 'vux'
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                dataList: [],
                noData: false,
                pageFlag:'',
                preRoute:this.$route.query.info,
                spinner1: 0,
                isZH:true,
                tagIds:[],
                tagsData:[],
                carList:[],
                ftotal:0,
                content:'',
                isLoad:false,
                ordDataList: [],
                details: [],
                title:'订单',
                orderNo:'',
                orderDate:'',
                orderTotal:0.0,
                orderNum:0,
                numStr:'数量',
                shopShow: false,
                hUrl:'',
                hName:'',
                productData:null,
                isProperty:false,
                properties:[],
                orderTitle:'',
                isDZM:false,
                title:'体验购物'
            }
        },
        created:function () {
            let _this = this;
            this.pageFlag = this.$route.query.pageFlag;
            //初始化酒店图片和名称
            this.hUrl = localStorage.HomeInfo.split(';')[0];
            this.hName = localStorage.HomeInfo.split(';')[1];
            //判断显示中/英文
            if(localStorage.LANGUAGE!='zh'){
                this.isZH = false;
                this.title = "Orders";
                this.numStr = "Quantity";
                this.title = 'Shopping';
            }
            //北京雅诗阁来福士中心服务公寓 图片显示
            if(localStorage.HOTELID==1){
                this.isDZM = true;
            }else{
                if(localStorage.LANGUAGE!='zh'){
                    _this.title = 'i Life Support';
                }else{
                    _this.title = '生活支持';
                }
            }
            let alobj = new alertLanguage();
            let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
            this.orderTitle = obj.shopping.msg;
            //根据分类信息
            this.tagIds = localStorage.NEWTYPE.split(',');
            $(function(){
                $(".navbar-center").css('marginLeft',0);
                let params = {
                    hotelid: localStorage.HOTELID,
                    status: 0,
                    parentid:_this.tagIds[0]
                }
                _this.$store.dispatch('getFirstTags', params).then(function (res) {
                    _this.tagsData = res.data.data.list;
                });
                //是否显示机器人洗衣服务
                if(localStorage.WASHING_MACHINE != 1){
                    $("#dataTable tr[name='wash']").hide();
                }
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
                    }
                });
                //判断购物车显示图片
                _this.shopCarImg();
                //订单物业
                let proParams = {
                    token: localStorage.TOKEN
                }
                _this.$store.dispatch('getShoppingHotelList', proParams).then(function (res) {
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
                //设置点击按钮大小
                $(".close").attr("style","background-size:.45rem;width:.45rem;height:.45rem;");
            });
        },
        methods: {
            goOrder:function(){
                $("footer a").css("color", "#979797");
                $("footer a:eq(2)").css("color", "#f0c366");
                $("footer img:eq(0)").attr("src","/static/images/shopping02.png");
                //判断购物车显示图片
                this.shopShow = false;
                this.shopCarImg();
                $("footer img:eq(2)").attr("src","/static/images/shopping05.png");
                $("#section1").hide();
                $("#section2").hide();
                $("#section3").hide();
                $("#section4").hide();
                $("#section5").show();
                $("#section6").hide();
            },
            goShopping:function(){
                $("footer a").css("color", "#979797");
                $("footer a:eq(0)").css("color", "#f0c366");
                $("footer img:eq(0)").attr("src","/static/images/shopping01.png");
                //判断购物车显示图片
                this.shopShow = false;
                this.shopCarImg();
                $("footer img:eq(2)").attr("src","/static/images/shopping06.png");
                $("#section1").show();
                $("#section2").hide();
                $("#section3").hide();
                $("#section4").hide();
                $("#section5").hide();
                $("#section6").hide();
            },
            orderClose:function(){
                $("#section2").show();
                $("#section3").hide();
            },
            buyClose:function(){
                $("#section1").show();
                $("#section2").hide();
            },
            productClose: function() {
                $("#section4").hide();
                $("#section1").show();
            },
            changeTab:function (id) {
                let _this = this
                let params = {
                    hotelid: localStorage.HOTELID,
                    lang: localStorage.LANGUAGE,
                    limit:0,
                    tagid:id,
                    status:1
                }
                this.$store.dispatch('getShoppingList', params).then(function (res) {
                    $(".g-scrollview").scrollTop(0);
                    _this.dataList=res.data.data.list;
                    if(_this.dataList.length==0){
                        _this.noData = true;
                    }else{
                        _this.noData = false;
                    }
                    _this.isLoad = true;
                });
            },
            goDetail:function (childObj) {
                if(global.shopCar===undefined){
                    //初始化购物车
                    global.shopCar = new Map();
                }
                if(global.shopCar.has('p'+global.firstTag.id)){
                    let shopCar = global.shopCar.get('p'+global.firstTag.id);
                    if(shopCar.c.has('c'+childObj.id)){
                        shopCar.c.get('c'+childObj.id).num = shopCar.c.get('c'+childObj.id).num + 1;
                    }else{
                        let cobjs = new Map();
                        let c={c:childObj,num:1};
                        shopCar.c.set('c'+childObj.id,c);
                    }
                }else{
                    let cobjs = new Map();
                    let c={c:childObj,num:1};
                    cobjs.set('c'+childObj.id,c);
                    let obj = {p:global.firstTag,c:cobjs};
                    global.shopCar.set('p'+global.firstTag.id, obj);
                }
                let msg = '加入购物车成功';
                if(localStorage.LANGUAGE!='zh'){
                    msg = 'Add to cart successful';
                }
                this.$dialog.toast({mes: msg, timeout: 1000});
                //判断购物车显示图片
                this.shopCarImg();
            },
            showProduct:function(obj){
                let data = {};
                for(var key in this.dataList){
                    if (this.dataList[key].id == obj.id){
                        data = this.dataList[key];
                        break;
                    }
                }
                this.productData = data;
                let _this = this;
                if (data.detail) {
                    $.get(data.detail, function(res) {
                        _this.content = res;
                        _this.$store.dispatch("hideLoading");
                    });
                } else {
                    this.$store.dispatch("hideLoading");
                }
                $("#section4").show();
                $("#section1").hide();
            },
            goBack:function(){
                this.$router.push('/shopping');
            },
            apply: function() {
                let dialog = window.YDUI.dialog;
                let alobj = new alertLanguage();
                let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                let title = obj.title;
                let sureBnt = obj.sureBnt;
                let msg = '请选择商品';
                if(localStorage.LANGUAGE!='zh'){
                    msg = 'Please select product';
                }
                let _this = this;
                if(global.shopCar.size==0){
                    dialog.confirm(title,msg, [
                        {
                            txt: sureBnt,
                            color: false,
                            callback: function () {
                                _this.caculate();
                            }
                        }
                    ]);
                }else{
                    $("#section3").show();
                    $("#section2").hide();
                }
            },
            doOrder: function(){
                $(".buy_foot button").attr("disabled",true);
                $(".buy_foot button").css({background: "grey"});
                //提交订单
                let arr = "[";
                for(let i = 0;i < this.carList.length;i++){
                    for(let j = 0;j < this.carList[i].p.c.length;j++){
                        arr = arr + '{"id":' +this.carList[i].p.c[j].id +',"num":'+this.carList[i].p.c[j].num +'},';
                    }
                }
                arr = arr.substring(0,arr.length-1) +"]";
                let _this = this;
                let params = {
                    hotelid: localStorage.HOTELID,
                    userid: localStorage.userId,
                    token: localStorage.TOKEN,
                    products: arr
                };
                this.$store.dispatch("addShoppingCart", params).then(res => {
                    let mymsg = "成功";
                    //判断显示中/英文
                    if(localStorage.LANGUAGE!='zh'){
                        mymsg = "success";
                    }
                    if (res.data.code != 0) {
                        mymsg = res.data.msg;
                    }
                    $(".buy_foot button").attr("disabled",false);
                    $(".buy_foot button").css({background: "#f0c366"});
                    //弹出购物提示信息
                    let alobj = new alertLanguage();
                    let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                    let title = obj.title;
                    let sureBnt = obj.sureBnt;
                    let dialog = window.YDUI.dialog;
                    dialog.confirm(title,mymsg, [
                        {
                            txt: sureBnt,
                            color: false,
                            callback: function () {
                                if (res.data.code == 0) {
                                    global.shopCar.clear();
                                    $("#section1").show();
                                    $("#section3").hide();
                                    $("footer img:eq(1)").attr("src","/static/images/shopping03.png");
                                }
                            }
                        }
                    ]);
                });
            },
            toBuy: function(){
                $("#section3").show();
                $("#section2").hide();
            },
            goShopCar: function() {
                $("footer a").css("color", "#979797");
                $("footer a:eq(1)").css("color", "#f0c366");
                $("footer img:eq(0)").attr("src","/static/images/shopping02.png");
                //判断购物车显示图片
                this.shopShow = true;
                $("footer img:eq(2)").attr("src","/static/images/shopping06.png");
                $("#section1").hide();
                $("#section2").show();
                $("#section3").hide();
                $("#section4").hide();
                $("#section5").hide();
                $("#section6").hide();
                this.caculate();
            },
            caculate: function() {
                let total = 0;
                let arr = [];
                global.shopCar.forEach(function(value, key, map){
                    let pobj = {p:{id:value.p.id,title_lang1:value.p.title_lang1,title_lang2:value.p.title_lang2,c:[]}};
                    value.c.forEach(function(v2, k2, m2){
                        let cobj = {id:v2.c.id,title_lang1:v2.c.title_lang1,title_lang2:v2.c.title_lang2,img:v2.c.pic,num:v2.num,price:v2.c.price};
                        pobj.p.c.push(cobj);
                        total = total + cobj.num * cobj.price;
                    });
                    arr.push(pobj);
                });
                this.carList = arr;
                this.ftotal = total;
                //判断购物车显示图片
                this.shopCarImg();
            },
            clearCar: function() {
                global.shopCar.clear();
                let dialog = window.YDUI.dialog;
                let alobj = new alertLanguage();
                let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                let title = obj.title;
                let sureBnt = obj.sureBnt;
                let msg = '购物车已清空';
                if(localStorage.LANGUAGE!='zh'){
                    msg = 'Shopping cart is empty';
                }
                let _this = this;
                dialog.confirm(title,msg, [
                    {
                        txt: sureBnt,
                        color: false,
                        callback: function () {
                            _this.caculate();
                        }
                    }
                ]);
            },
            addProduct: function(pid,cid) {
                let pshopCar = global.shopCar.get('p'+pid);
                let cshopCar = pshopCar.c.get('c'+cid);
                cshopCar.num = cshopCar.num + 1;
                this.caculate();
            },
            delProduct: function(pid,cid) {
                let pshopCar = global.shopCar.get('p'+pid);
                let cshopCar = pshopCar.c.get('c'+cid);
                if(cshopCar.num==1){
                    return;
                }else{
                    cshopCar.num = cshopCar.num - 1;
                }
                this.caculate();
            },
            delItem: function(pid,cid) {
                let pshopCar = global.shopCar.get('p'+pid);
                let cshopCar = pshopCar.c.get('c'+cid);
                pshopCar.c.delete('c'+cid);
                if(pshopCar.c.size==0){
                    global.shopCar.delete('p'+pid);
                }
                this.caculate();
            },
            ordClose:function(){
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
                this.orderNum = order.status;
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
            shopCarImg:function(){
                if(this.shopShow){
                    if((global.shopCar===undefined)||(global.shopCar.size==0)){
                        $("footer img:eq(1)").attr("src","/static/images/shopping03.png");
                    }else{
                        $("footer img:eq(1)").attr("src","/static/images/shopping07.png");
                    }
                }else{
                    if((global.shopCar===undefined)||(global.shopCar.size==0)){
                        $("footer img:eq(1)").attr("src","/static/images/shopping04.png");
                    }else{
                        $("footer img:eq(1)").attr("src","/static/images/shopping08.png");
                    }
                }
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
            let _this = this
            //初始化tab标签
            $(function() {
                _this.changeTab(_this.tagIds[1]);
                //添加标签点击事件样式修改
                let fader = setInterval(function() {
                    if (_this.isLoad) {
                        $("div[name='tagsDiv']").click(function(){
                            $("div[name='tagsDiv']").css({"border-bottom": "0px","color":"#afafaf"});
                            $(this).css({"border-bottom": "2px solid #f0c366","color":"#4a4a4a"});
                        });
                        clearInterval(fader);
                    }
                }, 1000);
            });

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


