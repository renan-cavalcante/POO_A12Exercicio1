package persistence.factory;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

import persistence.JogadorDao;
import persistence.TimeDao;

public abstract class DaoFactory {
	private static DaoFactory df;
	private static String caminho; 
	
	
	protected abstract TimeDao createTimeDao();	
	protected abstract JogadorDao createJogadorDao();
	
	public static TimeDao timeDao() {
		if(df == null) inicialize();
		return df.createTimeDao(); //como df tem uma instacia de sua filha, o metodo usado sera da classe filha definida no arquivo de configuração
	}
	
	public static JogadorDao jogadorDao() {
		if(df == null) inicialize();
		return df.createJogadorDao(); //como df tem uma instacia de sua filha, o metodo usado sera da classe filha definida no arquivo de configuração
	}
	
	
	private static void inicialize() {// arrumar um jeito de inicializar sem ser gambiarra(inicializar no main como spring)
		try {
			Class<?> classe = Class.forName("persistence.factory."+nomeBanco().strip());// pegara o nome da classe que extende DaoFactory 
			Object instancia = classe.getDeclaredConstructor().newInstance();//instancia uma classe da filha de daoFactory 
			df = (DaoFactory)instancia; //upcast de um object(que tem a filha de DaoFactory) para DaoFactory ou seja um downcast
			
		}catch (ClassNotFoundException e) {
            System.out.println("Classe não encontrada: " + e.getMessage());
        } catch (InstantiationException e) {
            System.out.println("Erro ao instanciar a classe: " + e.getMessage());
        } catch (IllegalAccessException e) {
            System.out.println("Acesso ilegal: " + e.getMessage());
        }  catch (InvocationTargetException e) {			
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	private  static String nomeBanco() {
		Properties props = null;
		try (FileInputStream fs = new FileInputStream(caminho+"\\banco.properties")) {
			props = new Properties();
			props.load(fs);
			String dbname = props.getProperty("bdname");
			return dbname;
		} catch (IOException erro) {
			erro.printStackTrace();
		}
		return null;
	}
	public static void setCaminho(String caminho) {
		DaoFactory.caminho = caminho;
	}
	
	
}
