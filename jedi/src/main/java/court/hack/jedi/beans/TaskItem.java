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

    public TaskItem(final String title, final String desc, final Date date, final String status) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(final String desc) {
        this.desc = desc;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(final Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }
}
