package com.tongbo.ctbt.pushbranchwarningarea.web;

import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaAlarm;
import com.tongbo.ctbt.pushbranchwarningarea.dic.InitData;
import com.tongbo.ctbt.pushbranchwarningarea.schedule.ShipsToUserMapRunnableImpl;
import com.tongbo.ctbt.pushbranchwarningarea.schedule.UpdatePathMapRunnableImpl;
import com.tongbo.ctbt.pushbranchwarningarea.service.AreaAlarmService;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author lenovo
 */
@Component
public class InitDataWeb {
    Logger logger = LoggerFactory.getLogger(InitData.class);

    @Autowired
    AreaAlarmService areaAlarmService;
    @Autowired
    ShipsToUserMapRunnableImpl shipsToUserMapRunnable;
    @Autowired
    UpdatePathMapRunnableImpl updatePathMapRunnable;

    public void process() {
        initAlarmData();

        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(
                4, new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(false).build());
        // 第一个参数是任务，第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间,第四个参数是时间单位
        scheduledThreadPoolExecutor.scheduleAtFixedRate(updatePathMapRunnable, 1, 300, TimeUnit.SECONDS);
        scheduledThreadPoolExecutor.scheduleAtFixedRate(shipsToUserMapRunnable, 2, 900, TimeUnit.SECONDS);
    }

    /**
     * 加载所有未完结的警戒报警
     */
    private void initAlarmData() {
        List<AreaAlarm> areaAlarmList = areaAlarmService.getAllAlarmNotLeaved();
        for (AreaAlarm areaAlarm : areaAlarmList) {
            if (InitData.ShipAreaAlarmMap.containsKey(areaAlarm.getShipId())) {
                Map<Integer, AreaAlarm> map = InitData.ShipAreaAlarmMap.get(areaAlarm.getShipId());
                map.put(areaAlarm.getAreaId(), areaAlarm);

            } else {
                Map<Integer, AreaAlarm> map = new ConcurrentHashMap<Integer, AreaAlarm>();
                map.put(areaAlarm.getAreaId(), areaAlarm);

                InitData.ShipAreaAlarmMap.put(areaAlarm.getShipId(), map);
            }
        }
        logger.info("所有未完结的警戒报警" + InitData.ShipAreaAlarmMap.toString());
    }
}
