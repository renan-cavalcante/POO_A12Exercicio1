package persistence;

import java.util.List;

import model.Time;

public interface TimeDao {
	
	void insert(Time time)throws Exception;
	void update(Time time)throws Exception;
	void delete(Integer codigo)throws Exception;
	Time findByCodigo(Integer codigo)throws Exception;
	List<Time> findAll()throws Exception;
	
}
