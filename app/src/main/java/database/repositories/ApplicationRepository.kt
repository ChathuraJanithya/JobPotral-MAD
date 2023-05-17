package database.repositories

import database.CompanyDatabase
import database.entities.Admin
import database.entities.Application

class ApplicationRepository(
    private  val db: CompanyDatabase
) {
    suspend fun insert(application: Application) = db.getApplicationDao().insert(application)
    suspend fun delete(application: Application) = db.getApplicationDao().delete(application)
    fun getAllApplications() =db.getApplicationDao().getAllApplications()
    fun getJobApplications(jid:Int)  = db.getApplicationDao().getJobApplications(jid)
    fun getUserApplications(uid:Int) = db.getApplicationDao().getUserApplications(uid)
    fun getApplDetails(id:Int) = db.getApplicationDao().getApplDetails(id)
    fun updateAppl(id:Int , notes :String , contact:String) =  db.getApplicationDao().updateAppl(id, notes, contact)
}