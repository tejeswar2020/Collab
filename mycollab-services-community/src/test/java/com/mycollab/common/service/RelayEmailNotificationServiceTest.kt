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

import com.mycollab.common.domain.criteria.RelayEmailNotificationSearchCriteria
import com.mycollab.db.arguments.BasicSearchRequest
import com.mycollab.test.DataSet
import com.mycollab.test.rule.DbUnitInitializerRule
import com.mycollab.test.spring.IntegrationServiceTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class, DbUnitInitializerRule::class)
class RelayEmailNotificationServiceTest : IntegrationServiceTest() {
    @Autowired
    private lateinit var relayEmailNotificationService: RelayEmailNotificationService

    @Test
    @DataSet
    fun getFindItems() {
        val criteria = RelayEmailNotificationSearchCriteria()
        val items = relayEmailNotificationService.findPageableListByCriteria(BasicSearchRequest(
                criteria, 0, Integer.MAX_VALUE))
        assertThat(items.size).isEqualTo(1)
    }
}
