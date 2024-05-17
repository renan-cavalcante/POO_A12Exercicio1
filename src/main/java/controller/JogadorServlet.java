package controller;

import java.io.IOException;
import java.time.LocalDate;

import db.Connection;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Jogador;
import model.Time;
import persistence.JogadorDao;
import persistence.TimeDao;
import persistence.factory.DaoFactory;

@WebServlet("/jogador")
public class JogadorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public JogadorServlet() {
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

	
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("jogador.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

			request.setAttribute("erro", e.getMessage());
		}finally {
			RequestDispatcher rd = request.getRequestDispatcher("jogador.jsp");
			rd.forward(request, response);
		}
		
	}

	private void find(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		Integer codigo = Integer.parseInt(request.getParameter("codigo"));
		request.setAttribute("pessoa",jDao.findByCodigo(codigo));

	}

	private void list(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		request.setAttribute("pessoas",jDao.findAll());
	}

	private void insert(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		Jogador j = instanciaJogador(request);
		jDao.insert(j);

	}

	private void update(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		Jogador j = instanciaJogador(request);
		jDao.update(j);

	}

	private void delete(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		Integer codigo = Integer.parseInt(request.getParameter("codigo"));
		jDao.delete(codigo);

	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JogadorDao jDao = DaoFactory.jogadorDao();
		try {
			Jogador j = instanciaJogador(request);
			jDao.update(j);
		} catch (Exception e) {
			request.setAttribute("erros", e.getMessage());
		}
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		JogadorDao jDao = DaoFactory.jogadorDao();
		Integer codigo = Integer.parseInt(request.getParameter("codigo"));
		try {
			jDao.delete(codigo);
		} catch (Exception e) {
			request.setAttribute("erros", e.getMessage());
		}
	}

	private Jogador instanciaJogador(HttpServletRequest request) throws Exception {
		Jogador jogador = new Jogador();

		Integer codigo = Integer.parseInt(request.getParameter("codigo"));
		String nome = request.getParameter("nome");
		LocalDate dataNasc = LocalDate.parse(request.getParameter("dataNasc"));
		float altura = Float.parseFloat(request.getParameter("altura"));
		float peso = Float.parseFloat(request.getParameter("peso"));
		Integer codigoTime = Integer.parseInt(request.getParameter("time"));
		TimeDao td = DaoFactory.timeDao();
		Time time = td.findByCodigo(codigoTime);

		jogador.setAltura(altura);
		jogador.setId(codigo);
		jogador.setDataNasc(dataNasc);
		jogador.setNome(nome);
		jogador.setPeso(peso);
		jogador.setTime(time);

		return jogador;
	}

}
