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

import org.apache.wicket.datetime.markup.html.form.DateTextField;
import org.apache.wicket.extensions.ajax.markup.html.form.upload.UploadProgressBar;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.PackageResource;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.resource.ByteArrayResource;
import org.apache.wicket.security.components.markup.html.form.SecureForm;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.lang.Bytes;

import com.senacor.domain.user.Gender;
import com.senacor.domain.user.Knowledge;
import com.senacor.domain.user.MaritalStatus;
import com.senacor.domain.user.User;
import com.senacor.domain.user.UserManager;
import com.senacor.wbs.web.border.ToggleBorder;
import com.senacor.wbs.web.core.EnumChoiceRenderer;
import com.senacor.wbs.web.core.security.WBSSession;

public class EditUserPanel extends Panel {
	@SpringBean
	private UserManager userManager;
	private UserForm userForm;

	public EditUserPanel(String id) {
		super(id);
		User user = userManager.retrieve(WBSSession.get().getUser().getId());
		add(new ToggleBorder("border", new Model("Passwort ändern")).add(new ChangePasswordPanel("changePasswordPanel", user, false)));
		userForm = new UserForm("userForm", user);
		add(new ToggleBorder("border2", new Model("Stammdaten ändern")).add(userForm));
	}

	public class UserForm extends SecureForm {
		private EditContactsPanel editContactDataPanel;
		private FileUploadField fotoUploadField = new FileUploadField("fotoUpload", new Model<FileUpload>());
		private transient FileUpload fotoUpload;
		private Image foto;

		public UserForm(final String componentName, final User user) {
			super(componentName, new CompoundPropertyModel(user));
			ToggleBorder borderLeft = new ToggleBorder("borderPerson1", new Model("Persönliche Daten"));
			add(borderLeft);
			borderLeft.add(new TextField("name").setRequired(true));
			borderLeft.add(new TextField("vorname").setRequired(true));
			// Beispiel für das Arbeiten mit Datumsangaben
			borderLeft.add(DateTextField.forShortStyle("birthDate").add(new DatePicker()));
			// Beispiel für Options
			borderLeft.add(new RadioChoice("gender", Arrays.asList(Gender.values()), new EnumChoiceRenderer()));
			// Beispiel für ListChoice
			borderLeft.add(new ListChoice("maritalStatus", Arrays.asList(MaritalStatus.values()), new EnumChoiceRenderer()));
			ToggleBorder borderRight = new ToggleBorder("borderPerson2", new Model("Mitarbeiterdaten"));
			add(borderRight);
			foto = new Image("foto", PackageResource.get(EditUserPanel.class, "photoPlaceholder.jpg").setCacheable(false));
			borderRight.add(foto);
			// Beispiel für File-Upload
			borderRight.add(fotoUploadField);
			// Beispiel für eine Checkbox
			borderRight.add(new CheckBox("senacorEmployee"));
			// Beispiel für CheckBoxMultipleChoice
			borderRight.add(new CheckBoxMultipleChoice("knowledges", Arrays.asList(Knowledge.values()), new EnumChoiceRenderer()));
			// Fileupload funktioniert nur mit Multipart-Requests
			setMultiPart(true);
			// File-Groesse kann pro Form oder global beschraenkt
			// werden
			setMaxSize(Bytes.megabytes(50));
			// default:
			// getApplication().getApplicationSettings().getDefaultMaximumUploadSize();
			add(new UploadProgressBar("progress", UserForm.this));
			add(new EditAddressPanel("addressPanel", user.getAddress()));
			editContactDataPanel = new EditContactsPanel("contactDataPanel", user.getContactData());
			add(editContactDataPanel);
			add(new Button("Speichern"));
		}

		@Override
		protected void onSubmit() {
			super.onSubmit();
			User user = (User) getModelObject();
			fotoUpload = fotoUploadField.getFileUpload();
			if (fotoUpload != null) {
				foto.setImageResource(new ByteArrayResource(fotoUpload.getContentType(), fotoUpload.getBytes()).setCacheable(false));
			}
			userManager.saveOrUpdate(user);
		}
	}

	public EditContactsPanel getEditContactDataPanel() {
		return userForm.editContactDataPanel;
	}
}
