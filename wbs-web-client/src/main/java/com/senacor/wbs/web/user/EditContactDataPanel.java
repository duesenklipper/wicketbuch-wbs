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

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidator;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;

import com.senacor.domain.user.ContactData;

public class EditContactDataPanel extends Panel {
	private VorwahlService vorwahlService = new VorwahlService() {
		public boolean validateVorwahl(String vorwahl) {
			if (vorwahl.startsWith("0")) {
				return true;
			}
			return false;
		}
	};

	public EditContactDataPanel(String id, ContactData cd) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<ContactData>(cd));
		Form cdf = new Form("contactDataForm");
		cdf.add(new TextField("telephone").setRequired(true).add(new PatternValidator("[0-9]*[-][0-9]*")).add(new VorwahlValidator()).add(
				new SimpleAttributeModifier("class", "fieldError") {
					@Override
					public void onComponentTag(Component component, ComponentTag tag) {
						if (component instanceof FormComponent) {
							FormComponent formComponent = (FormComponent) component;
							if (!formComponent.isValid()) {
								super.onComponentTag(component, tag);
							}
						}
					}
				}));
		cdf.add(new TextField("mobile").add(new PatternValidator("[0-9-]*")));
		cdf.add(new TextField("email").add(EmailAddressValidator.getInstance()));
		add(cdf);
	}

	class VorwahlValidator implements IValidator {
		public void validate(IValidatable validatable) {
			String vorwahl = StringUtils.split((String) validatable.getValue(), "-")[0];
			if (!vorwahlService.validateVorwahl(vorwahl)) {
				validatable.error(new ValidationError().addMessageKey("NotAValidVorwahl"));
			}
		}
	}
}

interface VorwahlService extends Serializable {
	boolean validateVorwahl(String vorwahl);
}