package court.hack.jedi.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.ApplicationScope;

import court.hack.jedi.beans.AccountBean;

@ApplicationScope
@ManagedBean
public class AccountRepository {
	
	private static final String GET_ACCOUNT_BY_ACCOUNT_ID = "SELECT * FROM ACCOUNT WHERE ACCOUNT_ID = ?";
	private static final String GET_ACCOUNT_BY_EMAIL = "SELECT * FROM ACCOUNT WHERE EMAIL = ?";
	private static final String GET_ACCOUNTS = "SELECT * FROM ACCOUNT";
	private static final String GET_ACCOUNT_BY_EMAIL_PASSWORD = "SELECT * FROM ACCOUNT WHERE EMAIL = ? AND PASSWORD = ?";
	private static final String INSERT_ACCOUNT = "INSERT INTO ACCOUNT"
			+ " (ACCOUNT_ID, EMAIL, PASSWORD, ACCOUNT_TYPE, FIRST_NAME, LAST_NAME, PHONE)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_ACCOUNT = "UPDATE ACCOUNT"
			+ " SET EMAIL = ?, PASSWORD = ?, ACCOUNT_TYPE = ?, FIRST_NAME = ?, LAST_NAME = ?, PHONE = ?"
			+ " WHERE ACCOUNT_ID = ?";

	public AccountBean getAccountByAccountId(final String accountId) {
		if (accountId == null || accountId.isEmpty()) {
			return null;
		}

		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ACCOUNT_BY_ACCOUNT_ID);
			ps.setString(1, accountId);
			ResultSet rs = ps.executeQuery();
			AccountBean bean = new AccountBean();
			if (rs.next())  {
				bean.setAccountId(rs.getString("ACCOUNT_ID"));
				bean.setAccountType(rs.getString("ACCOUNT_TYPE"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setFirstName(rs.getString("FIRST_NAME"));
				bean.setLastName(rs.getString("LAST_NAME"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setPhoneNumber(rs.getString("PHONE"));
				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public AccountBean getAccountByEmail(final String email) {
		if (email == null || email.isEmpty()) {
			return null;
		}

		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ACCOUNT_BY_EMAIL);
			ps.setString(1, email.toLowerCase());
			ResultSet rs = ps.executeQuery();
			AccountBean bean = new AccountBean();
			if (rs.next())  {
				bean.setAccountId(rs.getString("ACCOUNT_ID"));
				bean.setAccountType(rs.getString("ACCOUNT_TYPE"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setFirstName(rs.getString("FIRST_NAME"));
				bean.setLastName(rs.getString("LAST_NAME"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setPhoneNumber(rs.getString("PHONE"));
				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Collection<AccountBean> getAccounts() {
		Collection<AccountBean> accounts = new ArrayList<>();
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ACCOUNTS);
			ResultSet rs = ps.executeQuery();
			while (rs.next())  {
				AccountBean bean = new AccountBean();
				bean.setAccountId(rs.getString("ACCOUNT_ID"));
				bean.setAccountType(rs.getString("ACCOUNT_TYPE"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setFirstName(rs.getString("FIRST_NAME"));
				bean.setLastName(rs.getString("LAST_NAME"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setPhoneNumber(rs.getString("PHONE"));
				accounts.add(bean);
			}
			return  accounts;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public AccountBean getValidatedAccountByEmail(final String email, final String password) {
		if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
			return null;
		}
		
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_ACCOUNT_BY_EMAIL_PASSWORD);
			ps.setString(1, email.toLowerCase());
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				AccountBean bean = new AccountBean();
				bean.setAccountId(rs.getString("ACCOUNT_ID"));
				bean.setAccountType(rs.getString("ACCOUNT_TYPE"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setFirstName(rs.getString("FIRST_NAME"));
				bean.setLastName(rs.getString("LAST_NAME"));
				bean.setPassword(rs.getString("PASSWORD"));
				bean.setPhoneNumber(rs.getString("PHONE"));
				return bean;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Insert an account
	 * @return an error string, or null if successful
	 */
	public String insertAccount(final AccountBean account) {
		if (account == null) {
			return "Account Bean is null.";
		} else if (StringUtils.isEmpty(account.getEmail())) {
			return "Account email is necessary.";
		} else if (StringUtils.isEmpty(account.getPassword())) {
			return "Account password is necessary.";
		} else if (StringUtils.isEmpty(account.getAccountType())) {
			return "Account type is necessary.";
		} else if (StringUtils.isEmpty(account.getFirstName())) {
			return "First name is necessary.";
		} else if (StringUtils.isEmpty(account.getLastName())) {
			return "Last name is necessary.";
		} else {
			Context ctx;
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
				Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_ACCOUNT);
				ps.setString(1, RepositoryUtil.createUniqueId());
				ps.setString(2, account.getEmail().toLowerCase());
				ps.setString(3, account.getPassword());
				ps.setString(4, account.getAccountType().toUpperCase());
				ps.setString(5, account.getFirstName());
				ps.setString(6, account.getLastName());
				ps.setString(7, account.getPhoneNumber());
				if (ps.executeUpdate() == 1) {
					return null;
				} else {
					return "No rows were inserted";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
	}
	
	/**
	 * Insert an account
	 * @return an error string, or null if successful
	 */
	public String updateAccount(final AccountBean account) {
		if (account == null) {
			return "Account Bean is null.";
		} else if (StringUtils.isEmpty(account.getAccountId())) {
			return "Account ID is necessary.";
		} else if (StringUtils.isEmpty(account.getEmail())) {
			return "Account email is necessary.";
		} else if (StringUtils.isEmpty(account.getPassword())) {
			return "Account password is necessary.";
		} else if (StringUtils.isEmpty(account.getAccountType())) {
			return "Account type is necessary.";
		} else if (StringUtils.isEmpty(account.getFirstName())) {
			return "First name is necessary.";
		} else if (StringUtils.isEmpty(account.getLastName())) {
			return "Last name is necessary.";
		} else {
			Context ctx;
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
				Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_ACCOUNT);
				ps.setString(1, account.getEmail().toLowerCase());
				ps.setString(2, account.getPassword());
				ps.setString(3, account.getAccountType().toUpperCase());
				ps.setString(4, account.getFirstName());
				ps.setString(5, account.getLastName());
				ps.setString(6, account.getPhoneNumber());
				ps.setString(7, account.getAccountId());
				if (ps.executeUpdate() == 1) {
					return null;
				} else {
					return "No rows were updated.";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
	}
}