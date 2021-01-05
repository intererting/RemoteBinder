package com.yly.remotebinder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yly.comm.Common
import dalvik.system.DexClassLoader
import kotlinx.android.synthetic.main.activity_test_plugin.*
import java.io.File

class PluginTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_plugin)

        val apkName = "plugin-debug.apk"

        copyPlugin.setOnClickListener {
            val inputStream = assets.open(apkName)
            val outputStream = File(filesDir, apkName).outputStream()
            inputStream.copyTo(outputStream)
            inputStream.close()
            outputStream.close()
        }

        testPlugin.setOnClickListener {
            val dexOutputDir = getDir("dex", 0);
            val libPath = filesDir.absolutePath + File.separator + apkName;
            val loader = DexClassLoader(
                libPath,
                dexOutputDir.absolutePath,
                applicationInfo.nativeLibraryDir,
                classLoader
            )
            val clazz = loader.loadClass("com.yly.plugin.PluginImpl")
            val localConstructor = clazz.getConstructor()
            val mPluginInterface = localConstructor.newInstance() as Common
            println(mPluginInterface.test(10, 4))
        }
    }
}