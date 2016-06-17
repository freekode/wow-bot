package org.freekode.wowbot;

import org.freekode.wowbot.entity.fishing.FishingKitEntity;
import org.freekode.wowbot.entity.fishing.FishingOptionsEntity;
import org.freekode.wowbot.tools.ConfigKeys;
import org.freekode.wowbot.tools.StaticFunc;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class TestSimple {
    @Test
    public void test() throws InvocationTargetException, IllegalAccessException {
        Map<String, Object> config = StaticFunc.loadYaml(ConfigKeys.YAML_CONFIG_FILENAME, "fishing");
        FishingOptionsEntity optionsEntity = new FishingOptionsEntity(config);

        for (FishingKitEntity kit : optionsEntity.getKits()) {
            System.out.println(kit);
        }

        System.out.println(optionsEntity.getKits().size());
    }
}
