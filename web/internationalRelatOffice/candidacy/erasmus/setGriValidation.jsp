<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<%!

	static String f(String value, Object ... args) {
    	return String.format(value, args);
	}
%>


<html:xhtml/>


<em><bean:message key="label.candidacies" bundle="APPLICATION_RESOURCES"/></em>
<h2><bean:message key="label.erasmus.validate.application" bundle="ACADEMIC_OFFICE_RESOURCES"/></h2>

<html:messages id="message" message="true" bundle="APPLICATION_RESOURCES">
	<span class="error0"> <bean:write name="message" /> </span>
	<br />
</html:messages>
<fr:hasMessages for="individualCandidacyProcessBean.precedentDegreeInformation" type="conversion">
	<ul class="nobullet list6">
		<fr:messages>
			<li><span class="error0"><fr:message/></span></li>
		</fr:messages>
	</ul>
</fr:hasMessages>

<bean:define id="processId" name="process" property="idInternal" />

<script src="<%= request.getContextPath() + "/javaScript/jquery/jquery.js" %>" type="text/javascript" >
</script>



<script type="text/javascript">
	$(document).ready(function(){
		$("[id*=createAlert]").change(function(event) {
			if($("[id*=createAlert]").attr('checked')) {
				$("[id*=sendEmail]").attr('disabled', false);
				$("[id*=alertSubject]").attr('disabled', false);
				$("[id*=alertBody]").attr('disabled', false);
			} else {
				$("[id*=sendEmail]").attr('disabled', true);
				$("[id*=alertSubject]").attr('disabled', true);
				$("[id*=alertBody]").attr('disabled', true);
			}
		});
	});
</script>

<fr:form action='<%= f("/caseHandlingErasmusIndividualCandidacyProcess.do?method=executeSetGriValidation&amp;processId=%s&amp", processId.toString()) %>' id="thisForm">
	<fr:edit id="individualCandidacyProcessBean" name="individualCandidacyProcessBean" visible="false" />
	
	<fr:edit 	id="gri.validation"
				name="individualCandidacyProcessBean"
				schema="ErasmusIndividualCandidacyProcess.setGriValidation">
	</fr:edit>	
	
	<fr:edit	id="gri.alert.contents"
				name="individualCandidacyProcessBean"
				schema="ErasmusIndividualCandidacyProcess.alertContents">
		<fr:destination name="cancel" path="<%= f("/caseHandlingErasmusIndividualCandidacyProcess.do?method=listProcessAllowedActivities&amp;processId=%s&amp", processId.toString()) %>"/>
		<fr:layout name="tabular-editable">
			<fr:property name="classes" value="tstyle4 thlight thright mtop025"/>
	        <fr:property name="columnClasses" value="width12em,,tdclear tderror1"/>
		</fr:layout>		
	</fr:edit>
	
	<html:submit><bean:message key="button.submit" bundle="ACADEMIC_OFFICE_RESOURCES" /></html:submit>
	<html:cancel><bean:message key="button.cancel" bundle="ACADEMIC_OFFICE_RESOURCES" /></html:cancel>
</fr:form>
