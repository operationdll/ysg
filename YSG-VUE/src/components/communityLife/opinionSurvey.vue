<template>
    <div class="search">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.community.opinionSurvey" fixed>
            <router-link to="/communityLife" slot="left">
                <span class="close"></span>
            </router-link>
        </yd-navbar>
        <section class="promotiom_list top_nav resetPro s-list">
            <scroller :on-infinite="infinite">
                <ul>
                    <li v-for="data in dataList" @click="goDetail(data)">
                        <div class="s-left">
                            <div>
                                <h4>{{data.title}}</h4>
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
    </div>
</template>

<style>
</style>
<script>
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
	export default {
		data() {
			return {
                dataList: [],
                nextPage: 1,
                noData: false
			};
		},
        created:function () {

            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
	    },
		methods: {
		    getData:function () {
                let _this = this
                let params = {
                    hotelid: localStorage.HOTELID,
                    lang: localStorage.LANGUAGE,
                    limit:30,
                    page:_this.nextPage
                }
                this.$store.dispatch('getNoticList', params).then(function (res) {
 
                    var arrList = res.data.data.list;
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
            goDetail:function (info) {
                this.$router.push({path:'/opinionDetail', query:{info:info}})
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
        },

	};
</script>
