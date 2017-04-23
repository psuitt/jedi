package court.hack.jedi.beans;

import java.util.Date;

/**
 * Created by b888pcs on 4/22/2017.
 */
public class TaskItem {

    private String title;
    private String desc;
    private Date date;
    private String status;
    private String creatorId;
    private String ownerId;
    private String eventId;

    public TaskItem() {
    }
    
    public TaskItem(final String title, final String desc, final Date date, final String status, final String creatorId, final String ownerId) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.status = status;
        this.creatorId = creatorId;
        this.ownerId = ownerId;
    }

    public String getCreatorId() {
		return creatorId;
	}

	public Date getDate() {
        return date;
    }

	public String getDesc() {
        return desc;
    }

	public String getEventId() {
		return eventId;
	}

	public String getOwnerId() {
		return ownerId;
	}

    public String getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

    public void setDate(final Date date) {
        this.date = date;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public void setEventId(String eventId) {
		this.eventId = eventId;
	}

    public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public void setStatus(final String status) {
        this.status = status;
    }

	public void setTitle(final String title) {
        this.title = title;
    }
}
