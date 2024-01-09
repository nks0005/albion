package com.web.albion.Service;

import com.web.albion.Mapper.MetaCompSummaryMapper;
import com.web.albion.dto.MetaCompSummaryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetaCompSummaryService {
    @Autowired
    MetaCompSummaryMapper metacompsummaryMapper;

    public List<MetaCompSummaryDto> getDuoComp(int offset) {
        return metacompsummaryMapper.getDuoComp(offset);
    }

    public List<MetaCompSummaryDto> getFiveComp(int offset) {
        return metacompsummaryMapper.getFiveComp(offset);
    }

    public List<MetaCompSummaryDto> getTenComp(int offset) {
        return metacompsummaryMapper.getTenComp(offset);
    }
}
