<template>
  <div class="ssr">
      <div class="nav_mark"></div>
      <yd-navbar :title="language.community.ssr" fixed>
          <router-link to="/communityLife" slot="left" v-if="ssrFlag == 'commonunity'"><span class="close"></span></router-link>
          <router-link to="/home" slot="left" v-else><span class="close"></span></router-link>
          <span slot="right" @click="show = true">More</span>
      </yd-navbar>
      <section class="promotiom_index" style="margin-top: 1.3rem;padding: .1rem 0;position:relative;z-index: 9999;background: #fff;">
          <div class="swiper-container">
              <div class="swiper-wrapper tab-change">
                  <div class="swiper-slide" :class="index ==0 ? 'active': ''" v-for="(item,index) in tabList">
                      <a @click="changeTab(item.rss,item.name)"><span>{{item.name}}</span></a>
                  </div>
              </div>
          </div>
      </section>

      <section>
            <scroller style="padding-top: 2.3rem;background: #fff;">
                <ul class="ssr-list">
                    <li v-for="item in dataList" @click="openDetail(item.link, item.title)">
                        <!--<router-link :to="{path:'/ssr/ssrDetail',query:{listData:item}}">-->
                            <h4>{{item.title}}</h4>
                            <span>{{item.pubDate}} / {{item.author}}</span>
                            <p class="over-eclipse">{{item.description}}</p>
                        <!--</router-link>-->
                    </li>
                </ul>
            </scroller>
      </section>
      <yd-popup v-model="show" position="bottom" height="90%">
         <div class="s-show">
             <h4>{{language.community.chanel}} <span class="s-save" @click="setActive">{{language.community.save}}</span></h4>
             <h5 style="margin-top: 20px;">新闻-News</h5>
             <ul class="ssrList">
                 <li :key="item.id" v-for="item in ssrList" :class="item.active == 1 ? 'active':''" v-if="item.typename == '新闻'">{{item.name}} <input type="hidden" :value="item.id"></li>
             </ul>
             <h5>娱乐-Entertainment</h5>
             <ul class="ssrList">
                 <li :key="item.id" v-for="item in ssrList" :class="item.active == 1 ? 'active':''" v-if="item.typename == '娱乐'">{{item.name}} <input type="hidden" :value="item.id"></li>
             </ul>
             <h5>体育-Sports</h5>
             <ul class="ssrList">
                 <li :key="item.id" v-for="item in ssrList" :class="item.active == 1 ? 'active':''" v-if="item.typename == '体育'">{{item.name}} <input type="hidden" :value="item.id"></li>
             </ul>
             <h5>财经-Business</h5>
             <ul class="ssrList">
                 <li :key="item.id" v-for="item in ssrList" :class="item.active == 1 ? 'active':''" v-if="item.typename == '财经'">{{item.name}} <input type="hidden" :value="item.id"></li>
             </ul>
         </div>
      </yd-popup>
  </div>
</template>

