package kr.mjc.minsoo.web;

import jakarta.servlet.descriptor.JspPropertyGroupDescriptor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.descriptor.web.JspConfigDescriptorImpl;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroup;
import org.apache.tomcat.util.descriptor.web.JspPropertyGroupDescriptorImpl;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;

/**
 * jsp의 encoding을 UTF-8으로 설정하고 welcome file 등록
 */
@Component
@Slf4j
public class TomcatServerCustomizer
        implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {
  @Override
  public void customize(TomcatServletWebServerFactory factory) {
    factory.getTomcatContextCustomizers().add(context -> {
      // jspPropertyGroup
      JspPropertyGroup properties = new JspPropertyGroup();
      properties.addUrlPattern("*.jsp");
      properties.setPageEncoding("UTF-8");

      // jspPropertyGroupDescriptors
      Collection<JspPropertyGroupDescriptor> descriptors = new HashSet<>();
      descriptors.add(new JspPropertyGroupDescriptorImpl(properties));

      // jspConfigDescriptor
      context.setJspConfigDescriptor(
              new JspConfigDescriptorImpl(descriptors, new HashSet<>()));

      context.addWelcomeFile("index.html");
    });
    log.info("Tomcat customized");
  }
}