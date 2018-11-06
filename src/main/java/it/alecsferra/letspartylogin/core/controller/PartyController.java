package it.alecsferra.letspartylogin.core.controller;

import it.alecsferra.letspartylogin.core.Utils;
import it.alecsferra.letspartylogin.core.model.dto.input.ThrowParty;
import it.alecsferra.letspartylogin.core.model.dto.output.PartyInfo;
import it.alecsferra.letspartylogin.core.model.dto.output.SimpleResult;
import it.alecsferra.letspartylogin.core.model.dto.output.UserInfo;
import it.alecsferra.letspartylogin.core.model.entity.Party;
import it.alecsferra.letspartylogin.core.model.entity.User;
import it.alecsferra.letspartylogin.core.service.PartyService;
import it.alecsferra.letspartylogin.core.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        User user = userService.findByUsername(username);
        if(user == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        partyService.findAllByCreatorIdOrderByDate(user.getId())
                .stream()
                .map(x -> modelMapper.map(x, PartyInfo.class))
                .forEach(x -> result.add(x));

        return result;

    }

    @RequestMapping("/{partyid}")
    public PartyInfo getPartyInfo(@PathVariable String partyid){

        Party p = partyService.findById(partyid);

        if(p == null)


            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        return modelMapper.map(p, PartyInfo.class);

    }

    @RequestMapping("/{partyid}/partecipants")
    public UserInfo[] getPartyPartecipants(@PathVariable String partyid){

        Party p = partyService.findById(partyid);

        if(p == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        return p.getParticipants()
                .stream()
                .map(x -> modelMapper.map(x, UserInfo.class))
                .toArray(UserInfo[]::new);

    }

    @RequestMapping("/{partyid}/join")
    public SimpleResult joinParty(@PathVariable String partyid){

        SimpleResult simpleResult = new SimpleResult();

        Party p = partyService.findById(partyid);

        if(p == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        if(!p.getParticipants().add(userService.findByUsername(Utils.getCurrentUsername()))){

            simpleResult.setMessage("You already joined this party");
            simpleResult.setSuccess(false);

        }else
            simpleResult.setSuccess(true);

        partyService.saveParty(p);

        return simpleResult;

    }

    @RequestMapping("/{partyid}/leave")
    public SimpleResult leaveParty(@PathVariable String partyid){

        SimpleResult simpleResult = new SimpleResult();

        Party p = partyService.findById(partyid);

        if(p == null)
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);

        if(!p.getParticipants().remove(userService.findByUsername(Utils.getCurrentUsername()))){

            simpleResult.setMessage("You weren't a participant to this party");
            simpleResult.setSuccess(false);

        }else
            simpleResult.setSuccess(true);

        partyService.saveParty(p);

        return simpleResult;

    }

}