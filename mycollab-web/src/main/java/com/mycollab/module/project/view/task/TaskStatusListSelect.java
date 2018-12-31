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
package com.mycollab.module.project.view.task;

import com.mycollab.common.domain.OptionVal;
import com.mycollab.common.service.OptionValService;
import com.mycollab.module.project.CurrentProjectVariables;
import com.mycollab.module.project.ProjectTypeConstants;
import com.mycollab.spring.AppContextUtil;
import com.mycollab.vaadin.AppUI;
import com.vaadin.ui.ListSelect;

import java.util.List;

/**
 * @author MyCollab Ltd
 * @since 5.1.1
 */
// TODO
public class TaskStatusListSelect extends ListSelect {
    public TaskStatusListSelect() {
//        this.setItemCaptionMode(ItemCaptionMode.EXPLICIT);
//        this.setEmptySelectionAllowed(false);
//        this.setMultiSelect(true);
        this.setRows(4);
    }

    @Override
    public void attach() {
        OptionValService optionValService = AppContextUtil.getSpringBean(OptionValService.class);
        List<OptionVal> options = optionValService.findOptionVals(ProjectTypeConstants.TASK,
                CurrentProjectVariables.getProjectId(), AppUI.getAccountId());
        for (OptionVal option : options) {
//            this.addItem(option.getTypeval());
//            this.setItemCaption(option.getTypeval(), UserUIContext.getMessage(StatusI18nEnum.class,
//                    option.getTypeval()));
        }
        super.attach();
    }
}
