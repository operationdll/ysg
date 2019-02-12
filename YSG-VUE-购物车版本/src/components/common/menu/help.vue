<template>
	<div class="common_nav_style">
		<div class="nav_mark"></div>
		<yd-navbar title="HELP" fixed>
			<span slot="left" class="back" @click="goBack"></span>
		</yd-navbar>
		<div style="padding-top: 1.5rem;">
			<ul class="help-list">
				<li v-for="item in helpList">
					<h4>{{item.type}}</h4>
					<yd-cell-group>
						<yd-cell-item arrow type="label" v-for="dataList in item.list" @click.native="openView(dataList.help,dataList.title)">
							<span slot="left">{{dataList.title}}</span>
						</yd-cell-item>
					</yd-cell-group>
				</li>
			</ul>

		</div>
	</div>
</template>

<style>
.help-list h4{font-size: .32rem;padding: .2rem .3rem;background: #f2f2f2;}
</style>
<script type="text/babel">
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
			    helpList:[]
			};
		},
		created:function () {
		    let _this = this;
			this.$store.dispatch('help',{groupid:localStorage.groupid}).then(function (res) {
				_this.helpList = res.data
				console.log(_this.helpList)
            })
		},
		methods: {
			//判断用户是否登录
			goBack:function () {
				let flag = localStorage.menuFlag;
				switch (flag){
					//已登录首页
					case 'home':
						this.$router.push('/home')
						break;
					//未登录首页
					case 'unlogin':
						this.$router.push('/unlogin')
						break;
					//引导页
					case 'guide':
						this.$router.push('/guide')
						break;
					//查询特殊首页
					case 's_home':
						this.$router.push('/s_home')
						break;
				}
			},
			openView:function (url,title) {
				this.$router.push({path:'/helpDetail',query:{url:url,title:title}})
            }

		},
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
		components:{

		},
	};
</script>
