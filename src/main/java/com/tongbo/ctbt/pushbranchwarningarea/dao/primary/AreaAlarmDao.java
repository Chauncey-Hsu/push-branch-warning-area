package com.tongbo.ctbt.pushbranchwarningarea.dao.primary;

import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaAlarm;
import com.tongbo.ctbt.pushbranchwarningarea.bean.secondary.AreaAlarmRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lenovo
 */
@Repository
public interface AreaAlarmDao extends JpaRepository<AreaAlarm, Integer> {

    AreaAlarm findByShipIdAndAreaId(int shipId, Integer areaId);

    List<AreaAlarm> findByLeaveTimeIsNull();
}
