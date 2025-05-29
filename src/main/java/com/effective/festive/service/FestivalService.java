package com.effective.festive.service;

import com.effective.festive.model.Festival;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

@Service
public class FestivalService {
    private static final String CSV_FILE_PATH = "busan_festivals.csv";

    //CSV 파일에서 전체 축제 목록 가져옴
    public List<Festival> getAllFestivals() throws Exception {
        ClassPathResource resource = new ClassPathResource(CSV_FILE_PATH);

        try (InputStreamReader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
            List<Festival> festivals = new CsvToBeanBuilder<Festival>(reader)
                    .withType(Festival.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build()
                    .parse();

            return festivals;
        }
    }

    //날짜 기준 오름차순 조회 (운영기간 기준) - 오늘 이후 축제만
    public List<Festival> getUpcomingFestivals() throws Exception {
        LocalDate today = LocalDate.now();
        
        return getAllFestivals()
                .stream()
                .filter(f -> f.getOperatingPeriod() != null && !f.getOperatingPeriod().trim().isEmpty())
                .filter(f -> isUpcomingFestival(f.getOperatingPeriod(), today))
                .sorted((f1, f2) -> compareFestivalDates(f1.getOperatingPeriod(), f2.getOperatingPeriod()))
                .collect(Collectors.toList());
    }

    // ID 리스트로 개별 축제 조회 (ID는 축제 고유번호(seq))
    public List<Festival> getFestivalById(List<String> ids) throws Exception {
        return getAllFestivals()
                .stream()
                .filter(f -> ids.contains(f.getSeq()))
                .collect(Collectors.toList());
    }

    //단일 축제 상세
    public Festival getFestivalById(String id) throws Exception {
        return getAllFestivals()
                .stream()
                .filter(f -> f.getSeq().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("축제를 찾을 수 없습니다"));
    }

    // 축제가 다가오는 축제인지 확인 (오늘 이후)
    private boolean isUpcomingFestival(String operatingPeriod, LocalDate today) {
        LocalDate startDate = extractStartDate(operatingPeriod);
        if (startDate == null) {
            // 매년 개최되는 축제는 포함
            return operatingPeriod.contains("매년");
        }
        return !startDate.isBefore(today);
    }

    // 축제 날짜 비교 함수
    private int compareFestivalDates(String period1, String period2) {
        LocalDate date1 = extractStartDate(period1);
        LocalDate date2 = extractStartDate(period2);
        
        // 매년 개최되는 축제는 뒤로 정렬
        if (date1 == null && date2 == null) return 0;
        if (date1 == null) return 1;
        if (date2 == null) return -1;
        
        return date1.compareTo(date2);
    }

    // 운영기간에서 시작 날짜 추출
    private LocalDate extractStartDate(String operatingPeriod) {
        if (operatingPeriod == null || operatingPeriod.trim().isEmpty()) {
            return null;
        }

        // 매년 개최되는 축제
        if (operatingPeriod.contains("매년")) {
            return null;
        }

        // 날짜 추출 정규식
        Pattern datePattern = Pattern.compile("(\\d{4})[.\\s]+(\\d{1,2})[.\\s]+(\\d{1,2})");
        Matcher matcher = datePattern.matcher(operatingPeriod);
        
        if (matcher.find()) {
            try {
                int year = Integer.parseInt(matcher.group(1));
                int month = Integer.parseInt(matcher.group(2));
                int day = Integer.parseInt(matcher.group(3));
                return LocalDate.of(year, month, day);
            } catch (Exception e) {
                // 파싱 실패시 null 반환
                return null;
            }
        }

        return null;
    }
}