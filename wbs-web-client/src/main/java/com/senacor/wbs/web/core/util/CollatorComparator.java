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

/**
 * 
 */
package com.senacor.wbs.web.core.util;

import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

public class CollatorComparator<T> implements Comparator<T> {
	Collator collator;

	public CollatorComparator(Locale locale) {
		collator = Collator.getInstance(locale);
	}

	public int compare(T o1, T o2) {
		assert o1 instanceof String;
		assert o2 instanceof String;
		return collator.compare(o1, o2);
	}
}