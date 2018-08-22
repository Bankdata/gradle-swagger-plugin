package dk.bankdata.gradle.swagger

import dk.bankdata.gradle.swagger.extension.SwaggerConfig
import io.swagger.v3.jaxrs2.Reader
import io.swagger.v3.oas.models.OpenAPI
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

/**
 * Task to generate the OpenAPI documentation utilizing Swagger.
 */
class GenerateSwaggerTask extends DefaultTask {

    def outputDirectory

    @Input
    def outputFormats = [OutputFormat.YAML]

    @Input
    def attachSwaggerArtifact = true

    @OutputDirectory
    File getOutputDirectory() {
        outputDirectory
    }

    void setOutputDirectory(File outputDirectory) {
        this.outputDirectory = outputDirectory
    }

    @TaskAction
    void generate() {
        def originalClassloader = Thread.currentThread().getContextClassLoader()
        try {
            def urls = project.sourceSets.main.runtimeClasspath
                    .findAll { it.exists() }
                    .collect { it.toURI().toURL() } as URL[]
            Thread.currentThread().setContextClassLoader(new URLClassLoader(urls, originalClassloader))

            JaxRSScanner reflectiveScanner = new JaxRSScanner()
            SwaggerConfig swaggerConfig = project.swagger
            if (swaggerConfig.resourcePackages != null && !swaggerConfig.resourcePackages.isEmpty()) {
                reflectiveScanner.resourcePackages = swaggerConfig.resourcePackages
            }

            Reader reader = new Reader(swaggerConfig?.createSwaggerModel())
            OpenAPI swagger = reader.read(reflectiveScanner.classes())

            if (outputDirectory.mkdirs()) {
                project.logger.debug("Created output directory ${outputDirectory}")
            }

            outputFormats.each { format ->
                try {
                    File outputFile = new File(outputDirectory, "openapi." + format.name().toLowerCase())
                    format.write(swagger, outputFile)
                    if (attachSwaggerArtifact) {
                        project.artifacts  {
                            archives file: outputFile, classifier: 'openapi', type: format.name().toLowerCase()
                        }
                    }
                } catch (IOException e) {
                    throw new GradleException("Unable write OpenAPI document", e)
                }
            }
        } finally {
            Thread.currentThread().setContextClassLoader(originalClassloader)
        }
    }

}
