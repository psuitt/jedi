package court.hack.jedi.controllers.home;

import court.hack.jedi.controllers.HtmlPageController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/home")
public class Home extends HtmlPageController {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(getHtmlPage("pages/home.html"), MediaType.TEXT_HTML_TYPE).build();
    }

}
