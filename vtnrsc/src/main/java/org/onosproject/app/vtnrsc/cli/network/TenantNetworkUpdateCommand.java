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
package org.onosproject.app.vtnrsc.cli.network;

import java.util.HashSet;
import java.util.Set;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.app.vtnrsc.DefaultTenantNetwork;
import org.onosproject.app.vtnrsc.PhysicalNetwork;
import org.onosproject.app.vtnrsc.SegmentationId;
import org.onosproject.app.vtnrsc.TenantId;
import org.onosproject.app.vtnrsc.TenantNetwork;
import org.onosproject.app.vtnrsc.TenantNetworkId;
import org.onosproject.app.vtnrsc.tenantnetwork.TenantNetworkService;
import org.onosproject.cli.AbstractShellCommand;

/**
 * Supports for updating a NeutronNetworks.
 */
@Command(scope = "onos", name = "NeutronNetwork-update", description = "Supports for updating NeutronNetworks")
public class TenantNetworkUpdateCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "id", description = "NeutronNetwork networkid Id", required = true,
            multiValued = false)
    String id = null;
    @Argument(index = 1, name = "name", description = "NeutronNetwork String name", required = true,
            multiValued = false)
    String name = null;
    @Argument(index = 2, name = "adminStateUp", description = "NeutronNetwork boolean adminStateUp",
            required = true, multiValued = false)
    boolean adminStateUp = false;
    @Argument(index = 3, name = "state", description = "The state of NeutronNetwork", required = true,
            multiValued = false)
    String state = null;
    @Argument(index = 4, name = "shared", description = "NeutronNetwork boolean shared", required = true,
            multiValued = false)
    boolean shared = false;
    @Argument(index = 5, name = "tenantID", description = "NeutronNetwork tenantId Id", required = true,
            multiValued = false)
    String tenantID = null;
    @Argument(index = 6, name = "routerExternal", description = "NeutronNetwork boolean routerExternal",
            required = true, multiValued = false)
    boolean routerExternal = false;
    @Argument(index = 7, name = "type", description = "The type of NeutronNetwork", required = true,
            multiValued = false)
    String type = null;
    @Argument(index = 8, name = "physicalNetwork", description = "NeutronNetwork physicalNetwork",
            required = true, multiValued = false)
    String physicalNetwork = null;
    @Argument(index = 9, name = "segmentationID", description = "NeutronNetwork segmentationID Id",
            required = true, multiValued = false)
    String segmentationID = null;

    @Override
    protected void execute() {
        TenantNetworkService service = get(TenantNetworkService.class);
        TenantNetwork network = new DefaultTenantNetwork(
                                                           TenantNetworkId
                                                                   .networkId(id),
                                                           name,
                                                           adminStateUp,
                                                           TenantNetwork.State
                                                                   .valueOf(state),
                                                           shared,
                                                           TenantId.tenantId(tenantID),
                                                           routerExternal,
                                                           TenantNetwork.Type
                                                                   .valueOf(type),
                                                           PhysicalNetwork
                                                                   .physicalNetwork(physicalNetwork),
                                                           SegmentationId
                                                                   .segmentationID(segmentationID));

        Set<TenantNetwork> networksSet = new HashSet<TenantNetwork>();
        networksSet.add(network);
        service.updateNetworks(networksSet);
    }

}
