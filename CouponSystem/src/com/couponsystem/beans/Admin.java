package com.couponsystem.beans;

public class Admin {

	private long id;
	private String name;
	private String password;
	private ClientType clientType;

	public Admin() {
		this.clientType = ClientType.ADMIN;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ClientType getClientType() {
		return clientType;
	}

	public void setClientType(ClientType clientType) {
		this.clientType = clientType;
	}

	@Override
	public String toString() {
		return "--(Admin)--" + "\n"+ "ID=" + this.id + "\n" + "AdminName="
				+ this.name + "\n" + "Password=" + this.password + "\n"
				+ "Client Type=" + this.clientType + "\n" + "-----------";
	}

}
