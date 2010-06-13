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

import org.hibernate.Hibernate;

import com.senacor.core.manager.IndexBaseManager;

public class TaskManagerImpl extends IndexBaseManager<Task> implements TaskManager {
	public List<Task> findTasks(Project p) {
		reattach(p);
		List<Task> tasks = p.getTasks();
		Hibernate.initialize(tasks);
		return tasks;
	}

	public Task addTask(Task task, Project project) {
		reattach(project);
		project.addTask(task);
		return task;
	}

	public List<Task> findSubtasks(Task t) {
		reattach(t);
		List<Task> subtasks = t.getSubtasks();
		Hibernate.initialize(subtasks);
		return subtasks;
	}

	public Task addSubtask(Task task, Task subtask) {
		reattach(task);
		task.getSubtasks().add(subtask);
		return task;
	}

	public void saveTasks(Project project, List<Task> tasks) {
		// Löschen der Tasks
		reattach(project);
		project.getTasks().clear();
		// Speichern der Tasks
		for (Task task : tasks) {
			project.addTask(saveOrUpdate(task));
		}
	}
}
