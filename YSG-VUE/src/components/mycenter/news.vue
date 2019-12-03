<template>
    <div class="common_nav_style">
        <div class="nav_mark"></div>
        <v-back v-bind:title="language.news.title" home="home" style="position:fixed;"></v-back>
        <div class="news-list">
            <div id="myscroll">
                <ul style="background:white;margin-top:1rem;">
                    <li v-for="item in newsList" @click="goDetail(item.url)">
                        <table>
                            <tr>
                                <td style="width:70px;">
                                    <img slot="icon" :src="item.icon" style="width:40px;height:40px;" />
                                </td>
                                <td>
                                    <h4>{{item.title}}</h4>
                                    <span class="time-show">{{item.createtime}}</span>
                                    <p>{{item.value}}</p>
                                </td>
                            </tr>
                        </table>
                    </li>
                    <li style="border:0px" v-show="isMore">
                        <div style="width:100%;height:130px;text-align:center;" v-if="isZH">
                            没有更多数据
                        </div>
                        <div style="width:100%;height:130px;text-align:center;" v-if="!isZH">
                            No more data
                        </div>
                    </li>
                </ul>
            </div>
        </div>
    </div>
</template>
<style scoped>
.common_nav_style .m-navbar{z-index: 200}
.news-list ul{padding: .25rem;}
.news-list li{padding: .15rem;border-bottom: 1px solid #ddd;}
.news-list li h4{font-size: .32rem;color: #333;}
.news-list li p{font-size: .28rem;color: #666;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;}
.time-show{font-size: .22rem;color: #999;}
</style>
<script>
	import { mapGetters } from 'vuex'
    import { mapState } from 'vuex'
    import back from "../back";

	export default {
		data() {
			return {
                newsList:[],
                idType:0,
                isZH:true,
                total:0,
                isMore:false,
                page:1
			};
		},
        created:function () {
            //判断显示中/英文
            if(localStorage.LANGUAGE!='zh'){
                this.isZH = false;
            }
            let _this = this
	        let params = {
	            hotelid: localStorage.HOTELID,
                lang: localStorage.LANGUAGE,
                page:_this.page,
                token:localStorage.TOKEN
            }
            //员工消息
            this.idType = localStorage.idType;
            if(2==localStorage.idType){
                _this.$store.dispatch('getStaffAppMsgList', params).then(function (res) {
                    _this.newsList = res.data.list;
                    _this.total = res.data.total;
                    if(_this.total<11){
                        _this.isMore = true;
                    }
                });
            }else{//住户消息
                _this.$store.dispatch('getNews',params).then(function (res) {
                    _this.newsList = res.data.list;
                    _this.total = res.data.total;
                    if(_this.total<11){
                        _this.isMore = true;
                    }
                });
            }
            $(function(){
                $(".navbar-center").css('marginLeft',0);
                //获取显示器屏幕高度
                let h = window.screen.height +"px";
                $("#myscroll").css({"overflow":"auto","height":h,"marginRight":"-20px","paddingRight":"20px"});
                $("#myscroll").scrollTop(0);
                $("#myscroll").scroll(function(){
                    if((_this.page*10)<=_this.total){
                        let bodyTop = $("#myscroll").scrollTop();
                        let winH = $(window).height();
                        let bodyH = $("#myscroll")[0].scrollHeight;
                        let rollH = bodyTop + winH + 200;
                        if((rollH>=bodyH)&&!_this.isMore){
                            _this.page = _this.page+1;
                            let params = {
                                hotelid: localStorage.HOTELID,
                                lang: localStorage.LANGUAGE,
                                page:_this.page,
                                token:localStorage.TOKEN
                            }
                            //员工消息
                            this.idType = localStorage.idType;
                            if(2==localStorage.idType){
                                _this.$store.dispatch('getStaffAppMsgList', params).then(function (res) {
                                    res.data.list.forEach(function(element) {
                                        _this.newsList.push(element);
                                    });
                                    if((_this.page*10)>=_this.total){
                                        _this.isMore = true;
                                    }
                                });
                            }else{//住户消息
                                _this.$store.dispatch('getNews',params).then(function (res) {
                                    res.data.list.forEach(function(element) {
                                        _this.newsList.push(element);
                                    });
                                    if((_this.page*10)>=_this.total){
                                        _this.isMore = true;
                                    }
                                });
                            }
                        }
                    }
                });
            });
        },
		methods: {
			goDetail(url){
                if(url.substring(0,4)==="http"){
                    if(url.indexOf('#')==-1){
                        if (/(iPhone|iPad|iPod|iOS)/i.test(navigator.userAgent)) {
                            openFile(url);
                        }else{
                            openUrl(url,'');
                        }
                    }else{
                        this.$router.push(url.substring(url.indexOf('#')+1));
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
        components: {
            "v-back": back
        }
	};
</script>
