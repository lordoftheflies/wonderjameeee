package hu.cherubits.wonderjam;

import hu.cherubits.wonderjam.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

//@SpringBootApplication
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class FcmConfiguration {

    @Value("${fcm.serverkey}")
    private String fcmServerKey;

    @Value("${fcm.senderid}")
    private String fcmSenderId;

    @Value("${fcm.server}")
    private String cloudMessagingServer;

    @Value("${fcm.credentials}")
    private String fileName;
    
    @Bean
    @Autowired
    public NotificationService notificationService() {
        return new NotificationService(cloudMessagingServer, fileName, fcmServerKey, fcmSenderId);
    }

//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(FcmConfiguration.class);
//    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(FcmConfiguration.class, args);
//    }
}
