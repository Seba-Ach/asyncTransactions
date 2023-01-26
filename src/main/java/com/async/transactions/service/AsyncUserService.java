package com.async.transactions.service;

import com.async.transactions.entity.UserEntity;
import com.async.transactions.repository.UserRepository;
import org.apache.commons.collections4.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsyncUserService {

    Logger logger = LoggerFactory.getLogger(AsyncUserService.class);


    private final UserRepository userRepository;

    public AsyncUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Async
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void changeDB() {
        List<UserEntity> allRead = IterableUtils.toList(userRepository.findAll());

        long threadId = Thread.currentThread().getId();

        int activeAppointments = allRead.get(0).getActiveAppointments();

        logger.info("Changed active appts to: " + threadId + " from: " + activeAppointments);
        for (UserEntity user : allRead) {
            user.setActiveAppointments((int) threadId);
        }

        logger.info(threadId + " will save");
        userRepository.saveAll(allRead);
        logger.info(threadId + " has saved");

    }

}
