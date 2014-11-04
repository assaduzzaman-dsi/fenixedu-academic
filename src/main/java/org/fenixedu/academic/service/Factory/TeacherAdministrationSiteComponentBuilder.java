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
package org.fenixedu.academic.service.Factory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fenixedu.academic.domain.Attends;
import org.fenixedu.academic.domain.ExecutionCourse;
import org.fenixedu.academic.domain.Grouping;
import org.fenixedu.academic.domain.Shift;
import org.fenixedu.academic.domain.StudentGroup;
import org.fenixedu.academic.dto.InfoShift;
import org.fenixedu.academic.dto.InfoSiteGroupsByShift;
import org.fenixedu.academic.dto.InfoSiteShift;
import org.fenixedu.academic.dto.InfoSiteShifts;
import org.fenixedu.academic.dto.InfoSiteShiftsAndGroups;
import org.fenixedu.academic.dto.InfoSiteStudentGroup;
import org.fenixedu.academic.dto.InfoSiteStudentGroupAndStudents;
import org.fenixedu.academic.dto.InfoSiteStudentInformation;
import org.fenixedu.academic.dto.InfoSiteStudentsAndShiftByStudentGroup;
import org.fenixedu.academic.dto.InfoStudentGroup;
import org.fenixedu.academic.dto.InfoStudentGroupWithAttendsAndGroupingAndShift;
import org.fenixedu.academic.service.services.ExcepcaoInexistente;
import org.fenixedu.academic.service.services.exceptions.FenixServiceException;
import org.fenixedu.academic.service.strategy.groupEnrolment.strategys.GroupEnrolmentStrategyFactory;
import org.fenixedu.academic.service.strategy.groupEnrolment.strategys.IGroupEnrolmentStrategy;
import org.fenixedu.academic.service.strategy.groupEnrolment.strategys.IGroupEnrolmentStrategyFactory;

import pt.ist.fenixframework.FenixFramework;

/**
 * @author Fernanda Quit�rio
 * 
 */
@Deprecated
public class TeacherAdministrationSiteComponentBuilder {

    public InfoSiteStudentGroup getInfoSiteStudentGroup(InfoSiteStudentGroup component, String studentGroupID)
            throws FenixServiceException {

        final StudentGroup studentGroup = FenixFramework.getDomainObject(studentGroupID);
        if (studentGroup == null) {
            return null;
        }

        final List<InfoSiteStudentInformation> infoSiteStudentInformations = new ArrayList<InfoSiteStudentInformation>();
        for (final Attends attend : studentGroup.getAttendsSet()) {
            infoSiteStudentInformations.add(new InfoSiteStudentInformation(attend.getRegistration().getPerson().getName(), attend
                    .getRegistration().getPerson().getEmail(), attend.getRegistration().getPerson().getUsername(), attend
                    .getRegistration().getNumber()));
        }
        Collections.sort(infoSiteStudentInformations, InfoSiteStudentInformation.COMPARATOR_BY_NUMBER);
        component.setInfoSiteStudentInformationList(infoSiteStudentInformations);
        component.setInfoStudentGroup(InfoStudentGroupWithAttendsAndGroupingAndShift.newInfoFromDomain(studentGroup));

        if (studentGroup.getGrouping().getMaximumCapacity() != null) {
            int freeGroups = studentGroup.getGrouping().getMaximumCapacity() - studentGroup.getAttendsSet().size();
            component.setNrOfElements(Integer.valueOf(freeGroups));
        } else {
            component.setNrOfElements("Sem limite");
        }
        return component;
    }

    public InfoSiteStudentGroupAndStudents getInfoSiteStudentGroupAndStudents(InfoSiteStudentGroupAndStudents component,
            String groupPropertiesCode, String shiftCode) throws FenixServiceException {
        List infoSiteStudentsAndShiftByStudentGroupList = readStudentGroupAndStudents(groupPropertiesCode, shiftCode);
        component.setInfoSiteStudentsAndShiftByStudentGroupList(infoSiteStudentsAndShiftByStudentGroupList);

        InfoSiteShiftsAndGroups infoSiteShiftsAndGroups = readShiftAndGroups(groupPropertiesCode, shiftCode);
        component.setInfoSiteShiftsAndGroups(infoSiteShiftsAndGroups);
        return component;
    }

