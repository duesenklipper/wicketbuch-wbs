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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.TabbedPanel;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.senacor.domain.user.ContactData;
import com.senacor.wbs.web.border.ToggleBorder;

public class EditContactsPanel extends Panel {
	private TabbedPanel panel;
	private FeedbackPanel feedbackPanel;

	public EditContactsPanel(String id, Set<ContactData> cd) {
		super(id);
		final ContactData[] data = new ContactData[2];
		cd.toArray(data);
		ToggleBorder border = new ToggleBorder("contactBorder", new Model("Kontaktdaten"));
		feedbackPanel = new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(this));
		feedbackPanel.setOutputMarkupPlaceholderTag(true);
		border.add(feedbackPanel);
		// Anzeige über ein TabbedPanel
		Form editContactsForm = new Form("editContactsForm");
		List<ITab> tabs = new ArrayList<ITab>();
		tabs.add(new AbstractTab(new Model("Privat")) {
			public Panel getPanel(String panelId) {
				return new EditContactDataPanel(panelId, data[0]);
			}
		});
		tabs.add(new AbstractTab(new Model("Geschäftlich")) {
			public Panel getPanel(String panelId) {
				return new EditContactDataPanel(panelId, data[1]);
			}
		});
		panel = new TabbedPanel("contactDataPanel", tabs) {
			@Override
			protected WebMarkupContainer newLink(String linkId, final int index) {
				return new AjaxSubmitLink(linkId) {
					@Override
					protected void onError(AjaxRequestTarget target, Form form) {
						target.addComponent(feedbackPanel);
					}

					@Override
					protected void onSubmit(AjaxRequestTarget target, Form form) {
						setSelectedTab(index);
						target.addComponent(EditContactsPanel.this);
					}
				};
			}
		};
		editContactsForm.add(panel);
		border.add(editContactsForm);
		add(border);
		setOutputMarkupId(true);
	}
}