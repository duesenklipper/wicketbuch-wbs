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

package com.senacor.wbs.web.border;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebMarkupContainer;

public class ContentLayout extends WebMarkupContainer {
	private static final long serialVersionUID = 1L;

	public ContentLayout(String id) {
		super(id);
		add(CSSPackageResource.getHeaderContribution(getClass(), getClass().getSimpleName() + ".css"));
	}

	@Override
	protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		// Muss ein DIV-Container sein
		checkComponentTag(tag, "div");
		// Start YAML SubTemplate
		tag.getAttributes().put("class", "subcolumns");
	}
}
