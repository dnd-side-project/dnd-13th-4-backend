package com.example.wini.domain.member.service;

import com.example.wini.domain.member.domain.Status;
import com.example.wini.domain.member.dto.response.StatusResponse;
import com.example.wini.domain.member.repository.StatusRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    @Transactional(readOnly = true)
    public List<StatusResponse> getStatuses() {
        List<Status> statuses = statusRepository.findAll();
        return statuses.stream().map(StatusResponse::from).toList();
    }
}
