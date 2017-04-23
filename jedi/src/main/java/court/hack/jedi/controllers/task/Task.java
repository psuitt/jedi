package court.hack.jedi.controllers.task;

import court.hack.jedi.beans.TaskItem;
import court.hack.jedi.controllers.HtmlPageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Path("/data")
    public Response getJson() {

        List<TaskItem> list = new ArrayList<>();

        list.add(new TaskItem("Test", "text desc", new Date(), "done", "creator", "owner"));
        list.add(new TaskItem("Test", "text desc", new Date(), "closed", "creator", "owner"));
        list.add(new TaskItem("Test", "text desc", new Date(), "done", "creator", "owner"));
        list.add(new TaskItem("Appt:Test", "text desc", new Date(), "", "creator", "owner"));
        list.add(new TaskItem("Task:Test", "text desc", new Date(), "", "creator", "owner"));
        list.add(new TaskItem("Test", "text desc", new Date(), "done", "creator", "owner"));
        list.add(new TaskItem("Test", "text desc", new Date(), "done", "creator", "owner"));
        list.add(new TaskItem("Test", "text desc", new Date(), "", "creator", "owner"));

        return Response.ok(list, MediaType.APPLICATION_JSON).build();
    }

}
