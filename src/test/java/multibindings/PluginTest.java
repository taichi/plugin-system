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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;

import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

import first.FirstModule;

/**
 * @author taichi
 */
public class PluginTest {

	@Test
	public void staticLoading() {
		Injector injector = Guice.createInjector(new FirstModule());
		PluginConsumer consumer = injector.getInstance(PluginConsumer.class);

		Optional<Plugin> opt = consumer.find("first");
		assertTrue(opt.isPresent());

		String string = consumer.execute().get(0);
		assertEquals(">> first", string);
	}

	@Test
	public void dynamicLoading() throws Exception {
		ServiceLoader<Module> loader = ServiceLoader.load(Module.class);
		Injector injector = Guice.createInjector(loader);
		PluginConsumer consumer = injector.getInstance(PluginConsumer.class);
		List<String> names = consumer.execute();
		assertEquals(2, names.size());
		assertEquals(">> first", names.get(0));
		assertEquals(">> second", names.get(1));
	}

}
