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
        </section>
        <section class="g-scrollview">
            <table border="0" width="100%">
              <tr align="center">
                <td>
                    <font size="4" v-if="isZH">餐饮登记</font>
                    <font size="4" v-if="!isZH">Breakfast Registration</font>
                </td>
              </tr>
              <tr>
                  <td height="256px" align="center">
                      <div id="output" style="margin-top:10%;margin-left:auto;margin-right:auto;width:256px;height:256px;"></div>
                  </td>
              </tr>
              <tr>
                  <td align="center" height="50px">
                      <font color="grey" v-if="isZH">请向餐厅服务人员出示二维码</font>
                      <font color="grey" v-if="!isZH">Please show the QR code to attendant</font>
                  </td>
              </tr>
          </table>
        </section>
      </section>
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
                title:'早餐',
                isZH:true
            }
        },
        created:function () {
            if(localStorage.LANGUAGE!='zh'){
                this.title = 'Breakfast';
                this.isZH = false;
            }
            let _this = this;
            $(function(){
                $(".navbar-center").css('marginLeft',0);
                let params = {
                    type: 'breakfast',
                    token: localStorage.TOKEN
                };
                //GetBreakFast
                _this.$store.dispatch('GetBreakFast', params).then(function (res) {
                    if(res.data.code===0){
                        jQuery(function(){
                            jQuery('#output').qrcode(res.data.data);
                        });
                    }else{
                        let dialog = window.YDUI.dialog;
                        let alobj = new alertLanguage();
                        let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                        let title = obj.title;
                        let msg = obj.breakfastMsg;
                        let sureBnt = obj.sureBnt;
                        dialog.confirm(title,msg, [
                            {
                                txt: sureBnt,
                                color: false,
                                callback: function () {
                                    _this.$router.replace("/loginforguest");
                                }
                            }
                        ]);
                    }
                });
            });
        },
        methods: {
            goBack:function(){
                this.$router.push('/home');
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


