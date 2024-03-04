package univcapstone.employmentsite.service;

import jakarta.mail.Message;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import java.util.Random;


@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String sender;

    public int sendMail(String receiver) {

        int authNumber = createNumber();

        MimeMessagePreparator preparator = mimeMessage -> {
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));
            mimeMessage.setFrom(new InternetAddress(sender));
            mimeMessage.setSubject("테스트", "utf-8");
            mimeMessage.setText(Integer.toString(authNumber), "utf-8");
        };

        mailSender.send(preparator);

        return authNumber;
    }

    private static int createNumber() {
        Random random = new Random();
        return random.nextInt(90000) + 10000;
    }
}