package eu.hxreborn.remembermysort.hook;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.annotations.BeforeInvocation;
import io.github.libxposed.api.annotations.XposedHooker;

@XposedHooker
public final class SortCursorHookerBridge implements XposedInterface.Hooker {
    private SortCursorHookerBridge() {}

    @BeforeInvocation
    public static void before(XposedInterface.BeforeHookCallback callback) {
        SortCursorHooker.before(callback);
    }
}
