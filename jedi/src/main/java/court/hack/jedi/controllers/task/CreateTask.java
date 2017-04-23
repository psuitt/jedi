package court.hack.jedi.controllers.task;

import court.hack.jedi.controllers.HtmlPageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/createtask")
public class CreateTask extends HtmlPageController {

    @GET
    public Response get() {
        return Response.ok(getHtmlPage("pages/create_task.html"), MediaType.TEXT_HTML_TYPE).build();
    }

}
