package co.minasegura.measurement.mapper.impl;

import co.minasegura.measurement.entity.MeasurementEntity;
import co.minasegura.measurement.entity.MineEntity;
import co.minasegura.measurement.entity.ZoneEntity;
import co.minasegura.measurement.model.Measurement;
import co.minasegura.measurement.model.MeasurementType;
import co.minasegura.measurement.model.Mine;
import co.minasegura.measurement.model.Zone;
import co.minasegura.measurement.util.DynamoDBUtil;
import org.springframework.stereotype.Component;

@Component
public class MeasurementMapper {

    private final DynamoDBUtil dbUtil;

    public MeasurementMapper( DynamoDBUtil dbUtil){
        this.dbUtil=dbUtil;
    }

    public Measurement entityToModel(MeasurementEntity entity) {
        Mine mine = new Mine(entity.getMine().getId());
        Zone zone = new Zone(entity.getZone().getId(), entity.getZone().getType(), mine);
        MeasurementType measurementType = MeasurementType.valueOf(entity.getMeasurementType());

        return new Measurement(dbUtil.getTimestamp(entity.getMeasurementTypeTimestamp()), measurementType, zone, entity.getMeasurementInfo());
    }

    public MeasurementEntity modelToEntity(Measurement model) {

        MineEntity mineEntity = new MineEntity(model.zone().mine().id());
        ZoneEntity zoneEntity = new ZoneEntity(model.zone().id(), model.zone().type());

        return new MeasurementEntity(
            dbUtil.buildPartitionKey(mineEntity.getId(), zoneEntity.getId()),
            dbUtil.buildSortKey(model.measurementType().name(), model.timestamp()),
            mineEntity.getId(),
            zoneEntity.getId(),
            model.timestamp(),
            model.measurementType().name(),
            mineEntity,
            zoneEntity,
            model.measurementInfo()
            );
    }

}
