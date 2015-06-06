package com.couponsystem.dailytask;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;

import com.couponsystem.beans.CouponType;
import com.couponsystem.connection.ConnectionPool;

public class DeleteExpiredCouponsThread implements Runnable {

	private ConnectionPool connectionPool = ConnectionPool.getInstance();

	@Override
	public void run() {

		Connection connection = null;

		Date today = new Date();
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(today);

		try {
			connection = connectionPool.getConnection();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		String selectSQLQuery = "SELECT ID, END_DATE FROM COUPON";

		try {

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(selectSQLQuery);

			while (resultSet.next()) {

				long couponId = resultSet.getLong("ID");
				Date couponDate = new Date(resultSet.getDate("END_DATE")
						.getTime());

				Calendar couponCal = Calendar.getInstance();
				couponCal.setTime(couponDate);

				if (couponCal.before(todayCal)) {

					// Delete Customer_Coupon join
					PreparedStatement preparedStatement = null;
					String deleteSQLQuery = "DELETE FROM customer_coupon WHERE COUPON_ID = ?";

					preparedStatement = connection
							.prepareStatement(deleteSQLQuery);
					preparedStatement.setLong(1, couponId);
					preparedStatement.executeUpdate();

					System.out.println("Record: " + couponId
							+ " is deleted from customer_coupon table!");

					// Delete Company_Coupon join
					PreparedStatement preparedStatement2 = null;
					String deleteSQLQuery2 = "DELETE FROM company_coupon WHERE COUPON_ID = ?";

					preparedStatement2 = connection
							.prepareStatement(deleteSQLQuery2);
					preparedStatement2.setLong(1, couponId);
					preparedStatement2.executeUpdate();

					System.out.println("Record: " + couponId
							+ " is deleted from company_coupon table!");

					// Delete Coupon
					PreparedStatement preparedStatement3 = null;
					String deleteSQLQuery3 = "DELETE FROM COUPON WHERE ID = ?";

					preparedStatement3 = connection
							.prepareStatement(deleteSQLQuery3);
					preparedStatement3.setLong(1, couponId);
					preparedStatement3.executeUpdate();

					System.out.println("Record: " + couponId
							+ " is deleted from COUPON table!");

				}

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (connection != null) {
				connectionPool.releaseConnection(connection);
			}
		}

	}

}
