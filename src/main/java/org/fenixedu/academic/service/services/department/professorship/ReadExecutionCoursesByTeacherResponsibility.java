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
/*
 * Created on Aug 26, 2004
 *
 */
package org.fenixedu.academic.service.services.department.professorship;

import java.util.ArrayList;
import java.util.List;

import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.Professorship;
import org.fenixedu.academic.domain.Teacher;
import org.fenixedu.academic.dto.InfoExecutionCourse;
import org.fenixedu.academic.service.services.exceptions.FenixServiceException;

import pt.ist.fenixframework.Atomic;

/**
 * @author João Mota
 * 
 */
public class ReadExecutionCoursesByTeacherResponsibility {

    @Atomic
    public static List run(String id) throws FenixServiceException {

        final List<InfoExecutionCourse> infoExecutionCourses = new ArrayList<InfoExecutionCourse>();
        Person person = Person.readPersonByUsername(id);
        if (person.getTeacher() != null) {
            Teacher teacher = person.getTeacher();

            final List<Professorship> responsibilities = teacher.responsibleFors();

            if (responsibilities != null) {
                for (final Professorship professorship : responsibilities) {
                    infoExecutionCourses.add(InfoExecutionCourse.newInfoFromDomain(professorship.getExecutionCourse()));
                }
            }
            return infoExecutionCourses;
        } else {
            return new ArrayList<Professorship>();
        }
    }
}