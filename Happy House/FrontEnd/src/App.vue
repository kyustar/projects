<template>
<div id="app">
    <nav-bar></nav-bar>
    <search-section v-if="getNavFlag == 1"></search-section>
    <notice-board v-else-if="getNavFlag == 3"></notice-board>
    <map-section></map-section>
</div>
</template>

<script>
import {mapGetters} from 'vuex';

import NavBar from './components/NavBar.vue'
import SearchSection from './components/SearchSection.vue'
import NoticeBoard from './components/NoticeBoard.vue'
import MapSection from './components/MapSection.vue'

export default {
    components: {
        NavBar,
        SearchSection,
        NoticeBoard,
        MapSection
    },
    computed: {
        ...mapGetters({
            getNavFlag: 'navFlag'
        })
    }

}
</script>

<style>
#app {
    height: 100%;
}
</style>