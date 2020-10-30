<template>
    <div id="nav-bar">
        <div id="login" class="modal modal-hidden">
            <div class="modal-content">
                <span class="close" @click="closeLoginModal">&times;</span>
                <div class="modal-title">로그인</div>
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="lusername">아이디</label>
                    </div>
                    <input type="text" id="lusername" name="lusername" placeholder="아이디">
                </div>
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="lpassword">비밀번호</label>
                    </div>
                    <input type="password" id="lpassword" name="lpassword" placeholder="비밀번호">
                </div>

                <button class="modal-button" type="button" @click="doLogin">등록</button>

            </div>
        </div>

        <div id="join" class="modal modal-hidden">
            <div class="modal-content">
                <span class="close" @click="closeJoinModal">&times;</span>
                <div class="modal-title">회원가입</div>
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="jusername">아이디</label>
                    </div>
                    <input type="text" id="jusername" name="jusername" placeholder="아이디">
                </div>
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="jpassword">비밀번호</label>
                    </div>
                    <input type="password" id="jpassword" name="jpassword" placeholder="비밀번호">
                </div>
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="jname">이름</label>
                    </div>
                    <input type="text" id="jname" name="jname" placeholder="이름">
                </div>
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="jemail">이메일</label>
                    </div>
                    <input type="text" id="jemail" name="jemail" placeholder="이메일">
                </div>
                <button class="modal-button" type="button" @click="doJoin">제출</button>
            </div>
        </div>

        <div id="modify" class="modal modal-hidden">
            <div class="modal-content">
                <span class="close" @click="closeModifyModal">&times;</span>
                <div class="modal-title" v-if="modifyFlag == 0">회원정보조회</div>
                <div class="modal-title" v-if="modifyFlag == 1">회원정보수정</div>
                <!-- <div class="modal-title" v-if="modifyFlag == 2">비밀번호수정</div> -->
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="musername">아이디</label>
                    </div>
                    <input type="text" id="musername" name="musername" disabled>
                </div>
                <!-- <div class="modal-div">
                    <div class="modal-label">
                        <label for="mpassword">비밀번호</label>
                    </div>
                    <input type="password" id="mpassword" name="mpassword" :disabled="modifyFlag != 2">
                </div> -->
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="mname">이름</label>
                    </div>
                    <input type="text" id="mname" name="mname" :disabled="modifyFlag != 1" :class="{}">
                </div>
                <div class="modal-div">
                    <div class="modal-label">
                        <label for="memail">이메일</label>
                    </div>
                    <input type="text" id="memail" name="memail" :disabled="modifyFlag != 1">
                </div>
                <button class="modal-button" type="button" v-if="modifyFlag == 0" @click="modifyFlag = 1">회원정보수정</button>
                <!-- <button class="modal-button" type="button" v-if="modifyFlag == 0" @click="modifyFlag = 2">비밀번호수정</button> -->
                <button class="modal-button" type="button" v-if="modifyFlag != 0" @click="doModify">제출</button>
            </div>
        </div>

        <!-- 플래그로 각 섹션 제어 -->
        <div class="icon" id="logo">H</div>
        <div class="icon" :class="{'selected': getNavFlag == 1}" id="search" @click="moveSearch">
            <img src="../assets/icon/search.png">
        </div>
        <!-- <div class="icon" :class="{'selected': getNavFlag == 2}" id="favorite" @click="moveFavorite">
            <img src="../assets/icon/favorite.png">
        </div> -->
        <div class="icon" :class="{'selected': getNavFlag == 3}" id="notice" @click="moveNotice">
            <img src="../assets/icon/notice.png">
        </div>
        <div class="blank"></div>
        <div class="icon" :class="{'hidden': $store.state.accessToken != ''}" id="login" @click="openLoginModal">
            <img src="../assets/icon/login.png">
        </div>
        <div class="icon" :class="{'hidden': $store.state.accessToken != ''}" id="join" @click="openJoinModal">
            <img src="../assets/icon/join.png">
        </div>
        <div class="icon" :class="{'hidden': $store.state.accessToken == ''}" id="mypage" @click="openModifyModal">
            <img src="../assets/icon/mypage.png">
        </div>
        <div class="icon" :class="{'hidden': $store.state.accessToken == ''}" id="logout" @click="doLogout">
            <img src="../assets/icon/logout.png">
        </div>
    </div>
</template>

<script>
import {mapGetters, mapActions, mapMutations} from 'vuex'
import axios from 'axios'

