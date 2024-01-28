package co.minasegura.measurement.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

public class ZoneEntity {

    private String zoneID;
    private String zoneType;

    public ZoneEntity(String zoneID, String zoneType){
        this.zoneID=zoneID;
        this.zoneType=zoneType;
    }
    @DynamoDBAttribute(attributeName = "zoneID")

    public String getZoneID() {
        return zoneID;
    }

    public void setZoneID(String zoneID) {
        this.zoneID = zoneID;
    }

    @DynamoDBAttribute(attributeName = "zoneType")
    public String getZoneType() {
        return zoneType;
    }

    public void setZoneType(String zoneType) {
        this.zoneType = zoneType;
    }
}
