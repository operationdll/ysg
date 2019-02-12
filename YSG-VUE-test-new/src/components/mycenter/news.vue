<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.news.title" fixed>
            <router-link to="/home" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="news-list">
            <scroller >
                <ul>
                    <li v-for="item in newsList" @click="goDetail(item.url)">
                        <h4>{{item.title}}</h4>
                        <span class="time-show">{{new Date(item.createtime*1000).getFullYear()+'-'+(new Date(item.createtime*1000).getMonth() + 1)+'-'+new Date(item.createtime*1000).getDate()}}</span>
                        <!--<span class="time-show">{{item.createtime}}</span>-->
                        <p>{{item.value}}</p>
                    </li>
                </ul>
            </scroller>
        </div>
    </div>
</template>
<style>
.common_nav_style .m-navbar{z-index: 200}
.news-list ul{padding: .25rem;margin-top: 1.2rem;}
.news-list li{padding: .15rem;border-bottom: 1px solid #ddd;}
.news-list li h4{font-size: .32rem;color: #333;}
.news-list li p{font-size: .28rem;color: #666;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;}
.time-show{font-size: .22rem;color: #999;}
</style>
<script>
	import { mapGetters } from 'vuex'
	import { mapState } from 'vuex'
	export default {
		data() {
			return {
				newsList:[]
			};
		},
        created:function () {
            let _this = this
	        let params = {
	            hotelid: localStorage.HOTELID,
                lang: localStorage.LANGUAGE,
                page:1,
                limit:5,
                token:localStorage.TOKEN
            }
	        this.$store.dispatch('getNews',params).then(function (res) {
                console.log(_this.newsList)
                _this.newsList = res.data.list
            })
        },
		methods: {
			goDetail(url){
                if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                    openFile(url)
                }else{
                    if(url.indexOf('pdf')>0){
                        openPdf(url)

                    } else {
                        openUrl(url,'')
                    }
                }
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
	};
</script>
