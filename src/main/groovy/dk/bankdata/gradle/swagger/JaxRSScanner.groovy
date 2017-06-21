package dk.bankdata.gradle.swagger

import io.swagger.annotations.Api
import io.swagger.annotations.SwaggerDefinition
import io.swagger.config.Scanner
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ConfigurationBuilder

/**
 * Specialization to also handle classed annotated with {@link io.swagger.annotations.SwaggerDefinition}.
 *
 * @see io.swagger.jaxrs.config.BeanConfig#classes()
 * @see io.swagger.jaxrs.config.ReflectiveJaxrsScanner
 */
class JaxRSScanner implements Scanner {
    boolean prettyPrint
    Set<String> resourcePackages = []

    @Override
    Set<Class<?>> classes() {
        ConfigurationBuilder config = ConfigurationBuilder.build(resourcePackages)
            .setScanners(new ResourcesScanner(), new TypeAnnotationsScanner(), new SubTypesScanner())
        Reflections reflections = new Reflections(config)
        Set<Class<?>> apiClasses = reflections.getTypesAnnotatedWith(Api.class)
                .findAll { cls -> resourcePackages.isEmpty() || resourcePackages.contains(cls.getPackage().getName()) }
        Set<Class<?>> defClasses = reflections.getTypesAnnotatedWith(SwaggerDefinition.class)
                .findAll { cls -> resourcePackages.isEmpty() || resourcePackages.contains(cls.getPackage().getName()) }
        apiClasses.addAll(defClasses)
        return apiClasses
    }

}