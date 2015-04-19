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

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

/**
 * @author taichi
 */
public class EagerLoadingPluginRegistry {

	Map<Class<? extends Plugin>, Map<String, ? extends Plugin>> plugins = new HashMap<>();

	@Inject
	public EagerLoadingPluginRegistry(Injector injector) {
		register(injector, InputPlugin.class,
				new TypeLiteral<Map<String, InputPlugin>>() {
				});
		register(injector, OutputPlugin.class,
				new TypeLiteral<Map<String, OutputPlugin>>() {
				});
	}

	<T extends Plugin> void register(Injector injector, Class<T> clazz,
			TypeLiteral<Map<String, T>> tl) {
		Key<Map<String, T>> key = Key.get(tl);
		Binding<Map<String, T>> binding = injector.getBinding(key);
		Map<String, T> map = binding.getProvider().get();
		this.plugins.put(clazz, map);
	}

	@SuppressWarnings("unchecked")
	public <T extends Plugin> Optional<T> newPlugin(Class<T> type, String name) {
		Map<String, ? extends Plugin> map = this.plugins.get(type);
		if (map == null) {
			throw new IllegalStateException("unsupported plugin type " + type);
		}
		return (Optional<T>) Optional.ofNullable(map.get(name)).map(p -> {
			p.initialize();
			return p;
		});
	}

	/**
	 * call from Modules
	 */
	public static <PLUGIN extends Plugin> MapBinder<String, PLUGIN> newBinder(
			Binder binder, Class<PLUGIN> type) {
		return MapBinder.newMapBinder(binder, String.class, type)
				.permitDuplicates();
	}
}
