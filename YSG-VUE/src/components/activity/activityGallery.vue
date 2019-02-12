<template>
  <div>
    <span class="back" style="position: fixed;z-index:2;width: 0.5rem;height: 0.5rem;background-size: 0.3rem;margin-top:.2rem;" @click="goBack"></span>
    <!-- Header -->
    <div class="header">
        <h1>共{{total}}张活动照片</h1>
    </div>
    <!-- Photo Grid -->
    <ul class="picC">
        <li v-for="(photo,index) in data.photos"><img :src="photo.pic" alt="" @click="detail(index)"/></li>
    </ul>
    </br>
  </div>
</template>

<style scoped>
* {
    box-sizing: border-box;
}

body {
    margin: 0;
    font-family: Arial;
}

.header {
    text-align: center;
    padding: 4px;
}

.header h1{
    font-family:PingFangSC-Medium;
    font-size:20px;
    color:#000000;
    text-align:center;
}

.picC img{
    width:100%;
    height:2.5rem;
}

.picC{
    width:100%;
    margin:1px auto;
    -webkit-column-count:2;
    -moz-column-count:2;
    column-count:2;
    -webkit-column-gap:2px;
    -moz-column-gap:2px;
    -column-gap:2px;
    list-style:none;
}  

.picC li{
    margin-top:2px;
}
</style>
<script>
    export default {
        data() {
            return {
                data:[],
                total:0,
            }
        },
        created:function () {
            this.data = this.$route.query.data;
            this.total = this.data.photos.length;
            let _this = this;
            $(function(){
                $("body").css({background:"white"});
                $("body").scrollTop(0);
                setTimeout(function() {
                    $(".picC li:eq(0)").attr('style','margin-top:0px');
                }, 300);
            });
        },
        methods: {
            goBack:function(){
                this.$router.push({path:'/activity',query:{data:this.data}});
            },
            detail:function(idx){
                idx = idx +1;
                this.$router.push({path:'/photos',query:{data:this.data,backUrl:'/activityGallery',slideIndex:idx}});
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


