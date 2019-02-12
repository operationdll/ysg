<template>
  <div class="error">
      <p class="error-txt">该页面暂不支持PC，请通过手机浏览器打开!</p>
  </div>
</template>
<style>
.error{height: 100%;background: url("../assets/images/construction.jpg")center no-repeat;background-size: 100% auto;}
.error-txt{padding-top: 10rem;text-align: center;font-size: .58rem;color: #000;}
</style>

<script type="text/babel">
	import {getTime} from '../config/mUtils'
	export default {
		data() {
			return {
			};
		},
        created:function () {
        },
		methods: {
		},
		mounted() {
		},
		computed: {

		},
	};
</script>
