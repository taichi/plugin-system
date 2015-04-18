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
package serviceloader;

/**
 * @author taichi
 */
public class FirstPlugin implements Plugin {

	boolean initialized = false;

	@Override
	public String name() {
		return "first-plugin";
	}

	@Override
	public void initialize() {
		this.initialized = true;
	}

}
