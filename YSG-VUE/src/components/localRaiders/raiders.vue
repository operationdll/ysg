<template>
  <div class="promotiom">
      <section class="promotiom-box">
          <h1>{{language.raiders.title}}</h1>
      </section>
      <section class="promotiom_index raiders">
          <div class="swiper-container">
              <div class="swiper-wrapper ra">
                  <div class="swiper-slide" v-for="(item, index) in typeList" :class="{active:(item.id==tagIds)}" @click="changeTab(item.id)">
                      <a><span>{{item.title}}</span></a> 
                  </div>
              </div>
          </div>
      </section>
      <section class="promotiom_list s-list2">
          <scroller ref="my_scroller">
              <ul>
                  <li v-for="item in dataList" @click="toDetail(item.id)">
                      <div :id="item.id">
                          <h4>{{item.name}}</h4>
                          <img :src='item.pic'/>
                      </div>
                      <div class="ss-location">
                          <div class="ss-left">
                              <p>{{item.address}}</p>
                          </div>
                          <div class="ss-left">
                              <i></i> {{item.distance}} km
                          </div>
                      </div>
                  </li>
              </ul>
              <p class="no_data" v-show="noData">{{language.common.noMoreData}}</p> 
          </scroller>
      </section>
      <v-foot></v-foot>
  </div>
</template>
<style>

</style>

<script>
import { mapState } from "vuex";
import { mapGetters } from "vuex";
import foot from "../foot.vue";
import { PopupPicker, XButton } from "vux";
import { getDistance } from "../../config/mUtils";
import { setTimeout } from 'timers';
export default {
  data() {
    return {
      isItemShow: false,
      typeList: [],
      dataList: [],
      nextPage: 0,
      distanceFlag: false,
      typeId: 1,
      noData: false,
      oldData:'',
      tagIds:0
    };
  },
  created: function() {
    localStorage.footFlag = 3;
  },
  mounted: function() {
    let _this = this;
    //获取tab 列表
    let params = {
      hotelid: localStorage.HOTELID,
      lang: localStorage.LANGUAGE
    };
    this.$store.dispatch("getPoiTypeList", params).then(function(res) {
      _this.typeList = res.data.data.list;
      _this.typeId = _this.typeList[0].id;
      $(document).ready(function() {
        new Swiper(".raiders .swiper-container", {
          pagination: ".swiper-pagination",
          slidesPerView: 3,
          paginationClickable: true,
          spaceBetween: 0
        });

        $(".ra").on("click", ".swiper-slide", function() {
          $(this)
            .addClass("active")
            .siblings()
            .removeClass("active");
        });

        //添加数据
        if (_this.nextPage != "-1") {
          var flag = "inifinte";
          var id = _this.typeId;
          if(localStorage.oldData){
            var objs = localStorage.oldData.split(";");
            id = objs[0];
          }
          _this.tagIds = id;
          _this.getList(id, flag);
        } else {
          done(false);
          _this.noData = true;
        }
      });
    });

    //一级页面falg
    isHomePage(1);

  },
  methods: {
    getList: function(id, flag) {
      this.oldData = id;
      let _this = this;
      _this.typeId = id;
      if (flag == "click") {
        this.dataList = [];
        _this.nextPage = 0;
        _this.noData = false;
      }
      setTimeout(function() {
        let params = {
          hotelid: localStorage.HOTELID,
          typeid: id,
          lang: localStorage.LANGUAGE,
          limit: 30,
          page: _this.nextPage,
          status:1,
        };
        _this.$store.dispatch("getPoiList", params).then(res => {
          let list = res.data.data.list;
          _this.nextPage = res.data.data.nextPage;
          //获取距离,重组数据
          var dataArr = [];
          for (var item in list) {
            let distance = parseFloat(
              getDistance(
                localStorage.lat,
                localStorage.lng,
                list[item].lat,
                list[item].lng
              )
            ).toFixed(2);
            list[item].distance = distance;
            dataArr.push(list[item]);
          }
          _this.dataList = _this.dataList.concat(dataArr);
          if(localStorage.oldData){
            var objs = localStorage.oldData.split(";");
            setTimeout(() => {
                _this.$refs.my_scroller.scrollTo(0,objs[1]);
                localStorage.oldData = '';
            }, 100);
          }
        });
      }, 300);
    },
    changeTab: function(id) {
      var flag = "click";
      this.getList(id, flag);
    },
    toDetail: function(id) {
      this.oldData = this.oldData + ';' +this.$refs.my_scroller.getPosition().top;
      let data = {};
      for (var key in this.dataList) {
        if (this.dataList[key].id == id) {
          data = this.dataList[key];
          break;
        }
      }
      localStorage.oldData = this.oldData;
      this.$router.push({ path: "/raidersDetail", query: { data: data} });
    }
  },
  components: {
    "v-foot": foot,
    PopupPicker
  },
  computed: {
    ...mapState({
      language: state => state.language.language
    })
  }
};
</script>