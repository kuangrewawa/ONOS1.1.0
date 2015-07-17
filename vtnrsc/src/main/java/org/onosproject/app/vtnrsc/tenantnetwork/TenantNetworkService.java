/*
 * Copyright 2014 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.onosproject.app.vtnrsc.tenantnetwork;

import org.onosproject.app.vtnrsc.TenantNetwork;
import org.onosproject.app.vtnrsc.TenantNetworkId;

/**
 * Service for interacting with the inventory of neutronNetwork.
 */
public interface TenantNetworkService {

    /**
     * Returns if the neutronNetwork is existed.
     *
     * @param networkId neutronNetwork identifier
     * @return true or false if one with the given identifier exists.
     */
    boolean exists(TenantNetworkId networkId);

    /**
     * Returns the number of neutronNetwork known to the system.
     *
     * @return number of neutronNetwork.
     */
    int getNetworkCount();

    /**
     * Returns an iterable collection of the currently known neutronNetwork.
     *
     * @return collection of neutronNetwork.
     */
    Iterable<TenantNetwork> getNetworks();

    /**
     * Returns the neutronNetwork with the identifier.
     *
     * @param networkId TenantNetwork identifier
     * @return TenantNetwork or null if one with the given identifier is not
     *         known.
     */
    TenantNetwork getNetwork(TenantNetworkId networkId);

    /**
     * Creates neutronNetworks by networks.
     *
     * @param networks the collection of neutronNetworks
     * @return true if all given identifiers created successfully.
     */
    boolean createNetworks(Iterable<TenantNetwork> networks);

    /**
     * Updates neutronNetworks by neutronNetworks.
     *
     * @param networks the collection of neutronNetworks
     * @return true if all given identifiers updated successfully.
     */
    boolean updateNetworks(Iterable<TenantNetwork> networks);

    /**
     * Deletes neutronNetwork by neutronNetworkIds.
     *
     * @param networksId the collection of neutronNetworkIds
     * @return true if the specified neutronNetwork deleted successfully.
     */
    boolean removeNetworks(Iterable<TenantNetworkId> networksId);
}
