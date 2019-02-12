<template>
    <div class="common_nav_style" style="height: 100%;">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.map.title" fixed>
            <router-link :to="{path:'/home'}" slot="left" v-if="stateFlag == '1'">
                <span class="back"></span>
            </router-link>
            <router-link :to="{path:'/s_home',query:{hotelid:hotelid}}" slot="left" v-if="stateFlag == '2'">
                <span class="back"></span>
            </router-link>
        </yd-navbar>
        <div class="map-mark">
            <div class="col-8">
                <h4>{{home.data.name}}</h4>
                <p>{{home.data.address}}</p>
            </div>
            <div class="col-2 voice" @click="audioControl">
                <audio id="voiceControl" :src="home.data.voice"></audio>
            </div>
        </div>
        <div class="map-content" id="container" style="height: 100%">
            <el-amap :vid="'amap-vue'" :center="center" :zoom="zoom">
                <el-amap-marker v-for="marker in markers" :position="marker.position" :events="marker.events" :visible="marker.visible" :draggable="marker.draggable" :icon="marker.icon"></el-amap-marker>
            </el-amap>
        </div>
    </div>
</template>

<style>
    .map-mark{position:fixed;bottom: 0;left:0;width: 100%;height: 2rem;background: #fff;z-index: 999;padding: .2rem;}
    .map-mark h4{font-size: .28rem;color:#333;overflow: hidden;text-overflow: ellipsis;white-space: nowrap;}
    .map-mark p{font-size: .24rem;color:#666;display: -webkit-box;-webkit-box-orient: vertical;-webkit-line-clamp: 3;overflow: hidden;text-overflow: ellipsis;}
    .voice{background: url("../../assets/images/icon-voice.png")80% center no-repeat;background-size: .6rem;height: 100%;}
</style>

<script type="text/babel">
    import { mapState } from 'vuex'
    export default {
        data() {
            return {
                center: [],
                zoom:14,
                markers: [],
                stateFlag:''
            };
        },
        created: function () {
            let _this = this
            this.stateFlag = localStorage.stateFlag
            this.center = [_this.home.data.lng,_this.home.data.lat]
            this.markers = [
                {
                    position: [_this.home.data.lng,_this.home.data.lat],
                    events: {
                        click: (e) => {
                            openMap(e.lnglat.lng,e.lnglat.lat)
                        },
                        dragend: (e) => {
                            this.markers[0].position = [e.lnglat.lng, e.lnglat.lat];
                        }
                    },
                    visible: true,
                    draggable: false,
                    icon: new AMap.Icon({  //复杂图标
                        size: new AMap.Size(110, 52),//图标大小
                        image: require('../../assets/images/icon-guide.png'), //大图地址
                        imageOffset: new AMap.Pixel(0, 0)//相对于大图的取图位置
                    })
                }
            ]
        },
        methods: {
            audioControl:function () {
                var audio = document.getElementById('voiceControl')
                if(audio.paused){
                    audio.play()
                } else {
                    audio.pause()
                }
            }
        },
        mounted() {
        },
        computed: {
            ...mapState({
                language: state => state.language.language,
                home: state => state.home.home,
                hotelDetail: state => state.home.hotelDetail
            })
        },
    }
</script>