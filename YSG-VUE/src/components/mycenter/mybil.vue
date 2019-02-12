<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.myCenter.bill" fixed>
            <router-link to="/home" slot="left" v-if="stateFlag == '1'">
                <span class="back"></span>
            </router-link>
            <router-link :to="{path:'/s_home',query:{hotelid:hotelid}}" slot="left" v-if="stateFlag == '2'">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <section>
            <scroller :on-infinite="infinite">
                <yd-cell-group style="margin-top: 1.3rem;">
                    <yd-cell-item arrow v-for="item in billList" @click.native="showBill(item.pdf)">
                        <span slot="left">{{item.date}}</span>
                        <span slot="right">查看账单</span>
                    </yd-cell-item>
                </yd-cell-group>
            </scroller>
        </section>
    </div>
</template>
<style>
</style>

<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				billList:[],
                stateFlag:''
			};
		},
        created:function () {
            let _this = this;
            this.stateFlag = localStorage.stateFlag
	        let params = {
		        token:localStorage.TOKEN,
                page:1,
                limit:10
	        }
	        this.$store.dispatch('getMyBill', params).then(function (res) {
		        _this.billList =  res.data.list
			});
			
			$(function(){
                $(".navbar-center").css('marginLeft',0);
            });
        },
		computed: {
			...mapState({
				language: state => state.language.language
			})
		},
		methods: {
			showBill(pdfUrl){
				if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
					openFile(pdfUrl)
				}else{
					openPdf(pdfUrl)
				}
            },
			infinite(){
				let _this = this;
				let page = 1;
				let params = {
					token:localStorage.TOKEN,
					page:page,
					limit:10
				}
				this.$store.dispatch('getMyBill', params).then(function (res) {
                    if(res.data.nextPage != '-1'){
                    	_this.billList =  _this.billList.concat(res.data.list)
	                    page++;
                    }

				})
            }
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        }

	};
</script>
