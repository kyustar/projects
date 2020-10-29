<template>
<div id="district-result">
    <ul v-if="getDistrictList.length != 0">
        <li v-for="district in getDistrictList" v-bind:key="district.districtCode">
            <a v-on:click.prevent="aptList(district.districtCode)">{{district.district}}</a>
        </li>
    </ul>
    <div v-else>검색결과가 없습니다.</div>
    <paginate v-if="getDistrictList.length != 0"
        v-model="$store.state.DR_page"
        :page-count="Math.floor((getDistrictCount - 1) / 10) + 1"
        :click-handler="updateDistrictList"
        :prev-text="'Prev'"
        :next-text="'Next'"
        :container-class="'paginate'"
        :active-class="'highlighted'">
    </paginate>
</div>  
</template>

<script>
import {mapGetters, mapActions} from 'vuex'
import Paginate from 'vuejs-paginate'

export default {
    components: {
        Paginate
    },
    methods: {
        ...mapActions({
            updateDistrictList: 'updateDistrictList',
            aptList: 'aptList'
        })
    },
    computed: {
        ...mapGetters({
            getDistrictList: 'districtList',
            getDistrictCount: 'districtCount'
        })
    }
}
</script>

<style>
#district-result{
    height: 100%;
    overflow: hidden;
}

#district-result ul.paginate {
    margin: 10px auto;
    padding: 0;
    text-align: center;
    user-select:none;
}

#district-result .highlighted a{
    color: #007acc !important;
}

#district-result .paginate li {
    display: inline;
    margin: 0 10px;
    list-style: none;
}
</style>