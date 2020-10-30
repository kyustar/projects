<template>
<div id="notice-board">
    <button id="flying-write-button" v-if="$store.state.username == 'admin'" type="button" @click="openWriteModal()">작성</button>
    <h3>공지사항</h3>

    <div id="write" class="modal modal-hidden">
        <div class="modal-content">
            <span class="close" @click="closeWriteModal">&times;</span>
            <div class="modal-title">공지사항 작성</div>
            <div class="modal-div">
                <div class="modal-label">
                    <label for="title">제목</label>
                </div>
                <input type="text" id="title" name="title" placeholder="제목">
            </div>
            <div class="modal-div">
                <div class="modal-label">
                    <label for="content">본문</label>
                </div>
                <textarea id="content" name="content" placeholder="관리자라는 직책에 걸맞는 글을 작성합시다."></textarea>
            </div>
            <button class="modal-button" type="button" @click="doWrite">제출</button>
        </div>
    </div>

    <div id="notice-modify" class="modal modal-hidden">
        <div class="modal-content">
            <span class="close" @click="closeModifyModal">&times;</span>
            <div class="modal-title">공지사항 수정</div>
            <div class="modal-div">
                <div class="modal-label">
                    <label for="title">제목</label>
                </div>
                <input type="text" id="title" name="title">
            </div>
            <div class="modal-div">
                <div class="modal-label">
                    <label for="content">본문</label>
                </div>
                <textarea id="content" name="content"></textarea>
            </div>
            <button class="modal-button" type="button" @click="doModify">제출</button>
        </div>
    </div>
    
    <div v-for="(notice, index) of getNoticeList" :key="index">
        <div class="head" @click="setSelectedNotice(notice.no)" :class="{'open': getSelectedNotice == notice.no}">
            <div class="head-up">
                <div class="head-no">{{notice.no}}</div>
                <div class="head-title">{{notice.title}}</div>
            </div>
            <div class="head-down">
                <div class="head-writer">{{notice.writer}}</div>
                <div class="head-regtime">{{notice.regtime.substr(0,19).replace('T', ' ')}}</div>
            </div>
        </div>
        <div class="content" :class="{'hidden': notice.no != getSelectedNotice}">
            {{notice.content}}
            <div id="admin-only" v-if="$store.state.username == 'admin'">
                <button class="admin-button" type="button" @click="openModifyModal(notice.no)">수정</button>
                <button class="admin-button" type="button" @click="doDelete(notice.no)">삭제</button>
                <div class="float-helper"></div>
            </div>
        </div>
    </div>
    <paginate v-if="getNoticeList.length != 0"
        v-model="$store.state.NB_page"
        :page-count="Math.floor((getNoticeCount - 1) / 10) + 1"
        :click-handler="updateNoticeList"
        :prev-text="'Prev'"
        :next-text="'Next'"
        :container-class="'className'"
        :active-class="'highlighted'">
    </paginate>
</div>
</template>

<script>
import {mapGetters, mapMutations, mapActions} from 'vuex'
import Paginate from 'vuejs-paginate'
import axios from 'axios'

export default {
    components: {
        Paginate
    },
    data() {
        return {
            openNotice: 0,
            modifyNotice: 0,
            deleteNotice: 0
        }
    },
    methods: {
        ...mapActions({
            setNoticeList: 'noticeList',
            updateNoticeList: 'updateNoticeList'
        }),
        ...mapMutations({
            setSelectedNotice: 'selectedNotice'
        }),
        openWriteModal() {
            document.querySelector("#write").classList.remove('modal-hidden');
        },
        closeWriteModal() {
            document.querySelector("#write #title").value="";
            document.querySelector("#write #content").value="";
            document.querySelector("#write").classList.add('modal-hidden');
        },
        doWrite() {
            axios({
                method: 'POST',
                url: 'http://localhost:9999/happyhouse/api/notice',
                data: {
                    writer: this.$store.state.username,
                    title: document.querySelector("#write #title").value,
                    content: document.querySelector("#write #content").value
                }
            }).then(() => {
                alert('등록되었습니다.');
                this.$store.dispatch('noticeList');
                this.closeWriteModal();
            }).catch((error) => {
                console.log(error)
            });
        },
        openModifyModal(no) {
            this.modifyNotice = no;
            for(const notice of this.getNoticeList) {
                if(notice.no == no){
                    document.querySelector("#notice-modify #title").value=notice.title;
                    document.querySelector("#notice-modify #content").value=notice.content;
                }
            }
            document.querySelector("#notice-modify").classList.remove('modal-hidden');
        },
        closeModifyModal() {
            document.querySelector("#notice-modify #title").value="";
            document.querySelector("#notice-modify #content").value="";
            document.querySelector("#notice-modify").classList.add('modal-hidden');
        },
        doModify() {
            axios({
                method: 'PUT',
                url: 'http://localhost:9999/happyhouse/api/notice',
                data: {
                    no: this.modifyNotice,
                    writer: this.$store.state.username,
                    title: document.querySelector("#notice-modify #title").value,
                    content: document.querySelector("#notice-modify #content").value
                }
            }).then(() => {
                alert('수정되었습니다.');
                this.$store.dispatch('noticeList');
                this.closeModifyModal();
            }).catch((error) => {
                console.log(error)
            });
        },
        doDelete(no) {
            axios({
                method: 'DELETE',
                url: 'http://localhost:9999/happyhouse/api/notice/'+ no
            }).then(() => {
                alert('삭제되었습니다.');
                this.$store.dispatch('noticeList');
            }).catch((error) => {
                console.log(error)
            });
        }
    },
    computed: {
        ...mapGetters({
            getNoticeList: 'noticeList',
            getNoticeCount: 'noticeCount',
            getSelectedNotice: 'selectedNotice'
        })
    }
}
</script>

