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
package org.fenixedu.academic.ui.struts.action.messaging;

import org.fenixedu.academic.ui.struts.action.commons.FacesEntryPoint;
import org.fenixedu.bennu.struts.annotations.Mapping;
import org.fenixedu.bennu.struts.portal.StrutsApplication;
import org.fenixedu.bennu.struts.portal.StrutsFunctionality;

public class MessagingApplication {

    private static final String BUNDLE = "MessagingResources";
    private static final String ACCESS_GROUP = "role(MESSAGING)";
    private static final String HINT = "Messaging";

    @StrutsApplication(descriptionKey = "messaging.menu.announcements.link", path = "messaging",
            titleKey = "messaging.menu.announcements.link", bundle = BUNDLE, accessGroup = ACCESS_GROUP, hint = HINT)
    public static class MessagingAnnouncementsApp {
    }

    @StrutsApplication(descriptionKey = "label.emails", path = "emails", titleKey = "label.emails", bundle = BUNDLE,
            accessGroup = ACCESS_GROUP, hint = HINT)
    public static class MessagingEmailsApp {
    }

    @StrutsApplication(descriptionKey = "label.navheader.files", path = "files", titleKey = "label.navheader.files",
            bundle = BUNDLE, accessGroup = ACCESS_GROUP, hint = HINT)
    public static class MessagingFilesApp {
    }

    @StrutsApplication(descriptionKey = "label.navheader.search", path = "search", titleKey = "label.navheader.search",
            bundle = BUNDLE, accessGroup = ACCESS_GROUP, hint = HINT)
    public static class MessagingSearchApp {
    }

    // Faces Entry Points

    @StrutsFunctionality(app = MessagingSearchApp.class, path = "organizational-structure", titleKey = "label.orgUnit")
    @Mapping(path = "/organizationalStructure/structurePage", module = "messaging")
    public static class OrganizationalStructurePage extends FacesEntryPoint {
    }
}
