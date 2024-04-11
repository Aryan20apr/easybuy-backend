package com.example.easypay.services.interfaces;

import com.example.easypay.utils.emailUtil.AbstractEmailContext;
import jakarta.mail.MessagingException;

public interface MailService {

    void sendEmail(final AbstractEmailContext email) throws MessagingException;

}
