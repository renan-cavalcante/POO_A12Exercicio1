package persistence;

import java.util.List;

import model.Jogador;

public interface JogadorDao {
	
	void insert(Jogador jogador) throws Exception;
	void update(Jogador jogador)throws Exception;
	void delete(Integer codigo )throws Exception;
	Jogador findByCodigo(Integer codigo)throws Exception;
	List<Jogador> findAll()throws Exception;

}
