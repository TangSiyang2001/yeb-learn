package com.tsy.yebserver.common.handler;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Steven.T
 * @date 2022/1/11
 */
@Component
public class CustomAuthoritiesDeserializer extends JsonDeserializer<Collection<? extends GrantedAuthority>> {

    @Override
    public Collection<? extends GrantedAuthority> deserialize(JsonParser parser, DeserializationContext context)
            throws IOException {

        final ObjectMapper objectMapper = (ObjectMapper) parser.getCodec();
        final JsonNode treeNode = objectMapper.readTree(parser);
        List<GrantedAuthority> authorities = new ArrayList<>();
        treeNode.forEach(jsonNode -> {
            final JsonNode authority = jsonNode.get("authority");
            authorities.add(new SimpleGrantedAuthority(authority.asText()));
        });
        return authorities;
    }
}
