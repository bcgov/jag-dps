package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi.health.MetricsService;
import ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi.health.MetricsServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.actuate.health.HealthEndpoint;
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

    /**
     * Exposes the SOAP api
     * @param bus
     * @param registrationServiceEndpoint
     * @return
     */
    @Bean
    public Endpoint endpoint(Bus bus, RegistrationServiceEndpoint registrationServiceEndpoint) {
        EndpointImpl endpoint = new EndpointImpl(bus, registrationServiceEndpoint);
        endpoint.publish("/DPS_RegistrationServices.wsProvider:dpsDocumentStatusRegWS");
        return endpoint;
    }

    @Bean
    public MetricsService metricsService(HealthEndpoint healthEndpoint) {
        return new MetricsServiceImpl(healthEndpoint);
    }

//    @Bean
//    public Server rsServer(Bus bus, MetricsService metricsService) {
//        JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();
//        endpoint.setBus(bus);
//        endpoint.setAddress("/api");
//        endpoint.setProvider(new JacksonJsonProvider());
//        endpoint.setServiceBeans(Arrays.<Object>asList(metricsService));
//        return endpoint.create();
//    }

}
