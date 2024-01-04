package com.web.albion.Service;

import com.web.albion.Mapper.UserAlbionMapper;
import com.web.albion.dto.UserAlbionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAlbionService {

    @Autowired
    UserAlbionMapper useralbionmapper;

    public int insertUser(UserAlbionDto user){

        Integer userid = useralbionmapper.selectUserByName(user.getName());
        if(userid == null){
            useralbionmapper.insertUser(user);
            userid = useralbionmapper.selectUserByName(user.getName());
        }

        return userid;
    }
}
