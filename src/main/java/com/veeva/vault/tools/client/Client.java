/*---------------------------------------------------------------------
 *	Copyright (c) 2023 Veeva Systems Inc.  All Rights Reserved.
 *	This code is based on pre-existing content developed and
 *	owned by Veeva Systems Inc. and may only be used in connection
 *	with the deliverable with which it was provided to Customer.
 *---------------------------------------------------------------------
 */

package com.veeva.vault.tools.client;

import com.veeva.vault.tools.cli.DataToolOptions;
import com.veeva.vault.vapil.api.client.VaultClient;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class Client {
    private static VaultClient vaultClient;
    private static final Logger LOGGER = LogManager.getLogger(Client.class);

    private Client() {
    }

    /**
     * Gets the current VaultClient
     *
     * @return - Current VaultClient
     */
    public static VaultClient getVaultClient() {
        return vaultClient;
    }

    /**
     * Authenticates to Vault and stores the VaultClient
     *
     * @param dataToolOptions - DataToolOptions object containing the configuration from input
     */
    public static void login(DataToolOptions dataToolOptions) {
        if (dataToolOptions.getVaultDNS() == null || dataToolOptions.getVaultDNS().isEmpty()) {
            LOGGER.error("Vault DNS is required");
            throw new IllegalArgumentException("Vault DNS is required");
        }
        String clientId = "veeva-vault-devsupport-client-vault-data-tools";

        if (dataToolOptions.getVaultSessionId() != null) {
            vaultClient = VaultClient.newClientBuilder(VaultClient.AuthenticationType.SESSION_ID)
                    .withVaultDNS(dataToolOptions.getVaultDNS())
                    .withVaultClientId(clientId)
                    .withVaultSessionId(dataToolOptions.getVaultSessionId())
                    .build();
        } else {
            vaultClient = VaultClient.newClientBuilder(VaultClient.AuthenticationType.BASIC)
                    .withVaultDNS(dataToolOptions.getVaultDNS())
                    .withVaultClientId(clientId)
                    .withVaultUsername(dataToolOptions.getVaultUsername())
                    .withVaultPassword(dataToolOptions.getVaultPassword())
                    .build();
        }
    }
}
