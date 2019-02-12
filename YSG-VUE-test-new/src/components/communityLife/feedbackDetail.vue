<template>
  <div class="promotiom">
      <div class="nav_mark"></div>
      <yd-navbar :title="language.community.feedback" fixed>
      <router-link to="/feedback" slot="left">
          <span class="close"></span>
      </router-link>
      </yd-navbar>

      <section class="quetion_list">
          <ul id="feedback">
              <li v-for="(data,index) in dataList">
                  <div :data-type="data.type" class="que" :data-questionid="data.questionid" v-if="data.type == 1">
                      {{index + 1 }}、<label>{{data.question}}</label>
                      <div style="padding:0 .15rem;">
                          <textarea style="width:100%;height:100px;border: 1px solid #ddd;" placeholder="请输入..."></textarea>
                      </div>
                  </div>
                  <div :data-type="data.type" class="que" :data-questionid="data.questionid" v-if="data.type == 2">
                      {{index + 1 }}、<label>{{data.question}}</label>
                      <div class="list"  v-for="op in data.option"> <input type="radio" :value="op" :name="index"> {{op}}</div>
                  </div>
                  <div :data-type="data.type" class="que" :data-questionid="data.questionid" v-if="data.type == 3">
                      {{index + 1 }}、<label>{{data.question}}</label>
                    <div class="list" v-for="op in data.option"> <input type="checkbox" :value="op"> {{op}}</div>
                  </div>
              </li>
          </ul>
          <section class="foot_btn_box">
              <button class="foot_btn" type="button" @click="commit">{{language.community.submit}}</button>
          </section>


      </section>

  </div>
</template>
<style>
    .quetion_list{padding: .15rem;font-size: .28rem;margin-top: 1.3rem;}
    .quetion_list label{font-size: .32rem;color: #333;}
    .quetion_list .list{font-size: .28rem;color: #666;}
</style>

<script>
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
                listid:'',
                dataList:[],
                radio1: '啦啦啦',
                checkbox2: [],
                index:0,
			};
		},
        created:function () {
		    this.listid = this.$route.query.id;
		    //获取所有列表
            let params = {'hotelid':localStorage.HOTELID,'listid':this.listid};
            this.$store.dispatch('getFeedback', params).then((res) => {
                this.dataList = res.data.data.list;
            }).catch((res) => {
            });

        },
		methods: {
            commit: function () {
                let _this = this;
		        var result = {};
                //获取答案
                $("#feedback li .que").each(function () {
                    var questionid = $(this).data("questionid");
                    if ($(this).data("type") == 1){
                        //获取textarea的内容
                        result[questionid] = $(this).find("textarea").val();
                    }else if ($(this).data("type") == 2){
                        //获取单选按钮的内容
                        result[questionid] = $(this).find("input:radio:checked").val();
                    }else if ($(this).data("type") == 3){
                        let str = [];
                        $(this).find("input:checkbox:checked").each(
                            function () {
                                str.unshift($(this).val());
                            }
                        );
                        result[questionid] = str.toString();
                    }
                    }
                );
                var resultStr = JSON.stringify(result);

                console.log('test-xin',result)

                let params = {hotelid:localStorage.HOTELID,listid:this.listid,answer:resultStr,token:localStorage.TOKEN};
                this.$store.dispatch('addFeedbackResult', params).then((res) => {
//                    alert(1);
                    if(res.data.code == 0) {
                        _this.$router.go(-1);
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


http://lixin.xapi.wangtiansoft.com/feedbackResult/addFeedbackResult?hotelid=1&listid=1&answer={"2":"test","4":"rt"}&token=6dc778f57cf225a39e1730bdf0e54510&time=1504060754&sign=c25d13c61f6f71eb60bfb6a7d58c470e
