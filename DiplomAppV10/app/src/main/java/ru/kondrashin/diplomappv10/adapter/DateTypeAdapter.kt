package ru.kondrashin.diplomappv10.adapter

import android.annotation.SuppressLint
import android.util.Log
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonParseException
import java.lang.reflect.Type
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateTypeAdapter : JsonDeserializer<Date?> {
    @SuppressLint("SimpleDateFormat")
    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type?,
                             context: JsonDeserializationContext?
    ): Date? {
        val date = json.asString
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return try {
            formatter.parse(date)
        } catch (e: ParseException) {
            Log.d("ApiFactory", e.message.toString())
            return null
        }
    }
}