package org.samo_lego.taterzens.compatibility;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.minecraft.commands.CommandSourceStack;

/**
 * Permission checker.
 *
 * In its own class since we do not want to depend
 * on it fully, but use it just if luckperms mod is loaded.
 */
public class PermissionHelper {

    /**
     * Checks permission of commandSource using Lucko's
     * permission API.
     * If permission isn't set, it will require the commandSource
     * to have permission level of 4 (op).
     *
     * @param commandSource commandSource to check permission for.
     * @param permission permission node to check.
     * @param fallbackLevel level to require if permission isn't set
     * @return true if commandSource has the permission, otherwise false
     */
    public static boolean checkPermission(CommandSourceStack commandSource, String permission, int fallbackLevel) {
        // Enable command blocks
        return commandSource.getEntity() == null || Permissions.check(commandSource, permission, fallbackLevel);
    }
}
