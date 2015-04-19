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
import java.util.function.Function;

import javax.inject.Inject;
import javax.inject.Provider;

import com.google.inject.Binder;
import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;
import com.google.inject.multibindings.MapBinder;

/**
 * @author taichi
 */
public class PluginRegistry {

	Map<Class<? extends Plugin>, Function<String, Optional<Provider<? extends Plugin>>>> providers = new HashMap<>();

	@Inject
	public PluginRegistry(Injector injector) {
		register(injector, InputPlugin.class,
				new TypeLiteral<Map<String, Provider<InputPlugin>>>() {
				});
		register(injector, OutputPlugin.class,
				new TypeLiteral<Map<String, Provider<OutputPlugin>>>() {
				});
	}

	<T extends Plugin> void register(Injector injector, Class<T> clazz,
			TypeLiteral<Map<String, Provider<T>>> tl) {
		Key<Map<String, Provider<T>>> key = Key.get(tl);
		Binding<Map<String, Provider<T>>> binding = injector.getBinding(key);
		Map<String, Provider<T>> map = binding.getProvider().get();
		this.providers.put(clazz, name -> Optional.ofNullable(map.get(name)));
	}

	@SuppressWarnings("unchecked")
	public <T extends Plugin> Optional<T> newPlugin(Class<T> type, String name) {
		Function<String, Optional<Provider<? extends Plugin>>> fn = this.providers
				.get(type);
		if (fn == null) {
			throw new IllegalStateException("unsupported plugin type " + type);
		}
		Optional<? extends Plugin> plugin = fn.apply(name).map(Provider::get);
		return (Optional<T>) plugin.map(p -> {
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
