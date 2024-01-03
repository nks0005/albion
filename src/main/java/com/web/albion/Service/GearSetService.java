package com.web.albion.Service;

import com.web.albion.Mapper.GearsMapper;
import com.web.albion.Mapper.GearsSetMapper;
import com.web.albion.dto.Gears;
import com.web.albion.dto.GearsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GearSetService {

    @Autowired
    GearsSetMapper gearssetmapper;

    public int insertGearSet(GearsSet gearset) {

        Integer gearsetid = gearssetmapper.selectGearsetByGear(gearset);
        if (gearsetid == null) {
            gearssetmapper.insertGearset(gearset);
            gearsetid = gearssetmapper.selectGearsetByGear(gearset);
        }

        return gearsetid;
    }
}
