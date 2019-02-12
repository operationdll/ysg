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
                        <img src="../../assets/images/bgImg/Laundry.jpg" width="100%" height="45%">
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
                       机器人将到你的房间收取洗衣，请将要洗的衣物放入洗衣袋，并与洗衣单一起放入服务机器人。机器人将在3分钟内到您房间门口收取洗衣，请注意接听房间电话。
                    </div>
                    <div v-if="!isZH" style="font-size:14px;">
                       <div style="font-size:16px;font-weight:bold;">Reminder</br></br></div>
                       The  Robot “ Xiao Ya”  will come to your room to collect the laundry. Please put your laundry ,laundry list into the laundry bag  and put them into the service robot. The service Robot  will be at the door of your apartment within 3 minutes. Please pay attention to answer the in-room phone.
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
          <button type="button" @click="robotWash" v-if="isZH">提交</button>
          <button type="button" @click="robotWash" v-if="!isZH">Submit</button>
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
                title:'洗衣服务'
            }
        },
        created:function () {
             //判断显示中/英文
            if(localStorage.LANGUAGE!='zh'){
                this.isZH = false;
                this.title = 'Laundry Service';
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
            robotWash: function() {
                let params = {
                    token: localStorage.TOKEN//to: 2206
                };
                //RobotSend
                let dialog = window.YDUI.dialog;
                let alobj = new alertLanguage();
                let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                let msg = obj.washMsg;
                let sureBnt = obj.sureBnt;
                let title = obj.title;
                this.$store.dispatch('RobotSend', params).then(function (res) {
                    msg = obj.successMsg;
                    if(res.data.code!=0){
                        msg = obj.sytemBusy;
                    }
                    dialog.confirm(title,msg, [
                        {
                            txt: sureBnt,
                            color: false,
                            callback: function () {
                                
                            }
                        }
                    ]);
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


