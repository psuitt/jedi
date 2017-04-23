package court.hack.jedi.controllers.home;

import court.hack.jedi.beans.MenuItemBean;
import court.hack.jedi.controllers.HtmlPageController;
import court.hack.jedi.services.EmailService;
import court.hack.jedi.services.TextMessageService;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

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

    @GET
    @Path("/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu() {
        List<MenuItemBean> menuItemList = new LinkedList<>();
        MenuItemBean menuItem = new MenuItemBean();
        menuItem.setName("List of User");
        menuItem.setUrl("/jedi/resources/pages/users.html");
        menuItemList.add(menuItem);
        menuItem = new MenuItemBean();
        menuItem.setName("Tasks");
        menuItem.setUrl("/jedi/resources/pages/task.html");
        menuItemList.add(menuItem);
        menuItem = new MenuItemBean();
        menuItem.setName("Calendar");
        menuItem.setUrl("/jedi/resources/pages/calendar.html");
        menuItemList.add(menuItem);
        menuItem = new MenuItemBean();
        menuItem.setName("Create Task");
        menuItem.setUrl("/jedi/resources/pages/create_task.html");
        menuItemList.add(menuItem);
        menuItem = new MenuItemBean();
        menuItem.setName("Create Account");
        menuItem.setUrl("/jedi/resources/pages/create_account.html");
        menuItemList.add(menuItem);
        return Response.ok(menuItemList, MediaType.APPLICATION_JSON).build();
    }

}
