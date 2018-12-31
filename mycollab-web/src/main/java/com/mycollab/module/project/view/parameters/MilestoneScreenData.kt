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
package com.mycollab.module.project.view.parameters

import com.mycollab.module.project.domain.Milestone
import com.mycollab.module.project.domain.criteria.MilestoneSearchCriteria
import com.mycollab.vaadin.mvp.ScreenData

/**
 * @author MyCollab Ltd
 * @since 6.0.0
 */
object MilestoneScreenData {
    class Read(params: Int) : ScreenData<Int>(params)

    class Edit(params: Milestone) : ScreenData<Milestone>(params)

    class Add(params: Milestone) : ScreenData<Milestone>(params)

    class Search(params: MilestoneSearchCriteria) : ScreenData<MilestoneSearchCriteria>(params)

    class Roadmap : ScreenData<Any>(null)
}