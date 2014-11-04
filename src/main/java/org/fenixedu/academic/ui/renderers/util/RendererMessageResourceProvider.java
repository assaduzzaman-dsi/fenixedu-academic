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
package org.fenixedu.academic.ui.renderers.util;

import java.util.Properties;

import pt.ist.fenixWebFramework.renderers.utils.RenderUtils;
import pt.utl.ist.fenix.tools.resources.AbstractMessageResourceProvider;

public class RendererMessageResourceProvider extends AbstractMessageResourceProvider {

    public RendererMessageResourceProvider() {
        super();
    }

    public RendererMessageResourceProvider(Properties bundleMappings) {
        super(bundleMappings);
    }

    @Override
    public String getMessage(String bundle, String key, String... args) {
        if (containsMapping(bundle)) {
            return format(RenderUtils.getResourceString(getBundleMapping(bundle), key), args);
        } else {
            return format(RenderUtils.getResourceString(bundle, key), args);
        }
    }
}
