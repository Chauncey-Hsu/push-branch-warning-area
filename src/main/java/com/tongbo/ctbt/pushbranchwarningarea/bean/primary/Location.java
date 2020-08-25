package com.tongbo.ctbt.pushbranchwarningarea.bean.primary;

import java.util.Date;

import lombok.Data;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 杞ㄨ抗琛�
 * 
 * @author RD
 *
 */
@Data
public class Location implements Comparable<Location>{

	@JsonIgnore
    private boolean isUpdate=false;

	@JsonIgnore
	private String id;
	
	@Transient
	private int oid;

	private double longitude;

	private double latitude;

	private double direction;
	
	private double temperature;
	
	private double batteryState;
	
	private String batteryWarn;

	private Date positionDate;

    @JsonIgnore
	private String deviceId;
    @JsonIgnore
    private String deviceType;

    private String locationType;
    private Double speed;

    @JsonIgnore
    private Integer inHarbour;
    @JsonIgnore
	private String harbourName;
    @JsonIgnore
	private Integer harbourId;
    @JsonIgnore
	private Date enterHarbourTime;
    @JsonIgnore
	private Date leaveHarbourTime;

	private String state;
    @JsonIgnore
    private Double bowDirection;
	@JsonIgnore
	private Ships ship;
	
	private String mmsi;
	private String equipmentid;
		
	@JsonIgnore
	private Loc loc;

	@Override
	public int compareTo(Location arg0) {
		return this.getPositionDate().compareTo(arg0.getPositionDate());
	}

	public Location() {
		super();
		this.longitude = 0;
		this.latitude = 0;
		this.direction = 0.0;
		this.bowDirection = 0.0;
		this.temperature = 0.0;
		this.batteryState = 0.0;
		this.batteryWarn = "";
		this.positionDate = null;
		this.locationType = "";
		this.state = "开启";
		this.speed = 0.0;
	}

	public Location(String id) {
		super();
		this.id = id;
	}

	public Location(int id) {
		super();
		this.oid=id;
	}

	public Location(double longitude, double latitude) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
	}
	
}

