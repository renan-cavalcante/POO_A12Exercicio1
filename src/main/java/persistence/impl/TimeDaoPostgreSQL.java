package persistence.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionPostgreSQL;
import model.Time;
import persistence.TimeDao;

public class TimeDaoPostgreSQL implements TimeDao{
	
private ConnectionPostgreSQL db;
	
	public TimeDaoPostgreSQL(ConnectionPostgreSQL db) {
		this.db = db;
	}

	@Override
	public void insert(Time t) throws SQLException, ClassNotFoundException  {
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("INSERT INTO  time(nome,cidade)  VALUES(?,?)");
			ps.setString(1, t.getNome() );
			ps.setString(2, t.getCidade());
			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
	}

	@Override
	public void update(Time t) throws SQLException, ClassNotFoundException {
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("UPDATE time set nome = ? ,cidade = ? WHERE codigo = ? ");
			ps.setString(1, t.getNome() );
			ps.setString(2, t.getCidade());
			ps.setInt(3, t.getCodigo());
			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		
	}

	@Override
	public void delete(Integer codigo) throws Exception {
		Connection conn = db.getConnection();
		PreparedStatement  ps = conn.prepareStatement("DELETE time  WHERE codigo = ?");
		ps.setInt(1, codigo);
		ps.execute();
		ps.close();
		conn.close();
		
	}

	@Override
	public Time findByCodigo(Integer codigo) throws Exception {
		Time t = new Time();
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("SELECT * FROM time  where codigo = ?");
			ps.setInt(1, t.getCodigo());
			ResultSet rs  = ps.executeQuery();
			if(rs.next()) {
				t = instanciarTime(rs);
			}
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return t;
	}

	@Override
	public List<Time> findAll() throws Exception {
		List<Time> t = new ArrayList<>();
		try {
			Connection conn = db.getConnection();
			PreparedStatement  ps = conn.prepareStatement("SELECT * FROM time ");
	
			ResultSet rs  = ps.executeQuery();
			while(rs.next()) {
				t.add(instanciarTime(rs));
			}
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			throw new SQLException(e.getMessage());
		}
		return t;
	}

	private Time instanciarTime(ResultSet rs) throws SQLException {
		Time t = new Time();
		t.setCodigo(rs.getInt("codigo"));
		t.setNome(rs.getString("nome"));
		t.setCidade(rs.getString("cidade"));
		return t;
	}
}
