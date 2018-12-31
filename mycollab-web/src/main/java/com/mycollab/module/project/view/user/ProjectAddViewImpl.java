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
package com.mycollab.module.project.view.user;

import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.common.i18n.OptionI18nEnum.StatusI18nEnum;
import com.mycollab.module.project.domain.Project;
import com.mycollab.module.project.i18n.ProjectI18nEnum;
import com.mycollab.module.project.ui.ProjectAssetsUtil;
import com.mycollab.module.project.view.settings.component.ProjectMemberSelectionField;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.event.HasEditFormHandlers;
import com.mycollab.vaadin.mvp.AbstractVerticalPageView;
import com.mycollab.vaadin.mvp.ViewComponent;
import com.mycollab.vaadin.ui.*;
import com.mycollab.vaadin.web.ui.AddViewLayout;
import com.mycollab.vaadin.web.ui.DoubleField;
import com.mycollab.vaadin.web.ui.I18nValueComboBox;
import com.mycollab.vaadin.web.ui.grid.GridFormLayoutHelper;
import com.vaadin.data.HasValue;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import static com.mycollab.vaadin.web.ui.utils.FormControlsGenerator.generateEditFormControls;

/**
 * @author MyCollab Ltd.
 * @since 1.0
 */
@ViewComponent
public class ProjectAddViewImpl extends AbstractVerticalPageView implements ProjectAddView {

    private Project project;
    private final AdvancedEditBeanForm<Project> editForm;

    public ProjectAddViewImpl() {
        editForm = new AdvancedEditBeanForm<>();
        addComponent(editForm);
    }

    @Override
    public HasEditFormHandlers<Project> getEditFormHandlers() {
        return editForm;
    }

    @Override
    public void editItem(final Project item) {
        this.project = item;
        editForm.setFormLayoutFactory(new FormLayoutFactory());
        editForm.setBeanFormFieldFactory(new EditFormFieldFactory(editForm));
        editForm.setBean(project);
    }

    class FormLayoutFactory extends AbstractFormLayoutFactory {

        private ProjectInformationLayout projectInformationLayout;

        private Layout createButtonControls() {
            final HorizontalLayout controlPanel = new HorizontalLayout();
            final ComponentContainer controlButtons;

            if (project.getId() == null) {
                controlButtons = generateEditFormControls(editForm);
            } else {
                controlButtons = generateEditFormControls(editForm, true, false, true);
            }
            controlPanel.addComponent(controlButtons);
            controlPanel.setComponentAlignment(controlButtons, Alignment.TOP_RIGHT);
            return controlPanel;
        }

        @Override
        public AbstractComponent getLayout() {
            MHorizontalLayout header = new MHorizontalLayout().withFullWidth().withMargin(new MarginInfo(true, false, true, false));
            header.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
            final AddViewLayout projectAddLayout = new AddViewLayout(header);
            projectInformationLayout = new ProjectInformationLayout();
            projectAddLayout.addHeaderTitle(buildHeaderTitle());
            projectAddLayout.addHeaderRight(createButtonControls());
            projectAddLayout.addBody(projectInformationLayout.getLayout());

            return projectAddLayout;
        }

        private MHorizontalLayout buildHeaderTitle() {
            ELabel titleLbl = ELabel.h2(project.getName());

            MVerticalLayout logoLayout = new MVerticalLayout(ProjectAssetsUtil.projectLogoComp(project.getShortname(),
                    project.getId(), project.getAvatarid(), 32))
                    .withMargin(false).withWidth("-1px").alignAll(Alignment.TOP_CENTER);
            return new MHorizontalLayout(logoLayout, titleLbl).expand(titleLbl);
        }

        @Override
        public HasValue<?> onAttachField(Object propertyId, final HasValue<?> field) {
            return projectInformationLayout.onAttachField(propertyId, field);
        }
    }

    private static class ProjectInformationLayout extends AbstractFormLayoutFactory {
        private GridFormLayoutHelper informationLayout;
        private GridFormLayoutHelper financialLayout;
        private GridFormLayoutHelper descriptionLayout;

        @Override
        public AbstractComponent getLayout() {
            final FormContainer layout = new FormContainer();

            informationLayout = GridFormLayoutHelper.defaultFormLayoutHelper(2, 3);
            layout.addSection(UserUIContext.getMessage(ProjectI18nEnum.SECTION_PROJECT_INFO), informationLayout.getLayout());

            financialLayout = GridFormLayoutHelper.defaultFormLayoutHelper(2, 4);
            layout.addSection(UserUIContext.getMessage(ProjectI18nEnum.SECTION_FINANCE_SCHEDULE), financialLayout.getLayout());

            descriptionLayout = GridFormLayoutHelper.defaultFormLayoutHelper(2, 1);
            layout.addSection(UserUIContext.getMessage(ProjectI18nEnum.SECTION_DESCRIPTION), descriptionLayout.getLayout());
            return layout;
        }

