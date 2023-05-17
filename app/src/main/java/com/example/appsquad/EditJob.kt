package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import database.CompanyDatabase
import database.repositories.CompanyRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditJob : AppCompatActivity() {
    lateinit var jobTitle: EditText
    lateinit var jobSalary: EditText
    lateinit var jobReq: EditText
    lateinit var jobDesc: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_job)

        val id = intent.getStringExtra("jobId").toString()
        val jid = id.toInt()

        val repositoryJ = JobRepository(CompanyDatabase.getInstance(this))

        val btnGoback = findViewById<Button>(R.id.btnGoBackFromEditJob)
        btnGoback.setOnClickListener {
            var intent = Intent(this, CompanyJobs::class.java)
            startActivity(intent)
            finish()
        }

        jobTitle = findViewById(R.id.edtEditJobTitle)
        jobSalary = findViewById(R.id.edtEditJobSalary)
        jobReq = findViewById(R.id.edtEditJobReq)
        jobDesc = findViewById(R.id.edtEditJobDesc)
        val btnupdate = findViewById<Button>(R.id.btnUpdateJob)
        btnupdate.setOnClickListener {
            val jtitle = jobTitle.text.toString()
            val jsal = jobSalary.text.toString()
            val jreq = jobReq.text.toString()
            val jdes = jobDesc.text.toString()
            CoroutineScope(Dispatchers.IO).launch {

                repositoryJ.updateJob(jid , jtitle , jsal ,jreq , jdes)

            }

            var intent = Intent(this, CompanyJobs::class.java)
//        intent.putExtra("answer" , ans)
            startActivity(intent)
            finish()
        }




        val edtJobtitle = findViewById<EditText>(R.id.edtEditJobTitle)
        val edtJobSal = findViewById<EditText>(R.id.edtEditJobSalary)
        val edtJobReq= findViewById<EditText>(R.id.edtEditJobDesc)
        val edtJobDesc = findViewById<EditText>(R.id.edtEditJobReq)

        GlobalScope.launch(Dispatchers.IO) {
            val data = repositoryJ.getjobDetail(jid)

            edtJobtitle.setText(data.title.toString())
            edtJobReq.setText(data.requirements)
            edtJobDesc.setText(data.description)
            edtJobSal.setText(data.salary)

        }



    }
}