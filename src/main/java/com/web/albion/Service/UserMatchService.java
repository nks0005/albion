package com.web.albion.Service;

import com.web.albion.Mapper.UserMatchMapper;
import com.web.albion.dto.UserMatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMatchService {

    @Autowired
    UserMatchMapper usermatchmapper;

    public int insertUserMatch(UserMatchDto usermatch){
        return usermatchmapper.insertUserMatch(usermatch);
    }

}
