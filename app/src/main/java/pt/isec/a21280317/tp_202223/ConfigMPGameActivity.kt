package pt.isec.a21280317.tp_202223

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.isec.a21280317.tp_202223.databinding.ActivityConfigMpgameBinding

class ConfigMPGameActivity : AppCompatActivity(){

    lateinit var binding: ActivityConfigMpgameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigMpgameBinding.inflate(layoutInflater)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


    }

}
