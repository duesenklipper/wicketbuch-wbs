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

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

public class Menu extends Panel {
	private final List<TopLevelMenuItem> topLevelMenuItemList = new ArrayList<TopLevelMenuItem>();

	public Menu(String id) {
		super(id);
		setRenderBodyOnly(true);
		add(new ListView("topLevelMenuItemList", topLevelMenuItemList) {
			protected void populateItem(ListItem item) {
				TopLevelMenuItem menuItem = (TopLevelMenuItem) item.getModelObject();
				item.add(menuItem);
			}
		});
	}

	public void addTopLevelMenuItem(TopLevelMenuItem item) {
		topLevelMenuItemList.add(item);
	}
}
