package dev.worldgen.datapatched.api.loot.modifier;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import dev.worldgen.datapatched.impl.loot.modifier.target.DirectTarget;
import dev.worldgen.datapatched.impl.loot.modifier.target.PatternTarget;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public final class ModifierTargets {
    private ModifierTargets() {}

    public static Predicate<Identifier> direct(Identifier id) {
        return direct(List.of(id));
    }

    public static Predicate<Identifier> direct(Identifier... ids) {
        return direct(Arrays.asList(ids));
    }

    public static Predicate<Identifier> direct(List<Identifier> ids) {
        return new DirectTarget(ids);
    }

    public static Predicate<Identifier> direct(ResourceKey<LootTable> key) {
        return direct(key.identifier());
    }

    public static Predicate<Identifier> direct(Collection<ResourceKey<LootTable>> keys) {
        return direct(keys.stream().map(ResourceKey::identifier).toList());
    }

    public static Predicate<Identifier> pattern(Pattern pattern) {
        return new PatternTarget(pattern);
    }
}
