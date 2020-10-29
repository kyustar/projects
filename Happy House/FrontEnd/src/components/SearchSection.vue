<template>
<div id="search-section">
    <h3>검색</h3>
    <div id="search-bar">
        <input type="text" @input="setLiveKeyword($event.target.value)" @keyup.prevent="getRecommand" @focusin="getRecommand" @focusout="getRecommand" placeholder="지역명으로 검색"/>
    </div>
    <div id="recommand">
        <div class="recommand-list" v-for="(recommand, index) of recommandList" :key="index" @mouseenter="cursorFlag=true" @mouseleave="cursorFlag=false" @click="getRecommand" :class="{'highlighted': selected == index}">{{recommand.district}}</div>
    </div>
    <hr/>
    <district-result v-if="getFlag == 1"></district-result>
    <apt-result v-else-if="getFlag == 2"></apt-result>
    <apt-detail v-else-if="getFlag == 3"></apt-detail>
</div>
</template>

<script>
import DistrictResult from './DistrictResult.vue'
import AptDetail from './AptDetail.vue'
import AptResult from './AptResult.vue'


import axios from 'axios'
import hangul from 'hangul-js'
import {mapGetters, mapMutations, mapActions} from 'vuex'

export default {
    components: {
        DistrictResult,
        AptDetail,
        AptResult,
    },
    data() {
        return {
            selected: -1,
            recommandList: null,
            cursorFlag: false,
        }
    },
    methods: {
        ...mapActions({
            setDistrictList: 'districtList'
        }),
        ...mapMutations({
            setLiveKeyword: 'liveKeyword',
            setFixedKeyword: 'fixedKeyword'
        }),
        getRecommand(event) {
            console.log(event);
            if(event.type == 'click'){
                this.recommandList = null;
                this.selected = -1;
                this.setDistrictList(event.target.innerText);
            } else if(event.type == 'focusout') {
                if(!this.cursorFlag) {
                    this.recommandList = null;
                    this.selected = -1;
                }
            } else if(event.keyCode == 13) {
                if(document.querySelector('#recommand .highlighted') != null){
                    this.setDistrictList(document.querySelector('#recommand .highlighted').innerText);
                } else {
                    console.log('outer');
                    this.setDistrictList(this.getLiveKeyword);
                }
                this.recommandList = null;
                this.selected = -1;
            } else if(event.keyCode == 40) {
                if(this.recommandList && this.selected < this.recommandList.length-1) {    
                    this.selected++;
                }
            }  else if(event.keyCode == 38) {
                if(this.recommandList && this.selected > -1){
                    this.selected--;
                }
            } else if(event.code != 'Enter'){
                this.selected = -1;
                if(this.getLiveKeyword == '') {
                    this.recommandList = null;
                } else {
                    axios({
                        method: 'GET',
                        url: 'http://localhost:9999/happyhouse/api/list/recommand?hangul=' + hangul.disassemble(this.getLiveKeyword).join('')
                    }).then((response) => {
                        this.recommandList = response.data;
                    }).catch((error) => {
                        console.log(error)
                    });
                }
            }
        }   
    },
    computed: {
        ...mapGetters({
            getFlag: 'ssFlag',
            getLiveKeyword: 'liveKeyword',
            getFixedKeyword: 'fixedKeyword'
        })
    }
}
</script>

<style scoped>
#search-section{
    float: left;
    width: 400px;
    height: 100%;
    background-color: #252526;
    overflow: hidden;
}

#search-bar input {
    width: 360px;
    height: 30px;
    background-color: #323233;
    display: block;
    margin: 0 auto;
    border: none;
    border: 1px solid #007acc;
}

#search-bar input:focus {
    outline: none;
}

#recommand {
    position: absolute;
    top: 92px;
    left: 70px;
    width: 360px;
    background-color: #323233;
    box-shadow: 0 10px 20px 3px black;
}

#recommand .recommand-list {
    padding: 5px;
}

.highlighted {
    background-color: #007acc;
}
</style>