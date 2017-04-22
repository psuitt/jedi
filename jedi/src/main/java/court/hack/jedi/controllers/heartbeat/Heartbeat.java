package court.hack.jedi.controllers.heartbeat;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/heartbeat")
public class Heartbeat {

    @GET
    public Response get() {
        return Response.ok("test").build();
    }

}
