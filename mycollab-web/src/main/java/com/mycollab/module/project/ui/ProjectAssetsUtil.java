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
package com.mycollab.module.project.ui;

import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.core.utils.StringUtils;
import com.mycollab.module.file.PathUtils;
import com.mycollab.module.file.StorageUtils;
import com.mycollab.module.project.CurrentProjectVariables;
import com.mycollab.module.project.ProjectRolePermissionCollections;
import com.mycollab.module.project.i18n.OptionI18nEnum.MilestoneStatus;
import com.mycollab.module.project.ui.components.ProjectLogoUploadWindow;
import com.mycollab.vaadin.AppUI;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.ui.ELabel;
import com.mycollab.vaadin.ui.UIConstants;
import com.vaadin.event.LayoutEvents;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;

/**
 * @author MyCollab Ltd.
 * @since 5.0.0
 */
public class ProjectAssetsUtil {

    public static VaadinIcons getPhaseIcon(String status) {
        if (MilestoneStatus.Closed.name().equals(status)) {
            return VaadinIcons.MINUS_CIRCLE;
        } else if (MilestoneStatus.Future.name().equals(status)) {
            return VaadinIcons.CLOCK;
        } else {
            return VaadinIcons.SPINNER;
        }
    }

    public static Component projectLogoComp(String projectShortname, Integer projectId, String projectAvatarId, int size) {
        AbstractComponent wrapper;
        if (!StringUtils.isBlank(projectAvatarId)) {
            wrapper = new Image(null, new ExternalResource(StorageUtils.getResourcePath
                    (String.format("%s/%s_%d.png", PathUtils.getProjectLogoPath(AppUI.getAccountId(), projectId),
                            projectAvatarId, size))));
        } else {
            ELabel projectIcon = new ELabel(projectShortname).withStyleName(UIConstants.TEXT_ELLIPSIS, ValoTheme.LABEL_LARGE, "center");
            projectIcon.setWidth(size, Sizeable.Unit.PIXELS);
            projectIcon.setHeight(size, Sizeable.Unit.PIXELS);
            wrapper = new VerticalLayout();
            ((VerticalLayout) wrapper).addComponent(projectIcon);
            ((VerticalLayout) wrapper).setComponentAlignment(projectIcon, Alignment.MIDDLE_CENTER);
        }
        wrapper.setWidth(size, Sizeable.Unit.PIXELS);
        wrapper.setHeight(size, Sizeable.Unit.PIXELS);
        wrapper.addStyleName(UIConstants.CIRCLE_BOX);
        wrapper.setDescription(UserUIContext.getMessage(GenericI18Enum.OPT_CHANGE_IMAGE));
        return wrapper;
    }

    public static Component editableProjectLogoComp(String projectShortname, Integer projectId, String projectAvatarId, int size) {
        VerticalLayout wrapper = new VerticalLayout();

        if (CurrentProjectVariables.canWrite(ProjectRolePermissionCollections.PROJECT)) {
            wrapper.addStyleName("cursor_pointer");
            wrapper.setDescription(UserUIContext.getMessage(GenericI18Enum.OPT_CHANGE_IMAGE));
            wrapper.addLayoutClickListener((LayoutEvents.LayoutClickListener) layoutClickEvent ->
                    UI.getCurrent().addWindow(new ProjectLogoUploadWindow(projectShortname, projectId, projectAvatarId))
            );
        }

        if (!StringUtils.isBlank(projectAvatarId)) {
            Image image = new Image(null, new ExternalResource(StorageUtils.getResourcePath
                    (String.format("%s/%s_%d.png", PathUtils.getProjectLogoPath(AppUI.getAccountId(), projectId),
                            projectAvatarId, size))));
            image.addStyleName(UIConstants.CIRCLE_BOX);
            wrapper.addComponent(image);
        } else {
            ELabel projectIcon = new ELabel(projectShortname).withStyleName(UIConstants.TEXT_ELLIPSIS, ValoTheme.LABEL_LARGE, "center");
            projectIcon.addStyleName(UIConstants.CIRCLE_BOX);
            projectIcon.setWidth(size, Sizeable.Unit.PIXELS);
            projectIcon.setHeight(size, Sizeable.Unit.PIXELS);
            wrapper.addComponent(projectIcon);
            wrapper.setComponentAlignment(projectIcon, Alignment.MIDDLE_CENTER);
        }
        wrapper.setWidth(size, Sizeable.Unit.PIXELS);
        wrapper.setHeight(size, Sizeable.Unit.PIXELS);
        return wrapper;
    }

//    public static Component clientLogoComp(SimpleAccount account, int size) {
//        AbstractComponent wrapper;
//        if (!StringUtils.isBlank(account.getAvatarid())) {
//            wrapper = new Image(null, new ExternalResource(StorageUtils.getEntityLogoPath(AppUI.getAccountId(), account.getAvatarid(), 100)));
//        } else {
//            String accountName = account.getAccountname();
//            accountName = (accountName.length() > 3) ? accountName.substring(0, 3) : accountName;
//            ELabel projectIcon = new ELabel(accountName).withStyleName(UIConstants.TEXT_ELLIPSIS, "center");
//            wrapper = new VerticalLayout();
//            ((VerticalLayout) wrapper).addComponent(projectIcon);
//            ((VerticalLayout) wrapper).setComponentAlignment(projectIcon, Alignment.MIDDLE_CENTER);
//        }
//        wrapper.setWidth(size, Sizeable.Unit.PIXELS);
//        wrapper.setHeight(size, Sizeable.Unit.PIXELS);
//        wrapper.addStyleName(UIConstants.CIRCLE_BOX);
//        wrapper.setDescription(UserUIContext.getMessage(GenericI18Enum.OPT_CHANGE_IMAGE));
//        return wrapper;
//    }
}
