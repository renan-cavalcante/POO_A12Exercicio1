package db;

public abstract class Connection {
	private static String caminho;

	public static String getCaminho() {
		return caminho;
	}

	public static void setCaminho(String caminho) {
		Connection.caminho = caminho;
	} 

}
