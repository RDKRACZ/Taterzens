package org.samo_lego.taterzens.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.samo_lego.taterzens.Taterzens.LOGGER;
import static org.samo_lego.taterzens.Taterzens.MODID;

public class TaterConfig {
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    @SerializedName("// Language file to use.")
    public final String _comment_language = "";
    /**
     * Language file used by Taterzens.
     *
     * Located at $minecraftFolder/config/Taterzens/$lang.json
     */
    public String language = "en_us";

    @SerializedName("// Whether to remove Taterzens from registry sync. Auto applied in Fabric.")
    public final String _comment_disableRegistrySync1 = "";
    @SerializedName("// If using Forge however, this will disable WHOLE registry sync. Proceed with CAUTION.")
    public final String _comment_disableRegistrySync2 = "";
    /**
     * Whether to disable Forge's registry sync.
     * (This marks mod as serverside.)
     *
     * If using Forge, the whole thing is not synced. Use carefully!
     */
    @SerializedName("disable_registry_sync")
    public boolean disableRegistrySync = false;

    @SerializedName("// After how many ticks Taterzens should be cleared from tablist.")
    public final String _comment_taterzenTablistTimeout1 = "";
    @SerializedName("// Some delay is needed, otherwise clients don't fetch their skins.")
    public final String _comment_taterzenTablistTimeout2 = "";
    @SerializedName("// If you want them to stay on the tablist, set this to -1.")
    public final String _comment_taterzenTablistTimeout3 = "";
    /**
     * After how many ticks Taterzens should be cleared from tablist.
     * Some delay is needed, otherwise clients don't fetch their skins.
     */
    @SerializedName("taterzen_tablist_timeout")
    public int taterzenTablistTimeout = 30;


    @SerializedName("// Whether to remind you that if FabricTailor mod is installed,")
    public final String _comment_fabricTailorAdvert1 = "";
    @SerializedName("// it has some built-in skin swapping functionality for Taterzens as well.")
    public final String _comment_fabricTailorAdvert2 = "";
    /**
     * Whether to remind you that if FabricTailor
     * mod is installed, it has some more skin functionality.
     *
     * @see <a href="https://github.com/samolego/FabricTailor">FabricTailor</a>
     */
    @SerializedName("post_fabrictailor_advert")
    public boolean fabricTailorAdvert = false;

    @SerializedName("// Whether to save all permissions into permissions.toml file if LuckPerms is loaded.")
    public final String _comment_savePermsFile = "";
    @SerializedName("save_permissions_file")
    public boolean savePermsFile = true;

    @SerializedName("// Whether to cancel sending info that Taterzen has executed a command to ops.")
    public final String _comment_hideOpsMessage = "";
    @SerializedName("hide_ops_message")
    public boolean hideOpsMessage = true;

    public Defaults defaults = new Defaults();
    public Path path = new Path();
    public Messages messages = new Messages();
    public Permissions perms = new Permissions();
    public Bungee bungee = new Bungee();

    /**
     * Some permission stuff.
     * If you are looking for permission nodes,
     * see the generated permission.json file.
     *
     * (You must have LuckPerms installed.)
     */
    public static class Permissions {
        @SerializedName("// Permission level required to execute /npc command.")
        public final String _comment_npcCommandPermissionLevel1 = "";
        @SerializedName("// Valid only if LuckPerms isn't present.")
        public final String _comment_npcCommandPermissionLevel2 = "";
        /**
         * Permission level required to execute /npc command.
         * Valid only if LuckPerms isn't present.
         */
        @SerializedName("npc_command_permission_level")
        public int npcCommandPermissionLevel = 2;


        @SerializedName("// Permission level required to execute / taterzens command.")
        public final String _comment_taterzensCommandPermissionLevel1 = "";
        @SerializedName("// Again, valid only if LuckPerms isn't present.")
        public final String _comment_taterzensCommandPermissionLevel2 = "";
        /**
         * Permission level required to execute /taterzens command.
         * Valid only if LuckPerms isn't present.
         */
        @SerializedName("taterzens_command_permission_level")
        public int taterzensCommandPermissionLevel = 4;

        @SerializedName("// Whether to allow players to set the permission level")
        public final String _comment_allowSettingHigherPermissionLevel1 = "";
        @SerializedName("// of Taterzen higher than their own. Careful! This could")
        public final String _comment_allowSettingHigherPermissionLevel2 = "";
        @SerializedName("// enable players to bypass their permission level with NPC.")
        public final String _comment_allowSettingHigherPermissionLevel3 = "";
        @SerializedName("allow_setting_higher_perm_level")
        public boolean allowSettingHigherPermissionLevel = false;
    }

    /**
     * Default {@link org.samo_lego.taterzens.npc.TaterzenNPC} settings.
     */
    public static class Defaults {
        @SerializedName("// Default settings for new Taterzens.")
        public final String _comment_name = "";
        /**
         * Default Taterzen name
         */
        public String name = "Taterzen";
        /**
         * Whether Taterzens should be leashable.
         */
        public boolean leashable = false;
        public boolean pushable = false;

