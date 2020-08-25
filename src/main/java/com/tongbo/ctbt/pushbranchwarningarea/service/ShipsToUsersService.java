package com.tongbo.ctbt.pushbranchwarningarea.service;

import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.ShipsToUsers;
import com.tongbo.ctbt.pushbranchwarningarea.dao.primary.ShipsToUsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShipsToUsersService {
    @Autowired
    ShipsToUsersDao shipsToUsersDao;

    public List<Integer> getShipsListByUserId(int userId) {
        List<Integer> list = new ArrayList<>();
        List<ShipsToUsers> shipsToUsersList = shipsToUsersDao.findByUserId(userId);
        shipsToUsersList.forEach(v -> {
            list.add(v.getShipId());
        });
        return list;
    }
}
