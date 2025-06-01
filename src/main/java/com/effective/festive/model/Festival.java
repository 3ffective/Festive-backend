package com.effective.festive.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //getter, setter 자동 생성
@NoArgsConstructor //기본 생성자 자동 생성
@AllArgsConstructor //모든 필드를 인자로 받는 생성자 자동 생성
public class Festival {
    @CsvBindByName(column = "\uFEFF콘텐츠ID")
    private String seq; //축제 고유 ID

    @CsvBindByName(column = "콘텐츠명")
    private String title; //축제명

    @CsvBindByName(column = "구군")
    private String district; //구군

    @CsvBindByName(column = "위도")
    private String latitude; //위도

    @CsvBindByName(column = "경도")
    private String longitude; //경도

    @CsvBindByName(column = "장소")
    private String location; //장소

    @CsvBindByName(column = "제목")
    private String subtitle; //제목

    @CsvBindByName(column = "부제목")
    private String description; //부제목

    @CsvBindByName(column = "주요장소")
    private String mainPlace; //주요장소

    @CsvBindByName(column = "주소")
    private String address; //주소

    @CsvBindByName(column = "연락처")
    private String contact; //연락처

    @CsvBindByName(column = "홈페이지")
    private String homepage; //홈페이지

    @CsvBindByName(column = "교통정보")
    private String transportation; //교통정보

    @CsvBindByName(column = "운영기간")
    private String operatingPeriod; //운영기간

    @CsvBindByName(column = "이용요금")
    private String fee; //이용요금

    @CsvBindByName(column = "이미지URL")
    private String imageUrl; //이미지URL

    @CsvBindByName(column = "썸네일이미지URL")
    private String thumbUrl; //썸네일이미지URL

    @CsvBindByName(column = "상세내용")
    private String detailContent; //상세내용

    @CsvBindByName(column = "편의시설")
    private String facilities; //편의시설

    // 기존 API 호환성을 위한 메서드들
    public String getStartDate() {
        return operatingPeriod;
    }

    public String getEndDate() {
        return operatingPeriod;
    }

    public String getPlace() {
        return mainPlace != null ? mainPlace : address;
    }

    public String getUrl() {
        return homepage;
    }
}
