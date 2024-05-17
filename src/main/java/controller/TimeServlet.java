package controller;

import java.io.BufferedReader;
import java.io.IOException;

import db.Connection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Time;
import persistence.TimeDao;
import persistence.factory.DaoFactory;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public TimeServlet() {
        super();
    }
    
	@Override
    public void init() throws ServletException {
        super.init();
    	ServletContext context = getServletContext();
    	String raiz = context.getRealPath("/");
    	DaoFactory.setCaminho(raiz);// gambiarra para obter o path da aplicação ja que system não pega o path certo
    	Connection.setCaminho(raiz);
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("time.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
response.setContentType("text/plain");
        
        // Obtém o objeto BufferedReader para ler o corpo da requisição
        BufferedReader reader = request.getReader();
        StringBuilder requestBody = new StringBuilder();
        String line;
        
        // Lê o conteúdo do corpo da requisição linha por linha
        while ((line = reader.readLine()) != null) {
            requestBody.append(line);
        }
        
        // Fecha o BufferedReader
        reader.close();
        
        // Imprime o conteúdo do corpo da requisição no console
        System.out.println("Conteúdo do corpo da requisição:");
        System.out.println(requestBody.toString());
		
		
		
		try {
			String cmd = request.getParameter("button");

			if (cmd.equals("Insert")) {
				insert(request);
			} else if (cmd.equals("Delete")) {
				delete(request);
			} else if (cmd.equals("Update")) {
				update(request);
			} else if (cmd.equals("List")) {
				list(request);
			} else if (cmd.equals("Find")) {
				find(request);
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", e.getMessage());
		}finally {
			RequestDispatcher rd = request.getRequestDispatcher("time.jsp");
			rd.forward(request, response);
		}
	}
	
	private void find(HttpServletRequest request) throws Exception {
		TimeDao tDao = DaoFactory.timeDao();
		Integer codigo = Integer.parseInt(request.getParameter("codigo"));
		request.setAttribute("pessoa",tDao.findByCodigo(codigo));

	}

	private void list(HttpServletRequest request) throws Exception {
		TimeDao tDao = DaoFactory.timeDao();
		request.setAttribute("pessoas",tDao.findAll());
	}

	private void insert(HttpServletRequest request) throws Exception {
		TimeDao tDao = DaoFactory.timeDao();
		Time t = instanciaTime(request);
		tDao.insert(t);

	}

	private void update(HttpServletRequest request) throws Exception {
		TimeDao tDao = DaoFactory.timeDao();
		Time t = instanciaTime(request);
		tDao.update(t);

	}

	private void delete(HttpServletRequest request) throws Exception {
		TimeDao tDao = DaoFactory.timeDao();
		Integer codigo = Integer.parseInt(request.getParameter("codigo"));
		tDao.delete(codigo);

	}
	
	private Time instanciaTime(HttpServletRequest request) throws Exception {
		Time time = new Time();

		Integer codigo = request.getParameter("codigo").equals("") ? 0:  Integer.parseInt(request.getParameter("codigo"));
		String nome = request.getParameter("nome");
		String cidade = request.getParameter("cidade");

		time.setCidade(cidade);
		time.setCodigo(codigo);
		time.setNome(nome);

		return time;
	}

}
