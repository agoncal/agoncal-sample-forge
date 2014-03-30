package org.agoncal.sample.forge.template;

import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateProcessor;
import org.jboss.forge.addon.templates.TemplateProcessorFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 * @Inject private TemplateProcessorFactory factory;
 * ...
 * FileResource<?> resource = ...; // A file resource containing "Hello ${name}"
 * Template template = new FreemarkerTemplate(resource); // Mark this resource as a Freemarker template
 * TemplateProcessor processor = factory.fromTemplate(template);
 * Map<String,Object> params = new HashMap<String,Object>(); //Could be a POJO also.
 * params.put("name", "JBoss Forge");
 * String output = processor.process(params); // should return "Hello JBoss Forge".
 */
public class RestEndpoint {

    @Inject
    private TemplateProcessorFactory factory;

    @Inject
    ResourceFactory resourceFactory;


    public static void main(String[] args) throws IOException {
        new RestEndpoint().doIt();
    }

    private void doIt() throws IOException {
        Resource<URL> templateResource = resourceFactory.create(getClass().getResource("EndpointWithDTO.jv"));
        Template template = new FreemarkerTemplate(templateResource); // Mark this resource as a Freemarker template
        TemplateProcessor processor = factory.fromTemplate(template);
        Map<String,Object> params = new HashMap<String,Object>(); //Could be a POJO also.
        params.put("name", "JBoss Forge");
        String output = processor.process(params); // should return "Hello JBoss Forge".
        System.out.println(output);
    }
}
