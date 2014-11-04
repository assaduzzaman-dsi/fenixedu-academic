/**
 * Copyright © 2002 Instituto Superior Técnico
 *
 * This file is part of FenixEdu Academic.
 *
 * FenixEdu Academic is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * FenixEdu Academic is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.fenixedu.academic.service.services.commons;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.PropertyUtils;
import org.fenixedu.academic.domain.ExecutionCourse;
import org.fenixedu.academic.domain.ExecutionSemester;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.bennu.core.presentationTier.renderers.autoCompleteProvider.AutoCompleteProvider;
import org.fenixedu.commons.StringNormalizer;

public class SearchCurrentExecutionCourses implements AutoCompleteProvider<ExecutionCourse> {

    @Override
    public Collection<ExecutionCourse> getSearchResults(Map<String, String> argsMap, String value, int maxCount) {
        List<ExecutionCourse> result = new ArrayList<ExecutionCourse>();

        String slotName = argsMap.get("slot");
        Collection<ExecutionCourse> objects = ExecutionSemester.readActualExecutionSemester().getAssociatedExecutionCoursesSet();

        if (value == null) {
            result.addAll(objects);
        } else {
            String[] values = StringNormalizer.normalize(value).split("\\p{Space}+");

            outter: for (ExecutionCourse object : objects) {
                try {
                    Object objectValue = PropertyUtils.getProperty(object, slotName);

                    if (objectValue == null) {
                        continue;
                    }

                    String normalizedValue = StringNormalizer.normalize(objectValue.toString());

                    for (int i = 0; i < values.length; i++) {
                        String part = values[i];

                        if (!normalizedValue.contains(part)) {
                            continue outter;
                        }
                    }

                    result.add(object);

                    if (result.size() >= maxCount) {
                        break;
                    }

                } catch (IllegalAccessException e) {
                    throw new DomainException("searchObject.type.notFound", e);
                } catch (InvocationTargetException e) {
                    throw new DomainException("searchObject.failed.read", e);
                } catch (NoSuchMethodException e) {
                    throw new DomainException("searchObject.failed.read", e);
                }
            }
        }

        Collections.sort(result, new BeanComparator(slotName));
        return result;
    }

}
