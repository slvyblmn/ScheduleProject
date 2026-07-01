package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

    // 생성
    public ScheduleResponseDto create(ScheduleRequestDto dto) {

        Schedule schedule = new Schedule();

        schedule.setTitle(dto.getTitle());
        schedule.setInfo(dto.getInfo());
        schedule.setWriter(dto.getWriter());
        schedule.setPassword(dto.getPassword());

        return new ScheduleResponseDto(scheduleRepository.save(schedule));
    }

    // 전체 + 작성자 조회
    public List<ScheduleResponseDto> findAll(String writer) {

        List<Schedule> list = (writer == null || writer.isEmpty())
                ? scheduleRepository.findAllByOrderByModifiedAtDesc()
                : scheduleRepository.findByWriterOrderByModifiedAtDesc(writer);

        return list.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }

    // 단건 조회
    public ScheduleResponseDto findOne(Long id) {
        return new ScheduleResponseDto(
                scheduleRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("없음"))
        );
    }

    // 수정 (title + writer만)
    public ScheduleResponseDto update(Long id, ScheduleRequestDto dto) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없음"));

        if (!schedule.getPassword().equals(dto.getPassword())) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }

        schedule.update(dto.getTitle(), dto.getWriter());

        return new ScheduleResponseDto(scheduleRepository.save(schedule));
    }

    // 삭제
    public void delete(Long id, String password) {

        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("없음"));

        if (!schedule.getPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호 틀림");
        }

        scheduleRepository.delete(schedule);
    }
}