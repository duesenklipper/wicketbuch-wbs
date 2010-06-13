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

import org.apache.wicket.PageParameters;
import org.apache.wicket.extensions.markup.html.tree.table.TreeTable;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.project.Project;
import com.senacor.domain.project.ProjectManager;

public class ProjectDetailsPage extends BaseProjectPage {
	@SpringBean()
	private ProjectManager projectManager;
	private TreeTable tree;

	public ProjectDetailsPage(final PageParameters pageParameters) {
		long projectId = pageParameters.getLong("projectId");
		Project project = projectManager.retrieve(projectId);
		middleColumn.getListPanel().add(new ProjectDetailsPanel(middleColumn.getListPanel().newChildId(), project));
		middleColumn.getListPanel().add(new EditableTreeTablePanel(middleColumn.getListPanel().newChildId(), project));
	}
}
