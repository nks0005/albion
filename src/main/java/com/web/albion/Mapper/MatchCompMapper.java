package com.web.albion.Mapper;


import com.web.albion.dto.DuoCompDto;
import com.web.albion.dto.FiveCompDto;
import com.web.albion.dto.TenCompDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchCompMapper {

    public int insertDuoComp(DuoCompDto duocompdto);
    public int updateDuoComp(DuoCompDto duocompdto);
    public DuoCompDto selectDuoComp(DuoCompDto duocompdto);


    public int insertFiveComp(FiveCompDto compdto);
    public int updateFiveComp(FiveCompDto compdto);
    public FiveCompDto selectFiveComp(FiveCompDto compdto);


    public int insertTenComp(TenCompDto compdto);
    public int updateTenComp(TenCompDto compdto);
    public TenCompDto selectTenComp(TenCompDto compdto);



    // Get Meta


}
