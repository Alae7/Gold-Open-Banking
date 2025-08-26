package com.adaptive.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class AmlCredit {

    int person_age ;
    double person_income;
    String person_home_ownership;
    int person_emp_length;
    String loan_intent;
    double loan_amnt;
    String cb_person_default_on_file;

}
