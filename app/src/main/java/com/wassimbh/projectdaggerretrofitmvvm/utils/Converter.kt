package com.wassimbh.projectdaggerretrofitmvvm.utils

import androidx.room.TypeConverter

class Converter {
    companion object{
        @TypeConverter
        @JvmStatic
        fun frommArrayToString(value:Array<String>):String{
            var myString: String = value[0]
            return myString
        }
        @TypeConverter
        @JvmStatic
        fun stringToArray(value:String):Array<String>{
            var array = arrayOf(value)
            return array
        }
    }
}