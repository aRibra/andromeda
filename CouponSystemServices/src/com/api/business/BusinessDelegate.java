package com.api.business;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Queue;

import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import couponsystem.ejb.IncomeService;
import couponsystem.ejb.IncomeServiceBean;
import couponsystem.ejb.db.Income;

public class BusinessDelegate {

	@EJB
	IncomeService incomeService;
	
	private QueueConnectionFactory qconFactory;
	private QueueConnection qcon;
	private QueueSession qsession;
	private QueueSender qsender;
	private Queue queue;
	private ObjectMessage objectMsg;

	public BusinessDelegate() {

		init();

	}

	public void storeIncome(Income income) {
		incomeService.storeIncome(income);
	}

	public Collection<Income> viewIncomeByCompany(long companyId) {
		return incomeService.viewIncomeByCompany(companyId);
	}

	public Collection<Income> viewAllIncome() {
		return incomeService.viewAllIncome();
	}

	public Collection<Income> viewIncomeByCustomer(long customerId) {
		return incomeService.viewIncomeByCustomer(customerId);
	}

	public void init() {
		try {
			InitialContext ctx = getInitialContext();
			qconFactory = (QueueConnectionFactory) ctx
					.lookup("ConnectionFactory");
			qcon = qconFactory.createQueueConnection();
			qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = (Queue) ctx.lookup("queue/Income-Queue");
			qsender = qsession.createSender((javax.jms.Queue) queue);
			objectMsg = qsession.createObjectMessage();
			qcon.start();
		} catch (NamingException e1) {
			e1.printStackTrace();
		} catch (JMSException e2) {
			e2.printStackTrace();
		}
	}

	  public void send(String message){
		try{
			objectMsg.setObject(message);
			qsender.send(objectMsg);
		}catch(JMSException e){e.printStackTrace();}
	  }
	 
	  public void close(){
		try{
		    qsender.close();
			qsession.close();
		    qcon.close();
		}catch(JMSException e1){e1.printStackTrace();
		}catch(NullPointerException e2){e2.printStackTrace();}
	  }
	
	public  InitialContext getInitialContext(){
		Hashtable<String,String> h=new Hashtable<String,String>();
		h.put("java.naming.factory.initial","org.jnp.interfaces.NamingContextFactory");
		h.put("java.naming.provider.url","localhost");
		try {
			return new InitialContext(h);
		} catch (NamingException e) {
			System.out.println("Cannot generate InitialContext");
			e.printStackTrace();
		}
		return null;
   }

}
