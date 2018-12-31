/**
 * Copyright © MyCollab
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycollab.vaadin.ui.field;

import com.mycollab.vaadin.UserUIContext;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomField;
import com.vaadin.ui.Label;

import java.time.LocalDate;

/**
 * @author MyCollab Ltd.
 * @since 4.5.3
 */
public class DateViewField extends CustomField<String> {
    private LocalDate date;

    public DateViewField(LocalDate date) {
        this.date = date;
    }

    @Override
    protected Component initContent() {
        String dateValue = (date == null) ? "" : UserUIContext.formatDate(date);
        return new Label(dateValue);
    }

    @Override
    protected void doSetValue(String s) {

    }

    @Override
    public String getValue() {
        return null;
    }
}