    private InfoSiteShiftsAndGroups readShiftAndGroups(String groupPropertiesCode, String shiftCode) throws ExcepcaoInexistente,
            FenixServiceException {

        InfoSiteShiftsAndGroups infoSiteShiftsAndGroups = new InfoSiteShiftsAndGroups();

        Grouping grouping = FenixFramework.getDomainObject(groupPropertiesCode);
        if (grouping == null) {
            return null;
        }

        Shift shift = FenixFramework.getDomainObject(shiftCode);

        List<InfoSiteGroupsByShift> infoSiteGroupsByShiftList = new ArrayList<InfoSiteGroupsByShift>();
        InfoSiteShift infoSiteShift = new InfoSiteShift();
        infoSiteShift.setInfoShift(InfoShift.newInfoFromDomain(shift));

        List allStudentGroups = grouping.readAllStudentGroupsBy(shift);

        if (grouping.getDifferentiatedCapacity()) {
            Integer vagas = shift.getShiftGroupingProperties().getCapacity();
            infoSiteShift.setNrOfGroups(vagas);
        } else {
            if (grouping.getGroupMaximumNumber() != null) {
                int vagas = grouping.getGroupMaximumNumber().intValue() - allStudentGroups.size();
                infoSiteShift.setNrOfGroups(Integer.valueOf(vagas));
            } else {
                infoSiteShift.setNrOfGroups("Sem limite");
            }
        }
        InfoSiteGroupsByShift infoSiteGroupsByShift = new InfoSiteGroupsByShift();
        infoSiteGroupsByShift.setInfoSiteShift(infoSiteShift);

        List<InfoSiteStudentGroup> infoSiteStudentGroupsList = null;
        if (allStudentGroups.size() != 0) {
            infoSiteStudentGroupsList = new ArrayList<InfoSiteStudentGroup>();
            Iterator iterGroups = allStudentGroups.iterator();
            while (iterGroups.hasNext()) {
                InfoSiteStudentGroup infoSiteStudentGroup = new InfoSiteStudentGroup();
                InfoStudentGroup infoStudentGroup = new InfoStudentGroup();
                infoStudentGroup = InfoStudentGroup.newInfoFromDomain((StudentGroup) iterGroups.next());
                infoSiteStudentGroup.setInfoStudentGroup(infoStudentGroup);
                infoSiteStudentGroupsList.add(infoSiteStudentGroup);
            }
            Collections.sort(infoSiteStudentGroupsList, InfoSiteStudentGroup.COMPARATOR_BY_NUMBER);
        }
        infoSiteGroupsByShift.setInfoSiteStudentGroupsList(infoSiteStudentGroupsList);

        infoSiteGroupsByShiftList.add(infoSiteGroupsByShift);
        infoSiteShiftsAndGroups.setInfoSiteGroupsByShiftList(infoSiteGroupsByShiftList);

        return infoSiteShiftsAndGroups;
    }

