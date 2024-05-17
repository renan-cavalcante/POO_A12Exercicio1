package model;

import java.time.LocalDate;

public class Jogador {
	private int id ;
	private String nome;
	private LocalDate dataNasc;
	private float altura;
	private float peso;
	private Time time;
	
	
	public final int getId() {
		return id;
	}
	public final void setId(int id) {
		this.id = id;
	}
	public final String getNome() {
		return nome;
	}
	public final void setNome(String nome) {
		this.nome = nome;
	}
	public final LocalDate getDataNasc() {
		return dataNasc;
	}
	public final void setDataNasc(LocalDate dataNasc) {
		this.dataNasc = dataNasc;
	}
	public final float getAltura() {
		return altura;
	}
	public final void setAltura(float altura) {
		this.altura = altura;
	}
	public final float getPeso() {
		return peso;
	}
	public final void setPeso(float peso) {
		this.peso = peso;
	}
	public final Time getTime() {
		return time;
	}
	public final void setTime(Time time) {
		this.time = time;
	}
	
	
	
}
