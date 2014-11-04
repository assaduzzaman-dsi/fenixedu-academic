<%--

    Copyright © 2002 Instituto Superior Técnico

    This file is part of FenixEdu Academic.

    FenixEdu Academic is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    FenixEdu Academic is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with FenixEdu Academic.  If not, see <http://www.gnu.org/licenses/>.

--%>
<%@ page isELIgnored="true"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/fenix-renderers" prefix="fr" %>
<%@ taglib uri="http://fenix-ashes.ist.utl.pt/taglib/phd" prefix="phd" %>

<%@page import="org.fenixedu.academic.domain.phd.candidacy.PhdProgramCandidacyProcess"%><html:xhtml/>

<%-- ### Title #### --%>
<h2><bean:message key="label.phd.candidacy.manageCandidacyReview" bundle="PHD_RESOURCES" /></h2>
<%-- ### End of Title ### --%>


<%--  ###  Return Links / Steps Information(for multistep forms)  ### --%>
<bean:define id="individualProgramProcessId" name="process" property="individualProgramProcess.externalId" />

<html:link action="<%= "/phdIndividualProgramProcess.do?method=viewProcess&processId=" + individualProgramProcessId.toString() %>">
	<bean:message bundle="PHD_RESOURCES" key="label.back"/>
</html:link>

<br/><br/>
<%--  ### Return Links / Steps Information (for multistep forms)  ### --%>


<%--  ### Error Messages  ### --%>
<jsp:include page="/phd/errorsAndMessages.jsp" />
<%--  ### End of Error Messages  ### --%>


<%--  ### Context Information (e.g. Person Information, Registration Information)  ### --%>
<table>
  <tr style="vertical-align: top;">
    <td style="width: 55%">
    	<strong><bean:message  key="label.phd.process" bundle="PHD_RESOURCES"/></strong>
		<fr:view schema="PhdIndividualProgramProcess.view.simple" name="process" property="individualProgramProcess">
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle2 thlight mtop15" />
			</fr:layout>
		</fr:view>
	</td>
    <td>
	    <strong><bean:message  key="label.phd.candidacyProcess" bundle="PHD_RESOURCES"/></strong>
		<fr:view schema="PhdProgramCandidacyProcess.view.simple" name="process">
			<fr:layout name="tabular">
				<fr:property name="classes" value="tstyle2 thlight mtop15" />
			</fr:layout>
		</fr:view>
    </td>
  </tr>
</table>


<%--  ### End Of Context Information  ### --%>

<bean:define id="processId" name="process" property="externalId" />
<br/>

<%--  ### Documents  ### --%>

<strong><bean:message  key="label.phd.documents" bundle="PHD_RESOURCES"/></strong>
<logic:notEmpty name="process" property="candidacyReviewDocuments">
	
	<fr:view schema="PhdProgramProcessDocument.review.document" name="process" property="candidacyReviewDocuments">
		<fr:layout name="tabular">
			<fr:property name="classes" value="tstyle2 thlight mtop15" />
			
			<fr:property name="linkFormat(view)" value="${downloadUrl}"/>
			<fr:property name="key(view)" value="label.view"/>
			<fr:property name="bundle(view)" value="PHD_RESOURCES"/>
			<fr:property name="order(view)" value="0" />
			<fr:property name="hasContext(view)" value="false" />
			<fr:property name="contextRelative(view)" value="false" />

				
			<fr:property name="linkFormat(delete)" value="/phdProgramCandidacyProcess.do?method=deleteCandidacyReview&documentId=${externalId}&processId=${phdProgramProcess.externalId}"/>
			<fr:property name="key(delete)" value="label.delete"/>
			<fr:property name="bundle(delete)" value="PHD_RESOURCES"/>
			<fr:property name="confirmationKey(delete)" value="message.confirm.document.delete" />
			<fr:property name="confirmationBundle(delete)" value="PHD_RESOURCES" />
			<fr:property name="order(delete)" value="1" />
			
			<fr:property name="sortBy" value="documentType=asc" />
		</fr:layout>
	</fr:view>
	<br/>
</logic:notEmpty>
<logic:empty name="process" property="candidacyReviewDocuments">
	<br/>
	<bean:message  key="label.phd.noDocuments" bundle="PHD_RESOURCES"/>
