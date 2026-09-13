package dev.worldgen.datapatched.impl.loot.modifier.target;

import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public record PatternTarget(Pattern pattern) implements Predicate<Identifier> {
    public static final Codec<PatternTarget> CODEC = ExtraCodecs.PATTERN
        .xmap(PatternTarget::new, PatternTarget::pattern);

    @Override
    public boolean test(Identifier identifier) {
        return pattern.asPredicate().test(identifier.toString());
    }
}
