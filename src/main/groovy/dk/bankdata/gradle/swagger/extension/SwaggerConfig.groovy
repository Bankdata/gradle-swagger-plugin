package dk.bankdata.gradle.swagger.extension

import io.swagger.models.Info
import io.swagger.models.Scheme
import io.swagger.models.Swagger
import org.gradle.api.Project

import java.nio.file.Files
import java.util.stream.Collectors

/**
 * Configuring Swagger for OpenAPI generation.
 */
class SwaggerConfig {
    private Project project

    /**
     * Comma separated list of supported URI schemes.
     */
    Set<String> schemes

    /**
     * Hostname used to access API.
     */
    String host

    /**
     * Base path to prepend to all API operations.
     */
    String basePath

    /**
     * Providing the OpenAPI information description. This might be overridden by ReaderListener or SwaggerDefinition annotation.
     */
    SwaggerInfo info

    /**
     * Convenience for reading the informational description from file instead of embedding it.
     */
    File descriptionFile

    /**
     * Packages to include in the generated documentation.
     */
    Set<String> resourcePackages = []

    SwaggerConfig(Project project) {
        this.project = project
    }

    SwaggerInfo info(Closure closure) {
        info = project.configure(new SwaggerInfo(project: project), closure)
        info
    }

    Swagger createSwaggerModel() {
        Swagger swagger = new Swagger()

        if (schemes != null && !schemes.isEmpty()) {
            schemes.each { scheme ->
                swagger.scheme(Scheme.forValue(scheme))
            }
        }

        if (host != null)
            swagger.setHost(host)

        if (basePath != null)
            swagger.setBasePath(basePath)

        if (info != null)
            swagger.setInfo(info.createInfoModel())

        if (descriptionFile != null) {
            if (swagger.getInfo() == null) {
                swagger.setInfo(new Info())
            }
            try {
                swagger.getInfo().setDescription(Files.readAllLines(descriptionFile.toPath()).stream().collect(Collectors.joining("\n")))
            } catch (IOException e) {
                throw new RuntimeException("Unable to read descriptor file " + descriptionFile, e)
            }
        }

        return swagger
    }
}
