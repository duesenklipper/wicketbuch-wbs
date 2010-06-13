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

package com.senacor.wbs.web.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.apache.wicket.model.Model;

public class TestDateFormatModel extends TestCase {
	public void testDateFormat() throws Exception {
		DateFormatModel dfn = new DateFormatModel(new Model(new Date()), new SimpleDateFormat("dd.MM.yyyy"));
		assertNotNull(dfn);
		assertTrue(dfn.getObject() instanceof String);
		System.out.println(dfn.getObject());
		dfn.setObject("01.04.2008");
		assertEquals("01.04.2008", dfn.getObject());
	}
}
