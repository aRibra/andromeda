import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;

import com.couponsystem.CouponSystem;
import com.couponsystem.beans.*;
import com.couponsystem.dao.*;
import com.couponsystem.exceptions.CouponSystemException;
import com.couponsystem.facades.*;
import com.couponsystem.helper.classes.ClientBucket;

public class Test {

	public static void main(String[] args) throws CouponSystemException {

		// *** CONNECTIONPOOL TEST ***//

		// ConnectionPool connectionPool = ConnectionPool.getInstance();

		// RemoverThread remover = new RemoverThread(connectionPool);

		// remover.start();

		// List<ConnectionThread> list = new ArrayList<>();
		//
		// for (int i = 0; i < 15; i++) {
		// list.add(new ConnectionThread(connectionPool, i));
		// list.get(i).start();
		// }

		System.out.println("Wenak 7abeebe wenak.");

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

		ClientBucket clientBucket = couponSystem.login("Wall Mart", "1234",
				ClientType.CUSTOMER);

		AdminFacade adminFacade = null;
		CompanyFacade companyFacade = null;
		CustomerFacade customerFacade = null;

		Admin admin = null;
		Company company = null;
		Customer customer = null;

		if (clientBucket.getFacade() instanceof AdminFacade) {
			adminFacade = (AdminFacade) clientBucket.getFacade();
		} else if (clientBucket.getFacade() instanceof CompanyFacade) {
			companyFacade = (CompanyFacade) clientBucket.getFacade();
			company = (Company) clientBucket.getClient();
		} else if (clientBucket.getFacade() instanceof CustomerFacade) {
			customerFacade = (CustomerFacade) clientBucket.getFacade();
			customer = (Customer) clientBucket.getClient();
		}

		System.out.println(".--.--.--.--.--.--.--.--.--.--.--."
				+ customerFacade.getAllPurchasedCoupons(customer));

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

		//System.out.println(coupondb.getCompanyCouponsByType(comp));

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

		cal.set(Calendar.YEAR, 2016);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		startDate = new Date(cal.getTime().getTime());

		cal.set(Calendar.YEAR, 2016);
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

		c.setId(99);
		coupondb.removeCoupon(c);

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

		Customer c3 = new Customer("Mcdonald's", "1234", ClientType.CUSTOMER);

		// customerDb.createCustomer(c);

		Collection<Customer> cc = customerDb.getAllCustomers();

		// customerDb.removeCustomer(c);

		c3.setClientName("Wall Mart");

		c3.setClientId(2);

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

		couponSystem.shutdownCouponSystem();

	}
}
