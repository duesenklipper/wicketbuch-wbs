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

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.IValidationError;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.PatternValidator;

import com.senacor.domain.user.Address;
import com.senacor.domain.user.Country;
import com.senacor.wbs.web.border.ContentLayout;
import com.senacor.wbs.web.border.LayoutColumn;
import com.senacor.wbs.web.border.ToggleBorder;
import com.senacor.wbs.web.border.LayoutColumn.ColumnType;
import com.senacor.wbs.web.border.LayoutColumn.Position;
import com.senacor.wbs.web.core.EnumChoiceRenderer;

/**
 * @author Olaf Siefart
 */
public class EditAddressPanel extends Panel {
	public EditAddressPanel(final String id, final Address address) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<Address>(address));
		final ToggleBorder border = new ToggleBorder("addressBorder", new Model("Adressdaten"));
		final Form addressForm = new Form("addressForm");
		final ContentLayout mainLayout = new ContentLayout("mainLayout");
		addressForm.add(mainLayout);
		final LayoutColumn mainLeft = new LayoutColumn("colMainLeft", ColumnType.c50l, Position.LEFT);
		mainLayout.add(mainLeft);
		final ContentLayout inputLayout1l = new ContentLayout("inputLayout1");
		mainLeft.add(inputLayout1l);
		final LayoutColumn colLabel1l = new LayoutColumn("colLabel", ColumnType.c33l, Position.LEFT);
		inputLayout1l.add(colLabel1l);
		final LayoutColumn colField1l = new LayoutColumn("colField", ColumnType.c66r, Position.RIGHT);
		inputLayout1l.add(colField1l);
		colField1l.add(new TextField("street"));
		final ContentLayout inputLayout2l = new ContentLayout("inputLayout2");
		mainLeft.add(inputLayout2l);
		final LayoutColumn colLabel2l = new LayoutColumn("colLabel", ColumnType.c33l, Position.LEFT);
		inputLayout2l.add(colLabel2l);
		final LayoutColumn colField2l = new LayoutColumn("colField", ColumnType.c66r, Position.RIGHT);
		inputLayout2l.add(colField2l);
		final TextField zipCode = new TextField("zipcode");
		colField2l.add(zipCode);
		final ContentLayout inputLayout3l = new ContentLayout("inputLayout3");
		mainLeft.add(inputLayout3l);
		final LayoutColumn colLabel3l = new LayoutColumn("colLabel", ColumnType.c33l, Position.LEFT);
		inputLayout3l.add(colLabel3l);
		final LayoutColumn colField3l = new LayoutColumn("colField", ColumnType.c66r, Position.RIGHT);
		inputLayout3l.add(colField3l);
		final DropDownChoice country = new DropDownChoice("country", Arrays.asList(Country.values()), new EnumChoiceRenderer());
		colField3l.add(country);
		final LayoutColumn mainRight = new LayoutColumn("colMainRight", ColumnType.c50r, Position.RIGHT);
		mainLayout.add(mainRight);
		final ContentLayout inputLayout1r = new ContentLayout("inputLayout1");
		mainRight.add(inputLayout1r);
		final LayoutColumn colLabel1r = new LayoutColumn("colLabel", ColumnType.c33l, Position.LEFT);
		inputLayout1r.add(colLabel1r);
		final LayoutColumn colField1r = new LayoutColumn("colField", ColumnType.c66r, Position.RIGHT);
		inputLayout1r.add(colField1r);
		colField1r.add(new TextField("houseNr"));
		final ContentLayout inputLayout2r = new ContentLayout("inputLayout2");
		mainRight.add(inputLayout2r);
		final LayoutColumn colLabel2r = new LayoutColumn("colLabel", ColumnType.c33l, Position.LEFT);
		inputLayout2r.add(colLabel2r);
		final LayoutColumn colField2r = new LayoutColumn("colField", ColumnType.c66r, Position.RIGHT);
		inputLayout2r.add(colField2r);
		colField2r.add(new TextField("town"));
		addressForm.add(new ZipCodeValidator(zipCode, country));
		border.add(addressForm);
		add(border);
	}

	/**
	 * Validierung der Postleitzahl in Abhängigkeit des Lands. Ist das Land
	 * Deutschland, so muss die Postleitzahl numerisch und 5-stellig sein.
	 * 
	 * @author fmito045
	 */
	private class ZipCodeValidator extends AbstractFormValidator {
		private final DropDownChoice countryFC;
		private final TextField zipCodeFC;

		private ZipCodeValidator(final TextField zipCode, final DropDownChoice country) {
			this.zipCodeFC = zipCode;
			this.countryFC = country;
		}

		public FormComponent[] getDependentFormComponents() {
			return new FormComponent[] { zipCodeFC, countryFC };
		}

		public void validate(final Form form) {
			final Country country = (Country) countryFC.getConvertedInput();
			if (Country.Germany.equals(country)) {
				final IValidatable validatable = new ValidatableAdapter(zipCodeFC);
				if (validatable.getValue() == null) {
					validatable.error(new ValidationError().addMessageKey("Required"));
				} else {
					new PatternValidator("[1-9][0-9][0-9][0-9][0-9]").validate(validatable);
				}
			}
		}
	}
}

class ValidatableAdapter implements IValidatable {
	private final FormComponent formComponent;

	public ValidatableAdapter(final FormComponent formComponent) {
		this.formComponent = formComponent;
	}

	public void error(final IValidationError error) {
		formComponent.error(error);
	}

	public Object getValue() {
		return formComponent.getConvertedInput();
	}

	public boolean isValid() {
		return formComponent.isValid();
	}
}
