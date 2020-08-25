package com.tongbo.ctbt.pushbranchwarningarea.util;

import java.awt.geom.Point2D;
import java.math.BigDecimal;

public class GeometryUtils {

//	/**
//	 * 墨卡托坐标 转换成 地理坐标（网上公认的办法）
//	 * @param a
//	 * @param b
//	 * @return
//	 */
//	public static Point2D.Double locatechange(BigDecimal a, BigDecimal b){
//		double x = a.doubleValue() / 20037508.34 * 180;
//        double y = b.doubleValue() / 20037508.34 * 180;
//        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
//        //System.out.println("经度：" + x + " 纬度：" + y);
//        return new Point2D.Double(x, y);
//	}
	
	// 测试纠偏后
	public static void main(String[] args) {
//		Point2D.Double lonlat = locatechange(new BigDecimal("13230637.378595"),new BigDecimal("2664822.4732951"));
//		System.out.println(lonlat);
//		System.out.println("实际经纬度：" + "118.84844444,23.273694444");
//		System.out.println("----------------");
//		Point2D.Double Mercator = lonLatToMercator(lonlat.getX(),lonlat.getY());
//		System.out.println(Mercator);
//		System.out.println("实际经纬度：" + "13230637.378595,2664822.4732951");

		System.out.println(String.format("%s,%s", (117D+(22D/60D)+(34.0D/3600)),23+(03D/60D)+(32.0D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(22D/60D)+(18.5D/3600)),23+(03D/60D)+(18.8D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(22D/60D)+(01.9D/3600)),23+(03D/60D)+(03.4D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(21D/60D)+(45.1D/3600)),23+(02D/60D)+(47.7D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(21D/60D)+(28.5D/3600)),23+(02D/60D)+(31.4D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(21D/60D)+(11.9D/3600)),23+(02D/60D)+(14.7D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(20D/60D)+(55.2D/3600)),23+(01D/60D)+(57.9D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(20D/60D)+(39.1D/3600)),23+(01D/60D)+(39.8D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(20D/60D)+(22.0D/3600)),23+(01D/60D)+(23.5D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(20D/60D)+(03.9D/3600)),23+(01D/60D)+(06.9D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(19D/60D)+(46.1D/3600)),23+(00D/60D)+(49.4D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(19D/60D)+(27.4D/3600)),23+(00D/60D)+(32.3D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(19D/60D)+(07.2D/3600)),23+(00D/60D)+(12.6D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(18D/60D)+(50.3D/3600)),22+(59D/60D)+(57.1D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(18D/60D)+(31.9D/3600)),22+(59D/60D)+(40.1D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(18D/60D)+(13.1D/3600)),22+(59D/60D)+(24.9D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(17D/60D)+(54.6D/3600)),22+(59D/60D)+(09.9D/3600D)));
		System.out.println(String.format("%s,%s", (117D+(17D/60D)+(36.6D/3600)),22+(58D/60D)+(56.7D/3600D)));



		
	}
	
	/**
	 * @功能描述: 墨卡托坐标 转换成 地理坐标
	 * 针对具体问题，徐传奇纠偏后的
	 * @作者 xuchuanqi
	 * @日期 2019年11月1日
	 */
	public static Point2D.Double locatechange(BigDecimal a, BigDecimal b){
		double x = a.doubleValue() / 20037508.34 * 180;
        double y = b.doubleValue() / 20037508.34 * 180;
        y = 180 / Math.PI * (2 * Math.atan(Math.exp(y * Math.PI / 180)) - Math.PI / 2);
        //System.out.println("经度：" + x + " 纬度：" + y);
        x -= 0.0044D;
        y += 0.0028D;
        return new Point2D.Double(x, y);
	}

	/**
	 * 经纬度转墨卡托 [@param](https://my.oschina.net/u/2303379) LonLat 经纬度坐标
	 * 针对具体问题，徐传奇纠偏后的
	 * @作者 xuchuanqi
	 * @日期 2019年11月1日
	 */
	public static Point2D.Double lonLatToMercator(double lon,double lat) {
		lon += 0.0044D;
        lat -= 0.0028D;
		double x = (lon * 20037508.342789 / 180);
		double y = (Math.log(Math.tan((90 + lat) * Math.PI / 360)) / (Math.PI / 180));
		y = (double) (y * 20037508.342789 / 180);
		return new Point2D.Double(x, y);
	}
}
