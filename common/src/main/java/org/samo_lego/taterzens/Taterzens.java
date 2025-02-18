package org.samo_lego.taterzens;

import com.google.gson.JsonObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.samo_lego.taterzens.api.professions.TaterzenProfession;
import org.samo_lego.taterzens.npc.TaterzenNPC;
import org.samo_lego.taterzens.storage.TaterConfig;
import org.samo_lego.taterzens.util.LanguageUtil;
import org.samo_lego.taterzens.util.PermissionExtractor;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;

public class Taterzens {

    public static final String MODID = "taterzens";
    public static boolean DISGUISELIB_LOADED;

    /**
     * Configuration file.
     */
    public static TaterConfig config;
    /**
     * Language file.
     */
    public static JsonObject lang;
    public static final Logger LOGGER = (Logger) LogManager.getLogger(MODID);
    /**
     * List of **loaded** {@link TaterzenNPC TaterzenNPCs}.
     */
    public static final LinkedHashSet<TaterzenNPC> TATERZEN_NPCS = new LinkedHashSet<>();

    public static final HashMap<ResourceLocation, TaterzenProfession> PROFESSION_TYPES = new HashMap<>();

    /**
     * Taterzen entity type. Used server - only, as it is replaced with vanilla type
     * when packets are sent.
     */
    public static EntityType<TaterzenNPC> TATERZEN_TYPE;

    public static File taterDir;
    public static File presetsDir;


    /**
     * Whether LuckPerms mod is loaded.
     * @see <a href="https://luckperms.net/">LuckPerms website</a>.
     */
    public static boolean LUCKPERMS_LOADED;

    public static boolean SERVER_TRANSLATIONS_LOADED;

    public static boolean FABRICTAILOR_LOADED;


    public static void onInitialize() {
        if (!taterDir.exists() && !taterDir.mkdirs())
            throw new RuntimeException(String.format("[%s] Error creating directory!", MODID));
        presetsDir = taterDir;
        taterDir = taterDir.getParentFile();
        File configFile = new File(taterDir + "/config.json");
        config = TaterConfig.loadConfigFile(configFile);

        LanguageUtil.setupLanguage();

        if(LUCKPERMS_LOADED && config.savePermsFile) {
            PermissionExtractor.extractPermissionsFile(new File(taterDir + "/permissions.toml"));
        }
    }
}
