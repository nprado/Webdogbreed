package mc536.dogbreed.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mc536.dogbreed.dao.ResearcherDao;
import mc536.dogbreed.entities.Researcher;

/**
 * Servlet implementation class ResearcherController
 */
public class ResearcherController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResearcherController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		
		ResearcherDao resDao = new ResearcherDao();
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		ResearcherDao resDao = new ResearcherDao();
		
		
		// Capturando os valores do cabecalho http
		if (action.equals("save")){
			String name = request.getParameter("name");
			String password = request.getParameter("password");
		
			Researcher res = new Researcher(-1,name, password,1);
			resDao.save(res);
		
		// TODO find autenticacao , list, delete
		}
				
	}

}
