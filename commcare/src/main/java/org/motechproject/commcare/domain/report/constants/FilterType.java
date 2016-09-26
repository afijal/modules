package org.motechproject.commcare.domain.report.constants;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public enum FilterType {
    NUMERIC("numeric"),
    DATE("date"),
    CHOICE_LIST("choice_list"),
    DYNAMIC_CHOICE_LIST("dynamic_choice_list");

    private final String type;

    FilterType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public static FilterType getFilterTypeFromTypeValue(String typeValue) {
        FilterType[] filterTypes = FilterType.values();
        for (FilterType filterType : filterTypes) {
            if (filterType.getType().equals(typeValue)) {
                return filterType;
            }
        }
        throw new IllegalArgumentException("Invalid filter type value: " + typeValue);
    }

    public static class FilterTypeDeserializer implements JsonDeserializer<FilterType> {

        @Override
        public FilterType deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String value = json.getAsString();
            return FilterType.getFilterTypeFromTypeValue(value);
        }
    }
}