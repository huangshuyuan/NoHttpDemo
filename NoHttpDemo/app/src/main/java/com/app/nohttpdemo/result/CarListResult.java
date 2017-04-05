package com.app.nohttpdemo.result;

import com.app.nohttpdemo.model.Car;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xingjikang on 2017/3/7.
 */

public class CarListResult extends ResponseBody implements Serializable {


    private static final long serialVersionUID = -5924608897513416051L;
    @SerializedName(ResultKey.RESULT_LIST)
    private Car[] cars;

    public Car[] getCars() {
        return cars;
    }

    public void setCars(Car[] cars) {
        this.cars = cars;
    }


    public class Car implements Serializable {
        private String CAR_BH;

        public String getCAR_BH() {
            return CAR_BH;
        }

        public void setCAR_BH(String CAR_BH) {
            this.CAR_BH = CAR_BH;
        }
    }
}
