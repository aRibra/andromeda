package couponsystem.jms;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import couponsystem.ejb.IncomeService;
import couponsystem.ejb.db.Income;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/Income-Queue") })
public class IncomeMDB implements javax.jms.MessageListener {

	@EJB
	IncomeService incomeService;

	public void onMessage(Message msg) {
		ObjectMessage objectMessage = null;
		try {

			objectMessage = (ObjectMessage) msg;
			Income income = (Income) objectMessage.getObject();
			incomeService.storeIncome(income);

		} catch (JMSException e) {
			e.printStackTrace();
		}

	}
}
