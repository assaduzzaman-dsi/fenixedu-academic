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
package org.fenixedu.academic.domain.accounting;

public enum EntryType {

    TRANSFER,

    ADJUSTMENT,

    SCHOOL_REGISTRATION_CERTIFICATE_REQUEST_FEE,

    ENROLMENT_CERTIFICATE_REQUEST_FEE,

    APPROVEMENT_CERTIFICATE_REQUEST_FEE,

    DEGREE_FINALIZATION_CERTIFICATE_REQUEST_FEE,

    PHD_FINALIZATION_CERTIFICATE_REQUEST_FEE,

    SCHOOL_REGISTRATION_DECLARATION_REQUEST_FEE,

    ENROLMENT_DECLARATION_REQUEST_FEE,

    DIPLOMA_REQUEST_FEE,

    REGISTRY_DIPLOMA_REQUEST_FEE,

    COURSE_LOAD_REQUEST_FEE,

    EXTERNAL_COURSE_LOAD_REQUEST_FEE,

    EXAM_DATE_CERTIFICATE_REQUEST_FEE,

    PROGRAM_CERTIFICATE_REQUEST_FEE,

    EXTERNAL_PROGRAM_CERTIFICATE_REQUEST_FEE,

    PHOTOCOPY_REQUEST_FEE,

    STUDENT_REINGRESSION_REQUEST_FEE,

    EQUIVALENCE_PLAN_REQUEST_FEE,

    REVISION_EQUIVALENCE_PLAN_REQUEST_FEE,

    COURSE_GROUP_CHANGE_REQUEST_FEE,

    EXTRA_EXAM_REQUEST_FEE,

    FREE_SOLICITATION_ACADEMIC_REQUEST_FEE,

    CANDIDACY_ENROLMENT_FEE,

    GRATUITY_FEE,

    INSURANCE_FEE,

    REGISTRATION_FEE,

    ADMINISTRATIVE_OFFICE_FEE,

    ADMINISTRATIVE_OFFICE_FEE_INSURANCE,

    IMPROVEMENT_OF_APPROVED_ENROLMENT_FEE,

    ENROLMENT_OUT_OF_PERIOD_PENALTY,

    OVER23_INDIVIDUAL_CANDIDACY_FEE,

    SECOND_CYCLE_INDIVIDUAL_CANDIDACY_FEE,

    DEGREE_CANDIDACY_FOR_GRADUATED_PERSON_FEE,

    RESIDENCE_FEE,

    DEGREE_CHANGE_INDIVIDUAL_CANDIDACY_FEE,

    DEGREE_TRANSFER_INDIVIDUAL_CANDIDACY_FEE,

    STANDALONE_ENROLMENT_GRATUITY_FEE,

    STANDALONE_INDIVIDUAL_CANDIDACY_FEE,

    PARTIAL_REGISTRATION_REGIME_REQUEST_FEE,

    GENERIC_DECLARATION_REQUEST_FEE,

    EXTRA_CURRICULAR_APPROVEMENT_CERTIFICATE_REQUEST_FEE,

    STANDALONE_ENROLMENT_APPROVEMENT_CERTIFICATE_REQUEST_FEE,

    PHD_REGISTRATION_FEE,

    PHD_THESIS_REQUEST_FEE,

    PHD_GRATUITY_FEE,

    INSTITUTION_ACCOUNT_CREDIT,

    MICRO_PAYMENT,

    EXTERNAL_SCOLARSHIP_PAYMENT,

    DUPLICATE_REQUEST_FEE;

    public String getName() {
        return name();
    }

    public String getQualifiedName() {
        return EntryType.class.getSimpleName() + "." + name();
    }

    public String getFullyQualifiedName() {
        return EntryType.class.getName() + "." + name();
    }
}
