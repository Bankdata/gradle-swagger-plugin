package dk.bankdata.gradle.swagger.example;

import dk.bankdata.gradle.swagger.example.model.AccountRepresentation;
import dk.bankdata.gradle.swagger.example.model.AccountUpdateRepresentation;
import dk.bankdata.gradle.swagger.example.model.AccountsRepresentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Exposing account as REST service.
 */
@Path("/accounts")
public class AccountServiceExposure {

    @GET
    @Produces({"application/hal+json"})
    @Operation(summary = "List all accounts") //AccountsRepresentation.class)
    public Response list(@Context UriInfo uriInfo, @Context Request request) {
        return Response.ok().build();
    }

    @GET
    @Path("{regNo}-{accountNo}")
    @Produces({"application/hal+json"})
    @Operation(summary = "Get single account") // AccountRepresentation.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "No account found.")
    })
    public Response get(@PathParam("regNo") @Pattern(regexp = "^[0-9]{4}$") String regNo,
                        @PathParam("accountNo") @Pattern(regexp = "^[0-9]+$") String accountNo,
                        @Context UriInfo uriInfo, @Context Request request) {
        return Response.ok().build();
    }

    @GET
    @Path("{regNo}-{accountNo}")
    @Produces({"application/hal+json;v=1"})
    @Operation(summary = "Version 1", hidden = true)
    public Response getV1() {
        return null;
    }

    @PUT
    @Path("{regNo}-{accountNo}")
    @Produces({"application/hal+json"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create new or update existing account") //response = AccountRepresentation.class)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "No updating possible")
    })
    public Response createOrUpdate(@PathParam("regNo") @Pattern(regexp = "^[0-9]{4}$") String regNo,
                                   @PathParam("accountNo") @Pattern(regexp = "^[0-9]+$") String accountNo,
                                   @Valid AccountUpdateRepresentation account,
                                   @Context UriInfo uriInfo, @Context Request request) {
        return Response.ok().build();
    }

}
