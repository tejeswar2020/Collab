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
package com.mycollab.module.tracker.service

import com.mycollab.db.arguments.BasicSearchRequest
import com.mycollab.db.arguments.NumberSearchField
import com.mycollab.db.arguments.StringSearchField
import com.mycollab.module.tracker.domain.SimpleVersion
import com.mycollab.module.tracker.domain.Version
import com.mycollab.module.tracker.domain.criteria.VersionSearchCriteria
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
import java.time.LocalDateTime

@ExtendWith(SpringExtension::class, DbUnitInitializerRule::class)
class VersionServiceTest : IntegrationServiceTest() {

    @Autowired
    private lateinit var versionService: VersionService

    private val criteria: VersionSearchCriteria
        get() {
            val criteria = VersionSearchCriteria()
            criteria.saccountid = NumberSearchField(1)
            criteria.projectId = NumberSearchField(1)
            return criteria
        }

    @DataSet
    @Test
    @Throws(ParseException::class)
    fun testGetListVersions() {
        val versions = versionService.findPageableListByCriteria(BasicSearchRequest(criteria)) as List<SimpleVersion>

        assertThat(versions.size).isEqualTo(4)
        assertThat<SimpleVersion>(versions).extracting("id", "description", "status",
                "name", "numBugs", "numOpenBugs").contains(
                tuple(4, "Version 4.0.0", "Open", "4.0.0", 0, 0),
                tuple(3, "Version 3.0.0", "Closed", "3.0.0", 1, 1),
                tuple(2, "Version 2.0.0", "Closed", "2.0.0", 2, 1),
                tuple(1, "Version 1.0.0", "Open", "1.0.0", 1, 1))
    }

    @DataSet
    @Test
    fun testTotalCount() {
        val versions = versionService.findPageableListByCriteria(BasicSearchRequest(criteria))
        assertThat(versions.size).isEqualTo(4)
    }

    @DataSet
    @Test
    fun testFindVersionById() {
        val criteria = VersionSearchCriteria()
        criteria.id = NumberSearchField(1)

        val versions = versionService.findPageableListByCriteria(BasicSearchRequest(criteria)) as List<SimpleVersion>
        assertThat(versions.size).isEqualTo(1)
        assertThat<SimpleVersion>(versions).extracting("id", "description", "status",
                "name", "numBugs", "numOpenBugs").contains(
                tuple(1, "Version 1.0.0", "Open", "1.0.0", 1, 1))
    }

    @DataSet
    @Test
    fun testFindByCriteria() {
        val criteria = criteria
        criteria.id = NumberSearchField(2)
        criteria.status = StringSearchField.and("Closed")
        criteria.versionname = StringSearchField.and("2.0.0")

        val versions = versionService.findPageableListByCriteria(BasicSearchRequest(criteria)) as List<SimpleVersion>
        assertThat(versions.size).isEqualTo(1)
        assertThat<SimpleVersion>(versions).extracting("id", "description", "status",
                "name", "numBugs", "numOpenBugs").contains(
                tuple(2, "Version 2.0.0", "Closed", "2.0.0", 2, 1))
    }

    @DataSet
    @Test
    fun testSaveVersion() {
        val version = Version()
        version.projectid = 1
        version.duedate = LocalDate.of(2014, 10, 6)
        version.name = "sss"
        version.createduser = "hai79"
        version.saccountid = 1
        version.description = "a"
        version.status = "Open"

        val versionId = versionService.saveWithSession(version, "hai79")
        assertThat(versionId > 0).isEqualTo(true)
    }
}
