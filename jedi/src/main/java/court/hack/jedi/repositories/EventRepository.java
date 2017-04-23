package court.hack.jedi.repositories;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.ApplicationScope;

import court.hack.jedi.beans.AccountBean;
import court.hack.jedi.beans.ReminderBean;
import court.hack.jedi.beans.TaskItem;
 
@ApplicationScope
@ManagedBean
public class EventRepository {
	private AccountRepository accountRepository = new AccountRepository();
	private EventReminderRepository eventRemindeRepository = new EventReminderRepository();
	
	private static final String GET_PENDING_REMINDERS = "SELECT e.*, er.REMINDER_DATE, er.SENT_FLAG, a.EMAIL, a.PHONE FROM EVENT e "
		+ " JOIN EVENT_REMINDER er on er.ACCOUNT_ID = e.OWNER_ID AND er.EVENT_ID = e.EVENT_ID"
		+ " JOIN ACCOUNT a on a.ACCOUNT_ID = e.OWNER_ID"
		+ " WHERE REMINDER_DATE <= ? AND er.SENT_FLAG = 'N'";
	private static final String GET_EVENT_BY_EMAIL = "SELECT e.*, er.REMINDER_DATE, er.SENT_FLAG FROM EVENT e "
			+ " JOIN EVENT_REMINDER er on er.ACCOUNT_ID = e.OWNER_ID AND er.EVENT_ID = e.EVENT_ID"
			+ " JOIN ACCOUNT a on a.ACCOUNT_ID = e.OWNER_ID WHERE EMAIL = ?"
			+ " ORDER BY EVENT_DATE asc";
	private static final String INSERT_EVENT = "INSERT INTO EVENT"
			+ " (EVENT_ID, CREATOR_ID, OWNER_ID, TITLE, DESCRIPTION, EVENT_DATE, STATUS)"
			+ " VALUES (?, ?, ?, ?, ?, ?, ?)";
	private static final String UPDATE_EVENT = "UPDATE EVENT"
			+ " SET  CREATOR_ID = ?, OWNER_ID = ?, TITLE = ?, DESCRIPTION = ?, EVENT_DATE = ?, STATUS = ?"
			+ " WHERE EVENT_ID = ?";

