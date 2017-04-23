package court.hack.jedi.controllers.task;

import court.hack.jedi.beans.TaskItem;
import court.hack.jedi.controllers.HtmlPageController;
import court.hack.jedi.repositories.EventRepository;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/task")
public class Task extends HtmlPageController {
	@Inject
	private EventRepository eventRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(getHtmlPage("pages/task.html"), MediaType.TEXT_HTML_TYPE).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/data/{email}")
    public Response getJson(@PathParam("email") String email) {
    	Collection<TaskItem> tasks = eventRepository.getEventsByEmail(email);
        return Response.ok(tasks, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response post(final TaskItem taskItem) {
        return Response.ok(eventRepository.updateEvent(taskItem)).build();
    }


}
