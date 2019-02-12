<template>
    <div class="airport">
        <yd-navbar title="AIRPORT INFO">
            <router-link to="/home" slot="left" v-if="stateFlag == '1'">
                <span class="close"></span>
            </router-link>
            <router-link :to="{path:'/s_home',query:{hotelid:hotelid}}" slot="left" v-if="stateFlag == '2'">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="airport_box">
            <yd-tab>
                <yd-tab-panel label="PLACE">
                    <ul class="place">
                        <li>
                            <div class="left">Departure City</div>
                            <span class="arrive"></span>
                            <div class="right">Arrival City</div>
                        </li>
                        <li class="depart">
                            <p>Departure time</p>
                            <i class="time"></i>
                        </li>
                    </ul>
                    <div class="search_air">
                        <yd-button size="large" bgcolor="#01519e" color="#FFF">SEARCH</yd-button>
                    </div>
                </yd-tab-panel>
                <yd-tab-panel label="FLIGHT NO">
                    <ul class="place">
                        <li class="depart">
                            <p>FLIGHT NO</p>
                            <i class="time"></i>
                        </li>
                        <li class="depart">
                            <p>Departure time</p>
                            <i class="time"></i>
                        </li>
                    </ul>
                    <div class="search_air">
                        <yd-button size="large" bgcolor="#01519e" color="#FFF">SEARCH</yd-button>
                    </div>
                </yd-tab-panel>
            </yd-tab>
        </div>
    </div>
</template>

<style>
    .airport .tab-nav-item.tab-active{color: #f3bf54;}
    .airport_box .place{padding: .2rem;}
    .airport_box .place li{overflow: hidden;height: 1rem;line-height:1rem;font-size: .28rem;color: #acacac;overflow: hidden;margin-top: .5rem;}
    .airport_box .place .left{float: left;width: 40%;height: 1rem;line-height: 1rem;border-bottom: 1px solid #eee;}
    .airport_box .place .right{float: right;width: 40%;height: 1rem;line-height: 1rem;border-bottom: 1px solid #eee;text-align: right;}
    .airport_box .place .arrive{float: left;width: 20%;height: 1rem;line-height: 1rem;background: url("../../assets/images/icon_arrival.png")center no-repeat;background-size: .45rem;}
    .airport_box .place .time{float: right;width: .45rem;height: 1rem;background: url("../../assets/images/icon_dep_date.png")center no-repeat;background-size: .45rem;}
    .airport_box .place .depart{border-bottom: 1px solid #eee;}
    .airport_box .place .depart p{float: left;width: 80%;height: 1rem;line-height: 1rem;}
    .search_air{padding: .2rem;padding-bottom: 1rem;}
</style>

<script type="text/babel">
	export default {
		data() {
			return {
			    data:{},
                stateFlag:''
			};
		},
        created:function () {
		    this.test()
            this.stateFlag = localStorage.stateFlag
        },
		methods: {
		    test:function () {

            },

		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }

	};
</script>
