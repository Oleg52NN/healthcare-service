package testService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

public class TestCheckTemperature extends PatientsInfoMock {
    @Override
  MedicalService forTest() {
        super.forTest();
        return super.forTest();
    }
    @Test
    void testTemperature(){
        MedicalService medicalService = forTest();
        medicalService.checkTemperature("patientOne", new BigDecimal("34.21"));
        medicalService.checkTemperature("patientTwo", new BigDecimal("37.8"));
        medicalService.checkTemperature("patientThree", new BigDecimal("36.6"));
        List<String> expected = new ArrayList<>();
        expected.add("Warning, patient with id: patientOne, need help");
        expected.add("Warning, patient with id: patientTwo, need help");

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService, times(2)).send(argumentCaptor.capture());
        List<String> real = argumentCaptor.getAllValues();
        Assertions.assertEquals(expected, real);
    }
}
