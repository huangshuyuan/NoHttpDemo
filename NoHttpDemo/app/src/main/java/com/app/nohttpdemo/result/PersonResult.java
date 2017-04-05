package com.app.nohttpdemo.result;

import com.app.nohttpdemo.model.Person;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by xingjikang on 2017/3/7.
 */

public class PersonResult extends ResponseBody implements Serializable{
    private static final long serialVersionUID = -5924608897513416051L;
    @SerializedName(ResultKey.RESULT)
    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
