package services.login;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;


@Service
@Transactional
public class MailService {

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final int SMTP_HOST_PORT = 465;
	private static final String SMTP_AUTH_USER = "gamingpals465@gmail.com";
	private static final String SMTP_AUTH_PWD = "gamingpals94";

	public static String getCadenaAlfanumAleatoria(int longitud) {
		String cadenaAleatoria = "";
		long milis = new java.util.GregorianCalendar().getTimeInMillis();
		Random r = new Random(milis);
		int i = 0;
		while (i < longitud) {
			char c = (char) r.nextInt(255);
			if ((c >= '0' && c <= '9') || (c >= 'A' && c <= 'Z')|| (c >= 'a' && c <= 'z')) {
				cadenaAleatoria += c;
				i++;
			}
		}
		return cadenaAleatoria;
	}

	public  String send(String idioma,String receptor) throws Exception {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", SMTP_HOST_NAME);
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);

		Transport transport = mailSession.getTransport();
		String nuevaContrasena=getCadenaAlfanumAleatoria(16);
		MimeMessage message = new MimeMessage(mailSession);
		if(idioma.equals("es")){
			message.setSubject("Recuperación de contraseÃ±a");
			message.setContent("Tu nueva contraseÃ±a es: "+nuevaContrasena, "text/plain");
		}
		else if(idioma.equals("en")){
			message.setSubject("Password recovery");
			message.setContent("Your new password is: "+nuevaContrasena, "text/plain");
		}


		message.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor,true));

		transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);
		System.out.println(Message.RecipientType.TO);
		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
		return nuevaContrasena;
	}


	public static void main(String[] args) throws Exception{
		MailService mailService = new MailService();
		mailService.send("es","skyluna7@gmail.com");
		System.out.println(getCadenaAlfanumAleatoria(16));
	}
}