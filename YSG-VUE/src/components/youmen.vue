<template>
  <div class="error">
      <!-- <p class="error-txt">参数为:{{ $route.params.p }}!</p> -->
  </div>
</template>
<style scoped>
.error{height: 100%;background: url("../assets/images/guide_bg.jpg")center no-repeat;background-size: 100% auto;}
.error-txt{padding-top: 10rem;text-align: center;font-size: .28rem;color: #999;}
</style>

<script type="text/babel">
	import {getTime} from '../config/mUtils'
	export default {
		data() {
			return {
			};
		},
        created:function () {
			var _this = this;
			let dialog = window.YDUI.dialog;
			let youmenP = this.$route.params.p;
			
			if(youmenP=='raiders'){
				let params = {
					hotelid: localStorage.HOTELID,
					typeid: 37,
					lang: localStorage.LANGUAGE,
					limit: 30,
					page: 0
				};
				this.$store.dispatch('showLoading');
				this.$store.dispatch("getPoiList", params).then(res => {
					let list = res.data.data.list;
					_this.$store.dispatch('hideLoading');
					_this.$router.push({ path: "/raidersDetail", query: { data: list[1]} });
				});
			}else if(youmenP=='test'){
                let params = {
                    hotelid: localStorage.HOTELID,
					lang: localStorage.LANGUAGE,
                    limit:30,
                    page:0
				}
				this.$store.dispatch('showLoading');
                this.$store.dispatch('getActivityList', params).then(function (res) {
					let arrList = res.data.data.list;
					_this.$store.dispatch('hideLoading');
					let data = null;
                    for(var key in arrList){
						if (arrList[key].id == 9){
							data = arrList[key];
							break;
						}
					}
					_this.$router.push({path:'/propertyDetail',query:{info:data}})
				})
			}
        },
		methods: {
		},
		mounted() {
		},
		computed: {

		},
	};
</script>
