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
package com.mycollab.community.shell.view.components;

import com.mycollab.common.i18n.GenericI18Enum;
import com.mycollab.configuration.ServerConfiguration;
import com.mycollab.configuration.SiteConfiguration;
import com.mycollab.core.MyCollabException;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.support.domain.TestimonialForm;
import com.mycollab.vaadin.UserUIContext;
import com.mycollab.vaadin.ui.AbstractBeanFieldGroupEditFieldFactory;
import com.mycollab.vaadin.ui.AbstractFormLayoutFactory;
import com.mycollab.vaadin.ui.AdvancedEditBeanForm;
import com.mycollab.vaadin.ui.NotificationUtil;
import com.mycollab.vaadin.web.ui.WebThemes;
import com.mycollab.vaadin.web.ui.grid.GridFormLayoutHelper;
import com.vaadin.data.HasValue;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;
import org.vaadin.viritin.layouts.MWindow;

/**
 * @author MyCollab Ltd
 * @since 5.1.3
 */
class TestimonialWindow extends MWindow {

    TestimonialWindow() {
        super("Thank you! We appreciate your help!");
        this.withModal(true).withResizable(false).withWidth("900px").withCenter();

        MVerticalLayout content = new MVerticalLayout().withMargin(false);

        final TestimonialForm entity = new TestimonialForm();
        final AdvancedEditBeanForm<TestimonialForm> editForm = new AdvancedEditBeanForm<>();
        editForm.setFormLayoutFactory(new AbstractFormLayoutFactory() {
            GridFormLayoutHelper gridFormLayoutHelper;

            @Override
            public AbstractComponent getLayout() {
                gridFormLayoutHelper = GridFormLayoutHelper.defaultFormLayoutHelper(2, 4);
                return gridFormLayoutHelper.getLayout();
            }

            @Override
            public HasValue<?> onAttachField(Object propertyId, HasValue<?> field) {
                if ("displayname".equals(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, "Name", 0, 0, 2, "100%");
                } else if ("company".equals(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, "Company", 0, 1);
                } else if ("jobrole".equals(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, "Job Role", 1, 1);
                } else if ("website".equals(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, "Website", 0, 2);
                } else if ("email".equals(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, "Email", 1, 2);
                } else if ("testimonial".equals(propertyId)) {
                    return gridFormLayoutHelper.addComponent(field, "Testimonial", 0, 3, 2, "100%");
                }
                return null;
            }
        });
        editForm.setBeanFormFieldFactory(new AbstractBeanFieldGroupEditFieldFactory<TestimonialForm>(editForm) {
            @Override
            protected HasValue<?> onCreateField(Object propertyId) {
                if ("testimonial".equals(propertyId)) {
                    return new TextArea();
                }
                return null;
            }
        });
        editForm.setBean(entity);
        content.addComponent(editForm);

        MButton cancelBtn = new MButton(UserUIContext.getMessage(GenericI18Enum.BUTTON_CANCEL), clickEvent -> close())
                .withStyleName(WebThemes.BUTTON_OPTION);

        MButton submitBtn = new MButton(UserUIContext.getMessage(GenericI18Enum.BUTTON_SUBMIT), clickEvent -> {
            if (editForm.validateForm()) {
                try {
                    RestTemplate restTemplate = new RestTemplate();
                    restTemplate.getMessageConverters().add(new FormHttpMessageConverter());
                    MultiValueMap<String, Object> values = new LinkedMultiValueMap<>();
                    values.add("company", entity.getCompany());
                    values.add("displayname", entity.getDisplayname());
                    values.add("email", entity.getEmail());
                    values.add("jobrole", entity.getJobrole());
                    values.add("testimonial", entity.getTestimonial());
                    values.add("website", entity.getWebsite());
                    ServerConfiguration serverConfiguration = AppContextUtil.getSpringBean(ServerConfiguration.class);
                    restTemplate.postForObject(serverConfiguration.getApiUrl("testimonial"), values, String.class);
                    NotificationUtil.showNotification("We appreciate your kindness action. We will contact you soon " +
                            "to verify your information and provide the MyCollab growing license (for 10 users) to " +
                            "you after that", "Thank you for your time");
                } catch (Exception e) {
                    throw new MyCollabException(e);
                }
                close();
            }
        }).withStyleName(WebThemes.BUTTON_ACTION).withIcon(VaadinIcons.PAPERPLANE);

        MHorizontalLayout buttonControls = new MHorizontalLayout(cancelBtn, submitBtn).withMargin(true);
        content.with(buttonControls).withAlign(buttonControls, Alignment.MIDDLE_RIGHT);
        this.setContent(content);
    }
}
