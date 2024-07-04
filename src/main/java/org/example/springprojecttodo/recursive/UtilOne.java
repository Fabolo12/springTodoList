package org.example.springprojecttodo.recursive;

import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class UtilOne implements UtilOneI {

    private UtilTwoI utilTwo;

    public UtilOne(@Lazy final UtilTwoI utilTwo) {
        this.utilTwo = utilTwo;
    }

    public UtilOne setUtilTwo(final UtilTwoI utilTwo) {
        this.utilTwo = utilTwo;
        return this;
    }

    @PostConstruct
    public void init() {
        utilTwo.setUtilOne(this);
    }
}
