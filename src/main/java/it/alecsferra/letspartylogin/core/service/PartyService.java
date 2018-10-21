package it.alecsferra.letspartylogin.core.service;

import it.alecsferra.letspartylogin.core.model.entity.Party;

import java.util.List;

public interface PartyService {

    List<Party> findAllByCreatorIdOrderByDate(String creatorId);

    List<Party> findAllByProvinceOrderByDate(String province);

    void saveParty(Party party);

    Party findById(String id);

}
