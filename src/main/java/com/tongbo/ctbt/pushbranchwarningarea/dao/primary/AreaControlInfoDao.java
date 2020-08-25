package com.tongbo.ctbt.pushbranchwarningarea.dao.primary;

import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.AreaControlInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaControlInfoDao extends JpaRepository<AreaControlInfo, Integer> {
}
