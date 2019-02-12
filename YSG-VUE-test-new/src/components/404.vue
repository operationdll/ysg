<template>
  <div class="error">
      <p class="error-txt">网络不给力，请退出重试!</p>
  </div>
</template>
<style>
.error{height: 100%;background: url("../assets/images/404.png")center no-repeat;background-size: 100% auto;}
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
        },
		methods: {
		},
		mounted() {
		},
		computed: {

		},
	};
</script>
