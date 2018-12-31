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
package com.mycollab.core;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author MyCollab Ltd.
 * @since 5.0.6
 */
public class VersionTest {
    @Test
    public void testHigherVersion() {
        assertFalse(Version.isEditionNewer("5.0.5", "5.0.6"));
        assertFalse(Version.isEditionNewer("5.0.5", "5.0.5"));
        assertTrue(Version.isEditionNewer("5.0.5", "5.0.4"));
        assertTrue(Version.isEditionNewer("6.0.0", "5.0.4"));
    }
}
