package it.alecsferra.letspartylogin.core.model.dto.output;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PartyInfo {

    private String id;

    private String title;

    private String description;

    private String date;

    private String creatorId;

}
