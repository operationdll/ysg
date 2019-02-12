<template>
  <div class="invoice">
    <div class="nav_mark"></div>
    <yd-navbar :title="language.myCenter.invoices" fixed>
      <span slot="left" class="back" @click="goBack"></span>
    </yd-navbar>

    <div class="invoice-content">
      <div class="invoice-bg">
      </div>
      <div class="memoContainer">
        <p class="memo">{{language.msg.createInvoice}}</p>
        <p class="memo">{{language.msg.manageInvoice}}</p>
      </div>
      <div class="buttonInvoice" style="display: flex;">
        <button type="button" @click="showHeader" style="flex-grow: 1;border-right: 1px solid white;">{{language.myCenter.invoices_header}}</button>
        <button type="button" @click="showManager" style="flex-grow: 1;border-left: 1px solid white;">{{language.myCenter.invoices_manage}}</button>
      </div>
    </div>
  </div>
</template>
<style>
.invoice-bg {
  width: 100%;
  height: 4rem;
  margin-top: -10px;
  background: url("../../assets/images/invoice.jpg") center no-repeat;
  background-size: cover;
  position: relative;
  opacity: 0.7;
}
.buttonInvoice {
  width: 100%;
  margin: auto;
  text-align: center;
  position: fixed;
  bottom: 0;
}
.memo {
  margin: 10% 10%;
  color: grey;
  font-size: 15px;
}
.memoContainer {
  margin-top: 1rem;
}
</style>

<script>
import { mapGetters } from "vuex";
import { mapState } from "vuex";
export default {
  data() {
    return {
      tokenSrc: "",
      oid: localStorage.oid
    };
  },
  created: function() {
    $(function() {
      $(".navbar-center").css("marginLeft", 0);
    });
  },
  mounted: function() {
    //一级页面falg
    isHomePage(0);
  },
  computed: {
    ...mapState({
      language: state => state.language.language
    })
  },
  methods: {
    showHeader() {
      let _this = this;
      // 获取token
      var dataStr = JSON.stringify({ source: "YSG", otId: this.oid });
      dataStr = "dataMap=" + dataStr;
      $.ajax({
        type: "post",
        url:
          "https://bird.ioliu.cn/v1/?url=http://www.xfplink.cn/issp/ApiService?method=xforceplus.sys.qToken",
        async: false,
        data: dataStr,
        dataType: "jsonp",
        success: function(json) {
          let stNum = "";
          if (localStorage.HOTELID == 22) {
            //成都
            stNum = "ARCC";
          } else if (localStorage.HOTELID == 21) {
            //深圳
            stNum = "ARCSZ";
          } else if (localStorage.HOTELID == 7) {
            //广州国金
            stNum = "X80";
          } else if (localStorage.HOTELID == 15) {
            //合肥
            stNum = "SSLH";
          } else if (localStorage.HOTELID == 4) {
            //北京雅诗阁服务公寓
            stNum = "AB";
          }
          _this.tokenSrc =
            "http://www.xfplink.cn/xforce/#/invoiceTitle/upload?ut=" +
            json.token + "&br=YSG&st=" + stNum +
            "&roomId="+encodeURIComponent(localStorage.ROOM_INFO)+
            "&userName="+encodeURIComponent(localStorage.FULLNAME);
          //获取发票抬头
          if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
            openFile(_this.tokenSrc);
          } else {
            openUrl(_this.tokenSrc, "Invoices");
          }
        },
        error: function() {}
      });
    },
    showManager() {
      let _this = this;
      // 获取token
      var dataStr = JSON.stringify({ source: "YSG", otId: this.oid });
      dataStr = "dataMap=" + dataStr;
      $.ajax({
        type: "post",
        url:
          "https://bird.ioliu.cn/v1/?url=http://www.xfplink.cn/issp/ApiService?method=xforceplus.sys.qToken",
        async: false,
        data: dataStr,
        dataType: "jsonp",
        success: function(json) {
          _this.tokenSrc =
            "http://www.xfplink.cn/wx_invoiceCollector.html?u=" +
            json.token +
            "&m=YSG&b=" +
            localStorage.INVOICEID;
          //获取发票抬头
          if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
            openFile(_this.tokenSrc);
          } else {
            openUrl(_this.tokenSrc, "Invoices");
          }
        },
        error: function() {}
      });
    },
    goBack() {
      this.$router.go(-1);
    }
  }
};
</script>
