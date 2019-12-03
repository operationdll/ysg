<template>
  <a href="javascript:void" class="back-head" @click="goHome" v-if="backHome">
     <span class="back-home"></span>
     <div class="back-title">{{title}}</div>
  </a>
  <a href="javascript:void" class="back-head" @click="goBack" v-else>
     <span class="back-img"></span>
     <div class="back-title">{{title}}</div>
  </a>
</template>

<style scoped>
.back-head{
  display: flex;
  width: 100%;
  height: 1.3rem;
  flex-direction: row;
  align-items:flex-end;
  border-bottom: 1px solid #B2B2B2;;
  background: white;
}

.back-img {
  display: block;
  width: 0.6rem;
  height: 0.6rem;
  background: url("../assets/images/icon_back.png") center no-repeat;
  background-size: 0.3rem;
}

.back-home {
  display: block;
  width: 0.6rem;
  height: 0.6rem;
  background: url("../assets/images/icon_close_o.png") center no-repeat;
  background-size: 0.4rem;
}

.back-title{
    font-family:PingFangSC-Regular;
    font-size:.4rem;
    color:black;
    width:6.6rem;
    text-align: center;
    text-overflow: ellipsis;
    white-space: nowrap;
    overflow: hidden;
}
</style>

<script>
import { mapState } from "vuex";

export default {
  props: ['title','home'],
  data() {
    return {
      backHome:true,
      backFixed:false
    };
  },
  created:function () {
    this.backHome = this.home === 'home' ? true : false;
  },
  methods: {
    goBack() {
      this.$router.back();
    },
    goHome() {
      this.$router.replace('/');
    }
  }
};
</script>
