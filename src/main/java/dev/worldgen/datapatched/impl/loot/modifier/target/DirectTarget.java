package dev.worldgen.datapatched.impl.loot.modifier.target;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public record DirectTarget(List<Identifier> ids) implements Predicate<Identifier> {
    public static final Codec<DirectTarget> CODEC = Codec.either(Identifier.CODEC, Identifier.CODEC.listOf()).xmap(
        either -> either.map(List::of, Function.identity()),
        ids -> ids.size() == 1 ? Either.left(ids.getFirst()) : Either.right(ids)
    ).xmap(DirectTarget::new, DirectTarget::ids);

    @Override
    public boolean test(Identifier identifier) {
        return ids.contains(identifier);
    }
}
