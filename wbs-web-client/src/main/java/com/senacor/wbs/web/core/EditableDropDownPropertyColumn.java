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

import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.column.editable.EditableCellPanel;
import com.inmethod.grid.column.editable.EditablePropertyColumn;

public class EditableDropDownPropertyColumn extends EditablePropertyColumn {
	private List choices;
	private IChoiceRenderer choiceRenderer;

	public EditableDropDownPropertyColumn(IModel headerModel, String propertyExpression) {
		super(headerModel, propertyExpression);
	}

	public EditableDropDownPropertyColumn(IModel headerModel, String propertyExpression, String sortProperty, List choices, IChoiceRenderer choiceRenderer) {
		super(headerModel, propertyExpression, sortProperty);
		this.choices = choices;
		this.choiceRenderer = choiceRenderer;
	}

	public EditableDropDownPropertyColumn(String columnId, IModel headerModel, String propertyExpression, String sortProperty) {
		super(columnId, headerModel, propertyExpression, sortProperty);
	}

	public EditableDropDownPropertyColumn(String columnId, IModel headerModel, String propertyExpression) {
		super(columnId, headerModel, propertyExpression);
	}

	protected EditableCellPanel newCellPanel(String componentId, IModel rowModel, IModel cellModel) {
		return new DropDownChoicePanel(componentId, cellModel, choices, choiceRenderer, rowModel, this);
	}
}
