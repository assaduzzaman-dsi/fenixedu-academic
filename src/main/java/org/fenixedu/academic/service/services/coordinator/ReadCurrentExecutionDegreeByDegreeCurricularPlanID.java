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
package org.fenixedu.academic.service.services.coordinator;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.ExecutionDegree;
import org.fenixedu.academic.dto.InfoExecutionDegree;
import org.fenixedu.academic.service.filter.CoordinatorAuthorizationFilter;
import org.fenixedu.academic.service.filter.ManagerAuthorizationFilter;
import org.fenixedu.academic.service.filter.MasterDegreeAdministrativeOfficeAuthorizationFilter;
import org.fenixedu.academic.service.services.exceptions.NotAuthorizedException;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

public class ReadCurrentExecutionDegreeByDegreeCurricularPlanID {

    protected InfoExecutionDegree run(final String degreeCurricularPlanID) {

        final DegreeCurricularPlan degreeCurricularPlan = FenixFramework.getDomainObject(degreeCurricularPlanID);

        final Collection executionDegrees = degreeCurricularPlan.getExecutionDegreesSet();
        final ExecutionDegree executionDegree = (ExecutionDegree) CollectionUtils.find(executionDegrees, new Predicate() {
            @Override
            public boolean evaluate(Object arg0) {
                final ExecutionDegree executionDegree = (ExecutionDegree) arg0;
                return executionDegree.getExecutionYear().isCurrent();
            }
        });

        return InfoExecutionDegree.newInfoFromDomain(executionDegree);
    }

    // Service Invokers migrated from Berserk

    private static final ReadCurrentExecutionDegreeByDegreeCurricularPlanID serviceInstance =
            new ReadCurrentExecutionDegreeByDegreeCurricularPlanID();

    @Atomic
    public static InfoExecutionDegree runReadCurrentExecutionDegreeByDegreeCurricularPlanID(String degreeCurricularPlanID)
            throws NotAuthorizedException {
        try {
            ManagerAuthorizationFilter.instance.execute();
            return serviceInstance.run(degreeCurricularPlanID);
        } catch (NotAuthorizedException ex1) {
            try {
                MasterDegreeAdministrativeOfficeAuthorizationFilter.instance.execute();
                return serviceInstance.run(degreeCurricularPlanID);
            } catch (NotAuthorizedException ex2) {
                try {
                    CoordinatorAuthorizationFilter.instance.execute();
                    return serviceInstance.run(degreeCurricularPlanID);
                } catch (NotAuthorizedException ex3) {
                    throw ex3;
                }
            }
        }
    }

}