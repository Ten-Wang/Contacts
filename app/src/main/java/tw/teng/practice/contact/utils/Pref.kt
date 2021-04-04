package tw.teng.practice.contact.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


object Pref {
    private const val PREFS_USERS_FN = "users"
    private const val PREFS_CONTACTS_FN = "contacts"
    private const val PREFS_HASHMAP_FN = "hashmap"

    fun setContacts(context: Context, contacts: String) {
        val sharedPreferences = context.applicationContext.getSharedPreferences(
            PREFS_CONTACTS_FN,
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.putString(PREFS_USERS_FN, contacts)
        editor.apply()
    }

    fun getContacts(context: Context): String? {
        val sharedPreferences = context.applicationContext.getSharedPreferences(
            PREFS_CONTACTS_FN,
            Context.MODE_PRIVATE
        )
        return sharedPreferences.getString(PREFS_USERS_FN, "{}")
    }

    fun insertToSP(context: Context, jsonMap: HashMap<Int, Boolean>) {
        val jsonString = Gson().toJson(jsonMap)
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_HASHMAP_FN, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("map", jsonString)
        editor.apply()
    }

    fun readFromSP(context: Context): HashMap<Int, Boolean> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_HASHMAP_FN, MODE_PRIVATE)
        val defValue =
            Gson().toJson(HashMap<Int, Boolean>())
        val json = sharedPreferences.getString("map", defValue)
        val token: TypeToken<HashMap<Int, Boolean>> =
            object :
                TypeToken<HashMap<Int, Boolean>>() {}
        return Gson().fromJson(json, token.type)
    }
}