</logic:empty>

<br/><br/>

<%--  ### End Of Documents  ### --%>

<phd:activityAvailable process="<%= request.getAttribute("process") %>" activity="<%= org.fenixedu.academic.domain.phd.candidacy.activities.UploadCandidacyReview.class %>">

	<%--  ### Operation Area (e.g. Create Candidacy)  ### --%>
	<fr:form action="/phdProgramCandidacyProcess.do" encoding="multipart/form-data">
	  	<input type="hidden" name="method" value="" />
	  	<input type="hidden" name="processId" value="<%= processId.toString()  %>" />
	  	
	  	<strong><bean:message  key="label.phd.candidacy.candidacy.review.document" bundle="PHD_RESOURCES"/></strong>
		<fr:edit id="documentToUpload"
			name="documentToUpload"
			schema="PhdCandidacyDocumentUploadBean.review.document"
			action="/phdProgramCandidacyProcess.do?method=uploadCandidacyReview">
		
			<fr:layout name="tabular-editable">
				<fr:property name="classes" value="tstyle5 thlight thright mtop05" />
				<fr:property name="columnClasses" value=",,tdclear tderror1" />
				<fr:destination name="invalid" path="/phdProgramCandidacyProcess.do?method=uploadCandidacyReviewInvalid" />
			</fr:layout>
		</fr:edit>
		
		<fr:edit id="stateBean" name="stateBean" visible="false" />
		
		<html:submit bundle="HTMLALT_RESOURCES" altKey="submit.submit" onclick="this.form.method.value='uploadCandidacyReview';"><bean:message bundle="APPLICATION_RESOURCES" key="label.add"/></html:submit>
	</fr:form>
	
		<br />
		<br />
		<br />
		<br />
		
</phd:activityAvailable>

<phd:activityAvailable process="<%= request.getAttribute("process") %>" activity="<%= org.fenixedu.academic.domain.phd.candidacy.activities.RequestRatifyCandidacy.class %>">
	<fr:form action="/phdProgramCandidacyProcess.do?method=requestRatifyCandidacy">
	  	<input type="hidden" name="processId" value="<%= processId.toString()  %>" />
	  	<fr:edit id="documentToUpload" name="documentToUpload" visible="false" />
	
		<strong><bean:message key="label.phd.request.ratify.candidacy.question" bundle="PHD_RESOURCES" /></strong>
		
		<fr:edit id="stateBean" name="stateBean" visible="false" />
	  	<fr:edit id="stateBean-generateAlert" name="stateBean">
	  		<fr:schema bundle="PHD_RESOURCES" type="org.fenixedu.academic.domain.phd.candidacy.PhdProgramCandidacyProcessStateBean">
	  			<fr:slot name="generateAlert" layout="radio-postback">
	  				<fr:property name="destination" value="postback" />
	  			</fr:slot>
	  		</fr:schema>
			<fr:layout name="tabular-editable">
				<fr:property name="classes" value="tstyle5 thlight thright mtop05" />
				<fr:property name="columnClasses" value=",,tdclear tderror1" />
			</fr:layout>
			
			<fr:destination name="postback" path="<%= "/phdProgramCandidacyProcess.do?method=manageCandidacyReviewPostback&amp;processId=" + processId.toString() %>"/>
	  	</fr:edit>

		<logic:equal name="stateBean" property="generateAlert" value="true">
			<fr:edit id="stateBean" name="stateBean" schema="PhdProgramCandidacyProcessStateBean.edit">
				<fr:layout name="tabular-editable">
					<fr:property name="classes" value="tstyle5 thlight thright mtop05" />
					<fr:property name="columnClasses" value=",,tdclear tderror1" />
				</fr:layout>
			</fr:edit>
			
		</logic:equal>
		<p>
			<html:submit bundle="HTMLALT_RESOURCES" altKey="submit.submit"><bean:message bundle="PHD_RESOURCES" key="label.phd.request.ratify.candidacy"/></html:submit>
		</p>
		
	</fr:form>

</phd:activityAvailable>
<br/><br/>


<%--  ### End of Operation Area  ### --%>
