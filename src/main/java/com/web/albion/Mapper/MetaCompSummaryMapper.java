package com.web.albion.Mapper;

import com.web.albion.dto.MetaCompSummaryDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MetaCompSummaryMapper {
    public List<MetaCompSummaryDto> getDuoComp(int offset);

    public List<MetaCompSummaryDto> getFiveComp(int offset);

    public List<MetaCompSummaryDto> getTenComp(int offset);
}
