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

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.apache.wicket.model.IModel;

public class DateFormatModel implements IModel {
	private IModel dateModel;
	private DateFormat sf;

	public DateFormatModel(IModel dateModel, DateFormat sf) {
		super();
		this.dateModel = dateModel;
		this.sf = sf;
		assert sf != null;
		assert dateModel.getObject() instanceof Date;
	}

	public Object getObject() {
		return sf.format(dateModel.getObject());
	}

	public void setObject(Object object) {
		try {
			dateModel.setObject(sf.parseObject((String) object));
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public void detach() {
	}
}
