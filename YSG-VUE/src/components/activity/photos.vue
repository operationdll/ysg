<template>
    <div>
        <span class="back" style="position: fixed;z-index:2;width: 0.5rem;height: 0.5rem;background-size: 0.3rem;margin-top:.2rem;" @click="goBack"></span>
        <div class="slidesBg">
            <div class="container">
                <div class="mySlides" v-for="(photo,index) in data.photos">
                    <div class="numbertext">{{index+1}} / {{total}}</div><div class="downloadtext" @click="downLoad(photo.download)">下载</div>
                    <img :src="photo.pic" style="width:100%;height:5.4rem;">
                </div>
                
                <a class="prev" @click="plusSlides(-1)">❮</a>
                <a class="next" @click="plusSlides(1)">❯</a>

                <div class="caption-container">
                </div>

                <div class="row">
                    <div class="column" v-for="(photo,index) in data.photos">
                        <img class="demo cursor" :class='{active: (index+1 == slideIndex)}' :src="photo.pic" style="width:100%;height: 1.3rem;" @click="currentSlide(index+1)">
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
    body {
        font-family: Arial;
        margin: 0;
    }

    * {
        box-sizing: border-box;
    }

    /* Position the image container (needed to position the left and right arrows) */
    .container {
        position: relative;
    }

    /* Hide the images by default */
    .mySlides {
        display: none;
    }

    /* Add a pointer when hovering over the thumbnail images */
    .cursor {
        cursor: pointer;
    }

    /* Next & previous buttons */
    .prev,
    .next {
        cursor: pointer;
        position: absolute;
        top: 40%;
        width: auto;
        padding: 16px;
        margin-top: -50px;
        color: white;
        font-weight: bold;
        font-size: 20px;
        border-radius: 0 3px 3px 0;
        user-select: none;
        -webkit-user-select: none;
        background-color: rgba(0, 0, 0, 0.3);
    }

    /* Position the "next button" to the right */
    .next {
        right: 0;
        border-radius: 3px 0 0 3px;
    }

    /* Number text (1/3 etc) */
    .numbertext {
        color: #f2f2f2;
        font-size: 12px;
        padding: 8px 12px;
        position: absolute;
        top: 0;
    }

    .downloadtext {
        color: #f2f2f2;
        font-size: 18px;
        padding: 8px 12px;
        position: absolute;
        top: 0;
        right: 0;
    }

    /* Container for image text */
    .caption-container {
        text-align: center;
        background-color: #222;
        padding: 2px 16px;
        color: white;
    }

    .row:after {
        content: "";
        display: table;
        clear: both;
    }

    /* Six columns side by side */
    .column {
        float: left;
        width: 16.66%;
    }

    /* Add a transparency effect for thumnbail images */
    .demo {
        opacity: 0.6;
    }

    .slidesBg{
        display: block; /* Hidden by default */
        position: fixed; /* Stay in place */
        z-index: 1; /* Sit on top */
        padding-top: 3rem; /* Location of the box */
        left: 0;
        top: 0;
        width: 100%; /* Full width */
        height: 100%; /* Full height */
        overflow: auto; /* Enable scroll if needed */
        background-color: rgba(0,0,0,0.9); /* Black w/ opacity */
    }

    .slidesBg img {
        vertical-align: middle;
    }

    .active{
        opacity: 1;
    }
</style>
<script>
    export default {
        data() {
            return {
                slideIndex:1,
                backUrl:'',
                data:[],
                total:0,
            }
        },
        created:function () {
            this.backUrl = this.$route.query.backUrl;
            this.data = this.$route.query.data;
            this.total = this.data.photos.length;
            this.slideIndex = this.$route.query.slideIndex;
            let _this = this;
            $(function(){
                $("body").css({background:"white"});
                $("body").scrollTop(0);
                _this.showSlides(_this.slideIndex);
                $(".demo")
            });
        },
        methods: {
            goBack:function(){
                this.$router.push({path:this.backUrl,query:{data:this.data}});
            },
            plusSlides:function(n) {
                this.showSlides(this.slideIndex += n);
            },
            currentSlide:function(n) {
                this.showSlides(this.slideIndex = n);
            },
            showSlides:function(n) {
                var i;
                var slides = document.getElementsByClassName("mySlides");
                var dots = document.getElementsByClassName("demo");
                if (n > slides.length) {this.slideIndex = 1}
                if (n < 1) {this.slideIndex = slides.length}
                for (i = 0; i < slides.length; i++) {
                    slides[i].style.display = "none";
                }
                for (i = 0; i < dots.length; i++) {
                    dots[i].className = dots[i].className.replace(" active", "");
                }
                slides[this.slideIndex-1].style.display = "block";
                dots[this.slideIndex-1].className += " active";
            },
            downLoad(url){
                appDownload(url,'image');
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


