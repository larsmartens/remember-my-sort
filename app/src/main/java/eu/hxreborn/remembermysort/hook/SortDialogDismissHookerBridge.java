package eu.hxreborn.remembermysort.hook;

import io.github.libxposed.api.XposedInterface;
import io.github.libxposed.api.annotations.AfterInvocation;
import io.github.libxposed.api.annotations.XposedHooker;

@XposedHooker
public final class SortDialogDismissHookerBridge implements XposedInterface.Hooker {
    private SortDialogDismissHookerBridge() {}

    @AfterInvocation
    public static void after(XposedInterface.AfterHookCallback callback) {
        SortDialogDismissHooker.after(callback);
    }
}
