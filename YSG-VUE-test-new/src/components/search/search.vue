<template>
    <div class="search">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.home.findResidence" fixed>
                <span class="close" slot="left" @click="goBack"></span>
        </yd-navbar>
        <div class="search_box" style="margin-top: 1rem;">
            <section class="s_picker">
                <ul>
                    <li>
                        <div class="left">
                            <div class="c_picker" @click="isCountryShow = true">{{country}}</div>
                            <popup-picker :data="countries" :show="isCountryShow" :columns="1" @on-hide="isCountryShow = false"></popup-picker>
                        </div>
                        <div class="right">
                            <div class="c_picker" style="float: right;" @click="isCityShow = true">{{city}}</div>
                            <popup-picker :data="cities" :show="isCityShow" :columns="1" @on-hide="isCityShow = false" @on-change="onCityChange"></popup-picker>
                        </div>
                    </li>
                    <li>
                        <div class="c_picker" style="width: 100%;" @click="isHotelShow = true">{{hotel}}</div>
                        <popup-picker :data="hotelList" :show="isHotelShow" :columns="1" @on-hide="isHotelShow = false" @on-change="onHotelChange"></popup-picker>
                    </li>
                </ul>
                <yd-button size="large" bgcolor="#01519e" @click.native="searchHotel" color="#FFF">{{language.home.search}}</yd-button>
                <p>{{language.func.search}}</p>
            </section>
        </div>
    </div>
</template>
<style>
</style>
<script type="text/babel">
	import { PopupPicker, XButton } from 'vux'
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
	export default {
		data() {
			return {
				menuFlag:false,
                cities:[],
                countries:[{name:'China',value:'China'}],
                hotelList:[],
                allHotel:[],
                country:'China',
                city:'All Cities',
                hotel:'All Properties',
                hotelId:'-1',
                isCityShow:false,
                isCountryShow:false,
                isHotelShow:false,
                transletLan:{},
                langFlag:0,
                saveList:[]
			}
		},
        created:function () {
		    //设置中英文表示
            if(localStorage.LANGUAGE == 'en') {
                this.langFlag = 0
            } else {
                this.langFlag = 1
            }
		    //根据集团id获取物业列表
            let params = {groupid:localStorage.groupid};
            this.$store.dispatch('getGroupListById', params).then((res) => {
                //初始化hotelList
                this.allHotel = res.data.list;
                //假如全部
                var allName;
                if(localStorage.LANGUAGE == 'en') {
                    allName = 'All properties'
                    this.hotel = 'All properties'
                    this.city = 'All Cities'
                    this.country = 'China'
                    this.countries=[{name:'China',value:'China'}]
                } else {
                    allName = '全部物业'
                    this.hotel = '全部物业'
                    this.city = '全部城市'
                    this.country = '中国'
                    this.countries=[{name:'中国',value:'中国'}]
                }
                this.hotelList.push({name:allName,value:'0'});

                for (var key in res.data.list){
                    if(localStorage.LANGUAGE == 'en') {
                        this.hotelList.push({name:res.data.list[key].nameEn,value:res.data.list[key].hotelId});
                    } else {
                        this.hotelList.push({name:res.data.list[key].name,value:res.data.list[key].hotelId});
                    }
                }

                this.saveList = this.hotelList

                //将所有的城市导入
                var allCity;
                if(localStorage.LANGUAGE == 'en') {
                    allCity = 'All City'
                } else {
                    allCity = '全部城市'
                }

                this.cities.push({name:allCity, value:'0'});
                for (var key in this.allHotel){
                    var data;
                    if(localStorage.LANGUAGE == 'en') {
                        data = this.allHotel[key].cityEnName
                    } else {
                        data = this.allHotel[key].cityName+'市'
                    }

                    let flag = true;
                    // 校验
                    for(var item in this.cities) {
                        if(this.cities[item].name == data){
                            flag = false;
                            break;
                        }
                    }
                    if (flag){
                        this.cities.push({name:data,value:data});
                    }
                }
            }, (error) => {
                reject(error)
            });

	    },
		methods: {
            //城市选择
            onCityChange:function (changeKey) {
                if (changeKey == 0){
                    //选择全部城市
                    if(localStorage.LANGUAGE == 'en') {
                        this.city = 'All Cities';
                    } else {
                        this.city = '全部城市';
                    }
                    this.hotelList = this.saveList

                }else{
                    this.city = changeKey[0];

                    //当市改变时 同步更新hotelList
                    this.hotelList = [];
                    var allName;
                    if(localStorage.LANGUAGE == 'en') {
                        allName = 'All properties'
                    } else {
                        allName = '全部物业'
                    }
                    this.hotelList.push({name:allName,value:'0'});
                    if(localStorage.LANGUAGE == 'en') {
                        for (var key in this.allHotel){
                            console.log(this.allHotel[key].cityEnName +'====='+changeKey)
                            if (this.allHotel[key].cityEnName == changeKey){
                                console.log(this.allHotel[key].cityEnName +'====='+changeKey)
                                this.hotelList.push({name:this.allHotel[key].nameEn,value:this.allHotel[key].hotelId});
                            }
                        }
                        console.log(this.hotelList)
                    } else {
                        for (var key in this.allHotel){
                            if (this.allHotel[key].cityName+'市'  == changeKey){
                                console.log(this.allHotel[key].cityName +'市'+'====='+changeKey)
                                this.hotelList.push({name:this.allHotel[key].name,value:this.allHotel[key].hotelId});
                            }
                        }
                    }
                }
                if(localStorage.LANGUAGE == 'en') {
                    this.hotel  = 'All properties'

                } else {
                    this.hotel  = '全部物业'
                }
                this.hotelId = '-1';
            },
            //物业选择  中英文
            onHotelChange:function (changeKey) {
                if(localStorage.LANGUAGE == 'en'){
                    for (var key in this.allHotel){
                        if(this.allHotel[key].hotelId == changeKey){
                            this.hotel = this.allHotel[key].nameEn;
                            this.hotelId = this.allHotel[key].hotelId;
                        }
                    }
                } else {
                    for (var key in this.allHotel){
                        if(this.allHotel[key].hotelId == changeKey){
                            this.hotel = this.allHotel[key].name;
                            this.hotelId = this.allHotel[key].hotelId;
                        }
                    }
                }

            },
            //搜索跳转
            searchHotel:function () {
                this.$router.push({path:'/search/searchResult',query:{hotelId:this.hotelId,city:this.city}});
            },
			//判断搜索来源,回退到原来页面
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
					case 's_home':
						this.$router.push('/unlogin')
						break;
				}
			}
		},
        components:{
            PopupPicker
        },
        computed: {
            ...mapState({
                groupListbyId: state => state.home.groupListbyId,
                language: state => state.language.language
            })
        },
        mounted:function () {
            //一级页面falg
            isHomePage(0)
        },
	}
</script>
