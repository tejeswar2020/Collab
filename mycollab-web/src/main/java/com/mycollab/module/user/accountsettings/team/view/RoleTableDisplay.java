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
package com.mycollab.module.user.accountsettings.team.view;

import com.mycollab.common.TableViewField;
import com.mycollab.i18n.LocalizationHelper;
import com.mycollab.module.user.AccountLinkGenerator;
import com.mycollab.module.user.domain.SimpleRole;
import com.mycollab.module.user.domain.criteria.RoleSearchCriteria;
import com.mycollab.module.user.service.RoleService;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.web.ui.CheckBoxDecor;
import com.mycollab.vaadin.web.ui.LabelLink;
import com.mycollab.vaadin.web.ui.table.DefaultPagedBeanTable;
import com.vaadin.ui.Label;

import java.util.Set;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class RoleTableDisplay extends DefaultPagedBeanTable<RoleService, RoleSearchCriteria, SimpleRole> {
    private static final long serialVersionUID = 1L;

    public RoleTableDisplay(TableViewField requiredColumn, Set<TableViewField> displayColumns) {
        super(AppContextUtil.getSpringBean(RoleService.class), SimpleRole.class, requiredColumn, displayColumns);

        this.addGeneratedColumn("selected", (source, itemId, columnId) -> {
            final SimpleRole role = getBeanByIndex(itemId);
            CheckBoxDecor cb = new CheckBoxDecor("", role.isSelected());
            cb.addValueChangeListener(valueChangeEvent -> RoleTableDisplay.this.fireSelectItemEvent(role));
            role.setExtraData(cb);
            return cb;
        });

        this.addGeneratedColumn("isdefault", (source, itemId, columnId) -> {
            SimpleRole role = getBeanByIndex(itemId);
            Enum yesNo = LocalizationHelper.localizeYesNo(role.getIsdefault());
            return new Label(UserUIContext.getMessage(yesNo));
        });

        this.addGeneratedColumn("rolename", (source, itemId, columnId) -> {
            SimpleRole role = getBeanByIndex(itemId);
            return new LabelLink(role.getRolename(), AccountLinkGenerator.generateRoleLink(role.getId()));
        });
    }
}