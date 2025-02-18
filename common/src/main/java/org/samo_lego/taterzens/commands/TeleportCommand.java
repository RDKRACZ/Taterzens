package org.samo_lego.taterzens.commands;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;
import net.minecraft.world.phys.Vec3;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;
import static org.samo_lego.taterzens.Taterzens.config;
import static org.samo_lego.taterzens.commands.NpcCommand.selectedTaterzenExecutor;
import static org.samo_lego.taterzens.compatibility.LoaderSpecific.permissions$checkPermission;

public class TeleportCommand {

    public static void registerNode(LiteralCommandNode<CommandSourceStack> npcNode) {
        LiteralCommandNode<CommandSourceStack> tpNode = literal("tp")
                .requires(src -> permissions$checkPermission(src, "taterzens.npc.tp", config.perms.npcCommandPermissionLevel))
                .then(argument("destination", EntityArgument.entity())
                        .executes(context -> teleportTaterzen(context, EntityArgument.getEntity(context, "destination").position()))
                )
                .then(argument("position", Vec3Argument.vec3())
                        .executes(context -> teleportTaterzen(context, Vec3Argument.getCoordinates(context, "position").getPosition(context.getSource())))
                )
                .build();

        npcNode.addChild(tpNode);
    }

    private static int teleportTaterzen(CommandContext<CommandSourceStack> context, Vec3 destination) throws CommandSyntaxException {
        CommandSourceStack source = context.getSource();
        return selectedTaterzenExecutor(source.getEntityOrException(), taterzen -> {
            taterzen.teleportToWithTicket(destination.x(), destination.y(), destination.z());
        });
    }
}
