<template>
<div class="property">

	<div class="nav_mark"></div>
	<yd-navbar :title="info.title" fixed>
		<router-link to="/shopping" slot="left">
			<span class="back" ></span>
		</router-link>
	</yd-navbar>

	<section class="resolve-box" v-html="content"></section>

	<ul class="pdf-video" :class="{ 'one': isOne, 'two': isTwo}" v-show="btnFlag">
		<li @click="toPDF" v-show="pdfFlag"></li>
		<li @click="toVideo" v-show="videoFlag"></li>
	</ul>

	<section class="buy_foot" >
		<div class="col-5">
			<div><yd-spinner max="99" unit="1" v-model="spinner1"></yd-spinner></div>
		</div>
		<div class="col-5">
			<button type="button" @click="addToCart()">{{language.community.add}}</button>
		</div>
	</section>
	<section class="cart_foot" >
		<div class="col-5">
			<button type="button" @click="goToCart()">{{language.common.shoppingCart}}({{selectedNum}})</button>
		</div>
	</section>

</div>
</template>

<style>
.cart_foot{position: fixed;left:0;bottom:0;width: 100%;background: none;height: 1rem;line-height: 1rem;text-align: center;background: #eee;}
.cart_foot button{width: 200%;height: 1rem;color: #fff;background: #f0c366;border: none;font-size: .3rem;}

.buy_foot{position: fixed;left:0;bottom:20;width: 100%;background: none;height: 1rem;line-height: 1rem;text-align: center;background: #fff;}
.buy_foot button{width: 50%;height: 0.8rem;color: #fff;background: #cb4b45;border: none;font-size: .3rem;}
.buy_detail img{width: 100%;height: 5rem;}
.buy_txt{padding: .3rem;}
.buy_txt h4{font-size: .28rem;color: #333;}
.buy_txt p{font-size: .24rem;color: #666;}
.m-spinner{margin-top: .2rem;}
</style>
<script type="text/babel">
    import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
    export default {
        data() {
            return {
                info:{},
				num:'',
	            spinner1:0,
	            pdfShow:false,
	            videoShow:false,
	            isOne:false,
	            isTwo:false,
	            pdfFlag:false,
	            videoFlag:false,
	            btnFlag:false,
				content:'', 
				selectedNum:0,
				cart: [],
				totalPrice:0
            };
        },
        created:function () {
			this.info = this.$route.query.info;
			this.selectedNum = parseInt(localStorage.Quantity);
            this.$store.dispatch('showLoading');
            var ssrBase = 'https://bird.ioliu.cn/v1/?url=';
			let _this = this;
			if(localStorage.Quantity){
                this.cart = JSON.parse(localStorage.getItem('cart'));
                this.selectedNum = parseInt(localStorage.Quantity);
                this.totalPrice = parseInt(localStorage.totalPrice);
            }
            if(this.info.detail){
                $.get(ssrBase+this.info.detail,function (res) {
                    _this.content = res;
                    _this.$store.dispatch('hideLoading')
                })
			} else {
                this.$store.dispatch('hideLoading')
			}

        },
	    activated:function () {

	    },
        methods: {
            apply: function () {
                // let _this =this
                // let params = {'hotelid':localStorage.HOTELID,'shoppingid':this.info.id,'token':localStorage.TOKEN,'count':this.spinner1};
                // this.$store.dispatch('getShoppingOrder', params).then((res) => {
                // 	if (res.data.code == 0){
                //     	this.$dialog.toast({
                //     	    mes: _this.language.msg.buy_info,
				// 			timeout: 1000
                //     	});
	            //     } else {
                //     	this.$dialog.toast({mes: res.data.msg, timeout: 1000});
				// 	}
                // })
			},
			addToCart:function(){  
				this.selectedNum +=parseInt(this.spinner1);
                this.totalPrice += parseInt(this.info.price)*this.spinner1;
                let alreadyIndex = this.cart.findIndex((item,index)=>{ 
                    return item.id === this.info.id;
                    });  
                if(alreadyIndex==-1){  
                    var cartIndex = this.cart.length;
                    this.cart.push(this.info);
					this.$set(this.cart[cartIndex],'quantity',1);
					this.saveCart();
                    return;
                }else{   
					this.cart[alreadyIndex].quantity +=this.spinner1;
					this.saveCart();
                    return;
				}
			},
	        toPDF:function () {
		        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			        openFile(this.detail.pdf)
		        }else{
			        openPdf(this.detail.pdf)
		        }

	        },
	        toVideo: function () {
		        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
			        openFile(this.detail.video)
		        }else{
			        openVideo(this.detail.video)
		        }
			},
			saveCart: function(){
				localStorage.setItem('cart',JSON.stringify(this.cart));
                localStorage.setItem('Quantity',this.selectedNum);
                localStorage.setItem('totalPrice',parseInt(this.totalPrice)); 
			},
			goToCart:function(){ 
   				this.$router.push({path:'/shoppingCart'});
			}

        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        },
    };;;
</script>
