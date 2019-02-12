<template>
  <div>
    <div class="search" style="height: 100%" id="section1">
      <div class="nav_mark"></div>
      <yd-navbar :title="language.community.shopping" fixed>
        <span class="close" slot="left" @click="goBack()"></span>
      </yd-navbar>
      <section class="g-flexview">
        <section class="promotiom-box">
          <h1>{{language.raiders.title}}</h1>
        </section>
        <header class="m-navbar">
          <section class="promotiom_index raiders" style="width: 100%">
            <div class="swiper-container">
              <div class="swiper-wrapper ra">
                <div class="swiper-slide" v-for="(item, index) in tagList" @click="changeTab(item.id)" :class="{active:(index == 0)}">
                  <a><span>{{item.title}}</span></a>
                </div>
              </div>
            </div>
          </section>
        </header>
        <section class="g-scrollview">
          <div id="J_ListContent" class="m-list list-theme4">
            <ul class="type-buy" style="padding-top: 0.5rem">
              <li v-for="data in dataList" @click="goDetail(data.id)">
                <div class="col-4">
                  <img  :src="data.pic" alt="">
                </div>
                <div class="col-6">
                  <h4>{{data.title}}</h4>
                  <p>{{data.introduct}}</p>
                  <ul class="s-price">
                    <li class="col-5" style="border:0px">RMB {{data.price}}</li>
                    <li class="col-5" style="border:0px"><button type="button">{{language.community.buy}}</button></li>
                  </ul>
                </div>
              </li>
            </ul>
            <p class="no_data" v-show="noData">{{language.common.noMoreData}}</p>
          </div>
        </section>
      </section>
    </div>
    <!--购物页面-->
    <div class="property" id="section2" style="display: none;">
      <div class="nav_mark"></div>
      <yd-navbar :title="info.title" fixed>
        <span class="back" slot="left" @click="buyClose()"></span>
      </yd-navbar>
      <section class="resolve-box" v-html="content"></section>
      <ul class="pdf-video" :class="{ 'one': isOne, 'two': isTwo}" v-show="btnFlag">
        <li @click="toPDF" v-show="pdfFlag"></li>
        <li @click="toVideo" v-show="videoFlag"></li>
      </ul>
      <section class="buy_foot" style="margin-top: 1rem;">
        <div class="col-5">
          <div><yd-spinner max="75" unit="1" v-model="spinner1"></yd-spinner></div>
        </div>
        <div class="col-5">
          <button type="button" @click="apply">{{language.community.buy}}</button>
        </div>
      </section>
    </div>

  </div>
</template>

<style>
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
</style>
<script>
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                tagList:[],
                dataList: [],
                noData: false,
                pageFlag:'',
                preRoute:this.$route.query.info,
                mySwiper:{},
                info: {},
                num: "",
                spinner1: 0,
                pdfShow: false,
                videoShow: false,
                isOne: false,
                isTwo: false,
                pdfFlag: false,
                videoFlag: false,
                btnFlag: false,
                content: ""
            }
        },
        created:function () {
            this.pageFlag = this.$route.query.pageFlag;
            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        },
        methods: {
            buyClose:function(){
                $("#section1").show();
                $("#section2").hide();
            },
            buyCreate: function() {
                $("#section1").hide();
                $("#section2").show();
                this.$store.dispatch("showLoading");
                var ssrBase = "";
                let _this = this;
                if (this.info.detail) {
                    $.get(ssrBase + this.info.detail, function(res) {
                        _this.content = res;
                        _this.$store.dispatch("hideLoading");
                    });
                } else {
                    this.$store.dispatch("hideLoading");
                }
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
                });
            },
            goDetail:function (id) {
                let data = {};
                for(var key in this.dataList){
                    if (this.dataList[key].id == id){
                        data = this.dataList[key];
                        break;
                    }
                }
                this.info = data;
                this.buyCreate();

            },
            goBack:function(){
                if(this.preRoute){
                    this.$router.push('/'+this.preRoute);
                }else{
                    this.$router.push('/home');
                }
            },
            apply: function() {
                let alobj = new alertLanguage();
                let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                let title = obj.title;
                let msg = obj.shopping.msg;
                let sureBnt = obj.sureBnt;
                let cancelBnt = obj.cancelBnt;

                let _this = this;
                let dialog = window.YDUI.dialog;
                dialog.confirm(title,msg, [
                    {
                        txt: sureBnt,
                        color: false,
                        callback: function () {
                            _this.toBuy();
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
            toBuy: function(){
                $(".buy_foot button").attr("disabled",true);
                $(".buy_foot button").css({background: "grey"});
                let _this = this;
                let params = {
                    hotelid: localStorage.HOTELID,
                    shoppingid: this.info.id,
                    token: localStorage.TOKEN,
                    count: this.spinner1
                };
                this.$store.dispatch("getShoppingOrder", params).then(res => {
                    let mymsg = "";
                    if (res.data.code == 0) {
                        mymsg = this.language.msg.buy_info;
                    } else {
                        mymsg = res.data.msg;
                    }
                    $(".buy_foot button").attr("disabled",false);
                    $(".buy_foot button").css({background: "#f0c366"});
                    //弹出购物提示信息
                    let alobj = new alertLanguage();
                    let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                    let title = obj.title;
                    let msg = obj.shopping.msg;
                    let sureBnt = obj.sureBnt;

                    let _this = this;
                    let dialog = window.YDUI.dialog;
                    dialog.confirm(title,mymsg, [
                        {
                            txt: sureBnt,
                            color: false,
                            callback: function () {

                            }
                        }
                    ]);

                });
            },
            toPDF: function() {
                if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                    openFile(this.detail.pdf);
                } else {
                    openPdf(this.detail.pdf);
                }
            },
            toVideo: function() {
                if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                    openFile(this.detail.video);
                } else {
                    openVideo(this.detail.video);
                }
            }
        },
        mounted:function () {
            let _this = this
            let params = {
                hotelid: localStorage.HOTELID,
                lang: localStorage.LANGUAGE,
                limit:0
            }
            //console.log("=======HotileId:"+localStorage.HOTELID);//21,7
            if(localStorage.HOTELID==21||localStorage.HOTELID==7){
                _this.$router.push({
                    path: "/shoptype",
                    query: { pageFlag: "home" }
                });
            }
            this.$store.dispatch('getShoppingTagList', params).then(function (res) {
                _this.tagList = res.data.data.list;
                //初始化tab标签
                $(function() {
                    //初始化tab选择项
                    _this.mySwiper = new Swiper(".raiders .swiper-container", {
                        pagination: ".swiper-pagination",
                        slidesPerView: 3,
                        paginationClickable: true,
                        spaceBetween: 0,
                        initialSlide: 0
                    });
                    //添加标签点击样式
                    $(".ra").on("click", ".swiper-slide", function () {
                        $(this)
                            .addClass("active")
                            .siblings()
                            .removeClass("active");
                    });
                    //tab的click事件触发选择的初始化内容
                    _this.changeTab(_this.tagList[0].id);
                });
            });

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


