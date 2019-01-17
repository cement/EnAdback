package com.en.adback.serviceimp.sys;

import com.en.adback.entity.sys.FileHost;
import com.en.adback.mapper.sys.FileHostMapper;
import com.en.adback.service.sys.FileHostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/12/5.
 */
@Service
public class FileHostServiceImp implements FileHostService {

    @Autowired
    private FileHostMapper mapper;

    @Override
    public List<FileHost> getFileHostList() {
        return mapper.getFileHostList();
    }

    @Override
    public int insertFileHost(FileHost fileHost) {
        return mapper.insertFileHost(fileHost);
    }
}