<script>
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
	export default {
		data() {
			return {
                tabList:[],//展示使用
                ssrList:[],
                show: false,
                dataList:{},
                swiper:{}
			}
		},
        created:function () {

            let _this = this
			this.ssrFlag = this.$route.query.ssrFlag

            // 通过接口获取ssr 基本路径
            let params = {
                hotelid: localStorage.HOTELID,
                lang:localStorage.LANGUAGE
            }
            this.$store.dispatch('getRss', params).then(function (res) {
                _this.ssrList = res.data.list
                //初始化页面值
                console.log(_this.ssrList)
                _this.changeTab(_this.ssrList[0].rss,_this.ssrList[0].name)

                // 获取选中状态
                var activeList = localStorage.activeList.split(',')
                if(activeList){
                    // 重组列表数据
                    for (var item in activeList) {
                        for (var i in _this.ssrList) {
                            if (activeList[item] == _this.ssrList[i].id) {
                                _this.ssrList[i].active = 1
                            }
                        }
                    }
                } else {
                    for (var i in _this.ssrList) {
                        _this.ssrList[i].active = 1
                    }
                }

                for(var item in _this.ssrList) {
                    if(_this.ssrList[item].active == 1) {
                        _this.tabList.push(_this.ssrList[item])
                    }
                }
                //初始化swiper
                $(document).ready(function () {
                    _this.swiper= new Swiper('.ssr .swiper-container', {
                        pagination: '.swiper-pagination',
                        slidesPerView: 2.5,
                        paginationClickable: true,
                        spaceBetween: 0
                    });
                    // 样式切换
                    $('.ssrList').on('click','li',function () {
                        if ($(this).hasClass('active')) {
                            $(this).removeClass('active')
                        } else {
                            $(this).addClass('active')
                        }

                    })
                    $('.tab-change .swiper-slide').click(function () {
                        $(this).addClass('active').siblings().removeClass('active')
                    })
                })
            })

        },
		methods: {
			getTab:function (arr) {
				this.tabList=[];
                for(var index in arr){
                	this.tabList.push(arr[index])
                }
			},
            changeTab:function (url,name) {
                console.log(url.indexOf(".com"))
			    this.$store.dispatch('showLoading')
                var ssrBase = 'http://47.93.201.72'
                var path;
				let _this = this;
                if(url.indexOf('.com')>0){
                    if(url.indexOf('.cn')>0 && url.indexOf('.cnn')<0){
                        path = url.split('.cn')[1]
                    } else {
                        path = url.split('.com')[1]
                    }
                } else if(url.indexOf('.uk')>0) {
                    path = url.split('.uk')[1]
                }

			    var urlPath = ssrBase + path;

				//通过jq的get方法获取数据源
	            $.get(urlPath, {contentType : "application/x-www-form-urlencoded; charset=UTF-8"},function (res) {
		            var result = [];
		            var resXml = $(res);
		            resXml.find('item').each(function () {
			            var title = $(this).find('title').text();
			            var link = $(this).find('link').text();
			            var author = $(this).find('author').text();
			            var pubDate = $(this).find('pubDate').text();
			            var description = $(this).find('description').text()+'...';
			            var obj = { title: title, link:link, author:author, pubDate:pubDate, description:description}
			            result.push(obj)
		            })
		            _this.dataList = result;
		            console.log(result)
                    _this.$store.dispatch('hideLoading')
	            })

            },
			openDetail:function (url,title) {
                if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                    openFile(url)
                } else {
                    openUrl(url,title)
                }

			},
            setActive:function () {
			    let _this = this;
			    var setArr = []
                $('.ssrList li').each(function () {
                    if ($(this).hasClass('active')) {
                        setArr.push($(this).find('input').val())
                    }
                })
                this.show = !this.show
                localStorage.activeList = setArr

                // 通过接口获取ssr 基本路径
                let params = {
                    hotelid: localStorage.HOTELID,
                    lang:localStorage.LANGUAGE
                }
                this.$store.dispatch('getRss', params).then(function (res) {
                    _this.ssrList = res.data.list

                    // 获取选中状态
                    var activeList = localStorage.activeList.split(',')
                        console.log(activeList)
                    // 重组列表数据
                    for (var item in activeList) {
                        for (var i in _this.ssrList) {
                            if (activeList[item] == _this.ssrList[i].id) {
                                _this.ssrList[i].active = 1
                            }
                        }
                    }
                    console.log('bg',_this.ssrList)
                    _this.tabList = []
                    for(var item in _this.ssrList) {
                        if(_this.ssrList[item].active == 1) {
                            _this.tabList.push(_this.ssrList[item])
                        }
                    }

                    //更新swiper容器
                    $(document).ready(function () {
                        _this.swiper.updateSlidesSize();
                        _this.swiper.updateContainerSize();
                        _this.swiper.update(true);

                    })

                })
            }
		},
		mounted() {
            //一级页面falg
            isHomePage(0)
		},
        components:{
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
	};
</script>
<style>
    .ssr-list{padding: .2rem;}
    .ssr-list li{border-bottom: 1px solid #ddd;padding: .2rem 0;}
    .ssr-list li h4{font-size: .32rem;}
    .ssr-list li span{font-size: .24rem;color: #999;}
    .ssr-list li p{font-size: .28rem;color: #666;}
    .m-actionsheet, .mask-actionsheet{z-index: 10000!important;}
    .over-eclipse{display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;}
    .s-show,.s-hide{padding: .3rem;}
    .s-show>h4,.s-hide>h4{font-size: .32rem;color: #666;}
    .s-show>ul,.s-hide>ul{overflow: hidden;margin-top: 10px;}
    .s-show>ul>li.active{background: #f0c366;color: #fff;border: 1px solid #f0c366;}
    .s-show>ul>li,.s-hide>ul>li{float: left;width: 30%;height: .6rem;line-height:.6rem;border-radius: .5rem;border-radius: .5rem;border: 1px solid #eee;text-align: center;margin-right: 8px;margin-bottom: 12px;}
    .m-popup,.m-popup-mask{z-index: 9999!important;}
    .s-save{float: right;color: #f0c366;}
    .s-show h5{font-size: .28rem;}
</style>

