package com.tongbo.ctbt.pushbranchwarningarea.service;

import com.google.gson.Gson;
import com.tongbo.ctbt.pushbranchwarningarea.bean.MqMessageDo;
import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaAlarm;
import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaControlInfo;
import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.Location;
import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.Ships;
import com.tongbo.ctbt.pushbranchwarningarea.bean.secondary.AreaAlarmRecord;
import com.tongbo.ctbt.pushbranchwarningarea.dao.primary.AreaAlarmDao;
import com.tongbo.ctbt.pushbranchwarningarea.dao.secondary.AreaAlarmRecordDao;
import com.tongbo.ctbt.pushbranchwarningarea.dic.InitData;
import com.tongbo.ctbt.pushbranchwarningarea.util.Geometry;
import com.tongbo.ctbt.pushbranchwarningarea.util.GeometryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 徐传奇
 * @Version 2
 */
@Service
public class LocMqService {
    Logger logger = LoggerFactory.getLogger(LocMqService.class);

    @Autowired
    AreaAlarmRecordDao areaAlarmRecordDao;
    @Autowired
    AreaAlarmDao areaAlarmDao;
    private static Gson gson = new Gson();

    public void handler(String message) {
        try {
            MqMessageDo mqMessageDo = gson.fromJson(message, MqMessageDo.class);
            saveShipAreaAlarm(mqMessageDo.getShips(), mqMessageDo.getLocation());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断并处理 船舶触发警戒区域
     *
     * @param ship
     * @param location
     */
    private void saveShipAreaAlarm(Ships ship, Location location) {
        //地理坐标系 转 火星坐标系
//		double[] d1 = CoordinateUtil.WGS84ToGCJ02(new double[] {ship.getLongitude(), ship.getLatitude()});
//		double[] d2 = CoordinateUtil.WGS84ToGCJ02(new double[] {location.getLongitude(), location.getLatitude()});
        // 地理坐标系 不转 火星坐标系了--
        double[] d1 = new double[]{ship.getLatitude(), ship.getLongitude()};
        double[] d2 = new double[]{location.getLatitude(), location.getLongitude()};

        Date now = new Date();
        Map<Integer, AreaAlarm> areaAlarmMap = InitData.ShipAreaAlarmMap.get(ship.getId());
        if (areaAlarmMap != null) {
            //之前就已经触发过警戒报警（areaAlarm_table），判断是不是还是在原来的警戒范围
            boolean inArea = processAreaAlarmExisted(areaAlarmMap, ship, d1, d2);
            if (inArea) {
                return;
            }
        }

        /*
         * 走出一个区域后，才能走这里。现在开始遍历每一个警戒区域，是否落入某一个
         * 检测新的警戒报警（如果已经在一个警戒区域了，则直接return，走不到这里）
         */
        Iterator<Integer> it = InitData.AreaControlMap.keySet().iterator();
        while (it.hasNext()) {
            try {

                Integer areaId = it.next();
                AreaControlInfo areaControlInfo = InitData.AreaControlMap.get(areaId);
                if (areaControlInfo == null || "9".equals(areaControlInfo.getAreaAlarmType())) {
                    //AreaAlarmType == 9,仅显示 ,不用触发 报警 和 记录
                    continue;
                }

                //areaType	1警戒区	2警戒线
                if ("1".equals(areaControlInfo.getAreaType())) {
                    //警戒区，判断是否在 区域内
                    GeneralPath gp = areaControlInfo.getGp();
                    if (gp.contains(d2[1], d2[0])) {
                        goingInArea(ship, now, areaId, areaControlInfo);
//                        break;
                    }


                } else if ("2".equals(areaControlInfo.getAreaType())) {
                    //警戒线，根据船舶的前一次报位 和 本次报位，形成一条直线，判断该直线 是否和 警戒线的 任何一段线条 相交
                    Line2D line1 = new Line2D.Double(d1[1], d1[0], d2[1], d2[0]);

                    Geometry geometry = areaControlInfo.getGeometryObj();
                    List<List<BigDecimal>> list = geometry.getPaths().get(0);
                    for (int i = 0; i < list.size(); i++) {
                        if (i < list.size() - 1) {
                            //后面还有点，尝试连线
                            List<BigDecimal> numberList1 = list.get(i);
                            List<BigDecimal> numberList2 = list.get(i + 1);
                            Point2D.Double pp1 = GeometryUtils.locatechange(numberList1.get(0), numberList1.get(1));
                            Point2D.Double pp2 = GeometryUtils.locatechange(numberList2.get(0), numberList2.get(1));
                            Line2D line2 = new Line2D.Double(pp1.x, pp1.y, pp2.x, pp2.y);

                            /*
                             判断是否相交
                             这里有个坑：如果两个线段都变为一个点，结果就是相交。
                             如果一艘船停止，前后两次的定位则有可能相同的。另一方面就是警戒区域的出现了两个相邻并相同的点。
                             */
                            if (line1.intersectsLine(line2)) {
                                // 判断 警戒区域的出现了两个相邻并相同的点，取消此次判断。
                                boolean equAreaPoint = (line2.getX1() == line2.getX2() && line2.getY1() == line2.getY2());
                                // 判断 报位的出现了两个相邻并相同的点，取消此次判断。
                                boolean equLocPoint = (line1.getX1() == line1.getX2() && line1.getY1() == line1.getY2());
                                if (equAreaPoint || equLocPoint) {
                                    continue;
                                }

                                intersectsLineing(ship, now, areaId, areaControlInfo);
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("AreaControlMap:" + InitData.AreaControlMap.toString());
                logger.error("----检测新的警戒报警--shipName:" + ship.getName() + "," + e.getMessage(), e);
            }
        }
    }

    /**
     * 与任何一段警戒区相交，发起触发警戒线操作
     *
     * @param ship
     * @param now
     * @param areaId
     * @param areaControlInfo
     */
    private void intersectsLineing(Ships ship, Date now, Integer areaId, AreaControlInfo areaControlInfo) {
        //与任何一段相交，则认为触发警戒线
        //logger.info("ship:"+ship.getId()+", line1:["+ship.getLongitude()+","+ship.getLatitude()+"]["+location.getLongitude()+","+location.getLatitude()+"], areaId:"+areaId+", line2:["+pp1.x+","+pp1.y+"]["+pp2.x+","+pp2.y+"]");
        //logger.info("触发警戒线areaId："+areaId+", ship:"+ship.getId()+", PositionDate:"+DateUtil.date2String(location.getPositionDate()));

        AreaAlarmRecord areaAlarmRecord = new AreaAlarmRecord();
        areaAlarmRecord.setAreaId(areaId);
        areaAlarmRecord.setShipId(ship.getId());
        areaAlarmRecord.setEquipmentId(ship.getEquipmentid());
        areaAlarmRecord.setEntertime(now);
        areaAlarmRecord.setLeavetime(now);//警戒线，触发即离开
        areaAlarmRecordDao.save(areaAlarmRecord);

        AreaAlarm areaAlarm = areaAlarmDao.findByShipIdAndAreaId(ship.getId(), areaId);
        if (areaAlarm != null) {
            areaAlarm.setAreaId(areaId);
            areaAlarm.setEquipmentId(ship.getEquipmentid());
            areaAlarm.setShipId(ship.getId());
            areaAlarm.setLocStatus("1");//1区域内0区域外
            areaAlarm.setEnterTime(now);
            areaAlarm.setLeaveTime(now);//警戒线，触发即离开
            if ("4".equals(areaControlInfo.getAreaAlarmType())) {
                // 出入记录
                areaAlarm.setIsVisited("0");//0无需查看 进入
            } else {
                areaAlarm.setIsVisited("1");//1需要查看
            }
            areaAlarmDao.save(areaAlarm);
        } else {
            areaAlarm = new AreaAlarm();
            areaAlarm.setAreaId(areaId);
            areaAlarm.setEquipmentId(ship.getEquipmentid());
            areaAlarm.setShipId(ship.getId());
            areaAlarm.setLocStatus("1");//1区域内0区域外
            areaAlarm.setEnterTime(now);
            areaAlarm.setLeaveTime(now);//警戒线，触发即离开
            if ("4".equals(areaControlInfo.getAreaAlarmType())) {
                // 出入记录
                areaAlarm.setIsVisited("0");//0无需查看 进入
            } else {
                areaAlarm.setIsVisited("1");//1需要查看
            }
            areaAlarmDao.save(areaAlarm);
        }
    }

    /**
     * 跑进警戒区，发起警戒操作
     *
     * @param ship
     * @param now
     * @param areaId
     * @param areaControlInfo
     */
    private void goingInArea(Ships ship, Date now, Integer areaId, AreaControlInfo areaControlInfo) {
        //跑进警戒区内, 触发警戒报警
        AreaAlarmRecord areaAlarmRecord = new AreaAlarmRecord();
        areaAlarmRecord.setAreaId(areaId);
        areaAlarmRecord.setShipId(ship.getId());
        areaAlarmRecord.setEquipmentId(ship.getEquipmentid());
        areaAlarmRecord.setEntertime(now);
        // 这个语句会产生很多的数据量。问题就在这里：这个保存Record的条件与更新RecordLeaveTime的条件要能闭合上。
        areaAlarmRecordDao.save(areaAlarmRecord);

        //AreaAlarmType	1出入报警	2进入报警	3出去报警	4出入记录。 无论哪种报警类型，全部插入或更新AreaAlarm，为的是，在出入记录时出去时能够更新到leavetime。
        AreaAlarm areaAlarm = areaAlarmDao.findByShipIdAndAreaId(ship.getId(), areaId);
        if (areaAlarm != null) {

            areaAlarm.setAreaId(areaId);
            areaAlarm.setEquipmentId(ship.getEquipmentid());
            areaAlarm.setShipId(ship.getId());
            areaAlarm.setLocStatus("1");//1区域内0区域外
            areaAlarm.setEnterTime(now);
            if ("3".equals(areaControlInfo.getAreaAlarmType())
                    || "4".equals(areaControlInfo.getAreaAlarmType())) {
                //3出去报警 4出入记录
                areaAlarm.setIsVisited("0");//0无需查看 进入
            } else {
                areaAlarm.setIsVisited("1");//1需要查看
            }
            waterwayJudgment(areaAlarm, areaControlInfo);
            areaAlarm.setLeaveTime(null);
            areaAlarmDao.save(areaAlarm);
        } else {
            areaAlarm = new AreaAlarm();
            areaAlarm.setAreaId(areaId);
            areaAlarm.setEquipmentId(ship.getEquipmentid());
            areaAlarm.setShipId(ship.getId());
            areaAlarm.setLocStatus("1");//1区域内0区域外
            areaAlarm.setEnterTime(now);
            if ("3".equals(areaControlInfo.getAreaAlarmType())
                    || "4".equals(areaControlInfo.getAreaAlarmType())) {
                //3出去报警 4出入记录
                areaAlarm.setIsVisited("0");//0无需查看 进入
            } else {
                areaAlarm.setIsVisited("1");//1需要查看
            }
            waterwayJudgment(areaAlarm, areaControlInfo);
            areaAlarmDao.save(areaAlarm);
        }
        // 因为InitData.ShipAreaAlarmMap中，这个map就加载一次，所以新产生的AreaAlarm应该放入到这个ShipAreaAlarmMap中。
        // 也就是说，这里只会产生一个ship对应一个区域id报警。
        Map<Integer, AreaAlarm> map = InitData.ShipAreaAlarmMap.get(areaAlarm.getShipId());
        if (map == null || map.size() == 0) {
            map = new ConcurrentHashMap<Integer, AreaAlarm>();
        }
        map.put(areaAlarm.getAreaId(), areaAlarm);
        InitData.ShipAreaAlarmMap.put(areaAlarm.getShipId(), map);
    }

    /**
     * @return true 表示还在船舶报位还在某个区域内； false 表示船舶报位已经离开原报警区域
     */
    private boolean processAreaAlarmExisted(Map<Integer, AreaAlarm> areaAlarmMap, Ships ship, double[] d1, double[] d2) {
        Date now = new Date();
        Iterator<Integer> it = areaAlarmMap.keySet().iterator();
        try {

            while (it.hasNext()) {
                Integer areaId = it.next();
                AreaControlInfo areaControlInfo = InitData.AreaControlMap.get(areaId);
                if (areaControlInfo == null) {
                    logger.warn("InitData.AreaControlMap中的areaId" + areaId + "，在此刻的定时刷新为null");
                    continue;
                }
                GeneralPath gp = areaControlInfo.getGp();
                if (!"1".equals(areaControlInfo.getAreaType()) || gp == null) {
                    continue;
                }

                //areaType	1警戒区	2警戒线
                if (gp.contains(d2[1], d2[0])) {
                    //还在警戒区内,不作任何处理，退出方法
                    return true;
                }

                // 如果：不在警戒区内，结束警戒报警
                //AreaAlarmType	1出入报警	2进入报警	3出去报警	4出入记录
                long startTime = System.currentTimeMillis();
                List<AreaAlarmRecord> recordList = areaAlarmRecordDao.findByShipIdAndAreaIdAndLeavetimeIsNull(ship.getId(), areaId);
                // 这一条查询也没有相当频繁
                logger.info(recordList.size() + "个record，其queryNotLeavedRecordListByShipAndArea 耗时" + (System.currentTimeMillis() - startTime));
                if (recordList != null && recordList.size() > 0) {
                    for (AreaAlarmRecord record : recordList) {
                        record.setLeavetime(now);
                        areaAlarmRecordDao.save(record);
                    }
                }

                AreaAlarm areaAlarm = areaAlarmMap.get(areaId);
                areaAlarm.setLeaveTime(now);
                //1区域内0区域外
                areaAlarm.setLocStatus("0");
                //AreaAlarmType	1出入报警	2进入报警	3出去报警	4出入记录
                if ("1".equals(areaControlInfo.getAreaAlarmType())
                        || "3".equals(areaControlInfo.getAreaAlarmType())) {
                    //1需要查看0无需查看
                    areaAlarm.setIsVisited("1");
                } else {
                    areaAlarm.setIsVisited("0");
                }
                waterwayJudgment(areaAlarm, areaControlInfo);

                areaAlarmDao.save(areaAlarm);
                // 已经离开区域
                it.remove();
            }

        } catch (Exception e) {
            //3-7 10:23 打印一下异常看看
            e.printStackTrace();
            logger.error("------shipName:" + ship.getName() + ",areaAlarmMap.size:" + areaAlarmMap.size() + "," + e.getMessage(), e);
        }
        // 已经离开了这个区域
        return false;
    }


    /**
     * 一个关于南北航道上下警戒区域的特殊判定，1533、1534是该区域id：这个警戒区对应userId是pthyj 554
     * 只在23-5点钟以外的时间段不需要报警。
     * 只对pthyj下的船舶报警
     * 只对在区域内的报警船舶显示颜色
     *
     * @param areaAlarm
     * @param areaControlInfo
     */
    private void waterwayJudgment(AreaAlarm areaAlarm, AreaControlInfo areaControlInfo) {
        boolean isHangdao = areaControlInfo.getAreaId().equals(1533) | areaControlInfo.getAreaId().equals(1534);
        if (!isHangdao) {
            // 如果不是航道区域则直接返回不处理
            return;
        }

        // 554 pthyj 这个是普陀海洋渔业局的账号
        int pthyjUserId = 554;
        LocalTime nowTime = LocalTime.now();
        boolean dayLight = nowTime.getHour() > 4 & nowTime.getHour() < 23;
        boolean myShip = isMyShip(areaAlarm.getShipId(), pthyjUserId);
        if (!myShip || dayLight) {
            // 如果是白天或者不是该用户的船舶都不报警
            areaAlarm.setIsVisited("0");//1需要查看0无需查看
        }
    }

    private boolean isMyShip(int shipId, int userId) {
        List<Integer> shipIdList = InitData.shipsToUserMap.get(userId);
        if (shipIdList == null) {
            return false;
        }
        return shipIdList.contains(shipId);
    }

}
