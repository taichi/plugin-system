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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.inject.Inject;

/**
 * @author taichi
 */
public class PluginConsumer {

	Map<String, Plugin> plugins;

	@Inject
	public PluginConsumer(Map<String, Plugin> plugins) {
		plugins.entrySet().stream().map(Map.Entry::getValue)
				.forEach(Plugin::initialize);
		this.plugins = plugins;
	}

	public Optional<Plugin> find(String name) {
		return Optional.ofNullable(this.plugins.get(name));
	}

	public List<String> execute() {
		return this.plugins.entrySet().stream().map(e -> ">> " + e.getKey())
				.collect(Collectors.toList());
	}
}
