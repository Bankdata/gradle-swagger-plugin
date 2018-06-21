package dk.bankdata.gradle.swagger

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder

import javax.ws.rs.Path

/**
 * Scan for classes with {@link Path} annotation or {@link OpenAPIDefinition} annotation.
 */
class JaxRSScanner {
    Set<String> resourcePackages = []

    Set<Class<?>> classes() {
        ConfigurationBuilder config = ConfigurationBuilder.build(resourcePackages)
            .setScanners(new ResourcesScanner(), new TypeAnnotationsScanner(), new SubTypesScanner())
        Reflections reflections = new Reflections(config)
        Set<Class<?>> apiClasses = reflections.getTypesAnnotatedWith(Path.class)
                .findAll { cls -> resourcePackages.isEmpty() || resourcePackages.contains(cls.getPackage().getName()) }
        Set<Class<?>> defClasses = reflections.getTypesAnnotatedWith(OpenAPIDefinition.class)
                .findAll { cls -> resourcePackages.isEmpty() || resourcePackages.contains(cls.getPackage().getName()) }
        apiClasses.addAll(defClasses)
        return apiClasses
    }

}