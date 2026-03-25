package eu.hxreborn.remembermysort.hook;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.annotations.AfterInvocation;
import io.github.libxposed.api.annotations.BeforeInvocation;
import io.github.libxposed.api.annotations.XposedHooker;

@XposedHooker
public final class DirectoryLoaderHookerBridge implements XposedInterface.Hooker {
    private DirectoryLoaderHookerBridge() {}

    @BeforeInvocation
    public static void before(XposedInterface.BeforeHookCallback callback) {
        DirectoryLoaderHooker.before(callback);
    }

    @AfterInvocation
    public static void after(XposedInterface.AfterHookCallback callback) {
        DirectoryLoaderHooker.after(callback);
    }
}
