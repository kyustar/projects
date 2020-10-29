<template>
<div id="map-section">
    <div id="map"></div>
    <ul id="category" :class="{'hidden': ssFlag != 3}">
        <li id="BK9" data-order="0"> 
            <span class="category_bg bank"></span>
            은행
        </li>       
        <li id="MT1" data-order="1"> 
            <span class="category_bg mart"></span>
            마트
        </li>  
        <li id="PM9" data-order="2"> 
            <span class="category_bg pharmacy"></span>
            약국
        </li>  
        <li id="OL7" data-order="3"> 
            <span class="category_bg oil"></span>
            주유소
        </li>  
        <li id="CE7" data-order="4"> 
            <span class="category_bg cafe"></span>
            카페
        </li>  
        <li id="CS2" data-order="5"> 
            <span class="category_bg store"></span>
            편의점
        </li>      
    </ul>
</div>
</template>

<script>
import axios from 'axios';

export default {
    data() {
        return {
            map: null,
            markers: [],
            positions: null,
            event: null,
            currCategory: '',
            aroundMarkers: [],
            ps: null,
            placeOverlay: null,
            contentNode: null
        }
    },
    mounted () {
        if (window.kakao && window.kakao.maps) {
            this.initMap();
        } else {
            const script = document.createElement('script');
            /* global kakao */
            script.onload = () => kakao.maps.load(this.initMap);
            script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=8df0bc7e9acbeaa77a966e27cffbdb8e&libraries=services,clusterer,drawing';
            document.head.appendChild(script);
        }
    },
    methods: {
        initMap() {
            var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
            var options = { //지도를 생성할 때 필요한 기본 옵션
                center: new kakao.maps.LatLng(37.551881, 126.988062), //지도의 중심좌표.
                level: 7 //지도의 레벨(확대, 축소 정도)
            };

            this.map = new kakao.maps.Map(container, options); //지도 생성 및 객체 리턴
            kakao.maps.event.addListener(this.map, 'idle', this.searchPlaces);

            this.ps = new kakao.maps.services.Places(this.map);

            this.placeOverlay = new kakao.maps.CustomOverlay({zIndex:1});
            this.contentNode = document.createElement('div');
            this.contentNode.className = 'placeinfo_wrap';
            this.addEventHandle(this.contentNode, 'mousedown', kakao.maps.event.preventMap);
            this.addEventHandle(this.contentNode, 'touchstart', kakao.maps.event.preventMap);
            this.placeOverlay.setContent(this.contentNode);

            this.addCategoryClickEvent();
        },
        addEventHandle(target, type, callback) {
            if (target.addEventListener) {
                target.addEventListener(type, callback);
            } else {
                target.attachEvent('on' + type, callback);
            }
        },
        // 각 카테고리에 클릭 이벤트를 등록합니다
        addCategoryClickEvent() {
            var category = document.getElementById('category'),
                children = category.children;

            for (var i=0; i<children.length; i++) {
                children[i].addEventListener('click', (event) => {
                    var id = event.currentTarget.id, className = event.currentTarget.className;

                    this.placeOverlay.setMap(null);

                    if (className === 'on') {
                        this.currCategory = '';
                        this.changeCategoryClass();
                        this.removeMarker();
                    } else {
                        this.currCategory = id;
                        this.changeCategoryClass(event.target);
                        this.searchPlaces();
                    }
                });
            }
        },
        // 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
        changeCategoryClass(el) {
            var category = document.getElementById('category'),
                children = category.children,
                i;

            for ( i=0; i<children.length; i++ ) {
                children[i].className = '';
            }

            if (el) {
                el.className = 'on';
            } 
        },
        removeMarker() {
            for ( var i = 0; i < this.aroundMarkers.length; i++ ) {
                this.aroundMarkers[i].setMap(null);
            }   
            this.aroundMarkers = [];
        },
        searchPlaces() {
            if (!this.currCategory) {
                return;
            }
            
            // 커스텀 오버레이를 숨깁니다 
            this.placeOverlay.setMap(null);

            // 지도에 표시되고 있는 마커를 제거합니다
            this.removeMarker();
            
            this.ps.categorySearch(this.currCategory, this.placesSearchCB, {useMapBounds:true}); 
        },
        placesSearchCB(data, status, pagination) { // eslint-disable-line no-unused-vars
            if (status === kakao.maps.services.Status.OK) {

                // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
                this.displayPlaces(data);
            } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
                // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요

            } else if (status === kakao.maps.services.Status.ERROR) {
                // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
                
            }
        },
        displayPlaces(places) {

            // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
            // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
            var order = document.getElementById(this.currCategory).getAttribute('data-order');

            for ( var i=0; i<places.length; i++ ) {

                    // 마커를 생성하고 지도에 표시합니다
                    var marker = this.addMarker(new kakao.maps.LatLng(places[i].y, places[i].x), order);

                    // 마커와 검색결과 항목을 클릭 했을 때
                    // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
                    (function(marker, place, func) {
                        kakao.maps.event.addListener(marker, 'click', function() {
                            func(place);
                        });
                    })(marker, places[i], this.displayPlaceInfo);
            }
        },
        addMarker(position, order) {
            var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
                imageSize = new kakao.maps.Size(27, 28),  // 마커 이미지의 크기
                imgOptions =  {
                    spriteSize : new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
                    spriteOrigin : new kakao.maps.Point(46, (order*36)), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
                    offset: new kakao.maps.Point(11, 28) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
                },
                markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
                marker = new kakao.maps.Marker({
                    position: position, // 마커의 위치
                    image: markerImage 
                });

            marker.setMap(this.map); // 지도 위에 마커를 표출합니다
            this.aroundMarkers.push(marker);  // 배열에 생성된 마커를 추가합니다

            return marker;
        },
        displayPlaceInfo (place) {
            var content = '<div class="placeinfo">' +
                            '   <a class="title" href="' + place.place_url + '" target="_blank" title="' + place.place_name + '">' + place.place_name + '</a>';   

            if (place.road_address_name) {
                content += '    <span title="' + place.road_address_name + '">' + place.road_address_name + '</span>' +
                            '  <span class="jibun" title="' + place.address_name + '">(지번 : ' + place.address_name + ')</span>';
            }  else {
                content += '    <span title="' + place.address_name + '">' + place.address_name + '</span>';
            }                
        
            content += '    <span class="tel">' + place.phone + '</span>' + 
                        '</div>' + 
                        '<div class="after"></div>';

            this.contentNode.innerHTML = content;
            this.placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
            this.placeOverlay.setMap(this.map);  
        },

        afterGeocoding() {
            var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 

            //기존 마커를 삭제합니다
            if(this.markers) {
                for(let marker of this.markers) {
                    marker.setMap(null);
                }
                this.markers = [];
            }

            for (var i = 0; i < this.positions.length; i ++) {
                // 마커 이미지의 이미지 크기 입니다
                var imageSize = new kakao.maps.Size(24, 35); 
                
                // 마커 이미지를 생성합니다    
                var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 

                if(i == 0) {
                    this.map.setCenter(this.positions[i].latlng);
                }

                // 마커를 생성합니다
                var marker = new kakao.maps.Marker({
                    map: this.map, // 마커를 표시할 지도
                    position: this.positions[i].latlng, // 마커를 표시할 위치
                    // title : this.positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
                    image : markerImage // 마커 이미지
                });
                marker.aptInfo = this.positions[i].aptInfo;

                var infowindow = new kakao.maps.InfoWindow({
                    // content: '<div style="width: 200px; padding: 5px;">'+'<div style="color: black; border-bottom: 1px solid black; width: 160px; overflow: hidden; white-space: nowrap; text-overflow: ellipsis; margin: 0 0 10px 0; padding: 5px">' + this.positions[i].title + '</div><div style="color: black; padding: 5px">' + this.positions[i].title + '입니다. 글이 늘어나면 어떻게 될까요? 같이 늘어나 줄까요?</div></div>',
                    // removable: true
                    content: '<div style="color: black">' + this.positions[i].title + '</div>'
                });

                kakao.maps.event.addListener(marker, 'mouseover', this.makeOverListener(this.map, marker, infowindow));
                kakao.maps.event.addListener(marker, 'mouseout', this.makeOutListener(infowindow));
                kakao.maps.event.addListener(marker, 'click', this.clickListener(this.map, marker));
                this.markers.push(marker);
            }


        },
        clickListener(map, marker) {
            return () => {
                map.setCenter(marker.getPosition());
                axios({
                    methods: 'GET',
                    url: `http://localhost:9999/happyhouse/api/list/deal?aptId=${marker.aptInfo.aptId}&pageNum=1`
                }).then((response) => {
                    this.$store.commit('selectedAptInfo', marker.aptInfo);
                    this.$store.commit('ssFlag', 3);
                    this.$store.commit('dealList', response.data);
                }).catch((error) => {
                    console.log(error);
                });
            }
        },
        makeOverListener(map, marker, infowindow) {
            return () => {
                infowindow.open(map, marker);
                this.$store.commit('mouseOverAptId', marker.aptInfo.aptId);
            }
        },
        makeOutListener(infowindow) {
            return () => {
                infowindow.close();
                this.$store.commit('mouseOverAptId', 0);
            }
        },
        aroundSearch(y, x) {
            this.$store.commit('setSelectedApt', new kakao.maps.LatLng(y, x));
            this.$store.commit('setAroundSearchFlag', true);
        },
    },
    computed: {
        aptList() {
            return this.$store.getters.aptList;
        },
        mouseOverAptId() {
            return this.$store.getters.mouseOverAptId;
        },
        ssFlag() {
            return this.$store.getters.ssFlag;
        }
    },
    watch: {
        aptList(value) {
            // 주소-좌표 변환 객체를 생성합니다
            var geocoder = new kakao.maps.services.Geocoder();
            let positions = [];
            let errorList = [];

            // 주소로 좌표를 검색합니다

            let total = value.length;
            let counter = 0;

            for(let apt of value){
                geocoder.addressSearch(apt.roadNameAddress, (result, status) => {
                    // 정상적으로 검색이 완료됐으면 
                    if (status === kakao.maps.services.Status.OK) {
                        let position = {};
                        position.title = apt.aptName;
                        position.latlng = new kakao.maps.LatLng(result[0].y, result[0].x);
                        position.aptInfo = apt;
                        positions.push(position);
                    } else {
                        console.log(apt);
                          axios({
                            method: 'POST',
                            url: `http://localhost:9999/happyhouse/api/error/geocoding`,
                            data: {
                                status: 'reported',
                                aptId: apt.aptId
                            }
                        }).then((response) => {
                            console.log(response.data)
                        }).catch((error) => {
                            console.log(error);
                        });
                        errorList.push(apt.aptId);
                    }
                    counter++;
                    if(counter == total){
                        this.$store.commit('errorList', errorList);
                        this.positions = positions;
                        this.afterGeocoding();
                    }
                })
            }
        },
        mouseOverAptId(value) {
            let imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png"; 
            for(const marker of this.markers) {
                if(marker.aptInfo.aptId == value){
                    let imageSize = new kakao.maps.Size(48, 70); 
                    let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 

                    marker.setImage(markerImage);
                } else {
                    let imageSize = new kakao.maps.Size(24, 35);
                    let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 

                    marker.setImage(markerImage);
                }
            }
        },
        ssFlag(value) {
            if(value == 3) {
                for(let marker of this.markers) {
                    if(marker.aptInfo.aptId != this.$store.getters.selectedAptInfo.aptId){
                        marker.setMap(null);
                    }
                }
            } else {
                for(let marker of this.markers) {
                    marker.setMap(this.map);
                }
                for(let marker of this.aroundMarkers) {
                    marker.setMap(null);
                }
                this.arountMarkers = [];
                document.querySelectorAll('.on').forEach((el) => {
                    el.classList.remove('on');
                })
            }
        }
    }
}
</script>

