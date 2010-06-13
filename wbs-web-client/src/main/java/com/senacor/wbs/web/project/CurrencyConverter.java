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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;

import org.apache.wicket.util.convert.converters.BigDecimalConverter;

public class CurrencyConverter extends BigDecimalConverter {
	private static HashMap<Locale, BigDecimal> EXCHANGE_RATES = new HashMap<Locale, BigDecimal>();
	static {
		EXCHANGE_RATES.put(Locale.GERMANY, new BigDecimal("1"));
		EXCHANGE_RATES.put(Locale.GERMAN, new BigDecimal("1"));
		EXCHANGE_RATES.put(Locale.US, new BigDecimal("1.4428"));
	}

	public CurrencyConverter() {
		super();
	}

	public BigDecimal convertToObject(String value, Locale locale) {
		BigDecimal numValue = (BigDecimal) super.convertToObject(value, locale);
		return numValue.divide(EXCHANGE_RATES.get(locale), RoundingMode.HALF_EVEN);
	}

	public String convertToString(Object value, Locale locale) {
		BigDecimal numValue = ((BigDecimal) value).multiply(EXCHANGE_RATES.get(locale));
		return super.convertToString(numValue, locale);
	}

	/**
	 * @param locale
	 * @return Returns the numberFormat.
	 */
	public NumberFormat getNumberFormat(Locale locale) {
		return NumberFormat.getCurrencyInstance(locale);
	}
}
