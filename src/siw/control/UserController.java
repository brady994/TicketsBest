package siw.control;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import siw.model.Cart;
import siw.model.User;
import siw.service.UserService;

/**
 * Servlet implementation class ClientController
 */
@WebServlet("/user")
public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
	super();
	// TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	HttpSession session = request.getSession();
	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
	String json = "";
	if (br != null) {
	    json = br.readLine();
	}
	String action = request.getParameter("action");
	JsonObject result = new JsonObject();

	User user = (User) session.getAttribute("user");
	switch (action) {
	case "update": {
	    UserService service = new UserService();
	    User tmp = service.updateUser(json, user.getId(), result);
	    if (tmp != null) {
		session.setAttribute("user", tmp);
	    }
	    break;
	}
	case "addwishlist": {
	    UserService service = new UserService();
	    service.addWishlist(json, user, result);
	    break;
	}
	case "showwishlist": {
	    UserService service = new UserService();
	    result=service.s(user, 0, 10, result);
	    break;
	}
	case "showevent": {
	    UserService service = new UserService();
	    result = service.showWishlistTickets(json, result);
	    break;
	}
	case "addticket": {
	    UserService service = new UserService();  
	    result=service.addTicketWishlist(json, result);
	    break;
	}
	case "showorder": {
	    UserService service = new UserService();
	    service.showOrders(user.getId(), 0, 10, result);
	    break;
	}
	case "showitems": {
	    UserService service = new UserService();
	    result = service.showOrderItems(json, result);
	    break;
	}
	case "deletewishlist":{
		UserService userservice = new UserService();
		result = userservice.deleteWishlist(json);
		break;
	}
	case "delticketWish":{
		UserService userservice = new UserService();
		result = userservice.deleteTicketWishList(json);
	}
	default:
	    break;

	}
	response.getWriter().write(result.toString());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	doGet(request, response);
    }

}
