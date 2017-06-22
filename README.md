# Overview

This Gradle plugin is created to generate OpenAPI documentation from a JAX-RS based project using
[Swagger](https://github.com/swagger-api/swagger-core).


# Usage

Applying the plugin to your project the generation will run Swagger to generate OpenAPI documentation
after classes are compiled. Applying this plugin will also imply applying the Java plugin. A minimal
configuration is as follows.

```groovy
buildscript {
    dependencies {
        classpath (group: 'dk.bankdata.gradle.swagger', name: 'gradle-swagger-plugin', version: '1.0.0')
    }
}

apply 'dk.bankdata.swagger'

swagger {
    resourcePackages = ['dk.bankdata.service.example']
}
```


# Acknowledgements

The Gradle Swagger Plugin is inspired by the [Swagger Maven Plugin](https://github.com/kongchen/swagger-maven-plugin)
and also the [Swagger Gradle Plugin](https://github.com/gigaSproule/swagger-gradle-plugin). However this plugin
was created with the specific purpose of using as much of the core Swagger modules as possible.