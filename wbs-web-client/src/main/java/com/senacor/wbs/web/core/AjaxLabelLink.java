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

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxFallbackLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public abstract class AjaxLabelLink extends Panel {
	public AjaxLabelLink(String id, final IModel label) {
		super(id, label);
		setOutputMarkupId(true);
		add(new AjaxFallbackLink("link") {
			@Override
			public void onClick(AjaxRequestTarget target) {
				AjaxLabelLink.this.onClick(target);
			}
		}.add(new Label("label", label)));
	}

	public AjaxLabelLink(String id, String label) {
		this(id, new Model(label));
	}

	protected abstract void onClick(AjaxRequestTarget target);
}
