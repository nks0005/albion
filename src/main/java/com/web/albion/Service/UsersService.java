package com.web.albion.Service;

import com.web.albion.Mapper.UsersMapper;
import com.web.albion.dto.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersMapper mapper;

    public Users getUserByUsername(String name) {
        return mapper.getUserByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = mapper.getUserByUsername(username);

        if(user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
