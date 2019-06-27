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
                <li style="border-bottom: 1px solid #eee;">
                    <div class="left">{{language.myCenter.message}}</div>
                    <div class="right"><i class="message"></i></div>
                </li>
            </router-link>
            <!-- <router-link to="/order">
                <li v-show="idType == 2">
                    <div class="left">{{language.myCenter.order}}</div>
                    <div class="right"><i class="notice"></i></div>
                </li>
            </router-link> -->
            <router-link to="/viewOrder">
                <li v-show="idType == 1">
                    <div class="left">{{language.myCenter.viewOrder}}</div>
                    <div class="right"><i class="viewOrder"></i></div>
                </li>
            </router-link>
            <router-link to="/staffOrder">
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
            <li v-show="idType == 2&& isRobotControl == 1" @click="robotControl">
                <div class="left">{{language.myCenter.robotControl}}</div>
                <div class="right"><i class="message"></i></div>
            </li>
            <li v-show="idType == 1" @click="changePwd">
                <div class="left">{{language.myCenter.changePwd}}</div>
                <div class="right"><i class="pwd"></i></div>
            </li>
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
    .user_center .viewOrder{background: url("../../../assets/images/viewOrder.png")center no-repeat;background-size: .3rem;}
    .user_center .pwd{background: url("../../../assets/images/pwd.png")center no-repeat;background-size: .3rem;}
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
                oa_id:'',
                isRobotControl:0
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
            //是否可以控制机器人
            this.isRobotControl = localStorage.robotControl;
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
                                //退出音视频功能
                                logoutYS();
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
                    _this.gsmSrc =  res.data.serviceUrl;
                    if(res.code==2){
                        let msg4 = "token过期，请重新登录!";
                        //判断显示中/英文
                        if(localStorage.LANGUAGE!='zh'){
                            msg4 = "Token expired, please log in again!";
                        }
                        _this.$dialog.toast({mes: msg4, timeout: 3000});
                        _this.$router.replace("/loginforguest");
                        return;
                    }
                    console.log(res.data.serviceUrl);
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
            },
            robotControl:function () {
                this.$router.push({path:'/robotControl'})
            },
            changePwd: function() {
                let _this = this;
                let alobj = new alertLanguage();
                let obj = alobj.getAlertMsg(localStorage.LANGUAGE);
                let dialog = window.YDUI.dialog;
                let title = '请输入密码';
                let msg3 = "token过期，请重新登录!";
                let msg4 = "设置成功!";
                let msg5 = "旧密码输入错误!";
                let cancelNode = "取消";
                //判断显示中/英文
                if(localStorage.LANGUAGE!='zh'){
                    title = 'Please enter your password';
                    msg3 = "Token expired, please log in again!";
                    msg4 = "Successfully set up!";
                    msg5 = "Old password entered incorrectly!";
                    cancelNode = "Cancel";
                }

                let pwd = '';
                let pwd1 = '';
                let isShow = false;
                let titleNode = title;
                let showPwdNode = "显示密码";
                let hidePwdNode = "隐藏密码";
                let title1 = "请输入新密码";
                //判断显示中/英文
                if(localStorage.LANGUAGE!='zh'){
                    showPwdNode = "Show password";
                    hidePwdNode = "Hide password";
                    title1 = "Please enter a new password";
                }
                $("#shoppingPwd").show();
                $("#shoppingPwd .m-keyboard").animate({}, "slow", function(){
                    $(this).css({
                        '-webkit-transform': 'translate(0,0)',
                        '-moz-transform': 'translate(0,0)',
                        '-ms-transform': 'translate(0,0)',
                        '-o-transform':'translate(0,0)',
                        'transform': 'translate(0,0)'
                    });
                });
                $("#shoppingPwd .keyboard-head strong").html(titleNode);
                $("#shoppingPwd .J_ShowPwd").html(showPwdNode);
                $("#shoppingPwd .J_Cancel").html(cancelNode);
                $("#shoppingPwd .J_FillPwdBox li").html("");
                $("#shoppingPwd .keyboard-error").html("");
                //点击密码数字
                $("#shoppingPwd .J_Nums").unbind('click').click(function(){
                    if(pwd.length==6){
                        return;
                    }
                    let num = $(this).html();
                    pwd = pwd+num;
                    let numLength = pwd.length;
                    if(isShow){
                        $("#shoppingPwd .J_FillPwdBox li:eq("+(numLength-1)+")").html(num);
                    }else{
                        $("#shoppingPwd .J_FillPwdBox li:eq("+(numLength-1)+")").html("<i style='display:block;width:6px;height:6px;border-radius:50%;background-color:#000;'></i>");
                    }
                    if(numLength==6){
                        if(pwd1==''){
                            //检查密码
                            dialog.loading.open('Loading');
                            let pinParams = {
                                token: localStorage.TOKEN,
                                pin: pwd
                            };
                            _this.$store.dispatch('checkPin', pinParams).then(function (res) {
                                dialog.loading.close();
                                $("#shoppingPwd .J_FillPwdBox li").html("");
                                if(res.code == 0){
                                    $("#shoppingPwd .keyboard-head strong").html(title1);
                                    pwd1 = pwd;
                                }else if(res.code == 1){
                                    //dialog.alert(msg3);
                                    _this.$router.replace("/loginforguest");
                                }else if(res.code == 2){
                                    dialog.alert(msg5);
                                }else{
                                    dialog.alert(res.msg);
                                }
                                pwd = '';
                            });
                        }else{
                            dialog.loading.open('Loading');
                            //设置密码
                            let setParams = {
                                token: localStorage.TOKEN,
                                old_pin:pwd1,
                                pin: pwd
                            };
                            _this.$store.dispatch('setPin', setParams).then(function (res) {
                                if(res.code == 0){
                                    localStorage.PWD = pwd;
                                    dialog.alert(msg4);
                                    $("#shoppingPwd .m-keyboard").animate({}, "slow", function(){
                                        $(this).css({
                                            '-webkit-transform': 'translateY(100%)',
                                            '-moz-transform': 'translateY(100%)',
                                            '-ms-transform': 'translateY(100%)',
                                            '-o-transform':'translateY(100%)',
                                            'transform': 'translateY(100%)'
                                        });
                                        setTimeout(() => {
                                            $("#shoppingPwd").hide();
                                        }, 300);
                                    });
                                }else if(res.code == 1){
                                    //dialog.alert(msg3);
                                    _this.$router.replace("/loginforguest");
                                }else{
                                    dialog.alert(res.msg);
                                }
                                dialog.loading.close();
                            });
                        }
                    }
                });
                //点击重置按钮
                $("#shoppingPwd .J_Cancel").unbind('click').click(function(){
                    pwd1 = '';
                    $("#shoppingPwd .keyboard-head strong").html(titleNode);
                    pwd = '';
                    $("#shoppingPwd .J_FillPwdBox li").html("");
                    $("#shoppingPwd .keyboard-error").html("");
                    $("#shoppingPwd .m-keyboard").animate({}, "slow", function(){
                        $(this).css({
                            '-webkit-transform': 'translateY(100%)',
                            '-moz-transform': 'translateY(100%)',
                            '-ms-transform': 'translateY(100%)',
                            '-o-transform':'translateY(100%)',
                            'transform': 'translateY(100%)'
                        });
                        setTimeout(() => {
                            $("#shoppingPwd").hide();
                        }, 300);
                    });
                });
                //点击删除按钮
                $("#shoppingPwd .J_Backspace").unbind('click').click(function(){
                    if(pwd.length==0){
                        return;
                    }
                    pwd = pwd.substring(0,pwd.length-1);
                    $("#shoppingPwd .J_FillPwdBox li:eq("+pwd.length+")").html("");
                });
                //点击显示密码按钮
                $("#shoppingPwd .J_ShowPwd").unbind('click').click(function(){
                    isShow = !isShow;
                    if(isShow){
                        for(let i=0;i<pwd.length;i++){
                            $("#shoppingPwd .J_FillPwdBox li:eq("+i+")").html(pwd[i]);
                        }
                        $(this).html(hidePwdNode);
                    }else{
                        for(let i=0;i<pwd.length;i++){
                            $("#shoppingPwd .J_FillPwdBox li:eq("+i+")").html("<i style='display:block;width:6px;height:6px;border-radius:50%;background-color:#000;'></i>");
                        }
                        $(this).html(showPwdNode);
                    }
                });
            }
		},
		computed: {
			...mapState({
				language: state => state.language.language
			})
		},

	};
</script>
