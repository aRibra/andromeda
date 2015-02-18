import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import com.couponsystem.ConnectionThread;
import com.couponsystem.CouponSystem;
import com.couponsystem.RemoverThread;
import com.couponsystem.beans.*;
import com.couponsystem.connection.ConnectionPool;
import com.couponsystem.connection.DBConnection;
import com.couponsystem.dao.*;
import com.couponsystem.facades.*;

public class Test {

	public static void main(String[] args) {

		// *** CONNECTIONPOOL TEST ***//

		ConnectionPool connectionPool = ConnectionPool.getInstance();

		// RemoverThread remover = new RemoverThread(connectionPool);

		// remover.start();

		List<ConnectionThread> list = new ArrayList<>();

		// for (int i = 0; i < 15; i++) {
		// list.add(new ConnectionThread(connectionPool, i));
		// list.get(i).start();
		// }

		System.out.println("Wenak 7abeebe wenak.");

		// for (int i = 0; i < list.size(); i++) {
		// list.get(i).stopRunning();
		// }
		// *** END CONNECTIONPOOL TEST ***//

		// AdminDBDAO adminDbDao = new AdminDBDAO();
		// System.out.println(adminDbDao.login("ibrahim", "root"));

		// *** CLIENTFACADE TEST ***//

		// TODO: DONE BUT - some arrangements needed
		// will return CouponSystemClientFacade object then an if statement or a
		// switch should be run to determine the type of the object, here its
		// hard coded to AdminFacade which should be corrected later on.
		// After we have determined what type of object the
		// CouponSystemClientFacade is (admin, company or customer), we then use
		// the related login method for that type of user

		CouponSystem couponSystem = CouponSystem.getInstance();

		CouponSystemClientFacade client = couponSystem.login("ibrahim", "root",
				ClientType.ADMIN);

		// just for testing purposes
		AdminFacade adminClient = null;

		if (client instanceof AdminFacade) {
			adminClient = (AdminFacade) client;
		} else if (client instanceof CompanyFacade) {
			CompanyFacade companyClient = (CompanyFacade) client;
		} else if (client instanceof CustomerFacade) {
			CustomerFacade custometFacade = (CustomerFacade) client;
		}

		System.out.println(adminClient.getCustomer(3));

		System.out
				.println("*******admin-client-retrieved-a-customer**********");

		// *** END CLIENTFACADE LOGIN TEST ***//

		/*
		 * 
		 * 
		 */

		// *** CONNECTION TEST ***//

		// Connection connection = new DBConnection().getDBConnection();
		// System.out.println("Connected");
		// CouponType t = CouponType.HEALTH;
		// System.out.println(t.name());

		// *** END CONNECTION TEST ***//

		/*
		 * 
		 * 
		 */

		// *** CompanyDBDAO TEST ***//

		CompanyDBDAO cdb = new CompanyDBDAO();
		CouponDBDAO coupondb = new CouponDBDAO();

		// Company comp = new Company("Twix", "twix", "twix@snickers.bounty");
		//
		// cdb.createCompany(comp);
		// System.out.println(cdb.login("Twix", "twix"));
		//
		// comp.setCompanyName("TWIXs");
		// comp.setPassword("4567");
		//
		// comp.setId(2);

		Company comp = cdb.getCompany(2);

		System.out.println(coupondb.getCompanyCouponsByType(comp));

		cdb.updateCompany(comp);

		System.out.println("*****************");

		// *** END CompanyDBDAO TEST ***//

		/*
		 * 
		 * 
		 */

		// *** CouponDBDAO TEST ***//

		Calendar cal = Calendar.getInstance();
		Date startDate, endDate;

		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		startDate = new Date(cal.getTime().getTime());

		cal.set(Calendar.YEAR, 2015);
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 25);

		endDate = new Date(cal.getTime().getTime());

		Coupon c = new Coupon("Summer Coupon 20% OFF", startDate, endDate, 20,
				CouponType.FOOD, "20% OFF On New Collection", 20, "coupon1.img");

		coupondb.createCoupon(c);

		Collection<Coupon> col = coupondb
				.getAllCouponsByType(CouponType.HEALTH);
		System.out.println(col);
		System.out.println(coupondb.getCoupon(6));

		System.out.println("*****************");

		// Collection<Coupon> cs = coupondb.getAllCoupons();
		// System.out.println(cs);

		// c.setMessage("Message Updated");
		// c.setTitle("TITLE UPDATED");
		// coupondb.updateCoupon(c);

		// *** END CouponDBDAO TEST ***//

		/*
		 * 
		 * 
		 */

		// *** CustomerDBDAO TEST ***//

		CustomerDBDAO customerDb = new CustomerDBDAO();

		Customer c3 = new Customer("Mcdonald's", "1234");

		// customerDb.createCustomer(c);

		Collection<Customer> cc = customerDb.getAllCustomers();

		// customerDb.removeCustomer(c);

		c3.setCustomerName("Wall Mart");

		c3.setId(2);

		customerDb.updateCustomer(c3);

		Customer c4 = customerDb.getCustomer(3);

		System.out.println(c4);

		System.out.println(customerDb.getCoupons(c4));

		System.out.println(customerDb.login("Wall Mart", "1234"));

		// *** END CustomerDBDAO TEST ***//

		/*
		 * 
		 * 
		 */

	}
}
