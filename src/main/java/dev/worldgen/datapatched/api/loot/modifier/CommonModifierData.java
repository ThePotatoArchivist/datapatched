package dev.worldgen.datapatched.api.loot.modifier;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.worldgen.datapatched.impl.Datapatched;
import dev.worldgen.datapatched.impl.loot.modifier.target.DirectTarget;
import dev.worldgen.datapatched.impl.loot.modifier.target.PatternTarget;

import java.util.function.Predicate;

import net.minecraft.resources.Identifier;

public record CommonModifierData(Predicate<Identifier> target, int priority) {
    @Deprecated
    private static final Identifier UNKNOWN_ID = Datapatched.id("unknown");
    private static final Codec<Predicate<Identifier>> TARGET_CODEC = Codec.either(PatternTarget.CODEC, DirectTarget.CODEC).flatComapMap(
            Either::unwrap,
            predicate -> switch (predicate) {
                case PatternTarget patternTarget -> DataResult.success(Either.left(patternTarget));
                case DirectTarget directTarget -> DataResult.success(Either.right(directTarget));
                default -> DataResult.error(() -> "Can only serialize DirectTarget or PatternTarget, got " + predicate);
            }
    );

    public static MapCodec<CommonModifierData> codec(int defaultPriority) {
        return RecordCodecBuilder.mapCodec(instance -> instance.group(
            TARGET_CODEC.fieldOf("targets").forGetter(CommonModifierData::target),
            Codec.INT.fieldOf("priority").orElse(defaultPriority).forGetter(CommonModifierData::priority)
        ).apply(instance, CommonModifierData::new));
    }
}

