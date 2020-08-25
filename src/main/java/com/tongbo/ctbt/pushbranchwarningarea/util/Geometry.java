package com.tongbo.ctbt.pushbranchwarningarea.util;

import com.google.gson.JsonObject;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author lenovo
 */
@Data
public class Geometry {
    Integer id;
    JsonObject spatialReference;
    List<List<List<BigDecimal>>> rings;
    List<List<List<BigDecimal>>> paths;
}
