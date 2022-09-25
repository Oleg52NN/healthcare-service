package testService;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.service.medical.MedicalService;

import java.math.BigDecimal;


public class MessageTest extends PatientsInfoMock {
    @Override
    MedicalService forTest() {
        super.forTest();
        return super.forTest();
    }

    @Test
    void testMessage() {
        MedicalService medicalService = forTest();

        medicalService.checkBloodPressure("patientOne", new BloodPressure(120, 80));
        medicalService.checkTemperature("patientOne", new BigDecimal("36.21"));
        medicalService.checkTemperature("patientTwo", new BigDecimal("37.0"));
        medicalService.checkTemperature("patientThree", new BigDecimal("36.6"));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        // Если больным помощь не требуется, то здесь будет выброшена ошибка.
        // То есть, сообщений нет. Что и происходит

    }
}
