package court.hack.jedi.controllers.calendar;

import court.hack.jedi.controllers.HtmlPageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/calendar")
public class Calendar extends HtmlPageController{

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(getHtmlPage("pages/calendar.html"), MediaType.TEXT_HTML_TYPE).build();
    }
}
