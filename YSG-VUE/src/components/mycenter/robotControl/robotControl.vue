<template>
  <div class="search">
    <div class="search_box">
        <v-back v-bind:title="language.myCenter.robotControl"></v-back>
        <div class="divmBG"></div>
        <div class="bntDiv">
            <div>
              <span class="lArrow" style="margin-top:.78rem;margin-left:.28rem;"></span>
                <button class="bnt" @click="detail(1)">送物</button>
              <span class="rArrow" style="margin-top:-.52rem;margin-left:6.17rem;"></span>
            </div>
            <div>
              <span class="lArrow" style="margin-top:.78rem;margin-left:.28rem;"></span>
                <button class="bnt" @click="detail(2)">取物</button>
              <span class="rArrow" style="margin-top:-.52rem;margin-left:6.17rem;"></span>
            </div>
            <div>
              <span class="lArrow" style="margin-top:.78rem;margin-left:.28rem;"></span>
                <button class="bnt" @click="detail(3)">召唤</button>
              <span class="rArrow" style="margin-top:-.52rem;margin-left:6.17rem;"></span> 
            </div>
            <div>
              <span class="lArrow" style="margin-top:.78rem;margin-left:.28rem;"></span>
                <button class="bnt" @click="detail(4)">引领</button>
              <span class="rArrow" style="margin-top:-.52rem;margin-left:6.17rem;"></span>
            </div>
            <div>
              <span class="lArrow" style="margin-top:.78rem;margin-left:.28rem;"></span>
                <button class="bnt" @click="detail(5)">返回充电桩</button>
              <span class="rArrow" style="margin-top:-.52rem;margin-left:6.17rem;"></span>
            </div>
        </div>
    </div>
  </div>
</template>
<style scoped>
.search_box{
    background: url("../../../assets/images/robotControl/bg.png")center no-repeat;
    background-size: 9.5rem;
    width:100%;
    height:100%;
}

.bntDiv{
    width:100%;
    margin-top:5.5rem;
    display:block;
    position:absolute;
    z-index:1;
    justify-content: center;
    text-align: center;
}

.bnt{
    background: url("../../../assets/images/robotControl/selBG.png")center no-repeat;
    width: 5.6rem;
    height: 1rem;
    border: 0px;
    background-size: 5rem;
    font-family:PingFangSC-Regular;
    font-size:.4rem;
    margin-top: .3rem;
    color:#efc567;
    text-align:center;
    margin-left: auto; 
    margin-right: auto;
}

.divmBG{
    width:100%;
    display:block;
    position:absolute;
    z-index:1;
    justify-content: center;
    text-align: center;
    height: 6rem;
    background: url("../../../assets/images/robotControl/mBG.png")center no-repeat;
    background-size: 7rem 6rem;
}

.lArrow{
    background: url("../../../assets/images/robotControl/lArrow.png")center no-repeat;
    width: 1rem;
    height: .03rem;
    border: 0px;
    background-size: 1rem .03rem;
    z-index: 3;
    display: block;
    position: absolute;
}

.rArrow{
    background: url("../../../assets/images/robotControl/rArrow.png")center no-repeat;
    width: 1rem;
    height: .03rem;
    border: 0px;
    background-size: 1rem .03rem;
    z-index: 3;
    display: block;
    position: absolute;
}
</style>

<script>
import { mapState } from "vuex";
import back from "../../back";

export default {
  data() {
    return {
    };
  },
  created: function() {
    $(function() {
      $(".navbar-center").css("marginLeft", 0);
      $("body").css({"background":"white"});
    });
  },
  mounted: function() {
    //一级页面falg
    isHomePage(0);
  },
  computed: {
    ...mapState({
      language: state => state.language.language
    })
  },
  methods: {
    detail(v){
        this.$router.push({path:'/robotDetail',query:{v:v}});
    }
  },
  components: {
    "v-back": back
  }
};
</script>
