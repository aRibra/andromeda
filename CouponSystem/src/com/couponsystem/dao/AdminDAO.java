package com.couponsystem.dao;

import com.couponsystem.beans.Admin;

public interface AdminDAO {

	public Admin login(String adminName, String password);

}
