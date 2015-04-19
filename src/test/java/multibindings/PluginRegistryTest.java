/*
 * Copyright 2015 SATO taichi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package multibindings;

import static org.junit.Assert.assertNotNull;

import java.util.ServiceLoader;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * @author taichi
 */
public class PluginRegistryTest {

	@Test
	public void eagerLoading() {
		ServiceLoader<Module> loader = ServiceLoader.load(Module.class);
		Injector injector = Guice.createInjector(loader);

		EagerLoadingPluginRegistry registry = injector
				.getInstance(EagerLoadingPluginRegistry.class);
		assertNotNull(registry.newPlugin(InputPlugin.class, "file"));
		assertNotNull(registry.newPlugin(InputPlugin.class, "url"));
		assertNotNull(registry.newPlugin(OutputPlugin.class, "file"));
	}

	@Test
	public void lazyLoading() {
		ServiceLoader<Module> loader = ServiceLoader.load(Module.class);
		Injector injector = Guice.createInjector(loader);

		PluginRegistry registry = injector.getInstance(PluginRegistry.class);
		assertNotNull(registry.newPlugin(InputPlugin.class, "file"));
		assertNotNull(registry.newPlugin(InputPlugin.class, "url"));
		assertNotNull(registry.newPlugin(OutputPlugin.class, "file"));
	}

}
