package court.hack.jedi.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.ApplicationScope;

import court.hack.jedi.beans.AccountBean;

@ApplicationScope
@ManagedBean
public class AccountRepository {
	private static final String GET_ACCOUNT_BY_EMAIL = "SELECT * FROM ACCOUNT WHERE EMAIL = ?";
	private static final String INSERT_ACCOUNT = "INSERT INTO ACCOUNT"
			+ " (EMAIL, PASSWORD, ACCOUNT_TYPE, FIRST_NAME, LAST_NAME, PHONE_NUMBER)"
			+ " VALUES (?, ?, ?, ?, ?, ?)";
	
	private static final RowMapper<AccountBean> ACCOUNT_ROW_MAPPER = new RowMapper<AccountBean>() {
		@Override
		public AccountBean mapRow(ResultSet rs, int arg1) throws SQLException {
			return new AccountBean();
		}
	};
	
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
			ps.setString(0, email);
			ResultSet rs = ps.executeQuery();
			return ACCOUNT_ROW_MAPPER.mapRow(rs, 0);
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
				ps.setString(0, account.getEmail());
				ps.setString(0, account.getPassword());
				ps.setString(0, account.getAccountType());
				ps.setString(0, account.getFirstName());
				ps.setString(0, account.getLastName());
				ps.setString(0, account.getPhoneNumber());
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
	
	public boolean isValidUser(final String email, final String password) {
		//AccountBean user = getAccountByEmail(email);
		//return password.equals(user.getPassword());
		return true;
	}
}