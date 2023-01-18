package com.mimiter.mgs.admin.service.impl;

import com.mimiter.mgs.admin.repository.MGSUserRepository;
import com.mimiter.mgs.admin.service.MGSUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Service("mgsUserService")
@RequiredArgsConstructor
public class MGSUserServiceImpl implements MGSUserService {

    private final MGSUserRepository mgsUserRepository;

    @Override
    public int getNewUserCount(Long museumId, LocalDate date) {
        return mgsUserRepository.getNewUserCount(museumId, date);
    }

    @Override
    public Map<LocalDate, Integer> getNewUserCount(Long museumId, LocalDate beginDate, LocalDate endDate) {
        Map<LocalDate, Integer> dateWithNewUserCount = new HashMap<>();
        for (LocalDate date = beginDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
            dateWithNewUserCount.put(date, mgsUserRepository.getNewUserCount(museumId, date));
        }
        return dateWithNewUserCount;
    }

}
