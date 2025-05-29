package com.effective.festive.service;

import com.effective.festive.model.Festival;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    //날짜 기준 오름차순 조회 (운영기간 기준)
    public List<Festival> getUpcomingFestivals() throws Exception {
        return getAllFestivals()
                .stream()
                .filter(f -> f.getOperatingPeriod() != null && !f.getOperatingPeriod().trim().isEmpty())
                .sorted(Comparator.comparing(f -> f.getOperatingPeriod()))
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


}