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

package com.senacor.wbs.web.core.layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.MarkupContainer;
import org.apache.wicket.devutils.debugbar.DebugBar;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.feedback.IFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;

import com.senacor.wbs.web.StartPage;
import com.senacor.wbs.web.core.layout.menu.Menu;
import com.senacor.wbs.web.core.layout.menu.TopLevelMenuItem;
import com.senacor.wbs.web.gmap.GMapsPage;
import com.senacor.wbs.web.jmx.JmxPage;
import com.senacor.wbs.web.project.ProjectOverviewPage;
import com.senacor.wbs.web.user.EditUserPage;
import com.senacor.wbs.web.user.UserAdminPage;

public abstract class BaseWBSPage extends WebPage {
	protected final LeftColumn leftColumn;
	protected final Content middleColumn;
	// protected final RightColumn rightColumn;
	protected final Menu menu;
	private final GobalFeedbackMessageFilter gobalFeedbackMessageFilter;

	public BaseWBSPage() {
		add(new DebugBar("debug"));
		add(new Header("header", new ResourceModel("wbs.title"), new ResourceModel("wbs.subtitle")));
		menu = new Menu("menu");
		// Menü zur Startseite
		menu.addTopLevelMenuItem(new TopLevelMenuItem<StartPage>(new ResourceModel("menu.home.label"), StartPage.class));
		// Menü zur Benutzerverwaltung
		menu.addTopLevelMenuItem(new TopLevelMenuItem<UserAdminPage>(new ResourceModel("menu.usermanagement.label"), UserAdminPage.class));
		// Menü zur Projektverwaltung
		menu.addTopLevelMenuItem(new TopLevelMenuItem<ProjectOverviewPage>(new ResourceModel("menu.projectmanagement.label"), ProjectOverviewPage.class));
		// Menü zur Bearbeitung der eigenen Einstellungen
		menu.addTopLevelMenuItem(new TopLevelMenuItem<EditUserPage>(new ResourceModel("menu.settings.label"), EditUserPage.class));
		// Menü für die Google Map Integration
		menu.addTopLevelMenuItem(new TopLevelMenuItem<GMapsPage>(new Model<String>("Google Maps"), GMapsPage.class));
		// Menü für die JMX Integration
		menu.addTopLevelMenuItem(new TopLevelMenuItem<JmxPage>(new Model("JMX Konfiguration"), JmxPage.class));
		// menu
		// .addTopLevelMenuItem(new
		// TopLevelMenuItem<CreateUserPanelizedPage>(
		// new Model("User anlegen 2"),
		// CreateUserPanelizedPage.class));
		// Menü hinzufügen
		add(menu);
		leftColumn = new LeftColumn("leftColumn");
		middleColumn = new Content("middleColumn");
		gobalFeedbackMessageFilter = new GobalFeedbackMessageFilter();
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedbackPanel", gobalFeedbackMessageFilter);
		middleColumn.getListPanel().add(feedbackPanel);
		// rightColumn = new RightColumn("rightColumn");
		add(leftColumn);
		add(middleColumn);
		// add(rightColumn);
		add(new Footer("footer"));
	}

	protected void excludeContainerFromFeedbackPanel(final MarkupContainer markupContainer) {
		gobalFeedbackMessageFilter.excludeContainer(markupContainer);
	}
}

class GobalFeedbackMessageFilter implements IFeedbackMessageFilter {
	private final List<MarkupContainer> containerList = new ArrayList<MarkupContainer>();

	public GobalFeedbackMessageFilter() {
	}

	public GobalFeedbackMessageFilter(final MarkupContainer[] container) {
		if (container == null) {
			throw new IllegalArgumentException("container must be not null");
		}
		containerList.addAll(Arrays.asList(container));
	}

	public boolean accept(final FeedbackMessage message) {
		if (message.getReporter() == null) {
			return false;
		}
		for (MarkupContainer container : containerList) {
			if (container.contains(message.getReporter(), true) || (container == message.getReporter())) {
				return false;
			}
		}
		return true;
	}

	public void excludeContainer(final MarkupContainer markupContainer) {
		containerList.add(markupContainer);
	}
}
