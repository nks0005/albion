package com.web.albion.Service;

import com.web.albion.Mapper.GearsSetMapper;
import com.web.albion.dto.GearsSetDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GearSetService {

    @Autowired
    GearsSetMapper gearssetmapper;

    public int insertGearSet(GearsSetDto gearset) {

        Integer gearsetid = gearssetmapper.selectGearsetByGear(gearset);
        if (gearsetid == null) {
            gearssetmapper.insertGearset(gearset);
            gearsetid = gearssetmapper.selectGearsetByGear(gearset);
        }

        return gearsetid;
    }
}
