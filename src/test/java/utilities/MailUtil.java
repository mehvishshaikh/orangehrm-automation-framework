package utilities;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;

public class MailUtil {
    public static void sendExtentReport() {
        final String username = "mehvish.ms67@gmail.com"; // your Gmail
        final String password = "doii fwuj evbe unct";    // Gmail app password

        String toEmails = "vangalasairohith@gmail.com";
        String ccEmails = "mehvishshaikh1112@gmail.com";
        String subject = "Automation Test Report";
        String bodyText = "Hi Team,\n\nPlease find attached the latest automation test execution report.\n\nRegards,\nMehvish Shaikh";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));

            for (String email : toEmails.split(",")) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(email.trim()));
            }
            for (String email : ccEmails.split(",")) {
                message.addRecipient(Message.RecipientType.CC, new InternetAddress(email.trim()));
            }

            message.setSubject(subject);

            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(bodyText);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // Attach the HTML report
            MimeBodyPart attachmentPart = new MimeBodyPart();
            //String reportPath = "target/ExtentReports/extent-report.html"; // adjust if filename differs
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String reportPath = "target/ExtentReports/ExtentReport_" + timestamp + ".html";
            File reportFile = new File(reportPath);

            if (reportFile.exists()) {
                DataSource source = new FileDataSource(reportFile);
                attachmentPart.setDataHandler(new DataHandler(source));
                attachmentPart.setFileName(reportFile.getName());
                multipart.addBodyPart(attachmentPart);
                message.setContent(multipart);
                Transport.send(message);
                System.out.println("Email sent with report!");
            } else {
                System.err.println("Report file not found at: " + reportPath);
            }

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
