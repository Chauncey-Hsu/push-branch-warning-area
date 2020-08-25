package com.tongbo.ctbt.pushbranchwarningarea;

import com.tongbo.ctbt.pushbranchwarningarea.bean.secondary.AreaAlarmRecord;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class TestLombok {
    @Test
    public void lombokTest(){
        AreaAlarmRecord areaAlarmRecord = new AreaAlarmRecord();
        areaAlarmRecord.setEntertime(new Date());
        System.out.println(areaAlarmRecord);
    }
}
