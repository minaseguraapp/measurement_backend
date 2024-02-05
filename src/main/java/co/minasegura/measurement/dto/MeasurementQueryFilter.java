package co.minasegura.measurement.dto;

public enum MeasurementQueryFilter {
    MINE("mine"),
    ZONE_ID("zoneId"),
    ZONE_TYPE("zoneType"),
    MEASUREMENT_TYPE("measurementType");

    private final String filter;

    MeasurementQueryFilter(String filter){
        this.filter= filter;
    }

    public String getFilter(){
        return  this.filter;
    }
}
