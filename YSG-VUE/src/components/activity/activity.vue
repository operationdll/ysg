<template>
  <div>
    <span class="back" style="position: fixed;z-index:2;width: 0.5rem;height: 0.5rem;background-size: 0.3rem;margin-top:.2rem;" @click="goBack"></span>
    <img :src="imgUrl" style="width:100%;height:4rem;"/>
    <div id="header"></div>
    <div class="subTitle">
        PHOTOS<a href="#" @click="showAll">全部{{total}}张</a>
    </div>
    <div class="swiper-container" style="width: 100%">
        <div class="swiper-wrapper">
            <div class="swiper-slide" v-for="(photo,index) in data.photos">
                <img :src="photo.pic" style="width:183px;height:101px;" @click="detail(index)">
            </div>
        </div>
    </div>
    <div id="footer"></div>
  </div>
</template>

<style scoped>
.firstTitle{
    font-family:Avenir-Medium;
    font-size:28px;
    color:#4a4a4a;
    text-align:left;
    padding: .2rem;
}

.swiper-slide{
    width:"185px";
}

.subTitle{
    font-family:Avenir-Book;
    font-size:20px;
    color:#4a4a4a;
    text-align:left;
    padding: .2rem;
}

.subTitle a{
    font-family:PingFangSC-Regular;
    font-size:14px;
    color:#137ce2;
    text-align:left;
    margin-right:0px;
    margin-top: 6px;
    float: right;
}

</style>
<script>
    export default {
        data() {
            return {
                imgUrl:'',
                data:[],
                total:0,
            }
        },
        created:function () {
            this.data = this.$route.query.data;
            this.total = this.data.photos.length;
            this.imgUrl = this.data.pic;
            let _this = this;
            $(function(){
                setTimeout(function() {
                    let pn=($(window).width()/183+0.05).toFixed(1);
                    new Swiper(".swiper-container", {
                        pagination: ".swiper-pagination",
                        slidesPerView: pn,
                        paginationClickable: true,
                        spaceBetween: 0
                    });
                    $("#header").html(_this.data.header);
                    $("#footer").html(_this.data.footer);
                }, 300);
                $("body").css({background:"white"});
                $("body").scrollTop(0);
            });
        },
        methods: {
            goBack:function(){
                this.$router.push('/propertyActivity');
            },
            detail:function(idx){
                idx = idx +1;
                this.$router.push({path:'/photos',query:{data:this.data,backUrl:'/activity',slideIndex:idx}});
            },
            showAll:function(){
                this.$router.push({path:'/activityGallery',query:{data:this.data}});
            }
        },
        mounted:function () {
            
        },
        components: {
        },
        computed: {
            
        },
    };
</script>


