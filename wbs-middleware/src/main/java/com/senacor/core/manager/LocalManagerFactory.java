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

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.senacor.domain.user.UserManager;

public class LocalManagerFactory implements ManagerFactory {
	private final static ApplicationContext applicationContext;
	private final static ManagerFactory managerFactory;
	static {
		applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
		managerFactory = new LocalManagerFactory();
		UserManager userManager = managerFactory.getManager(UserManager.class);
		userManager.initializeIndex();
	}

	private LocalManagerFactory() {
	}

	public <T extends Manager> T getManager(Class<T> clazz) {
		Object bean = applicationContext.getBean(StringUtils.uncapitalize(clazz.getSimpleName()));
		assert bean != null : ManagerError.NO_EXACT_MANAGER_DEFINITION_FOUND.getDescription();
		return (T) bean;
	}

	public static ManagerFactory getManagerFactory() {
		return managerFactory;
	}
}
