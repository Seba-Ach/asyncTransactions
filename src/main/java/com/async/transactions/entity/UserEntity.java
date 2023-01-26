package com.async.transactions.entity;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;


@Data
@RequiredArgsConstructor
@Entity
@Table(name = "user_entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column
    private String fname;

    @Column
    private String lname;

    @Column
    private int activeAppointments;


    public UserEntity(String fname, String lname, int activeAppointments) {
        this.fname = fname;
        this.lname = lname;
        this.activeAppointments = activeAppointments;
    }

}
