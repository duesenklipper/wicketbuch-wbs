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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public class SecondLevelMenu extends Panel {
	private final List<Link> links = new ArrayList<Link>();

	public SecondLevelMenu(String id) {
		super(id);
		add(new ListView("links", links) {
			protected void populateItem(ListItem item) {
				Link link = (Link) item.getModelObject();
				item.add(link);
			}
		});
	}

	public void addMenuLink(IModel title, Class<? extends WebPage> target) {
		BookmarkablePageLink pageLink = new BookmarkablePageLink("link", target);
		pageLink.add(new Label("title", title));
		links.add(pageLink);
	}
}
