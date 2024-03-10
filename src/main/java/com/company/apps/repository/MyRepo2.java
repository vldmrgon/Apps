package com.company.apps.repository;

import com.company.apps.model.entity.DeathInfo;
import com.company.apps.utils.resolver.factory.CSVRepository;

public interface MyRepo2 extends CSVRepository<DeathInfo, String> {
}
