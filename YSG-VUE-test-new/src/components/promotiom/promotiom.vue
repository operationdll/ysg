<template>
  <div class="promotiom">
      <section class="promotiom-box">
          <h1>{{language.promotion.title}} </h1>
      </section>
      <section class="promotiom_index">
          <div class="swiper-container">
              <div class="swiper-wrapper">
                  <div class="swiper-slide active" @click="getPromotionList">
                      <a><span>{{language.promotion.porpertyProper}}</span></a>
                  </div>
                  <div  class="swiper-slide" @click="getNewsList">
                      <a><span>{{language.promotion.porpertyNews}}</span></a>
                  </div>
                  <div  class="swiper-slide" @click="getGroupNewsList">
                      <a><span>{{language.promotion.grounpAc}}</span></a>
                  </div>
                  <div class="swiper-slide" @click="getGroupNoticeList">
                      <a><span>{{language.promotion.grounpNews}}</span></a>
                  </div>
              </div>
          </div>
      </section>
      <section class="promotiom_list s-list">
          <scroller
                  :on-infinite="infinite"
                  >
              <ul>
                  <li v-for="data in dataList" @click="goDetail(data.id)">
                      <div class="s-left">
                          <div>
                              <h4>{{data.title}}</h4>
                              <!--<p>这是副标题</p>-->
                          </div>
                          <span>{{new Date(data.updatetime*1000).getFullYear()+'-'+(new Date(data.updatetime*1000).getMonth() + 1)+'-'+new Date(data.updatetime*1000).getDate()}}</span>
                      </div>
                      <div class="s-right">
                          <img :src='data.pic'/>
                          <div class="s-tag" v-show="data.tagName"><span>{{data.tagName}}</span></div>
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
	import { mapGetters } from 'vuex'
    import foot from '../foot.vue'
	import menu from '../common/menu.vue'
    import guestCenter from '../common/menu/guestCenter.vue'
    import { Divider, Spinner, XButton, Group, Cell, LoadMore, PopupPicker } from 'vux'
    import { mapState } from 'vuex'
	export default {
		data() {
			return {
				dataList:[],
				tagList:[],
                nextPage:'1',
				noData:false,
				menuFlag:1,
				isItemShow:false
			}
		},
        created:function () {
			//激活菜单按钮
            localStorage.footFlag = 2
            //服务按钮组
            let _this = this;
			$(document).ready(function () {
				var swiper = new Swiper('.promotiom .swiper-container', {
					pagination: '.swiper-pagination',
					slidesPerView: 2.8,
					paginationClickable: true,
					spaceBetween: 0
				});
				//点击效果
				$('.promotiom_index .swiper-wrapper>div').click(function () {
                    $(this).addClass('active').siblings().removeClass('active');
				})

			})
        },
        mounted:function () {
            //一级页面falg
            isHomePage(1)
        },
		methods: {
			infinite(done){
			    let _this = this
				done(true);
				//添加数据
                if(this.nextPage != '-1'){
                    if(this.menuFlag == 1){
                        this.getPromotionList('inifinte')
                    }else if(this.menuFlag == 2){
                        this.getNewsList('inifinte')
                    }else if(this.menuFlag == 3){
                        this.getGroupNewsList('inifinte')
                    }else if(this.menuFlag == 4){
	                    this.getGroupNoticeList('inifinte')
                    }

                }else{
                    done(false);
                    this.noData = true;
                }
            },
            getPromotionList:function (flag) {
                this.menuFlag = 1
                let _this =this
                if(flag != 'inifinte'){
                    this.dataList=[]
                }
                setTimeout(function () {
                    let params = {
                        hotelid: localStorage.HOTELID,
                        lang: localStorage.LANGUAGE,
                        tagid:'',
                        page:_this.nextPage,
                        limit:5
                    }
                    _this.$store.dispatch('getPromotionList', params).then((res) => {
                        _this.dataList = _this.dataList.concat(res.data.data.list)
                        _this.nextPage = res.data.data.nextPage
                        _this.getFlag(_this.dataList)
                    })
                },300)
            },
            getNewsList:function (flag) {
                this.menuFlag = 2
			    let _this =this
                if(flag){
                    this.dataList=[]
                }
			    setTimeout(function () {
                    let params = {
                        hotelid: localStorage.HOTELID,
                        lang: localStorage.LANGUAGE,
                        tagid:'',
                        page:_this.nextPage,
                        limit:5
                    };
                    _this.$store.dispatch('getNewsList', params).then((res) => {
                        _this.dataList = _this.dataList.concat(res.data.data.list)
                        _this.nextPage = res.data.data.nextPage
                        _this.getFlag(_this.dataList)
                    })
                },300)

            },
            getGroupNewsList:function (flag) {
                this.menuFlag = 3
                let _this =this
                if(flag){
                    this.dataList=[]
                }
                setTimeout(function () {
                    let params = {
                        groupid: localStorage.groupid,
                        tagid:'',
                        page:_this.nextPage,
                        limit:5
                    };
                    _this.$store.dispatch('getGroupNewsList', params).then((res) => {
                        _this.dataList = _this.dataList.concat(res.data.data.list)
                        _this.nextPage = res.data.data.nextPage
                        _this.getFlag(_this.dataList)
                    })
                },300)
            },
            getGroupNoticeList:function (flag) {
                this.menuFlag = 4
                let _this =this
                if(flag){
                    this.dataList=[]
                }
                setTimeout(function () {
                    let params = {
                        groupid: localStorage.groupid,
                        tagid:'',
                        page:_this.nextPage,
                        limit:5
                    };
                    _this.$store.dispatch('getGroupNoticeList', params).then((res) => {
                        _this.dataList = _this.dataList.concat(res.data.data.list)
                        _this.nextPage = res.data.data.nextPage
                        _this.getFlag(_this.dataList)
                    })
                },300)
            },
            changeMenuFlag(flag) {
				//初始化当前列表数据
				this.dataList=[];
	            this.nextPage = '1';
                this.menuFlag = flag;
            },
			goDetail(id) {
				let data = {};
				for(var key in this.dataList){
					if (this.dataList[key].id == id){
						data = this.dataList[key];
						break;
					}
				}
				this.$router.push({path:'/promotionDetail',query:{info:data}});
			},
            getFlag:function (dataList) {
	            this.tagList= [];
	            //组织标签筛选数据
	            for(var item in dataList){
	            	if(this.dataList[item].tagName !=''){
			            this.tagList.push(this.dataList[item].tagName)
                    }
	            }
            }
		},
        components:{
			'v-foot':foot,
            'v-menu':menu,
            'v-guestCenter':guestCenter,
            Divider,
            Spinner,
            PopupPicker,
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
	};
</script>
