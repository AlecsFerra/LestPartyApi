package it.alecsferra.letspartylogin.core.controller;

import it.alecsferra.letspartylogin.core.Utils;
import it.alecsferra.letspartylogin.core.model.dto.input.ThrowParty;
import it.alecsferra.letspartylogin.core.model.dto.output.PartyInfo;
import it.alecsferra.letspartylogin.core.model.dto.output.SimpleResult;
import it.alecsferra.letspartylogin.core.model.entity.Party;
import it.alecsferra.letspartylogin.core.service.PartyService;
import it.alecsferra.letspartylogin.core.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/party")
@RestController
public class PartyController {

    private final PartyService partyService;
    private final UserService userService;
    private final ModelMapper modelMapper = new ModelMapper();

    @Autowired
    public PartyController(PartyService partyService, UserService userService){
        this.partyService = partyService;
        this.userService = userService;
    }

    @PostMapping("/throw")
    public SimpleResult throwParty(@RequestBody @Validated ThrowParty throwParty, BindingResult bindingResult){

        SimpleResult result = new SimpleResult();

        if (bindingResult.hasErrors())
            return result.setBindingError(bindingResult);

        Party party = modelMapper.map(throwParty, Party.class);

        partyService.saveParty(party);

        result.setSuccess(true);

        return result;

    }

    @RequestMapping("/thrownby/me")
    public List<PartyInfo> thrownByme(){

        return thrownBy(Utils.getCurrentUsername());

    }

    @RequestMapping("/thrownby/{username}")
    public List<PartyInfo> thrownBy(@PathVariable String username){

        List<PartyInfo> result = new ArrayList<>();

        partyService.findAllByCreatorIdOrderByDate(userService.findByUsername(username).getId())
                .stream()
                .map(x -> modelMapper.map(x, PartyInfo.class))
                .forEach(x -> result.add(x));

        return result;

    }

}