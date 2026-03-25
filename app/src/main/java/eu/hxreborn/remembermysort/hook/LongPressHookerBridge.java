package eu.hxreborn.remembermysort.hook;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.annotations.AfterInvocation;
import io.github.libxposed.api.annotations.XposedHooker;

@XposedHooker
public final class LongPressHookerBridge implements XposedInterface.Hooker {
    private LongPressHookerBridge() {}

    @AfterInvocation
    public static void after(XposedInterface.AfterHookCallback callback) {
        LongPressHooker.after(callback);
    }
}
