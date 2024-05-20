package model;

public class Time {
	
	 private  int codigo;
	 private  String nome;
     private  String cidade;
     
	public final int getCodigo() {
		return codigo;
	}
	public final void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public final String getNome() {
		return nome;
	}
	public final void setNome(String nome) {
		this.nome = nome;
	}
	public final String getCidade() {
		return cidade;
	}
	public final void setCidade(String cidade) {
		this.cidade = cidade;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Time [codigo=");
		builder.append(codigo);
		builder.append(", nome=");
		builder.append(nome);
		builder.append(", cidade=");
		builder.append(cidade);
		builder.append("]");
		return builder.toString();
	}
     
     
}
