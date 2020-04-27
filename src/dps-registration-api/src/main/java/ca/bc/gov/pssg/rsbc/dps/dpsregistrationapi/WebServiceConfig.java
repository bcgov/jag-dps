package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.xml.ws.Endpoint;

@Configuration
public class WebServiceConfig {

    @Bean
    public ServletRegistrationBean<CXFServlet> dispatcherServlet() {
        return new ServletRegistrationBean<CXFServlet>(new CXFServlet(), "/ws/DPS_RegistrationServices.wsProvider.dpsDocumentStatusRegWS/*");
    }
    @Bean
    @Primary
    public DispatcherServletPath dispatcherServletPathProvider() {
        return () -> "";
    }
    @Bean(name= Bus.DEFAULT_BUS_ID)
    public SpringBus springBus(LoggingFeature loggingFeature) {
        SpringBus cxfBus = new  SpringBus();
        cxfBus.getFeatures().add(loggingFeature);
        return cxfBus;
    }
    @Bean
    public LoggingFeature loggingFeature() {
        LoggingFeature loggingFeature = new LoggingFeature();
        return loggingFeature;
    }
    
    @Bean
    public Endpoint endpoint(Bus bus, RegistrationServiceEndpoint registrationServiceEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, registrationServiceEndpoint);
        endpoint.publish("/DPS_RegistrationServices_wsProvider_dpsDocumentStatusRegWS_Port");
        return endpoint;
    }


}
