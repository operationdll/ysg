<template>
  <div class="promotiom">
      <section class="promotiom-box">
          <h1>{{language.raiders.title}}</h1>
      </section>
      <section class="promotiom_index raiders">
          <div class="swiper-container">
              <div class="swiper-wrapper ra">
                  <div class="swiper-slide" v-for="(item, index) in typeList" :class="{active:(index == 0)}" @click="changeTab(item.id)">
                      <a><span>{{item.title}}</span></a>
                  </div>
              </div>
          </div>
      </section>
      <section class="promotiom_list s-list2">
          <scroller :on-infinite="infinite">
              <ul>
                  <li v-for="item in dataList" @click="toDetail(item.id)">
                      <div>
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
    import { mapState } from 'vuex'
    import { mapGetters } from 'vuex'
    import foot from '../foot.vue'
    import { PopupPicker, XButton } from 'vux'
    import { getDistance } from '../../config/mUtils'
	export default {
		data() {
			return {
                isItemShow: false,
                typeList: [],
                dataList: [],
                nextPage: 0,
				distanceFlag: false,
                typeId: 1,
                noData: false
			};
		},
        created:function () {
            localStorage.footFlag = 3;
        },
        mounted:function () {
            let _this = this
            //获取tab 列表
            let params = {
                hotelid: localStorage.HOTELID,
                lang: localStorage.LANGUAGE
            }
            this.$store.dispatch('getPoiTypeList', params).then(function (res) {
                _this.typeList = res.data.data.list
                $(document).ready(function () {
                    new Swiper('.raiders .swiper-container', {
                        pagination: '.swiper-pagination',
                        slidesPerView: 3,
                        paginationClickable: true,
                        spaceBetween: 0
                    });

                    $('.ra').on('click','.swiper-slide',function () {
                        $(this).addClass('active').siblings().removeClass('active')
                    })
                })
            })
            //一级页面falg
            isHomePage(1)
        },
		methods: {
		    getList:function (id, flag) {
		        let _this = this
                _this.typeId = id
                if(flag == 'click'){
                    this.dataList=[]
                    _this.nextPage = 0
                    _this.noData = false;
                }
                setTimeout(function () {
                    let params = {
                        hotelid: 1,
                        typeid: id,
                        lang: localStorage.LANGUAGE,
                        limit:5,
                        page:_this.nextPage
                    }
                    _this.$store.dispatch('getPoiList', params).then((res) => {
                        let list =  res.data.data.list
                        _this.nextPage = res.data.data.nextPage
                        //获取距离,重组数据
                        var dataArr=[]
                        for (var item in list){
                            let distance = parseFloat(getDistance(localStorage.lng, localStorage.lat, list[item].lat, list[item].lng)).toFixed(2);
                            list[item].distance = distance
                            dataArr.push(list[item])
                        }
                        _this.dataList = _this.dataList.concat(dataArr)

                    })
                }, 300)
            },
            infinite:function (done) {
                let _this = this
                done(true);
                //添加数据
                if(this.nextPage != '-1'){
                    var flag = 'inifinte'
                    var id =this.typeId
                    this.getList(id, flag)
                }else{
                    done(false);
                    this.noData = true;
                }
            },
            changeTab:function (id) {
		        var flag = 'click'
                this.getList(id,flag)
            },
            toDetail:function (id) {
                let data = {};
                for(var key in this.dataList){
                    if (this.dataList[key].id == id){
                        data = this.dataList[key];
                        break;
                    }
                }
                this.$router.push({path:'/raidersDetail',query:{data:data}});
            }
		},
        components:{
			'v-foot':foot,
            PopupPicker,
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
	};
</script>
