package org.example.springprojecttodo.recursive;

import org.springframework.stereotype.Component;

@Component
public class UtilTwo implements UtilTwoI {

    private UtilOneI utilOne;

    public UtilTwo setUtilOne(final UtilOneI utilOne) {
        this.utilOne = utilOne;
        return this;
    }
}
