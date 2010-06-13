/**
 * Copyright 2009 Roland Foerther, Carl-Eric-Menzel, Olaf Siefart
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

package com.senacor.core.manager;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import junit.framework.TestCase;

public abstract class ManagerTest<T extends Manager> extends TestCase {
	protected final T manager;
	protected final ManagerFactory managerFactory;

	public ManagerTest() {
		Type[] types = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments();
		Class<T> clazz = (Class<T>) types[0];
		managerFactory = LocalManagerFactory.getManagerFactory();
		assertNotNull(managerFactory);
		manager = managerFactory.getManager(clazz);
	}
}