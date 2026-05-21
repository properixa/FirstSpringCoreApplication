package com.application.id;

import org.springframework.stereotype.Component;

@Component
public class IntegerIdGenerator implements IdGenerator{
    private int lastValue = 1;

    @Override
    public int getNewId() {
       return lastValue++;
    }
}
