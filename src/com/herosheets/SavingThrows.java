package com.herosheets;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public final class SavingThrows  implements java.io.Serializable {
    private final int reflex;
    private final int fortitude;
    private final int will;
    
    @JsonCreator
    public SavingThrows(@JsonProperty("fortitude") final int fortitude,
                        @JsonProperty("will") final int will,
                        @JsonProperty("reflex") final int reflex) {
        this.reflex = reflex;
        this.will = will;
        this.fortitude = fortitude;
    }

    public int getByAbbreviation(String abbreviation) {
        System.out.println("This.will" + this.will);
        switch (abbreviation) {
            case "FORT":
                return fortitude;
            case "REF":
                return reflex;
            case "WILL":
                return will;
            default:
                return 0;
        }

    }
}
