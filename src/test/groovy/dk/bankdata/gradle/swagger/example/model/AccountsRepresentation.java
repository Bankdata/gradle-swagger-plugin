package dk.bankdata.gradle.swagger.example.model;

import dk.nykredit.jackson.dataformat.hal.HALLink;
import dk.nykredit.jackson.dataformat.hal.annotation.EmbeddedResource;
import dk.nykredit.jackson.dataformat.hal.annotation.Link;
import dk.nykredit.jackson.dataformat.hal.annotation.Resource;

import java.util.Collection;
import java.util.Collections;

/**
 * Represents a set of accounts from the REST service exposure.
 */
@Resource
public class AccountsRepresentation {

    @Link
    private HALLink self;

    @EmbeddedResource("accounts")
    private Collection<AccountRepresentation> accounts;

    public HALLink getSelf() {
        return self;
    }

    public Collection<AccountRepresentation> getAccounts() {
        return Collections.unmodifiableCollection(accounts);
    }
}
