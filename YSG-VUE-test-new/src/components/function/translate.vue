<template>
    <div class="translate">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.func.translate" fixed>
            <router-link to="/home" slot="left" v-if="stateFlag == '1'">
                <span class="close"></span>
            </router-link>
            <router-link :to="{path:'/s_home',query:{hotelid:hotelid}}" slot="left" v-if="stateFlag == '2'">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="translate_box" style="margin-top: 1rem;">
            <section class="sele_language">
                <!-- 存储点击的按钮 -->
                <ul>
                    <li class="col-4">
                        <button id="fromBtn" type="button" @click="isBeginItemShow = true">{{beginLanguage}} <i></i></button>
                        <popup-picker :data="translateLanguageList" :show="isBeginItemShow" :columns="1" @on-hide="isBeginItemShow=false" @on-change="onBeginChange"></popup-picker>
                    </li>
                    <li class="col-2" @click="change">
                        <i></i>
                    </li>
                    <li class="col-4">
                        <button id="toBtn"type="button" @click="isEndItemShow = true">{{endLanguage}} <i></i></button>
                        <popup-picker :data="translateLanguageList" :show="isEndItemShow" :columns="1" @on-hide="isEndItemShow=false" @on-change="onEndChange"></popup-picker>
                    </li>
                </ul>
            </section>
            <section class="translate_input">
                <textarea name="translateContent" id="translateContent" placeholder="Enter the content" v-model="keyword"></textarea>
            </section>
            <section class="translate_result" v-show="translateFlag">
                <h4>Translation</h4>
                <p>{{result}}</p>
            </section>
            <section style="padding-bottom: .3rem;">
                <yd-button size="large" bgcolor="#01519e" color="#FFF" @click.native="translate()">{{language.func.translate}}</yd-button>
            </section>

        </div>
    </div>
</template>

<style>
    .translate .translate_box{padding: .2rem;border-bottom: 1px solid #ddd;}
    .translate .translate_box .sele_language{padding: .35rem 0;overflow: hidden;border-bottom: 1px solid #eee;}
    .translate .translate_box .sele_language button{width: 100%;height: .7rem;border: 1px solid  #f0c366;color: #f0c366;background: none;font-size: .28rem;}
    .translate .translate_box .sele_language button i{display: inline-block;vertical-align: middle;width: .14rem;height: .12rem;background: url("../../assets/images/icon_down_arrow.png")center no-repeat;background-size: .14rem;}
    .translate .translate_box .col-2 i{display:block;height: .7rem;width: 100%;background: url("../../assets/images/icon_arrival.png")center no-repeat;background-size: .4rem;}
    .translate .translate_box .translate_input{padding: .3rem 0;border-bottom: 1px solid #eee;}
    .translate .translate_box .translate_input textarea{font-size: .28rem;border: none;height: 2.6rem;width: 100%;}
    .translate .translate_result{padding: .2rem;font-size: .28rem;}
    .translate .translate_result p{font-size: .28rem;color: #666;}
    .translate .vux-cell-box{display: none;}
</style>

<script>
    import { PopupPicker, XButton } from 'vux'
    import { mapState } from 'vuex'
	export default {
		data() {
			return {
			    result:'',
                beginLanguage: 'English',
                isBeginItemShow : false,
                endLanguage: '中文',
                isEndItemShow : false,
                translateFlag:false,
                from:'en',
                to:'zh',
                keyword:'',
                stateFlag:''
			};
		},
        created:function () {
            this.stateFlag = localStorage.stateFlag
            //获取翻译语言的列表
            let params = {
                lang:'zh'
            };
            this.$store.dispatch('translateLanguageList', params);
        },
		methods: {
		    //翻译点击事件
		    translate:function () {
		        if (this.keyword){
                    let params = {"from":this.from,"keyword":this.keyword,"to":this.to};
                    this.$store.dispatch('translate', params).then((res) => {
                        this.result = res.data.data.result;
                        this.translateFlag = true;
//                    alert(res.data.data.result);
                    }).catch((res) => {
                    })
                }
            },
            onBeginChange (changeKey) {
		        for (var index in this.translateLanguageList){
                    if (this.translateLanguageList[index].value == changeKey){
                        this.beginLanguage =  this.translateLanguageList[index].name;
                        this.from = this.translateLanguageList[index].value;
                        break;
                    }
                }
            },
            onEndChange (changeKey) {
                for (var index in this.translateLanguageList){
                    if (this.translateLanguageList[index].value == changeKey){
                        this.endLanguage =  this.translateLanguageList[index].name;
                        this.to = this.translateLanguageList[index].value;
                        break;
                    }
                }
            },
          change:function () {
              let val = this.beginLanguage;
              this.beginLanguage = this.endLanguage;
              this.endLanguage = val;
              val = this.from;
              this.from = this.to;
              this.to = val;
          }
		},
        components:{
            PopupPicker,
        },
        computed: {
            ...mapState({
                translateLanguageList: state => state.translate.translateLanguageList,
                language: state => state.language.language
            })
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }

	};


</script>
