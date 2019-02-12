<!--<template>-->
    <!--<div>-->
        <!--<span class="back" slot="left" @click="goBack" ></span>-->
        <!--<div>-->
            <!--<input type="number" v-model.number="page"> /{{numPages}}-->
            <!--<button @click="before">上一页</button>-->
            <!--<button @click="next">下一页</button>-->
            <!--<pdf :src="url" :page="page" @numPages="numPages = $event" style="width:75%"></pdf>-->
        <!--</div>-->
    <!--</div>-->


<!--</template>-->

<!--<style>-->

<!--</style>-->

<!--<script type="text/babel">-->
    <!--//http://storage.easyiservice.com/iservicev2/pdf/201705/ae60bb366117879d284ac7e319424465.pdf-->
    <!--import pdf from 'vue-pdf'-->
    <!--export default {-->
        <!--data() {-->
            <!--return {-->
                <!--url:'',-->
                <!--info:{},-->
                <!--backUrl:'',-->
                <!--show: true,-->

                <!--page: 1,-->
                <!--numPages: 0,-->
            <!--};-->
        <!--},-->
        <!--created:function () {-->
<!--//            this.url = this.$route.query.url;-->
<!--//            alert(this.url)-->
            <!--this.info = this.$route.query.info;-->
            <!--this.url = this.info.pdf;-->
            <!--this.backUrl = this.$route.query.backUrl;-->
<!--//            alert(this.url)-->

        <!--},-->
        <!--methods: {-->
            <!--goBack:function () {-->
<!--//                this.$router.back();-->
                <!--this.$router.push({path:'/'+this.backUrl,query:{info:this.info}});-->
            <!--},-->
            <!--before:function () {-->
                <!--if (this.page > 1)-->
                <!--this.page = this.page - 1;-->
            <!--},-->
            <!--next: function () {-->
                <!--if (this.page < this.numPages)-->
                    <!--this.page = this.page + 1;-->
            <!--}-->
        <!--},-->
        <!--components: {-->
            <!--pdf-->
        <!--}-->

    <!--};-->
<!--</script>-->
