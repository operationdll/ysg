<template>
  <div class="promotiom">
      <div class="nav_mark"></div>
      <yd-navbar :title="language.community.feedback" fixed>
          <router-link to="/communityLife" slot="left">
              <span class="close"></span>
          </router-link>
      </yd-navbar>

      <section style="margin-top: 1.3rem;">
              <yd-cell-group>
                  <yd-cell-item v-for="data in dataList" arrow type="label" @click.native="toFeedback(data.id)">
                      <span slot="left">{{data.name}}</span>
                  </yd-cell-item>
              </yd-cell-group>
      </section>

  </div>
</template>
<style>
</style>

<script type="text/babel">
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    import foot from '../foot.vue'
	import menu from '../common/menu.vue'
    import guestCenter from '../common/menu/guestCenter.vue'
    import { PopupPicker, XButton, Scroller } from 'vux'
//    import { Scroller, Divider, Spinner, XButton, Group, Cell, LoadMore, PopupPicker } from 'vux'
	export default {
		data() {
			return {
                isItemShow:false,
                dataList:[],
                nextPage:1,
			};
		},
        created:function () {
		    //获取所有列表
            let params = {'hotelid':localStorage.HOTELID};
            this.$store.dispatch('getFeedbackList', params).then((res) => {
                this.dataList = res.data.data.list;
            }).catch((res) => {
            });

        },
		methods: {
           toFeedback :function (id) {
//               alert(1)
               this.$router.push({path:'/feedbackDetail',query:{id:id}});
           }


		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components:{
			'v-foot':foot,
            'v-menu':menu,
            'v-guestCenter':guestCenter,
            PopupPicker,
            Scroller,
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
	};
</script>
