package services.login;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;

import domain.User;
import services.UserService;

public class Gmail {
	
	@Autowired
	private static UserService userService;

	private static final String SMTP_HOST_NAME = "smtp.gmail.com";
	private static final int SMTP_HOST_PORT = 465;
	private static final String SMTP_AUTH_USER = "grupo8ispp@gmail.com";
	private static final String SMTP_AUTH_PWD = "ispp8grupo";
	
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

	public static String send(String idioma,String receptor) throws Exception {

		Properties props = new Properties();

		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtps.host", SMTP_HOST_NAME);
		props.put("mail.smtps.auth", "true");

		Session mailSession = Session.getDefaultInstance(props);

		Transport transport = mailSession.getTransport();
		String nuevaContraseña=getCadenaAlfanumAleatoria(16);
		MimeMessage message = new MimeMessage(mailSession);
		if(idioma=="es"){
			message.setSubject("Recuperación de contraseña");
			message.setContent("Tu nueva contraseña es: "+nuevaContraseña, "text/plain");
		}
		else if(idioma=="en"){
			message.setSubject("Password recovery");
			message.setContent("Your new password is: "+nuevaContraseña, "text/plain");
		}
		

		message.addRecipient(Message.RecipientType.TO, new InternetAddress(receptor));

		transport.connect(SMTP_HOST_NAME, SMTP_HOST_PORT, SMTP_AUTH_USER, SMTP_AUTH_PWD);

		transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
		transport.close();
		return nuevaContraseña;
	}

	public static Map<Boolean,User> existeUsuario(String username, String correo){
		Boolean b=false;
		User user=new User();
		Map<Boolean,User> res=new HashMap<Boolean,User>();
		for(User u:userService.findAll()){
			if(u.getUserAccount().getUsername()==username &&u.getEmail()==correo){
				b=true;
				user=u;
				break;
			}
		}
		res.put(b, user);
		return res;
	}
	
	
	public static void main(String[] args) throws Exception{
//		send("Hola caracola","");
		System.out.println(getCadenaAlfanumAleatoria(16));
	}
}