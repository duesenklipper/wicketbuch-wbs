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

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.TestPanelSource;
import org.apache.wicket.util.tester.WicketTester;

import com.senacor.domain.project.Project;
import com.senacor.domain.project.ProjectManagerImpl;
import com.senacor.wbs.web.core.layout.menu.SecondLevelMenu;
import com.senacor.wbs.web.project.ProjectOverviewPage;
import com.senacor.wbs.web.project.ProjectStatisticsPage;

public class LinkNavigationTest extends TestCase {
	private WicketTester tester;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// Eine frische Testumgebung starten
		tester = new WicketTester();
	}

	public void testLinkInPage() throws Exception {
		ProjectOverviewPage page = new ProjectOverviewPage(new ProjectManagerImpl() {
			@Override
			public List<Project> findAll() {
				return new ArrayList<Project>();
			}
		});
		tester.startPage(page);
		tester.debugComponentTrees();
		tester.clickLink("leftColumn:listPanel:projects:links:1:link");
		tester.assertRenderedPage(ProjectStatisticsPage.class);
	}

	public void testLinkInPanel() throws Exception {
		tester.startPanel(new TestPanelSource() {
			public Panel getTestPanel(final String panelId) {
				final SecondLevelMenu menu = new SecondLevelMenu(panelId);
				menu.addMenuLink(new Model("foo"), ProjectStatisticsPage.class);
				return menu;
			}
		});
		tester.debugComponentTrees();
		tester.clickLink("panel:links:0:link");
		tester.assertRenderedPage(ProjectStatisticsPage.class);
	}
}
