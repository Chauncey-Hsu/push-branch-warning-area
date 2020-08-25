package com.tongbo.ctbt.pushbranchwarningarea.service;

import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaAlarm;
import com.tongbo.ctbt.pushbranchwarningarea.dao.primary.AreaAlarmDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaAlarmService {
    @Autowired
    AreaAlarmDao areaAlarmDao;

    public List<AreaAlarm> getAllAlarmNotLeaved() {
        return areaAlarmDao.findByLeaveTimeIsNull();
    }
}
