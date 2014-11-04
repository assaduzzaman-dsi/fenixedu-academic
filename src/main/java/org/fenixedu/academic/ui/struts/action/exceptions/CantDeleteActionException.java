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
 * Created on 19/Mai/2003
 */
package org.fenixedu.academic.ui.struts.action.exceptions;

/**
 * @author lmac1
 */
public class CantDeleteActionException extends FenixActionException {

    public static String key = "errors.invalid.delete.not.empty";

    public CantDeleteActionException(Throwable cause) {
        super(key, cause);
    }

    public CantDeleteActionException(Object value, Throwable cause) {
        super(key, value, cause);
    }

    public CantDeleteActionException(Object[] values, Throwable cause) {
        super(key, values, cause);
    }

    public CantDeleteActionException(String text) {
        super(text);
    }

    /**
     * @return String
     */
    public static String getKey() {
        return key;
    }

    /**
     * Sets the key.
     * 
     * @param key
     *            The key to set
     */
    public static void setKey(String key) {
        ExistingActionException.key = key;
    }

    @Override
    public String toString() {
        String result = "[CantDeleteActionException\n";
        result += "property" + this.getProperty() + "\n";
        result += "error" + this.getError() + "\n";
        result += "cause" + this.getCause() + "\n";
        result += "]";
        return result;
    }
    // TODO find a way of internationalizing the message passed as argument to
    // the exception error message of the resource bundle
}