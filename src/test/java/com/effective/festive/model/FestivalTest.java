package com.effective.festive.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class FestivalTest {

    /**
     * 테스트 전체 구조:
     * 1. Festival 객체를 모든 필드 값으로 생성
     * 2. 각 필드의 getter 메소드가 생성자에서 설정한 값을 올바르게 반환하는지 검증
     */
    @Test
    void testLombokGettersAndSetters() {
        // given
        String seq = "1";                                     // 축제 시퀀스 번호
        String title = "축제명";                                 // 축제 제목
        String district = "구군";                                 // 개최 지역 (구/군)
        String latitude = "35.0";                               // 위도 좌표
        String longitude = "129.0";                             // 경도 좌표
        String location = "장소";                                 // 개최 장소
        String subtitle = "부제목";                             // 축제 부제목
        String description = "상세설명";                          // 축제 상세 설명
        String mainPlace = "주요장소";                            // 주요 개최 장소
        String address = "주소";                                  // 축제 장소 주소
        String contact = "연락처";                                // 문의 연락처
        String homepage = "https://example.com";                 // 축제 홈페이지 URL
        String transportation = "버스";                           // 교통편 정보
        String operatingPeriod = "2025-06-01~2025-06-03";      // 축제 운영 기간
        String fee = "무료";                                      // 참가비/입장료
        String imageUrl = "https://example.com/image.jpg";       // 축제 대표 이미지 URL
        String thumbUrl = "https://example.com/thumb.jpg";       // 썸네일 이미지 URL
        String detailContent = "상세내용";                        // 축제 상세 내용
        String facilities = "화장실,주차장";                      // 편의시설 정보

        //when: Festival 객체를 모든 필드 값으로 생성
        Festival festival = new Festival(
                seq, title, district, latitude, longitude, location,
                subtitle, description, mainPlace, address, contact, homepage,
                transportation, operatingPeriod, fee, imageUrl, thumbUrl, detailContent, facilities
        );

        //hthen: 각 getter 메소드가 설정한 값을 올바르게 반환하는지 검증
        assertEquals(seq, festival.getSeq());                   // 시퀀스번호 검증
        assertEquals(title, festival.getTitle());                // 축제명
        assertEquals(district, festival.getDistrict());          // 개최지

        assertEquals(latitude, festival.getLatitude());          // 위도
        assertEquals(longitude, festival.getLongitude());        // 경도
        assertEquals(location, festival.getLocation());          // 장소

        assertEquals(subtitle, festival.getSubtitle());          // 부제목
        assertEquals(description, festival.getDescription());    // 상세설명
        assertEquals(mainPlace, festival.getMainPlace());        // 주요장소

        assertEquals(address, festival.getAddress());            //주소
        assertEquals(contact, festival.getContact());            //연락처
        assertEquals(homepage, festival.getHomepage());          // 홈페이지

        assertEquals(transportation, festival.getTransportation()); // 교통편
        assertEquals(operatingPeriod, festival.getOperatingPeriod()); //운영기간
        assertEquals(fee, festival.getFee());                    // 참가비

        assertEquals(imageUrl, festival.getImageUrl());          // 이미지 URL
        assertEquals(thumbUrl, festival.getThumbUrl());          // 썸네일 URL
        assertEquals(detailContent, festival.getDetailContent()); // 상세내용
        assertEquals(facilities, festival.getFacilities());      // 편의시설
    }

    /**
     * 축제 시작일과 종료일을 반환하는 메소드들이 정상적으로 작동하는지 테스트
     */
    @Test
    void testGetStartDateAndEndDate() {
        //given
        String period = "2025-06-01~2025-06-03";               // 2025년 6월 1일부터 3일까지
        Festival festival = new Festival();
        festival.setOperatingPeriod(period);

        //when, then: 시작일과 종료일 메소드가 운영 기간을 반환하는지 검증
        assertEquals(period, festival.getStartDate());          // 시작일 검증
        assertEquals(period, festival.getEndDate());            // 종료일 검증
    }

    /**
     * getPlace() 메소드가 주요장소(mainPlace)를 우선적으로 반환하는지 테스트
     * 
     * 비즈니스 로직: 주요장소가 있으면 주요장소를, 없으면 주소를 반환
     */
    @Test
    void testGetPlacePrefersMainPlace() {
        // given
        Festival festival = new Festival();
        festival.setMainPlace("주요장소");                       // 주요장소 설정
        festival.setAddress("주소");                            // 주소 설정

        // when, hten: getPlace()가 주요장소를 우선적으로 반환하는지 검증
        assertEquals("주요장소", festival.getPlace());           // 주요장소가 반환되어야 함
    }

    /**
     * getPlace() 메소드가 주요장소가 null일 때 주소를 반환하는지 테스트
     * 
     * 비즈니스 로직: 주요장소가 null이면 주소를 fallback으로 사용
     */
    @Test
    void testGetPlaceWhenMainPlaceIsNull() {
        //given: 주요장소는 null이고 주소만 설정된 Festival 객체
        Festival festival = new Festival();
        festival.setMainPlace(null);                            // 주요장소를  null로 설정
        festival.setAddress("주소");                            // 주소 설정

        // when, then: getPlace()가 주소를 반환하는지 검증
        assertEquals("주소", festival.getPlace());              // 주소가 반환되어야 함
    }

    /**
     * getUrl() 메소드가 홈페이지 URL을 정상적으로 반환하는지 테스트
     */
    @Test
    void testGetUrl() {
        //given
        Festival festival = new Festival();
        festival.setHomepage("https://example.com");           // 홈페이지 URL 설정

        // when, then: getUrl()이 홈페이지 URL을 반환하는지 검증
        assertEquals("https://example.com", festival.getUrl()); // 홈페이지 URL이 반환되어야 함
    }
}
