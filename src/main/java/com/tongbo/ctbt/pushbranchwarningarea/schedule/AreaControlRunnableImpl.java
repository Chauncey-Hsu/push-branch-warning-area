package com.tongbo.ctbt.pushbranchwarningarea.schedule;

import com.google.gson.Gson;
import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaControlInfo;
import com.tongbo.ctbt.pushbranchwarningarea.dic.InitData;
import com.tongbo.ctbt.pushbranchwarningarea.service.AreaControlInfoService;
import com.tongbo.ctbt.pushbranchwarningarea.util.Geometry;
import com.tongbo.ctbt.pushbranchwarningarea.util.GeometryUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

/**
 * 定时从数据库加载 警戒信息、港口信息等，用于船舶校验
 *
 * @author Administrator
 */
@Component
public class AreaControlRunnableImpl implements Runnable {
    private Logger logger = LoggerFactory.getLogger(AreaControlRunnableImpl.class);
    private AreaControlInfoService areaControlInfoService;

    @Autowired
    public AreaControlRunnableImpl(AreaControlInfoService areaControlInfoService) {
        this.areaControlInfoService = areaControlInfoService;
    }

    @Override
    public void run() {
        logger.info("定时加载警戒区域信息");
        loadAreaControl();

//		logger.info("定时从数据库加载 港口信息等，用于船舶校验");
//		loadLeafHarbourList();
//		loadAlarmShipErrorList();
    }

    /**
     * 加载警戒信息到InitData.AreaControlMap
     */
    public void loadAreaControl() {
        List<AreaControlInfo> areaControlInfoList = null;
        try {
            InitData.AreaControlMap.clear();// 清空，重新加载
            areaControlInfoList = areaControlInfoService.getAll();
            Gson gson = new Gson();
            for (AreaControlInfo areaControlInfo : areaControlInfoList) {
                Geometry geometry = (Geometry) gson.fromJson(areaControlInfo.getGeometry(), Geometry.class);
                areaControlInfo.setGeometryObj(geometry);
                if ("1".equals(areaControlInfo.getAreaType()) && geometry.getRings() != null
                        && geometry.getRings().size() > 0) {
                    // 警戒区
                    GeneralPath gp = new GeneralPath();
                    for (int i = 0; i < geometry.getRings().get(0).size(); i++) {
                        Point2D.Double pp = GeometryUtils.locatechange(geometry.getRings().get(0).get(i).get(0),
                                geometry.getRings().get(0).get(i).get(1));
                        // System.out.print(pp.getX()+","+pp.getY()+"||");
                        if (i == 0) {
                            gp.moveTo(pp.x, pp.y);
                        } else {
                            gp.lineTo(pp.x, pp.y);
                        }
                    }
                    gp.closePath();

                    areaControlInfo.setGp(gp);
                } else if ("2".equals(areaControlInfo.getAreaType()) && geometry.getPaths() != null
                        && geometry.getPaths().size() > 0) {
                    // 警戒线

                }

                InitData.AreaControlMap.put(areaControlInfo.getAreaId(), areaControlInfo);
            }
            System.out.println();
            logger.info("定时加载警戒区域信息【结果】：InitData.AreaControlMap:" + InitData.AreaControlMap.size());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            e.printStackTrace();
        }

    }
}
