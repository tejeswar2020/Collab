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
package com.mycollab.vaadin.ui;

import com.vaadin.ui.ListSelect;

import java.util.Collection;
import java.util.List;

/**
 * @author MyCollab Ltd.
 * @since 4.3.0
 */
// TODO
public class I18nValueListSelect extends ListSelect {
    private static final long serialVersionUID = 1L;

    public void loadData(Collection<? extends Enum<?>> values) {
//        this.setItemCaptionMode(ItemCaptionMode.EXPLICIT_DEFAULTS_ID);
//
//        for (Enum<?> entry : values) {
//            this.addItem(entry.name());
//            this.setItemCaption(entry.name(), UserUIContext.getMessage(entry));
//        }
//
//        this.setRows(4);
//        this.setMultiSelect(true);
    }
}
