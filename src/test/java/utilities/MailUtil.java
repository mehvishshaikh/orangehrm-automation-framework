package utilities;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.activation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.File;

public class MailUtil {

    public static void main(String[] args) {
        sendExtentReport();  // This enables GitHub Actions to call this via exec-maven-plugin
    }

    public static void sendExtentReport() {
        final String username = System.getenv("EMAIL_USERNAME");
        final String password = System.getenv("EMAIL_PASSWORD");
        String toEmails = System.getenv("TO_EMAIL");
        String ccEmails = System.getenv("CC_EMAIL");

        String subject = "Automation Test Report";
        String bodyText = "Hi Team,\n\nPlease find attached the latest automation test execution report.\n\nRegards,\nQA Team";

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

            // ✅ Find the most recently modified "ExtentReport_*.html" file
            File reportDir = new File("target/ExtentReports");
            File[] reports = reportDir.listFiles((dir, name) ->
                    name.toLowerCase().endsWith(".html") && name.startsWith("ExtentReport_")
            );

            if (reports == null || reports.length == 0) {
                System.err.println("❌ No suitable report files found in: " + reportDir.getAbsolutePath());
                return;
            }

            Arrays.sort(reports, Comparator.comparingLong(File::lastModified).reversed());
            File latestReport = reports[0];

            MimeBodyPart attachmentPart = new MimeBodyPart();
            DataSource source = new FileDataSource(latestReport);
            attachmentPart.setDataHandler(new DataHandler(source));
            attachmentPart.setFileName(latestReport.getName());

            multipart.addBodyPart(attachmentPart);
            message.setContent(multipart);

            Transport.send(message);
            System.out.println("✅ Email sent with latest report: " + latestReport.getName());

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to send email.");
        }
    }
}
