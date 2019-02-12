<template>
  <div>
    <div class="search" style="height: 100%">
      <div class="nav_mark"></div>
      <yd-navbar :title="title" fixed>
        <span class="close" slot="left"></span>
      </yd-navbar>
      <section class="g-flexview">
        <section class="promotiom-box">
          <h1>{{language.raiders.title}}</h1>
        </section>
        <section class="g-scrollview">
          <table id="dataTable">
              <tr name="wash">
                <td width="5%">&nbsp;</td>
                <td>
                    <div>
                        <img src="../../assets/images/bgImg/MiniMart.jpg" width="100%" height="45%">
                    </div>
                </td>
                <td width="5%">&nbsp;</td>
              </tr>
              <tr>
                <td colspan="3" style="height:30px;">
                    <div style="display: block; background: rgb(248, 248, 248); height: 5px;"></div>
                </td>
              </tr>
              <tr>
                <td width="5%">&nbsp;</td>
                <td>
                    <div v-if="isZH" style="font-size:14px;">
                       <div style="font-size:16px;font-weight:bold;">提示</br></br></div>
                       此购物网站名称为“机器人服务生”，由北京云迹科技有限公司提供。</br></br>
                       此购物网站仅限微信支付，所有购买的物品，均会由机器人服务生送到您的房间，请注意接听房间电话。</br></br>
                       如有购物问题，请联系 15712856521
                    </div>
                    <div v-if="!isZH" style="font-size:14px;">
                       <div style="font-size:16px;font-weight:bold;">Reminder</br></br></div>
                       This shopping site is called "Robot Mall" and it is provided by Beijing Yunji Technolgy Co,.Ltd.</br></br>
                       This shopping website services  is for WeChat only. All items purchased will be sent to your room by a service  robot.  Please pay attention to answer the in-room phone.</br></br>
                       If there are shopping problems, please contact 15712856521
                    </div>
                </td>
                <td width="5%">&nbsp;</td>
              </tr>
          </table>
        </section>
      </section>
    </div>
    <section class="buy_foot">
        <div class="col-5">
          <button type="button" @click="goBack" v-if="isZH" style="border-right:1px solid white;">取消</button>
          <button type="button" @click="goBack" v-if="!isZH" style="border-right:1px solid white;">Cancel</button>
        </div>
        <div class="col-5">
          <button type="button" @click="lifeShop" v-if="isZH">确定</button>
          <button type="button" @click="lifeShop" v-if="!isZH">Sure</button>
        </div>
      </section>
  </div>
</template>

<style scoped>
  .mycss:after {
     border-bottom: 0px;
  }
</style>

<script>
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                isZH:true,
                title:'雅诗阁货廊'
            }
        },
        created:function () {
             //判断显示中/英文
            if(localStorage.LANGUAGE!='zh'){
                this.isZH = false;
                this.title = 'Mini-Mart';
            }
            let _this = this;
            $(function(){
                $(".navbar-center").css('marginLeft',0);
                //设置点击按钮大小
                $(".close").attr("style","background-size:.45rem;width:.45rem;height:.45rem;");
                $(".navbar-item").click(function(){
                    _this.goBack();
                });
                $(".m-navbar").addClass("mycss");
            });
        },
        methods: {
            goBack:function () {
                this.$router.push('/home');
            },
            lifeShop: function() {
                //调用购物柜微信小程序
                let params = {
                    token: localStorage.TOKEN
                };
                this.$store.dispatch("getShoppingBoxDetail", params).then(res => {
                    if (res.code == 0) {
                        launchMiniProgramme(res.data);
                    }else{
                        alert(res.msg);
                    }
                });
            }
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components: {
            
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
    };
</script>


