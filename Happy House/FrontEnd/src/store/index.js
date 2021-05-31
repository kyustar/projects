import axios from 'axios'

import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
    state: {
        // Login Session
        accessToken: '',
        username: '',

        DR_page: 1,
        NB_page: 1,
        AR_page: 1,
        AD_page: 1,

        // App.vue
        appSearchFlag: 1,

        // NavBar.vue
        navFlag: 1,

        // SearchSection.vue
        SS_flag: 0,
        SS_liveKeyword: '',
        SS_fixedKeyword: '',

        // SearchList.vue
        SS_districtList: [],
        SS_districtCount: 0,

        // AptList.vue
        SS_aptList: [],
        SS_aptCount: 0,
        SS_currentDistrict: 0,

        // NoticeBoard.vue
        NB_noticeList: [],
        NB_noticeCount: 0,
        NB_selectedNotice: 0,

        // MapSection.vue
        MS_mouseOverAptId: 0,
        MS_errorList: [],

        // Comp3.vue -> AptDetail.vue
        comp3SelectedAptInfo: {},
        comp3DealList:[],

        // use somewhere...
        districtList: undefined,
        districtRecords: undefined,
        aptList: undefined,
        
        recommandList: undefined,
        aroundSearchFlag: false,

    },
    getters: {
        accessToken(state) {
            return state.accessToken;
        },
        username(state) {
            return state.username;
        },

        // App.vue
        searchFlag(state) {
            return state.appSearchFlag;
        },

        // NavBar.vue
        navFlag(state) {
            return state.navFlag;
        },

        // SearchSection.vue
        ssFlag(state) {
            return state.SS_flag;
        },
        liveKeyword(state) {
            return state.SS_liveKeyword;
        },
        fixedKeyword(state) {
            return state.SS_fixedKeyword;
        },

        // SearchResult.vue
        districtList(state) {
            return state.SS_districtList;
        },
        districtCount(state) {
            return state.SS_districtCount;
        },

        // AptResult.vue
        aptList(state) {
            return state.SS_aptList;
        },
        aptCount(state) {
            return state.SS_aptCount;
        },
        currentDistrict(state) {
            return state.SS_currentDistrict;
        },

        // NoticeBoard.vue
        noticeList(state) {
            return state.NB_noticeList;
        },
        noticeCount(state){
            return state.NB_noticeCount;
        },
        selectedNotice(state) {
            return state.NB_selectedNotice;
        },

        // MapSection.vue
        mouseOverAptId(state) {
            return state.MS_mouseOverAptId;
        },
        errorList(state) {
            return state.MS_errorList;
        },
        
        // Comp3.vue -> AptDetail.vue
        selectedAptInfo(state) {
            return state.comp3SelectedAptInfo;
        },
        dealList(state) {
            return state.comp3DealList;
        },

        // use somewhere...
        getDistrictList(state) {
            return state.districtList;
        },
        getAptList(state) {
            return state.aptList;
        },

        getRecommandList(state) {
            return state.recommandList;
        },
        getAroundSearchFlag(state) {
            return state.aroundSearchFlag
        },

    },
    mutations: {
        accessToken(state, value) {
            state.accessToken = value;
        },
        username(state, value) {
            state.username = value;
        },

        // App.vue
        searchFlag(state, value) {
            state.appSearchFlag = value;
        },

        // NavBar.vue
        navFlag(state, value) {
            state.navFlag = value;
        },

        // SearchSection.vue
        ssFlag(state, value) {
            state.SS_flag = value;
        },
        liveKeyword(state, value) {
            state.SS_liveKeyword = value;
        },
        fixedKeyword(state, value) {
            state.SS_fixedKeyword = value;
        },

        // SearchResult.vue
        districtList(state, value) {
            state.SS_districtList = value;
        },
        districtCount(state, value) {
            state.SS_districtCount = value;
        },

        // AptResult.vue
        aptList(state, value) {
            state.SS_aptList = value;
        },
        aptCount(state, value) {
            state.SS_aptCount = value;
        },
        currentDistrict(state, value) {
            state.SS_currentDistrict = value;
        },

        // NoticeBoard.vue
        noticeList(state, value) {
            state.NB_noticeList = value;
        },
        noticeCount(state, value){
            state.NB_noticeCount = value;
        },
        selectedNotice(state,value) {
            if(value == state.NB_selectedNotice){
                state.NB_selectedNotice = 0;
            } else {
                state.NB_selectedNotice = value;
            }
        },

        // MapSection.vue
        mouseOverAptId(state, value) {
            state.MS_mouseOverAptId = value;
        },
        errorList(state, value) {
            state.MS_errorList = value;
        },

        // Comp3.vue -> AptDetail.vue
        selectedAptInfo(state, value) {
            state.comp3SelectedAptInfo = value;
        },
        dealList(state, value) {
            state.comp3DealList = value;
        },

        // use somewhere...
        setDistrictList(state, value) {
            state.districtList = value;
        },
        setAptList(state, value) {
            state.aptList = value;
        },

        setRecommandList(state, value){
            state.recommandList = value;
        },
        setAroundSearchFlag(state, value) {
            state.aroundSearchFlag = value;
        },

    },
    actions: {
        districtList(context, value) {
            console.log(this);
            console.log(value);
            context.commit('ssFlag', 1);
            if(value != null && value != '') {
                context.commit('fixedKeyword', value);
                axios({
                        method: 'GET',
                        url: `http://localhost:9999/happyhouse/api/list/district?keyword=${value}&pageNum=1`
                }).then((response) => {
                        context.commit('districtList', response.data);
                        if(response.data.length == 1) {
                            context.dispatch('aptList', response.data[0].districtCode);
                        }
                }).catch((error) => {
                        console.log(error);
                });

                axios({
                    method: 'GET',
                    url: `http://localhost:9999/happyhouse/api/count/district?keyword=${value}`
                }).then((response) => {
                    context.commit('districtCount', response.data);
                    if(response.data == 1) {
                        context.commit('ssFlag', 2);
                    }
                }).catch((error) => {
                    console.log(error);
                });
            }
        },
        updateDistrictList(context, pageNum) {
            axios({
                method: 'GET',
                url: `http://localhost:9999/happyhouse/api/list/district?keyword=${context.getters.fixedKeyword}&pageNum=${pageNum}`
            }).then((response) => {
                context.commit('districtList', response.data);
            }).catch((error) => {
                console.log(error);
            });
        },
        aptList(context, value) {
            context.commit('ssFlag', 2);
            context.commit('currentDistrict', value);
            axios({
                method: 'GET',
                url: `http://localhost:9999/happyhouse/api/list/apt?districtCode=${value}&pageNum=1`
            }).then((response) => {
                    context.commit('aptList', response.data)
            }).catch((error) => {
                    console.log(error);
            });

            axios({
                method: 'GET',
                url: `http://localhost:9999/happyhouse/api/count/apt?districtCode=${value}`
            }).then((response) => {
                context.commit('aptCount', response.data);
            }).catch((error) => {
                console.log(error);
            });
        },
        updateAptList(context, pageNum) {
            console.log(context.getters.currentDistrict);
            console.log(pageNum);
            axios({
                method: 'GET',
                url: `http://localhost:9999/happyhouse/api/list/apt?districtCode=${context.getters.currentDistrict}&pageNum=${pageNum}`
            }).then((response) => {
                context.commit('aptList', response.data);
            }).catch((error) => {
                console.log(error);
            });
        },
        noticeList(context) {
            axios({
                method: 'GET',
                url: 'http://localhost:9999/happyhouse/api/notice/list?pageNum=1'
            }).then((response) => {
                context.commit('noticeList', response.data);
            }).catch((error) => {
                console.log(error)
            });

            axios({
                method: 'GET',
                url: `http://localhost:9999/happyhouse/api/notice/count`
            }).then((response) => {
                context.commit('noticeCount', response.data);
            }).catch((error) => {
                console.log(error)
            });
        },
        updateNoticeList(context, pageNum) {
            console.log(context.getters.noticeCount);
            axios({
                method: 'GET',
                url: `http://localhost:9999/happyhouse/api/notice/list?pageNum=${pageNum}`
            }).then((response) => {
                context.commit('noticeList', response.data);
            }).catch((error) => {
                console.log(error)
            });
        }
    },
    modules: {
    }
})
