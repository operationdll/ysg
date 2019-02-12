<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.order.title" fixed>
            <router-link to="/home" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div style="position: absolute;height: 100%;left:0;top:0;width: 100%;">
            <scroller style="background: #f8f8f8;padding-top: 1.3rem;" :on-infinite="infinite">
                <ul class="order-list">
                    <li v-for="item in orderList">
                        <h4><p>{{item.shoppingInfo.name}}</p>
                            <button v-if="item.status == 1" class="red-bg" type="button" @click="changeOrder(item.status, item.id)">{{item.statusName}}</button>
                            <button v-else-if="item.status == 2" class="orange-bg" type="button" @click="changeOrder(item.status, item.id)">{{item.statusName}}</button>
                            <button v-else type="button" class="green-bg" @click="changeOrder(item.status, item.id)">{{item.statusName}}</button>
                        </h4>
                        <ul class="order-inner">
                            <!--<li>{{language.order.orderid}}:{{item.shoppingid}}</li>-->
                            <li>{{language.order.roomid}}:{{item.userInfo.room}}</li>
                            <li>{{language.order.user}}:{{item.userInfo.name}}</li>
                            <li>{{language.order.time}}:{{new Date(parseInt(item.createtime) * 1000).toLocaleString().substr(0,9)}}</li>
                        </ul>
                    </li>
                </ul>
                <p class="no_data" v-show="noData">{{language.common.noMoreData}}</p>
            </scroller>
        </div>

    </div>
</template>
<style>
    .order-list{padding: .3rem;padding-bottom: 1rem;}
    .order-list>li{background: #fff;border-radius: 5px;margin-bottom: .2rem;}
    .order-list>li>h4{font-size: .32rem;padding: .15rem;border-bottom: 1px solid #eee;overflow: hidden}
    .order-list>li>h4 p{float: left;width: 80%;text-overflow: ellipsis;overflow: hidden;white-space: nowrap;}
    .order-list>li>h4 button{float: right;width: 19%;height: .5rem;border: none;border-radius: 20px;font-size: .24rem;color: #fff;}
    .order-inner{padding: .15rem;overflow: hidden;}
    .order-inner>li{float: left;width: 50%;color: #999;font-size: .24rem;}
    .red-bg{background: #f15a23;}
    .orange-bg{background: #f0c366;}
    .green-bg{background: #91c551;}
</style>
<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
			    orderList:[],
                nextPage:1,
                noData: false
			}
		},
        created:function () {
        },
		methods: {
            getData:function(){
                let _this = this
                let params = {
                    token: localStorage.TOKEN,
                    hotelid: localStorage.HOTELID,
                    limit: 10,
                    page:_this.nextPage
                }
                this.$store.dispatch('getOrderList', params).then(function (res) {
                    var arrList = res.data.list
                    _this.orderList = _this.orderList.concat(arrList)
                    _this.nextPage = res.data.nextPage
                })
            },
            infinite:function (done) {
                let _this = this
                done(true);
                if (this.nextPage != '-1') {
                    _this.getData()
                } else {
                    done(false);
                    _this.noData = true;
                }
            },
            changeOrder:function (status, id) {
                if(status<3){
                    status = Number(status)+1
                }
                let _this = this
                let params = {
                    token: localStorage.TOKEN,
                    orderid:id,
                    status:status
                }
                this.$store.dispatch('changeOrder', params).then(function (res) {
                    if(res.code == 0){
                        _this.$dialog.toast({mes: '处理完成', timeout: 1000});
                    } else {
                        _this.$dialog.toast({mes: res.data.msg, timeout: 1000});
                    }

                })
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
                language: state => state.language.language
            })
        }
	};
</script>
