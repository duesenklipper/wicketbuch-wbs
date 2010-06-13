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

import org.apache.wicket.markup.html.pages.AccessDeniedPage;
import org.apache.wicket.util.tester.WicketTester;

import com.senacor.wbs.web.user.ListRolesPage;

public class TestAccessControl extends TestCase {
	private WicketTester tester;

	@Override
	public void setUp() throws Exception {
		super.setUp();
		// Unsere eigene Application-Klasse instantiieren:
		WorkBreakdownStructureApplication application = new WorkBreakdownStructureApplication(false) {
			// Für jeden Test einen eigenen HiveKey
			// verwenden:
			@Override
			protected Object getHiveKey() {
				return super.getHiveKey() + "" + TestAccessControl.this.getName();
			}
		};
		// Tester mit unserer Applikation starten
		tester = new WicketTester(application);
	}

	/**
	 * Prüft, daß ohne Login nur die AccessDeniedPage kommt.
	 */
	public void testAccessControl() throws Exception {
		// Erste Seite aufrufen:
		tester.startPage(ListRolesPage.class);
		// Verifizieren, daß AccessDenied kam:
		tester.assertRenderedPage(AccessDeniedPage.class);
	}
}
