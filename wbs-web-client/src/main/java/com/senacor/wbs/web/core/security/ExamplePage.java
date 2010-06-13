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

package com.senacor.wbs.web.core.security;

import org.apache.wicket.Component;
import org.apache.wicket.util.lang.EnumeratedType;
import org.apache.wicket.util.string.Strings;

import com.senacor.domain.security.AuthenticationManager;
import com.senacor.domain.user.User;
import com.senacor.wbs.web.core.layout.BaseWBSPage;

// TODO: Verlagern in spezeille ExampleApplikation
public class ExamplePage extends BaseWBSPage {
	AuthenticationManager authenticationManager = null;

	public ExamplePage() {
		User user = authenticationManager.login("ss", "dd");
		MyWBSSession.get().setUser(user);
	}

	public interface IAuthorizationStrategy {
		boolean isInstantiationAuthorized(Class componentClass);

		boolean isActionAuthorized(Component component, Action action);

		public static final IAuthorizationStrategy ALLOW_ALL = new IAuthorizationStrategy() {
			public boolean isActionAuthorized(final Component c, final Action action) {
				return true;
			}

			public boolean isInstantiationAuthorized(final Class c) {
				return true;
			}
		};
	}

	public class Action extends EnumeratedType {
		public static final String RENDER = "RENDER";
		public static final String ENABLE = "ENABLE";
		private static final long serialVersionUID = -1L;
		/** The name of this action. */
		private final String name;

		public Action(final String name) {
			super(name);
			if (Strings.isEmpty(name)) {
				throw new IllegalArgumentException("Name argument may not be null, whitespace or the empty string");
			}
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
