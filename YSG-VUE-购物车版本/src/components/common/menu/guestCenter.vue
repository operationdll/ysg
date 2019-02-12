<template>
    <div class="user_center">
        <h2>{{fullName}}</h2>
        <!--<p>View profile</p>-->
        <ul>
            <li v-if="idType == 1">
                <div class="left">{{language.myCenter.room_info}}</div>
                <div class="right">No.{{roomInfo}}</div>
            </li>
            <!--<li v-if="idType == 2">-->
                <!--<div class="left">{{language.myCenter.oaId}}</div>-->
                <!--<div class="right">No.{{oa_id}}</div>-->
            <!--</li>-->
            <router-link to="/home/news">
                <li>
                    <div class="left">{{language.myCenter.message}}</div>
                    <div class="right"><i class="message"></i></div>
                </li>
            </router-link>
            <router-link to="/order">
                <li v-show="idType == 2">
                    <div class="left">{{language.myCenter.order}}</div>
                    <div class="right"><i class="notice"></i></div>
                </li>
            </router-link>
            <li v-show="idType == 2" @click="goGsm">
                <div class="left">{{language.myCenter.gsm}}</div>
                <div class="right"><i class="message"></i></div>
            </li>
            <li @click="goBill" v-show="idType == 1">
                <div class="left">{{language.myCenter.bill}}</div>
                <div class="right"><i class="message"></i></div>
            </li>
            <!--<li @click="goInvoices" v-show="idType == 1">-->
                <!--<div class="left">{{language.myCenter.invoices}}</div>-->
                <!--<div class="right"><i class="message"></i></div>-->
            <!--</li>-->
            <li @click="logOut">
                <div class="left">{{language.myCenter.log_out}}</div>
                <div class="right"><i class="loginout"></i></div>
            </li>
        </ul>
    </div>
</template>

<style>
    .user_center{position:absolute;top:0;left:0;width:100%;height:100%;padding: 1rem .36rem;background: #fff;z-index: 1;}
    .user_center h2{color: #4a4a4a;font-size: .48rem;}
    .user_center p{font-size: .32rem;margin-top: .2rem;color: #666;}
    .user_center>ul{margin-top: 1.2rem;}
    .user_center>ul li{height: 1rem;line-height: 1rem;border-bottom: 1px solid #eee;font-size: .28rem;color: #404040;width: 60%;overflow: hidden;padding-right: .3rem;}
    .user_center>ul li .left{float: left;}
    .user_center>ul li .right{float: right;color: #f0c366;}
    .user_center>ul li .right i{float: right;width: .45rem;height: .45rem;margin-top: .25rem;}
    .user_center .message{background: url("../../../assets/images/icon_message.png")center no-repeat;background-size: .3rem;}
    .user_center .notice{background: url("../../../assets/images/icon_notice.png")center no-repeat;background-size: .3rem;}
    .user_center .loginout{background: url("../../../assets/images/icon_loginout.png")center no-repeat;background-size: .3rem;}
</style>

<script>
	import { mapState } from 'vuex'
	import { mapGetters } from 'vuex'
	export default {
		data() {
			return {
				fullName:'',
                roomInfo:'',
				idType:'',
                oa_id:''
		    };
		},
        created:function () {
	        //获取用户名称
	        this.fullName = localStorage.FULLNAME;
	        //获取房间号
	        this.roomInfo = localStorage.ROOM_INFO;
	        //获取身份标识
	        this.idType = localStorage.idType;
	        //获取员工id
	        this.oa_id = localStorage.oa_id;
        },
		methods: {
			logOut:function () {
				//清除本地token及fullname
				this.$dialog.confirm({
					title: ' ',
					mes: this.language.common.loginTxt,
					opts: [
						{
							txt: this.language.common.cancel,
							color: false
						},
						{
							txt: this.language.common.sure,
							color: '#f0c366',
							callback: () => {

                                let obj={
                                    userType:'staff',
                                    userId:localStorage.userId,
                                    hotelId: [
                                        'hotel_'+localStorage.HOTELID,
                                        'group_1',
                                        localStorage.LANGUAGE
                                    ]
                                }

                                if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){
                                    removeAllTagAlias(obj.userType,obj.userId,obj.hotelId)
                                } else {
                                    removeMessageTag(obj.userType,obj.userId,obj.hotelId)
                                }

                                localStorage.TOKEN = '';
                                localStorage.FULLNAME = '';
                                localStorage.HOTELID = '';
                                localStorage.idType = '';
                                localStorage.searchFlag = '';
                                this.$router.push('/unlogin');

							}
						}
					]
				});
			},
			goGsm:function () {
				let _this = this;
				let params = {
					token:localStorage.TOKEN
				}
				this.$store.dispatch('getUsers', params).then(function (res) {
					_this.gsmSrc =  res.data.serviceUrl
                    if(/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)){
                        openFile(res.data.serviceUrl)
                    } else {
                        openUrl(res.data.serviceUrl,'gsm')
                    }
				})
//				this.$router.replace({path:'/gsm'})
			},
			goBill:function () {
				this.$router.push({path:'/mybill'})
			},
            goInvoices:function () {
                this.$router.push({path:'/invoices'})
            }

		},
		computed: {
			...mapState({
				language: state => state.language.language
			})
		},

	};
</script>
