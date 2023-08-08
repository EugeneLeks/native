package com.example.anative

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import com.example.anative.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI(application.assets.open("1.BIN").bufferedReader().use{it.readText()})

        val content = stringFromJNI(application.assets.open("1.BIN").bufferedReader().use{it.readText()})

        //вычисляю путь к External Storage и вызываю native метод для сохранения файла
        val saveDestination = getExternalFilesDir(null)?.absolutePath
        saveOnExt(content, saveDestination)
    }
    //доступно ли external storage на чтение и запись
    fun isExternalStorageWritable(): Boolean{
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }
    /**
     * A native method that is implemented by the 'anative' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(string: String): String
    external fun saveOnExt(text: String, path: String?)

    companion object {
        // Used to load the 'anative' library on application startup.
        init {
            System.loadLibrary("anative")
        }
    }
}