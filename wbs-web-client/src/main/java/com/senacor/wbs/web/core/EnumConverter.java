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

import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.wicket.util.convert.converters.AbstractConverter;

public class EnumConverter extends AbstractConverter {
	private final Class enumType;

	public EnumConverter(Class enumType) {
		this.enumType = enumType;
	}

	public Object convertToObject(String value, Locale locale) {
		String upper = value.toUpperCase(locale).trim();
		String enumName = StringUtils.replace(upper, " ", "_");
		try {
			return Enum.valueOf(enumType, enumName);
		} catch (IllegalArgumentException ex) {
			throw newConversionException("Cannot parse '" + value + "' using enum type " + enumType.getName(), value, locale);
		}
	}

	public String convertToString(Object value, Locale locale) {
		Enum enumValue = (Enum) value;
		String enumName = enumValue.name();
		String words = StringUtils.replace(enumName, "_", " ");
		return WordUtils.capitalizeFully(words);
	}

	@Override
	protected Class getTargetType() {
		return enumType;
	}
}
