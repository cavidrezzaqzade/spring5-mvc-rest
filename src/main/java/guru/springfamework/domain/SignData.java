package guru.springfamework.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignData {
    //PROTOKOL
    private String srpo_name;
    private String pb_name;

    private String protocol_no_serie;
    private String protocol_no;
    private Date protocol_date_time;
    private String constructor_info;

    private int person_type_id;

    //private String is_identity_card_exist;
    private String per_surname;
    private String per_name;
    private String per_patronymic;
    private String per_citizenship;
    private Date per_birth_date;
    private String idcard_serie;
    private String idcard_no;

    private String jper_voen;
    private String jper_name;

    private String execute_region;
    private String execute_place;
    private Date execute_date;
    private String execute_time;
    private String short_content;

    private String ixm_codes;

    //QERAR
    private String dec_serie;
    private String dec_no;
    private Date decision_date;

    private String decision_maker_str;
    private String decision_maker_info;

    private String dec_name;

    private float penalty_amount;




   /* public SignData(String srpo_name, String pb_name, String protocol_no_serie, String protocol_no, Date protocol_date_time,
                    String constructor_info, int person_type_id, String per_surname, String per_name, String per_patronymic,
                    String per_citizenship, Date per_birth_date, String idcard_serie, String idcard_no, String jper_name,
                    String jper_voen, String execute_region, String  execute_place, Date execute_date, String execute_time,
                    String short_content, String ixm_codes, String dec_serie, String dec_no, Date decision_date,
                    String decision_maker_str, String decision_maker_info, String dec_name, float penalty_amount)
    {
        //protokol
        this.srpo_name = srpo_name;
        this.pb_name = pb_name;
        this.protocol_no_serie = protocol_no_serie;
        this.protocol_no = protocol_no;
        this.protocol_date_time = protocol_date_time;
        this.constructor_info = constructor_info;

        this.person_type_id = person_type_id;

        this.per_surname = per_surname;
        this.per_name = per_name;
        this.per_patronymic = per_patronymic;
        this.per_citizenship = per_citizenship;
        this.per_birth_date = per_birth_date;
        this.idcard_serie = idcard_serie;
        this.idcard_no = idcard_no;

        this.jper_voen = jper_voen;
        this.jper_name = jper_name;

        this.execute_region = execute_region;
        this.execute_place = execute_place;
        this.execute_date = execute_date;
        this.execute_time = execute_time;
        this.short_content = short_content;

        this.ixm_codes =ixm_codes;

        //qerar
        this.dec_serie = dec_serie;
        this.dec_no = dec_no;
        this.decision_date = decision_date;
        this.decision_maker_str = decision_maker_str;
        this.decision_maker_info = decision_maker_info;
        this.dec_name = dec_name;
        this.penalty_amount = penalty_amount;
    }*/

}