<style scoped>
#map-section * {
    margin: 0;
    padding: 0;
    font-family: 'Malgun Gothic', dotum, '돋움', sans-serif;
    font-size: 12px;
    color: black;
}

#map-section {
    position: relative;
    height: 100%;
    margin-left: 450px;
}

#map{
    width: 100%;
    height: 100%;
}

.infowindow {
    color: black;
}
/* #map-section, #map-section * {margin:0; padding:0;font-family:'Malgun Gothic',dotum,'돋움',sans-serif;font-size:12px;color: black;} */
#category {position:absolute;top:10px;left:10px;border-radius: 5px;border:1px solid #909090;box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);background: #fff;overflow: hidden;z-index: 2;}
#category li {float:left;list-style: none;width:50px;border-right:1px solid #acacac;padding:6px 0;text-align: center; cursor: pointer;}
#category li.on {background: #eee;}
#category li:hover {background: #ffe6e6;border-left:1px solid #acacac;margin-left: -1px;}
#category li:last-child{margin-right:0;border-right:0;}
#category li span {display: block;margin:0 auto 3px;width:27px;height: 28px;pointer-events: none;}
#category li .category_bg {background:url(https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png) no-repeat;}
#category li .bank {background-position: -10px 0;}
#category li .mart {background-position: -10px -36px;}
#category li .pharmacy {background-position: -10px -72px;}
#category li .oil {background-position: -10px -108px;}
#category li .cafe {background-position: -10px -144px;}
#category li .store {background-position: -10px -180px;}
#category li.on .category_bg {background-position-x:-46px;}

#category.hidden {display: none;}

</style>