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

import junit.framework.TestCase;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.tester.DummyHomePage;
import org.apache.wicket.util.tester.TestPanelSource;
import org.apache.wicket.util.tester.WicketTester;

import com.senacor.wbs.web.core.layout.menu.SecondLevelMenu;

public class TestSecondLevelMenu extends TestCase {
	private WicketTester tester;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		// Eine frische Testumgebung starten
		tester = new WicketTester();
	}

	public void testSingleLink() throws Exception {
		// Tester mit unserem Panel ausstatten
		tester.startPanel(new TestPanelSource() {
			public Panel getTestPanel(final String panelId) {
				// Die übergebene ID verwenden, damit das Panel
				// später gefunden werden kann
				SecondLevelMenu panel = new SecondLevelMenu(panelId);
				// Link auf eine Dummy-Seite
				panel.addMenuLink(new Model("title"), DummyHomePage.class);
				return panel;
			}
		});
		// Klick auf den Link simulieren...
		tester.clickLink("panel:links:0:link");
		// ..und prüfen, daß die gewünschte Seite gerendert
		// wurde:
		tester.assertRenderedPage(DummyHomePage.class);
	}
}
