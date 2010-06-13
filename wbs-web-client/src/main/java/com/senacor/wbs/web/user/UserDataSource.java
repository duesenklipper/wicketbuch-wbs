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

package com.senacor.wbs.web.user;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;

import com.inmethod.grid.IDataSource;
import com.inmethod.grid.IGridSortState;
import com.inmethod.grid.IGridSortState.ISortStateColumn;
import com.senacor.domain.user.User;
import com.senacor.wbs.web.core.util.SortUtils;

public class UserDataSource implements IDataSource {
	private IModel userDataModel;

	public UserDataSource(IModel data) {
		this.userDataModel = data;
	}

	public IModel model(Object obj) {
		return new CompoundPropertyModel(obj);
	}

	public void query(IQuery iquery, IQueryResult iqueryresult) {
		List<User> result = (List<User>) userDataModel.getObject();
		IGridSortState sortState = iquery.getSortState();
		if (!sortState.getColumns().isEmpty()) {
			ISortStateColumn column = sortState.getColumns().get(0);
			String propertyName = column.getPropertyName();
			IGridSortState.Direction direction = column.getDirection();
			SortUtils.sort(result, propertyName, IGridSortState.Direction.ASC.equals(direction), Session.get().getLocale());
		}
		iqueryresult.setTotalCount(result.size());
		List<User> pageResult = result.subList(iquery.getFrom(), iquery.getFrom() + iquery.getCount());
		iqueryresult.setItems(pageResult.iterator());
	}

	public void detach() {
	}
}