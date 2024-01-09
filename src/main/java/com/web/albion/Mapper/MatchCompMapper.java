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



    public int insertFiveComp(FiveCompDto fivecompdto);

    public int insertTenComp(TenCompDto tencompdto);


}
