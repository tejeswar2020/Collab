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

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MyCollab Ltd.
 * @since 4.4.0
 */
public class Version {
    public static final String THEME_VERSION = "mycollab_20181214";

    public static String getVersion() {
        return "7.0.0";
    }

    public static LocalDate getReleasedDate() {
        return LocalDate.of(2017, 5, 25);
    }

    static int[] getVersionNumbers(String ver) {
        Matcher m = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(beta(\\d*))?").matcher(ver);
        if (!m.matches())
            throw new IllegalArgumentException("Malformed FW version");

        return new int[]{Integer.parseInt(m.group(1)),  // majorMain
                Integer.parseInt(m.group(2)),             // minor
                Integer.parseInt(m.group(3)),             // rev.
                m.group(4) == null ? Integer.MAX_VALUE    // no beta suffix
                        : m.group(5).isEmpty() ? 1        // "beta"
                        : Integer.parseInt(m.group(5))    // "beta3"
        };
    }

    public static boolean isEditionNewer(String testFW) {
        return isEditionNewer(testFW, getVersion());
    }

    /**
     * @param testFW
     * @param baseFW
     * @return true if testFW is greater than baseFW
     */
    public static boolean isEditionNewer(String testFW, String baseFW) {
        try {
            int[] testVer = getVersionNumbers(testFW);
            int[] baseVer = getVersionNumbers(baseFW);

            for (int i = 0; i < testVer.length; i++)
                if (testVer[i] != baseVer[i])
                    return testVer[i] > baseVer[i];

            return false;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
