package court.hack.jedi.controllers.createAccount;

import court.hack.jedi.beans.AccountBean;
import court.hack.jedi.controllers.HtmlPageController;
import court.hack.jedi.repositories.AccountRepository;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.util.StringUtils;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/create_account")
public class CreateAccountController extends HtmlPageController {
	private AccountRepository accountRepository = new AccountRepository();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
        return Response.ok(getHtmlPage("pages/create_account.html"), MediaType.TEXT_HTML_TYPE).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_HTML)
    public Response post(AccountBean account) {
    	AccountBean existingAccount = accountRepository.getAccountByEmail(account.getEmail());
    	if (existingAccount != null) {
    		System.out.println("Account already exists!");
    		return Response.ok("Account already exists!", MediaType.TEXT_HTML_TYPE).build();
    	}
    	String errorMessage = accountRepository.insertAccount(account);
    	if (!StringUtils.isEmpty(errorMessage)) {
    		System.out.println(errorMessage);
    		return Response.ok(errorMessage, MediaType.TEXT_HTML_TYPE).build();
    	}
    	return null;
    }

}
