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
package com.mycollab.common.service

import com.mycollab.common.domain.MonitorItem
import com.mycollab.module.project.ProjectTypeConstants
import com.mycollab.test.DataSet
import com.mycollab.test.rule.DbUnitInitializerRule
import com.mycollab.test.spring.IntegrationServiceTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDateTime
import java.util.*

@ExtendWith(SpringExtension::class, DbUnitInitializerRule::class)
class MonitorServiceTest : IntegrationServiceTest() {
    @Autowired
    private lateinit var monitorItemService: MonitorItemService

    @Test
    @DataSet
    fun testSaveBatchMonitor() {
        val mon1 = MonitorItem()
        mon1.createdtime = LocalDateTime.now()
        mon1.saccountid = 1
        mon1.type = ProjectTypeConstants.BUG
        mon1.typeid = 1
        mon1.extratypeid = 1
        mon1.user = "hainguyen"
        val items = ArrayList<MonitorItem>()
        items.add(mon1)
        monitorItemService.saveMonitorItems(items)
    }
}
