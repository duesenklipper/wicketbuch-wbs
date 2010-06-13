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

import java.util.Arrays;

import org.apache.wicket.extensions.wizard.StaticContentStep;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.extensions.wizard.WizardModel.ICondition;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.ListMultipleChoice;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackIndicator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import com.senacor.domain.user.Country;
import com.senacor.domain.user.RoleManager;
import com.senacor.domain.user.User;
import com.senacor.domain.user.UserManager;
import com.senacor.wbs.web.core.EnumChoiceRenderer;
import com.senacor.wbs.web.core.form.AddMarkupFeedbackIndicator;
import com.senacor.wbs.web.core.form.MarkAsRequiredIndicator;

public class CreateUserWizard extends Wizard {
	private boolean roleConfiguration;
	private String passwort1;
	private String passwort2;
	private User user;
	@SpringBean
	private UserManager userManager;

	public CreateUserWizard(String id) {
		super(id);
		user = new User();
		setDefaultModel(new CompoundPropertyModel<CreateUserWizard>(this));
		WizardModel model = new WizardModel();
		model.add(new UserNameStep());
		model.add(new UserDetailsStep());
		model.add(new UserRolesStep());
		model.add(new ConfirmationStep());
		init(model);
	}

	public void onCancel() {
		setResponsePage(UserAdminPage.class);
	}

	public void onFinish() {
		user.setPasswort(passwort1);
		userManager.save(user);
		setResponsePage(UserAdminPage.class);
	}

	// Username + Passwort + Email
	private final class UserNameStep extends WizardStep {
		public UserNameStep() {
			super(new ResourceModel("username.title"), new ResourceModel("username.summary"));
			passwort1 = user.getPasswort();
			passwort2 = user.getPasswort();
			add(new FormComponentFeedbackBorder("fbusername").add(new RequiredTextField("user.username").add(new MarkAsRequiredIndicator())));
			final PasswordTextField pw1 = new PasswordTextField("passwort1");
			pw1.add(new MarkAsRequiredIndicator());
			add(pw1);
			FormComponentFeedbackIndicator indicator = new FormComponentFeedbackIndicator("fbpasswort1");
			indicator.setIndicatorFor(pw1);
			add(indicator);
			add(new PasswordTextField("passwort2").add(new AddMarkupFeedbackIndicator()).add(new MarkAsRequiredIndicator()));
			RequiredTextField email = new RequiredTextField("user.mainContact.email");
			email.add(new AddMarkupFeedbackIndicator());
			email.add(EmailAddressValidator.getInstance());
			email.add(new MarkAsRequiredIndicator());
			add(email);
		}
	}

	private final class UserDetailsStep extends WizardStep {
		public UserDetailsStep() {
			setTitleModel(new ResourceModel("confirmation.title"));
			setSummaryModel(new StringResourceModel("userdetails.summary", this, new Model(user)));
			add(new RequiredTextField("user.vorname"));
			add(new RequiredTextField("user.name"));
			DropDownChoice dropDownChoice = new DropDownChoice("user.address.country", new PropertyModel(user, "address.country"), Arrays.asList(Country
					.values()), new EnumChoiceRenderer());
			add(dropDownChoice);
			add(new CheckBox("roleConfiguration"));
		}
	}

	private final class UserRolesStep extends WizardStep implements ICondition {
		@SpringBean
		private RoleManager roleManager;

		public UserRolesStep() {
			super(new ResourceModel("userroles.title"), null);
			setSummaryModel(new StringResourceModel("userroles.summary", this, new Model(user)));
			ListMultipleChoice roles = new ListMultipleChoice("user.roles", roleManager.findAll(), new ChoiceRenderer("name"));
			add(roles);
		}

		public boolean evaluate() {
			return roleConfiguration;
		}
	}

	private final class ConfirmationStep extends StaticContentStep {
		public ConfirmationStep() {
			super(true);
			IModel userModel = new Model(user);
			setTitleModel(new ResourceModel("confirmation.title"));
			setSummaryModel(new StringResourceModel("confirmation.summary", this, userModel));
			setContentModel(new StringResourceModel("confirmation.content", this, userModel));
		}
	}
}
