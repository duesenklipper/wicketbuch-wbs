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

import java.util.HashMap;
import java.util.Map;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.senacor.domain.project.ProgressState;
import com.senacor.domain.project.Project;

public class ProjectDetailsPanel extends Panel {
	private static final Map<ProgressState, ResourceReference> progressStateIndicators = new HashMap<ProgressState, ResourceReference>() {
		{
			put(ProgressState.GREEN, new ResourceReference(EditableTreeTablePanel.class, "res/progress_green.png"));
			put(ProgressState.YELLOW, new ResourceReference(EditableTreeTablePanel.class, "res/progress_yellow.png"));
			put(ProgressState.RED, new ResourceReference(EditableTreeTablePanel.class, "res/progress_red.png"));
		}
	};

	public ProjectDetailsPanel(final String id, final Project project) {
		super(id);
		add(new Image("progressState", new Model<ResourceReference>() {
			@Override
			public ResourceReference getObject() {
				return progressStateIndicators.get(project.getProgress());
			}
		}));
	}
}
