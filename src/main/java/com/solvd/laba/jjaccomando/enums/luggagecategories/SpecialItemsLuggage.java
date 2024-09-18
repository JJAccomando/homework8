package com.solvd.laba.jjaccomando.enums.luggagecategories;

public enum SpecialItemsLuggage {
    SURFBOARDS(LuggageCategories.FRAGILE),
    AUDIO_EQUIPMENT(LuggageCategories.VALUABLE),
    VISUAL_EQUIPMENT(LuggageCategories.VALUABLE),
    MOBILITY_DEVICES(LuggageCategories.OVERSIZE),
    FIREARMS(LuggageCategories.VALUABLE),
    HUMAN_REMAINS(LuggageCategories.VALUABLE),
    HUMAN_ORGANS(LuggageCategories.PERISHABLE),
    MUSICAL_INSTRUMENTS(LuggageCategories.FRAGILE),
    HUNTING_EQUIPMENT(LuggageCategories.VALUABLE),
    CAMPING_EQUIPMENT(LuggageCategories.OVERSIZE),
    FISHING_EQUIPMENT(LuggageCategories.OVERSIZE),
    SCUBA_GEAR(LuggageCategories.OVERSIZE),
    SKATEBOARDS(LuggageCategories.OVERSIZE),
    SKIS(LuggageCategories.OVERSIZE),
    TENNIS_EQUIPMENT(LuggageCategories.OVERSIZE),
    GOLF_CLUBS(LuggageCategories.OVERSIZE),
    BICYCLES(LuggageCategories.OVERSIZE),
    BASEBALL_EQUIPMENT(LuggageCategories.OVERSIZE);

    private final LuggageCategories category;

    SpecialItemsLuggage(LuggageCategories category) {
        this.category = category;
    }

    public String getCategory() {
        return category.name();
    }

    public String getCategoryDescription() {
        return category.description;
    }
}
