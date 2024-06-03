package de.ljz.questify.data.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

private const val SHARED_PREFERENCES_NAME = "de.ljz.questify.ApiPreferences"

private const val KEY_ACCESS_TOKEN = "de.ljz.questify.SessionManager.AccessToken"
private const val KEY_REFRESH_TOKEN = "de.ljz.questify.SessionManager.RefreshToken"
private const val KEY_EXPIRATION_TIME = "de.ljz.questify.SessionManager.ExpirationTime"

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
     * Set the refresh token
     */
    fun setRefreshToken(value: String) {
        store.edit().putString(KEY_REFRESH_TOKEN, value).apply()
    }

    /**
     * Get the refresh token
     */
    fun getRefreshToken(): String {
        return store.getString(KEY_REFRESH_TOKEN, "") ?: ""
    }

    /**
     * Check if there is currently a refresh token present
     */
    fun isRefreshTokenPresent(): Boolean {
        return !store.getString(KEY_REFRESH_TOKEN, "").isNullOrBlank()
    }

    /**
     * Set expiration time of the access token
     *
     * @param expiresIn the amount of seconds in which the token expires from now
     */
    fun setExpirationTime(expiresIn: Long) {
        store.edit().putLong(KEY_EXPIRATION_TIME, System.currentTimeMillis() + (expiresIn * 1000L)).apply()
    }

    /**
     * Get expiration time of the access token
     */
    fun getExpirationTime(): Long {
        return store.getLong(KEY_EXPIRATION_TIME, 0)
    }

    /**
     * Clear the session manager data
     */
    fun clear() {
        store.edit().apply {
            putString(KEY_ACCESS_TOKEN, "")
            putString(KEY_REFRESH_TOKEN, "")
            putLong(KEY_EXPIRATION_TIME, 0L)
        }.apply()
    }

}