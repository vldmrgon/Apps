package com.company.apps.repository;

import com.company.apps.model.entity.Player;
import com.company.apps.utils.resolver.factory.CSVRepository;

public interface MyRepo extends CSVRepository<Player, String> {
}
