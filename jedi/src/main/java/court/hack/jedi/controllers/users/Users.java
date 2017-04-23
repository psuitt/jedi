package court.hack.jedi.controllers.users;

import court.hack.jedi.beans.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by b88ch on 4/22/2017.
 */
@Path("/users")
public class Users {

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        List<User> usersList = new LinkedList<>();
        User user = new User();
        user.setFirstName("Bryan");
        user.setMiddleName("F.");
        user.setLastName("Cranston");
        user.setTasks(62);
        usersList.add(user);
        user = new User();
        user.setFirstName("Michelle");
        user.setMiddleName("A.");
        user.setLastName("Odrick");
        user.setTasks(62);
        usersList.add(user);
        user = new User();
        user.setFirstName("Bob");
        user.setMiddleName("X.");
        user.setLastName("Paul");
        user.setTasks(62);
        usersList.add(user);
        return Response.ok(usersList, MediaType.APPLICATION_JSON).build();
    }

}
