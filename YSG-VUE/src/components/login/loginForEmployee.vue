<template>
    <div class="box">
        <div class="guide">
            <div class="home-bnt" @click="goHome">
                <span class="back-home"></span>
            </div>
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

<style scoped>
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



.home-bnt{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 2rem;
    height: 2rem;
    border-radius: 1rem;
    margin-top: .2rem;
    margin-left: -.2rem;
    float:left;
}

.back-home {
  display: block;
  width: 0.6rem;
  height: 0.6rem;
  background: url("../../assets/images/icon_close_o.png") center no-repeat;
  background-size: 0.4rem;
}
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
            let _this = this;
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
                //设置初始化选择酒店
                _this.onEndChange(localStorage.HOTELID);
            });
            
            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });
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
                            localStorage.searchFlag = 'home';
                            localStorage.userId = res.data.id;
                            localStorage.id = res.data.id;
                            localStorage.ip = res.data.lastloginip;
                            //员工可选择物业信息
                            localStorage.staffHotels = JSON.stringify(res.data.hotel_list_detail);
                            //初始化购物车
                            global.shopCar = new Map();
                            //跳转home页面
                            this.$router.replace({path:'/home',query:{type:1}});
                            localStorage.idType = 2;
                            //是否可以控制机器人
                            localStorage.robotControl = res.data.permission.includes("1")?1:0;

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

                            //音频注册
                            if(localStorage.FULLNAME=='eleory'){
                                localStorage.ROOM_INFO = '1000';
                            }
                            loginim();
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
			},
            goHome() {
                this.$router.replace('/');
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
