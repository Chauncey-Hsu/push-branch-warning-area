package com.tongbo.ctbt.pushbranchwarningarea.bean.primary;

import lombok.CustomLog;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "SHIPSTOUSERS_TABLE")
public class ShipsToUsers {
    @Id
    private Integer id;

//    @JoinColumn(name = "ID",referencedColumnName = "SHIPID")
//    @ManyToOne
//    private Ships ships;

    @Column(name = "SHIPID")
    private Integer shipId;

    @Column(name = "USERID")
    private Integer userId;

    //最新修改设备号的时间
    private Date updateequipmentiddate;
}
