/**
 * Copyright © MyCollab
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycollab.vaadin.web.ui;

import org.vaadin.viritin.fields.AbstractNumberField;

/**
 * @author MyCollab Ltd
 * @since 5.3.1
 */
// TODO
public class IntegerField extends AbstractNumberField {
//    @Override
//    protected void userInputToValue(String str) {
//        try {
//            this.setValue(Integer.parseInt(str));
//        } catch (Exception e) {
//            this.setValue(0);
//        }
//    }
//
//    @Override
//    public Integer getValue() {
//        return null;
//    }


    @Override
    protected void userInputToValue(String s) {

    }

    @Override
    public Object getValue() {
        return null;
    }
}
