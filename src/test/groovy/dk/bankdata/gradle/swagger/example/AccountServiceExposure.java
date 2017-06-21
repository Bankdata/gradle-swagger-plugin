package dk.bankdata.gradle.swagger.example;

import dk.bankdata.gradle.swagger.example.model.AccountRepresentation;
import dk.bankdata.gradle.swagger.example.model.AccountUpdateRepresentation;
import dk.bankdata.gradle.swagger.example.model.AccountsRepresentation;
import io.swagger.annotations.*;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

/**
 * Exposing account as REST service.
 */
@Path("/accounts")
@Api(value = "/accounts", authorizations = @Authorization("oauth2"))
public class AccountServiceExposure {

    @GET
    @Produces({"application/hal+json"})
    @ApiOperation(value = "List all accounts", nickname = "listAccounts", response = AccountsRepresentation.class)
    public Response list(@Context UriInfo uriInfo, @Context Request request) {
        return Response.ok().build();
    }

    @GET
    @Path("{regNo}-{accountNo}")
    @Produces({"application/hal+json"})
    @ApiOperation(value = "Get single account", nickname = "getAccount", response = AccountRepresentation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "No account found.")
    })
    public Response get(@PathParam("regNo") @Pattern(regexp = "^[0-9]{4}$") String regNo,
                        @PathParam("accountNo") @Pattern(regexp = "^[0-9]+$") String accountNo,
                        @Context UriInfo uriInfo, @Context Request request) {
        return Response.ok().build();
    }

    @GET
    @Path("{regNo}-{accountNo}")
    @Produces({"application/hal+json;v=1"})
    @ApiOperation(value = "Version 1", hidden = true)
    public Response getV1() {
        return null;
    }

    @PUT
    @Path("{regNo}-{accountNo}")
    @Produces({"application/hal+json"})
    @Consumes(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Create new or update existing account", nickname = "updateAccount", response = AccountRepresentation.class)
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "No updating possible")
    })
    public Response createOrUpdate(@PathParam("regNo") @Pattern(regexp = "^[0-9]{4}$") String regNo,
                                   @PathParam("accountNo") @Pattern(regexp = "^[0-9]+$") String accountNo,
                                   @Valid AccountUpdateRepresentation account,
                                   @Context UriInfo uriInfo, @Context Request request) {
        return Response.ok().build();
    }

}
