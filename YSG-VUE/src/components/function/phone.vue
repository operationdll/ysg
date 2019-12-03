<template>
    <div class="phone">
        <div class="nav_mark"></div>
        <v-back v-bind:title="language.func.tel" home="home" style="position:fixed;margin-top: -1.2rem;"></v-back>
        <div class="phone_box"style="margin-top: 1.2rem;">
            <section v-for="item in telList">
                <h4>{{item.title}}</h4>
                <div v-for="tel in item.telList" @click="call(tel.tel)">
                    <h3>{{tel.title}}</h3>
                    <p> <i class="icon-phone"></i> <a :href="('tel:'+(tel.tel))">{{tel.tel}}</a></p>
                </div>
            </section>
        </div>
    </div>
</template>

<style scoped>
    .phone .phone_box{padding: 0 .2rem;color: #454545;padding-bottom: .6rem;background: #fff;}
    .phone .phone_box h4{font-size: .3rem;border-bottom: 1px solid #eee;padding: .2rem 0;margin-top: .2rem;}
    .phone .phone_box div{padding:.2rem 0;border-bottom: 1px solid #eee;}
    .phone .phone_box div h3{font-weight: normal;font-size: .3rem;}
    .phone .phone_box div p{font-size: .32rem;color: #3978b3;}
    .icon-phone{display: inline-block;width: .3rem;height: .3rem;background: url("../../assets/images/icon_3.png")center no-repeat;background-size: .3rem;vertical-align: middle;margin-right: .2rem;}
</style>

<script type="text/babel">
    import { mapState } from 'vuex'
    import back from "../back";

	export default {
		data() {
			return {
				telList:[],
                stateFlag:''
			};
		},
        created:function () {
            this.stateFlag = localStorage.stateFlag
            let _this = this;
            let params;
            if(localStorage.TOKEN){
                params = {'token':localStorage.TOKEN,'hotelid':localStorage.HOTELID,lang:localStorage.LANGUAGE};
            } else {
                params = {'hotelid':localStorage.HOTELID,lang:localStorage.LANGUAGE};
            }

            this.$store.dispatch('getHotelTelList', params).then(function (res) {
                _this.telList = res.data.data.list;
            });

            $(function(){
                $(".navbar-center").css('marginLeft',0);
            });

        },
		methods: {
		    call:function (phone) {
//		        alert(phone)
                window.location.href = "tel:"+phone;
            }
		},
        computed: {
            ...mapState({
                language: state => state.language.language,
                hotelTelList: state => state.translate.hotelTelList
            })
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
        components: {
            "v-back": back
        }
	};
</script>
