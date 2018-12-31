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
package com.mycollab.module.project.view.bug;

import com.mycollab.common.TableViewField;
import com.mycollab.core.utils.StringUtils;
import com.mycollab.module.project.ProjectLinkGenerator;
import com.mycollab.module.project.ProjectTooltipGenerator;
import com.mycollab.module.project.i18n.OptionI18nEnum;
import com.mycollab.module.project.i18n.OptionI18nEnum.BugResolution;
import com.mycollab.module.project.ui.ProjectAssetsManager;
import com.mycollab.module.project.view.settings.component.ProjectUserLink;
import com.mycollab.module.tracker.domain.SimpleBug;
import com.mycollab.module.tracker.domain.criteria.BugSearchCriteria;
import com.mycollab.module.tracker.service.BugService;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.AppUI;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.ui.ELabel;
import com.mycollab.vaadin.web.ui.LabelLink;
import com.mycollab.vaadin.web.ui.WebThemes;
import com.mycollab.vaadin.web.ui.table.DefaultPagedBeanTable;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Label;

import java.util.Set;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
class BugTableDisplay extends DefaultPagedBeanTable<BugService, BugSearchCriteria, SimpleBug> {
    private static final long serialVersionUID = 1L;

    BugTableDisplay(Set<TableViewField> displayColumns) {
        this(null, displayColumns);
    }

    BugTableDisplay(TableViewField requiredColumn, Set<TableViewField> displayColumns) {
        this(null, requiredColumn, displayColumns);
    }

    BugTableDisplay(String viewId, TableViewField requiredColumn, Set<TableViewField> displayColumns) {
        super(AppContextUtil.getSpringBean(BugService.class), SimpleBug.class, viewId, requiredColumn, displayColumns);

        this.addGeneratedColumn("assignuserFullName", (source, itemId, columnId) -> {
            SimpleBug bug = getBeanByIndex(itemId);
            return new ProjectUserLink(bug.getProjectid(), bug.getAssignuser(), bug.getAssignUserAvatarId(), bug.getAssignuserFullName());
        });

        this.addGeneratedColumn("loguserFullName", (source, itemId, columnId) -> {
            SimpleBug bug = getBeanByIndex(itemId);
            return new ProjectUserLink(bug.getProjectid(), bug.getCreateduser(), bug.getLoguserAvatarId(), bug.getLoguserFullName());
        });

        this.addGeneratedColumn("name", (source, itemId, columnId) -> {
            SimpleBug bug = getBeanByIndex(itemId);
            LabelLink b = new LabelLink(bug.getName(), ProjectLinkGenerator.generateBugPreviewLink(bug.getBugkey(),
                    bug.getProjectShortName()));

            if (StringUtils.isNotBlank(bug.getPriority())) {
                b.setIconLink(ProjectAssetsManager.getPriority(bug.getPriority()));
                b.addStyleName("priority-" + bug.getPriority().toLowerCase());
            }

            b.setDescription(ProjectTooltipGenerator.generateToolTipBug(UserUIContext.getUserLocale(), AppUI.getDateFormat(),
                    bug, AppUI.getSiteUrl(), UserUIContext.getUserTimeZone(), false));

            if (bug.isCompleted()) {
                b.addStyleName(WebThemes.LINK_COMPLETED);
            } else if (bug.isOverdue()) {
                b.addStyleName(WebThemes.LINK_OVERDUE);
            }
            return b;
        });

        this.addGeneratedColumn("severity", (source, itemId, columnId) -> {
            SimpleBug bug = getBeanByIndex(itemId);
            Label lbPriority = new Label(UserUIContext.getMessage(OptionI18nEnum.BugSeverity.class, bug.getSeverity()));
            lbPriority.setIcon(VaadinIcons.STAR);
            if (bug.getSeverity() != null) {
                lbPriority.addStyleName("bug-severity-" + bug.getSeverity().toLowerCase());
            }
            return lbPriority;
        });

        this.addGeneratedColumn("duedate", (source, itemId, columnId) -> {
            SimpleBug bug = getBeanByIndex(itemId);
            return new ELabel().prettyDate(bug.getDuedate());
        });

        this.addGeneratedColumn("createdtime", (source, itemId, columnId) -> {
            SimpleBug bug = getBeanByIndex(itemId);
            return new ELabel().prettyDateTime(bug.getCreatedtime());
        });

        this.addGeneratedColumn("resolution", (source, itemId, columnId) -> {
            SimpleBug bug = getBeanByIndex(itemId);
            return new Label(UserUIContext.getMessage(BugResolution.class, bug.getResolution()));
        });
        this.setWidth("100%");
    }
}
