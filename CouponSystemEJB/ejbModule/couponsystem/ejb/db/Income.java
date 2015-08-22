package couponsystem.ejb.db;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "INCOME")
public class Income implements Serializable {

	private long id;
	private String executorName;
	private IncomeType description;
	private Date date;
	private double amount;

	@Id
	@GeneratedValue
	@Column(name = "INCOME_ID", nullable = false, columnDefinition = "integer")
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "EXECUTOR_NAME", nullable = false)
	public String getExecutorName() {
		return executorName;
	}

	public void setExecutorName(String executorName) {
		this.executorName = executorName;
	}

	@Column(name = "DESCRIPTION", nullable = false)
	@Enumerated(EnumType.STRING)
	public IncomeType getDescription() {
		return description;
	}

	public void setDescription(IncomeType description) {
		this.description = description;
	}

	@Column(name = "AMOUNT", nullable = false)
	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

}
