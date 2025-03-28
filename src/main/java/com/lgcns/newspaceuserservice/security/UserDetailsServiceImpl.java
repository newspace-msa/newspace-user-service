package com.lgcns.newspaceuserservice.security;

import com.lgcns.newspaceuserservice.entity.User;
import com.lgcns.newspaceuserservice.exception.UserException;
import com.lgcns.newspaceuserservice.exception.UserResponseStatus;
import com.lgcns.newspaceuserservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;
    
    // 유저 이름으로 레포지토리에서 찾아내는 클래스
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        User user = userRepository.findByUsername(username).orElseThrow(
                ()-> new UserException(UserResponseStatus.USER_NOT_FOUND));
        return new UserDetailsImpl(user);
    }
}
