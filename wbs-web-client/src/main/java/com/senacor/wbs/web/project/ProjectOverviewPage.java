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

package com.senacor.wbs.web.project;

import java.util.List;

import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.project.Project;
import com.senacor.domain.project.ProjectManager;

public class ProjectOverviewPage extends BaseProjectPage {
	@SpringBean()
	private ProjectManager projectManager;

	public ProjectOverviewPage() {
		initialize();
	}

	private void initialize() {
		List<Project> projects = projectManager.findAll();
		middleColumn.getListPanel().add(new ProjectListPanel("projectListPanel", projects));
	}

	public ProjectOverviewPage(final ProjectManager projectManager) {
		this.projectManager = projectManager;
		initialize();
	}
}
