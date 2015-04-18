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

import java.util.List;
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
		PluginConsumer instance = injector.getInstance(PluginConsumer.class);
		String string = instance.execute().get(0);
		assertEquals(">> first-plugin", string);
	}

	@Test
	public void dynamicLoading() throws Exception {
		ServiceLoader<Module> loader = ServiceLoader.load(Module.class);
		Injector injector = Guice.createInjector(loader);
		PluginConsumer instance = injector.getInstance(PluginConsumer.class);
		List<String> names = instance.execute();
		assertEquals(2, names.size());
		assertEquals(">> first-plugin", names.get(0));
		assertEquals(">> second-plugin", names.get(1));
	}
}