export default {
    data() {
        return {
            modifyFlag: 0
        }
    },
    methods: {
        ...mapMutations({
            setNavFlag: 'navFlag'
        }),
        ...mapActions({
            setNoticeList: 'noticeList'
        }),
        moveSearch() {
            this.setNavFlag(1);
        },
        moveFavorite() {
            this.setNavFlag(2);
        },
        moveNotice() {
            this.setNoticeList();
            this.setNavFlag(3);
        },

        openJoinModal() {
            document.querySelector("#join").classList.remove('modal-hidden');
        },
        closeJoinModal() {
            document.querySelector("#jusername").value="";
            document.querySelector("#jpassword").value="";
            document.querySelector("#jname").value="";
            document.querySelector("#jemail").value="";
            document.querySelector("#join").classList.add('modal-hidden');
        },
        doJoin() {
            axios({
                method: 'POST',
                url: 'http://localhost:9999/happyhouse/api/auth/signup',
                data: {
                    username: document.querySelector("#jusername").value,
                    password: document.querySelector("#jpassword").value,
                    name: document.querySelector("#jname").value,
                    email: document.querySelector("#jemail").value,
                }
            }).then((response) => {
                alert(response.data.message);
                if(response.data.success){
                    this.closeJoinModal();
                }
            }).catch((error) => {
                console.log(error);
            });
        },

        openLoginModal() {
            document.querySelector("#login").classList.remove('modal-hidden');
        },
        closeLoginModal() {
            document.querySelector("#lusername").value="";
            document.querySelector("#lpassword").value="";
            document.querySelector("#login").classList.add('modal-hidden');
        },
        doLogin() {
            axios({
                method: 'POST',
                url: 'http://localhost:9999/happyhouse/api/auth/signin',
                data: {
                    username: document.querySelector("#lusername").value,
                    password: document.querySelector("#lpassword").value
                }
            }).then((response) => {
                if(response.data.accessToken) {
                    this.$store.commit('accessToken', response.data.accessToken);
                    this.$store.commit('username', document.querySelector("#lusername").value);
                    this.closeLoginModal();
                } else {
                    alert("로그인에 실패했습니다.");
                }
            }).catch((error) => {
                console.log(error);
            });
        },

        doLogout() {
            axios({
                method: 'GET',
                url: 'http://localhost:9999/happyhouse/api/auth/signout'
            }).then((response) => {
                if(response.data.success) {
                    alert(response.data.message);
                    this.$store.commit('accessToken', '');
                    this.$store.commit('username', '');
                }
            }).catch((error) => {
                console.log(error);
            })
        },

        openModifyModal() {
            axios({
                method: 'GET',
                url: 'http://localhost:9999/happyhouse/api/auth/users/' + this.$store.getters.username
            }).then((response) => {
                document.querySelector("#musername").value=response.data.username;
                document.querySelector("#mname").value=response.data.name;
                document.querySelector("#memail").value=response.data.email;
            }).catch((error) => {
                console.log(error);
            });

            document.querySelector("#modify").classList.remove('modal-hidden');
        },
        closeModifyModal() {
            document.querySelector("#musername").value="";
            document.querySelector("#mname").value="";
            document.querySelector("#memail").value="";
            document.querySelector("#modify").classList.add('modal-hidden');
            this.modifyFlag = 0;
        },
        doModify() {
            axios({
                method: 'POST',
                url: 'http://localhost:9999/happyhouse/api/auth/users/userinfo',
                data: {
                    username: document.querySelector("#musername").value,
                    name: document.querySelector("#mname").value,
                    email: document.querySelector("#memail").value
                }
            }).then((response) => {
                alert(response.data.message);
                if(response.data.success) {
                    this.closeModifyModal();
                }
            }).catch((error) => {
                console.log(error)
            });
        },



    },
    computed: {
        ...mapGetters({
            getNavFlag: 'navFlag'
        })
    }
}
</script>

<style scoped>
.icon {
    width: 50px;
    height: 50px;
    line-height: 50px;
    text-align: center;
    margin: 10px 0;
}

.blank {
    height: calc(100% - 190px - 130px);
}

.hidden {
    display: none;
}

.selected {
    border-left: 2px solid white;
}

.icon img {
    width: 30px;
    vertical-align: middle;

    filter: invert(50%);
}

.selected img {
    filter: invert(100%);
}

#nav-bar{
    position:relative;
    float: left;
    width: 50px;
    height: 100%;
    background-color: #323233;
}

/* The Modal (background) */
.modal-hidden {
    display: none !important;
}

.modal {
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
.modal-content {
  background-color: #252526;
  margin: auto;
  padding: 20px;
  border: 1px solid #1e1e1e;
  width: 480px;
  border-radius: 20px;
}

.modal-title {
    margin: 10px;
    font-size: 32px;
}

.modal-div {
    margin: 20px;
}

.modal-label label{
    font-size: 18px;
    font-style: italic;
}

.modal input {
    background-color: #323233;
    border: none;
    height: 40px;
    width: 400px;
    border-radius: 5px;
    display: block;
    margin: 10px auto 0 auto;
    padding: 10px;
}

.modal-button {
    background-color: #007acc;
    border: none;
    height: 40px;
    width: 100px;
    border-radius: 5px;
    display: block;
    margin: 0 auto;
}

/* The Close Button */
.close {
  color: #aaaaaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: #000;
  text-decoration: none;
  cursor: pointer;
}

</style>