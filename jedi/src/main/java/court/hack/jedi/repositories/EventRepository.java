package court.hack.jedi.repositories;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.ApplicationScope;

import court.hack.jedi.beans.TaskItem;
 
@ApplicationScope
@ManagedBean
public class EventRepository {
	
	private static final String GET_EVENT_BY_EMAIL = "SELECT e.* FROM EVENT e JOIN ACCOUNT a on a.ACCOUNT_ID = e.OWNER_ID WHERE EMAIL = ?";
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
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
			Connection connection = ds.getConnection();
			PreparedStatement ps = connection.prepareStatement(GET_EVENT_BY_EMAIL);
			ps.setString(1, email.toLowerCase());
			ResultSet rs = ps.executeQuery();
			while (rs.next())  {
				TaskItem bean = new TaskItem();
				bean.setCreatorId(rs.getString("CREATOR_ID"));
				bean.setOwnerId(rs.getString("OWNER_ID"));
				bean.setTitle(rs.getString("TITLE"));
				bean.setDesc(rs.getString("DESCRIPTION"));
				bean.setDate(rs.getTimestamp("EVENT_DATE"));
				bean.setStatus(rs.getString("STATUS"));
				bean.setEventId(rs.getString("EVENT_ID"));
				tasks.add(bean);
			}
			return tasks;
		} catch (Exception e) {
			e.printStackTrace();
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
		} else if (StringUtils.isEmpty(event.getOwnerId())) {
			return "Owner Id is necessary.";
		} else if (StringUtils.isEmpty(event.getStatus())) {
			return "Status is necessary.";
		} else if (StringUtils.isEmpty(event.getTitle())) {
			return "Title is necessary.";
		} else {
			Context ctx;
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
				Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(INSERT_EVENT);
				ps.setString(1, RepositoryUtil.createUniqueId());
				ps.setString(2, event.getCreatorId());
				ps.setString(3, event.getOwnerId());
				ps.setString(4, event.getTitle());
				ps.setString(5, event.getDesc());
				ps.setTimestamp(6, (Timestamp) event.getDate());
				ps.setString(7, event.getStatus());
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
			try {
				ctx = new InitialContext();
				DataSource ds = (DataSource)ctx.lookup("jdbc/jedi");
				Connection connection = ds.getConnection();
				PreparedStatement ps = connection.prepareStatement(UPDATE_EVENT);
				ps.setString(1, event.getCreatorId());
				ps.setString(2, event.getOwnerId());
				ps.setString(3, event.getTitle());
				ps.setString(4, event.getDesc());
				ps.setTimestamp(5, (Timestamp) event.getDate());
				ps.setString(6, event.getStatus());
				ps.setString(7, event.getEventId());
				if (ps.executeUpdate() == 1) {
					return null;
				} else {
					return "No rows were u";
				}
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
	}
}