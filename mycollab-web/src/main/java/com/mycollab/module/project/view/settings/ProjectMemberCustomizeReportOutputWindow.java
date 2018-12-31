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
package com.mycollab.module.project.view.settings;

import com.mycollab.common.TableViewField;
import com.mycollab.db.query.VariableInjector;
import com.mycollab.module.project.ProjectTypeConstants;
import com.mycollab.module.project.domain.SimpleProjectMember;
import com.mycollab.module.project.domain.criteria.ProjectMemberSearchCriteria;
import com.mycollab.module.project.fielddef.ProjectMemberTableFieldDef;
import com.mycollab.module.project.i18n.ProjectMemberI18nEnum;
import com.mycollab.module.project.service.ProjectMemberService;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.reporting.CustomizeReportOutputWindow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author MyCollab Ltd
 * @since 5.3.4
 */
public class ProjectMemberCustomizeReportOutputWindow extends CustomizeReportOutputWindow<ProjectMemberSearchCriteria, SimpleProjectMember> {
    public ProjectMemberCustomizeReportOutputWindow(VariableInjector<ProjectMemberSearchCriteria> variableInjector) {
        super(ProjectTypeConstants.MEMBER, UserUIContext.getMessage(ProjectMemberI18nEnum.LIST), SimpleProjectMember.class,
                AppContextUtil.getSpringBean(ProjectMemberService.class), variableInjector);
    }

    @Override
    protected Set<TableViewField> getDefaultColumns() {
        return new HashSet<>(Arrays.asList(ProjectMemberTableFieldDef.memberName, ProjectMemberTableFieldDef.roleName,
                ProjectMemberTableFieldDef.billingRate, ProjectMemberTableFieldDef.overtimeRate));
    }

    @Override
    protected Set<TableViewField> getAvailableColumns() {
        return new HashSet<>(Arrays.asList(ProjectMemberTableFieldDef.projectName, ProjectMemberTableFieldDef.memberName,
                ProjectMemberTableFieldDef.roleName, ProjectMemberTableFieldDef.numOpenTasks,
                ProjectMemberTableFieldDef.numOpenBugs, ProjectMemberTableFieldDef.totalBillableLogTime,
                ProjectMemberTableFieldDef.totalNonBillableLogTime, ProjectMemberTableFieldDef.billingRate,
                ProjectMemberTableFieldDef.overtimeRate));
    }

    @Override
    protected Object[] buildSampleData() {
        return new Object[]{"MyCollab", "John Adams", "Administrator", "12", "3", "40", "8", "50", "60"};
    }
}
