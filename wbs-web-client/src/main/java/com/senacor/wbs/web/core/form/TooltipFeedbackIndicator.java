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

package com.senacor.wbs.web.core.form;

import org.apache.wicket.Component;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.behavior.AbstractBehavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderContributor;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;

public class TooltipFeedbackIndicator extends AbstractBehavior implements IHeaderContributor {
	@Override
	public void onComponentTag(Component component, ComponentTag tag) {
		super.onComponentTag(component, tag);
		if (component.hasErrorMessage()) {
			tag.put("class", "fieldError");
			tag.put("showtooltip", "true");
			tag.put("title", component.getFeedbackMessage().getMessage().toString());
		} else {
			tag.remove("class");
			tag.remove("showtooltip");
			tag.remove("title");
		}
	}

	public void renderHead(IHeaderResponse response) {
		response.renderJavascriptReference(new JavascriptResourceReference(TooltipFeedbackIndicator.class, "addEvent.js"));
		response.renderJavascriptReference(new JavascriptResourceReference(TooltipFeedbackIndicator.class, "sweetTitles.js"));
		response.renderCSSReference(new ResourceReference(TooltipFeedbackIndicator.class, "sweetTitles.css"));
	}
}
