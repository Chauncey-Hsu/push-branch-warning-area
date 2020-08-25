package com.tongbo.ctbt.pushbranchwarningarea.dao.primary;

import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.Ships;
import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.ShipsToUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShipsToUsersDao extends JpaRepository<ShipsToUsers,Integer> {
    List<ShipsToUsers> findByUserId(Integer userId);
}
