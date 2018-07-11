package com.daniel.monografia.util;

import java.io.Serializable;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EnviarEmail implements Serializable {

	private static final long serialVersionUID = 1L;
	

	public void enviarEmail(String usuarioEmail, String senha, String emailDestino, String assunto,
			String mensagem) throws AddressException, MessagingException, AuthenticationFailedException {

		try {
			Properties props = System.getProperties();
			props.put("mail.smtp.host", "smtp.gmail.com");

			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "587");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.debug", true);

			//java.security.Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
			Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator(){
				protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(usuarioEmail,senha);
	            }
	        });
			mailSession.setDebug(true);

			Message mailMessage = new MimeMessage(mailSession);

			mailMessage.setFrom(new InternetAddress(usuarioEmail));
			mailMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(emailDestino));
			mailMessage.setContent(mensagem, "text/html");
			mailMessage.setSubject(assunto);

			
			Transport transport = mailSession.getTransport("smtp");
			transport.connect("smtp.gmail.com", usuarioEmail, senha);
			transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
			System.out.println("Email enviado com sucesso!!!");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}
