<template>
    <div class="airport">
        <div class="nav_mark"></div>
        <yd-navbar title="AIRPORT INFO" fixed>
            <router-link to="/home" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="airport_info_box">
            <section class="airport_info">
                <h2>Select Flight: PVG <i></i> NYC</h2>
                <p>Wed,Jan 18,2017</p>
            </section>
            <section class="tab_list">
                <yd-tab>
                    <yd-tab-panel label="PRICE">
                        <ul class="price">
                            <li>
                                <section class="part_1">
                                    <div class="col-7">China Eastern Airlines</div>
                                    <div class="col-3">HK$8,366</div>
                                </section>
                                <section class="part_2">
                                    <div class="col-3">PVG 9:10AM</div>
                                    <div class="col-4"></div>
                                    <div class="col-3">NRT 1:50PM</div>
                                </section>
                            </li>
                            <li>
                                <section class="part_1">
                                    <div class="col-7">China Eastern Airlines</div>
                                    <div class="col-3">HK$8,366</div>
                                </section>
                                <section class="part_2">
                                    <div class="col-3">PVG 9:10AM</div>
                                    <div class="col-4"></div>
                                    <div class="col-3">NRT 1:50PM</div>
                                </section>
                            </li>
                            <li>
                                <section class="part_1">
                                    <div class="col-7">China Eastern Airlines</div>
                                    <div class="col-3">HK$8,366</div>
                                </section>
                                <section class="part_2">
                                    <div class="col-3">PVG 9:10AM</div>
                                    <div class="col-4"></div>
                                    <div class="col-3">NRT 1:50PM</div>
                                </section>
                            </li>
                            <li>
                                <section class="part_1">
                                    <div class="col-7">China Eastern Airlines</div>
                                    <div class="col-3">HK$8,366</div>
                                </section>
                                <section class="part_2">
                                    <div class="col-3">PVG 9:10AM</div>
                                    <div class="col-4"></div>
                                    <div class="col-3">NRT 1:50PM</div>
                                </section>
                            </li>
                            <li>
                                <section class="part_1">
                                    <div class="col-7">China Eastern Airlines</div>
                                    <div class="col-3">HK$8,366</div>
                                </section>
                                <section class="part_2">
                                    <div class="col-3">PVG 9:10AM</div>
                                    <div class="col-4"></div>
                                    <div class="col-3">NRT 1:50PM</div>
                                </section>
                            </li>
                        </ul>

                    </yd-tab-panel>
                    <yd-tab-panel label="DEPARTS">

                    </yd-tab-panel>
                    <yd-tab-panel label="ARRIVES">

                    </yd-tab-panel>
                    <yd-tab-panel label="MINS">

                    </yd-tab-panel>
                </yd-tab>
            </section>

        </div>
    </div>
</template>

<style>
    .airport_info_box{margin-top: .9rem;}
    .airport_info_box .airport_info{padding: .6rem .2rem;background: #f8f8f8;border-bottom: 1px solid #ddd;}
    .airport_info_box .airport_info h2{font-size: .36rem;color: #333;font-weight: normal;margin-bottom: .3rem;}
    .airport_info_box .airport_info h2 i{display: inline-block;width: .36rem;height: .36rem;background: url("../../assets/images/icon_arriveto.png")center no-repeat;background-size: .36rem;}
    .airport_info_box .airport_info p{font-size: .28rem;color: #a3a3a3;}
    .airport_info_box .tab_list{padding: .2rem;}
    .airport_info_box .price>li{padding: .6rem 0;border-bottom: 1px solid #eee;}
    .airport_info_box .price>li>section{overflow: hidden;}
    .airport_info_box .price>li>section.part_1{font-size: .32rem;margin-bottom: .4rem;}
    .airport_info_box .price>li>section.part_1 .col-3{text-align: right;color: #f0c366;}
    .airport_info_box .price>li>section.part_2{font-size: .28rem;color: #505050;}
    .airport_info_box .price>li>section.part_2>div:last-child{text-align: right;}
    .airport_info_box .price>li>section.part_2 .col-4{height: .28rem;background: url("../../assets/images/icon_arrive_arrow.png")center no-repeat;background-size: 100%;}
</style>

<script type="text/babel">
	export default {
		data() {
			return {
			}
		},
        created:function () {

        },
		methods: {
		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }

	};
</script>
