package couponsystem.ejb;

import java.util.Collection;

import javax.ejb.Remote;

import couponsystem.ejb.db.Income;

@Remote
public interface IncomeService {

	public void storeIncome(Income income);
	public Collection<Income> viewAllIncome();
	public Collection<Income> viewIncomeByCustomer(long customerId);
	public Collection<Income> viewIncomeByCompany(long companyId);
	
}
