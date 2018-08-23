# Overview

[![Build Status](https://travis-ci.org/Bankdata/gradle-swagger-plugin.svg?branch=master)](https://travis-ci.org/Bankdata/gradle-swagger-plugin)

This Gradle plugin is created to generate OpenAPI documentation from a JAX-RS based project using
[Swagger](https://github.com/swagger-api/swagger-core).


# Usage

Applying the plugin to your project the generation will run Swagger to generate OpenAPI documentation
after classes are compiled. Applying this plugin will also imply applying the Java plugin. A minimal
configuration is as follows.

```groovy
buildscript {
    dependencies {
        classpath (group: 'dk.bankdata.gradle.swagger', name: 'gradle-swagger-plugin', version: '2.0.0')
    }
}

apply plugin: 'dk.bankdata.swagger'

swagger {
    resourcePackages = ['dk.bankdata.service.example']
}
```

It is possible to configure most of the general properties of the OpenAPI document with
the configuration in the build file. This implies using variable from the build file
if applicable.

```groovy
buildscript {
    dependencies {
        classpath (group: 'dk.bankdata.gradle.swagger', name: 'gradle-swagger-plugin', version: '2.0.0')
    }
}

apply plugin: 'dk.bankdata.swagger'

swagger {
    resourcePackages = ['dk.bankdata.gradle.swagger.example']
    servers = [
            {
                url = "https://api.bankdata.dk"
                description = "production"
            }
    ]
    info {
        title = 'Swagger Plugin Full'
        version = '1.0.0'
        description = 'This service does...'
        termsOfService = 'Terms'
        contact {
            name = 'Bankdata'
            url = 'https://www.bankdata.dk'
            email = 'bankdata@e.mail'
        }
        license {
            name = 'MIT'
            url = 'http://mit'
        }
    }
}
```



# Acknowledgements

The Gradle Swagger Plugin is inspired by the [Swagger Maven Plugin](https://github.com/openapi-tools/swagger-maven-plugin)
and also the [Swagger Gradle Plugin](https://github.com/gigaSproule/swagger-gradle-plugin). However this plugin
was created with the specific purpose of using as much of the core Swagger modules as possible.