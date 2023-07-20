/*---------------------------------------------------------------------
 *	Copyright (c) 2023 Veeva Systems Inc.  All Rights Reserved.
 *	This code is based on pre-existing content developed and
 *	owned by Veeva Systems Inc. and may only be used in connection
 *	with the deliverable with which it was provided to Customer.
 *---------------------------------------------------------------------
 */

package com.veeva.vault.tools.cli;

import com.veeva.vault.tools.client.Client;
import com.veeva.vault.tools.count.CountVaultData;
import com.veeva.vault.tools.delete.DeleteVaultData;
import com.veeva.vault.vapil.api.model.response.DomainResponse;
import com.veeva.vault.vapil.api.request.DomainRequest;
import org.apache.log4j.Logger;

import java.util.Arrays;

public class VaultDataTools {
    private static Logger logger = Logger.getLogger(VaultDataTools.class);

    /**
     * Main driver method for VaultDataTools. Loads the given command line inputs, performs basic validation, and
     * initiates the selected action.
     *
     * @param args - command line arguments
     */
    public static void main(String[] args) {

        DataToolOptions dataToolOptions = DataToolOptions.loadFromCliArguments(args);

        DataToolOptions.Action action;
        try {
            action = dataToolOptions.getAction();
            if (action == null) {
                logger.error("Action is required");
                return;
            }
        } catch (IllegalArgumentException e) {
            logger.error("Unknown action provided; Expected values = " + Arrays.asList(DataToolOptions.Action.values()));
            return;
        }

        try {
            Client.login(dataToolOptions);
        } catch (IllegalArgumentException illegalArgumentException) {
            return;
        }

        if (Client.getVaultClient() == null || !Client.getVaultClient().getAuthenticationResponse().isSuccessful()) {
            return;
        }

        DomainResponse domainResponse = Client.getVaultClient().newRequest(DomainRequest.class)
                .retrieveDomainInformation();

        if (!domainResponse.getDomain().getDomainType().equalsIgnoreCase("SANDBOX")) {
            logger.error("This tool can only be run in a Sandbox domain.");
            return;
        }

        switch (action) {

            case COUNT:
                CountVaultData countVaultData = new CountVaultData();
                countVaultData.process(dataToolOptions);
                break;

            case DELETE:
                DeleteVaultData deleteVaultData = new DeleteVaultData();
                deleteVaultData.process(dataToolOptions);
                break;

            default:
        }
    }
}
