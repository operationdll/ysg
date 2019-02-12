<template>
    <div class="judge-box" style="background: #f8f8f8;">
        <div class="nav_mark"></div>
        <yd-navbar :title="language.judge.title" fixed>
            <router-link to="/judge" slot="left">
                <span class="back"></span>
            </router-link>
        </yd-navbar>

        <section class="judge-btn">
            <yd-button size="large" type="primary">保存</yd-button>
        </section>
    </div>
</template>

<style>
</style>

<script type="text/babel">
    import { mapState } from 'vuex'
    export default {
        data() {
            return {

            };
        },
        created: function () {

        },
        methods: {},
        mounted() {
        },
        computed: {
            ...mapState({
                language: state => state.language.language
            })
        }
    }
</script>