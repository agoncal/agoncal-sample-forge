package org.agoncal.sample.forge.furnace;

import org.jboss.forge.addon.parser.java.projects.JavaWebProjectType;
import org.jboss.forge.addon.projects.Project;
import org.jboss.forge.addon.projects.ProjectFactory;
import org.jboss.forge.addon.projects.ProjectProvider;
import org.jboss.forge.addon.projects.facets.MetadataFacet;
import org.jboss.forge.addon.resource.Resource;
import org.jboss.forge.addon.resource.ResourceFactory;
import org.jboss.forge.furnace.Furnace;
import org.jboss.forge.furnace.addons.AddonRegistry;
import org.jboss.forge.furnace.repositories.AddonRepositoryMode;
import org.jboss.forge.furnace.se.FurnaceFactory;
import org.jboss.forge.furnace.util.OperatingSystemUtils;

import java.io.File;

/**
 * @author Antonio Goncalves
 *         http://www.antoniogoncalves.org
 *         --
 */
public class CreateAProject {

    public static void main(String[] args) throws Exception
    {
        Furnace furnace = startFurnace();
        try
        {
            AddonRegistry addonRegistry = furnace.getAddonRegistry();
            ProjectFactory projectFactory = addonRegistry.getServices(ProjectFactory.class).get();
            ResourceFactory resourceFactory = addonRegistry.getServices(ResourceFactory.class).get();

            // Create a temporary directory as an example
            File underlyingResource = OperatingSystemUtils.getTempDirectory();

            Resource<File> projectDir = resourceFactory.create(underlyingResource);

            // This could return more than one provider, but since the maven addon is the only one deployed, this is ok
            ProjectProvider projectProvider = addonRegistry.getServices(ProjectProvider.class).get();

            // Creating WAR project
            JavaWebProjectType javaWebProjectType = addonRegistry.getServices(JavaWebProjectType.class).get();
            Project project = projectFactory.createProject(projectDir, projectProvider,
                    javaWebProjectType.getRequiredFacets());

            // Changing metadata
            MetadataFacet facet = project.getFacet(MetadataFacet.class);
            facet.setProjectName("my-demo-project");
            facet.setProjectVersion("1.0.0-SNAPSHOT");
            facet.setTopLevelPackage("com.mycompany.project");

            System.out.println("### Project Created in: " + project);
        }
        finally
        {
            furnace.stop();
        }
    }

    static Furnace startFurnace()
    {
        // Create a Furnace instance. NOTE: This must be called only once
        Furnace furnace = FurnaceFactory.getInstance();

        // Add repository containing addons specified in pom.xml
        furnace.addRepository(AddonRepositoryMode.IMMUTABLE, new File("target/addons"));

        // Start Furnace in another thread
        furnace.startAsync();

        // Wait until Furnace is started
        while (!furnace.getStatus().isStarted())
        {
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException e)
            {
                break;
            }
        }
        return furnace;
    }
}
