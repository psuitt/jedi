package court.hack.jedi.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by b888pcs on 4/22/2017.
 */
@XmlRootElement
public class TaskItem {

    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "desc")
    private String desc;
    @XmlElement(name = "date")
    private Date date;
    @XmlElement(name = "status")
    private String status;
    @XmlElement(name = "creatorId")
    private String creatorId;
    @XmlElement(name = "ownerId")
    private String ownerId;
    @XmlElement(name = "eventId")
    private String eventId;
    @XmlElement(name = "reminderDate")
    private Date reminderDate;
    @XmlElement(name = "sentFlag")
    private String sentFlag;
    @XmlElement(name = "email")
    private String email;

    public TaskItem() {
    }

	public TaskItem(final String title, final String desc, final Date date, final String status, final String creatorId, 
			final String ownerId, final Date reminderDate, final String sentFlag) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.status = status;
        this.creatorId = creatorId;
        this.ownerId = ownerId;
        this.reminderDate = reminderDate;
        this.sentFlag = sentFlag;
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

	public String getEmail() {
        return email;
    }
    
    public String getEventId() {
		return eventId;
	}

    public String getOwnerId() {
		return ownerId;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public String getSentFlag() {
		return sentFlag;
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

	public void setEmail(String email) {
        this.email = email;
    }

    public void setEventId(String eventId) {
		this.eventId = eventId;
	}

    public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

    public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

    public void setSentFlag(String sentFlag) {
		this.sentFlag = sentFlag;
	}

	public void setStatus(final String status) {
        this.status = status;
    }

	public void setTitle(final String title) {
        this.title = title;
    }
}
