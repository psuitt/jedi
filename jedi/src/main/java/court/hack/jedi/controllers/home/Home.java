package court.hack.jedi.controllers.home;

import court.hack.jedi.beans.MenuItemBean;
import court.hack.jedi.controllers.HtmlPageController;
import court.hack.jedi.services.ReminderWorker;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by b888pcs on 4/22/2017.
 */
@Path("/home")
public class Home extends HtmlPageController {

    //Super lazy way of initializing reminders
    private ReminderWorker reminder = new ReminderWorker();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@Context HttpServletRequest request) {
        return Response.ok(getHtmlPage("pages/home.html"), MediaType.TEXT_HTML_TYPE).build();
    }

    @GET
    @Path("/menu")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMenu(@Context HttpServletRequest request) {
    	String cookieVal = "";
    	Cookie[] cookies = request.getCookies();
    	if (cookies != null) {
	    	for (Cookie cookie: cookies) {
	    		if (cookie.getName().equalsIgnoreCase("account")) {
	    			cookieVal = cookie.getValue();
	    		}
	    	}
    	}
    	String userType = null;
    	if (cookieVal.contains("USER")) {
    		userType = "USER";
    	} else {
    		userType = "ADMN";
    	}
        List<MenuItemBean> menuItemList = new LinkedList<>();
        MenuItemBean menuItem;
        if (userType.equalsIgnoreCase("ADMN")) {
            menuItem = new MenuItemBean();
	        menuItem.setName("Account List");
	        menuItem.setUrl("/jedi/resources/pages/users.html");
	        menuItemList.add(menuItem);
        }
        menuItem = new MenuItemBean();
        menuItem.setName("Tasks and Appointments");
        menuItem.setUrl("/jedi/resources/pages/task.html");
        menuItemList.add(menuItem);
        if (userType.equalsIgnoreCase("USER")) {
	        menuItem = new MenuItemBean();
	        menuItem.setName("Calendar");
	        menuItem.setUrl("/jedi/resources/pages/calendar.html");
	        menuItemList.add(menuItem);
        }
        menuItem = new MenuItemBean();
        menuItem.setName("Create Task");
        menuItem.setUrl("/jedi/resources/pages/create_task.html");
        menuItemList.add(menuItem);
        if (userType.equalsIgnoreCase("ADMN")) {
            menuItem = new MenuItemBean();
	        menuItem.setName("Create Account");
	        menuItem.setUrl("/jedi/resources/pages/create_account.html");
	        menuItemList.add(menuItem);
        }
        if (userType.equalsIgnoreCase("USER")) {
	        menuItem = new MenuItemBean();
	        menuItem.setName("Update Account");
	        menuItem.setUrl("/jedi/resources/pages/update_account.html");
	        menuItemList.add(menuItem);
        }
        return Response.ok(menuItemList, MediaType.APPLICATION_JSON).build();
    }

}