        @SerializedName("// Default command permission level of Taterzen.")
        public final String _comment_commandPermissionLevel = "";
        /**
         * Default command permission level of Taterzen.
         */
        @SerializedName("command_permission_level")
        public int commandPermissionLevel = 4;

        @SerializedName("// Default sounds for Taterzens. Set to [] to mute them.")
        public final String _comment_sounds = "";
        /**
         * Default Taterzen death sound.
         * Can be null to not produce any sounds.
         */
        @SerializedName("death_sounds")
        public ArrayList<String> deathSounds = new ArrayList<>(Arrays.asList(
                "entity.player.death"
        ));
        /**
         * Default Taterzen hurt / hit sound.
         * Can be null to not produce any sounds.
         */
        @SerializedName("hurt_sounds")
        public ArrayList<String> hurtSounds = new ArrayList<>(Arrays.asList(
                "entity.player.hurt"
        ));
        /**
         * Default Taterzen ambient sound.
         * Can be null to not produce any sounds.
         */
        @SerializedName("ambient_sounds")
        public ArrayList<String> ambientSounds = new ArrayList<>();

        @SerializedName("// Whether Taterzen is invulnerable by default.")
        public final String _comment_invulnerable = "";
        /**
         * Whether Taterzen is invulnerable by default.
         */
        public boolean invulnerable = true;

        @SerializedName("// Enable jumps when Taterzen is in attack mode.")
        public final String _comment_jumpWhileAttacking = "";
        /**
         * Whether to enable jumps when Taterzen
         * is in attack mode.
         */
        @SerializedName("jump_while_attacking")
        public boolean jumpWhileAttacking = true;
    }

    /**
     * Settings for Taterzen's messages
     */
    public static class Messages {
        @SerializedName("// Default delay between each message, in ticks.")
        public final String _comment_messageDelay = "";
        /**
         * Default delay between each message, in ticks.
         */
        @SerializedName("message_delay")
        public int messageDelay = 100;
        @SerializedName("// Whether to exit message editor mode after editing a message.")
        public final String _comment_exitEditorAfterMsgEdit = "";
        /**
         * Whether to exit message editor mode after editing a message.
         */
        @SerializedName("exit_editor_after_msg_edit")
        public boolean exitEditorAfterMsgEdit = true;

        @SerializedName("// Message format. First %s is replaced with name, second one with message.")
        public final String _comment_structure = "";
        public String structure = "%s -> you: %s";
    }

    /**
     * Settings for path visualisation.
     */
    public static class Path {
        public Color color = new Color();
        /**
         * Color of particles used in path editor.
         * Accepts RGB values (0 - 255).
         */
        public static class Color {
            @SerializedName("// Which color of particles to use in path editor. Use RGB values ( 0 - 255 ).")
            public final String _comment = "";
            public float red = 0;
            public float green = 255 ;
            public float blue = 255;
        }
    }


    /**
     * Settings for proxy connections.
     */
    public static class Bungee {

        @SerializedName("// Whether to enable bungee commands feature fo NPCs.")
        public final String _comment_enableCommands = "";
        @SerializedName("enable_commands")
        public boolean enableCommands = false;

        @SerializedName("// Bungee servers to be listed in command suggestions.")
        public final String _comment_servers = "";
        public ArrayList<String> servers = new ArrayList<>(Arrays.asList("lobby", "minigames", "factions"));
    }


    /**
     * Loads config file.
     *
     * @param file file to load the language file from.
     * @return TaterzenLanguage object
     */
    public static TaterConfig loadConfigFile(File file) {
        TaterConfig config = null;
        if (file.exists()) {
            try (BufferedReader fileReader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8)
            )) {
                config = gson.fromJson(fileReader, TaterConfig.class);
            } catch (IOException e) {
                throw new RuntimeException(MODID + " Problem occurred when trying to load config: ", e);
            }
        }
        if(config == null)
            config = new TaterConfig();

        config.saveConfigFile(file);

        return config;
    }

    /**
     * Saves the config to the given file.
     *
     * @param file file to save config to
     */
    public void saveConfigFile(File file) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            gson.toJson(this, writer);
        } catch (IOException e) {
            LOGGER.error("Problem occurred when saving config: " + e.getMessage());
        }
    }

    /**
     * Changes values of current object with reflection,
     * in order to keep the same object.
     * (that still allows in-game editing)
     *
     * @param file file to read new config from
     */
    public void reload(File file) {
        TaterConfig newConfig = loadConfigFile(file);
        this.refreshFields(this, newConfig);
    }

    private void refreshFields(Object oldConfig, Object newConfig) {
        try {
            for(Field field : oldConfig.getClass().getFields()) {
                Class<?> type = field.getType();

                if(Modifier.isFinal(type.getModifiers()) && !type.isPrimitive())
                    continue;

                field.setAccessible(true);
                Object value = field.get(newConfig);

                // We overwrite primitives and strings
                if (type.isPrimitive() || type.equals(String.class) || type.equals(ArrayList.class)) {
                    field.set(oldConfig, value);
                } else {
                    // Recursion
                    this.refreshFields(field.get(oldConfig), value);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
