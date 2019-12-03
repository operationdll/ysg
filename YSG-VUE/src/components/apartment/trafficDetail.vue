<template>
    <div class="property">
        <div class="nav_mark"></div>
        <v-back v-bind:title="info.introduct"></v-back>
        <section class="resolve-box" v-show="showContent" v-html="content"></section>
        <ul class="pdf-video" :class="{ 'one': isOne, 'two': isTwo}" v-show="btnFlag">
            <li @click="toPDF" v-show="pdfFlag"></li>
            <li @click="toVideo" v-show="videoFlag"></li>
        </ul>
    </div>
</template>
<style>
</style>
<script type="text/babel">
import back from "../back";

export default {
  data() {
    return {
      info: {},
      pdfShow: false,
      videoShow: false,
      isOne: false,
      isTwo: false,
      pdfFlag: false,
      videoFlag: false,
      btnFlag: false,
      content: "",
      stateFlag: "",
      showContent: false
    };
  },
  created: function() {
    this.stateFlag = localStorage.stateFlag;
    this.info = this.$route.query.info;
    var ssrBase = "";
    let _this = this;
    if (this.info.detail) {
      $.get(ssrBase + this.info.detail, function(res) {
        _this.content = res;
        _this.$store.dispatch("hideLoading");
        _this.showContent = true;
        $(function() {
          $(".resolve-box img").click(function() {
            _this.toPic("https:" + $(this).attr("src"));
          });
        });
      });
    } else {
      this.$store.dispatch("hideLoading");
    }
    if (this.info.pdf && this.info.video) {
      this.btnFlag = true;
      this.pdfFlag = true;
      this.videoFlag = true;
      this.isTwo = true;
    } else if (this.info.pdf) {
      this.btnFlag = true;
      this.pdfFlag = true;
      this.isOne = true;
    } else if (this.info.video) {
      this.btnFlag = true;
      this.videoFlag = true;
      this.isOne = true;
    }

    $(function() {
      $(".navbar-center").css("marginLeft", 0);
    });
  },
  methods: {
    toPic: function(url) {
      url = url.substring(0,url.lastIndexOf("!"));
      if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        openFile(url);
      } else {
        openUrl(url, " ");
      }
    },
    toPDF: function() {
      if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        openFile(this.info.pdf);
      } else {
        openPdf(this.info.pdf);
      }
    },
    toVideo: function() {
      if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
        openFile(this.info.video);
      } else {
        openVideo(this.info.video);
      }
    }
  },
  mounted: function() {
    //一级页面falg
    isHomePage(0);
  },
  components: {
      "v-back": back
  }
};
</script>
