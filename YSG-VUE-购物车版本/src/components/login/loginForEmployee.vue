<template>
    <div class="box">
        <div class="guide">
            <yd-navbar class="guide_title" title="">
                <router-link to="/unlogin" slot="left" v-if="flag == 'unlogin'">
                    <span class="close"></span>
                </router-link>
                <router-link to="/home" slot="left" v-if="flag == 'home'">
                    <span class="close"></span>
                </router-link>
            </yd-navbar>
            
            <section class="login_bottom">
                <h2 class="login_title">{{language.login.sign}}</h2>
                <div class="search_box login_select" @click="isPopShow = true">
                    <input type="text" :placeholder="language.login.residence" disabled v-model="residence">
                    <i></i>
                    <popup-picker :data="propertyList" :show="isPopShow" :columns="1" @on-hide="isPopShow = false" @on-change="onEndChange" show-name></popup-picker>
                </div>

                <div class="search_box">
                    <input type="text" :placeholder="language.login.empAccount" v-model="lname">
                </div>

                <div style="border-bottom: 1px solid #fff;padding-bottom: .4rem;">
                    <ul class="em_password">
                        <li><input type="password" :placeholder="language.login.password" v-model="pwd"></li>
                        <!--<li><span>Forgot ?</span></li>-->
                    </ul>
                </div>
                <div style="margin-top: .2rem;">
                    <yd-checkbox size="15" v-model="isAd"> {{language.login.isAd}}</yd-checkbox>
                </div>
                <div class="login_in">
                    <span @click="doLogin">{{language.login.aLogin}}</span>
                </div>
            </section>
        </div>
        <v-menu></v-menu>
    </div>
</template>

<style>
.em_password{padding: .35rem 0;}
.em_password li{float: left;}
.em_password li:first-child{width: 70%;}
.em_password li:last-child{width: 30%;text-align: right;font-size: .28rem;color: #fff;}
.em_password li input{font-size: .28rem;color: #fff;border: none;width: 100%;}
.em_password li input::-webkit-input-placeholder{color: #fff;}
.em_password li input:-moz-placeholder {color: #fff;}
.em_password li input::-moz-placeholder {color: #fff;}
.m-checkbox>.checkbox-icon{vertical-align: middle!important;}
.m-checkbox>.checkbox-text{color: #fff!important;}
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
                isAd:false,
                ad:0,
                lname:'',
                pwd:'',
				residence:'',
				propertyList:[],
				isPopShow:false,
                flag:'',
                hotelId:''
			};
		},
        created:function () {
			this.flag = localStorage.menuFlag
	        //根据id获取集团物业列表
	        let params = {groupid: localStorage.groupid}
	        this.$store.dispatch('getGroupListById', params).then((res) => {
		        //整理结果集
		        for(var i=0;i<this.groupListbyId.list.length;i++){
			        var item = new Object();
			        item.name = this.groupListbyId.list[i].name;
			        item.value = this.groupListbyId.list[i].hotelId;
			        this.propertyList.push(item);
		        }
	        })
	    },
		methods: {
            doLogin:function () {
                let _this = this
            	//获取ad状态
                if(this.isAd){
                	this.ad = 1;
                }else {
                	this.ad = 0;
                }

                if(this.lname && this.pwd){
	                //如果用户输入信息完整，执行登录操作
	                let params = {
		                groupid: localStorage.groupid,
		                hotelid: this.hotelId,
		                identity: localStorage.identity,
		                lname: this.lname,
		                pwd: this.pwd,
		                platform: localStorage.platform,
		                ad: this.ad
	                };

	                this.$store.dispatch('loginStaff', params).then((res) => {
	                	if(res.code == 0){
                            //存储相关用户信息
                            localStorage.TOKEN = res.data.token;
                            localStorage.HOTELID = _this.hotelId;
                            localStorage.baseHotelId = this.hotelId;
                            localStorage.FULLNAME = _this.lname;
                            localStorage.oa_id = res.data.staffid;
                            localStorage.oaServiceUrl = res.data.serviceUrl;
                            localStorage.spage=0;
                            localStorage.searchFlag = 'home'
                            localStorage.userId = res.data.id
                            localStorage.id = res.data.id
                            localStorage.ip = res.data.lastloginip
                            //跳转home页面
                            this.$router.replace({path:'/home',query:{type:1}});
                            localStorage.idType = 2;

                            //消息推送
                            let obj={
                                userType:'staff',
                                userId:res.data.id,
                                hotelId:[
                                    'hotel_'+_this.hotelId,
                                    'group_'+localStorage.groupid,
                                    localStorage.LANGUAGE
                                ]

                            }
                            setMessageTag(obj.userType,obj.userId,obj.hotelId);

                        } else {
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
			}


		},
		components: {
			'v-menu': menu,
			PopupPicker
		}
    ,
    computed: {
        ...mapState({
                groupListbyId: state => state.home.groupListbyId,
                language: state => state.language.language
            })
    },
	};
</script>
