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

package com.senacor.wbs.web;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.senacor.domain.user.User;
import com.senacor.wbs.web.core.security.WBSSession;

public class WelcomePanel extends Panel {
	public WelcomePanel(String id) {
		super(id);
		setDefaultModel(new CompoundPropertyModel<User>(WBSSession.get().getUser()));
		add(new Label("welcomeLabel", new ResourceModel("welcomeMessage")));
		add(new Label("vorname"));
		add(new Label("name"));
	}
}
