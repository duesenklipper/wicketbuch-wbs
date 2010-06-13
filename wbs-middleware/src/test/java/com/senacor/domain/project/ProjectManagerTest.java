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

package com.senacor.domain.project;

import com.senacor.core.manager.ManagerTest;
import com.senacor.domain.user.User;
import com.senacor.domain.user.UserManager;

public class ProjectManagerTest extends ManagerTest<ProjectManager> {
	private final UserManager userManager;

	public ProjectManagerTest() {
		userManager = managerFactory.getManager(UserManager.class);
	}

	public void testCreateProject() {
		User user = new User();
		user.setName("Siefart");
		userManager.save(user);
		Project project = new Project();
		manager.save(project);
		assertEquals(0, project.getUsers().size());
		project.addUser(user);
		manager.save(project);
		assertEquals(1, project.getUsers().size());
		project = manager.retrieve(project.getId());
		assertEquals(1, project.getUsers().size());
		manager.delete(project);
		userManager.delete(user);
	}
}
