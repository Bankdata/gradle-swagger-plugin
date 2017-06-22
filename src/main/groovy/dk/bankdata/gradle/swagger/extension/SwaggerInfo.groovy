package dk.bankdata.gradle.swagger.extension

import io.swagger.models.Info
import io.swagger.models.Swagger
import org.gradle.api.Project

/**
 * General information that goes into the generated Swagger documentation.
 */
class SwaggerInfo {
    private Project project

    String title
    String version
    String description
    String termsOfService
    SwaggerContact contact
    SwaggerLicense license

    SwaggerContact contact(Closure closure) {
        contact = project.configure(new SwaggerContact(), closure)
        contact
    }

    SwaggerLicense license(Closure closure) {
        license = project.configure(new SwaggerLicense(), closure)
        license
    }

    Info createInfoModel() {
        Info info = new Info()
        if (title != null)
            info.setTitle(title)
        if (version != null)
            info.setVersion(version)
        if (description != null)
            info.setDescription(description)
        if (termsOfService != null)
            info.setTermsOfService(termsOfService)
        if (contact != null)
            info.setContact(contact.createContactModel())
        if (license != null)
            info.setLicense(license.createLicenseModel())
        return info
    }}
