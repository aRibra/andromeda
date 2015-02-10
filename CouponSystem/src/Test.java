import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.couponsystem.beans.*;
import com.couponsystem.connection.DBConnection;
import com.couponsystem.dao.*;
import com.couponsystem.facades.*;

public class Test {

	private AdminDBDAO adminDbDao;
	private CompanyDBDAO companyDbDao;
	private CustomerDBDAO customerDbDao;

	public Test() {
		adminDbDao = new AdminDBDAO();
		companyDbDao = new CompanyDBDAO();
		customerDbDao = new CustomerDBDAO();
	}

	public CouponSystemClientFacade login(String name, String password,
			ClientType type) {

		boolean loggedIn;

		switch (type) {
		case ADMIN:
			loggedIn = adminDbDao.login(name, password);

			if (loggedIn) {
				return new AdminFacade();
			} else
				System.out.println("Sorry Credentials doens't match");

			return null;

		case COMPANY:
			loggedIn = companyDbDao.login(name, password);

			if (loggedIn) {
				return new AdminFacade();
			} else
				System.out.println("Sorry Credentials doens't match");
			
			return null;
			
		case CUSTOMER:
			loggedIn = customerDbDao.login(name, password);

			if (loggedIn) {
				return new AdminFacade();
			} else
				System.out.println("Sorry Credentials doens't match");

			return null;
			
		default:
			System.out.println("Account type doesn't exist");
			return null;
		}
	}

	public static void main(String[] args) {

		// AdminDBDAO adminDbDao = new AdminDBDAO();
		// System.out.println(adminDbDao.login("ibrahim", "root"));

		// *** CLIENTFACADE TEST ***//

		Test test = new Test();

		AdminFacade admin_ibrahim = (AdminFacade) test.login("ibrahim", "root",
				ClientType.ADMIN);

		System.out.println(admin_ibrahim.getCustomerFacade(2));

		System.out.println("*****************");

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

		Company comp = new Company("Twix", "twix", "twix@snickers.bounty");

		cdb.createCompany(comp);
		System.out.println(cdb.login("Twix", "twix"));

		comp.setCompanyName("TWIXs");
		comp.setPassword("4567");

		comp.setId(2);

		cdb.updateCompany(comp);

		System.out.println("*****************");

		// *** END CompanyDBDAO TEST ***//

		/*
		 * 
		 * 
		 */

		// *** CouponDBDAO TEST ***//

		CouponDBDAO coupondb = new CouponDBDAO();

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

		System.out.println(customerDb.login("Wall Mart", "1234"));

		// *** END CustomerDBDAO TEST ***//

		/*
		 * 
		 * 
		 */

	}
}
