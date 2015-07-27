/*
 * Copyright 2015 Open Networking Laboratory
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
package org.onosproject.distributedprimitives.cli;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.store.service.AsyncAtomicCounter;
import org.onosproject.store.service.StorageService;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * CLI command to increment a distributed counter.
 */
@Command(scope = "onos", name = "counter-test-increment",
        description = "Increment a distributed counter")
public class CounterTestIncrementCommand extends AbstractShellCommand {

    private final Logger log = getLogger(getClass());

    @Option(name = "-i", aliases = "--inMemory", description = "use in memory map?",
            required = false, multiValued = false)
    private boolean inMemory = false;

    @Argument(index = 0, name = "counter",
            description = "Counter name",
            required = true, multiValued = false)
    String counter = null;

    AsyncAtomicCounter atomicCounter;


    @Override
    protected void execute() {
        StorageService storageService = get(StorageService.class);
        if (inMemory) {
            atomicCounter = storageService.atomicCounterBuilder()
                    .withName(counter)
                    .withPartitionsDisabled()
                    .buildAsyncCounter();
        } else {
            atomicCounter = storageService.atomicCounterBuilder()
                    .withName(counter)
                    .buildAsyncCounter();
        }

        CompletableFuture<Long> result = atomicCounter.incrementAndGet();
        try {
            print("%s was incremented to %d", counter, result.get(3, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            return;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
    }
}
