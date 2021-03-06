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
package org.fenixedu.academic.domain.candidacyProcess.mobility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.fenixedu.academic.domain.CurricularCourse;
import org.fenixedu.academic.domain.Degree;
import org.fenixedu.academic.domain.DegreeCurricularPlan;
import org.fenixedu.academic.domain.ExecutionSemester;
import org.fenixedu.academic.domain.ExecutionYear;
import org.fenixedu.academic.domain.Person;
import org.fenixedu.academic.domain.StudentCurricularPlan;
import org.fenixedu.academic.domain.candidacy.Ingression;
import org.fenixedu.academic.domain.candidacyProcess.IndividualCandidacyProcess;
import org.fenixedu.academic.domain.candidacyProcess.IndividualCandidacyProcessBean;
import org.fenixedu.academic.domain.candidacyProcess.erasmus.ApprovedLearningAgreementDocumentFile;
import org.fenixedu.academic.domain.candidacyProcess.erasmus.NationalIdCardAvoidanceQuestion;
import org.fenixedu.academic.domain.curricularRules.executors.ruleExecutors.CurricularRuleLevel;
import org.fenixedu.academic.domain.degreeStructure.Context;
import org.fenixedu.academic.domain.degreeStructure.CycleType;
import org.fenixedu.academic.domain.enrolment.DegreeModuleToEnrol;
import org.fenixedu.academic.domain.enrolment.IDegreeModuleToEvaluate;
import org.fenixedu.academic.domain.exceptions.DomainException;
import org.fenixedu.academic.domain.student.PersonalIngressionData;
import org.fenixedu.academic.domain.student.PrecedentDegreeInformation;
import org.fenixedu.academic.domain.student.Registration;
import org.fenixedu.academic.domain.studentCurriculum.CurriculumGroup;
import org.fenixedu.academic.domain.studentCurriculum.NoCourseGroupCurriculumGroup;
import org.fenixedu.academic.domain.studentCurriculum.NoCourseGroupCurriculumGroupType;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

public class MobilityIndividualApplication extends MobilityIndividualApplication_Base {

    public MobilityIndividualApplication() {
        super();
    }

    MobilityIndividualApplication(final MobilityIndividualApplicationProcess process,
            final MobilityIndividualApplicationProcessBean bean) {
        this();

        Person person = init(bean, process);

        createEramusStudentData(bean);

        associateCurricularCourses(bean.getSelectedCurricularCourses());
    }

    private void associateCurricularCourses(Set<CurricularCourse> selectedCurricularCourses) {
        for (CurricularCourse curricularCourse : selectedCurricularCourses) {
            addCurricularCourses(curricularCourse);
        }
    }

    private void createEramusStudentData(MobilityIndividualApplicationProcessBean bean) {
        setMobilityStudentData(new MobilityStudentData(this, bean.getMobilityStudentDataBean(), bean.determineMobilityQuota()));
    }

    @Override
    protected void createDebt(final Person person) {

    }

    @Override
    protected void checkParameters(final Person person, final IndividualCandidacyProcess process,
            final IndividualCandidacyProcessBean bean) {
        MobilityIndividualApplicationProcess erasmusIndividualCandidacyProcess = (MobilityIndividualApplicationProcess) process;
        MobilityIndividualApplicationProcessBean secondCandidacyProcessBean = (MobilityIndividualApplicationProcessBean) bean;
        LocalDate candidacyDate = bean.getCandidacyDate();

        checkParameters(person, erasmusIndividualCandidacyProcess, candidacyDate, null);
    }

    private void checkParameters(final Person person, final MobilityIndividualApplicationProcess process,
            final LocalDate candidacyDate, Object dummy) {

        checkParameters(person, process, candidacyDate);

        /*
         * 31/03/2009 - The candidacy may be submited externally hence may not
         * be associated to a person
         * 
         * 
         * if(person.hasValidSecondCycleIndividualCandidacy(process.
         * getCandidacyExecutionInterval())) { throw newDomainException(
         * "error.SecondCycleIndividualCandidacy.person.already.has.candidacy",
         * process .getCandidacyExecutionInterval().getName()); }
         */
    }

    void editDegreeAndCoursesInformation(MobilityIndividualApplicationProcessBean bean) {
        Set<CurricularCourse> setOne = new HashSet<CurricularCourse>(this.getCurricularCoursesSet());
        setOne.addAll(bean.getSelectedCurricularCourses());

        getMobilityStudentData().setSelectedOpening(bean.determineMobilityQuota());

        for (CurricularCourse curricularCourse : setOne) {
            if (getCurricularCoursesSet().contains(curricularCourse)
                    && !bean.getSelectedCurricularCourses().contains(curricularCourse)) {
                removeCurricularCourses(curricularCourse);
            } else if (!getCurricularCoursesSet().contains(curricularCourse)
                    && bean.getSelectedCurricularCourses().contains(curricularCourse)) {
                addCurricularCourses(curricularCourse);
            }
        }
    }

