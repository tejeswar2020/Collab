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
package com.mycollab.module.project.view;

import com.mycollab.common.ModuleNameConstants;
import com.mycollab.shell.view.MainView;
import com.mycollab.shell.view.ShellUrlResolver;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.mvp.ScreenData;
import com.mycollab.vaadin.web.ui.AbstractPresenter;
import com.vaadin.ui.HasComponents;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
public class ProjectModulePresenter extends AbstractPresenter<ProjectModule> {
    private static final long serialVersionUID = 1L;

    public ProjectModulePresenter() {
        super(ProjectModule.class);
    }

    @Override
    protected void onGo(HasComponents container, ScreenData<?> data) {
        MainView mainView = (MainView) container;
        mainView.addModule(view);

        String[] params = (String[]) data.getParams();
        if (params == null || params.length == 0) {
            UserProjectDashboardPresenter dashboardPresenter = view.getDashboardPresenter();
            dashboardPresenter.onGo(view, null);
        } else {
            ShellUrlResolver.ROOT.getSubResolver("project").handle(params);
        }

        UserUIContext.updateLastModuleVisit(ModuleNameConstants.PRJ);
    }
}
