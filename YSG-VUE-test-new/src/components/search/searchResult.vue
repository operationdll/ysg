<template>
    <div class="search">
        <div class="nav_mark"></div>
        <yd-navbar title="FIND A RESIDENCE" fixed>
            <router-link to="/search" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="search_box">

            <section class="map_list" style="padding-top: 1.4rem;min-height: 7.5rem">
                <ul>
                    <li v-for="hotel in hotelList">
                        <div class="pic_show">
                            <!--<div class="location">-->
                                <!--<i></i>-->
                                <!--<span>{{hotel.distance}}KM</span>-->
                            <!--</div>-->
                            <h4 v-if="langFlag == 'en'">{{hotel.nameEn}}</h4>
                            <h4 v-else>{{hotel.name}}</h4>
                            <img :src="hotel.listpic"  @click="toView(hotel.hotelId)">
                        </div>
                        <div class="address">
                            <div class="left">
                                <p>{{hotel.address}}</p>
                            </div>
                            <div class="right">
                                <router-link :to="{path:'/booking',query:{bookUrl:hotel.bookurl}}">
                                    <button type="button">BOOKING</button>
                                </router-link>
                            </div>
                        </div>
                    </li>
                </ul>
            </section>

        </div>
    </div>
</template>

<style>
</style>
<script type="text/babel">
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    import { getDistance } from '../../config/mUtils'
	export default {
		data() {
			return {
				menuFlag:false,
                hotelList:[],
                distance:0,
                langFlag: 'en'
			};
		},
        created:function () {
		    //自己当前的经纬度
            var lat = localStorage.lat;
            var lng = localStorage.lng;
            //判断当前语言
            if(localStorage.LANGUAGE == 'en') {
                this.langFlag = 'en'
            } else {
                this.langFlag = 'zh'
            }
		    //如果是具体的某个hotel

		    if(this.$route.query.hotelId != '-1'){
		        for(var key in this.groupListbyId.list){
		            if(this.groupListbyId.list[key].hotelId == this.$route.query.hotelId){
		                //计算距离
                        let distance = getDistance(lat, lng, this.groupListbyId.list[key].lat, this.groupListbyId.list[key].lng);
                        this.groupListbyId.list[key].distance = distance;
		                this.hotelList.push(this.groupListbyId.list[key]);
                    }
                }
            }else{
                if(localStorage.LANGUAGE == 'en') {
                    if (this.$route.query.city == 'All Cities'){
                        for (var key in this.groupListbyId.list){
                            let distance = getDistance(lat, lng, this.groupListbyId.list[key].lat, this.groupListbyId.list[key].lng);
                            this.groupListbyId.list[key].distance = distance;
                            this.hotelList.push(this.groupListbyId.list[key]);
                        }
                    }else{
                        for(var key in this.groupListbyId.list){
                            if(this.groupListbyId.list[key].cityName == this.$route.query.city){
                                let distance = getDistance(lat, lng, this.groupListbyId.list[key].lat, this.groupListbyId.list[key].lng);
                                this.groupListbyId.list[key].distance = distance;
                                this.hotelList.push(this.groupListbyId.list[key]);
                            }
                        }
                    }
                } else {
                    if (this.$route.query.city == '全部城市'){
                        for (var key in this.groupListbyId.list){
                            let distance = getDistance(lat, lng, this.groupListbyId.list[key].lat, this.groupListbyId.list[key].lng);
                            this.groupListbyId.list[key].distance = distance;
                            this.hotelList.push(this.groupListbyId.list[key]);
                        }
                    }else{
                        for(var key in this.groupListbyId.list){
                            if(this.groupListbyId.list[key].cityName+'市' == this.$route.query.city){
                                let distance = getDistance(lat, lng, this.groupListbyId.list[key].lat, this.groupListbyId.list[key].lng);
                                this.groupListbyId.list[key].distance = distance;
                                this.hotelList.push(this.groupListbyId.list[key]);
                            }
                        }
                    }
                }

            }



	    },
		methods: {
            toView:function (id) {
                this.$router.push({path:'/s_home',query:{hotelid:id, page:'search'}});
            }

		},
		components: {
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        computed: {
            ...mapState({
                groupListbyId: state => state.home.groupListbyId
            })
        }

	};
</script>
