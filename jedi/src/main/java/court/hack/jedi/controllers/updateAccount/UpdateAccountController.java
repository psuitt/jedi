package court.hack.jedi.controllers.updateAccount;

import court.hack.jedi.beans.AccountBean;
import court.hack.jedi.controllers.HtmlPageController;
import court.hack.jedi.repositories.AccountRepository;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.util.StringUtils;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/update_account")
public class UpdateAccountController extends HtmlPageController {
	@Inject
	AccountRepository accountRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(getHtmlPage("pages/update_account.html"), MediaType.TEXT_HTML_TYPE).build();
    }

    @GET
    @Path("/{accountId}")
    public Response getJson(@PathParam("accountId") String accountId) {
    	AccountBean account = accountRepository.getAccountByAccountId(accountId);
        return Response.ok(account, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response post(AccountBean account) {
    	String errorMessage = accountRepository.updateAccount(account);
    	if (!StringUtils.isEmpty(errorMessage)) {
    		System.out.println(errorMessage);
    		return Response.ok(errorMessage, MediaType.TEXT_HTML_TYPE).build();
    	}
    	return null;
    }

}
