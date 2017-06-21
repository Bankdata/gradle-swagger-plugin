package dk.bankdata.gradle.swagger

import dk.bankdata.gradle.swagger.extension.SwaggerConfig;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

/**
 * Plugin to handle generation of OpenAPI specification using Swagger.
 */
class GradleSwaggerPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('swagger', SwaggerConfig, project)
        project.task('swaggerGenerate', type: GenerateSwaggerTask, dependsOn: 'classes')
    }
}
