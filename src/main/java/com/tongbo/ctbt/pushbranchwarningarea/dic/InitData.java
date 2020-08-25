package com.tongbo.ctbt.pushbranchwarningarea.dic;


import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaAlarm;
import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaControlInfo;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 只执行一次的与定时执行的，初始化的数据。
 *
 * @author lenovo
 */
public class InitData {
    /**
     * 警戒区域
     */
    public static Map<Integer, AreaControlInfo> AreaControlMap = new ConcurrentHashMap<>();
    /**
     * 用户船舶关系表
     */
    public static Map<Integer, List<Integer>> shipsToUserMap = new ConcurrentHashMap<>();

    /**
     *
     */
    public static Map<Integer, Map<Integer, AreaAlarm>> ShipAreaAlarmMap = new ConcurrentHashMap<>();
}
