package tw.teng.practice.contact.utils

import android.content.Context

object Pref {
    private const val PREFS_USERS_FN = "users"
    private const val PREFS_CONTACTS_FN = "contacts"

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
}