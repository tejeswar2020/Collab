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

import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.db.arguments.SearchCriteria;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.ui.HeaderWithIcon;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import org.vaadin.viritin.layouts.MHorizontalLayout;

/**
 * @param <S>
 * @author MyCollab Ltd.
 * @since 2.0
 */
public abstract class DefaultGenericSearchPanel<S extends SearchCriteria> extends GenericSearchPanel<S> {
    private static final long serialVersionUID = 1L;

    private MHorizontalLayout header;
    private ComponentContainer headerText;
    protected boolean canSwitchToAdvanceLayout;

    public DefaultGenericSearchPanel() {
        this(true);
    }

    public DefaultGenericSearchPanel(boolean canSwitchToAdvanceLayout) {
        this.canSwitchToAdvanceLayout = canSwitchToAdvanceLayout;
    }

    @Override
    public void attach() {
        super.attach();
        moveToBasicSearchLayout();
    }

    abstract protected SearchLayout<S> createBasicSearchLayout();

    abstract protected SearchLayout<S> createAdvancedSearchLayout();

    abstract protected ComponentContainer buildSearchTitle();

    protected Component buildExtraControls() {
        return null;
    }

    MHorizontalLayout constructHeader() {
        if (header == null) {
            headerText = buildSearchTitle();
            if (headerText != null) {
                MHorizontalLayout rightComponent = new MHorizontalLayout();
                header = new MHorizontalLayout().withFullWidth().withMargin(new MarginInfo(true, false, true, false));

                header.with(headerText, rightComponent).withAlign(headerText, Alignment.MIDDLE_LEFT)
                        .withAlign(rightComponent, Alignment.MIDDLE_RIGHT);
            }

            Component extraControls = buildExtraControls();
            if (extraControls != null) {
                addHeaderRight(extraControls);
            }
            return header;
        } else {
            return header;
        }
    }

    public void setTotalCountNumber(Integer countNumber) {
        if (headerText instanceof HeaderWithIcon) {
            ((HeaderWithIcon) headerText).appendToTitle(UserUIContext.getMessage(GenericI18Enum.OPT_TOTAL_VALUE, countNumber));
        }
    }

    protected SearchLayout<S> moveToBasicSearchLayout() {
        SearchLayout<S> layout = createBasicSearchLayout();
        setCompositionRoot(layout);
        return layout;
    }

    protected SearchLayout<S> moveToAdvancedSearchLayout() {
        SearchLayout<S> layout = createAdvancedSearchLayout();
        setCompositionRoot(layout);
        return layout;
    }
}