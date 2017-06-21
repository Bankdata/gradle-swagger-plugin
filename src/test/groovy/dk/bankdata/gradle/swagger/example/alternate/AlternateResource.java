package dk.bankdata.gradle.swagger.example.alternate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Api
@Path("alternate")
public class AlternateResource {

    @GET
    @ApiOperation("alternate operation")
    public Response list() {
        return null;
    }

}
