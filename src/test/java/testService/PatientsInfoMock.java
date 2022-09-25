package testService;


import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class PatientsInfoMock {
    HealthInfo healthInfo = new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80));
    List<PatientInfo> patients = Arrays.asList(
            new PatientInfo(
                    "patientOne",
                    "Иван",
                    "Петров",
                    LocalDate.of(1965, 3, 8),
                    healthInfo),
            new PatientInfo(
                    "patientTwo",
                    "Пётр",
                    "Иванов",
                    LocalDate.of(1985, 9, 1),
                    healthInfo),
            new PatientInfo(
                    "patientThree",
                    "Сергей",
                    "Соловьёв",
                    LocalDate.of(2001, 1, 1),
                    healthInfo));
    PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
    SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

    MedicalService forTest() {
        when(patientInfoRepository.getById("patientOne"))
                .thenReturn(patients.get(0));
        when(patientInfoRepository.getById("patientTwo"))
                .thenReturn(patients.get(1));
        when(patientInfoRepository.getById("patientThree"))
                .thenReturn(patients.get(2));
        doNothing().when(sendAlertService).send(any(String.class));
        return new MedicalServiceImpl(patientInfoRepository, sendAlertService);
    }
}
