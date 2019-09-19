package com.qding.bigdata.ds.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DbUtil {

	public static void main(String[] args) throws Exception {
		testConnections();
//		Class.forName("org.postgresql.Driver");
//		Connection connection = null;
//		connection = DriverManager.getConnection("jdbc:postgresql://10.37.5.115:5432/dw?currentSchema=ds", "gpadmin",
//				"gpadmin");
//		PreparedStatement stmt = connection
//				.prepareStatement("insert into ds_tag(id,tag,name,datatype,sortno,pid,level)values(?,?,?,?,?,?,?)");
//		PreparedStatement stmtQuery = connection.prepareStatement("select * from ds_tag where name like '%[2016-12%'");
//		// m1(stmt);
//		m3(stmt, stmtQuery);
//		stmt.close();
//		connection.close();
	}
	private static void testConnections() throws Exception{
		
		for (int i = 0; i<200; i++) {
			
	
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:postgresql://10.37.5.115:5432/dw?currentSchema=ds", "gpadmin",
				"gpadmin");
		Statement stat = connection.createStatement();
 stat.executeQuery("  select * from pg_stat_activity; ");
 System.out.println("i:"+i);
		}
		
		Thread.sleep(1000000);
	}
	private static void m3(PreparedStatement stmt, PreparedStatement stmtQuery) throws SQLException {
		ResultSet executeQuery = stmtQuery.executeQuery();
		Map<String, String> tagMap = new HashMap<String, String>();
		while (executeQuery.next()) {
			if (executeQuery.getString("tag").contains("2016-12")) {
				String tag = executeQuery.getString("tag");
				String name = executeQuery.getString("name");
				int datatype = executeQuery.getInt("datatype");
				int sortno = executeQuery.getInt("sortno");
				String pid = executeQuery.getString("pid");
				int level = executeQuery.getInt("level");

				String[] years = { "2017" };
				for (String year : years) {
					for (int month = 1; month <= 4; month++) {
						stmt.setString(1, UUIDUtil.createId());
						String monthStr = year + "-" + (month > 9 ? (month + "") : ("0" + month));
						stmt.setString(2, tag.replaceAll("2016-12", monthStr));
						stmt.setString(3, name.replaceAll("2016-12", monthStr));
						stmt.setInt(4, datatype);

						stmt.setInt(5, --sortno);
						stmt.setString(6, pid);
						stmt.setInt(7, level);
						stmt.execute();
					}
				}
			}
		}
	}

	private static void m33(PreparedStatement stmt, PreparedStatement stmtQuery) throws SQLException {
		ResultSet executeQuery = stmtQuery.executeQuery();
		Map<String, String> tagMap = new HashMap<String, String>();
		while (executeQuery.next()) {
			tagMap.put(executeQuery.getString("tag"), executeQuery.getString("id"));
		}
		System.out.println(tagMap);
		// String dianwei =
		// "buy_days_all,总购买天数$buy_orders_total,累计订单数$buy_money_total,累计购买金额$buy_orders_all,总订单数$buy_money_all,总购买金额$buy_sku_per_order,平均每单商品数";
		// String dianwei =
		// "month_service_order,当月服务业态下单数$month_service_amount,当月服务业态下单金额$month_service_pay,当月服务业态GMV定单数$month_service_price,当月服务业态GMV金额";
		// String
		// dianwei="month_ng_order,当月商城下单数$month_ng_amount,当月商城下单金额$month_ng_pay,当月商城GMV单数$month_ng_price,当月商城GMV金额";
		// String
		// dianwei="month_ly_order,当月旅游下单数$month_ly_amount,当月旅游下单金额$month_ly_pay,当月旅游GMV单数$month_ly_price,当月旅游GMV金额";
		// String
		// dianwei="month_zxs_order,当月周鲜生下单数$month_zxs_amount,当月周鲜生下单金额$month_zxs_pay,当月周鲜生GMV单数$month_zxs_price,当月周鲜生GMV金额";
		// String
		// dianwei="month_rxc_order,当月日鲜车下单数$month_rxc_amount,当月日鲜车下单金额$month_rxc_pay,当月日鲜车GMV单数$month_rxc_price,当月日鲜车GMV金额";
		String dianwei = "month_fxpd_order,当月发现频道下单数$month_fxpd_amount,当月发现频道下单金额$month_fxpd_pay,当月发现频道GMV单数$month_fxpd_price,当月发现频道GMV金额";

		for (String dw : dianwei.split("\\$")) {
			int sortno = 100;
			System.out.println(dw);
			String[] years = { "2015", "2016" };
			for (String year : years) {
				for (int month = 1; month <= 12; month++) {
					// dcb02c75682a48639b6b2f2cb62fc127 active_days_all_2017-01
					// 总活跃天数【2017-01】 1 1 c50e9d78e2f344be9f8883eb2e226f30 4
					stmt.setString(1, UUIDUtil.createId());
					String monthStr = year + "-" + (month > 9 ? (month + "") : ("0" + month));
					stmt.setString(1, UUIDUtil.createId());
					stmt.setString(2, dw.split(",")[0] + "_" + monthStr);
					stmt.setString(3, dw.split(",")[1] + "[" + monthStr + "]");
					stmt.setInt(4, 1);

					stmt.setInt(5, sortno--);
					stmt.setString(6, tagMap.get(dw.split(",")[0]));
					stmt.setInt(7, 4);
					stmt.execute();
				}
			}
		}
	}

	private static void m2(PreparedStatement stmt) throws SQLException {

		String dianwei = "active_times_mall_enter,商城入口$active_times_tongxing,通行$active_times_gonggao,公告$active_times_wuye,物业$active_times_baoguo,包裹$active_times_mall,商城$active_times_orders,订单$active_times_baojie,保洁$active_times_baoshi,报事$active_times_xiyi,洗衣$active_times_lvyou,旅游$active_times_qita,其他$active_times_shouye,首页$active_times_guanjia,管家$active_times_linju,邻聚$active_times_wode,我的";
		int sortno = 100;
		for (String dw : dianwei.split("\\$")) {
			System.out.println(dw);
			stmt.setString(1, UUIDUtil.createId());
			stmt.setString(2, dw.split(",")[0]);
			stmt.setString(3, "[" + dw.split(",")[1] + "]点位活跃次数");
			stmt.setInt(4, 0);

			stmt.setInt(5, sortno--);
			stmt.setString(6, "86e9dee1bd3749d0a385b08265f474ab");
			stmt.setInt(7, 3);
			stmt.execute();
		}
	}

	private static void m22(PreparedStatement stmt) throws SQLException {

		String dianwei = "buy_days_all,总购买天数$buy_orders_total,累计订单数$buy_money_total,累计购买金额$buy_orders_all,总订单数$buy_money_all,总购买金额$buy_sku_per_order,平均每单商品数";
		int sortno = 1;
		for (String dw : dianwei.split("\\$")) {
			System.out.println(dw);
			stmt.setString(1, UUIDUtil.createId());
			stmt.setString(2, dw.split(",")[0]);
			stmt.setString(3, dw.split(",")[1]);
			stmt.setInt(4, 0);

			stmt.setInt(5, sortno++);
			stmt.setString(6, "abff4914843147ef9a93fdbc450b5ae6");
			stmt.setInt(7, 3);
			stmt.execute();
		}
	}

	private static void m1(PreparedStatement stmt) throws SQLException {
		String[] years = { "2015", "2016" };
		String tags = "month_service_order,当月服务业态下单数$month_service_amount,当月服务业态下单金额$month_service_pay,当月服务业态GMV定单数$month_service_price,当月服务业态GMV金额";

		for (String tag : tags.split("\\$")) {
			System.out.println(tag);

		}

		for (String tag : tags.split("\\$")) {
			System.out.println(tag);

			int sortno = 100;
			for (String year : years) {
				for (int month = 1; month <= 12; month++) {
					// dcb02c75682a48639b6b2f2cb62fc127 active_days_all_2017-01
					// 总活跃天数【2017-01】 1 1 c50e9d78e2f344be9f8883eb2e226f30 4
					stmt.setString(1, UUIDUtil.createId());
					String monthStr = year + "-" + (month > 9 ? (month + "") : ("0" + month));
					stmt.setString(2, tag.split(",")[0] + "_" + monthStr);
					stmt.setString(3, tag.split(",")[1] + "[" + monthStr + "]");
					stmt.setInt(4, 1);

					stmt.setInt(5, sortno--);
					stmt.setString(6, "c618e08ae654408e9e55ceb4bc309b48");
					stmt.setInt(7, 4);
					stmt.execute();
				}
			}
		}
	}
}
