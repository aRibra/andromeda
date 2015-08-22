package couponsystem.ejb;

import java.util.Collection;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import couponsystem.ejb.db.Income;

@Stateless(name = "IncomeServiceEJB")
public class IncomeServiceBean implements IncomeService {

	@PersistenceContext(unitName = "income")
	private EntityManager entityManager;
	
	
	@Override
	public void storeIncome(Income income) {
		entityManager.persist(income);
	}

	@Override
	public Collection<Income> viewAllIncome() {
		Query query = entityManager.createQuery("SELECT * FROM INCOME");
	    return (Collection<Income>) query.getResultList();
	}

	@Override
	public Collection<Income> viewIncomeByCustomer(long customerId) {
		Query query = entityManager.createQuery("SELECT * FROM INCOME WHERE DESCRIPTION='CUSTOMER_PURCHASE' ");
	    return (Collection<Income>) query.getResultList();
	}

	@Override
	public Collection<Income> viewIncomeByCompany(long companyId) {
		Query query = entityManager.createQuery("SELECT * FROM INCOME WHERE DESCRIPTION='COMPANY_NEW_COUPON' ");
	    return (Collection<Income>) query.getResultList();
	}

}
