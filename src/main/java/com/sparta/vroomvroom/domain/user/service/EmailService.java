package com.sparta.vroomvroom.domain.user.service;

import com.sparta.vroomvroom.domain.user.model.entity.EmailVerification;
import com.sparta.vroomvroom.domain.user.repository.EmailVerificationRepository;
import com.sparta.vroomvroom.global.conmon.constants.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final EmailVerificationRepository emailVerificationRepository;
    private final JavaMailSender mailSender;

    private static final String VERIFY_URL = "http://localhost:8080/api/v2/email/verify?email=%s&code=%s";
    private static final int EXPIRATION_MINUTES = 10;

    @Value("${spring.mail.username}")
    private String senderEmail;

    //인증 코드 전송
    @Transactional
    public void sendVerificationMail(String email) {
        //기존에 인증 요청 있다면 전부 삭제
        emailVerificationRepository.deleteAllByEmail(email);

        String code = UUID.randomUUID().toString().substring(0, 8);
        String verifyLink = String.format(VERIFY_URL, email, code);

        EmailVerification verification = new EmailVerification(email,code, LocalDateTime.now().plusMinutes(EXPIRATION_MINUTES));
        emailVerificationRepository.save(verification);

        //전송 본문 완성
        String subject = EmailTemplate.EMAIL_VERIFY_REQUEST.getSubject();
        String content = EmailTemplate.EMAIL_VERIFY_REQUEST.buildContent(verifyLink,EXPIRATION_MINUTES);

        // 메일 발송
        sendHtmlMail(email, subject, content);
    }

    //이메일 인증 여부 체크
    public void isVerified(String email) {
        EmailVerification verification = emailVerificationRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("이메일 인증이 필요합니다."));

        if (!verification.isVerified()) {
            throw new IllegalArgumentException("이메일 인증이 완료되지 않았습니다.");
        }

        if (verification.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("이메일 인증 코드가 만료되었습니다.");
        }
    }
    public void deleteEmailVerification(String email) {
        emailVerificationRepository.deleteAllByEmail(email);
    }

    //이메일 검증
    @Transactional
    public void verifyEmail(String email, String code) {
        EmailVerification verification = emailVerificationRepository.findByEmailAndCode(email, code)
                .orElseThrow(() -> new IllegalArgumentException("인증 요청이 존재하지 않습니다."));

        if (verification.isExpired()) {
            throw new IllegalArgumentException("인증 코드가 만료되었습니다.");
        }

        verification.verify();
    }

    //html 메일 전송
    private void sendHtmlMail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setFrom(senderEmail);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("메일 발송 중 오류가 발생했습니다.");
        }
    }
}
