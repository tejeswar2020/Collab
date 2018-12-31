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
package com.mycollab.module.user.accountsettings.view;

import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.configuration.SiteConfiguration;
import com.mycollab.module.user.accountsettings.billing.view.IBillingPresenter;
import com.mycollab.module.user.accountsettings.customize.view.AccountSettingPresenter;
import com.mycollab.module.user.accountsettings.localization.AdminI18nEnum;
import com.mycollab.module.user.accountsettings.profile.view.ProfilePresenter;
import com.mycollab.module.user.accountsettings.team.view.UserPermissionManagementPresenter;
import com.mycollab.module.user.accountsettings.view.event.ProfileEvent;
import com.mycollab.module.user.accountsettings.view.parameters.BillingScreenData;
import com.mycollab.module.user.ui.SettingAssetsManager;
import com.mycollab.module.user.ui.SettingUIConstants;
import com.mycollab.shell.event.ShellEvent;
import com.mycollab.vaadin.EventBusFactory;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.mvp.*;
import com.mycollab.vaadin.web.ui.ServiceMenu;
import com.mycollab.vaadin.web.ui.VerticalTabsheet;
import com.mycollab.vaadin.web.ui.VerticalTabsheet.TabImpl;
import com.mycollab.vaadin.web.ui.WebThemes;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.BrowserWindowOpener;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import com.vaadin.ui.TabSheet.SelectedTabChangeEvent;
import com.vaadin.ui.TabSheet.SelectedTabChangeListener;
import com.vaadin.ui.TabSheet.Tab;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;

/**
 * @author MyCollab Ltd.
 * @since 2.0
 */
@ViewComponent
public class AccountModuleImpl extends AbstractSingleContainerPageView implements AccountModule {
    private static final long serialVersionUID = 1L;

    private VerticalTabsheet tabSheet;

    private ProfilePresenter profilePresenter;
    private UserPermissionManagementPresenter userPermissionPresenter;
    private IBillingPresenter billingPresenter;
    private AccountSettingPresenter customizePresenter;

    public AccountModuleImpl() {
        addStyleName("module");
        ControllerRegistry.addController(new UserAccountController(this));

        MHorizontalLayout topPanel = new MHorizontalLayout().withFullWidth().withMargin(true).withStyleName("border-bottom");
        AccountSettingBreadcrumb breadcrumb = ViewManager.getCacheComponent(AccountSettingBreadcrumb.class);

        MButton helpBtn = new MButton(UserUIContext.getMessage(GenericI18Enum.ACTION_HELP))
                .withIcon(VaadinIcons.ACADEMY_CAP).withStyleName(WebThemes.BUTTON_LINK);
        ExternalResource helpRes = new ExternalResource("https://community.mycollab.com/docs/account-management/");
        BrowserWindowOpener helpOpener = new BrowserWindowOpener(helpRes);
        helpOpener.extend(helpBtn);

        topPanel.with(breadcrumb, helpBtn).withAlign(helpBtn, Alignment.TOP_RIGHT);

        tabSheet = new VerticalTabsheet();
        tabSheet.setSizeFull();
        tabSheet.setNavigatorStyleName("sidebar-menu");
        CssLayout contentWrapper = tabSheet.getContentWrapper();
        contentWrapper.addStyleName("main-content");
        contentWrapper.addComponentAsFirst(topPanel);

        this.buildComponents();
        this.setContent(tabSheet);
    }

