package br.com.carteira.infra;

public interface EnviadorDeEmail {

	public void enviarEmail(String destinatario, String assunto, String mensagem);
}