<style>
.float-helper {
    clear: both;
}

#notice-board ul {
    margin: 10px auto;
    padding: 0;
    text-align: center;
    user-select:none;
}

#notice-board li {
    display: inline;
    margin: 0 10px;
    list-style: none;
}

#notice-board .highlighted a{
    color: #007acc !important;
}

#admin-only {
    margin: 10px 0;
    text-align: right;
}

#notice-board .admin-button{
    display: inline-block;

    height: 30px;
    width: 50px;
    background-color: #323233;
    border: none;
}

#flying-write-button {
    position: absolute;
    left: 380px;
    top: 10px;
    height: 30px;
    width: 50px;
    background-color: #323233;
    border: 1px solid #007acc;
}

#notice-board{
    float: left;
    width: 400px;
    height: 100%;
    background-color: #252526;
    overflow-y: auto;
}

#notice-board .hidden {
    display: none;
}

#notice-board .head {
    padding: 10px;
    border-bottom: 1px solid #808080;
}

#notice-board .content {
    padding: 10px;
    border-bottom: 1px solid #808080;
}

#notice-board .open {
    border: none !important;
}

#notice-board .head-up div {
    display: inline-block;
}
#notice-board .head-no {
    margin-right: 20px;
}

#notice-board .head-title {
    font-weight: bold;
}

#notice-board .head-down div {
    display: inline-block;
    font-size: 12px;
    color: #808080;
}

#notice-board .head-writer {
    width: 50%;
}
#notice-board .head-regtime {
    width: 50%;
    text-align: right;
}

/* The Modal (background) */
#notice-board .modal-hidden {
    display: none !important;
}

#notice-board .modal {
  display: block; /* Hidden by default */
  position: fixed; /* Stay in place */
  z-index: 999; /* Sit on top */
  padding-top: 100px; /* Location of the box */
  left: 0;
  top: 0;
  width: 100%; /* Full width */
  height: 100%; /* Full height */
  overflow: auto; /* Enable scroll if needed */
  background-color: rgb(0,0,0); /* Fallback color */
  background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
}
#notice-board .modal-content {
  background-color: #252526;
  margin: auto;
  padding: 20px;
  border: 1px solid #1e1e1e;
  width: 480px;
  border-radius: 20px;
}

#notice-board .modal-title {
    margin: 10px;
    font-size: 32px;
}

#notice-board .modal-div {
    margin: 20px;
}

#notice-board .modal-label label{
    font-size: 18px;
    font-style: italic;
}

#notice-board .modal input {
    background-color: #323233;
    border: none;
    /* border: 1px solid #007acc; */
    height: 40px;
    width: 400px;
    border-radius: 5px;
    display: block;
    margin: 10px auto 0 auto;
    padding: 10px;
}

#notice-board .modal textarea {
    background-color: #323233;
    border: none;
    /* border: 1px solid #007acc; */
    height: 400px;
    width: 400px;
    border-radius: 5px;
    display: block;
    margin: 10px auto 0 auto;
    padding: 10px;
}

#notice-board .modal-button {
    background-color: #007acc;
    border: none;
    height: 40px;
    width: 100px;
    border-radius: 5px;
    display: block;
    margin: 0 auto;
}

/* The Close Button */
#notice-board .close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

#notice-board .close:hover,
#notice-board .close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}
</style>