    public Degree getSelectedDegree() {
        return getMobilityStudentData().getSelectedOpening().getDegree();
    }

    protected boolean hasSelectedDegree() {
        return getSelectedDegree() != null;
    }

    @Override
    public Collection<Degree> getAllDegrees() {
        List<Degree> result = new ArrayList<Degree>();
        result.add(getSelectedDegree());
        return result;
    }

    @Override
    public String getDescription() {
        return getCandidacyProcess().getDisplayName() + (hasSelectedDegree() ? ": " + getSelectedDegree().getNameI18N() : "");
    }

    @Override
    public MobilityIndividualApplicationProcess getCandidacyProcess() {
        return (MobilityIndividualApplicationProcess) super.getCandidacyProcess();
    }

    public ApprovedLearningAgreementDocumentFile getMostRecentApprovedLearningAgreement() {
        if (!hasAnyActiveApprovedLearningAgreements()) {
            return null;
        }

        List<ApprovedLearningAgreementDocumentFile> approvedLearningAgreement =
                new ArrayList<ApprovedLearningAgreementDocumentFile>(getActiveApprovedLearningAgreements());

        Collections.sort(approvedLearningAgreement,
                Collections.reverseOrder(ApprovedLearningAgreementDocumentFile.SUBMISSION_DATE_COMPARATOR));

        return approvedLearningAgreement.iterator().next();
    }

    public boolean isMostRecentApprovedLearningAgreementNotViewed() {
        if (!hasAnyActiveApprovedLearningAgreements()) {
            return false;
        }

        return !getMostRecentApprovedLearningAgreement().isApprovedLearningAgreementViewed();
    }

    boolean hasProcessWithAcceptNotification() {
        return hasProcessWithAcceptNotificationAtDate(new DateTime());
    }

    boolean hasProcessWithAcceptNotificationAtDate(final DateTime dateTime) {
        return getMostRecentApprovedLearningAgreement() == null
                || (getMostRecentApprovedLearningAgreement().getMostRecentSentEmailAcceptedStudentAction() != null && getMostRecentApprovedLearningAgreement()
                        .getMostRecentSentEmailAcceptedStudentAction().getWhenOccured().isBefore(dateTime));
    }

    public List<ApprovedLearningAgreementDocumentFile> getActiveApprovedLearningAgreements() {
        List<ApprovedLearningAgreementDocumentFile> activeDocuments = new ArrayList<ApprovedLearningAgreementDocumentFile>();
        CollectionUtils.select(getApprovedLearningAgreementsSet(), new Predicate() {

            @Override
            public boolean evaluate(Object arg0) {
                ApprovedLearningAgreementDocumentFile document = (ApprovedLearningAgreementDocumentFile) arg0;
                return document.getCandidacyFileActive();
            }

        }, activeDocuments);

        return activeDocuments;
    }

    public boolean hasAnyActiveApprovedLearningAgreements() {
        return !getActiveApprovedLearningAgreements().isEmpty();
    }

    @Override
    public Registration createRegistration(final DegreeCurricularPlan degreeCurricularPlan, final CycleType cycleType,
            final Ingression ingression) {

        if (getRegistration() != null) {
            throw new DomainException("error.IndividualCandidacy.person.with.registration",
                    degreeCurricularPlan.getPresentationName());
        }

        if (hasActiveRegistration(degreeCurricularPlan)) {
            final Registration registration = getStudent().getActiveRegistrationFor(degreeCurricularPlan);
            setRegistration(registration);

            ExecutionYear currentYear = ExecutionYear.readCurrentExecutionYear();
            PersonalIngressionData pid = getStudent().getPersonalIngressionDataByExecutionYear(currentYear);
            pid.setCountryOfResidence(getPersonalDetails().getCountryOfResidence());
            PrecedentDegreeInformation pdi = registration.getPrecedentDegreeInformation(currentYear);
            pdi.setSchoolLevel(getMobilityStudentData().getSchoolLevel());
            pdi.setOtherSchoolLevel(getMobilityStudentData().getOtherSchoolLevel());

            return registration;
        }

        getPersonalDetails().ensurePersonInternalization();
        return createRegistration(getPersonalDetails().getPerson(), degreeCurricularPlan, cycleType, ingression);
    }

