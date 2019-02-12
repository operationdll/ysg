<template>
    <div class="box">
        <div class="guide">
            <yd-navbar class="guide_title" title="">
                <router-link to="/unlogin" slot="left" v-if="pageType == 'n_home'">
                    <span class="close"></span>
                </router-link>
                <router-link :to="{path:'/s_home',query:{hotelid:come_hotelid}}" slot="left" v-else-if="pageType == 's_home'">
                    <span class="close"></span>
                </router-link>
                <span class="close"  slot="left" v-else @click="goBack"></span>
            </yd-navbar>


            <section class="login_bottom">
                <h2 class="login_title">{{language.login.sign}}</h2>
                <div class="search_box login_select" @click="isPopShow = true">
                    <input type="text" :placeholder="language.login.residence" disabled v-model="residence">
                    <i></i>
                    <popup-picker :data="propertyList" :show="isPopShow" :columns="1" @on-hide="isPopShow = false" @on-change="onEndChange" show-name></popup-picker>
                </div>
                <ul class="login_style">
                    <li>
                        <input type="text" :placeholder="language.login.room" v-model="roomNo">
                    </li>
                    <li>
                        <input type="text" :placeholder="language.login.name" v-model="fullName">
                    </li>
                </ul>
                <div class="login_in">
                    <span @click="doLogin">{{language.login.login}}</span>
                </div>
            </section>

        </div>

    </div>
</template>

<style>
.login_bottom{position: absolute;width: 100%;left:0;bottom:0;padding: .36rem;}
.login_bottom .txt{text-align: center;padding-top: 1rem;font-size: .28rem;color: #fff;line-height: .36rem;}
.login_bottom .txt h4{color: #efb562;}
.login_style>li{width: 46%;padding: .35rem 0;border-bottom: 1px solid #fff;}
.login_style>li:first-child{float: left;}
.login_style>li:last-child{float: right;}
.login_style input{font-size: .28rem;border: none;width: 80%;color: #fff;}
.login_style input::-webkit-input-placeholder{color: #fff;}
.login_style input:-moz-placeholder {color: #fff;}
.login_style input::-moz-placeholder {color: #fff;}
.login_title{font-size: .52rem;color: #fff;font-weight: normal;}
.login_in{text-align: center;padding: .6rem 0;font-size: .3rem;color: #fff;clear: both;}
.login_in span{border-bottom: 1px solid #fff;}
.login_select i{background: url("../../assets/images/icon_download.png")center no-repeat!important;background-size: .3rem!important;}
.search_box .vux-cell-box{display: none;}
.vux-popup-picker-header{color: #4fb7ee!important;}
</style>

<script>
	import { mapState } from 'vuex'
	import { mapGetters } from 'vuex'
	import { PopupPicker} from 'vux'
    import menu from '../common/menu.vue'
	export default {
		data() {
			return {
				menuFlag:false,
                fullName:'',
                roomNo:'',
                propertyList:[],
                isPopShow:false,
				residence:'',
                hotelId:'',
                pageType:'',
                come_hotelid:''
			};
		},
		created:function () {
			this.come_hotelid = this.$route.query.hotelid

			//获取跳转的上一个页面标识
            this.pageType = this.$route.query.pageType
            //根据id获取集团物业列表
            let params = {groupid: localStorage.groupid}
            this.$store.dispatch('getGroupListById', params).then((res) => {
                //整理结果集
                for(var i=0;i<this.groupListbyId.list.length;i++){
                    var item = new Object();
                    if(localStorage.LANGUAGE == 'en' || localStorage.LANGUAGE == null){
                        item.name = this.groupListbyId.list[i].nameEn;
                    } else {
                        item.name = this.groupListbyId.list[i].name;
                    }

                item.value = this.groupListbyId.list[i].hotelId;
                this.propertyList.push(item);
            }
            }).catch((res) => {

            })

		},
		methods: {
            doLogin:function () {
                let _this = this
                //如果用户输入信息完整，执行登录操作
            	if (!(this.roomNo && this.roomNo)) {
                    //如果用户输入信息不完整
                    this.$dialog.toast({mes: this.language.msg.empty_one, timeout: 1000});
                } else {
                    let params = {
                        fullname: this.fullName,
                        groupid: localStorage.groupid,
                        hotelid: this.hotelId,
                        identity: localStorage.identity,
                        lang: 'zh',
                        platform: localStorage.platform,
                        room_no: this.roomNo
                    }

                    this.$store.dispatch('doLogin', params).then((res) => {
                        if (res.code == 0) {
                            //存储用户token及物业id
                            localStorage.TOKEN = res.data.token;
                            localStorage.HOTELID = this.hotelId;
                            localStorage.baseHotelId = this.hotelId;
                            localStorage.FULLNAME = this.fullName;
                            localStorage.ROOM_INFO = this.roomNo;
                            localStorage.serviceUrl = res.data.serviceUrl;
                            localStorage.spage = 0;
                            localStorage.searchFlag = 'home'
                            localStorage.oid = res.data.oid
                            localStorage.id = res.data.id
                            localStorage.ip = res.data.lastloginip
                            //跳转首页

                            this.$router.replace({path: '/home', query: {type: 1}});
                            localStorage.idType = 1;

                            //消息推送
                            let obj = {
                                userType: 'user',
                                userId: res.data.id,
                                hotelId: [
                                    'hotel_'+_this.hotelId,
                                    'group_'+localStorage.groupid,
                                    localStorage.LANGUAGE
                                ]
                            }
                            setMessageTag(obj.userType, obj.userId, obj.hotelId);

                        } else {
                            //展示错误信息
                            this.$dialog.toast({mes: _this.language.msg.login_err, timeout: 1000});
                        }
                    })
                }
            },
			onEndChange:function (key) {
				for (var index in this.propertyList){
					if (this.propertyList[index].value == key){
						this.residence =  this.propertyList[index].name
						break;
					}
				}
				this.hotelId = key;
			},
			goBack:function () {
                this.$router.go(-1);
			}
		},
		components: {
			'v-menu': menu,
			PopupPicker,

		},
		computed: {
			...mapState({
				groupListbyId: state => state.home.groupListbyId,
                language: state => state.language.language
			})
		},

	};
</script>
