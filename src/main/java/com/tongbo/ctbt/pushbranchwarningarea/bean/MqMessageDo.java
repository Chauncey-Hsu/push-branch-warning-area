package com.tongbo.ctbt.pushbranchwarningarea.bean;


import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.Location;
import com.tongbo.ctbt.pushbranchwarningarea.bean.primary.Ships;
import lombok.Data;

/**
 * @author lenovo
 */
@Data
public class MqMessageDo {
    Ships ships;
    Location location;

    public MqMessageDo() {
        super();
    }

    public MqMessageDo(Ships ships, Location location) {
        super();
        this.ships = ships;
        this.location = location;
    }

}
