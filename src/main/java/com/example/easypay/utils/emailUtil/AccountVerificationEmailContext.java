package com.example.easypay.utils.emailUtil;


import com.example.easypay.config.email.EmailConfigurationProperties;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public  class AccountVerificationEmailContext extends AbstractEmailContext {
    private String token;
    private final EmailConfigurationProperties emailConfigurationProperties;



    @Override
    public  void init() {

        setTemplateLocation("email/email-verification.html");
        setSubject("Verify your email and complete your registration");
        setFrom(emailConfigurationProperties.username());
        setFromDisplayName("No-reply@easybuy");
    }

 public void setOTP(String otp){
        put("verificationOTP",otp);
 }


}
