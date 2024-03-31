package univcapstone.employmentsite.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import univcapstone.employmentsite.domain.Otp;
import univcapstone.employmentsite.domain.User;
import univcapstone.employmentsite.repository.OtpRepository;
import univcapstone.employmentsite.repository.UserRepository;

import java.security.SecureRandom;
import java.util.Date;

@Slf4j
@Transactional
@Service
@RequiredArgsConstructor
public class OtpService {

    private final OtpRepository otpRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final String chars =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "0123456789" +
            "~`!@#$%^&*()-_=+\\|[{]};:'\",<.>/?";

    //임시 비밀번호를 사용자의 비밀번호로 변경하는 방식
    public String saveOtp(Long userId, int length) {
        String strOtp = generateOtp(length);

        String encodedOtp = passwordEncoder.encode(strOtp);

        Otp otp = Otp.builder()
                .id(userId)
                .otp(encodedOtp)
                .build();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계정입니다."));

        user.updatePassword(encodedOtp);

        otpRepository.save(otp);

        return strOtp;
    }

    private String generateOtp(int length) {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(new Date().getTime());
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < length; i++) {
            int randomInt = secureRandom.nextInt(chars.length());
            sb.append(chars.charAt(randomInt));
        }

        return sb.toString();
    }

}
