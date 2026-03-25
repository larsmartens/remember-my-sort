package eu.hxreborn.remembermysort

import android.database.Cursor
import eu.hxreborn.remembermysort.hook.DirectoryLoaderHookerBridge
import eu.hxreborn.remembermysort.hook.FolderLoaderHookerBridge
import eu.hxreborn.remembermysort.hook.LongPressHookerBridge
import eu.hxreborn.remembermysort.hook.RecentsLoaderHookerBridge
import eu.hxreborn.remembermysort.hook.SortCursorHookerBridge
import eu.hxreborn.remembermysort.hook.SortDialogDismissHookerBridge
import io.github.libxposed.api.XposedInterface
import io.github.libxposed.api.XposedModule
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam
import io.github.libxposed.api.XposedModuleInterface.PackageLoadedParam

internal lateinit var module: RememberMySortModule

class RememberMySortModule(
    base: XposedInterface,
    param: ModuleLoadedParam,
) : XposedModule(base, param) {
    init {
        module = this
        log("v${BuildConfig.VERSION_NAME} loaded")
    }

    override fun onPackageLoaded(param: PackageLoadedParam) {
        if (!param.isFirstPackage) return

        hookSortCursor(param.classLoader)
        hookSortListFragment(param.classLoader)
        hookLoaders(param.classLoader)

        log("Module initialized in ${param.packageName}")
    }

    private fun hookSortCursor(classLoader: ClassLoader) {
        val className = "com.android.documentsui.sorting.SortModel"
        runCatching {
            val sortModel = classLoader.loadClass(className)
            val lookup = classLoader.loadClass("com.android.documentsui.base.Lookup")
            hook(
                sortModel.getDeclaredMethod("sortCursor", Cursor::class.java, lookup),
                SortCursorHookerBridge::class.java,
            )
            log("Hooked $className.sortCursor")
        }.onFailure { e ->
            log("Failed to hook $className.sortCursor", e)
        }.getOrThrow()
    }

    private fun hookSortListFragment(classLoader: ClassLoader) {
        for (className in SORT_FRAGMENT_CLASSES) {
            runCatching {
                val clazz = classLoader.loadClass(className)
                hook(clazz.getMethod("onStart"), LongPressHookerBridge::class.java)
                hook(clazz.getMethod("onStop"), SortDialogDismissHookerBridge::class.java)
                log("Hooked $className.onStart/onStop")
            }.onFailure {
                log("$className not found, skipping")
            }
        }
    }

    private fun hookLoaders(classLoader: ClassLoader) {
        for ((className, hooker) in LOADERS) {
            runCatching {
                val loaderClass = classLoader.loadClass(className)
                hook(loaderClass.getDeclaredMethod("loadInBackground"), hooker)
                log("Hooked $className.loadInBackground")
            }.onFailure {
                log("$className not found, skipping")
            }
        }
    }

    companion object {
        private val SORT_FRAGMENT_CLASSES = listOf(
            "com.android.documentsui.sorting.SortListFragment",
            "com.google.android.documentsui.sorting.SortListFragment",
        )

        private val LOADERS: List<Pair<String, Class<out XposedInterface.Hooker>>> = listOf(
            "com.android.documentsui.DirectoryLoader" to DirectoryLoaderHookerBridge::class.java,
            "com.android.documentsui.loaders.FolderLoader" to FolderLoaderHookerBridge::class.java,
            "com.android.documentsui.RecentsLoader" to RecentsLoaderHookerBridge::class.java,
        )

        fun log(
            msg: String,
            t: Throwable? = null,
        ) {
            if (t != null) module.log(msg, t) else module.log(msg)
        }
    }
}
