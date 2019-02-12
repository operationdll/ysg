<template>
<div class="property">
	<div class="nav_mark"></div>
	<yd-navbar :title="language.community.sign" fixed>
			<span class="back" slot="left" @click="goBack"></span>

	</yd-navbar>
	<section class="top_nav">

		<img style="height: 4rem;width: 100%;" src="../../assets/images/promotiomList.png" alt="">

		<yd-cell-group >
			<yd-cell-item>
				<span slot="left">{{language.submitForm.name}}：</span>
				<input slot="right" type="text" placeholder="请输入姓名" v-model="name">
			</yd-cell-item>
			<yd-cell-item>
				<span slot="left">{{language.submitForm.tel}}：</span>
				<input slot="right" type="number" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" placeholder="请输入手机号" v-model="phone">
			</yd-cell-item>
			<yd-cell-item>
				<span slot="left">{{language.submitForm.number}}：</span>
				<input slot="right" type="number" onkeyup="this.value=this.value.replace(/\D/g,'')" onafterpaste="this.value=this.value.replace(/\D/g,'')" placeholder="请输入报名人数" v-model="num">
			</yd-cell-item>
			<yd-cell-item>
				<yd-textarea slot="right" :placeholder="language.submitForm.bz" maxlength="100" v-model="remark"></yd-textarea>
			</yd-cell-item>
		</yd-cell-group>
	</section>
	<section class="foot_btn_box">
		<button class="foot_btn" type="button" @click="apply">{{language.submitForm.subBtn}}</button>
	</section>

</div>
</template>

<style>
.foot_btn_box{position: fixed;left:0;bottom: 0;width: 100%;background: none;}
.foot_btn_box .foot_btn{width: 100%;height: 1rem;color: #fff;background: #f0c366;border: none;font-size: .3rem;}
</style>
<script >
    import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                info:{},
				name:'',
				phone:'',
				num:'',
				remark:'',
            };
        },
        created:function () {
            this.info = this.$route.query.info;
        },
        methods: {
            apply: function () {
                if (!this.name){
                    this.$dialog.toast({mes: this.language.msg.empty_name, timeout: 1000});
                    return;
				}else if(!this.phone){
                    this.$dialog.toast({mes: this.language.msg.empty_phone, timeout: 1000});
                    return;
				}else if(!this.num){
                    this.$dialog.toast({mes: this.language.msg.empty_num_par, timeout: 1000});
                    return;
				}
                let params = {'name':this.name,'phone':this.phone,'hotelid':localStorage.HOTELID,'activityid':this.info.id,'token':localStorage.TOKEN,'ordercount':this.num,'remark':this.remark};
                this.$store.dispatch('ActivityOrder', params).then((res) => {
	                this.$dialog.toast({mes: this.language.submitForm.alertTxt, timeout: 1000});
                }).catch((res) => {
                });
            },
			goBack:function () {
                this.$router.push({path:'/propertyDetail',query:{info:this.info}});
            }

        },
      	mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components:{

        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        }
    };
</script>
