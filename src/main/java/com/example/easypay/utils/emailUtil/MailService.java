package com.example.easypay.utils.emailUtil;

import jakarta.mail.MessagingException;

public interface MailService {

    void sendEmail(final AbstractEmailContext email) throws MessagingException;

}
