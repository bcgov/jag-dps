package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.feature.StaxTransformFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import jakarta.xml.ws.Endpoint;
import java.text.MessageFormat;
import java.util.Collections;

/**
 *
 * WebServiceConfig configures the output notification service
 *
 * This code is based on https://spring.io/guides/gs/producing-web-service/
 *
 * @author alexjobc@github
 */

@Configuration
@EnableConfigurationProperties(WebServiceProperties.class)
public class WebServiceConfig {


    private final WebServiceProperties webServiceProperties;

    public WebServiceConfig(WebServiceProperties webServiceProperties) {
        this.webServiceProperties = webServiceProperties;
    }

    @Bean
    public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
        return new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/ws/*");
    }

    @Bean
    @Primary
    public DispatcherServletPath dispatcherServletPathProvider() {
        return () -> "";
    }

    @Bean(name= Bus.DEFAULT_BUS_ID)
    public SpringBus springBus(LoggingFeature loggingFeature, @Qualifier("rewriteNamespaceTransformFeature")StaxTransformFeature rewriteNamespaceTransformFeature) {
        SpringBus cxfBus = new  SpringBus();

        cxfBus.getFeatures().add(loggingFeature);

        if(StringUtils.isNotBlank(this.webServiceProperties.getCustomNamespace())) {
            cxfBus.getFeatures().add(rewriteNamespaceTransformFeature);
        }

        return cxfBus;
    }

    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();

        if(webServiceProperties.isLoggingEnabled()) {
            loggingFeature.setPrettyLogging(true);
        } else {
            loggingFeature.setLimit(0);
        }

        return loggingFeature;
    }

    @Bean
    public StaxTransformFeature rewriteNamespaceTransformFeature() {

        StaxTransformFeature staxTransformFeature = new StaxTransformFeature();
        staxTransformFeature.setInTransformElements(
                Collections.singletonMap(
                        MessageFormat.format("'{'{0}'}'*", this.webServiceProperties.getCustomNamespace()),
                "{http://reeks.bcgov/DPS_Extensions.common.wsProvider:outputNotificationWS}*")
        );

        return staxTransformFeature;

    }

    @Bean
    public Endpoint endpoint(Bus bus, OutputNotificationEndpoint outputNotificationEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, outputNotificationEndpoint);
        endpoint.publish("/DPS_Extensions.common.wsProvider.outputNotificationWS/DPS_Extensions_common_wsProvider_outputNotificationWS_Port");
        return endpoint;
    }

}
