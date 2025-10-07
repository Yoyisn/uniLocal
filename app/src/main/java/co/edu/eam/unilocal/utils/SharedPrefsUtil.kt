package co.edu.eam.unilocal.utils

import android.content.Context
import co.edu.eam.unilocal.model.Role
import androidx.core.content.edit

object SharedPrefsUtil {

    fun savePreferences(context: Context, userId: String, rol: Role){
        val sharedPreferences = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString("userId", userId)
            putString("role", rol.toString())
        }
    }

    fun clearPreferences(context: Context){
        val sharedPreferences = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            clear()
        }
    }

    fun getPreference(context: Context): Map<String, String> {
        val sharedPreferences = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val rol = sharedPreferences.getString("role", "")

        return if (userId.isNullOrEmpty() || rol.isNullOrEmpty()) {
            emptyMap()
        } else {
            mapOf(
                "userId" to userId,
                "role" to rol
            )
        }
    }


}