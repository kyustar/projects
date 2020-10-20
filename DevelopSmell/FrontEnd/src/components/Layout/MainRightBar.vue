<template>
  <v-navigation-drawer id="rightbar"
    class="border-top"
    width="20%"
    permanent
    right
    absolute
    collpase
  >
    <v-container class="mt-10">
      <v-list header>
        <v-list-item-title style="text-align:center" class="title">
          Top Ranking
        </v-list-item-title>
        <v-list-item class="list_font"
          v-for="(item, index) in this.rankitems"
          :key="index"
          dense
          @click="goblog(item.uid)"
        >
          {{ index + 1 }}. {{ item.uid }}
        </v-list-item>
      </v-list>
      <v-divider></v-divider>

      <v-list dense>
        <v-list-item-title style="text-align:center" class="title">
          ＊ 주간 인기 게시물 ＊
        </v-list-item-title>

        <v-list-item-content style="cursor:pointer"
          v-for="(item, index) in this.likeitems"
          :key="index"
          @click="detail(item.postno)"
        >
          <v-list-item-title class="list_font">
            <span class="float-left mt-3"></span>
            <v-list-item-avatar class="float-left list_font"
              ><img :src="item.profilename" />
            </v-list-item-avatar>
            <v-list-item-subtitle style="padding-left:0.5rem" class="mt-5 ml-3 list_font"
              >by {{ item.uid }}
            </v-list-item-subtitle>
          </v-list-item-title>
          <div class="list_font">
          - {{ item.title }}
          </div>
        </v-list-item-content>
      </v-list>
      <v-divider></v-divider>
    </v-container>
  </v-navigation-drawer>
</template>

<script>
import axios from "axios";
export default {
  data: () => {
    return {
      rankitems: [],
      likeitems: []
    };
  },
  created() {
    axios({
      method: "GET",
      url: this.$baseurl + "/user/rank/0"
    }).then(res => {
      this.rankitems = res.data.object;
    });

    axios({
      method: "GET",
      url: this.$baseurl + "/post/toppost/"
    }).then(res => {
      this.likeitems = res.data.object;
    });
  },
  methods: {
    detail(postno) {
      this.$router.push("/post/detail/" + postno);
    },
    goblog(bloguser) {
      let uid = this.$store.state.userm.uid;
      if(bloguser == uid){
        this.$router.push("/myblog/" + uid);
      }
      else{
        this.$router.push("/userblog/" + bloguser);
      }
    }
  }
};
</script>
<style scoped>
.title {
  font-size: 1.3vw !important;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}

.list_font {
  font-size: 1.0vw !important;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
@media (min-width: 1440px) {
  .title {
    font-size: 22px !important;
  }
  .list_font {
    font-size: 18px !important;
  }
}
.border-top {
  /* right: 80px; */
  margin-top:140px;
  border-right: 1px solid rgba(211, 211, 211, 0.9);
}
</style>
