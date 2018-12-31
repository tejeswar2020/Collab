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
package com.mycollab.vaadin.web.ui;

import com.mycollab.common.i18n.ShellI18nEnum;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.ui.ELabel;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.viritin.layouts.MVerticalLayout;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class NoSubDomainExistedWindow extends MVerticalLayout {
    private static final long serialVersionUID = 1L;

    public NoSubDomainExistedWindow(final String domain) {
        this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        final Label titleIcon = ELabel.fontIcon(VaadinIcons.EXCLAMATION_CIRCLE).withStyleName("warning-icon",
                ValoTheme.LABEL_NO_MARGIN).withUndefinedWidth();

        Label warningMsg = new ELabel(UserUIContext.getMessage(ShellI18nEnum.ERROR_NO_SUB_DOMAIN, domain)).withUndefinedWidth();

        Button backToHome = new Button(UserUIContext.getMessage(ShellI18nEnum.BUTTON_BACK_TO_HOME_PAGE),
                clickEvent -> getUI().getPage().setLocation("https://www.mycollab.com"));
        backToHome.addStyleName(WebThemes.BUTTON_ACTION);
        this.with(titleIcon, warningMsg, backToHome);
    }
}