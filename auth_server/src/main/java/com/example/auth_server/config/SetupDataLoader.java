package com.example.auth_server.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import com.example.auth_server.entity.Privilege;
import com.example.auth_server.entity.Role;
import com.example.auth_server.entity.User;
import com.example.auth_server.repository.PrivilegeRepository;
import com.example.auth_server.repository.RoleRepository;
import com.example.auth_server.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent>{

    boolean alreadySetup = false;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PrivilegeRepository privilegeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(alreadySetup)
            return;
        Privilege readPrivilege = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        createRoleIfNotFound("ROLE_ADMIN", Arrays.asList(readPrivilege, writePrivilege));
        createRoleIfNotFound("ROLE_USER", Collections.singletonList(readPrivilege));
        createAdminIfNotFound();
        alreadySetup = true;

    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name){
        Privilege privilege = privilegeRepository.findByName(name);
        if(privilege ==null){
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges){
        Role role = roleRepository.findByName(name);
        if(role == null){
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }

    @Transactional
    User createAdminIfNotFound(){
        Role Adminrole = roleRepository.findByName("ROLE_ADMIN");
        User user = userRepository.findByUsername("admin");
        if(user == null){
            user = new User();
            user.setUsername("admin");
            user.setRealName("系统管理员");
            user.setPassword("{noop}abc");
            user.setEmail("192167@qq.com");
            user.setIsEnabled(true);
            user.setRoles(Collections.singletonList(Adminrole));
            userRepository.save(user);
        }
        return user;
    }

    
}
