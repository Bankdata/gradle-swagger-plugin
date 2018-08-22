package dk.bankdata.gradle.swagger.extension

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.gradle.api.Project

import java.nio.file.Files
import java.util.stream.Collectors

/**
 * Configuring Swagger for OpenAPI generation.
 */
class SwaggerConfig {
    Project project

    /**
     * Servers used to access API.
     */
    List<Closure> servers

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

    OpenAPI createSwaggerModel() {
        OpenAPI oas = new OpenAPI()

        if (!servers?.isEmpty()) {
            oas.servers(servers.collect { Closure it ->
                def server = project.configure(new SwaggerServer(), it)
                server.createServerModel()
            })
        }

        if (info != null)
            oas.setInfo(info.createInfoModel())

        if (descriptionFile != null) {
            if (oas.getInfo() == null) {
                oas.setInfo(new Info())
            }
            try {
                oas.getInfo().setDescription(Files.readAllLines(descriptionFile.toPath()).stream().collect(Collectors.joining("\n")))
            } catch (IOException e) {
                throw new RuntimeException("Unable to read descriptor file " + descriptionFile, e)
            }
        }

        return oas
    }
}
