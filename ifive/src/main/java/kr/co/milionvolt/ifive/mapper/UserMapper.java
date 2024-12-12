package kr.co.milionvolt.ifive.mapper;

import kr.co.milionvolt.ifive.domain.user.*;
import kr.co.milionvolt.ifive.domain.userinfo.UserInfoPaymentListVO;
import kr.co.milionvolt.ifive.domain.userinfo.UserInfoReservationListVO;
import kr.co.milionvolt.ifive.domain.userinfo.UserDashboradUserCarDTO;
import kr.co.milionvolt.ifive.domain.userinfo.UserInfoDTO;
import kr.co.milionvolt.ifive.domain.userinfo.CarBatteryAndChargerTypeUpdateDTO;
import kr.co.milionvolt.ifive.domain.userinfo.UserCarInfoDTO;
import kr.co.milionvolt.ifive.domain.userinfo.CarNumberAndModelUpdateDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserMapper {

    // 유저 전체 조회
    @Select("select * from user where user_id > 0 ")
    List<UserVO> getList();

    // 유저 정보 조회
    @Select("select username, user_id, email, phone_number " +
            "from user " +
            "where id = #{id}")
    UserInfoDTO selectId(Integer id);
    // 유저 정보 조회
    @Select("select username, user_id, email, phone_number " +
            "from user " +
            "where user_id = #{userId}")
    UserInfoDTO selectUser(String userId);

    // 유저 비밀번호 변경
    @Update("update user " +
            "set password = #{newPassword} where id = #{id}")
    boolean updatePW(Integer id, String newPassword);

    // 유저 비밀번호 찾기
    @Select("select password " +
            "from user " +
            "where id = #{id}")
    String findByPassword(Integer id);

    // 유저의 자동차 정보 조회
    @Select("select car_number, car_battery, charger_type, model_name " +
            "from user_car " +
            "join charger_type " +
            "using (charger_type_id) " +
            "join car_model " +
            "using (model_id) " +
            "where user_car.car_id = #{carId}")
    UserCarInfoDTO findByUserCar(@Param("carId") Integer id);

    // 유저의 차 번호 + 차 모델 변경
    @Update("update user_car " +
            "set car_number = #{carNumber}, model_id = #{modelId} " +
            "where car_id = #{carId}")
    boolean updateUserCarNumberAndCarModel(CarNumberAndModelUpdateDTO carNumberDTO);

    // 유저의 차 배터리 + 선호 충전 타입 변경
    @Update("update user_car " +
            "set car_battery = #{carBattery}, charger_type_id = #{chargerTypeId} " +
            "where car_id = #{carId}")
    boolean updateUserCarBatteryAndChargerType(CarBatteryAndChargerTypeUpdateDTO updateDTO);

    // 유저의 예약 내역 리스트
    @Select("select reservation_id, start_time, end_time, status, r.created_at, charger_id, c.address " +
            "from reservation r " +
            "join charging_station c " +
            "using (station_id) " +
            "left join user " +
            "using (user_id) " +
            "where user_id = #{userId} " +
            "order by r.created_at desc")
    List<UserInfoReservationListVO> findByUserReservationList(@Param("userId") Integer id);

    // 유저의 결제 내역 리스트
    @Select("select payment_id, amount, payment_method, payment_status, p.created_at, " +
            "       charge_start, charge_end, c.address " +
            "from payment p " +
            "join charging_station c " +
            "using (station_id) " +
            "where user_id = #{userId} " +
            "order by p.created_at desc")
    List<UserInfoPaymentListVO> findByUserPaymentList(@Param("userId") Integer id);

    // 대시보드 유저 + 유저 차 정보 + 차 모델 + 선호 충전 타입
    @Select("select username, " +
            "       car_battery,  " +
            "       model_name, model_battery, model_filepath, " +
            "       charger_type " +
            "from user_car " +
            "JOIN user u ON car_id = u.id " +
            "join car_model " +
            "using (model_id) " +
            "join charger_type " +
            "using (charger_type_id) " +
            "where car_id = #{carId}")
    UserDashboradUserCarDTO findByUserCarAndCarModel(@Param("carId") Integer id);

    // 대시보드 예약리스트 최대 5개 불러옴
    @Select("select reservation_id, start_time, end_time, status, r.created_at, charger_id, c.address " +
            "from reservation r " +
            "join charging_station c " +
            "using (station_id) " +
            "left join user " +
            "using (user_id) " +
            "where user_id = #{userId} " +
            "order by r.created_at desc " +
            "limit 5")
    List<UserInfoReservationListVO> getDashboardReservations(Integer id);

    // 유저의 대시보드 결제 내역 리스트
    @Select("select payment_id, amount, payment_method, payment_status, p.created_at, " +
            "       charge_start, charge_end, c.address " +
            "from payment p " +
            "join charging_station c " +
            "using (station_id) " +
            "where user_id = #{userId} " +
            "order by p.created_at desc " +
            "limit 5")
    List<UserInfoPaymentListVO> getDashboardPayments(Integer id);


    @Select("select id, user_id, password, role from user where user_id = #{userId}")
    UserDetailsVO findByUserId(String userId);

    @Select("select id, user_id, password, role from user where id = #{id}")
    UserDetailsVO findById(Integer id);


    //아이디찾기
    @Select(" SELECT user_id, created_at FROM user " +
            " WHERE username = #{username} " +
            " AND email = #{email} ")
    public FindIdDTO findByIdUserId(String username, String email);

    // 1. 비밀번호 찾기
    @Select(" SELECT email FROM user " +
            " WHERE user_id = #{userId}")
    public String findPasswordByUserId(String userId);

    // 2. 비밀번호 찾기
    @Select(" SELECT username, user_id, email, password " +
            " FROM user " +
            " WHERE username = #{username} " +
            " AND email = #{email} ")
    public FindFwdDTO findPassword(String username , String email);

    // 3. 비밀번호 찾기
    @Update(" UPDATE user SET password = #{password}" +
            " WHERE user_id = #{userId} " +
            " AND username = #{username} " +
            " AND email = #{email} ")
    public void newPassword(ResetDTO dto);

    @Select(" SELECT id FROM user where user_id=#{userId}")
    public Integer selectFindId(String userId);
}
