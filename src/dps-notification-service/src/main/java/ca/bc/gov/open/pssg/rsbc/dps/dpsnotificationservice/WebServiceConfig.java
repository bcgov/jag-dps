package ca.bc.gov.open.pssg.rsbc.dps.dpsnotificationservice;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.xml.ws.Endpoint;

/**
 *
 * WebServiceConfig configures the output notification service
 *
 * This code is based on https://spring.io/guides/gs/producing-web-service/
 *
 * @author alexjobc@github
 */

@Configuration
public class WebServiceConfig {

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
    public SpringBus springBus() {
        SpringBus cxfBus = new  SpringBus();
        return cxfBus;
    }


    @Bean
    public Endpoint endpoint(Bus bus, OutputNotificationEndpoint outputNotificationEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, outputNotificationEndpoint);
        endpoint.publish("/DPS_Extensions.common.wsProvider.outputNotificationWS/DPS_Extensions_common_wsProvider_outputNotificationWS_Port");
        return endpoint;
    }

}
