package com.tongbo.ctbt.pushbranchwarningarea.dao.secondary;

import com.tongbo.ctbt.pushbranchwarningarea.bean.secondary.AreaAlarmRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lenovo
 */
@Repository
public interface AreaAlarmRecordDao extends JpaRepository<AreaAlarmRecord, Long> {
    List<AreaAlarmRecord> findByShipIdAndAreaIdAndLeavetimeIsNull(int shipid, Integer areaId);
}
