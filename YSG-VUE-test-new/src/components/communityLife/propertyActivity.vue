<template>
<div class="property">
	<div class="nav_mark"></div>
	<yd-navbar :title="language.community.propertyActivity" fixed>
		<router-link to="/communityLife" slot="left">
			<span class="close"></span>
		</router-link>
	</yd-navbar>
	<section class="promotiom_list top_nav resetPro">
		<scroller :on-infinite="infinite">
			<ul class="ac-list">
				<li v-for="data in dataList" @click="goDetail(data.id)">
					<span class="tag">{{data.tagName}}</span>
					<img  :src="data.pic" alt="">
					<div class="promotion-info">
						<p>{{data.title}}</p>
						<ul class="s-time">
							<li>
								<div class="col-5">{{language.community.apply}}</div>
								<div class="col-5">{{language.community.start}}</div>
							</li>
							<li>
								<div class="col-5">{{new Date(parseInt(data.fromdate) * 1000).toLocaleString().substr(0,9)}}</div>
								<div class="col-5">{{new Date(parseInt(data.todate) * 1000).toLocaleString().substr(0,9)}}</div>
							</li>
						</ul>
					</div>
				</li>
			</ul>
			<p class="no_data" v-show="noData">{{language.common.noMoreData}}</p>
		</scroller>
	</section>
</div>
</template>
<style>
</style>
<script >
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
				dataList:[],
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
                    hotelid: localStorage.HOTELID,
					lang: localStorage.LANGUAGE,
                    limit:5,
                    page:_this.nextPage
				}
                this.$store.dispatch('getActivityList', params).then(function (res) {
                    var arrList = res.data.data.list
                    _this.dataList = _this.dataList.concat(arrList)
                    _this.nextPage = res.data.data.nextPage
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
            goDetail:function (id) {
                let data = {};
                for(var key in this.dataList){
                    if (this.dataList[key].id == id){
                        data = this.dataList[key];
                        break;
                    }
                }
                this.$router.push({path:'/propertyDetail',query:{info:data}})
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
                language: state => state.language.language
            })
        }

    };
</script>
