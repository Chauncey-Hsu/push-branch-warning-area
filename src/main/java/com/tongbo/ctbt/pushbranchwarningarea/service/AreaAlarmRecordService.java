package com.tongbo.ctbt.pushbranchwarningarea.service;

import com.tongbo.ctbt.pushbranchwarningarea.bean.secondary.AreaAlarmRecord;
import com.tongbo.ctbt.pushbranchwarningarea.dao.secondary.AreaAlarmRecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaAlarmRecordService {

    @Autowired
    private AreaAlarmRecordDao areaAlarmRecordDao;

    /**
     * test
     * @param id
     * @return
     */
    public AreaAlarmRecord getAreaAlarmRecordById(Long id){
        return areaAlarmRecordDao.getOne(id);
    }

}
