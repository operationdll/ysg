<template>
  <div style="text-align: center;padding-top: 4rem;">
      <p>wqertytuyuwerty</p>
      <yd-button type="hollow" @click.native="back">back</yd-button>
  </div>
</template>

<style>
    .hello{text-align: center;padding-top: 3rem;}
    .hello img{width: 1.8rem;margin: 0 auto;}
    .hello h4{font-size: .36rem;margin-top: .5rem;font-weight: normal;color: #666;}
</style>

<script type="text/babel">
	import { mapGetters } from 'vuex'
	export default {
		data() {
			return {
			};
		},
        created:function () {

        },
		methods: {
			back:function () {
				this.$store.dispatch('getDirect','back')
				this.$router.replace('/')
			}

		},
		mounted() {

		}
	};
</script>