        @Override
        public HasValue<?> onAttachField(Object propertyId, final HasValue<?> field) {
            if (propertyId.equals("name")) {
                return informationLayout.addComponent(field, UserUIContext.getMessage(GenericI18Enum.FORM_NAME), 0, 0);
            } else if (propertyId.equals("homepage")) {
                return informationLayout.addComponent(field, UserUIContext.getMessage(ProjectI18nEnum.FORM_HOME_PAGE), 1, 0);
            } else if (propertyId.equals("shortname")) {
                return informationLayout.addComponent(field, UserUIContext.getMessage(ProjectI18nEnum.FORM_SHORT_NAME),
                        UserUIContext.getMessage(ProjectI18nEnum.FORM_SHORT_NAME_HELP), 0, 1);
            } else if (propertyId.equals("projectstatus")) {
                return informationLayout.addComponent(field, UserUIContext.getMessage(GenericI18Enum.FORM_STATUS), 1, 1);
            } else if (Project.Field.memlead.equalTo(propertyId)) {
                return informationLayout.addComponent(field, UserUIContext.getMessage(ProjectI18nEnum.FORM_LEADER), 0, 2);
            } else if (propertyId.equals("planstartdate")) {
                return financialLayout.addComponent(field, UserUIContext.getMessage(GenericI18Enum.FORM_START_DATE), 0, 0);
            } else if (Project.Field.accountid.equalTo(propertyId)) {
                return financialLayout.addComponent(field, UserUIContext.getMessage(ProjectI18nEnum.FORM_ACCOUNT_NAME),
                        UserUIContext.getMessage(ProjectI18nEnum.FORM_ACCOUNT_NAME_HELP), 1, 0);
            } else if (propertyId.equals("planenddate")) {
                return financialLayout.addComponent(field, UserUIContext.getMessage(GenericI18Enum.FORM_END_DATE), 0, 1);
            } else if (Project.Field.currencyid.equalTo(propertyId)) {
                return financialLayout.addComponent(field, UserUIContext.getMessage(GenericI18Enum.FORM_CURRENCY),
                        UserUIContext.getMessage(ProjectI18nEnum.FORM_CURRENCY_HELP), 1, 1);
            } else if (propertyId.equals("defaultbillingrate")) {
                return financialLayout.addComponent(field, UserUIContext.getMessage(ProjectI18nEnum.FORM_BILLING_RATE),
                        UserUIContext.getMessage(ProjectI18nEnum.FORM_BILLING_RATE_HELP), 0, 2);
            } else if (Project.Field.defaultovertimebillingrate.equalTo(propertyId)) {
                return financialLayout.addComponent(field, UserUIContext.getMessage(ProjectI18nEnum.FORM_OVERTIME_BILLING_RATE),
                        UserUIContext.getMessage(ProjectI18nEnum.FORM_OVERTIME_BILLING_RATE_HELP), 1, 2);
            } else if (Project.Field.targetbudget.equalTo(propertyId)) {
                return financialLayout.addComponent(field, UserUIContext.getMessage(ProjectI18nEnum.FORM_TARGET_BUDGET),
                        UserUIContext.getMessage(ProjectI18nEnum.FORM_TARGET_BUDGET_HELP), 0, 3);
            } else if (Project.Field.actualbudget.equalTo(propertyId)) {
                return financialLayout.addComponent(field, UserUIContext.getMessage(ProjectI18nEnum.FORM_ACTUAL_BUDGET),
                        UserUIContext.getMessage(ProjectI18nEnum.FORM_ACTUAL_BUDGET_HELP), 1, 3);
            } else if (propertyId.equals("description")) {
                return descriptionLayout.addComponent(field, UserUIContext.getMessage(GenericI18Enum.FORM_DESCRIPTION), 0, 0, 2, "100%");
            }
            return null;
        }
    }

    // TODO
    private static class EditFormFieldFactory extends AbstractBeanFieldGroupEditFieldFactory<Project> {
        private static final long serialVersionUID = 1L;

        public EditFormFieldFactory(GenericBeanForm<Project> form) {
            super(form);
        }

        @Override
        protected HasValue<?> onCreateField(final Object propertyId) {
            Project project = attachForm.getBean();
            if (Project.Field.description.equalTo(propertyId)) {
                final RichTextArea field = new RichTextArea();
                field.setHeight("350px");
                return field;
            } else if (Project.Field.projectstatus.equalTo(propertyId)) {
                final ProjectStatusComboBox projectCombo = new ProjectStatusComboBox();
//                projectCombo.setRequired(true);
//                projectCombo.setRequiredError("Please enter a project status");
                if (project.getProjectstatus() == null) {
                    project.setProjectstatus(StatusI18nEnum.Open.name());
                }
                return projectCombo;
            } else if (Project.Field.shortname.equalTo(propertyId)) {
                final TextField tf = new TextField();
//                tf.setNullRepresentation("");
//                tf.setRequired(true);
//                tf.setRequiredError("Please enter a project short name");
                return tf;
            } else if (Project.Field.currencyid.equalTo(propertyId)) {
                return new CurrencyComboBoxField();
            } else if (Project.Field.name.equalTo(propertyId)) {
                final TextField tf = new TextField();
//                tf.setNullRepresentation("");
//                tf.setRequired(true);
//                tf.setRequiredError("Please enter a Name");
                return tf;
            } else if (Project.Field.accountid.equalTo(propertyId)) {
//                return new AccountSelectionField();
            } else if (Project.Field.memlead.equalTo(propertyId)) {
                return new ProjectMemberSelectionField();
            } else if (Project.Field.defaultbillingrate.equalTo(propertyId)
                    || Project.Field.defaultovertimebillingrate.equalTo(propertyId)
                    || Project.Field.targetbudget.equalTo(propertyId)
                    || Project.Field.actualbudget.equalTo(propertyId)) {
                return new DoubleField();
            }

            return null;
        }
    }

    private static class ProjectStatusComboBox extends I18nValueComboBox {
        ProjectStatusComboBox() {
            super(StatusI18nEnum.class, StatusI18nEnum.Open, StatusI18nEnum.Closed);
        }
    }

    @Override
    public Project getItem() {
        return project;
    }
}
