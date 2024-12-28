package com.android.goodnatureagro.util

object Constants {
    // API Configuration
    const val BASE_URL = "http://192.168.1.189:8000/api/"  // Update with your actual API URL
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
    const val API_KEY = "FfmJnByjVVcVPwZyYliRiGaym6k1OEvK"  // Replace with your actual API key

    // API Headers
    object Headers {
        const val API_KEY_HEADER = "X-API-KEY"
    }

    // Navigation Routes
    object Route {
        const val SIGN_IN = "sign_in"
        const val FARMER_LIST = "farmer_list"
        const val ADD_FARMER = "add_farmer"
        const val EDIT_FARMER = "edit_farmer/{farmerId}"

        // For navigation with farmer ID
        fun editFarmer(farmerId: String) = "edit_farmer/$farmerId"
    }

    // Shared Preferences Keys
    const val PREFS_NAME = "goodnature_agro_prefs"
    const val KEY_USER_SIGNED_IN = "user_signed_in"

    // Firebase Auth Providers
    const val GOOGLE_SIGN_IN_REQUEST = 1234

    // Error Messages
    object Error {
        const val NO_INTERNET = "No internet connection available"
        const val SYNC_FAILED = "Failed to sync with server"
        const val INVALID_INPUT = "Please fill in all required fields"
        const val SERVER_ERROR = "Server error occurred"
        const val UNKNOWN_ERROR = "An unknown error occurred"
        const val UNAUTHORIZED = "Unauthorized access"  // Added for API key related errors
    }

    // Validation
    object Validation {
        const val MIN_NAME_LENGTH = 2
        const val MAX_NAME_LENGTH = 50
        const val MIN_PHONE_LENGTH = 10
        const val MAX_PHONE_LENGTH = 15
        const val MAX_LOCATION_LENGTH = 100
    }

    // UI Constants
    object UI {
        const val PADDING_SMALL = 8
        const val PADDING_MEDIUM = 16
        const val PADDING_LARGE = 24
        const val CORNER_RADIUS = 8
        const val ELEVATION = 4
    }
}