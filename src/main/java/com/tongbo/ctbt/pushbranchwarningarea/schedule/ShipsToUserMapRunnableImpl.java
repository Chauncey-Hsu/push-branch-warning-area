package com.tongbo.ctbt.pushbranchwarningarea.schedule;

import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.Ships;
import com.tongbo.ctbt.pushbranchwarningarea.dic.InitData;
import com.tongbo.ctbt.pushbranchwarningarea.service.ShipsToUsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author lenovo
 */
@Component
public class ShipsToUserMapRunnableImpl implements Runnable {
    private Logger logger = LoggerFactory.getLogger(ShipsToUserMapRunnableImpl.class);

    private ShipsToUsersService shipsToUsersService;

    @Autowired
    public ShipsToUserMapRunnableImpl(ShipsToUsersService shipsToUsersService) {
        this.shipsToUsersService = shipsToUsersService;
    }

    @Override
    public void run() {
        logger.info("定时加载船舶与用户关系表");
        loadShipsToUserPthyj();

    }

    private void loadShipsToUserPthyj() {
        try {
            InitData.shipsToUserMap.clear();
            // 554 pthyj 这个是普陀海洋渔业局的账号
            int pthyjUserId = 554;
            searchAndPutMap(pthyjUserId);
            logger.info("定时加载船舶与用户关系表:【pthyj普陀海洋渔业局的账号下所有船舶】"+ InitData.shipsToUserMap.size());

        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private void searchAndPutMap(int userId) {
        List<Integer> shipIdList = shipsToUsersService.getShipsListByUserId(userId);
        if (shipIdList == null || shipIdList.size() == 0) {
            return;
        }
        InitData.shipsToUserMap.put(userId, shipIdList);
    }
}