    @Override
    protected Registration createRegistration(final Person person, final DegreeCurricularPlan degreeCurricularPlan,
            final CycleType cycleType, final Ingression ingression) {

        final Registration registration =
                new Registration(person, degreeCurricularPlan, getMobilityProgram().getRegistrationProtocol(), cycleType,
                        ((ExecutionYear) getCandidacyExecutionInterval()));

        // Standalone group will be necessary for minor subjects
        NoCourseGroupCurriculumGroup.create(NoCourseGroupCurriculumGroupType.STANDALONE, registration
                .getActiveStudentCurricularPlan().getRoot());

        registration.editStartDates(getStartDate(), registration.getHomologationDate(), registration.getStudiesStartDate());
        setRegistration(registration);

        createRaidesInformation(registration);
        PersonalIngressionData pid = getStudent().getPersonalIngressionDataByExecutionYear(registration.getStartExecutionYear());
        pid.setCountryOfResidence(getPersonalDetails().getCountryOfResidence());
        PrecedentDegreeInformation pdi = registration.getPrecedentDegreeInformation(registration.getStartExecutionYear());
        pdi.setSchoolLevel(getMobilityStudentData().getSchoolLevel());
        pdi.setOtherSchoolLevel(getMobilityStudentData().getOtherSchoolLevel());

        return registration;
    }

    void enrol() {
        final Registration registration = getRegistration();
        final ExecutionYear executionYear = (ExecutionYear) getCandidacyExecutionInterval();
        final ExecutionSemester semesterToEnrol = executionYear.getFirstExecutionPeriod();

        Set<IDegreeModuleToEvaluate> degreeModulesToEnrol = new HashSet<IDegreeModuleToEvaluate>();
        degreeModulesToEnrol.addAll(getModulesToEnrolForFirstSemester());

        registration.getActiveStudentCurricularPlan().enrol(semesterToEnrol, degreeModulesToEnrol, Collections.EMPTY_LIST,
                CurricularRuleLevel.ENROLMENT_NO_RULES);
    }

    public Collection<DegreeModuleToEnrol> getModulesToEnrolForFirstSemester() {
        final Registration registration = getRegistration();
        final ExecutionYear executionYear = (ExecutionYear) getCandidacyExecutionInterval();
        final ExecutionSemester semesterToEnrol = executionYear.getFirstExecutionPeriod();
        final StudentCurricularPlan studentCurricularPlan = registration.getActiveStudentCurricularPlan();
        final DegreeCurricularPlan degreeCurricularPlan = registration.getLastDegreeCurricularPlan();

        Set<DegreeModuleToEnrol> degreeModulesToEnrol = new HashSet<DegreeModuleToEnrol>();

        for (CurricularCourse selectedCurricularCourse : getCurricularCoursesSet()) {
            List<Context> contextList = selectedCurricularCourse.getParentContextsByExecutionSemester(semesterToEnrol);

            if (contextList.isEmpty()) {
                continue;
            }

            Context selectedContext = contextList.iterator().next(); // WTF?.. /facepalm

            CurriculumGroup curriculumGroup = null;
            if (selectedCurricularCourse.getDegreeCurricularPlan().equals(degreeCurricularPlan)) {
                curriculumGroup = studentCurricularPlan.getRoot().findCurriculumGroupFor(selectedContext.getParentCourseGroup());
            } else {
                // Enrol on standalone curriculum group
                curriculumGroup = studentCurricularPlan.getStandaloneCurriculumGroup();
            }

            if (curriculumGroup == null) {
                continue;
            }

            DegreeModuleToEnrol toEnrol = new DegreeModuleToEnrol(curriculumGroup, selectedContext, semesterToEnrol);
            degreeModulesToEnrol.add(toEnrol);
        }

        return degreeModulesToEnrol;
    }

    public void answerNationalIdCardAvoidanceOnSubmission(MobilityIndividualApplicationProcessBean bean) {
        NationalIdCardAvoidanceQuestion question = bean.getNationalIdCardAvoidanceQuestion();

        this.setNationalIdCardAvoidanceQuestion(question);
        this.setNationalIdCardAvoidanceAnswerDate(new DateTime());
        this.setIdCardAvoidanceOtherReason(bean.getIdCardAvoidanceOtherReason());
    }

    @Override
    public boolean isErasmus() {
        return true;
    }

    public MobilityProgram getMobilityProgram() {
        MobilityQuota selectedOpening = getMobilityStudentData().getSelectedOpening();

        return selectedOpening.getMobilityAgreement().getMobilityProgram();
    }

}
