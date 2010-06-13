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

package com.senacor.wbs.web.core.layout;

import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IFormVisitorParticipant;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Fragment;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.apache.wicket.util.value.ValueMap;

import com.senacor.domain.security.AuthenticationManager;
import com.senacor.wbs.web.StartPage;
import com.senacor.wbs.web.core.form.TooltipFeedbackIndicator;
import com.senacor.wbs.web.core.security.AuthenticationContext;
import com.senacor.wbs.web.core.security.WBSSession;

@SuppressWarnings("serial")
public class AuthenticationStatePanel extends Panel {
	@SpringBean()
	private AuthenticationManager authenticationManager;

	public AuthenticationStatePanel(String id) {
		super(id);
		setRenderBodyOnly(true);
		add(new LoginFragment("login"));
		add(new LogoutFragment("logout"));
	}

	private class LoginFragment extends Fragment {
		public LoginFragment(String id) {
			super(id, "loginFragment", AuthenticationStatePanel.this);
			setRenderBodyOnly(true);
			add(new LoginForm("loginForm"));
		}

		@Override
		public boolean isVisible() {
			return !WBSSession.get().isAuthenticated();
		}

		public final class LoginForm extends StatelessForm {
			/**
			 * TooltipFeedbackIndicator an der Form und allen enthaltenen
			 * FormComponents registrieren
			 */
			@Override
			protected void onBeforeRender() {
				super.onBeforeRender();
				this.add(new TooltipFeedbackIndicator());
				visitFormComponents(new FormComponent.IVisitor() {
					public Object formComponent(IFormVisitorParticipant formComponent) {
						FormComponent f = (FormComponent) formComponent;
						f.add(new TooltipFeedbackIndicator());
						return f;
					}
				});
			}

			public LoginForm(final String id) {
				super(id, new CompoundPropertyModel(new ValueMap()));
				// only remember username, not passwords
				TextField username = new TextField("username");
				username.setRequired(true);
				username.setOutputMarkupId(false);
				add(username);
				// the token
				add(new PasswordTextField("password").setOutputMarkupId(false));
			}

			public final void onSubmit() {
				ValueMap values = (ValueMap) getModelObject();
				String username = values.getString("username");
				String password = values.getString("password");
				AuthenticationContext authenticationContext = new AuthenticationContext(username, password, authenticationManager);
				try {
					WBSSession.get().login(authenticationContext);
					setResponsePage(StartPage.class);
				} catch (Exception e) {
					error(getLocalizer().getString("exception.login", LoginFragment.this, "Illegal username password combo"));
				}
			}
		}
	}

	private class LogoutFragment extends Fragment {
		public LogoutFragment(String id) {
			super(id, "logoutFragment", AuthenticationStatePanel.this);
			setRenderBodyOnly(true);
			add(new Link("logoutLink") {
				@Override
				public void onClick() {
					WBSSession.get().invalidate();
					setResponsePage(StartPage.class);
				}
			});
			Label loggedInDuration = new Label("loggedInDuration", new AbstractReadOnlyModel() {
				@Override
				public Object getObject() {
					WBSSession session = (WBSSession) getSession();
					return (System.currentTimeMillis() - session.getLoginTime()) / 60000;
				}
			});
			loggedInDuration.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(30)));
			add(loggedInDuration);
			add(new Label("vorname", new PropertyModel(WBSSession.get(), "user.vorname")));
			add(new Label("name", new PropertyModel(WBSSession.get(), "user.name")));
		}

		@Override
		public boolean isVisible() {
			return WBSSession.get().isAuthenticated();
		}
	}
}
