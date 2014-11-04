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
/**
 * 
 */
package org.fenixedu.academic.predicate;

import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.degreeStructure.CourseGroup;
import org.fenixedu.academic.domain.person.RoleType;

/**
 * @author - Shezad Anavarali (shezad@ist.utl.pt)
 * 
 */
public class CourseGroupPredicates {

    public static final AccessControlPredicate<CourseGroup> curricularPlanMemberWritePredicate =
            new AccessControlPredicate<CourseGroup>() {

                @Override
                public boolean evaluate(CourseGroup cg) {

                    final DegreeCurricularPlan parentDegreeCurricularPlan = cg.getParentDegreeCurricularPlan();
                    if (!parentDegreeCurricularPlan.isBolonhaDegree()) {
                        return true;
                    }

                    final Person person = AccessControl.getPerson();
                    if (person.hasRole(RoleType.SCIENTIFIC_COUNCIL) || person.hasRole(RoleType.MANAGER)
                            || person.hasRole(RoleType.DEGREE_ADMINISTRATIVE_OFFICE_SUPER_USER)
                            || person.hasRole(RoleType.OPERATOR)) {
                        return true;
                    }

                    return parentDegreeCurricularPlan.getCurricularPlanMembersGroup().isMember(person.getUser());
                }

            };

}
