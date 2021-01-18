package tj.boronov.farhang.util

const val DATABASE_NAME = "farhangDataBase"

const val DATABASE_TABLE_NAME = "word"
const val TABLE_WORD_COLUMN_ID = "_id"
const val TABLE_WORD_COLUMN_WORD = "word"
const val TABLE_WORD_COLUMN_DEFINITION = "definition"
const val TABLE_WORD_COLUMN_DICTIONARY = "dictionary_id"
const val TABLE_WORD_COLUMN_FAVORITE = "favorite"

const val DATABASE_TABLE_CATEGORIES = "categories"
const val TABLE_CATEGORIES_COLUMN_ID = "_id"
const val TABLE_CATEGORIES_COLUMN_NAME = "name"
const val TABLE_CATEGORIES_COLUMN_DESCRIPTION = "description"
const val TABLE_CATEGORIES_COLUMN_IMAGE = "image"

const val DATABASE_TABLE_SUBCATEGORY = "subcategory"
const val TABLE_SUBCATEGORY_COLUMN_ID = "_id"
const val TABLE_SUBCATEGORY_COLUMN_NAME = "name"
const val TABLE_SUBCATEGORY_COLUMN_CATEGORY_ID = "category_id"

const val DATABASE_TABLE_PHRASES = "phrases"
const val TABLE_PHRASES_COLUMN_ID = "_id"
const val TABLE_PHRASES_COLUMN_CATEGORY_ID = "category_id"
const val TABLE_PHRASES_COLUMN_SUBCATEGORY_ID = "subcategory_id"
const val TABLE_PHRASES_COLUMN_TRANSLATE_TJ = "translate_tj"
const val TABLE_PHRASES_COLUMN_TRANSLATE_RU = "translate_ru"

const val DATABASE_TABLE_NOTE = "note"
const val TABLE_NOTE_COLUMN_ID = "_id"
const val TABLE_NOTE_COLUMN_NAME = "name"
const val TABLE_NOTE_COLUMN_DESCRIPTION = "description"