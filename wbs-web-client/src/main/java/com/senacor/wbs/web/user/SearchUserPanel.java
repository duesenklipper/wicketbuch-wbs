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

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AbstractAutoCompleteTextRenderer;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.security.components.markup.html.form.SecureForm;

import com.senacor.domain.user.User;
import com.senacor.wbs.web.border.ToggleBorder;

public abstract class SearchUserPanel extends Panel {
	public SearchUserPanel(final String id) {
		super(id);
		final ToggleBorder border = new ToggleBorder("border", new ResourceModel("searchpanel.heading"));
		border.add(new UserIndexForm("userIndexForm"));
		border.add(new UserForm("userForm"));
		border.add(new Link("createUser") {
			@Override
			public void onClick() {
				setResponsePage(CreateUserPage.class);
			}
		});
		add(border);
	}

	public class UserIndexForm extends SecureForm {
		private final Pattern usernamePattern = Pattern.compile(".*\\((.*)\\).*");
		String selectedUser;

		public UserIndexForm(final String id) {
			super(id);
			final AutoCompleteTextField kriterien = new AutoCompleteTextField("kriterien", new PropertyModel(this, "selectedUser"),
					new UserAutoCompleteTextRenderer()) {
				@Override
				protected Iterator getChoices(final String input) {
					return doGetChoices(input);
				}
			};
			kriterien.add(new AjaxFormSubmitBehavior(UserIndexForm.this, "onchange") {
				@Override
				protected void onSubmit(final AjaxRequestTarget target) {
					final Matcher m = usernamePattern.matcher(selectedUser);
					if (m.find()) {
						final String userName = m.group(1);
						final User user = new User();
						user.setUsername(userName);
						doSearch(target, user);
					}
				}

				@Override
				protected void onError(final AjaxRequestTarget target) {
				}
			});
			add(kriterien);
		}

		@Override
		protected void onSubmit() {
		}
	}

	public class UserAutoCompleteTextRenderer extends AbstractAutoCompleteTextRenderer {
		@Override
		protected String getTextValue(final Object object) {
			final User user = (User) object;
			return user.getVorname() + " " + user.getName() + " (" + user.getUsername() + ")";
		}
	}

	public class UserForm extends SecureForm {
		protected final User user = new User();

		public UserForm(final String componentName) {
			super(componentName);
			setModel(new CompoundPropertyModel(user));
			add(new TextField("name"));
			add(new TextField("vorname"));
			add(new TextField("username"));
			final AjaxButton searchButton = new AjaxButton("search", this) {
				@Override
				protected void onSubmit(final AjaxRequestTarget target, final Form form) {
					doSearch(target, user);
				}
			};
			add(searchButton);
		}
	}

	protected abstract Iterator<User> doGetChoices(String input);

	protected abstract void doSearch(AjaxRequestTarget target, User user);

	public static void main(final String[] args) {
		final Matcher m = Pattern.compile(".*\\((.*)\\)").matcher("aa (bb)");
		m.find();
		System.out.println(m.group(1));
	}
}
