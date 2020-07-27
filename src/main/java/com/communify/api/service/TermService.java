package com.communify.api.service;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.communify.api.contracts.IMailService;
import com.communify.api.contracts.ITermService;
import com.communify.api.contracts.IUserService;
import com.communify.api.dto.TaskDTO;
import com.communify.api.model.User;

import lombok.Getter;

@Service
@Getter
public class TermService implements ITermService {

    private static final String TEMPLATE_FILE = "term.vm";
    private static final String DEFAULT_SUBJECT_MESSAGE = "Termo de responsabilidade";
    
    @Autowired
    private IUserService userService;
    
    @Autowired
    private IMailService mailService;
    
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void send(String email, String ip) {
        User user = getUserService().findByClassroom(email);
        MimeMessage message = getMailSender().createMimeMessage();
        getMailService().create(user, new TaskDTO(), ip, 
                TEMPLATE_FILE, DEFAULT_SUBJECT_MESSAGE, message);
        getMailSender().send(message);
    }
}
