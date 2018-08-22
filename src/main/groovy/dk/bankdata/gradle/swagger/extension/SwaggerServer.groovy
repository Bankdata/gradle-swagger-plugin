package dk.bankdata.gradle.swagger.extension

import io.swagger.v3.oas.models.servers.Server

class SwaggerServer {

    String url
    String description

    Server createServerModel() {
        Server server = new Server()
        server.url = url
        server.description = description
        server
    }
}
