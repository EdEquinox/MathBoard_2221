package pt.isec.a21280317.tp_202223

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import pt.isec.a21280317.tp_202223.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSP.setOnClickListener {
            val intent = Intent(this, ConfigSPGameActivity::class.java)
            startActivity(intent)
        }

        binding.btnMP.setOnClickListener {
            val intent = Intent(this, ConfigMPGameActivity::class.java)
            startActivity(intent)
        }

        binding.btnInfo.setOnClickListener {
            val intent = Intent(this, ConfigUserActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_credits, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val intent = Intent(this, ConfigCreditsActivity::class.java)
        startActivity(intent)

        return super.onOptionsItemSelected(item)
    }


}