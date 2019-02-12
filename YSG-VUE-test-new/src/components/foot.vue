<template>
    <yd-tabbar fixed v-show="footShow" color="#333333" activeColor="#f0c366">
        <yd-tabbar-item :title="language.foot.hotel" link="#" v-if="hotelFlag" @click.native="gohome">
            <img slot="icon" src="../assets/images/icon_foot_1.png">
        </yd-tabbar-item>
        <yd-tabbar-item :title="language.foot.hotel" link="#" active v-else @click.native="gohome">
            <img slot="icon" src="../assets/images/icon_foot_1pre.png">
        </yd-tabbar-item>
        <yd-tabbar-item :title="language.foot.promotion" link="#" v-if="promotionFlag" @click.native="goPromotion">
            <img slot="icon" src="../assets/images/icon_foot_2.png">
        </yd-tabbar-item>
        <yd-tabbar-item :title="language.foot.promotion" active link="#" v-else @click.native="goPromotion">
            <img slot="icon" src="../assets/images/icon_foot_2pre.png">
        </yd-tabbar-item>

        <yd-tabbar-item :title="language.foot.surrounding" link="#" v-if="surrFlag" @click.native="goSurround">
            <img slot="icon" src="../assets/images/icon_foot_3.png">
        </yd-tabbar-item>
        <yd-tabbar-item :title="language.foot.surrounding" active link="#" color="#faf" v-else @click.native="goSurround">
            <img slot="icon" src="../assets/images/icon_foot_3pre.png">
        </yd-tabbar-item>

        <yd-tabbar-item :title="language.foot.community" link="#" v-if="communityFlag" @click.native="goCommuntiy">
            <img slot="icon" src="../assets/images/icon_foot_4.png">
        </yd-tabbar-item>
        <yd-tabbar-item :title="language.foot.community" active link="#" v-else @click.native="goCommuntiy">
            <img slot="icon" src="../assets/images/icon_foot_4pre.png">
        </yd-tabbar-item>
    </yd-tabbar>
</template>
<style>
/*footer .tabbar-active .tabbar-txt{color: #f0c366;}*/
</style>
<script type="text/babel">
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				hotelFlag: true,
				promotionFlag: true,
				surrFlag: true,
				communityFlag: true,
                hotelid: '',
                footShow: true,
                stateFlag: ''  // 当前是正常页面还是查询页面
			};
		},
        created:function () {
		    // 获取状态
            this.stateFlag = localStorage.stateFlag

		    //如果是未登录状态，隐藏底部tabar
		    if (this.stateFlag == 0) {
		        this.footShow = false
            } else {
                this.footShow = true
            }

			this.hotelid = localStorage.HOTELID;
			switch (localStorage.footFlag){
                case '1':
                	this.hotelFlag =false;
                	this.promotionFlag =true;
	                this.surrFlag =true;
	                this.communityFlag =true;
                	break;
                case '2':
	                this.hotelFlag =true;
	                this.promotionFlag =false;
	                this.surrFlag =true;
	                this.communityFlag =true;
                	break;
                case '3':
	                this.hotelFlag =true;
	                this.promotionFlag =true;
	                this.surrFlag =false;
	                this.communityFlag =true;
                	break;
				case '4':
					this.hotelFlag =true;
					this.promotionFlag =true;
					this.surrFlag =true;
					this.communityFlag =false;
					break;
            }

        },
		methods: {
			/**
			 * 按钮跳转方法
             * 设置三种状态： 1.stateFlag = 0  未登录；2.stateFlag = 1 已登录； 3.stateFlag = 2 查询状态
			*/
			gohome:function () {
			    if (this.stateFlag == 1) {
                    this.$router.replace('/home')
                } else {
                    this.$router.replace('/s_home')
                }
			},
            //promotion 菜单
            goPromotion:function () {
                if(localStorage.menuFlag != 'unlogin'){
                    this.$router.replace('/promotion')
                }else {
	                this.$router.replace({path:'/loginforguest', query:{pageType:this.hotelid}})
                }
            },
			//surroundings 菜单
			goSurround:function () {
				if(localStorage.menuFlag != 'unlogin'){
					this.$router.replace('/raiders')
				}else {
					this.$router.replace({path:'/loginforguest', query:{pageType:this.hotelid}})
				}
			},
			//communtiy 菜单
			goCommuntiy:function () {
				if( localStorage.TOKEN ){
					this.$router.replace('/communityLife')
				}else {
					this.$router.replace({path:'/loginforguest', query:{pageType:this.hotelid}})
				}
			}
		},
		computed: {
			...mapState({
				language: state => state.language.language
			})
		},
	};
</script>
