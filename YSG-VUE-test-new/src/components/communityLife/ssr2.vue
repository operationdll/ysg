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
                  <div class="swiper-slide" :class="index ==0 ? 'active': ''" v-for="(item,index) in ssrDataList">
                      <a @click="changeTab(item.url)"><span>{{item.name}}</span></a>
                  </div>
              </div>
          </div>
      </section>

      <section>
            <scroller style="padding-top: 2.3rem;background: #fff;">
                <ul class="ssr-list">
                    <li v-for="item in ssrList" @click="openDetail(item.link, item.title)">
                        <!--<router-link :to="{path:'/ssr/ssrDetail',query:{listData:item}}">-->
                            <h4>{{item.title}}</h4>
                            <span>{{item.pubDate}} / {{item.author}}</span>
                            <p class="over-eclipse">{{item.description}}</p>
                        <!--</router-link>-->
                    </li>
                </ul>
            </scroller>
      </section>
      <yd-actionsheet :items="items" v-model="show" cancel="取消"></yd-actionsheet>
  </div>
</template>
<style>
    .ssr-list{padding: .2rem;}
    .ssr-list li{border-bottom: 1px solid #ddd;padding: .2rem 0;}
    .ssr-list li h4{font-size: .32rem;}
    .ssr-list li span{font-size: .24rem;color: #999;}
    .ssr-list li p{font-size: .28rem;color: #666;}
    .m-actionsheet, .mask-actionsheet{z-index: 10000!important;}
    .over-eclipse{display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;}
</style>

<script>
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    import foot from '../foot.vue'
	import menu from '../common/menu.vue'
    import guestCenter from '../common/menu/guestCenter.vue'
    import { PopupPicker, XButton, Scroller } from 'vux'
//    import { Scroller, Divider, Spinner, XButton, Group, Cell, LoadMore, PopupPicker } from 'vux'
	export default {
		data() {
			return {
				tabList:[],
                ssrList:[],
                newsList:[],
                financeList:[],
                sportsList:[],
                disportList:[],
				items:[],
                show:false,
                ssrShowData: {},
                ssrData:{},
                ssrDataList:[],
                ssrFlag:''
			};
		},
        created:function () {
			this.ssrFlag = this.$route.query.ssrFlag
			let _this = this
			var ssrBase = 'https://bird.ioliu.cn/v1/?url='
            this.ssrData = {
				zh:{
					'news':[
						{
							name: '腾讯国内新闻',
							url: 'http://lixin.xapi.wangtiansoft.com/newsgn/rss_newsgn.xml',
							lang:'zh'
						},
						{
							name: '人民网国际新闻',
							url: ssrBase+'http://www.people.com.cn/rss/world.xml',
							lang:'zh'
						}
                    ],
                    'finance':[
	                    {
		                    name: '腾讯财经',
		                    url: 'http://lixin.xapi.wangtiansoft.com/scroll/rss_scroll.xml',
		                    lang:'zh'
	                    },
	                    {
		                    name: '人民网经济新闻',
		                    url: ssrBase+'http://www.people.com.cn/rss/finance.xml',
		                    lang:'zh'
	                    },
                    ],
                    'disport':[
	                    {
		                    name: '腾讯娱乐电影',
		                    url: ssrBase+'http://ent.qq.com/movie/rss_movie.xml',
		                    lang:'zh'
	                    },
	                    {
		                    name: '新华网',
		                    url: ssrBase+'http://www.xinhuanet.com/ent/news_ent.xml',
		                    lang:'zh'
	                    },
                    ],
                    'sports':[
	                    {
		                    name: '腾讯体育',
		                    url: ssrBase+'http://sports.qq.com/rss_newssports.xml',
		                    lang:'zh'
	                    },
	                    {
		                    name: '新华网体育频道',
		                    url: ssrBase+'http://www.xinhuanet.com/sports/news_sports.xml',
		                    lang:'zh'
	                    }
                    ]
                },
                en:{
	                'news':[
		                {
			                name: 'China Daily News',
			                url: ssrBase+'http://www.chinadaily.com.cn/rss/cndy_rss.xml',
			                lang:'en'
		                },
		                {
			                name: 'BBC World',
			                url: ssrBase+'http://feeds.bbci.co.uk/news/world/rss.xml',
			                lang:'en'
		                },
		                {
			                name: 'CNN World',
			                url: ssrBase+'http://rss.cnn.com/rss/edition_world.rss',
			                lang:'en'
		                }
	                ],
	                'finance':[
		                {
			                name: 'Bizchina News',
			                url: ssrBase+'http://www.chinadaily.com.cn/rss/bizchina_rss.xml',
			                lang:'en'
		                },
		                {
			                name: 'Business',
			                url: ssrBase+'http://feeds.bbci.co.uk/news/business/rss.xml',
			                lang:'en'
		                },
		                {
			                name: 'MONEY',
			                url: ssrBase+'http://rss.cnn.com/rss/money_latest.rss',
			                lang:'en'
		                }
	                ],
	                'disport':[
		                {
			                name: 'China Daily Entertainment News',
			                url: ssrBase+'http://www.chinadaily.com.cn/rss/entertainment_rss.xml',
			                lang:'en'
		                },
		                {
			                name: 'Entertainment',
			                url: ssrBase+'http://rss.cnn.com/rss/edition_entertainment.rss',
			                lang:'en'
		                },
	                ],
	                'sports':[
		                {
			                name: 'Sports News',
			                url: ssrBase+'http://www.chinadaily.com.cn/rss/sports_rss.xml',
			                lang:'en'
		                },
		                {
			                name: 'World Sport',
			                url: ssrBase+'http://rss.cnn.com/rss/edition_sport.rss',
			                lang:'en'
		                }
	                ]
                }
            }
            if(localStorage.LANGUAGE == 'en'){
	            this.ssrShowData = this.ssrData.en
            } else {
	            this.ssrShowData = this.ssrData.zh
            }
            this.ssrDataList = this.ssrShowData.news
            if(localStorage.LANGUAGE == 'en'){
	            this.resolve3(this.ssrDataList[0].url)
            } else {
	            this.resolve1(this.ssrDataList[0].url)
            }



	        //弹窗数据
	        this.items = [
		        {
			        label:'新闻',
			        method: function () {
				        _this.ssrDataList = _this.ssrShowData.news
				        if(localStorage.LANGUAGE == 'en'){
					        _this.resolve3(_this.ssrDataList[0].url)
                        }else{
					        _this.resolve1(_this.ssrDataList[0].url)
                        }

			        }
		        },
		        {
			        label:'财经',
			        method: function () {
				        _this.ssrDataList = _this.ssrShowData.finance
				        if(localStorage.LANGUAGE == 'en'){
					        _this.resolve3(_this.ssrDataList[0].url)
				        }else{
					        _this.resolve1(_this.ssrDataList[0].url)
				        }
			        }
		        },
		        {
			        label:'娱乐',
			        method: function () {
				        _this.ssrDataList = _this.ssrShowData.disport
				        if(localStorage.LANGUAGE == 'en'){
					        _this.resolve3(_this.ssrDataList[0].url)
				        }else{
					        _this.resolve1(_this.ssrDataList[0].url)
				        }
			        }
		        },
		        {
			        label:'体育',
			        method: function () {
				        _this.ssrDataList = _this.ssrShowData.sports
				        if(localStorage.LANGUAGE == 'en'){
					        _this.resolve3(_this.ssrDataList[0].url)
				        }else{
					        _this.resolve1(_this.ssrDataList[0].url)
				        }
			        }
		        }

	        ]


            $(document).ready(function () {
	            var swiper = new Swiper('.ssr .swiper-container', {
		            pagination: '.swiper-pagination',
		            slidesPerView: 2.5,
		            paginationClickable: true,
		            spaceBetween: 0
	            });
	            $('.tab-change .swiper-slide').click(function () {
                    $(this).addClass('active').siblings().removeClass('active')
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
            resolve1:function (url) {
			    this.$store.dispatch('showLoading')
				let _this = this;
				//通过jq的get方法获取数据源
	            $.get(url,function (res) {
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
		            _this.ssrList = result;
                    _this.$store.dispatch('hideLoading')
	            })

            },
			resolve2:function (url) {
                this.$store.dispatch('showLoading')
				let _this = this;
				//通过jq的get方法获取数据源
				$.get(url,function (res) {
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
					_this.ssrList = result;
                    _this.$store.dispatch('hideLoading')
				})
			},
			resolve3:function (url) {
                this.$store.dispatch('showLoading')
				let _this = this;
				//通过jq的get方法获取数据源
				$.get(url,function (res) {
					var result = [];
					var resXml = $(res);
					resXml.find('item').each(function () {
						var title = $(this).find('title').text().slice(9,-3);
						var link = $(this).find('link').text();
						var author = $(this).find('author').text();
						var pubDate = $(this).find('pubDate').text();
						var description = $(this).find('description').text().slice(9,-3)+'...';
						var obj = { title: title, link:link, author:author, pubDate:pubDate, description:description}
						result.push(obj)
					})
					_this.ssrList = result;
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
            changeTab:function (url) {
	            if(localStorage.LANGUAGE == 'en'){
		            this.resolve3(url)
                }else{
		            this.resolve1(url)
                }

            }
		},
		mounted() {
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
