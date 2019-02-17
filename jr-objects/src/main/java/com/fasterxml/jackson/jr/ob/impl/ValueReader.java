package com.fasterxml.jackson.jr.ob.impl;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

/**
 * Base class for all "deserializer" implementations used to actually read
 * values of Java types from (json) input.
 */
public abstract class ValueReader
{
    /*
    /**********************************************************************
    /* Basic API
    /**********************************************************************
     */

    /**
     * Method called to deserialize value of type supported by this reader, using
     * given parser. Parser is already positioned to the (first) token
     * of the value to read.
     *
     * @param reader Context object that allows calling other read methods for contained
     *     values of different types (for example for collection readers).
     * @param p Underlying parser used for reading decoded token stream
     */
    public abstract Object read(JSONReader reader, JsonParser p) throws IOException;

    /**
     * Method called to deserialize value of type supported by this reader, using
     * given parser. Parser is not yet positioned to the (first) token
     * of the value to read and needs to be advanced.
     *
     * @param reader Context object that allows calling other read methods for contained
     *     values of different types (for example for collection readers).
     * @param p Underlying parser used for reading decoded token stream
     */
    public abstract Object readNext(JSONReader reader, JsonParser p) throws IOException;

    /*
    /**********************************************************************
    /* Helper methods for sub-classes
    /**********************************************************************
     */

    static String _tokenDesc(JsonParser p) throws IOException {
        return _tokenDesc(p, p.currentToken());
    }

    // just to give access to JSONReader
    static String _tokenDesc(JsonParser p, JsonToken t) throws IOException {
        if (t == null) {
            return "NULL";
        }
        switch (t) {
        case FIELD_NAME:
            return "JSON Field name '"+p.getCurrentName()+"'";
        case START_ARRAY:
            return "JSON Array";
        case START_OBJECT:
            return "JSON Object";
        case VALUE_FALSE:
            return "'false'";
        case VALUE_NULL:
            return "'null'";
        case VALUE_NUMBER_FLOAT:
        case VALUE_NUMBER_INT:
            return "JSON Number";
        case VALUE_STRING:
            return "JSON String";
        case VALUE_TRUE:
            return "'true'";
        default:
            return t.toString();
        }
    }
}
