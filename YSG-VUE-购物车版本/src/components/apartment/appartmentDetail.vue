<template>
    <div class="appartment">
        <yd-navbar title=" " fixed>
            <router-link to="/home" slot="left" v-if="pageType == 'home'">
                <span class="back"></span>
            </router-link>
            <router-link to="/s_home" slot="left" v-else-if="pageType == 's_home'">
                <span class="back"></span>
            </router-link>
            <router-link to="/s_home" slot="left" v-else="pageType == 's_home'">
                <span class="back"></span>
            </router-link>

            <router-link to="#" slot="right">
                <span class="export"></span>
            </router-link>
        </yd-navbar>
        <scroller>
            <section class="appartment_top" :style="{backgroundImage: 'url(' + hotelDetail.pic[0] + ')'}"></section>
            <section class="app_content" style="background: #fff;">
                <h4>{{ hotelDetail.name }}</h4>
                <div class="map_content" @click="goLocation">
                    <img :src="hotelDetail.localpic">
                </div>
                <ul class="add">
                    <li class="col-7">
                        <p>{{home.address}}</p>
                    </li>
                    <li class="col-3"> </li>
                </ul>
                <section class="apart_profile">
                    <h4>APARTMENT LISTING</h4>
                    <P>{{hotelDetail.introduction}}</P>
                    <!--<a href="#">more</a>-->
                </section>
                <section class="equipment">
                    <div class="swiper-container">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide" v-for="item in facList">
                                <img :src="item.imgSrc">
                            </div>
                        </div>
                    </div>
                </section>
                <section class="virtual">
                    <h4><i></i> VIRTUAL TOUR</h4>
                    <div class="swiper-container">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide" v-for="(item,index) in hotelDetail.panoramicList">
                                <router-link :to="{path:'/home/virtual', query:{src:item.panoramic, pageType:this.pageType}}"><img :src="item.pic"></router-link>
                            </div>
                        </div>
                    </div>
                </section>
                <section class="apartment">
                    <h4>APARTMENT LISTING</h4>
                    <div class="swiper-container" id="test">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide" v-for="item in hotelDetail.roomTypeList">
                                <router-link :to="{path:'/appartmentStyle'}">
                                    <img :src="item.pic">
                                    <h5>{{item.title}}</h5>
                                    <ul class="apart_style">
                                        <li class="col-5"><i></i>Max {{item.personcount}} person(s)</li>
                                        <li class="col-5"><i></i>{{item.bedtype}}</li>
                                    </ul>
                                    <ul class="apart_util">
                                        <li>
                                            <div class="col-5">No of Units:</div>
                                            <div class="col-5">{{item.roomcount}}</div>
                                        </li>
                                        <li>
                                            <div class="col-5">Apartment Size:</div>
                                            <div class="col-5">{{item.size}}sqm</div>
                                        </li>
                                        <!--<li>-->
                                        <!--<div class="col-5">Location</div>-->
                                        <!--<div class="col-5">1st to 6th floors</div>-->
                                        <!--</li>-->
                                    </ul>
                                    <ul class="apart_btn">
                                        <li class="col-6">
                                            <router-link  to="/addShowingOrder">BOOK NOW</router-link>
                                        </li>
                                        <li class="col-4">RESERVE</li>
                                    </ul>
                                </router-link>
                            </div>

                        </div>
                    </div>
                </section>
                <section class="fire">
                    <h4>FIRE RIGHTINH</h4>
                    <div class="swiper-container">
                        <div class="swiper-wrapper">
                            <div class="swiper-slide">
                                <img src="../../assets/images/floor-case.png">
                            </div>
                            <div class="swiper-slide">
                                <img src="../../assets/images/floor-case.png">
                            </div>
                            <div class="swiper-slide">
                                <img src="../../assets/images/floor-case.png">
                            </div>
                        </div>
                    </div>
                </section>

                <section class="how">
                    <!--<h4>GETTING TO ASCOTT HENG SHAN SHANGHAI</h4>-->
                    <!--<p>From Shanghai Hongqiao Airport:</p>-->
                    <div class="how_txt how_phone" v-for="item in hotelDetail.trafficList">
                        <!--<ul>-->
                        <!--<li class="col-8">{{item.introduct}}</li>-->
                        <!--&lt;!&ndash;<li class="col-6"><i></i>(86-21)2329 8999</li>&ndash;&gt;-->
                        <!--</ul>-->
                        <p>{{item.introduct}}</p>
                    </div>
                    <!--<div class="how_txt how_location">-->
                    <!--<ul>-->
                    <!--<li class="col-8">By Airport Bus and Taxi</li>-->
                    <!--&lt;!&ndash;<li class="col-6"><i></i>(86-21)2329 8999</li>&ndash;&gt;-->
                    <!--</ul>-->
                    <!--<p>It takes approximately 40 minutes by taxi and costs about $80 to get to </p>-->
                    <!--</div>-->
                </section>


            </section>
        </scroller>

    </div>
</template>
<style>

</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
    import {getDistance} from'../../config/mUtils'
    export default {
		data() {
			return {
                details:{},
                lat:'',
                lng:'',
				pageType:'',
                distance:'',
                facList:[
                    {icon:'tv',imgSrc:require('../../assets/images/Group 5 Copy@2x.png')},
                    {icon:'restaurant',imgSrc:require('../../assets/images/dining room@2x.png')},
                    {icon:'WIFI',imgSrc:require('../../assets/images/wifi@2x.png')},
                    {icon:'gym',imgSrc:require('../../assets/images/gym@2x.png')},
                ],
                facListShow:[]
			};
		},
        created:function () {
			//获取前一个页面标识
            this.pageType = this.$route.query.pageType;

            //获取物业详情
	        let params = {
		        hotelid: localStorage.HOTELID,
		        lang: localStorage.LANGUAGE
	        }
	        this.$store.dispatch('getHotelDetail', params).then((res) => {
                this.details= this.hotelDetail
                this.lat = this.hotelDetail.lat;
                this.lng = this.hotelDetail.lng;

                //重组图标数据
                for(var item in this.hotelDetail.facilitiesList){
                	for(var i in this.facList){
                		if(this.hotelDetail.facilitiesList[item].icon == this.facList[i].icon){
			                this.facListShow.push(this.facList[i])
                        }
                    }
                }
                $(document).ready(function () {
                    var swiper = new Swiper('.equipment .swiper-container', {
                        pagination: '.swiper-pagination',
                        slidesPerView: 6,
                        paginationClickable: true,
                        spaceBetween: 0
                    });
	                var swiper3 = new Swiper('.apartment .swiper-container', {
		                pagination: '.swiper-pagination',
		                slidesPerView: 1.1,
		                paginationClickable: true,
		                spaceBetween: 15
	                });
	                var swiper4 = new Swiper('.fire .swiper-container', {
		                pagination: '.swiper-pagination',
		                slidesPerView: 1.2,
		                paginationClickable: true,
		                spaceBetween: 15
	                });
	                var swiper2 = new Swiper('.virtual .swiper-container', {
		                pagination: '.swiper-pagination',
		                slidesPerView: 3,
		                paginationClickable: true,
		                spaceBetween: 0
	                });
                })
	        })
        },
		methods: {
			goLocation:function () {
				openMap(this.lat,this.lng)
			}
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components:{
        },
		computed: {
			...mapState({
				hotelDetail: state => state.home.hotelDetail,
				home: state => state.home.home,
			})
		}
	};
</script>
