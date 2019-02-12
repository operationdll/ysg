<template>
    <div class="appartment_style app-style">
        <div class="nav_mark"></div>
        <yd-navbar :title="appartmentShow.title" fixed>
            <router-link to="/home" slot="left" v-if="stateFlag == '1'">
                <span class="back"></span>
            </router-link>
            <router-link :to="{path:'/s_home',query:{hotelid:hotelid}}" slot="left" v-if="stateFlag == '2'">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <scroller>
            <section class="appartment_style_tab" style="margin-top: 1.3rem;">
                <div class="swiper-container">
                    <div class="swiper-wrapper">
                        <div class="swiper-slide" v-for="(item,index) in hotelDetail.roomTypeList" :class='{active: (index == tabIndex)}'>
                            <span @click="changeShow(index)">{{item.title}}</span>
                            <i class="tab_bottom_line"></i>
                        </div>
                    </div>
                </div>
            </section>

            <section>
                <div class="app_content2">
                    <img :src="appartmentShow.pic">
                    <h5>{{appartmentShow.title}}</h5>
                    <ul class="apart_style">
                        <li class="col-5"><i></i>{{language.apartment.maxPerson}} {{appartmentShow.personcount}} {{language.apartment.person}}(s)</li>
                        <li class="col-5"><i></i>{{appartmentShow.bedtype}}</li>
                    </ul>
                    <ul class="apart_util">
                        <li>
                            <div class="col-5">{{language.apartment.fcount}}:</div>
                            <div class="col-5">{{appartmentShow.roomcount}}</div>
                        </li>
                        <li>
                            <div class="col-5">{{language.apartment.size}}:</div>
                            <div class="col-5">{{appartmentShow.size}}</div>
                        </li>
                    </ul>

                </div>
            </section>

            <section v-if="appartmentShow.panoramic">
                <yd-cell-group>
                    <yd-cell-item arrow @click.native="goPhoto">
                            <span slot="left" >720°{{language.home.virtual}}</span>
                    </yd-cell-item>
                </yd-cell-group>
            </section>
            <section class="roomTypeList">
                <ul>
                    <li v-for="item in wpList">
                        <router-link :to="{path:'/home/article',query:{detail:item.detail,pdf:item.pdf,video:item.video,index:tabIndex}}">
                        <div class="s-pic">
                            <img :src=item.pic>
                        </div>
                        <div class="s-txt">
                            <h4>{{item.name}}</h4>
                        </div>
                        </router-link>
                    </li>
                </ul>
            </section>
        </scroller>

        <section class="foot_btn">
            <ul class="apart_btn" style="margin-top: 0;">
                <li @click="openBooking(hotelDetail.bookurl)">{{language.apartment.book}}</li>
                <!--<li class="col-4">RESERVE</li>-->
            </ul>
        </section>

    </div>
</template>
<style>
    .app_content2{padding: .3rem;border-bottom: 1px solid #ddd;}
    .app_content2 h4,.fire h4,.how h4{font-size: .32rem;font-weight: normal;padding: .4rem 0;}
    .app_content2 img{width: 100%;height: 4.36rem;}
    .app_content2 h5{font-size: .3rem;color: #464646;padding: .2rem 0;}
    .foot_btn{position: fixed;left:0;bottom: 0;width: 100%;height: .74rem;}
    .appartment_style_tab{border-bottom: 1px solid #eee;padding: 0 .3rem;}
    .appartment_style_tab .swiper-slide{height: .8rem;line-height: .8rem;font-size: .28rem;color: #666;}
    .roomTypeList{padding: 0 .3rem;padding-bottom: 2rem;}
    .roomTypeList>ul>li{overflow: hidden;border-bottom: 1px solid #eee;padding: .25rem 0;}
    .s-pic{float: left;width: 38%;}
    .s-pic img{width: 2.39rem;height: 2.39rem;border:1px solid #eee;}
    .s-txt{float: left;width: 62%;font-size: .32rem;}
    .app-style ._v-content{background: #fff;}
    .appartment_style_tab {text-align: center;}
    .appartment_style_tab .swiper-slide{position: relative;}
    .appartment_style_tab .active{color: #f0c366;}
    .appartment_style_tab .active .tab_bottom_line{position: absolute;width: 80%;height: 2px;bottom:0px;left:50%;margin-left: -40%;background: #f0c366;}
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				appartmentShow:{},
                tabIndex:'',
                wpList:[],
                bookUrl:'',
                stateFlag:''
			};
		},
        created:function () {
            this.stateFlag = localStorage.stateFlag
	        let _this =this;
	        //进入页面时状态
	        this.tabIndex = this.$route.query.index
	        this.bookUrl = this.$route.query.bookUrl

	        this.appartmentShow = this.hotelDetail.roomTypeList[this.tabIndex]
	        //加载房间物品列表
	        let params = {
		        hotelid: localStorage.HOTELID,
		        roomtypeid: this.appartmentShow.id,
		        lang: localStorage.LANGUAGE

	        }
	        this.$store.dispatch('getRoomResList',params).then(function (res) {
		        _this.wpList = res.data.list
	        })
        },
		methods: {
            changeShow:function (index) {
            	let _this = this;
	            this.appartmentShow = this.hotelDetail.roomTypeList[index];

	            //更新tabindex
                this.tabIndex = index;

	            //获取相应房间物品列表
	            let params = {
		            hotelid: localStorage.HOTELID,
		            roomtypeid: this.appartmentShow.id,
		            lang: localStorage.LANGUAGE

	            }
	            this.$store.dispatch('getRoomResList',params).then(function (res) {
		            _this.wpList = res.data.list
	            })

            },
			goPhoto:function () {
                this.$router.push({ path: '/photo', query: { panoramic: this.appartmentShow.panoramic,index:this.tabIndex }})
			},
            openBooking:function (url) {
                openBooking(url)
            }

		},
		mounted() {
			$(document).ready(function () {
				var swiper = new Swiper('.appartment_style_tab .swiper-container', {
					pagination: '.swiper-pagination',
					slidesPerView: 3,
					paginationClickable: true,
					spaceBetween: 0
				});
				//初始化绑定

                //点击切换状态
				$('.appartment_style_tab .swiper-slide').click(function () {
                    $(this).addClass('active').siblings().removeClass('active');
				})
			})
            //一级页面falg
            isHomePage(0)
		},
        activated:function () {

        },
        components:{

        },
		computed: {
			...mapState({
                language: state => state.language.language,
				hotelDetail: state => state.home.hotelDetail,
				home: state => state.home.home,
			})
		},
	};
</script>
