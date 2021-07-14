package net.dreamparadise.hospital.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.paradise.hospital.model.cmn.Dict;
import net.paradise.hospital.model.hosp.HospitalSet;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface DictService extends IService<Dict> {
    List<Dict> findChildData(Long id);

    void exportDictData(HttpServletResponse response);

    void importDictData(MultipartFile file);
}
