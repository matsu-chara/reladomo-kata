/*
Copyright 2016 Goldman Sachs.
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
*/

package simplebank.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import simplebank.domain.Customer;
import simplebank.domain.CustomerAccount;

import javax.ws.rs.ext.ContextResolver;

public class SimpleBankJacksonObjectMapperProvider implements ContextResolver<ObjectMapper>
{

    final ObjectMapper defaultObjectMapper;

    public SimpleBankJacksonObjectMapperProvider()
    {
        defaultObjectMapper = createDefaultMapper();
    }

    private static ObjectMapper createDefaultMapper()
    {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addSerializer(Customer.class, new CustomerSerde.Serializer());
        module.addDeserializer(Customer.class, new CustomerSerde.Deserializer());
        module.addSerializer(CustomerAccount.class, new CustomerAccountSerde.Serializer());
        module.addDeserializer(CustomerAccount.class, new CustomerAccountSerde.Deserializer());
        mapper.registerModule(module);

        return mapper;
    }

    @Override
    public ObjectMapper getContext(Class<?> type)
    {
        return defaultObjectMapper;
    }

}
