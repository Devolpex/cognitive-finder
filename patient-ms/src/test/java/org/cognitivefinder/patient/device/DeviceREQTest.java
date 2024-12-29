package org.cognitivefinder.patient.device;


import static org.junit.jupiter.api.Assertions.*;

import org.cognitivefinder.patient.modules.device.DeviceREQ;
import org.junit.jupiter.api.Test;

class DeviceREQTest {

    @Test
    void testDeviceREQBuilder() {
        String imei = "123456789012345";
        String sim = "8901234567890123456";
        String patientId = "patient123";

        DeviceREQ deviceREQ = DeviceREQ.builder()
                .imei(imei)
                .sim(sim)
                .patientId(patientId)
                .build();

        assertEquals(imei, deviceREQ.imei());
        assertEquals(sim, deviceREQ.sim());
        assertEquals(patientId, deviceREQ.patientId());
    }

    @Test
    void testDeviceREQDefaultValues() {
        DeviceREQ deviceREQ = DeviceREQ.builder().build();

        assertNull(deviceREQ.imei());
        assertNull(deviceREQ.sim());
        assertNull(deviceREQ.patientId());
    }
}
