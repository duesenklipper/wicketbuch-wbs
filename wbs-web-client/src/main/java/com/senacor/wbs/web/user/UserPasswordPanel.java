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
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.panel.Panel;

public class UserPasswordPanel extends Panel {
	public UserPasswordPanel(String id, final boolean showButton) {
		super(id);
		setOutputMarkupId(true);
		Form form = new Form("passwordform") {
			@Override
			protected void onSubmit() {
				UserPasswordPanel.this.onSubmit();
			}
		};
		form.add(new PasswordTextField("passwort"));
		form.add(new PasswordTextField("passwort2"));
		form.add(new Button("submit") {
			@Override
			public boolean isVisible() {
				return showButton;
			}
		});
		this.add(form);
	}

	protected void onSubmit() {
	}
}
