package dk.bankdata.gradle.swagger

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.util.Yaml
import io.swagger.v3.oas.models.OpenAPI

/**
 * Enumeration to define the available Swagger output formats.
 */
enum OutputFormat {
    JSON(new JSONWriter()),
    YAML(new YAMLWriter())

    private final SwaggerWriter writer

    OutputFormat(SwaggerWriter writer) {
        this.writer = writer;
    }

    void write(OpenAPI swagger, File file) throws IOException {
        writer.write(swagger, file)
    }

    /**
     * Interface defining requirements for being able to write out Swagger instance to file.
     */
    @FunctionalInterface
    interface SwaggerWriter {
        void write(OpenAPI swagger, File file) throws IOException;
    }

    /**
     * As the Maven plugin plugin does not support lambdas properly a real implementation is needed.
     */
    static class JSONWriter implements SwaggerWriter {

        @Override
        void write(OpenAPI swagger, File file) throws IOException {
            ObjectMapper mapper = new ObjectMapper()
            mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
            mapper.writeValue(file, swagger)
        }
    }

    /**
     * As the Maven plugin plugin does not support lambdas properly a real implementation is needed.
     */
    static class YAMLWriter implements SwaggerWriter {

        @Override
        void write(OpenAPI swagger, File file) throws IOException {
            Yaml.mapper().writeValue(file, swagger)
        }
    }
}
