package com.easybank.commons.facades.mappers;

import com.easybank.commons.repositories.entities.CustomerEntity;
import com.easybank.controllers.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerMapper {

    CustomerEntity mapToEntity(final Customer customer);

}
