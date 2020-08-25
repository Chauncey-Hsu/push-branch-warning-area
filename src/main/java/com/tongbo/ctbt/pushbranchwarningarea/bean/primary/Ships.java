package com.tongbo.ctbt.pushbranchwarningarea.bean.primary;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 船舶表
 *
 * @author RD
 */
@Data
@Table(name = "SHIPS_TABLE")
@Entity
public class Ships {

    public Ships() {
        super();
        this.number = "";
        this.name = "";
        this.longitude = 0.0;
        this.latitude = 0.0;
        this.speed = 0.0;
        this.callSign = "";
        this.mmsi = "";
        this.shipLength = 0.0;
        this.shipWidth = 0.0;
        this.draught = 0.0;
        this.shipType = "";
        this.state = "";
        this.direction = 0.0;
        this.destination = "";
        this.arriveDate = null;
        this.positionDate = null;
        this.city = null;
        this.nation = "";
        this.province = "";
        this.county = "";
        this.imo = "";
        this.aisType = "";
        this.bowDirection = 0.0;
        this.receiveDate = null;
        this.equipmentid = "";
        this.byName = "";
        this.productId = "";
        this.isUnpowerView = "";
    }

    public Ships(int id, String name, String mmsi, Date positionDate) {
        super();
        this.id = id;
        this.name = name;
        this.mmsi = mmsi;
        this.positionDate = positionDate;
    }

    public Ships(int id, double longitude, double latitude, Date positionDate, double direction) {
        super();
        this.id = id;
        this.longitude = longitude;
        this.latitude = latitude;
        this.positionDate = positionDate;
        this.direction = direction;
    }

    public Ships(int id, String mmsi, double longitude, double latitude, Date positionDate,
                 double direction, String state, double speed, String name, String productId, String isUnpower,
                 double unpowerLatitude, double unpowerLongitude, double radius) {
        super();
        this.id = id;
        this.mmsi = mmsi;
        this.longitude = longitude;
        this.latitude = latitude;
        this.positionDate = positionDate;
        this.direction = direction;
        this.state = state;
        this.speed = speed;
        this.name = name;
        this.productId = productId;
        this.isUnpower = isUnpower;
        this.unpowerLatitude = unpowerLatitude;
        this.unpowerLongitude = unpowerLongitude;
        this.radius = radius;
    }

    public Ships(int id, String mmsi, double longitude, double latitude, Date positionDate,
                 double direction, String state, double speed, String name, String productId, String isUnpower,
                 double unpowerLatitude, double unpowerLongitude, double radius, String owner, String phoneNumber, double tons) {
        super();
        this.id = id;
        this.mmsi = mmsi;
        this.longitude = longitude;
        this.latitude = latitude;
        this.positionDate = positionDate;
        this.direction = direction;
        this.state = state;
        this.speed = speed;
        this.name = name;
        this.productId = productId;
        this.isUnpower = isUnpower;
        this.unpowerLatitude = unpowerLatitude;
        this.unpowerLongitude = unpowerLongitude;
        this.radius = radius;
        this.owner = owner;
        this.phoneNumber = phoneNumber;
        this.tons = tons;
    }

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Integer id;



    private String nation;

    private String province;

    private String city;

    private String county;

    private String name;

    private String byName;

    private String number;

    private String callSign;

    private String mmsi;

    private String imo;

    private double longitude;

    private double latitude;

    private double speed;

    private double shipLength;

    private double shipWidth;

    private double draught;

    private String shipType;

    private String state;

    private double direction;

    private double bowDirection;

    private String destination;

    private Date arriveDate;

    private Date positionDate;

    private String aisType;

    private Date receiveDate;

    private String positionType;

    private String equipmentid;

    @Transient
    public List<Location> location;

    private String productId;//产品Id

    private double temperature;

    private double batteryState;

    private String batteryWarn;

    @Transient
    @JsonIgnore
    public List<Double> voyageList;

    private String isUnpower;//是否无动力船舶

    private double unpowerLongitude;//无动力船舶经度

    private double unpowerLatitude;//无动力船舶纬度

    private double radius; //无动力船舶报警半径

    private String isUnpowerView;//无动力船舶是否启用

    private String isNeedAlarm;//30分钟未上报位置是否baojing

    private String owner;     //船主 联系人
    private String phoneNumber;  //手机号
    private double tons;        //吨位

    private Integer inHarbour;

    private String harbourName;
    private Integer harbourId;

    private Date enterHarbourTime;

    private Date leaveHarbourTime;

    private String locationDeviceId;
    private String locationDeviceType;

    private Date lastUpdateTime;//最后更新时间
    private String contactTwo;
}
