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

package com.senacor.wbs.web.project;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

import com.senacor.wbs.web.core.EnumChoiceRenderer;

/**
 * Panel mit einer drop down box die automatisch submitted, wenn der focus nicht
 * mehr gesetzt ist
 * 
 * @author Olaf Siefart
 */
public class DropDownBoxPanel extends Panel {
	public DropDownBoxPanel(String id, IModel inputModel, List values) {
		super(id);
		DropDownChoice dropDownChoice = new DropDownChoice("dropDown", inputModel, values, new EnumChoiceRenderer());
		add(dropDownChoice);
		dropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onblur") {
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
	}
}