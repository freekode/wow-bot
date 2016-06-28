package org.freekode.wowbot;

import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TestSimple {
    @Test
    public void testSimple() throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<FishingKitEntity> kits = new ArrayList<>();
        kits.add(FishingKitEntity.getStandard());
        FishingOptionsEntity originalOptions = new FishingOptionsEntity("q", 1, kits);


        FishingOptionsEntity copy = FishingOptionsEntity.class.newInstance();

        for (Field field : FishingOptionsEntity.class.getDeclaredFields()) {
            if (field.getType() == List.class) {
                System.out.println(field.getGenericType());
            } else {
                Object fieldValue = field.get(originalOptions);
                field.set(copy, fieldValue);
            }
        }

        System.out.println(copy.getFailTryings());
    }
}
