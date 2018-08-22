package dk.bankdata.gradle.swagger.extension

import io.swagger.v3.oas.models.info.Contact

/**
 * Modelling contact information to go into the generated Swagger document.
 */
class SwaggerContact {
    String name
    String url
    String email

    Contact createContactModel() {
        Contact contact = new Contact()
        if (name != null)
            contact.setName(name)
        if (url != null)
            contact.setUrl(url)
        if (email != null)
            contact.setEmail(email)
        return contact
    }
}
