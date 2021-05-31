<template>
<div id="apt-result">
    <ul v-if="getAptList.length != 0">
        <li v-for="apt in getAptList" v-bind:key="apt.aptId">
            <div class="apt-wrap" @mouseover="setMouseOverAptId(apt.aptId)" @mouseout="setMouseOverAptId(0)" @click="clickHandler">
                <div class="apt-name" :class="{highlighted: apt.aptId == getMouseOverAptId, error: getErrorList.includes(apt.aptId)}">
                    {{apt.aptName}} 
                    <img class="caution-icon" v-if="getErrorList.includes(apt.aptId)" src="../assets/icon/caution_red.png" title="좌표변환에 실패하여 지도에 표시되지 않습니다. 거래내역 조회는 가능합니다."> 
                </div>
                <div class="apt-year">{{apt.buildYear}}</div>

                <div class="apt-address">{{apt.district}} {{apt.bunji}}</div>
                <div class="apt-road-address">도로명: {{apt.roadNameAddress}}</div>
            </div>
        </li>
    </ul>
    <div v-else>검색결과가 없습니다.</div>
    <paginate v-if="getAptList.length != 0"
        v-model="$store.state.AR_page"
        :page-count="Math.floor((getAptCount - 1) / 10) + 1"
        :click-handler="updateAptList"
        :prev-text="'Prev'"
        :next-text="'Next'"
        :container-class="'paginate'"
        :active-class="'highlighted'">
    </paginate>
</div>
</template>

<script>
import Paginate from 'vuejs-paginate'
import {mapGetters, mapActions, mapMutations} from 'vuex'

export default {
    components: {
        Paginate
    },
    methods: {
        ...mapActions({
            updateAptList: 'updateAptList'
        }),
        ...mapMutations({
            setMouseOverAptId: 'mouseOverAptId',
        }),
        clickHandler() {
            let aptList = this.$store.getters.aptList;
            for(const apt of aptList) {
                if(apt.aptId == this.getMouseOverAptId) {
                    this.$store.commit('selectedAptInfo', apt);
                    break;
                }
            }
            this.$store.commit('ssFlag', 3);
        },
    },
    computed: {
        ...mapGetters({
            getMouseOverAptId: 'mouseOverAptId',
            getAptList: 'aptList',
            getAptCount: 'aptCount',
            getErrorList: 'errorList'
        })
    }
}
</script>

<style>
#apt-result ul.paginate {
    margin: 10px auto;
    padding: 0;
    text-align: center;
    user-select:none;
}

#apt-result .highlighted a{
    color: #007acc !important;
}

#apt-result .paginate li {
    display: inline;
    margin: 0 10px;
    list-style: none;
}

#apt-result .caution-icon {
    width: 16px;
}

#apt-result{
    height: calc(100% - 110px);
    overflow-y: auto;
}

#apt-result .highlighted {
    color: #007acc !important;
}

#apt-result .error {
    color: #808080 !important;
}

#apt-result ul {
    list-style: none;
    padding: 10px;
    margin: 0;
}

#apt-result li {
    display: block;
}

#apt-result .apt-wrap {
    padding: 10px;
    border-bottom: 1px solid #808080;
}

#apt-result .apt-name {
    font-weight: bold;
}

#apt-result .apt-year {
    color: #808080;
    font-size: 14px;
}

#apt-result .apt-road-address {
    font-size: 14px;
}

</style>