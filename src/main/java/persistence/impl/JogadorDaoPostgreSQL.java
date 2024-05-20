package persistence.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionPostgreSQL;
import model.Jogador;
import model.Time;
import persistence.JogadorDao;

public class JogadorDaoPostgreSQL implements JogadorDao{
	
	private ConnectionPostgreSQL db;
	
	public JogadorDaoPostgreSQL(ConnectionPostgreSQL db) {
		this.db = db;
	}

	@Override
	public void insert(Jogador j) throws SQLException, ClassNotFoundException {
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("INSERT INTO  jogador(nome,data_nasc,altura,peso,TimeCodigo)  VALUES(?,?,?,?,?)");
			ps.setString(1, j.getNome() );
			ps.setDate(2, Date.valueOf(j.getDataNasc()));
			ps.setFloat(3, j.getAltura());
			ps.setFloat(4, j.getPeso());
			ps.setInt(5, j.getTime().getCodigo());
			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
	}

	@Override
	public void update(Jogador j) throws SQLException, ClassNotFoundException {
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("UPDATE  jogador SET nome = ?,data_nasc = ? ,altura = ? ,peso = ? ,TimeCodigo = ?  WHERE id = ?");
			ps.setString(1, j.getNome() );
			ps.setDate(2, Date.valueOf(j.getDataNasc()));
			ps.setFloat(3, j.getAltura());
			ps.setFloat(4, j.getPeso());
			ps.setInt(5, j.getTime().getCodigo());
			ps.setInt(6, j.getId());
			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
	}

	@Override
	public void delete(Integer c) throws SQLException, ClassNotFoundException {
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("DELETE FROM jogador  WHERE id = ?");
			ps.setInt(1, c);
			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
	}

	@Override
	public Jogador findByCodigo(Integer codigo) throws SQLException, ClassNotFoundException {
		Jogador j = null;
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("SELECT *, jogador.nome as jogadorNome, time.nome as timeNome FROM jogador join time on TimeCodigo = codigo where id = ?");
			ps.setInt(1, codigo);
			ResultSet rs  = ps.executeQuery();
			if(rs.next()) {
				Time t = instanciarTime(rs);
				j = (instanciarJogador(rs, t));
			}
			ps.close();
			conn.close();
			if(j == null) throw new SQLException("Jogador não encotrado");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return j;
	}

	@Override
	public List<Jogador> findAll() throws SQLException, ClassNotFoundException {
		List<Jogador> jogadores = new ArrayList<Jogador>();
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("SELECT *, time.nome as timeNome, jogador.nome as jogadorNome FROM jogador join time on TimeCodigo = codigo");
			ResultSet rs  = ps.executeQuery();
			while(rs.next()) {
				Time t = instanciarTime(rs);
				jogadores.add(instanciarJogador(rs, t));
			}
			ps.close();
			conn.close();
			if(jogadores.size() == 0) throw new SQLException("Nenhum jogador cadastrado");
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return jogadores;
	}
	
	private Time instanciarTime(ResultSet rs) throws SQLException {
		Time t = new Time();
		t.setCodigo(rs.getInt("codigo"));
		t.setNome(rs.getString("timeNome"));
		t.setCidade(rs.getString("cidade"));
		return t;
	}

	private Jogador instanciarJogador(ResultSet rs, Time t) throws SQLException {
		Jogador j = new Jogador();
		j.setId(rs.getInt("id"));
		j.setNome(rs.getString("jogadorNome"));
		j.setDataNasc(LocalDate.parse(rs.getString("data_nasc")));
		j.setAltura(rs.getFloat("altura"));
		j.setPeso(rs.getFloat("peso"));
		j.setTime(t);
		return j;
		
	}

}
