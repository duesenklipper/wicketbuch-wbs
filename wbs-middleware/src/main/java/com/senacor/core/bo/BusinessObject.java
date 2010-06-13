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

package com.senacor.core.bo;

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class BusinessObject implements Serializable {
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof BusinessObject) {
			BusinessObject businessObject = (BusinessObject) obj;
			if (businessObject.getId() != null && businessObject.getId() > 0) {
				return new EqualsBuilder().append(businessObject.getId(), this.getId()).isEquals();
			} else {
				super.equals(obj);
			}
		}
		return false;
	}

	@Override
	public int hashCode() {
		if (getId() != null && getId() > 0) {
			return new HashCodeBuilder().append(getId()).toHashCode();
		} else {
			return super.hashCode();
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public abstract Long getId();

	public abstract void setId(Long id);
}
