<template>
  <div class="communityLife">
      <scroller>
          <section class="community-box">
              <div class="community-bg">
                  <button></button>
              </div>
              <div class="community-list">
                  <ul>
                      <li>
                          <router-link to="/propertyActivity">
                              <img src="../../assets/images/property-activity.png" alt="">
                              <p>{{language.community.propertyActivity}}</p>
                          </router-link>
                      </li>
                      <li>
                          <router-link to="/opinionSurvey">
                              <img src="../../assets/images/opinion-survey.png" alt="">
                              <p>{{language.community.opinionSurvey}}</p>
                          </router-link>
                      </li>
                      <li>
                          <router-link to="/shopping">
                              <img src="../../assets/images/shopping.png" alt="">
                              <p>{{language.community.shopping}}</p>
                          </router-link>
                      </li>
                      <li style="border-bottom: none">
                          <router-link to="/ascottLife">
                              <img src="../../assets/images/ascott-life.png" alt="">
                              <p>{{language.community.ascottLife}}</p>
                          </router-link>
                      </li>
                      <li style="border-bottom: none">
                          <router-link to="/feedback">
                              <img src="../../assets/images/icon-feedback.png" alt="">
                              <p>{{language.community.feedback}}</p>
                          </router-link>
                      </li>
                      <li style="border-bottom: none">
                          <router-link :to="{path:'/ssr', query:{ssrFlag:'commonunity'}}">
                              <img src="../../assets/images/icon-rss.png" alt="">
                              <p>{{language.community.ssr}}</p>
                          </router-link>
                      </li>
                      <!--<li>-->
                          <!--<router-link to="/lifeStyle">-->
                              <!--<img src="../../assets/images/ascott-life.png" alt="">-->
                              <!--<p>{{language.community.life}}</p>-->
                          <!--</router-link>-->
                      <!--</li>-->
                  </ul>
              </div>

          </section>
      </scroller>


      <v-foot></v-foot>
      <v-menu v-show="menuFlag"></v-menu>
      <v-guestCenter v-show="centerFlag"></v-guestCenter>
  </div>
</template>
<style>
.community-box{width:100%;background: #fff;}
.community-bg{width:100%;height:5rem;margin-top: -20px;background: url("../../assets/images/community-bg.png") center no-repeat;background-size: cover;position: relative;}
.community-bg button{position:absolute;left:50%;bottom:0;transform:translate(-50%,50%);border: none;width: 6.5rem;height: 1.2rem;background: url("../../assets/images/community-Group@2x.png")center no-repeat;background-size: cover;margin: 0 auto;}
.community-list{width:100%;padding: 1rem 0.3rem 0 .3rem;background: #fff;box-sizing: border-box;padding-bottom: 0.7rem;}
.community-list ul{overflow: hidden}
.community-list li{width:33.33%;padding-top:0.6rem;box-sizing: border-box;float:left;cursor: pointer;border-bottom: 1px solid #f4f4f4;border-right:1px solid #f4f4f4;}
.community-list li:nth-child(3n){border-right: none;}
.community-list li img{height:1rem;margin:0 auto;}
.community-list li p{text-align: center;margin: 0.3rem 0 0.5rem;font-size: 0.28rem;width:100%;color:#969696;}
</style>

<script type="text/babel">
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    import foot from '../foot.vue'
	import menu from '../common/menu.vue'
    import guestCenter from '../common/menu/guestCenter.vue'
	export default {
		data() {
			return {
                menuFlag:false,
                centerFlag:false
			};
		},
        created:function () {
			//激活菜单
			localStorage.footFlag = 4;
        },
        mounted:function () {
            //一级页面falg
            isHomePage(1)
        },
        components:{
			'v-foot':foot,
            'v-menu':menu,
            'v-guestCenter':guestCenter
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
	};
</script>
