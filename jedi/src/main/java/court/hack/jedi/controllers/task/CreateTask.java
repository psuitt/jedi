package court.hack.jedi.controllers.task;

import court.hack.jedi.beans.TaskItem;
import court.hack.jedi.controllers.HtmlPageController;
import court.hack.jedi.repositories.EventRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/createtask")
public class CreateTask extends HtmlPageController {

    @Inject
    private EventRepository eventRepository;

    @GET
    public Response get() {
        return Response.ok(getHtmlPage("pages/create_task.html"), MediaType.TEXT_HTML_TYPE).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response put(final TaskItem taskItem) {
        return Response.ok(eventRepository.insertEvent(taskItem)).build();
    }

}
