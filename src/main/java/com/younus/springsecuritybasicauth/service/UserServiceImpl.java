package com.younus.springsecuritybasicauth.service;

import com.younus.springsecuritybasicauth.domain.UserDto;
import com.younus.springsecuritybasicauth.entity.RoleEntity;
import com.younus.springsecuritybasicauth.entity.UserEntity;
import com.younus.springsecuritybasicauth.repository.RoleRepository;
import com.younus.springsecuritybasicauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public String saveUser(UserDto userDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "auth/registration";
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setFullName(userDto.getFullName());
        userEntity.setEmail(userDto.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setRoles(Arrays.asList(new RoleEntity("ROLE_ADMIN")));

        userRepository.save(userEntity);

        return "redirect:/register?success";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);

        if(user == null) {
            throw new UsernameNotFoundException("Invalid email or password!");
        }

        return new User(user.getEmail(), user.getPassword(), mapRoles(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRoles(Collection<RoleEntity> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}
