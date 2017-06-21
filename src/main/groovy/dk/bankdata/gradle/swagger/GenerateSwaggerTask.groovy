package dk.bankdata.gradle.swagger

import dk.bankdata.gradle.swagger.extension.SwaggerConfig
import io.swagger.jaxrs.Reader
import io.swagger.models.Swagger
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction

/**
 * Task to generate the OpenAPI documentation utilizing Swagger.
 */
class GenerateSwaggerTask extends DefaultTask {

    @Input
    def outputDirectory = "${project.buildDir}"

    @Input
    def outputFormats = [OutputFormat.YAML]

    @Input
    def attachSwaggerArtifact = true

    @TaskAction
    void generate() {
        SwaggerConfig swaggerConfig = project.swagger
        Reader reader = new Reader(swaggerConfig == null ? new Swagger() : swaggerConfig.createSwaggerModel())

        JaxRSScanner reflectiveScanner = new JaxRSScanner()
        if (swaggerConfig.resourcePackages != null && !swaggerConfig.resourcePackages.isEmpty()) {
            reflectiveScanner.resourcePackages = swaggerConfig.resourcePackages
        }

        Swagger swagger = reader.read(reflectiveScanner.classes())

        if (project.file(outputDirectory).mkdirs()) {
            project.logger.debug("Created output directory " + outputDirectory)
        }

        outputFormats.each { format ->
            try {
                File outputFile = new File(outputDirectory, "swagger." + format.name().toLowerCase())
                format.write(swagger, outputFile)
                if (attachSwaggerArtifact && project.configurations.findByName('archives')) {
                    project.artifacts  {
                        archives file: outputFile, classifier: 'swagger', type: format.name().toLowerCase()
                    }
                }
            } catch (IOException e) {
                throw new GradleException("Unable write Swagger document", e)
            }
        }
    }

}
