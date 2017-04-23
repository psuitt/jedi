package court.hack.jedi.services;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailService {
	private static Transport transport;
	private static Session session;

    static {
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.user", "court.hackathon");
        props.put("mail.smtp.password", "CourtHackathon@123");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", true);

        session = Session.getInstance(props, null);
		try {
			transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", "court.hackathon", "CourtHackathon@123");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
    }

    public static void sendEmail(String toEmailAddress, String subjectLine, String bodyContent){
        MimeMessage message = new MimeMessage(session);

        System.out.println("Port: "+session.getProperty("mail.smtp.port"));

        // Create the email addresses involved
        try {

            message.setFrom(new InternetAddress("court.hack.123@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmailAddress));
            message.setSubject(subjectLine);

            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart("alternative");

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(bodyContent);

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(bodyContent, "text/html");


            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);

            // Associate multi-part with message
            message.setContent(multipart);

            // Send message
            transport.sendMessage(message, message.getAllRecipients());


        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
