package com.solvd.laba.jjaccomando.enums.luggagecategories;

enum LuggageCategories {
    FRAGILE("Handle with care."),
    OVERSIZE("Requires special handling"),
    PERISHABLE("Keep refrigerated"),
    VALUABLE("High security required");

    final String description;

    LuggageCategories(String description) {
        this.description = description;
    }
}
