package org.samo_lego.taterzens.mixin.accessors;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.syncher.SynchedEntityData;

@Mixin(ClientboundSetEntityDataPacket.class)
public interface ClientboundSetEntityDataPacketAccessor {
    @Mutable
    @Accessor("id")
    int getEntityId();
    @Mutable
    @Accessor("packedItems")
    void setPackedItems(List<SynchedEntityData.DataItem<?>> packedItems);
}
