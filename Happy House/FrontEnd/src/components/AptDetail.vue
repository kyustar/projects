<template>
<div id="apt-detail">
    <div class="return-button" @click="returnBack">&lt; 돌아가기</div>
    <table class="apt-table">
        <tr>
            <th>아파트명</th>
            <td>{{selectedAptInfo.aptName}}</td>
        </tr>
        <tr>
            <th>건축연도</th>
            <td>{{selectedAptInfo.buildYear}}년</td>
        </tr>
        <tr>
            <th>주소</th>
            <td>{{selectedAptInfo.district}} {{selectedAptInfo.bunji}}번지</td>
        </tr>
        <tr>
            <th>도로명 주소</th>
            <td>{{selectedAptInfo.roadNameAddress}}</td>
        </tr>
    </table>
    <div class="graph-title">&lt;거래금액 변동추이(2015~2020)&gt;</div>
    <div id="graph"></div>
    <div class="graph-title">&lt;거래내역&gt;</div>
    <table class="deal-table">
        <tr>
            <th>거래일시</th>
            <th>평수(m<sup>2</sup>)</th>
            <th>층수</th>
            <th>거래금액(만원)</th>
        </tr>
        <tr v-for="(deal, index) of dealList" :key="index">
            <td>{{deal.dealYear}}.{{deal.dealMonth}}.{{deal.dealDay}}</td>
            <td>{{deal.area}}</td>
            <td>{{deal.floor}}</td>
            <td>{{deal.dealAmount}}</td>
        </tr>
    </table>
    <paginate v-if="dealList.length != 0"
        v-model="$store.state.AD_page"
        :page-count="Math.floor((dealCount - 1) / 10) + 1"
        :click-handler="getDealList"
        :prev-text="'Prev'"
        :next-text="'Next'"
        :container-class="'paginate'"
        :active-class="'highlighted'">
    </paginate>
</div>
</template>

<script>
import Paginate from 'vuejs-paginate'
import axios from 'axios';
import * as d3 from 'd3';

