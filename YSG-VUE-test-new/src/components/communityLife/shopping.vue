<template>
  <div class="search" style="height: 100%">
    <div class="nav_mark"></div>
    <yd-navbar :title="language.community.shopping" fixed>

    <router-link to="/home" slot="left" v-if="pageFlag == 'home'">
        <span class="close"></span>
    </router-link>

    <router-link to="/s_home" slot="left" v-else-if="pageFlag == 's_home'">
        <span class="close"></span>
    </router-link>

    <router-link to="/communityLife" slot="left" v-else>
        <span class="close"></span>
    </router-link>


    </yd-navbar>
    <section class="promotiom_list top_nav" style="height: 100%">
      <scroller :on-infinite="infinite">
        <ul class="type-buy" style="padding-top: 0.5rem">
          <li v-for="data in dataList" @click="goDetail(data.id)">
              <div class="col-4">
                  <img  :src="data.pic" alt="">
              </div>
              <div class="col-6">
                  <h4>{{data.title}}</h4>
                  <p>{{data.introduct}}</p>
                  <ul class="s-price">
                    <li class="col-5">RMB {{data.price}}</li>
                    <li class="col-5"><button type="button">{{language.community.buy}}</button></li>
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
<script>
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                dataList: [],
                nextPage: 1,
                noData: false,
                pageFlag:''
            }
        },
        created:function () {
            this.pageFlag = this.$route.query.pageFlag
        },
        methods: {
            getData:function () {
                let _this = this
                let params = {
                    hotelid: localStorage.HOTELID,
                    lang: localStorage.LANGUAGE,
                    limit:6,
                    page:_this.nextPage
                }
                this.$store.dispatch('getShoppingList', params).then(function (res) {
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
                this.$router.push({path:'/buy',query:{info:data}});
            }
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components: {
        },
    computed: {
    ...mapState({
            language: state => state.language.language
        })
    },

    };
</script>


