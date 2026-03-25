package eu.hxreborn.remembermysort.hook;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.annotations.BeforeInvocation;
import io.github.libxposed.api.annotations.XposedHooker;

@XposedHooker
public final class RecentsLoaderHookerBridge implements XposedInterface.Hooker {
    private RecentsLoaderHookerBridge() {}

    @BeforeInvocation
    public static void before(XposedInterface.BeforeHookCallback callback) {
        RecentsLoaderHooker.before(callback);
    }
}
