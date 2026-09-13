package dev.worldgen.datapatched.impl.loot.modifier.target;

import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.function.Predicate;

public record DirectTarget(List<Identifier> ids) implements Predicate<Identifier> {
    public static final Codec<DirectTarget> CODEC = ExtraCodecs.compactListCodec(Identifier.CODEC)
            .xmap(DirectTarget::new, DirectTarget::ids);

    @Override
    public boolean test(Identifier identifier) {
        return ids.contains(identifier);
    }
}