export default {
    components: {
        Paginate
    },
    data() {
        return {
            dealList: [],
            dealCount: 0
        }
    },
    mounted() {
        console.log("mounted");
        this.getStatistics();
        this.getDealCount();
        this.getDealList(1);
    },
    methods: {
        getDealCount(){
            axios({
                method: 'GET',
                url: `http://localhost:9999/happyhouse/api/count/deal?aptId=${this.selectedAptInfo.aptId}`
            }).then((response) => {
                this.dealCount = response.data;
            }).catch((error) => {
                console.log(error);
            });
        },
        getDealList(pageNum){
            axios({
                method: 'GET',
                url: `http://localhost:9999/happyhouse/api/list/deal?aptId=${this.selectedAptInfo.aptId}&pageNum=${pageNum}`
            }).then((response) => {
                this.dealList = response.data;
            }).catch((error) => {
                console.log(error);
            });
        },
        returnBack() {
            this.$store.state.AD_page = 1;
            this.$store.commit('ssFlag', 2);
        },
        getStatistics(){
            axios({
                methods: 'get',
                url: `http://localhost:9999/happyhouse/api/util/amount/${this.selectedAptInfo.aptId}`
            }).then((response) => {
                this.createGraph(response.data);
            }).catch((error) => {
                console.log(error);
            });
        },
        createGraph(data) {
            console.log(data);

            var margin = {top: 10, right: 50, bottom: 80, left: 50}
            , width = 330 - margin.left - margin.right
            , height = 330 - margin.top - margin.bottom;

            var n = data.length;

            var xScale = d3.scaleLinear()
                .domain([2015, 2020])
                .range([0, width]);

            var min = Number.MAX_SAFE_INTEGER;
            for(let datum of data){
                min = (datum.minAmount < min) ? datum.minAmount : min;
            }

            var max = Number.MIN_SAFE_INTEGER;
            for(let datum of data){
                max = (datum.maxAmount > max) ? datum.maxAmount : max;
            }

            var yScale = d3.scaleLinear()
                .domain([min - 5000, max + 5000])
                .range([height, 0]);

            var line = d3.line()
                .x(function(d) { return xScale(d.dealYear); })
                .y(function(d) { return yScale(d.avgAmount); })

            var maxLine = d3.line()
                .x(function(d) { return xScale(d.dealYear); })
                .y(function(d) { return yScale(d.maxAmount); })

            var minLine = d3.line()
                .x(function(d) { return xScale(d.dealYear); })
                .y(function(d) { return yScale(d.minAmount); })

            var dataset = d3.range(n).map(function(d) { return {"x": d.dealYear, "y": d.avgAmount } }) // eslint-disable-line no-unused-vars

            d3.select("svg").remove();

            var svg = d3.select("#graph").append("svg")
                .attr("width", width + margin.left + margin.right)
                .attr("height", height + margin.top + margin.bottom)
                .append("g")
                .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

            svg.append("g")
                .attr("class", "x axis")
                .attr("transform", "translate(0," + height + ")")
                .call(d3.axisBottom(xScale).ticks(6));

            svg.append("g")
                .attr("class", "y axis")
                .call(d3.axisLeft(yScale));

            svg.append("path")
                .datum(data)
                .attr("class", "line")
                .attr("d", line);

            svg.append("path")
                .datum(data)
                .attr("class", "line-red")
                .attr("d", maxLine);

            svg.append("path")
                .datum(data)
                .attr("class", "line-blue")
                .attr("d", minLine);  

            svg.selectAll(".dot-min")
                .data(data)
                .enter().append("circle")
                .attr("class", "dot-blue")
                .attr("cx", function(d) { return xScale(d.dealYear) })
                .attr("cy", function(d) { return yScale(d.minAmount) })
                .attr("r", 5)
                .append("svg:title")
                .text(function(d) { return (d.minAmount*10000).toLocaleString()+"원"});

            svg.selectAll(".dot-avg")
                .data(data)
                .enter().append("circle")
                .attr("class", "dot")
                .attr("cx", function(d) { return xScale(d.dealYear) })
                .attr("cy", function(d) { return yScale(d.avgAmount) })
                .attr("r", 5)
                .append("svg:title")
                .text(function(d) { return (d.avgAmount*10000).toLocaleString()+"원"});

            svg.selectAll(".dot-max")
                .data(data)
                .enter().append("circle")
                .attr("class", "dot-red")
                .attr("cx", function(d) { return xScale(d.dealYear) })
                .attr("cy", function(d) { return yScale(d.maxAmount) })
                .attr("r", 5)
                .append("svg:title")
                .text(function(d) { return (d.maxAmount*10000).toLocaleString()+"원"});

            svg.append("circle")
                .attr("cx",0)
                .attr("cy",300)
                .attr("r", 5)
                .attr("class", "dot-red")

            svg.append("circle")
                .attr("cx",80)
                .attr("cy",300)
                .attr("r", 5)
                .attr("class", "dot")

            svg.append("circle")
                .attr("cx",160)
                .attr("cy",300)
                .attr("r", 5)
                .attr("class", "dot-blue")

            svg.append("text")
                .attr("x", 10)
                .attr("y", 300)
                .text("최대금액")
                .style("font-size", "15px")
                .style("fill", "white")
                .attr("alignment-baseline","middle")

            svg.append("text")
                .attr("x", 90)
                .attr("y", 300)
                .text("평균금액")
                .style("font-size", "15px")
                .style("fill", "white")
                .attr("alignment-baseline","middle")

            svg.append("text")
                .attr("x", 170)
                .attr("y", 300)
                .text("최소금액")
                .style("font-size", "15px")
                .style("fill", "white")
                .attr("alignment-baseline","middle")

            svg.append("text")
                .attr("x", 170)
                .attr("y", 275)
                .text("(단위: 만원)")
                .style("font-size", "13px")
                .style("fill", "white")
                .attr("alignment-baseline","middle")
        }
    },
    computed: {
        selectedAptInfo() {
            return this.$store.getters.selectedAptInfo;
        },
        ssFlag() {
            return this.$store.getters.ssFlag;
        }
    },
    watch: {
        selectedAptInfo(value) {
            this.getStatistics(value);
        },
        ssFlag() {
            this.getStatistics();
        }
    }
}
</script>

<style>
.apt-table {
    border-top: 2px solid white;
    border-bottom: 2px solid white;
    margin: 20px auto;
}

.apt-table tr * {
    word-break: keep-all;
}

.apt-table tr th {
    border-right: 1px solid white;
}

.deal-table {
    padding: 0;
    width: 360px;
    text-align: center;
    margin: 0 auto;
}

.graph-title {
    width: 100%;
    text-align: center;
    margin-bottom: 10px;
}

#apt-detail{
    float: left;
    width: 400px;
    height: calc(100% - 110px);
    background-color: #1e1e1e;    
    overflow-y: auto;
}

#graph {
    width: 330px;
    height: 330px;
    margin: 0 auto 20px auto;
}

/* 13. Basic Styling with CSS */

/* Style the lines by removing the fill and applying a stroke */
.line {
    fill: none;
    stroke: #ffab00;
    stroke-width: 3;
}

.line-red {
    fill: none;
    stroke: red;
    stroke-width: 3;
}

.line-blue {
    fill: none;
    stroke: blue;
    stroke-width: 3;
}
  
.overlay {
  fill: none;
  pointer-events: all;
}

/* Style the dots by assigning a fill and stroke */
.dot {
    fill: #ffab00;
    stroke: #fff;
}

.dot-red {
    fill: red;
    stroke: #fff;
}

.dot-blue {
    fill: blue;
    stroke: #fff;
}
  
.focus circle {
  fill: none;
  stroke: steelblue;
}

.return-button {
    display: inline-block;
    margin: 10px 0 10px 10px;
}

#apt-detail ul.paginate {
    margin: 10px auto;
    padding: 0;
    text-align: center;
    user-select:none;
}

#apt-detail .highlighted a{
    color: #007acc !important;
}

#apt-detail .paginate li {
    display: inline;
    margin: 0 10px;
    list-style: none;
}
</style>