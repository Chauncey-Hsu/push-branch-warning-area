package com.tongbo.ctbt.pushbranchwarningarea.bean.primary;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "AREAALARM_TABLE")
@Entity
public class AreaAlarm {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="AREAALARMSEQ")
    @SequenceGenerator(name="AREAALARMSEQ",sequenceName="AREAALARMSEQ",allocationSize=1)
    @Column(name = "ID", unique = true, nullable = false)
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
