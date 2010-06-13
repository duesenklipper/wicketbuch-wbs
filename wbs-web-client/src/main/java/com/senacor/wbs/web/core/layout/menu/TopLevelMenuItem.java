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

package com.senacor.wbs.web.core.layout.menu;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.security.checks.LinkSecurityCheck;
import org.apache.wicket.security.components.markup.html.panel.SecurePanel;

public class TopLevelMenuItem<T extends WebPage> extends SecurePanel {
	public TopLevelMenuItem(IModel<String> title, final Class<T> pageClass) {
		super("topLevelMenuItem");
		Link<T> pageLink = new BookmarkablePageLink<T>("link", pageClass) {
			@Override
			protected void onComponentTag(ComponentTag tag) {
				super.onComponentTag(tag);
				if ("span".equals(tag.getName())) {
					tag.setName("div");
					tag.getAttributes().put("class", "menuItemLink");
				}
			}
		};
		pageLink.add(CSSPackageResource.getHeaderContribution(this.getClass(), "TopLevelMenuItem.css"));
		pageLink.add(new Label("title", title));
		add(pageLink);
		setSecurityCheck(new LinkSecurityCheck(this, pageClass));
	}
}
