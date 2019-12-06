import com.sun.mail.smtp.SMTPTransport;
import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Properties;


public class EmailSender {
    private final String SMTP_SERVER;
    private final String sender;
    private final String pass;
    private String recipient;
    EmailSender(){
        this.SMTP_SERVER = "smtp.gmail.com";
        this.sender = "youngc467@gmail.com";
        this.pass = ""; // enter password
        this.recipient = "charles.arellano75@gmail.com";
    }

    public void send(ArrayList<MoviesToEmail> eM){
        Properties properties = System.getProperties();
        properties.setProperty("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        Session session = Session.getInstance(properties, null);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.sender));
            message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(this.recipient,false));
            message.setSubject("List of Movies for the Week of: " + java.time.LocalDate.now());

            StringBuilder sb = new StringBuilder();
            sb.append("Movies you might be interested in:\n\n");
            for (int i = 0; i < eM.size(); i++){
                sb.append(eM.get(i).formatMovieString());
            }

            sb.append("Sincerely,\n\nA Simple Email Bot");
            message.setText(sb.toString());

            System.out.println("Message is ready.");
            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(this.SMTP_SERVER, this.sender, this.pass);
            t.sendMessage(message, message.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());
            t.close();
        } catch (AddressException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public void checkSubject(){
        System.out.println("List of Movies for the Week of: " + java.time.LocalDate.now());
    }
}
