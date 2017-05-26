import java.io.File;
import java.security.Security;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class GoogleTest {

private static final String SMTP_HOST_NAME = "smtp.gmail.com";
private static final String SMTP_PORT = "465";
private static final String emailMsgTxt = "Test Message Contents";
private static final String emailSubjectTxt = "A test from gmail";
private static final String emailFromAddress = "somesh.ballia@gmail.com";
private static final String SSL_FACTORY =
"javax.net.ssl.SSLSocketFactory";
private static final String[] sendTo = {"somesh.ballia@gmail.com","somesh_ballia@yahoo.com"};

private static final String fileAttachment="C:\\hai.txt";

public static void main(String args[]) throws Exception {

Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());

new GoogleTest().sendSSLMessage(sendTo, emailSubjectTxt,emailMsgTxt, emailFromAddress);
System.out.println("Sucessfully Sent mail to All Users");

}

public void sendSSLMessage(String recipients[], String subject,String message, String from) throws MessagingException 

{
boolean debug = true;

Properties props = new Properties();
props.put("mail.smtp.host", SMTP_HOST_NAME);
props.put("mail.smtp.auth", "true");
props.put("mail.debug", "true");
props.put("mail.smtp.port", SMTP_PORT);
props.put("mail.smtp.socketFactory.port", SMTP_PORT);
props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
props.put("mail.smtp.socketFactory.fallback", "false");

Session session = Session.getDefaultInstance(props,
new javax.mail.Authenticator() {

protected PasswordAuthentication getPasswordAuthentication() {
return new PasswordAuthentication("somesh.ballia@gmail.com", "give password ofgmail");
}
});



MimeMessage message1 =new MimeMessage(session);
message1.setFrom(new InternetAddress(from));
message1.addRecipient(Message.RecipientType.TO,new InternetAddress(recipients[0]));

message1.addRecipient(Message.RecipientType.TO,new InternetAddress(recipients[1]));

message1.setSubject("Hello JavaMail Attachment");

// create the message part
MimeBodyPart messageBodyPart =new MimeBodyPart();

//fill message
messageBodyPart.setText("Hi");

Multipart multipart = new MimeMultipart();
multipart.addBodyPart(messageBodyPart);

// Part two is attachment
messageBodyPart = new MimeBodyPart();
DataSource source =new FileDataSource(fileAttachment);
messageBodyPart.setDataHandler(new DataHandler(source));
messageBodyPart.setFileName(fileAttachment);
multipart.addBodyPart(messageBodyPart);

// Put parts in message
message1.setContent(multipart);

// Send the message
Transport.send( message1 );
}
} 