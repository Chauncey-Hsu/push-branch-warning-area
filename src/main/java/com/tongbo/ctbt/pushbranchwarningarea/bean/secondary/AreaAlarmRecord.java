package com.tongbo.ctbt.pushbranchwarningarea.bean.secondary;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author lenovo
 */
@Data
@Table(name = "AREAALARMRECORD_TABLE")
@Entity
public class AreaAlarmRecord {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AREAALARMRECORDSEQ")
    @SequenceGenerator(name="AREAALARMRECORDSEQ",sequenceName="AREAALARMRECORDSEQ",allocationSize=1)
    @Column(name = "ID", unique = true, nullable = false)
    public Long id;

    /**
     由于ships 与 areaAlarmRecord不再同一个数据中，所以不能使用多对一的多表联查。
     */
    @Column(name = "SHIPID")
    public Integer shipId;

    @Column(name = "AREAID")
    public Integer areaId;
    @Column(name = "EQUIPMENTID")
    public String equipmentId;
    @Column(name = "ENTERTIME")
    public Date entertime;
    @Column(name = "LEAVETIME")
    public Date leavetime;


}
