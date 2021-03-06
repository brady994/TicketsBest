package siw.control;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import siw.model.Cart;
import siw.model.Organizer;
import siw.model.Type;
import siw.model.User;
import siw.service.AccountService;

/**
 * Servlet implementation class AccountServlet
 */
@WebServlet("/account")
public class AccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AccountServlet() {
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// read the ajax request
		BufferedReader br = new BufferedReader(request.getReader());
		String json = "";
		if (br != null) {
			json = br.readLine();
		}
		// get the action
		String action = request.getParameter("action");
		JsonObject result = new JsonObject();
		switch (action) {
		case "signup":
			AccountService.signUp(json, result);
			break;
		case "signin":
			User user = AccountService.signIn(json, result);
			if (user != null) {
				HttpSession session = request.getSession();				
				
				if (user.getType().equals(Type.Organizer))
				{
					Organizer organizer = new Organizer();
					organizer.setId(user.getId());
					organizer.setEmail(user.getEmail());
					organizer.setName(user.getName());
					organizer.setPassword(user.getPassword());
					organizer.setUsername(user.getUsername());
					session.setAttribute("organizer", organizer);
				}else{
				session.setAttribute("user", user);
				}
				
			}
			break;
		case "logout":
			request.getSession().invalidate();
			RequestDispatcher dispatcher = request.getRequestDispatcher("home");
			dispatcher.forward(request, response);
			break;
		default:
			result.addProperty("result", "FAIL");
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
