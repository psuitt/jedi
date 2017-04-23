package court.hack.jedi.controllers.users;

import court.hack.jedi.repositories.AccountRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by b88ch on 4/22/2017.
 */
@Path("/users")
public class Users {
	@Inject
	AccountRepository accountRepository;

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(accountRepository.getAccounts(), MediaType.APPLICATION_JSON).build();
    }

}
