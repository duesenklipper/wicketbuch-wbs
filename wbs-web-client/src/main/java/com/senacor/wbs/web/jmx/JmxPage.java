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

package com.senacor.wbs.web.jmx;

import org.wicketstuff.jmx.markup.html.JmxPanel;
import org.wicketstuff.jmx.util.IDomainFilter;

import com.senacor.wbs.web.core.layout.SecureBaseWBSPage;

public class JmxPage extends SecureBaseWBSPage {
	public JmxPage() {
		middleColumn.getListPanel().add(new JmxPanel(middleColumn.getListPanel().newChildId()) {
			protected IDomainFilter getDomainFilter() {
				return ALL_DOMAINS;
			}
		});
	}

	private final IDomainFilter ALL_DOMAINS = new IDomainFilter() {
		public boolean accept(String domain) {
			return true;
		}
	};
}