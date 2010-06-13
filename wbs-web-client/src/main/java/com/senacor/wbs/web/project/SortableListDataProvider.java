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

package com.senacor.wbs.web.project;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.springframework.util.Assert;

import com.senacor.wbs.web.core.util.SortUtils;

@SuppressWarnings("serial")
public abstract class SortableListDataProvider<E> extends SortableDataProvider {
	private final List<E> data;

	public SortableListDataProvider(final List<E> data) {
		Assert.notNull(data, "argument [list] cannot be null");
		this.data = data;
	}

	public Iterator<E> iterator(final int first, final int count) {
		final SortParam sp = getSort();
		SortUtils.sort(data, sp.getProperty(), sp.isAscending(), getLocale());
		int toIndex = first + count;
		if (toIndex > data.size()) {
			toIndex = data.size();
		}
		return data.subList(first, toIndex).listIterator();
	}

	public int size() {
		return data.size();
	}

	protected Locale getLocale() {
		return Locale.getDefault();
	}
}
