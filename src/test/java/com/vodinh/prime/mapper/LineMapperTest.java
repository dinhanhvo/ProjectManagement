package com.vodinh.prime.mapper;

import com.vodinh.prime.entities.Line;
import com.vodinh.prime.entities.User;
import com.vodinh.prime.mappers.LineMapper;
import com.vodinh.prime.model.LineDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LineMapperTest {

    @Autowired
    private LineMapper lineMapper;

    @Test
    public void testToDTO() {
        User client = new User();
        client.setId(1L);
        client.setUsername("testUser");
        client.setName("Test Name");

        Line line = new Line();
        line.setId(100L);
        line.setLineId("L001");
        line.setName("Test Line");
        line.setStatus(true);
        line.setClient(client);

        LineDTO dto = lineMapper.toDTO(line);

        Assertions.assertEquals("1", dto.getClientId());
        Assertions.assertEquals("testUser", dto.getClientUsername());
        Assertions.assertEquals("Test Name", dto.getClientName());
        Assertions.assertEquals("L001", dto.getLineId());
        Assertions.assertEquals("Test Line", dto.getName());
        Assertions.assertTrue(dto.getStatus());
    }

    @Test
    public void testToEntity() {
        LineDTO dto = new LineDTO();
        dto.setId(100L);
        dto.setLineId("L001");
        dto.setName("Test Line");
        dto.setStatus(true);
        dto.setClientId(1L);

        Line entity = lineMapper.toEntity(dto);

        Assertions.assertEquals(100L, entity.getId());
        Assertions.assertEquals("L001", entity.getLineId());
        Assertions.assertEquals("Test Line", entity.getName());
        Assertions.assertTrue(entity.getStatus());
        Assertions.assertNull(entity.getClient()); // Vì client được ignore
    }
}
