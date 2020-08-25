package com.tongbo.ctbt.pushbranchwarningarea.bean.primary;

import java.awt.geom.GeneralPath;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tongbo.ctbt.pushbranchwarningarea.util.Geometry;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CONTROLAREA_TABLE")
public class AreaControlInfo {

	@Id
	@GeneratedValue()
	@Column(name = "AREA_ID")
	private Integer areaId;

	@Column(name = "AREANAME")
	private String areaName;

	@Column(name = "USERID")
	private Integer userId;

	@Column(name = "AREAFUNCTION")
	private String areaFunction;


	@Column(name = "GEOMETRY")
	private String geometry;

	@JsonIgnore
	@Transient
	private GeneralPath gp;

	@JsonIgnore
	@Transient
	private Geometry geometryObj;

	@Column(name = "AREATIME")
	private Date areaTime;

	@Column(name = "PEOPLENAME")
	private String peopleName;

	@Column(name = "AREA_TYPE")
	private String areaType;

	@Column(name = "AREA_FUNC")
	private String areaFunc;

	@Column(name = "AREA_ALARM_TYPE")
	private String areaAlarmType;

	private String validity;
}
