package org.agoncal.sample.forge.template;

import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.addon.templates.Template;
import org.jboss.forge.addon.templates.TemplateProcessor;
import org.jboss.forge.addon.templates.TemplateProcessorFactory;
import org.jboss.forge.addon.templates.freemarker.FreemarkerTemplate;
import org.jboss.forge.furnace.Furnace;
import org.jboss.forge.furnace.repositories.AddonRepositoryMode;
import org.jboss.forge.furnace.se.FurnaceFactory;

import java.io.File;
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

    public static void main(String[] args) throws IOException {
        new RestEndpoint().doIt();
    }

    private void doIt() throws IOException {
        Furnace furnace = startFurnace();
        try {

            ResourceFactory resourceFactory = furnace.getAddonRegistry().getServices(ResourceFactory.class).get();
            TemplateProcessorFactory factory = furnace.getAddonRegistry().getServices(TemplateProcessorFactory.class).get();

            Resource<URL> templateResource = resourceFactory.create(getClass().getResource("RestEndpoint.jv"));
            Template template = new FreemarkerTemplate(templateResource); // Mark this resource as a Freemarker template
            TemplateProcessor processor = factory.fromTemplate(template);
            Map<String, Object> params = new HashMap<String, Object>(); //Could be a POJO also.
            params.put("name", "JBoss Forge");
            String output = processor.process(params); // should return "Hello JBoss Forge".
            System.out.println(output);

        } finally {
            furnace.stop();
        }

    }

    static Furnace startFurnace() {
        // Create a Furnace instance. NOTE: This must be called only once
        Furnace furnace = FurnaceFactory.getInstance();

        // Add repository containing addons specified in pom.xml
        furnace.addRepository(AddonRepositoryMode.IMMUTABLE, new File("target/addons"));

        // Start Furnace in another thread
        furnace.startAsync();

        // Wait until Furnace is started
        while (!furnace.getStatus().isStarted()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
        return furnace;
    }
}
