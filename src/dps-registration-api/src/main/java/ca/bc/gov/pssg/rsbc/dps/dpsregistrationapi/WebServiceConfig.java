package ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi;

import ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi.health.MetricsService;
import ca.bc.gov.pssg.rsbc.dps.dpsregistrationapi.health.MetricsServiceImpl;
import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.ext.logging.LoggingFeature;
import org.apache.cxf.jaxws.EndpointImpl;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletPath;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.xml.ws.Endpoint;

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
    public SpringBus springBus(LoggingFeature loggingFeature) {
        SpringBus cxfBus = new  SpringBus();

        cxfBus.getFeatures().add(loggingFeature);

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

}
