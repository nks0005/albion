package com.web.albion.Service;

import com.web.albion.Mapper.GearsMapper;
import com.web.albion.dto.Gears;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GearsService {

    @Autowired
    GearsMapper gearsmapper;

    public int insertGearSet(Gears gear){
        //System.out.println("gear name : " + gear.getName());

        Integer gearid = gearsmapper.selectGearByName(gear.getName());
        if(gearid == null){
            gearsmapper.insertGear(gear);
            gearid = gearsmapper.selectGearByName(gear.getName());
        }

        //System.out.println(gearid);

        return gearid;
    }
}
