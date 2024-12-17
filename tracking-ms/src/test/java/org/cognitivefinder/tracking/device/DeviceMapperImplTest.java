package org.cognitivefinder.tracking.device;

import org.cognitivefinder.tracking.modules.device.Device;
import org.cognitivefinder.tracking.modules.device.DeviceMapperImpl;
import org.cognitivefinder.tracking.modules.device.dto.DeviceDTO;
import org.cognitivefinder.tracking.modules.device.http.DeviceREQ;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DeviceMapperImplTest {

    private DeviceMapperImpl deviceMapper;

    @BeforeEach
    void setUp() {
        deviceMapper = new DeviceMapperImpl();
    }

    // Helper method to create a sample Device object
    private Device createDevice() {
        return Device.builder()
                .id(1L)
                .imei("123456789")
                .sim("SIM123")
                .patientId("PatientID-123")
                .traccarId(202L)
                .build();
    }

    // Helper method to create a sample DeviceREQ object
    private DeviceREQ createDeviceREQ() {
        return DeviceREQ.builder()
                .imei("123456789")
                .sim("SIM123")
                .patientId("PatientID-123")
                .build();
    }

    @Test
    void fromEntityToDtoTest() {
        // Arrange
        Device device = createDevice();

        // Act
        DeviceDTO result = deviceMapper.toDTO(device);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(device);

        // Null Test
        DeviceDTO nullResult = deviceMapper.toDTO(null);
        assertThat(nullResult).isNull();
    }

    @Test
    void formCreateRequestToEntity() {
        // Arrange
        DeviceREQ createRequest = createDeviceREQ();

        // Act
        Device result = deviceMapper.toEntity(createRequest);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getImei()).isEqualTo(createRequest.imei());
        assertThat(result.getSim()).isEqualTo(createRequest.sim());
        assertThat(result.getPatientId()).isEqualTo(createRequest.patientId());

        // Null Test
        Device nullResult = deviceMapper.toEntity(null);
        assertThat(nullResult).isNull();
    }

    @Test
    void fromUpdateRequestToEntity() {
        // Arrange
        Device existingDevice = createDevice();
        DeviceREQ updateRequest = createDeviceREQ();

        // Act
        Device result = deviceMapper.toEntity(updateRequest, existingDevice);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getImei()).isEqualTo(updateRequest.imei());
        assertThat(result.getSim()).isEqualTo(updateRequest.sim());
        assertThat(result.getPatientId()).isEqualTo(updateRequest.patientId());

        // Null Test
        Device nullResult = deviceMapper.toEntity(null, null);
        Device nullResult2 = deviceMapper.toEntity(updateRequest,null);
        Device nullResult3 = deviceMapper.toEntity(null, existingDevice);
        assertThat(nullResult).isNull();
        assertThat(nullResult2).isNull();
        assertThat(nullResult3).isNull();
    }
}
