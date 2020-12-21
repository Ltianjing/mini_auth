package com.example.auth_server.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Data
@Entity
@GenericGenerator(name = "uuid", strategy = "uuid2" )
public class Privilege {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(length = 36)
    private String id;
    
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

    

}
