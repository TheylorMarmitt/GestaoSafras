package br.edu.unoesc.contatoemail;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.RandomStringUtils;

public class EmailUtil {
	
	public static String enviarEmail(String para) throws Exception {
		Properties properties = new Properties();
		
		properties.put("mail.smtp.auth", "configuração email");
		properties.put("mail.smtp.starttls.enable", "configuração email");

		properties.put("mail.smtp.host", "configuração email");
		properties.put("mail.smtp.port", "configuração email");
		
		String meuEmail = "email";
		String senha = "senha";
		
		Session session = Session.getInstance(properties, new Authenticator(){
			
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(meuEmail, senha);
			}
		});
		
		String senhaTemp = geradorSenha();
		
		Message mensagem = prepareMessage(session, meuEmail, para, senhaTemp);
		
		Transport.send(mensagem);
		
		return senhaTemp;
	}

	private static Message prepareMessage(Session session, String meuEmail, String para, String senhaTemp) {
		try {
			Message mensagem = new MimeMessage(session);
			mensagem.setFrom(new InternetAddress(meuEmail));
			mensagem.setRecipient(Message.RecipientType.TO, new InternetAddress(para));
			mensagem.setSubject("Recuperação de senha");
			mensagem.setText("Está senha é temporária, favor mudar na aba Editar Usuário ao entrar no sistema. "
					+ "Entre utilizando a senha: "+ senhaTemp);
			return mensagem;
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	private static String geradorSenha() {
		String cararcteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*-_";
		String senha = RandomStringUtils.random( 8, cararcteres );
		return senha;
	}
	

}
