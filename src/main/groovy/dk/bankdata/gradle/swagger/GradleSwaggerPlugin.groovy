package dk.bankdata.gradle.swagger

import dk.bankdata.gradle.swagger.extension.SwaggerConfig;
import org.gradle.api.Plugin;
import org.gradle.api.Project
import org.gradle.api.plugins.BasePlugin
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.plugins.JavaPluginConvention
import org.gradle.api.tasks.SourceSet;

/**
 * Plugin to handle generation of OpenAPI specification using Swagger.
 */
class GradleSwaggerPlugin implements Plugin<Project> {

    static SWAGGER_TASK_NAME = 'swaggerGenerate'

    @Override
    void apply(Project project) {
        project.pluginManager.apply(JavaPlugin)
        project.extensions.create('swagger', SwaggerConfig, project)


        def task = project.task(SWAGGER_TASK_NAME, type: GenerateSwaggerTask)
        task.dependsOn(project.convention.getPlugin(JavaPluginConvention).sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME).runtimeClasspath)
        task.outputDirectory = project.file("${project.buildDir}/swagger")
        task.group = BasePlugin.BUILD_GROUP
        task.description = 'Generates OpenAPI documentation using Swagger.'
        project.tasks.getByName(BasePlugin.ASSEMBLE_TASK_NAME).dependsOn(task)
    }
}
