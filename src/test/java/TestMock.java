import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TestMock {
    @Test
    void testPatient() {
        HealthInfo healthInfo = new HealthInfo(new BigDecimal("36.6"), new BloodPressure(120, 80));

        List<PatientInfo> patients = new ArrayList<>();
        patients.add(new PatientInfo(
                "patientOne",
                "Иван",
                "Петров",
                LocalDate.of(1965, 3, 8),
                healthInfo));
        patients.add(new PatientInfo(
                "patientTwo",
                "Пётр",
                "Иванов",
                LocalDate.of(1985, 9, 1),
                healthInfo));
        patients.add(new PatientInfo(
                "patientThree",
                "Сергей",
                "Соловьёв",
                LocalDate.of(2001, 1, 1),
                healthInfo));

        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        when(patientInfoRepository.getById("patientOne"))
                .thenReturn(patients.get(0));
        when(patientInfoRepository.getById("patientTwo"))
                .thenReturn(patients.get(1));
        when(patientInfoRepository.getById("patientThree"))
                .thenReturn(patients.get(2));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);
        doNothing().when(sendAlertService).send(any(String.class));



        MedicalService medicalService = new MedicalServiceImpl(patientInfoRepository, sendAlertService);

        medicalService.checkBloodPressure("patientOne", new BloodPressure(115, 80));
        medicalService.checkTemperature("patientOne", new BigDecimal("34.21"));

        medicalService.checkBloodPressure("patientTwo", new BloodPressure(120, 80));
        medicalService.checkTemperature("patientTwo", new BigDecimal("36.8"));

        medicalService.checkBloodPressure("patientThree", new BloodPressure(160, 107));
        medicalService.checkTemperature("patientThree", new BigDecimal("36.6"));
        List<String> expected = new ArrayList<>();
        expected.add("Warning, patient with id: patientOne, need help");
        expected.add("Warning, patient with id: patientOne, need help");
        expected.add("Warning, patient with id: patientThree, need help");

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService, times(3)).send(argumentCaptor.capture());
        List<String> real = argumentCaptor.getAllValues();
        Assertions.assertEquals(expected, real);

    }
}

