package persistence.factory;

import db.ConnectionPostgreSQL;
import persistence.JogadorDao;
import persistence.TimeDao;
import persistence.impl.JogadorDaoPostgreSQL;
import persistence.impl.TimeDaoPostgreSQL;

public class DaoPostgreSQL extends DaoFactory{
	
	public TimeDao createTimeDao() {
		return new TimeDaoPostgreSQL(new ConnectionPostgreSQL());
		
	}
	
	public JogadorDao createJogadorDao() {
		return new JogadorDaoPostgreSQL(new ConnectionPostgreSQL());
	}

}
