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

import com.mycollab.db.arguments.SearchCriteria;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

/**
 * @author MyCollab Ltd
 * @since 5.4.3
 */
abstract public class BasicSearchLayout<S extends SearchCriteria> extends SearchLayout<S> {
    private static final long serialVersionUID = 1L;

    protected MHorizontalLayout header;
    protected ComponentContainer body;

    public BasicSearchLayout(DefaultGenericSearchPanel<S> parent) {
        super(parent, "basicSearch");
        this.initLayout();
    }

    private void initLayout() {
        header = this.constructHeader();
        body = this.constructBody();
        if (header != null) {
            this.addComponent(header, "basicSearchHeader");
        }

        this.addComponent(body, "basicSearchBody");
    }

    @Override
    protected void addHeaderRight(Component component) {
        if (header == null)
            return;

        header.with(component).withAlign(component, Alignment.MIDDLE_RIGHT);
    }

    private MHorizontalLayout constructHeader() {
        return ((DefaultGenericSearchPanel)searchPanel).constructHeader();
    }

    abstract public ComponentContainer constructBody();
}