    private List readStudentGroupAndStudents(String groupPropertiesCode, String shiftCode) throws ExcepcaoInexistente,
            FenixServiceException {

        List<InfoSiteStudentsAndShiftByStudentGroup> infoSiteStudentsAndShiftByStudentGroupList =
                new ArrayList<InfoSiteStudentsAndShiftByStudentGroup>();

        Grouping groupProperties = FenixFramework.getDomainObject(groupPropertiesCode);
        if (groupProperties == null) {
            return null;
        }

        Shift shift = FenixFramework.getDomainObject(shiftCode);

        List<StudentGroup> aux = new ArrayList<StudentGroup>();
        List studentGroupsWithShift = groupProperties.getStudentGroupsWithShift();
        Iterator iterStudentGroupsWithShift = studentGroupsWithShift.iterator();
        while (iterStudentGroupsWithShift.hasNext()) {
            StudentGroup studentGroup = (StudentGroup) iterStudentGroupsWithShift.next();
            if (studentGroup.getShift().equals(shift)) {
                aux.add(studentGroup);
            }
        }
        List<StudentGroup> allStudentGroups = new ArrayList<StudentGroup>();
        allStudentGroups.addAll(groupProperties.getStudentGroupsSet());

        Iterator iterAux = aux.iterator();
        while (iterAux.hasNext()) {
            StudentGroup studentGroup = (StudentGroup) iterAux.next();
            allStudentGroups.remove(studentGroup);
        }

        Iterator iterAllStudentGroups = allStudentGroups.iterator();
        InfoSiteStudentsAndShiftByStudentGroup infoSiteStudentsAndShiftByStudentGroup = null;
        while (iterAllStudentGroups.hasNext()) {
            infoSiteStudentsAndShiftByStudentGroup = new InfoSiteStudentsAndShiftByStudentGroup();

            StudentGroup studentGroup = (StudentGroup) iterAllStudentGroups.next();
            Shift turno = studentGroup.getShift();
            infoSiteStudentsAndShiftByStudentGroup.setInfoStudentGroup(InfoStudentGroup.newInfoFromDomain(studentGroup));
            infoSiteStudentsAndShiftByStudentGroup.setInfoShift(InfoShift.newInfoFromDomain(turno));

            Collection attendsList = studentGroup.getAttendsSet();

            List<InfoSiteStudentInformation> studentGroupAttendInformationList = new ArrayList<InfoSiteStudentInformation>();
            Iterator iterAttendsList = attendsList.iterator();
            InfoSiteStudentInformation infoSiteStudentInformation = null;
            Attends attend = null;

            while (iterAttendsList.hasNext()) {
                infoSiteStudentInformation = new InfoSiteStudentInformation();

                attend = (Attends) iterAttendsList.next();

                infoSiteStudentInformation.setUsername(attend.getRegistration().getPerson().getUsername());
                infoSiteStudentInformation.setNumber(attend.getRegistration().getNumber());

                studentGroupAttendInformationList.add(infoSiteStudentInformation);

            }

            Collections.sort(studentGroupAttendInformationList, InfoSiteStudentInformation.COMPARATOR_BY_NUMBER);

            infoSiteStudentsAndShiftByStudentGroup.setInfoSiteStudentInformationList(studentGroupAttendInformationList);
            infoSiteStudentsAndShiftByStudentGroupList.add(infoSiteStudentsAndShiftByStudentGroup);

            Collections.sort(infoSiteStudentsAndShiftByStudentGroupList,
                    InfoSiteStudentsAndShiftByStudentGroup.COMPARATOR_BY_NUMBER);

        }

        return infoSiteStudentsAndShiftByStudentGroupList;
    }

    /**
     * @param shifts
     * @param site
     * @return
     * @throws ExcepcaoPersistencia
     */
    public InfoSiteShifts getInfoSiteShifts(InfoSiteShifts component, String groupPropertiesCode, String studentGroupCode)
            throws FenixServiceException {
        List<InfoShift> infoShifts = new ArrayList<InfoShift>();
        ExecutionCourse executionCourse = null;

        Grouping groupProperties = FenixFramework.getDomainObject(groupPropertiesCode);
        if (groupProperties == null) {
            return null;
        }
        if (studentGroupCode != null) {
            StudentGroup studentGroup = FenixFramework.getDomainObject(studentGroupCode);
            if (studentGroup == null) {
                component.setShifts(null);
                return component;
            }

            component.setOldShift(InfoShift.newInfoFromDomain(studentGroup.getShift()));
        }

        IGroupEnrolmentStrategyFactory enrolmentGroupPolicyStrategyFactory = GroupEnrolmentStrategyFactory.getInstance();
        IGroupEnrolmentStrategy strategy = enrolmentGroupPolicyStrategyFactory.getGroupEnrolmentStrategyInstance(groupProperties);

        if (strategy.checkHasShift(groupProperties)) {

            List executionCourses = new ArrayList();
            executionCourses = groupProperties.getExecutionCourses();

            Iterator iterExecutionCourses = executionCourses.iterator();
            List<Shift> shifts = new ArrayList<Shift>();
            while (iterExecutionCourses.hasNext()) {
                ExecutionCourse executionCourse2 = (ExecutionCourse) iterExecutionCourses.next();
                Set<Shift> someShifts = executionCourse2.getAssociatedShifts();

                shifts.addAll(someShifts);
            }

            if (shifts == null || shifts.isEmpty()) {

            } else {
                for (int i = 0; i < shifts.size(); i++) {
                    Shift shift = shifts.get(i);
                    if (strategy.checkShiftType(groupProperties, shift)) {
                        executionCourse = shift.getDisciplinaExecucao();
                        InfoShift infoShift = new InfoShift(shift);
                        infoShifts.add(infoShift);
                    }
                }
            }
        }

        component.setShifts(infoShifts);

        return component;
    }
}