    private void buildComponents() {
        tabSheet.addTab(constructUserInformationComponent(), SettingUIConstants.PROFILE,
                UserUIContext.getMessage(AdminI18nEnum.VIEW_PROFILE), SettingAssetsManager.getAsset(SettingUIConstants.PROFILE));

        if (!SiteConfiguration.isCommunityEdition()) {
            tabSheet.addTab(constructAccountSettingsComponent(), SettingUIConstants.BILLING,
                    UserUIContext.getMessage(AdminI18nEnum.VIEW_BILLING), SettingAssetsManager.getAsset(SettingUIConstants.BILLING));
        }

        tabSheet.addTab(constructUserRoleComponent(), SettingUIConstants.USERS,
                UserUIContext.getMessage(AdminI18nEnum.VIEW_USERS_AND_ROLES), SettingAssetsManager.getAsset(SettingUIConstants.USERS));

        tabSheet.addTab(constructThemeComponent(), SettingUIConstants.GENERAL_SETTING,
                UserUIContext.getMessage(AdminI18nEnum.VIEW_SETTING), SettingAssetsManager.getAsset(SettingUIConstants.GENERAL_SETTING));

        tabSheet.addSelectedTabChangeListener(new SelectedTabChangeListener() {
            private static final long serialVersionUID = 1L;

            @Override
            public void selectedTabChange(SelectedTabChangeEvent event) {
                Tab tab = ((VerticalTabsheet) event.getSource()).getSelectedTab();
                String tabId = ((TabImpl) tab).getTabId();
                if (SettingUIConstants.PROFILE.equals(tabId)) {
                    profilePresenter.go(AccountModuleImpl.this, null);
                } else if (SettingUIConstants.BILLING.equals(tabId)) {
                    billingPresenter.go(AccountModuleImpl.this, new BillingScreenData.BillingSummary());
                } else if (SettingUIConstants.USERS.equals(tabId)) {
                    userPermissionPresenter.go(AccountModuleImpl.this, null);
                } else if (SettingUIConstants.GENERAL_SETTING.equals(tabId)) {
                    customizePresenter.go(AccountModuleImpl.this, null);
                }
            }
        });
    }

    private HasComponents constructAccountSettingsComponent() {
        billingPresenter = PresenterResolver.getPresenter(IBillingPresenter.class);
        return billingPresenter.getView();
    }

    private ComponentContainer constructUserInformationComponent() {
        profilePresenter = PresenterResolver.getPresenter(ProfilePresenter.class);
        return profilePresenter.getView();
    }

    private HasComponents constructUserRoleComponent() {
        userPermissionPresenter = PresenterResolver.getPresenter(UserPermissionManagementPresenter.class);
        return userPermissionPresenter.getView();
    }

    private ComponentContainer constructThemeComponent() {
        customizePresenter = PresenterResolver.getPresenter(AccountSettingPresenter.class);
        return customizePresenter.getView();
    }

    @Override
    public void gotoSubView(String viewId) {
        tabSheet.selectTab(viewId);
    }

    @Override
    public void gotoUserProfilePage() {
        EventBusFactory.getInstance().post(new ProfileEvent.GotoProfileView(this));
    }

//    @Override
//    public MHorizontalLayout buildMenu() {
//        if (serviceMenuContainer == null) {
//            serviceMenuContainer = new MHorizontalLayout();
//            serviceMenu = new ServiceMenu();
//            serviceMenu.addService(UserUIContext.getMessage(GenericI18Enum.MODULE_PROJECT), new Button.ClickListener() {
//                @Override
//                public void buttonClick(Button.ClickEvent clickEvent) {
//                    EventBusFactory.getInstance().post(new ShellEvent.GotoProjectModule(this, new String[]{"dashboard"}));
//                    serviceMenu.selectService(0);
//                }
//            });
//
//
//            serviceMenu.addService(UserUIContext.getMessage(GenericI18Enum.MODULE_PEOPLE), new Button.ClickListener() {
//                @Override
//                public void buttonClick(Button.ClickEvent clickEvent) {
//                    EventBusFactory.getInstance().post(new ShellEvent.GotoUserAccountModule(this, new String[]{"user", "list"}));
//
//                }
//            });
//            serviceMenuContainer.with(serviceMenu);
//        }
//        serviceMenu.selectService(3);
//        return serviceMenuContainer;
//    }
}
