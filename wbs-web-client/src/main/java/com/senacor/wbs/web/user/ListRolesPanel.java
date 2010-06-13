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

package com.senacor.wbs.web.user;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.user.Role;
import com.senacor.domain.user.RoleManager;

public class ListRolesPanel extends Panel {
	@SpringBean()
	private RoleManager roleManager;
	private EditRoleModalWindow editRoleModalWindow;
	private ListView roleListView;

	public ListRolesPanel(String id) {
		super(id);
		setOutputMarkupId(true);
		setRenderBodyOnly(false);
		roleListView = new ListView("roleList", roleManager.findAll()) {
			protected void populateItem(ListItem item) {
				Role role = (Role) item.getModelObject();
				item.add(new RoleFragment("role", "roleFragment", role));
			}
		};
		add(roleListView);
		editRoleModalWindow = new EditRoleModalWindow("editRoleWindow");
		editRoleModalWindow.setOutputMarkupId(true);
		editRoleModalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {
			public void onClose(AjaxRequestTarget target) {
				roleListView.setList(roleManager.findAll());
				target.addComponent(ListRolesPanel.this);
			}
		});
		add(editRoleModalWindow);
	}

	public class RoleFragment extends Fragment {
		public RoleFragment(String id, String markupId, final Role role) {
			super(id, markupId);
			AjaxLink link = new AjaxLink("editRole") {
				@Override
				public void onClick(AjaxRequestTarget target) {
					editRoleModalWindow.setRole(role);
					editRoleModalWindow.show(target);
				}
			};
			add(link);
			link.add(new Label("id", role.getId().toString()));
			link.add(new Label("name", role.getName()));
		}
	}
}
