package co.minasegura.measurement.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;

public class MineEntity {
    private String id;

    public MineEntity(String id){
        this.id=id;
    }

    @DynamoDBAttribute(attributeName = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
