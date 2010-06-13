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

import java.util.List;

import com.senacor.core.manager.ManagerTest;
import com.senacor.domain.user.User;
import com.senacor.domain.user.UserManager;

public class TaskManagerTest extends ManagerTest<TaskManager> {
	private ProjectManager projectManager;
	private UserManager userManager;
	private TaskManager taskManager;

	public TaskManagerTest() {
		userManager = managerFactory.getManager(UserManager.class);
		projectManager = managerFactory.getManager(ProjectManager.class);
		taskManager = managerFactory.getManager(TaskManager.class);
	}

	public void testAbgeltungssteuer() throws Exception {
		Project project = projectManager.retrieve(1);
		List<Task> tasks = taskManager.findTasks(project);
		assertEquals(1, tasks.size());
	}

	public void testSave() throws Exception {
		User user = new User();
		user.setName("Siefart");
		userManager.save(user);
		Project project = new Project();
		projectManager.save(project);
		assertEquals(0, project.getTasks().size());
		project = projectManager.retrieve(project.getId().longValue());
		Task task = new Task();
		task.setName("test");
		task = manager.addTask(task, project);
		projectManager.saveOrUpdate(project);
		assertEquals(1, project.getTasks().size());
		List<Task> tasks = manager.findTasks(project);
		assertEquals(1, tasks.size());
		task = tasks.get(0);
		assertEquals("test", task.getName());
		Task subtask = new Task();
		subtask.setName("subtest");
		manager.addSubtask(task, subtask);
		tasks = manager.findSubtasks(task);
		assertEquals(1, tasks.size());
		subtask = tasks.get(0);
		assertEquals("subtest", subtask.getName());
		userManager.delete(user);
	}
}
