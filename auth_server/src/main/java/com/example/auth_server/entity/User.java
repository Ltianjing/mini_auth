package com.example.auth_server.entity;

import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Data
@Entity
@GenericGenerator(name = "uuid", strategy = "uuid2" )
public class User {
    @Id
    @GeneratedValue(generator = "uuid")
    @Column(length = 36)
    private String id;
    
    private String username;
    private String password;
    private String realName;
    private String email;
    private Boolean isEnabled;

    @ManyToMany
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;
  
}