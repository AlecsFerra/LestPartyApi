package it.alecsferra.letspartylogin.core.service.impl;

import it.alecsferra.letspartylogin.core.Utils;
import it.alecsferra.letspartylogin.core.model.entity.Party;
import it.alecsferra.letspartylogin.core.repository.PartyRepository;
import it.alecsferra.letspartylogin.core.service.PartyService;
import it.alecsferra.letspartylogin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartyServiceImpl implements PartyService {

    private final PartyRepository repository;
    private final UserService userService;

    @Autowired
    public PartyServiceImpl(PartyRepository repository, UserService userService) {

        this.repository = repository;
        this.userService = userService;

    }

    @Override
    public List<Party> findAllByCreatorIdOrderByDate(String creatorId) {

        return repository.findAllByCreatorIdOrderByDate(creatorId);

    }

    @Override
    public List<Party> findAllByProvinceOrderByDate(String province) {

        return repository.findAllByProvinceOrderByDate(province);

    }

    @Override
    public void saveParty(Party party) {

        party.setCreator(userService.findByUsername(Utils.getCurrentUsername()));

        repository.save(party);

    }

}