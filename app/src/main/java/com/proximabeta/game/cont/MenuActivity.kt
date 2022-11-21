package com.proximabeta.game.cont

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.proximabeta.game.cont.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    lateinit var iGamerBind: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        iGamerBind = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(iGamerBind.root)

        val nameTxt = iGamerBind.nameET
        iGamerBind.strtGm.setOnClickListener {
            if (TextUtils.isEmpty(nameTxt.text.toString())) {
                nameTxt.error = "Field is empty"
            } else {
                val intent = Intent(this, BallsActivity::class.java)
                intent.putExtra("name", nameTxt.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}