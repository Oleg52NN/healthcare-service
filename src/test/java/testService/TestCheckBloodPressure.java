package testService;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.service.medical.MedicalService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


public class TestCheckBloodPressure extends PatientsInfoMock{

    @Override
   MedicalService forTest(){
        super.forTest();
        return super.forTest();
    }
    @Test
    void testBloodPressure(){
        MedicalService medicalService = forTest();

        medicalService.checkBloodPressure("patientOne", new BloodPressure(115, 80));
        medicalService.checkBloodPressure("patientTwo", new BloodPressure(120, 80));
        medicalService.checkBloodPressure("patientThree", new BloodPressure(160, 107));
        List<String> expected = new ArrayList<>();
        expected.add("Warning, patient with id: patientOne, need help");
        expected.add("Warning, patient with id: patientThree, need help");
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService, times(2)).send(argumentCaptor.capture());
        List<String> real = argumentCaptor.getAllValues();
        Assertions.assertEquals(expected, real);
    };



}
