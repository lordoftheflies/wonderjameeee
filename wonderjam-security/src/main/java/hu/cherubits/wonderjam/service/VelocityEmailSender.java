/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.cherubits.wonderjam.service;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author lordoftheflies
 */
@Component
public class VelocityEmailSender implements UaaMailSender {

    private static final Logger LOG = Logger.getLogger(VelocityEmailSender.class.getName());

    private final VelocityEngine velocityEngine;
    private final JavaMailSender mailSender;
    private final SimpleMailMessage simpleMailMessage;

    @Value("${mail.logo}")
    private String logo;
    
    
    /**
     * Constructor
     *
     * @param velocityEngine
     * @param mailSender
     * @param simpleMailMessage
     */
    @Autowired
    public VelocityEmailSender(VelocityEngine velocityEngine, JavaMailSender mailSender, SimpleMailMessage simpleMailMessage) {
        this.velocityEngine = velocityEngine;
        this.mailSender = mailSender;
        this.simpleMailMessage = simpleMailMessage;
    }

    /**
     * Sends e-mail using Velocity template for the body and the properties
     * passed in as Velocity variables.
     *
     * @param msg The e-mail message to be sent, except for the body.
     * @param hTemplateVariables Variables to use when processing the template.
     */
    protected void send(SimpleMailMessage msg, String language, String template, Map<String, Object> hTemplateVariables) {

        LOG.info("Send email ...");
        MimeMessagePreparator preparator = (MimeMessage mimeMessage) -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setTo(msg.getTo());
            message.setFrom(msg.getFrom());
            message.setSubject(msg.getSubject());

            String body = VelocityEngineUtils.mergeTemplateIntoString(
                    velocityEngine, 
                    "/" + template + "." + language + ".vm", 
                    "UTF-8", 
                    hTemplateVariables);

            LOG.log(Level.INFO, "Body: {0}", body);

            message.setText(body, true);
        };

        mailSender.send(preparator);

        LOG.log(Level.INFO, "Sender {0}", msg.getFrom());
        LOG.log(Level.INFO, "Recipient {0}", msg.getTo());
    }

    @Override
    public void sendForgottenPasswordEmail(String email, String userName, String applicationName, String organizationName, String passwordResetLink, String locale) {
        Map<String, Object> props = new HashMap<>();
        props.put("userName", userName);
        props.put("applicationName", applicationName);
        props.put("passwordResetLink", passwordResetLink);
        props.put("organizationName", organizationName);
        props.put("applicationLogo", logo);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Password reset from " + applicationName);
        this.send(simpleMailMessage, locale, "forgotten-password", props);
    }

    @Override
    public void sendRegistrationActivationEmail(String inviter, String email, String userName, String applicationName, String organizationName, String webRegistrationActivationLink, String androidInstallerLink, String locale) {
        Map<String, Object> props = new HashMap<>();
        props.put("userName", userName);
        props.put("applicationName", applicationName);
        props.put("inviter", inviter);
        props.put("applicationLogo", logo);
        props.put("webFrontendLink", webRegistrationActivationLink);
        props.put("androidInstallerLink", androidInstallerLink);
        props.put("organizationName", organizationName);
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Activate your " + applicationName + " account");
        this.send(simpleMailMessage, locale, "registration-activation", props);
    }

}
