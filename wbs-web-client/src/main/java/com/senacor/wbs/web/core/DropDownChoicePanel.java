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

package com.senacor.wbs.web.core;

import java.util.List;

import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.column.AbstractColumn;
import com.inmethod.grid.column.editable.EditableCellPanel;

public class DropDownChoicePanel extends EditableCellPanel {
	public DropDownChoicePanel(String id, final IModel model, List choices, IChoiceRenderer choiceRenderer, IModel rowModel, AbstractColumn column) {
		super(id, column, rowModel);
		DropDownChoice ddc = new DropDownChoice("dropdownchoice", model, choices, choiceRenderer) {
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				if (isValid() == false) {
					tag.put("class", "imxt-invalid");
					FeedbackMessage message = getFeedbackMessage();
					if (message != null) {
						tag.put("title", message.getMessage().toString());
					}
				}
			}
		};
		ddc.setOutputMarkupId(true);
		ddc.setLabel(column.getHeaderModel());
		add(ddc);
	}

	@Override
	protected FormComponent getEditComponent() {
		return (FormComponent) get("dropdownchoice");
	}
}
