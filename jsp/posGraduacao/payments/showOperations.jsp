<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/fenix-renderers.tld" prefix="fr"%>

<h2><bean:message
	key="label.masterDegree.administrativeOffice.payments.operations" /></h2>

<strong><bean:message
	key="label.masterDegree.administrativeOffice.payments.person" />:</strong>
<fr:view name="person"
	schema="person.view-with-name-and-idDocumentType-and-documentIdNumber">
	<fr:layout name="tabular">
		<fr:property name="classes" value="tstyle4 thlight thright" />
	</fr:layout>
</fr:view>

<bean:define id="personId" name="person" property="idInternal" />
<table>
	<tr>
		<td>
			<html:link action="<%="/payments.do?method=showEvents&personId=" + personId%>">
					<bean:message key="link.masterDegree.administrativeOffice.payments.events" />
			</html:link>
		</td>
	</tr>
	<tr>
		<td>
			<html:link action="<%="/payments.do?method=showPaymentsWithoutReceipt&personId=" + personId%>">
				<bean:message key="link.masterDegree.administrativeOffice.payments.paymentsWithoutReceipt" />
			</html:link>
		</td>
	</tr>
	<tr>
		<td>
			<html:link action="<%="/payments.do?method=showReceipts&personId=" + personId%>">
				<bean:message key="link.masterDegree.administrativeOffice.payments.receipts" />
			</html:link>
		</td>
	</tr>
</table>

