package com.example.application.components;

import com.vaadin.flow.component.details.Details;
import org.jetbrains.annotations.NotNull;

/**
 * Details component that implements comparable in order to make MapboxLayout search bar methods work. It compares
 * details by their summary text
 */

public class ComparableDetails extends Details implements Comparable {
    public ComparableDetails() {
        super();
    }

    @Override
    public int compareTo(@NotNull Object o) {
        if (o instanceof ComparableDetails) {
            ComparableDetails otherObj = (ComparableDetails) o;
            return getSummaryText().compareTo(otherObj.getSummaryText());
        } else return 0;
    }
}
