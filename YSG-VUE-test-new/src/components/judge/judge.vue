<template>
    <div class="judge-box" style="background: #f8f8f8;">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.judge.title" fixed>
            <router-link :to="backTo" slot="left">
                <span class="back"></span>
            </router-link>
            <yd-icon name="compose" size="20px" color="#f0c366" slot="right" @click.native = "show = !show"></yd-icon>
        </yd-navbar>
        <ul class="judge-list">
            <li v-for="item in judgeList">
                <p>{{item.value}}</p>
                <div class="jd-foot">
                    <div class="col-5">{{item.fullname}}({{item.roomno}})</div>
                    <div class="col-5">{{new Date(item.createtime*1000).getFullYear()+'-'+(new Date(item.createtime*1000).getMonth() + 1)+'-'+new Date(item.createtime*1000).getDate()}}</div>
                </div>
            </li>
        </ul>
        <yd-popup v-model="show" position="bottom" height="40%">
            <section class="popInput-title">
                <div class="left"><span @click="show = !show">{{language.judge.close}}</span></div>
                <div class="right"><span @click="saveJudge">{{language.judge.save}}</span></div>
            </section>
            <section class="judge-input">
                <textarea :placeholder="language.judge.txt" v-model="judge"></textarea>
            </section>
        </yd-popup>
    </div>
</template>

<style>
    .judge-box{height: 100%;}
    .judge-list{padding: 1.3rem .2rem;}
    .judge-list>li{background: #fff;border-radius: 4px;padding: .2rem;margin-top: .2rem;font-size: .28rem;}
    .judge-list>li p{padding-bottom: .2rem;}
    .jd-foot{overflow: hidden;border-top: 1px solid #eee;font-size: .24rem;color: #999;padding-top: .2rem;}
    .jd-foot>div:last-child{text-align: right;}

    .judge-input{padding: 0 .3rem;background: #fff;}
    .judge-input textarea{width: 100%;height: 3rem;border: none;font-size: .28rem;}
    .judge-btn{padding: .2rem;}

    .popInput-title{overflow: hidden;padding: .3rem;}
    .popInput-title>div{float: left;width: 50%;font-size: 16px;}
    .popInput-title>div:last-child{text-align: right;}
    .mask-black-dialog, .mask-white-dialog{z-index: 9999!important;}

</style>

<script type="text/babel">
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                judgeList:[],
                pageFlag:'',
                backTo:{},
                trData:{},
                show:false,
                judge:''
            };
        },
        created: function () {
            let _this = this
            // 获取来源页面
            _this.pageFlag = _this.$route.query.judgeFlag
            _this.trData = _this.$route.query.info
            // 根据来源选择接口url
            var url;
            if (_this.pageFlag == 'raides') {
                url = '/Comment/getFloorCommentList'
                _this.backTo = {path:'/promotionDetail',query:{info:_this.trData}}
            } else if(_this.pageFlag == 'promotion') {
                url = '/Comment/getPromotionCommentList'
                _this.backTo = {path:'/promotionDetail',query:{info:_this.trData}}
            } else if(_this.pageFlag == 'activity') {
                url = '/Comment/getHotelActivityCommentList'
                _this.backTo = {path:'/propertyActivity',query:{info:_this.trData}}
            }


            let params = {
                url: url,
                data:{
                    id:1,
                    limit:10,
                    page:1
                }
            }
            this.$store.dispatch('getJudge',params).then(function (res) {
                _this.judgeList =  res.data.list
            })
        },
        methods: {
            goback:function () {
                this.$router.go(-1)
            },
            saveJudge:function () {
                let _this = this
                var url;
                if (_this.pageFlag == 'raides') {
                    url = '/Comment/createPoiComment'
                } else if(_this.pageFlag == 'promotion') {
                    url = '/Comment/getPromotionCommentList'
                } else if(_this.pageFlag == 'activity') {
                    url = '/Comment/createHotelActivityComment'
                }

                if(_this.judge.length > 0){
                    let params = {
                        url: url,
                        data:{
                            id:1,
                            userid:localStorage.id,
                            value:_this.judge,
                            ip:localStorage.ip,
                            hotelid:localStorage.HOTELID
                        }
                    }
                    this.$store.dispatch('saveJudge',params).then(function (res) {
                        if(res.code == 0) {
                            _this.show = false
                        } else {
                            this.$dialog.toast({
                                mes: language.judge.error,
                                timeout: 1500,
                                icon: 'error'
                            });
                        }


                    })
                } else {
                    this.$dialog.toast({
                        mes: '数据不能为空',
                        timeout: 1500,
                        icon: 'error'
                    });
                }


            }
        },
        mounted() {
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        }
    }
</script>