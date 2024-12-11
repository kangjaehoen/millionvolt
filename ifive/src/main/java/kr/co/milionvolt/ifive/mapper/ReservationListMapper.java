package kr.co.milionvolt.ifive.mapper;

import kr.co.milionvolt.ifive.domain.reservation.ReservationListDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface ReservationListMapper {

    //@Select("SELECT charger_id, status, start_time, end_time " +
    //        "FROM reservation " +
    //        "WHERE start_time LIKE CONCAT('%', #{startTime}, '%')")
//    @Select("SELECT charger_id, status, start_time, end_time " +
//            "FROM reservation " +
//            "WHERE DATE(start_time) >= DATE(#{startTime}) " +
//            "OR DATE(end_time) <= DATE(#{endTime})")
//    @Select("SELECT charger_id, status, start_time, end_time " +
//            "FROM reservation " +
//            "WHERE DATE(start_time) >= DATE(#{startTime}) " +
//            "OR DATE(end_time) <= DATE(#{endTime})")
    @Select("SELECT charger_id, status, start_time, end_time " +
            "FROM reservation " +
            "WHERE (DATE(start_time) BETWEEN DATE(#{startTime}) AND DATE(#{endTime})) " +
            "OR (DATE(end_time) BETWEEN DATE(#{startTime}) AND DATE(#{endTime})) " +
            "OR (DATE(start_time) <= DATE(#{startTime}) AND DATE(end_time) >= DATE(#{endTime}))")
    List<ReservationListDTO> selectReservationList(
            @Param("startTime") Timestamp startTime,
            @Param("endTime") Timestamp endTime);
}