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

import java.util.Locale;

import junit.framework.TestCase;

import org.apache.wicket.util.convert.ConversionException;

import com.senacor.domain.project.ProjectState;

public class TestEnumConverter extends TestCase {
	private EnumConverter ec;

	@Override
	protected void setUp() throws Exception {
		ec = new EnumConverter(ProjectState.class);
	}

	public void testConvertToObject() throws Exception {
		Object obj = ec.convertToObject("In Development", Locale.getDefault());
		assertTrue(obj instanceof ProjectState);
		assertEquals(ProjectState.IN_DEVELOPMENT, obj);
		obj = ec.convertToObject(" Final ", Locale.getDefault());
		assertEquals(ProjectState.FINAL, obj);
		try {
			obj = ec.convertToObject("Unknown911", Locale.getDefault());
			fail("ConversionException expected here");
		} catch (ConversionException ex) {
			// ok
		} catch (Exception ex) {
			fail("ConversionException expected, but was: " + ex);
		}
	}

	public void testConvertToString() throws Exception {
		String str = ec.convertToString(ProjectState.IN_DEVELOPMENT, Locale.getDefault());
		assertEquals("In Development", str);
		str = ec.convertToString(ProjectState.FINAL, Locale.getDefault());
		assertEquals("Final", str);
	}
}
