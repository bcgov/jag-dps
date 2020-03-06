package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 *
 * WebServiceConfig configures the output notification service
 *
 * This code is based on https://spring.io/guides/gs/producing-web-service/
 *
 * @author alexjobc@github
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean(name = "dpsOutputNotification")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema outputNotificationSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName(Keys.OUTPUT_NOTIFICATION_PORT);
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace(Keys.NAMESPACE_URI);
        wsdl11Definition.setSchema(outputNotificationSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema outputNotificationSchema() {
        return new SimpleXsdSchema(new ClassPathResource(Keys.OUTPUT_NOTIFICATION_XSD));
    }

}
