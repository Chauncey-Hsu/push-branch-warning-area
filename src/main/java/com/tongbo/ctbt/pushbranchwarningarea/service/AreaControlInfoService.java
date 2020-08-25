package com.tongbo.ctbt.pushbranchwarningarea.service;

import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaControlInfo;
import com.tongbo.ctbt.pushbranchwarningarea.dao.primary.AreaControlInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AreaControlInfoService {
    @Autowired
    AreaControlInfoDao areaControlInfoDao;

    public List<AreaControlInfo> getAll() {
        return areaControlInfoDao.findAll();
    }
}
