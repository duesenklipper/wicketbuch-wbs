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

package com.senacor.wbs.web.core.util;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeanWrapperImpl;

public class SortUtils {
	@SuppressWarnings("unchecked")
	public static void sort(List beanCollection, final String sortAttribute, final boolean ascending, final Locale locale) {
		Comparator comparator = new LocaleAwareComparator(sortAttribute, ascending, locale);
		Collections.sort(beanCollection, comparator);
	}

	static class LocaleAwareComparator implements Comparator {
		private String sortAttribute;
		private boolean ascending;
		private Locale locale;

		public LocaleAwareComparator(String sortAttribute, boolean ascending, Locale locale) {
			this.sortAttribute = sortAttribute;
			this.ascending = ascending;
			this.locale = locale;
		}

		public int compare(Object o1, Object o2) {
			if (!ascending) {
				Object tmp = o1;
				o1 = o2;
				o2 = tmp;
			}
			if (o1 == null && o2 == null) {
				return 0;
			} else if (o1 == null) {
				return -1;
			} else if (o2 == null) {
				return 1;
			}
			BeanWrapperImpl wrapper1 = new BeanWrapperImpl(o1);
			BeanWrapperImpl wrapper2 = new BeanWrapperImpl(o2);
			Object prop1 = wrapper1.getPropertyValue(sortAttribute);
			Object prop2 = wrapper2.getPropertyValue(sortAttribute);
			if (prop1 == null && prop2 == null) {
				return 0;
			} else if (prop1 == null) {
				return -1;
			} else if (prop2 == null) {
				return 1;
			}
			assert prop1 instanceof Comparable;
			assert prop2 instanceof Comparable;
			if (prop1 instanceof String && prop2 instanceof String && "de".equals(locale.getLanguage())) {
				// für die Sprache Deutsch verwenden wir
				// Collator, um die lexikografische Ordnung
				// zu berücksichtigen.
				Collator collator = Collator.getInstance(locale);
				String c1 = (String) prop1;
				String c2 = (String) prop2;
				return collator.compare(c1, c2);
			} else {
				Comparable c1 = (Comparable) prop1;
				Comparable c2 = (Comparable) prop2;
				return c1.compareTo(c2);
			}
		}
	}
}
