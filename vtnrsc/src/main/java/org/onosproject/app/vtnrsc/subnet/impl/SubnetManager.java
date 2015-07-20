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
package org.onosproject.app.vtnrsc.subnet.impl;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static org.onlab.util.Tools.groupedThreads;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.felix.scr.annotations.Activate;
import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Deactivate;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.onlab.util.KryoNamespace;
import org.onosproject.app.vtnrsc.Subnet;
import org.onosproject.app.vtnrsc.SubnetId;
import org.onosproject.app.vtnrsc.TenantNetworkId;
import org.onosproject.app.vtnrsc.subnet.SubnetService;
import org.onosproject.app.vtnrsc.tenantnetwork.TenantNetworkService;
import org.onosproject.store.service.EventuallyConsistentMap;
import org.onosproject.store.service.MultiValuedTimestamp;
import org.onosproject.store.service.StorageService;
import org.onosproject.store.service.WallClockTimestamp;
import org.slf4j.Logger;

/**
 * Provides implementation of the subnet SB &amp; NB APIs.
 */
@Component(immediate = true)
@Service
public class SubnetManager implements SubnetService {

    private static final String SUBNET_ID_NULL = "Subnet ID cannot be null";

    private final Logger log = getLogger(getClass());

    private EventuallyConsistentMap<SubnetId, Subnet> subnetStore;
    private ScheduledExecutorService backgroundService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected StorageService storageService;

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    protected TenantNetworkService tenantNetworkService;

    @Activate
    public void activate() {
        backgroundService = newSingleThreadScheduledExecutor(groupedThreads("onos/subnet",
                                                                            "manager-background"));

        KryoNamespace.Builder serializer = KryoNamespace.newBuilder()
                .register(MultiValuedTimestamp.class);
        subnetStore = storageService
                .<SubnetId, Subnet>eventuallyConsistentMapBuilder()
                .withName("all_subnet").withSerializer(serializer)
                .withTimestampProvider((k, v) -> new WallClockTimestamp())
                .build();

        log.info("Started");
    }

    @Deactivate
    public void deactivate() {
        backgroundService.shutdown();

        subnetStore.destroy();
        log.info("Stopped");
    }

    @Override
    public Iterable<Subnet> getSubnets() {
        return Collections.unmodifiableCollection(subnetStore.values());
    }

    @Override
    public Subnet getSubnet(SubnetId subnetId) {
        checkNotNull(subnetId, SUBNET_ID_NULL);
        if (exists(subnetId)) {
            return subnetStore.get(subnetId);
        } else {
            return null;
        }
    }

    @Override
    public boolean exists(SubnetId subnetId) {
        checkNotNull(subnetId, SUBNET_ID_NULL);
        return subnetStore.containsKey(subnetId);
    }

    @Override
    public boolean createSubnets(Iterable<Subnet> subnets) {
        for (Subnet subnet : subnets) {
            if (!tenantNetworkService.exists(TenantNetworkId.networkId(subnet
                    .networkId().toString()))) {
                return false;
            }
            subnetStore.put(subnet.id(), subnet);
        }
        return true;
    }

    @Override
    public boolean updateSubnets(Iterable<Subnet> subnets) {
        if (subnets != null) {
            for (Subnet subnet : subnets) {
                subnetStore.put(subnet.id(), subnet);
            }
        }
        return true;
    }

    @Override
    public boolean removeSubnets(Iterable<SubnetId> subnetIds) {
        if (subnetIds != null) {
            for (SubnetId subnetId : subnetIds) {
                subnetStore.remove(subnetId);
            }
        }
        return true;
    }

}
