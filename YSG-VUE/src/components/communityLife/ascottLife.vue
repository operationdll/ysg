<template>
    <div class="search">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.community.ascottLife" fixed>
            <router-link to="/communityLife" slot="left">
                <span class="close"></span>
            </router-link>
        </yd-navbar>
        <section class="promotiom_list top_nav resetPro  s-list">
            <scroller :on-infinite="infinite">
                <ul class="ac-list">

                    <li v-for="data in dataList" @click="goDetail(data.id)">
                        <div class="s-left">
                            <div>
                                <h4>{{data.name}}</h4>
                                <p>{{data.address}}</p>
                                <span class="tag-type">{{data.type}}</span>
                            </div>
                        </div>
                        <div class="s-right">
                            <img :src='data.pic' style="margin-bottom: 8px;"/>
                        </div>
                    </li>
                </ul>
                <p class="no_data" v-show="noData">{{language.common.noMoreData}}</p>
            </scroller>
        </section>
    </div>
</template>

<style>
    .s-left{position: relative;}
    .tag-type{position: absolute;left:0;bottom: 0;border: 1px solid #f0c366;font-size: .22rem;padding: 0 5px;border-radius: .32rem;}
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
            }
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
                    page:_this.nextPage,
                    status:1
                }
                this.$store.dispatch('getLifeList', params).then(function (res) { 
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
            goDetail:function (id) {
                let data = {};
                for(var key in this.dataList){
                    if (this.dataList[key].id == id){
                        data = this.dataList[key];
                        break;
                    }
                }
                this.$router.push({path:'/lifeDetail',query:{info:data}})
            }
        },
        components: {
        },
        computed: {
        ...mapState({
                language: state => state.language.language
            })
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }
    }
</script>
