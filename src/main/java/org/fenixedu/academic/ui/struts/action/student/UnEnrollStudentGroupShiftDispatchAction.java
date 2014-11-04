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
 * Created on 13/Nov/2004
 *
 */
package org.fenixedu.academic.ui.struts.action.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.fenixedu.academic.service.services.exceptions.ExistingServiceException;
import org.fenixedu.academic.service.services.exceptions.FenixServiceException;
import org.fenixedu.academic.service.services.exceptions.InvalidArgumentsServiceException;
import org.fenixedu.academic.service.services.exceptions.InvalidChangeServiceException;
import org.fenixedu.academic.service.services.exceptions.InvalidSituationServiceException;
import org.fenixedu.academic.service.services.exceptions.InvalidStudentNumberServiceException;
import org.fenixedu.academic.service.services.exceptions.NotAuthorizedException;
import org.fenixedu.academic.service.services.student.UnEnrollGroupShift;
import org.fenixedu.academic.ui.struts.action.base.FenixDispatchAction;
import org.fenixedu.academic.ui.struts.action.exceptions.FenixActionException;
import org.fenixedu.bennu.core.domain.User;
import org.fenixedu.bennu.struts.annotations.Forward;
import org.fenixedu.bennu.struts.annotations.Forwards;
import org.fenixedu.bennu.struts.annotations.Mapping;

/**
 * @author joaosa and rmalo
 * 
 */
@Mapping(module = "student", path = "/unEnrollStudentGroupShift", formBean = "groupEnrolmentForm",
        functionality = ViewEnroledExecutionCoursesAction.class)
@Forwards(value = { @Forward(name = "insucess", path = "/student/viewEnroledExecutionCourses.do?method=prepare"),
        @Forward(name = "viewStudentGroupInformation", path = "/student/viewStudentGroupInformation.do"),
        @Forward(name = "viewShiftsAndGroups", path = "/student/viewShiftsAndGroups.do"),
        @Forward(name = "viewExecutionCourseProjects", path = "/student/viewExecutionCourseProjects.do") })
public class UnEnrollStudentGroupShiftDispatchAction extends FenixDispatchAction {

    public ActionForward unEnrollStudentGroupShift(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws FenixActionException {

        User userView = getUserView(request);
        String studentGroupCodeString = request.getParameter("studentGroupCode");

        try {
            UnEnrollGroupShift.run(studentGroupCodeString, studentGroupCodeString, userView.getUsername());
        } catch (NotAuthorizedException e) {
            ActionErrors actionErrors2 = new ActionErrors();
            ActionError error2 = null;
            error2 = new ActionError("errors.noStudentInAttendsSet");
            actionErrors2.add("errors.noStudentInAttendsSet", error2);
            saveErrors(request, actionErrors2);
            return mapping.findForward("insucess");
        } catch (ExistingServiceException e) {
            ActionErrors actionErrors = new ActionErrors();
            ActionError error = null;
            error = new ActionError("error.noProject");
            actionErrors.add("error.noProject", error);
            saveErrors(request, actionErrors);
            return mapping.findForward("viewExecutionCourseProjects");
        } catch (InvalidArgumentsServiceException e) {
            ActionErrors actionErrors = new ActionErrors();
            ActionError error = null;
            error = new ActionError("error.noGroup");
            actionErrors.add("error.noGroup", error);
            saveErrors(request, actionErrors);
            return mapping.findForward("viewShiftsAndGroups");

        } catch (InvalidSituationServiceException e) {
            ActionErrors actionErrors = new ActionErrors();
            ActionError error = null;
            error = new ActionError("errors.unEnrollStudentGroupShift.notEnroled");
            actionErrors.add("errors.unEnrollStudentGroupShift.notEnroled", error);
            saveErrors(request, actionErrors);
            return mapping.findForward("viewStudentGroupInformation");

        } catch (InvalidChangeServiceException e) {
            ActionErrors actionErrors3 = new ActionErrors();
            ActionError error3 = null;
            error3 = new ActionError("errors.unEnrollStudentGroupShift.shiftFull");
            actionErrors3.add("errors.unEnrollStudentGroupShift.shiftFull", error3);
            saveErrors(request, actionErrors3);
            return mapping.findForward("viewStudentGroupInformation");

        } catch (InvalidStudentNumberServiceException e) {
            ActionErrors actionErrors3 = new ActionErrors();
            ActionError error3 = null;
            error3 = new ActionError("error.UnEnrollStudentGroupShift");
            actionErrors3.add("error.UnEnrollStudentGroupShift", error3);
            saveErrors(request, actionErrors3);
            return mapping.findForward("viewShiftsAndGroups");

        } catch (FenixServiceException e) {
            throw new FenixActionException(e);
        }
        return mapping.findForward("viewShiftsAndGroups");
    }
}