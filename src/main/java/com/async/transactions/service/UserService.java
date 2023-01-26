package com.async.transactions.service;

import com.async.transactions.entity.AppointmentEntity;
import com.async.transactions.entity.UserEntity;
import com.async.transactions.repository.AppointmentRepository;
import com.async.transactions.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final AppointmentRepository appointmentRepository;
    private final AsyncUserService asyncUserService;

    public UserService(UserRepository userRepository, AppointmentRepository appointmentRepository, AsyncUserService asyncUserService) {
        this.userRepository = userRepository;
        this.appointmentRepository = appointmentRepository;
        this.asyncUserService = asyncUserService;
    }


    @Transactional
    public void createAppointmentForUser(int id) {

        UserEntity userEntity = userRepository.findById(id).get();

        int activeAppointments = userEntity.getActiveAppointments();
        activeAppointments++;
        userEntity.setActiveAppointments(activeAppointments);

        logger.info("Incrementing active appointments for user " + userEntity.getFname());
        userRepository.save(userEntity);
        logger.info("Increment of active appoints successful for user " + userEntity.getFname() + " to " + userEntity.getActiveAppointments());
        logger.info("User has " + userEntity.getActiveAppointments() + " active appointments");

        AppointmentEntity appointmentEntity = new AppointmentEntity();
        appointmentEntity.setFname(userEntity.getFname());

        List<AppointmentEntity> appointmentEntities = appointmentRepository.findAllByfname(userEntity.getFname());

        if (appointmentEntities.size() > 5) {
            throw new IllegalArgumentException();
        }

        logger.info("Creating appointment for user");
        appointmentRepository.save(appointmentEntity);
        logger.info("Created appointment successfully");

    }

    public void triggerAsyncTransactions() {
        for (int i = 0; i < 5; i++) {
            asyncUserService.changeDB();
            logger.info("triggered changeDB for " + i);
        }
    }

    public void setUpDB() {
        List<UserEntity> populateDB = generateUsers();
        userRepository.saveAll(populateDB);
    }

    private List<UserEntity> generateUsers() {
        List<UserEntity> userList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            userList.add(new UserEntity("fnameOf", String.valueOf(i), 0));
        }
        return userList;
    }


}
