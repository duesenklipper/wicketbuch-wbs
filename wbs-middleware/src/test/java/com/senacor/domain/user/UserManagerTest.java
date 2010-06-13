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

package com.senacor.domain.user;

import java.util.List;

import com.senacor.core.manager.ManagerTest;

public class UserManagerTest extends ManagerTest<UserManager> {
	public void testCreateUser() {
		User user = new User();
		user.setName("Siefart");
		user.setVorname("Olaf");
		user.setUsername("osiefart");
		user.getContactData();
		manager.save(user);
		assertNotNull(user.getId());
		user = manager.retrieve(user.getId());
		assertNotNull(user.getId());
		ContactData[] data = new ContactData[2];
		user.getContactData().toArray(data);
		assertNotNull(data[0].getId());
		assertNotNull(data[1].getId());
		assertNotNull(user.getAddress().getId());
		manager.delete(user);
	}

	public void testAdminUser() {
		User user = manager.retrieve(1);
		assertEquals(1, user.getRoles().size());
		assertEquals(4, user.getRoles().iterator().next().getPrincipals().size());
	}

	public void testFindPerIndex() {
		User user = new User();
		user.setName("Siefert");
		user.setVorname("Olaf");
		user.setUsername("osiefert");
		manager.save(user);
		assertNotNull(user.getId());
		List<User> users = manager.findPerIndex("sief*");
		assertNotNull(users);
		for (User u : users) {
			System.out.println(u);
		}
		assertEquals(2, users.size());
		// more index attributes
		users = manager.findPerIndex("sief* rfoer* carl-eric");
		assertNotNull(users);
		assertEquals(4, users.size());
		manager.delete(user);
	}
}
