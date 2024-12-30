package kr.co.milionvolt.ifive.service.charging;

import kr.co.milionvolt.ifive.domain.notification.ChargingStatusDTO;
import kr.co.milionvolt.ifive.domain.usercar.UserCarChargingUpdateDTO;
import kr.co.milionvolt.ifive.mapper.ChargingStationMapper;
import kr.co.milionvolt.ifive.mapper.ChargingStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargingStatusServiceImpl implements ChargingStatusSerivce{

    @Autowired
    private ChargingStatusMapper chargingStatusMapper;

    @Override
    public ChargingStatusDTO chargingStatus(String userId, int reservationId, int stationId) {
        ChargingStatusDTO dto =  chargingStatusMapper.chargingStatus(userId,reservationId,stationId);
        return dto;
    }


    @Override
    public void chargingUpdate(int carId, double carBattery) {
        chargingStatusMapper.chargingUpdate(carId,carBattery);
    }

    @Override
    public void chargingStatusInuse(int chargerId, int stationId) {
        chargingStatusMapper.chargingStatusInuse(chargerId,stationId);
    }
}
