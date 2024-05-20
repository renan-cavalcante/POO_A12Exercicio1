package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
		TimeDao td = DaoFactory.timeDao();
		try {
			request.setAttribute("time", td.findAll());
		} catch (Exception e) {
			request.setAttribute("erro", e.getMessage());
			e.printStackTrace();
		}
		RequestDispatcher rd = request.getRequestDispatcher("jogador.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
				
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
			TimeDao td = DaoFactory.timeDao();
			request.setAttribute("time", td.findAll());
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("erro", e.getMessage());
		}finally {
			
			RequestDispatcher rd = request.getRequestDispatcher("jogador.jsp");
			rd.forward(request, response);
		}
	}

	private void find(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		Integer codigo = Integer.parseInt(request.getParameter("id"));
		request.setAttribute("jogador",jDao.findByCodigo(codigo));
	}

	private void list(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		request.setAttribute("jogadores",jDao.findAll());
	}

	private void insert(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		Jogador j = instanciaJogador(request);
		jDao.insert(j);
	}

	private void update(HttpServletRequest request) throws Exception {
		if(request.getParameter("id").equals("")) throw new IllegalArgumentException("Preencha id");
		JogadorDao jDao = DaoFactory.jogadorDao();
		
		Integer codigo =   Integer.parseInt(request.getParameter("id"));
		
		Jogador j = jDao.findByCodigo(codigo);
		
		if(request.getParameter("nome") != null && !request.getParameter("nome").equals("")) j.setNome(request.getParameter("nome"));
		if(request.getParameter("dataNasc") != null && !request.getParameter("dataNasc").equals("")) j.setDataNasc(LocalDate.parse(request.getParameter("dataNasc"),  DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		if(request.getParameter("altura") != null && !request.getParameter("altura").equals("")) j.setAltura(Float.parseFloat(request.getParameter("altura")));
		if(request.getParameter("peso") != null && !request.getParameter("peso").equals("")) j.setPeso(Float.parseFloat(request.getParameter("peso")));
		if(request.getParameter("time_id") != null && !request.getParameter("time_id").equals("")) { 
			Integer codigoTime = Integer.parseInt(request.getParameter("time_id"));
			TimeDao td = DaoFactory.timeDao();
			Time time = td.findByCodigo(codigoTime);
			j.setTime(time);
		}
	
		jDao.update(j);
	}

	private void delete(HttpServletRequest request) throws Exception {
		JogadorDao jDao = DaoFactory.jogadorDao();
		if(request.getParameter("id").equals("")) throw new IllegalArgumentException("Preencha id");
		Integer codigo = Integer.parseInt(request.getParameter("id"));
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
		Integer codigo = Integer.parseInt(request.getParameter("id")); 
		try {
			jDao.delete(codigo);
		} catch (Exception e) {
			request.setAttribute("erros", e.getMessage());
		}
	}

	private Jogador instanciaJogador(HttpServletRequest request) throws Exception {
		Jogador jogador = new Jogador();
		
		if(request.getParameter("nome").equals("")) throw new IllegalArgumentException("Preencha o nome");
		if(request.getParameter("dataNasc").equals("")) throw new IllegalArgumentException("Preencha a data");
		if(request.getParameter("altura").equals("")) throw new IllegalArgumentException("Preencha a altura");
		if(request.getParameter("peso").equals("")) throw new IllegalArgumentException("Preencha o peso");
		if(request.getParameter("time_id").equals("")) throw new IllegalArgumentException("Preencha o time");
		
		String nome = request.getParameter("nome");
		LocalDate dataNasc = LocalDate.parse(request.getParameter("dataNasc"),  DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		float altura = Float.parseFloat(request.getParameter("altura"));
		float peso = Float.parseFloat(request.getParameter("peso"));
		Integer codigoTime = Integer.parseInt(request.getParameter("time_id"));
		TimeDao td = DaoFactory.timeDao();
		Time time = td.findByCodigo(codigoTime);
		
		jogador.setAltura(altura);
		jogador.setDataNasc(dataNasc);
		jogador.setNome(nome);
		jogador.setPeso(peso);
		jogador.setTime(time);

		return jogador;
	}
}