package dk.bankdata.gradle.swagger.extension

import io.swagger.models.License

/**
 * License information to go into the generated Swagger.
 */
class SwaggerLicense {
    String name

    String url

    License createLicenseModel() {
        License license = new License()
        if (name != null)
            license.setName(name)
        if (url != null)
            license.setUrl(url)
        return license
    }
}
