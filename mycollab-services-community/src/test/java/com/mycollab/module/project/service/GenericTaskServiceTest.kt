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
package com.mycollab.module.project.service

import com.mycollab.db.arguments.BasicSearchRequest
import com.mycollab.db.arguments.DateSearchField
import com.mycollab.db.arguments.NumberSearchField
import com.mycollab.db.arguments.SetSearchField
import com.mycollab.module.project.domain.ProjectTicket
import com.mycollab.module.project.domain.criteria.ProjectTicketSearchCriteria
import com.mycollab.test.DataSet
import com.mycollab.test.rule.DbUnitInitializerRule
import com.mycollab.test.spring.IntegrationServiceTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.text.ParseException
import java.time.LocalDate

@ExtendWith(SpringExtension::class, DbUnitInitializerRule::class)
class GenericTaskServiceTest : IntegrationServiceTest() {
    @Autowired
    private lateinit var genericTaskService: ProjectTicketService

    @DataSet
    @Test
    fun testGenericTaskListFindPageable() {
        val criteria = ProjectTicketSearchCriteria()
        criteria.projectIds = SetSearchField(1)
        criteria.saccountid = NumberSearchField(1)
        val tasks = genericTaskService.findPageableListByCriteria(BasicSearchRequest(criteria)) as List<ProjectTicket>
        assertThat(tasks.size).isEqualTo(2)
        assertThat<ProjectTicket>(tasks).extracting("type", "name").contains(tuple("Project-Risk", "b"), tuple("Project-Bug", "name 1"))
    }

    @DataSet
    @Test
    @Throws(ParseException::class)
    fun testCountTaskOverDue() {
        val d = LocalDate.of(2014, 1, 23)
        val criteria = ProjectTicketSearchCriteria()
        criteria.dueDate = DateSearchField(d, DateSearchField.LESS_THAN)
        criteria.projectIds = SetSearchField(1)
        criteria.saccountid = NumberSearchField(1)
        val tasks = genericTaskService.findPageableListByCriteria(BasicSearchRequest(criteria)) as List<ProjectTicket>
        assertThat(tasks.size).isEqualTo(1)
        assertThat<ProjectTicket>(tasks).extracting("type", "name").contains(tuple("Project-Risk", "b"))
    }

    @DataSet
    @Test
    @Throws(ParseException::class)
    fun testListTaskOverDue() {
        val d = LocalDate.of(2014, 1, 23)

        val criteria = ProjectTicketSearchCriteria()
        criteria.dueDate = DateSearchField(d, DateSearchField.LESS_THAN)
        criteria.projectIds = SetSearchField(1)
        criteria.saccountid = NumberSearchField(1)
        val taskList = genericTaskService.findPageableListByCriteria(BasicSearchRequest(criteria)) as List<ProjectTicket>

        assertThat(taskList.size).isEqualTo(1)
        assertThat<ProjectTicket>(taskList).extracting("type", "name").contains(tuple("Project-Risk", "b"))
    }
}
