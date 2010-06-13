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
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.senacor.domain.user.User;
import com.senacor.domain.user.UserManager;

public class CreateUserSimplePage extends ComponentDemoPage {
	@SpringBean()
	private UserManager userManager;
	private User user = new User();

	public CreateUserSimplePage() {
		Form form = new Form("userform", new CompoundPropertyModel(user)) {
			@Override
			protected void onSubmit() {
				System.out.println("submit!");
				System.out.println(user);
			}
		};
		form.add(new TextField("username"));
		form.add(new TextField("mainContact.email"));
		form.add(new PasswordTextField("passwort"));
		form.add(new PasswordTextField("passwort2"));
		form.add(new Button("submit"));
		this.add(form);
	}
}
