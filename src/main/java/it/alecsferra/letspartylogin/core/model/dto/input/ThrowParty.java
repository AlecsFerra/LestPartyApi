package it.alecsferra.letspartylogin.core.model.dto.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
public class ThrowParty {

    @NotNull
    private Date date;

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String province;


}

/*

REQUEST TEMPLATE

{

	date: "12-12-12",

	title: "Party",

	description: "funny",

	province: "VR"

}
 */
