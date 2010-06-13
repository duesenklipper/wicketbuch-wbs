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

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;

public class UserDetailsPanel extends Panel {
	public UserDetailsPanel(String id, final boolean showButton) {
		super(id);
		Form form = new Form("detailsform") {
			@Override
			protected void onSubmit() {
				UserDetailsPanel.this.onSubmit();
			}
		};
		form.add(new TextField("username"));
		form.add(new TextField("contactData.email"));
		form.add(new Button("submit") {
			@Override
			public boolean isVisible() {
				return showButton;
			}
		});
		add(form);
	}

	protected void onSubmit() {
	}
}
