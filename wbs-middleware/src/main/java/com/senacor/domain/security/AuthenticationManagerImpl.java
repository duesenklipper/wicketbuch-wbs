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

package com.senacor.domain.security;

import org.hibernate.Session;

import com.senacor.core.manager.ManagerImpl;
import com.senacor.domain.user.User;

public class AuthenticationManagerImpl extends ManagerImpl implements AuthenticationManager {
	public User login(String userName, String passwort) {
		Session session = sessionFactory.getCurrentSession();
		User user = (User) session.createQuery("from User user where user.username=:userName and user.passwort=:passwort").setParameter("userName", userName)
				.setParameter("passwort", passwort).uniqueResult();
		if (user == null) {
			throw new RuntimeException("Fehlerhafter Login");
		}
		return user;
	}
}
