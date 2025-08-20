package de.ljz.questify.core.data.shared_preferences

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFERENCES_NAME = "de.ljz.questify.ApiPreferences"

private const val KEY_ACCESS_TOKEN = "de.ljz.questify.SessionManager.AccessToken"

/**
 * Class for storing session data to make requests to the API
 */
class SessionManager(context: Context) {

    private val store: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME,
        Context.MODE_PRIVATE
    )

    /**
     * Set the access token
     *
     * @param value The actual token, without "Bearer" prepended
     */
    fun setAccessToken(value: String) {
        store.edit().putString(KEY_ACCESS_TOKEN, value).apply()
    }

    /**
     * Return the current access token if present, an empty string otherwise
     */
    fun getAccessToken(): String {
        return store.getString(KEY_ACCESS_TOKEN, "")?.let {
            "Bearer ${it.ifBlank { "token" }}"
        } ?: ""
    }

    /**
     * Check if there is currently an access token present
     */
    fun isAccessTokenPresent(): Boolean {
        return !store.getString(KEY_ACCESS_TOKEN, "").isNullOrBlank()
    }

    /**
     * Clear the session manager data
     */
    fun clear() {
        store.edit().apply {
            putString(KEY_ACCESS_TOKEN, "")
        }.apply()
    }
}