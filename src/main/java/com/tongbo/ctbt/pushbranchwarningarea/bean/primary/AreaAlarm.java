package com.tongbo.ctbt.pushbranchwarningarea.bean.primary;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "AREAALARM_TABLE")
@Entity
public class AreaAlarm {
    @Id
    @GeneratedValue
    private Integer id;

//    @JoinColumn(name = "ID", referencedColumnName = "SHIPID")
//    @ManyToOne
//    private Ships ships;

    @Column(name = "SHIPID")
    private Integer shipId;
    @Column(name = "EQUIPMENTID")
    private String equipmentId;
    @Column(name = "AREAID")
    private Integer areaId;
    @Column(name = "LOCSTATUS")
    private String locStatus;
    @Column(name = "ISVISITED")
    private String isVisited;
    @Column(name = "ENTERTIME")
    private Date enterTime;
    @Column(name = "LEAVETIME")
    private Date leaveTime;
}
