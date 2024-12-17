package kr.co.milionvolt.ifive.mapper;

import kr.co.milionvolt.ifive.domain.notification.ChargingStatusDTO;
import kr.co.milionvolt.ifive.domain.usercar.UserCarChargingUpdateDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ChargingStatusMapper {

    @Select(" SELECT u.id, u.user_id, u.username, uc.car_battery, uc.car_number, uc.car_id , " +
            " cm.model_id, cm.model_battery,r.reservation_id, r.start_time, r.end_time, " +
            " cs.station_id, cs.name, cs.address, " +
            " c.charger_id, cs.price_per_kWh, csp.charger_speed " +
            " FROM user u" +
            " JOIN user_car uc " +
            " ON u.id = uc.car_id " +
            " JOIN car_model cm " +
            " ON uc.car_id = cm.model_id " +
            " JOIN reservation r " +
            " ON u.id=r.user_id " +
            " JOIN charger c " +
            " ON r.charger_id = c.charger_id " +
            " JOIN charging_station cs " +
            " ON cs.station_id = c.station_id " +
            " JOIN charge_speed csp " +
            " ON c.charger_speed_id = csp.charger_speed_id " +
            " WHERE u.user_id = #{userId} " +
            " AND r.reservation_id = #{reservationId}")
    public ChargingStatusDTO chargingStatus(String userId, int reservationId);

    @Update( "UPDATE user_car SET  car_battery =#{carBattery} WHERE car_id=#{carId}")
    public void chargingUpdate(int carId, double carBattery);

    @Update(" UPDATE charger SET charger_status_id = 2 " +
            " WHERE charger_id=#{chargerId} AND station_id=#{stationId}")
    public void chargingStatusInuse(int chargerId, int stationId); // 충전시 상태 변화

    @Update(" UPDATE charger SET charger_status_id = 1" +
            " WHERE charger_id=#{chargerId} AND station_id=#{stationId}")
    public void chargingStatusAvailable(int chargerId, int stationId); // 출차시 상태변화???
}
