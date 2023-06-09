package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import database.CompanyDatabase
import database.entities.Application
import database.repositories.AdminRepository
import database.repositories.ApplicationRepository
import database.repositories.CompanyRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ApplyJobForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apply_job_form)

        val title = findViewById<TextView>(R.id.tvApplyFormTitle)
        val desc = findViewById<TextView>(R.id.tvApplyFormDesc)
        val salary = findViewById<TextView>(R.id.tvApplyFormSalary)
        val req = findViewById<TextView>(R.id.tvApplyFormReq)
        val comp = findViewById<TextView>(R.id.tvApplyFormComp)

        val id = intent.getStringExtra("jobId").toString()
        val jid = id.toInt()

        val repositoryJ = JobRepository(CompanyDatabase.getInstance(this))
        val repositoryC = CompanyRepository(CompanyDatabase.getInstance(this))


        GlobalScope.launch(Dispatchers.IO) {
            val dataJ = repositoryJ.getjobDetail(jid)
            title.text = dataJ.title
            salary.text = dataJ.salary
            desc.text = dataJ.description
            req.text =dataJ.requirements

            val dataC = repositoryC.getCompanyDetail(dataJ.company)
            comp.text = dataC.name
        }








        val btnGoback = findViewById<Button>(R.id.btnGoBackToJobs)
        btnGoback.setOnClickListener {
            var intent = Intent(this, AllJobs::class.java)
            startActivity(intent)
            finish()
        }
        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("user", null)

        val repositoryA = ApplicationRepository(CompanyDatabase.getInstance(this))
        val notes =findViewById<EditText>(R.id.edtApplNotes)
        val contact = findViewById<EditText>(R.id.edtApplContact)
        val user = cookies?.toInt()


        val btnApply = findViewById<Button>(R.id.btnApply)
        btnApply.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val an = notes.text.toString()
                val ac = contact.text.toString()

                user?.let { it1 -> Application(an , jid , it1, ac) }
                    ?.let { it2 -> repositoryA.insert(it2) }
            }

            var intent = Intent(this, AllJobs::class.java)
            startActivity(intent)
            finish()
        }
    }
}