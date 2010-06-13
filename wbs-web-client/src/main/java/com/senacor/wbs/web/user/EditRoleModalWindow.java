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

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.user.PrincipalManager;
import com.senacor.domain.user.Role;
import com.senacor.domain.user.RoleManager;
import com.senacor.wbs.web.core.layout.BaseModalWindow;

public class EditRoleModalWindow extends BaseModalWindow {
	private EditRoleFragment editRoleFragment;

	public EditRoleModalWindow(String id) {
		super(id);
		setInitialWidth(450);
		setInitialHeight(300);
		setTitle("Ändern der Rolle");
		editRoleFragment = new EditRoleFragment(this.getContentId(), "editRoleFragment", this);
		setContent(editRoleFragment);
	}

	public void setRole(Role role) {
		editRoleFragment.updateModel(role);
	}

	class EditRoleFragment extends Fragment {
		@SpringBean
		private PrincipalManager principalManager;
		private EditRoleForm editRoleForm;

		public EditRoleFragment(String id, String markupId, MarkupContainer markupContainer) {
			super(id, markupId, markupContainer);
			editRoleForm = new EditRoleForm("editRoleForm", new CompoundPropertyModel(new Role()));
			add(editRoleForm);
		}

		class EditRoleForm extends Form {
			public EditRoleForm(String id, IModel model) {
				super(id, model);
				add(new TextField("id").setEnabled(false));
				add(new TextField("name"));
				add(new ListMultipleChoice("principals", principalManager.findAll(), new ChoiceRenderer("name")));
				add(new AjaxButton("saveRole") {
					@SpringBean
					private RoleManager roleManager;

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						Role role = (Role) form.getModel().getObject();
						roleManager.saveOrUpdate(role);
						close(target);
					}
				});
			}
		}

		public void updateModel(Role role) {
			editRoleForm.setModel(new CompoundPropertyModel(role));
		}
	}
}
