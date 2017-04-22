package court.hack.jedi.controllers.task;

import court.hack.jedi.controllers.HtmlPageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/task")
public class Task extends HtmlPageController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(getHtmlPage("pages/task.html"), MediaType.TEXT_HTML_TYPE).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("json")
    public Response getJson() {
        return Response.ok(new TaskItem("Test", "text desc", new Date()), MediaType.APPLICATION_JSON).build();
    }

    public class TaskItem {

        private String title;
        private String desc;
        private Date date;

        public TaskItem(final String title, final String desc, final Date date) {
            this.title = title;
            this.desc = desc;
            this.date = date;
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
    }

}
