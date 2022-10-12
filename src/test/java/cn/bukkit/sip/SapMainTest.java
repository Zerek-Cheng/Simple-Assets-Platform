package cn.bukkit.sip;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ControllerTest.class})
public class SapMainTest {
    @Test
    public void test() {
        System.out.println("test");
    }
}
