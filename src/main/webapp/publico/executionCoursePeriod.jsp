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
<%@ page language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>

<logic:present name="siteView">
	<bean:define id="commonComponent" name="siteView" property="commonComponent"/>
	<bean:define id="executionCourse" name="commonComponent" property="executionCourse"/>
	<bean:define id="executionPeriod" name="executionCourse" property="infoExecutionPeriod"/>	
	<h2 class="greytxt">
		<bean:write name="executionPeriod" property="semester" /><bean:message bundle="PUBLIC_DEGREE_INFORMATION" locale="pt_PT" key="public.degree.information.label.ordinal.semester.abbr" />
		<bean:write name="executionPeriod" property="infoExecutionYear.year" />
	</h2>
</logic:present>
