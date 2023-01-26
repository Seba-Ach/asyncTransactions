package com.async.transactions.repository;

import com.async.transactions.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<UserEntity> findAll();
//
//    @Override
//    @Lock(LockModeType.PESSIMISTIC_READ)
//    <S extends UserEntity> List<S> saveAll(Iterable<S> entities);
}
