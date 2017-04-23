package court.hack.jedi.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by b888rml on 4/22/2017.
 */
@XmlRootElement
public class ReminderBean extends TaskItem {

	@XmlElement(name="email")
	private String email;
	@XmlElement(name="phoneNumber")
	private String phoneNumber;

    public ReminderBean() {
    }

	public String getEmail() {
		return email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
