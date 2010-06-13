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

package com.senacor.core.manager;

import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;

import com.senacor.core.bo.BusinessObject;

public class IndexBaseManager<T extends BusinessObject> extends BaseManagerImpl<T> {
	public void initializeIndex() {
		// Ramp up des Indexes
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.createFullTextSession(session);
		List<T> list = findAll();
		if (!list.isEmpty()) {
			T tElement = list.get(0);
			fullTextSession.purgeAll(tElement.getClass());
			for (T t : list) {
				fullTextSession.index(t);
			}
			fullTextSession.getSearchFactory().optimize(tElement.getClass());
		}
	}

	protected List<T> findPerIndex(String searchText, String[] fields) {
		try {
			Session session = sessionFactory.getCurrentSession();
			FullTextSession fullTextSession = Search.createFullTextSession(session);
			MultiFieldQueryParser parser = new MultiFieldQueryParser(fields, new StandardAnalyzer());
			Query query = parser.parse(searchText);
			org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, boClass);
			List<T> result = hibQuery.list();
			return result;
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
