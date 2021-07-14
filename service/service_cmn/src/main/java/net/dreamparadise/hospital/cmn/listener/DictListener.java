package net.dreamparadise.hospital.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import net.dreamparadise.hospital.cmn.mapper.DictMapper;
import net.paradise.hospital.model.cmn.Dict;
import net.paradise.hospital.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

public class DictListener extends AnalysisEventListener<DictEeVo> {

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    private DictMapper dictMapper;

    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo,dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
