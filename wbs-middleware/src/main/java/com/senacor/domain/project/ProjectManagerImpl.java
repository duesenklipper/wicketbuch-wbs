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

package com.senacor.domain.project;

import java.util.ArrayList;
import java.util.List;

import com.senacor.core.manager.IndexBaseManager;

public class ProjectManagerImpl extends IndexBaseManager<Project> implements ProjectManager {
	public List<Integer> getLast30DaysOpenTaskCount() {
		List<Integer> result = new ArrayList<Integer>(30);
		Integer prev = new Integer((int) (Math.random() * 50));
		for (int i = 0; i < 30; i++) {
			int add = (int) (Math.random() * 20);
			if (Math.random() > 0.5) {
				add = -1 * add;
			}
			Integer val = prev + add;
			if (val > 200) {
				val = 180;
			}
			if (val < 0) {
				val = 0;
			}
			result.add(val);
			prev = val;
		}
		return result;
	}
}
