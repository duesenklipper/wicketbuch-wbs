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

package com.senacor.core.hibernate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.hibernate.collection.AbstractPersistentCollection;
import org.hibernate.collection.PersistentCollection;

import com.senacor.core.bo.BusinessObject;

/**
 * Entfernt alle Hibernate Lazy Loading Maps und Collections aus dem
 * Rückgabeobjekt
 * 
 * @author osiefart
 */
public class RemoveHibernateLazyLoadingProxiesInterceptor implements MethodInterceptor {
	@SuppressWarnings("unchecked")
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object returnObject = invocation.proceed();
		if (isBusinessObject(returnObject)) {
			removeHibernateProxy((BusinessObject) returnObject);
		} else if (isCollection(returnObject)) {
			removeHibernateProxy((Collection) returnObject);
		}
		return returnObject;
	}

	/**
	 * True, wenn das übergebene Objekt vom Typ BusinessObject ist
	 * 
	 * @param object
	 * @return
	 */
	private boolean isBusinessObject(Object object) {
		return (object instanceof BusinessObject);
	}

	/**
	 * True, wenn das übergebene Objekt vom Typ Collection ist
	 * 
	 * @param object
	 * @return
	 */
	private boolean isCollection(Object object) {
		return (object instanceof Collection);
	}

	/**
	 * True, wenn der Typ des Fields eine Collection ist
	 * 
	 * @param field
	 * @return
	 */
	private boolean isCollection(Field field) {
		return (Collection.class.isAssignableFrom(field.getType()));
	}

	/**
	 * True, wenn die Instanz om Objekt vom Typ PersistentCollection ist
	 * 
	 * @param field
	 * @param businessObject
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private boolean isPersistentCollection(Field field, BusinessObject businessObject) throws IllegalArgumentException, IllegalAccessException {
		Object object = field.get(businessObject);
		return (object instanceof PersistentCollection);
	}

	/**
	 * True, wenn der Typ des Fields eine List ist
	 * 
	 * @param field
	 * @return
	 */
	private boolean isList(Field field) {
		return List.class.isAssignableFrom(field.getType());
	}

	/**
	 * True, wenn der Typ des Fields eine Map ist
	 * 
	 * @param field
	 * @return
	 */
	private boolean isMap(Field field) {
		return Map.class.isAssignableFrom(field.getType());
	}

	/**
	 * True, wenn der Typ des Fields ein Set ist
	 * 
	 * @param field
	 * @return
	 */
	private boolean isSet(Field field) {
		return Set.class.isAssignableFrom(field.getType());
	}

	/**
	 * Iteriert über die Collection und ersetzt in allen Objekten
	 * Lazy-Collections / Maps durch richtige Collection / Maps
	 * 
	 * @param collection
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private void removeHibernateProxy(Collection collection) throws IllegalAccessException {
		for (Object object : collection) {
			if (isBusinessObject(object)) {
				removeHibernateProxy((BusinessObject) object);
			}
		}
	}

	/**
	 * Iteriert durch alle Attribute des Objekts (unter aller Unterobjekte) und
	 * ersetzt Lazy-Collections / Maps durch richtige Collection / Maps
	 * 
	 * @param businessObject
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private void removeHibernateProxy(BusinessObject businessObject) throws IllegalAccessException {
		Field[] fields = getFields(businessObject.getClass());
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			field.setAccessible(true);
			if (isPersistentCollection(field, businessObject)) {
				if (isMap(field)) {
					convertMap(field, businessObject);
				} else if (isCollection(field)) {
					convertCollection(field, businessObject);
				}
				convertCollection(field, businessObject);
				removeHibernateProxy((Collection) field.get(businessObject));
			}
		}
	}

	/**
	 * Liefert alle Attribute der Klasse samt Oberklassen
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Field[] getFields(Class clazz) {
		Field[] fields = new Field[] {};
		fields = clazz.getDeclaredFields();
		if (!clazz.getSuperclass().equals(BusinessObject.class)) {
			Field[] addFields = getFields(clazz.getSuperclass());
			Field[] tmp = new Field[fields.length + addFields.length];
			System.arraycopy(fields, 0, tmp, 0, fields.length);
			System.arraycopy(addFields, 0, tmp, fields.length, addFields.length);
			fields = tmp;
		}
		return fields;
	}

	/**
	 * Erzeugt eine neuen Collection und überträgt alle Objekte in sie, wenn die
	 * vorherige LazyCollection initialisiert war
	 * 
	 * @param field
	 * @param object
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private void convertCollection(Field field, Object object) throws IllegalAccessException {
		Collection collection = (Collection) field.get(object);
		Collection newCollection = createCollection(field);
		newCollection.addAll(collection);
		field.set(object, newCollection);
	}

	/**
	 * Erzeugt eine neuen Map und überträgt alle Objekte in sie, wenn die
	 * vorherige LazyMap initialisiert war
	 * 
	 * @param field
	 * @param object
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private void convertMap(Field field, Object object) throws IllegalAccessException {
		Map map = (Map) field.get(object);
		Map newMap = createMap(field);
		if (((AbstractPersistentCollection) object).wasInitialized()) {
			newMap.putAll(map);
		}
		field.set(object, newMap);
	}

	/**
	 * Wenn der Typ des Fields eine List ist, wird eine Arraylist zurückgegeben.
	 * 
	 * Wenn der Typ des Fields ein Set ist, wird ein HashSet zurückgegeben.
	 * 
	 * Ansonsten null.
	 * 
	 * @param field
	 * @return
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private Collection createCollection(Field field) throws IllegalAccessException {
		if (isList(field)) {
			return new ArrayList();
		} else if (isSet(field)) {
			return new HashSet();
		}
		return null;
	}

	/**
	 * Wenn der Typ des Fields eine Map ist, wird eine HashMap zurückgegeben,
	 * ansonsten null.
	 * 
	 * @param field
	 * @return
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	private Map createMap(Field field) throws IllegalAccessException {
		if (isMap(field)) {
			return new HashMap();
		}
		return null;
	}
}
