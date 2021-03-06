package court.hack.jedi.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.ApplicationScope;

import court.hack.jedi.beans.TaskItem;
 
@ApplicationScope
@ManagedBean
public class EventReminderRepository {

	private static final String INSERT_EVENT_REMINDER = "INSERT INTO EVENT_REMINDER"
			+ " (EVENT_ID, ACCOUNT_ID, REMINDER_DATE, SENT_FLAG)"
			+ " VALUES (?, ?, ?, ?)";
	private static final String UPDATE_EVENT_REMINDER = "UPDATE EVENT_REMINDER"
			+ " SET  REMINDER_DATE = ?, SENT_FLAG = ?"
			+ " WHERE EVENT_ID = ? AND ACCOUNT_ID = ?";

	/**
	 * Insert an event reminder
	 * @return an error string, or null if successful
	 */
	public String insertEvent(final TaskItem event) {
		if (event == null) {
			return "TaskItem is null.";
		} else if (StringUtils.isEmpty(event.getOwnerId())) {
			return "Owner Id is necessary.";
		} else if (StringUtils.isEmpty(event.getEventId())) {
			return "Event Id is necessary.";
		} else if (StringUtils.isEmpty(event.getReminderDate())) {
			return "ReminderWorker Date is necessary.";
		} else if (StringUtils.isEmpty(event.getSentFlag())) {
			return "Sent Flag is necessary.";
		} else {
			Context ctx;
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
				connection = ds.getConnection();
				ps = connection.prepareStatement(INSERT_EVENT_REMINDER);
				ps.setString(1, event.getEventId());
				ps.setString(2, event.getOwnerId());
				ps.setObject(3, event.getReminderDate());
				ps.setString(4, event.getSentFlag());
				if (ps.executeUpdate() == 1) {
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
	 * Update an event reminder
	 * @return an error string, or null if successful
	 */
	public String updateEvent(final TaskItem event) {
		if (event == null) {
			return "TaskItem is null.";
		} else if (StringUtils.isEmpty(event.getOwnerId())) {
			return "Owner Id is necessary.";
		} else if (StringUtils.isEmpty(event.getEventId())) {
			return "Event Id is necessary.";
		} else if (StringUtils.isEmpty(event.getReminderDate())) {
			return "ReminderWorker Date is necessary.";
		} else if (StringUtils.isEmpty(event.getSentFlag())) {
			return "Sent Flag is necessary.";
		} else {
			Context ctx;
			Connection connection = null;
			PreparedStatement ps = null;
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
				connection = ds.getConnection();
				ps = connection.prepareStatement(UPDATE_EVENT_REMINDER);
				ps.setObject(1, event.getReminderDate());
				ps.setString(2, event.getSentFlag());
				ps.setString(3, event.getEventId());
				ps.setString(4, event.getOwnerId());
				if (ps.executeUpdate() == 1) {
					ps.close();
					connection.close();
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