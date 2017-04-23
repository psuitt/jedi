package court.hack.jedi.controllers.login;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import court.hack.jedi.controllers.HtmlPageController;
import court.hack.jedi.repositories.AccountRepository;

/**
 * Created by b888pcs on 4/22/2017.
 */

@Path("/login")
public class Login extends HtmlPageController {
	@Inject
	AccountRepository accountRepository;
	
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(getHtmlPage("pages/login.html"), MediaType.TEXT_HTML_TYPE).build();
    }

    @POST
    public Boolean post(String userpass) {
    	Boolean valid = false;
    	
    	String[] parts = userpass.split(",");
    	String user = parts[0];
    	String pass = parts[1];
    	
    	valid = accountRepository.isValidUser(user, pass);
    	
    	if (!valid) {
    		return false;
    	}
    	return true;
    }

    
}
