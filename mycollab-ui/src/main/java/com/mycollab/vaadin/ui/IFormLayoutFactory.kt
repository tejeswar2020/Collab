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
 * along with this program.  If not, see <http:></http:>//www.gnu.org/licenses/>.
 */
package com.mycollab.vaadin.ui

import com.vaadin.data.HasValue
import com.vaadin.ui.AbstractComponent

/**
 * @author MyCollab Ltd
 * @since 5.3.2
 */
interface IFormLayoutFactory {
    val layout: AbstractComponent

    fun attachField(propertyId: Any, field: HasValue<*>): HasValue<*>

    fun bindFields(): Set<String>
}
