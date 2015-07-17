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
package org.onosproject.app.vtnrsc;

/**
 * Representation of the neutronNetwork.
 */
public interface TenantNetwork {

    /**
     * Coarse classification of the state of the neutronNetwork.
     */
    public enum State {
        /**
         * Signifies that a neutronNetwork is currently active.This state means
         * that this network is available.
         */
        ACTIVE,
        /**
         * Signifies that a neutronNetwork is currently built.
         */
        BUILD,
        /**
         * Signifies that a neutronNetwork is currently unavailable.
         */
        DOWN,
        /**
         * Signifies that a neutronNetwork is currently error.
         */
        ERROR
    }

    /**
     * Coarse classification of the type of the neutronNetwork.
     */
    public enum Type {
        /**
         * Signifies that a neutronNetwork is local.
         */
        LOCAL
    }

    /**
     * Returns the neutronNetwork identifier.
     *
     * @return neutronNetwork identifier
     */
    TenantNetworkId id();

    /**
     * Returns the neutronNetwork name.
     *
     * @return neutronNetwork name
     */
    String name();

    /**
     * Returns the administrative state of the neutronNetwork,which is up(true)
     * or down(false).
     *
     * @return network admin state up
     */
    boolean adminStateUp();

    /**
     * Returns the neutronNetwork state.
     *
     * @return neutronNetwork state
     */
    State state();

    /**
     * Indicates whether this neutronNetwork is shared across all tenants. By
     * default,only administrative user can change this value.
     *
     * @return neutronNetwork shared
     */
    boolean shared();

    /**
     * Returns the UUID of the tenant that will own the neutronNetwork. This
     * tenant can be different from the tenant that makes the create
     * neutronNetwork request.
     *
     * @return neutronNetwork tenant identifier
     */
    TenantId tenantId();

    /**
     * Returns the routerExternal.Indicates whether this network is externally
     * accessible.
     *
     * @return neutronNetwork router external
     */
    boolean routerExternal();

    /**
     * Returns the neutronNetwork Type.
     *
     * @return neutronNetwork Type
     */
    Type type();

    /**
     * Returns the neutronNetwork physical network.
     *
     * @return neutronNetwork physical network
     */
    PhysicalNetwork physicalNetwork();

    /**
     * Returns the neutronNetwork segmentation id.
     *
     * @return neutronNetwork segmentation id
     */
    SegmentationId segmentationId();
}