	public Collection<TaskItem> getEventsByEmail(final String email) {
		if (email == null || email.isEmpty()) {
			return null;
		}
		
		Collection<TaskItem> tasks = new ArrayList<>();
		Context ctx;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
			connection = ds.getConnection();
			ps = connection.prepareStatement(GET_EVENT_BY_EMAIL);
			ps.setString(1, email.toLowerCase());
			rs = ps.executeQuery();
			while (rs.next())  {
				TaskItem bean = new TaskItem();
				bean.setCreatorId(rs.getString("CREATOR_ID"));
				bean.setOwnerId(rs.getString("OWNER_ID"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setDesc(rs.getString("DESCRIPTION"));
				bean.setDate(rs.getTimestamp("EVENT_DATE"));
				bean.setStatus(rs.getString("STATUS"));
				bean.setEventId(rs.getString("EVENT_ID"));
				bean.setReminderDate(rs.getTimestamp("REMINDER_DATE"));
				bean.setSentFlag(rs.getString("SENT_FLAG"));
				tasks.add(bean);
			}
			rs.close();
			ps.close();
			connection.close();
			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public Collection<ReminderBean> getPendingReminders() {
		final Date today = new Date();
		Collection<ReminderBean> reminders = new ArrayList<>();
		Context ctx;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
			connection = ds.getConnection();
			ps = connection.prepareStatement(GET_PENDING_REMINDERS);
			ps.setTimestamp(1, new Timestamp(today.getTime()));
			rs = ps.executeQuery();
			while (rs.next())  {
				ReminderBean bean = new ReminderBean();
				bean.setCreatorId(rs.getString("CREATOR_ID"));
				bean.setOwnerId(rs.getString("OWNER_ID"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setDesc(rs.getString("DESCRIPTION"));
				bean.setDate(rs.getTimestamp("EVENT_DATE"));
				bean.setStatus(rs.getString("STATUS"));
				bean.setEventId(rs.getString("EVENT_ID"));
				bean.setReminderDate(rs.getTimestamp("REMINDER_DATE"));
				bean.setSentFlag(rs.getString("SENT_FLAG"));
				bean.setEmail(rs.getString("EMAIL"));
				bean.setPhoneNumber(rs.getString("PHONE"));
				reminders.add(bean);
			}
			rs.close();
			ps.close();
			connection.close();
			return reminders;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				ps.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Insert an event
	 * @return an error string, or null if successful
	 */
	public String insertEvent(final TaskItem event) {
		if (event == null) {
			return "TaskItem is null.";
		} else if (StringUtils.isEmpty(event.getCreatorId())) {
			return "Creator Id is necessary.";
		} else if (StringUtils.isEmpty(event.getDate())) {
			return "Date is necessary.";
		} else if (StringUtils.isEmpty(event.getDesc())) {
			return "Description is necessary.";
		} else if (StringUtils.isEmpty(event.getOwnerId()) && StringUtils.isEmpty(event.getEmail())) {
			return "Owner Id or Email is necessary.";
		} else if (StringUtils.isEmpty(event.getStatus())) {
			return "Status is necessary.";
		} else if (StringUtils.isEmpty(event.getTitle())) {
			return "Title is necessary.";
		} else {
			Context ctx;
			Connection connection = null;
			PreparedStatement ps = null;
			event.setEventId(RepositoryUtil.createUniqueId());
			if (StringUtils.isEmpty(event.getOwnerId())) {
				AccountBean ownerAccount = accountRepository.getAccountByEmail(event.getEmail());
				if (ownerAccount == null || StringUtils.isEmpty(ownerAccount.getAccountId())) {
					return "Unable to find owner account.";
				}
				event.setOwnerId(ownerAccount.getAccountId());
			}
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
				connection = ds.getConnection();
				ps = connection.prepareStatement(INSERT_EVENT);
				ps.setString(1, event.getEventId());
				ps.setString(2, event.getCreatorId());
				ps.setString(3, event.getOwnerId());
				ps.setString(4, event.getTitle());
				ps.setString(5, event.getDesc());
				ps.setObject(6, event.getDate());
				ps.setString(7, event.getStatus());
				if (ps.executeUpdate() == 1) {
				    ps.close();
				    connection.close();
					eventRemindeRepository.insertEvent(event);
					ps.close();
					connection.close();
					return null;
				} else {
					return "No rows were inserted";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			} finally {
				try {
					ps.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
        }
	}
	
	/**
	 * Update an event
	 * @return an error string, or null if successful
	 */
	public String updateEvent(final TaskItem event) {
		if (event == null) {
			return "TaskItem is null.";
		} else if (StringUtils.isEmpty(event.getEventId())) {
			return "Event Id is necessary.";
		} else if (StringUtils.isEmpty(event.getCreatorId())) {
			return "Creator Id is necessary.";
		} else if (StringUtils.isEmpty(event.getDate())) {
			return "Date is necessary.";
		} else if (StringUtils.isEmpty(event.getDesc())) {
			return "Description is necessary.";
		} else if (StringUtils.isEmpty(event.getOwnerId())) {
			return "Owner Id is necessary.";
		} else if (StringUtils.isEmpty(event.getStatus())) {
			return "Status is necessary.";
		} else if (StringUtils.isEmpty(event.getTitle())) {
			return "Title is necessary.";
		} else {
			Context ctx;
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
				connection = ds.getConnection();
				ps = connection.prepareStatement(UPDATE_EVENT);
				ps.setString(1, event.getCreatorId());
				ps.setString(2, event.getOwnerId());
				ps.setString(3, event.getTitle());
				ps.setString(4, event.getDesc());
				ps.setObject(5, event.getDate());
				ps.setString(6, event.getStatus());
				ps.setString(7, event.getEventId());
				if (ps.executeUpdate() == 1) {
				    ps.close();
				    connection.close();
					eventRemindeRepository.updateEvent(event);
					return null;
				} else {
					return "No rows were updated";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			} finally {
				try {
					ps.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}