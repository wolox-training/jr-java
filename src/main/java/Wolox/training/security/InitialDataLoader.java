package Wolox.training.security;

import Wolox.training.models.Privilege;
import Wolox.training.models.Role;
import Wolox.training.models.User;
import Wolox.training.repositories.PrivilegeRepository;
import Wolox.training.repositories.RoleRepository;
import Wolox.training.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (!alreadySetup) {
            Privilege readPrivilege = findPrivilegeOrCreate("READ_PRIVILEGE");
            Privilege writePrivilege = findPrivilegeOrCreate("WRITE_PRIVILEGE");
            List<Privilege> adminPrivileges = Arrays.asList(readPrivilege, writePrivilege);
            findRoleOrCreate("ROLE_ADMIN", adminPrivileges);
            findRoleOrCreate("ROLE_USER", Arrays.asList(readPrivilege));
            Role adminRole = roleRepository.findByName("ROLE_ADMIN").get();
            User user = new User();
            user.setName("name");
            user.setUsername("username");
            user.setPassword(passwordEncoder.encode("password"));
            user.setRoles(Arrays.asList(adminRole));
            user.setEnabled(true);
            userRepository.save(user);
            alreadySetup = true;
        }
    }

    @Transactional
    private Privilege findPrivilegeOrCreate(String name) {
        Optional<Privilege> privilegeOpt = privilegeRepository.findByName(name);
        Privilege privilege;
        if (!privilegeOpt.isPresent()) {
            privilege = new Privilege();
            privilege.setName(name);
            privilegeRepository.save(privilege);
        } else {
            privilege = privilegeOpt.get();
        }
        return privilege;
    }

    @Transactional
    private Role findRoleOrCreate(String name, Collection<Privilege> privileges) {
        Optional<Role> roleOpt = roleRepository.findByName(name);
        Role role;
        if (!roleOpt.isPresent()) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        } else {
            role = roleOpt.get();
        }
        return role;
    }
}