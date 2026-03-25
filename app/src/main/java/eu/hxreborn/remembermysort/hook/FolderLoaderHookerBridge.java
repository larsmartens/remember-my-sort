package eu.hxreborn.remembermysort.hook;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.annotations.AfterInvocation;
import io.github.libxposed.api.annotations.BeforeInvocation;
import io.github.libxposed.api.annotations.XposedHooker;

@XposedHooker
public final class FolderLoaderHookerBridge implements XposedInterface.Hooker {
    private FolderLoaderHookerBridge() {}

    @BeforeInvocation
    public static void before(XposedInterface.BeforeHookCallback callback) {
        FolderLoaderHooker.before(callback);
    }

    @AfterInvocation
    public static void after(XposedInterface.AfterHookCallback callback) {
        FolderLoaderHooker.after(callback);
    }
}
