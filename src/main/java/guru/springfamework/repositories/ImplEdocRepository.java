package guru.springfamework.repositories;

import guru.springfamework.domain.SignData;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ImplEdocRepository implements EdocRepository{


    private final JdbcTemplate jdbcTemplate;

    public ImplEdocRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SignData> findAll() {
        return jdbcTemplate.query(
                "select\n" +
                        "srpo.NAME srpo_name,\n" +
                        "pb.NAME pb_name, \n" +
                        "serieCombos.description protocol_no_serie, \n" +
                        "DOC.PROTOKOL_NO protocol_no, \n" +
                        "trunc(DOC.PROTOKOL_DATE) protocol_date_time,\n" +
                        "constructorEMP.EMPLOYEE_SURNAME||' '||constructorEMP.EMPLOYEE_NAME||' '||constructorEMP.EMPLOYEE_LASTNAME||' ('||constructorPOS.NAME||','||constructorStrTree.name||' - '||constructorStrTreeP.name||')' constructor_info,  \n" +
                        "doc.person_type_combos_id person_type_id,\n" +
                        "per.surname per_surname,\n" +
                        "per.name per_name,\n" +
                        "per.lastname per_patronymic,\n" +
                        "sernum.description idcard_serie,\n" +
                        "idcard.IDENTITY_CARD_NO idcard_no,  \n" +
                        "per.birth_date per_birth_date,  \n" +
                        "cm_citizenship.description per_citizenship,\n" +
                        "jper.VOEN jper_voen,\n" +
                        "jper.FULL_NAME jper_name,                \n" +
                        "IXQS_TO_JUSTICE_CL.pkg_ixqs_to_justice_cl.get_doc_ixm_ids_names(doc.id,3) ixm_codes,\n" +
                        "DOC.CONTEXT_NOTE short_content,\n" +
                        "DOC.EXECUTE_DATE execute_date,                                                                           \n" +
                        "execTime.DESCRIPTION execute_time,\n" +
                        "execRegion.DESCRIPTION execute_region,    \n" +
                        "execPlace.DESCRIPTION execute_place,\n" +
                        " \n" +
                        "case when decdoc.reprove_type_id is not null\n" +
                        "then cmReproveType.description||'-' end || cm_dec_name.description as dec_name ,\n" +
                        "cm_dec_num_serial.description dec_serie, \n" +
                        "decdoc.dec_number dec_no,\n" +
                        "case when decdoc.DEC_ID is not null then decdoc.DECISION_DATE else doc.resolution_date end decision_date,\n" +
                        "dec_org.description decision_organisation,\n" +
                        "pos_dec_maker.NAME||' - '||emp_dec_maker.EMPLOYEE_SURNAME||' '||emp_dec_maker.EMPLOYEE_NAME||' '||emp_dec_maker.EMPLOYEE_LASTNAME decision_maker_str,\n" +
                        "case when (decdoc.dec_id is not null) \n" +
                        "then str_dec_maker_p.name||' - '||str_dec_maker.name else null end  decision_maker_info,\n" +
                        "case when decdoc.dec_id is not null\n" +
                        "then decdoc.penalty_amount_azn\n" +
                        "else \n" +
                        " case when doc.resolution_id=60062\n" +
                        " then doc.penalty_amount_azn\n" +
                        " else null end\n" +
                        "end penalty_amount,\n" +
                        "case when (decdoc.reprove_type_id=60320 or (decdoc.reprove_type_id is null and DOC.RESOLUTION_ID=60062))\n" +
                        "     then  \n" +
                        "         case when ph.id is null  \n" +
                        "         then 0  \n" +
                        "         else 1 end  \n" +
                        "     else null \n" +
                        "end payed1_not0,                   \n" +
                        "case when decdoc.dec_id is not null then 1 else 0 end dec_new1_old0,\n" +
                        "decdoc.dec_type_id dec_type_id  \n" +
                        "                               \n" +
                        "  from INZIBATIX.INZ_DOC doc              \n" +
                        "  left outer join INZIBATIX.COM_COMBOS serieCombos on doc.protokol_no_serie_combos_id=serieCombos.id\n" +
                        "                                                     \n" +
                        "  join INZIBATIX.HR_STRUCTURE_TREE srpo on DOC.POLICE_DEPARTMENT_STR_TREE_ID=srpo.ID\n" +
                        "  join INZIBATIX.HR_STRUCTURE_TREE pb on DOC.POLICE_STATION_STR_TREE_ID=pb.ID\n" +
                        "  \n" +
                        "  join INZIBATIX.COM_PERSON per on DOC.PERSON_ID=per.ID                                                   \n" +
                        "                                                     \n" +
                        "  left outer join inzibatix.com_JURIDICAL_PERSON jper on doc.juridical_person_id=jper.id\n" +
                        "  left outer join inzibatix.com_combos cm_org_legal_form on jper.ORGANISATIONAL_LEGAL_FORM_ID=cm_org_legal_form.id\n" +
                        "  left join INZIBATIX.COM_PERSON_PASSPORT idcard on (idcard.PERSON_ID=per.id and idcard.active='1' )\n" +
                        "  left join inzibatix.com_combos sernum on idcard.COM_COMBOS_SERIAL_NUMBER_ID=sernum.id\n" +
                        "  left join inzibatix.com_combos cm_male_female on per.COM_COMBOS_MALE_FEMALE_ID=cm_male_female.id\n" +
                        "  left outer join INZIBATIX.COM_COMBOS cm_citizenship on PER.COM_COMBOS_CITIZENSHIP_ID=cm_citizenship.ID\n" +
                        "  left outer join INZIBATIX.COM_COMBOS sosialPosition on DOC.PERSON_SOSIAL_POSITION_ID=sosialPosition.ID\n" +
                        "  left outer join INZIBATIX.COM_COMBOS cm_nationality on PER.COM_COMBOS_NATIONALITY_ID=cm_nationality.ID\n" +
                        "  left outer join INZIBATIX.COM_COMBOS familyStatus on DOC.FAMILY_STATUS_ID=familyStatus.ID\n" +
                        "  left outer join inzibatix.com_combos perEducation on per.COM_COMBOS_PERSON_EDUCATION_ID=perEducation.id\n" +
                        "\n" +
                        "  left outer join INZIBATIX.COMMON_ADDRESS bornAddress on PER.BORN_ADDRESS_ID=bornAddress.ID\n" +
                        "  left outer join INZIBATIX.COMMON_COUNTRY bornCountry on BORNADDRESS.COUNTRY_ID=BORNCOUNTRY.ID\n" +
                        "  left outer join INZIBATIX.COMMON_REGION bornRegion on BORNADDRESS.REGION_ID=bornRegion.ID\n" +
                        "  left outer join INZIBATIX.COMMON_SUBREGION bornSubregion on BORNADDRESS.SUBREGION_ID=bornSubregion.ID\n" +
                        "  left outer join INZIBATIX.COMMON_VILLAGE bornVillage on BORNADDRESS.VILLAGE_ID=bornVillage.ID\n" +
                        "  left outer join INZIBATIX.COMMON_STREET bornStreet on BORNADDRESS.STREET_ID=bornStreet.ID\n" +
                        "\n" +
                        "  left outer join INZIBATIX.COM_PERSON_ADDRESS_HISTORY ah on DOC.PERSON_ADDRESS_HISTORY_ID=ah.ID\n" +
                        "  left outer join INZIBATIX.COMMON_ADDRESS address on ah.ADDRESS_ID=address.ID\n" +
                        "  left outer join INZIBATIX.COMMON_COUNTRY country on ADDRESS.COUNTRY_ID=COUNTRY.ID\n" +
                        "  left outer join INZIBATIX.COMMON_REGION region on ADDRESS.REGION_ID=region.ID\n" +
                        "  left outer join INZIBATIX.COMMON_SUBREGION subregion on ADDRESS.SUBREGION_ID=subregion.ID\n" +
                        "  left outer join INZIBATIX.COMMON_VILLAGE village on ADDRESS.VILLAGE_ID=village.ID\n" +
                        "  left outer join INZIBATIX.COMMON_STREET street on ADDRESS.STREET_ID=street.ID\n" +
                        "\n" +
                        "  left outer join INZIBATIX.COM_COMBOS execTime on DOC.COMBOS_EXECUTE_TIME_ID=execTime.ID\n" +
                        "  left outer join INZIBATIX.COM_COMBOS execRegion on DOC.EXECUTE_REGION_ID=execRegion.ID\n" +
                        "  left outer join INZIBATIX.COM_COMBOS execPlace on DOC.EXECUTE_PLACE_ID=execPlace.ID\n" +
                        "  left outer join INZIBATIX.COM_COMBOS comboGroup on DOC.COMBOS_PERSON_GROUP_ID=comboGroup.ID\n" +
                        "  left outer join INZIBATIX.COM_COMBOS executeSituation on DOC.COMBOS_EXECUTE_SITUATION_ID=executeSituation.ID\n" +
                        "  left outer join INZIBATIX.COM_COMBOS fixedOrganisation on DOC.FIXED_ORGANIZATION_ID=fixedOrganisation.ID\n" +
                        "\n" +
                        "  join INZIBATIX.HR_EMPLOYEE_POSITION authorEmpPos on DOC.AUTHOR_EMPLOYEE_POS_ID=authorEmpPos.ID\n" +
                        "  join INZIBATIX.HR_EMPLOYEE authorEmp on AUTHOREMPPOS.EMPLOYEE_ID=authorEmp.ID\n" +
                        "  join INZIBATIX.HR_STRUCTURE_POSITION authorStrPos on AUTHOREMPPOS.STRUCTURE_POSITION_ID=AUTHORSTRPOS.ID\n" +
                        "  join INZIBATIX.HR_POSITION authorPos on AUTHORSTRPOS.POSITION_ID=authorPos.ID\n" +
                        "  join INZIBATIX.HR_STRUCTURE_TREE authorStrTree on AUTHORSTRPOS.STRUCTURE_TREE_ID=authorStrTree.id\n" +
                        "  left outer join INZIBATIX.HR_STRUCTURE_TREE authorStrTreeP on authorStrTree.parent_id=authorStrTreeP.id\n" +
                        "                                                                                                         \n" +
                        "  join INZIBATIX.HR_EMPLOYEE_POSITION constructorEmpPos on DOC.CONSTRUCTOR_EMP_POS_ID=constructorEmpPos.ID\n" +
                        "  join INZIBATIX.HR_EMPLOYEE constructorEmp on constructorEmpPos.EMPLOYEE_ID=constructorEmp.ID\n" +
                        "  join INZIBATIX.HR_STRUCTURE_POSITION constructorStrPos on constructorEMPPOS.STRUCTURE_POSITION_ID=constructorSTRPOS.ID\n" +
                        "  join INZIBATIX.HR_POSITION constructorPos on constructorSTRPOS.POSITION_ID=constructorPos.ID                                \n" +
                        "  join INZIBATIX.HR_STRUCTURE_TREE constructorStrTree on constructorSTRPOS.STRUCTURE_TREE_ID=constructorStrTree.id\n" +
                        "  left outer join INZIBATIX.HR_STRUCTURE_TREE constructorStrTreeP on constructorStrTree.parent_id=constructorStrTreeP.id                                            \n" +
                        "                                                     \n" +
                        "  join INZIBATIX.HR_EMPLOYEE_POSITION chieffEmpPos on DOC.CHIEFF_EMP_POS_ID=chieffEmpPos.ID\n" +
                        "  join INZIBATIX.HR_EMPLOYEE chieffEmp on chieffEmpPos.EMPLOYEE_ID=chieffEmp.ID\n" +
                        "  join INZIBATIX.HR_STRUCTURE_POSITION chieffStrPos on chieffEMPPOS.STRUCTURE_POSITION_ID=chieffSTRPOS.ID\n" +
                        "  join INZIBATIX.HR_POSITION chieffPos on chieffSTRPOS.POSITION_ID=chieffPos.ID\n" +
                        "  join INZIBATIX.HR_STRUCTURE_TREE chieffStrTree on chieffSTRPOS.STRUCTURE_TREE_ID=chieffStrTree.id\n" +
                        "  left outer join INZIBATIX.HR_STRUCTURE_TREE chieffStrTreeP on chieffStrTree.parent_id=chieffStrTreeP.id                                                                                                    \n" +
                        "                                                     \n" +
                        "  left join INZIBATIX.COM_COMBOS perTypeCombos on doc.PERSON_TYPE_COMBOS_ID=perTypeCombos.ID    \n" +
                        "                                                                                                                  \n" +
                        "  left outer join penalty_protocol_ws.ws_payment_history ph on (PH.INZ_DOC_ID=doc.id and ph.active='1')    \n" +
                        "       \n" +
                        "  left outer join  \n" +
                        "                       (  \n" +
                        "                               select dec1.id dec_id,dec1.inz_doc_id inz_doc_id ,\n" +
                        "                               60315 dec_type_id,\n" +
                        "                               dec1.DECISION_NAME_COMBOS_ID cm_id,\n" +
                        "                               cast(null as number) reprove_type_id,\n" +
                        "                               dec1.DECISION_ORGANISATION_ID dec_org_id,dec1.DECISION_DATE decision_date,dec1.DECISION_NUM_SERIAL_COMBOS_ID dec_num_serial_id,  \n" +
                        "                               dec1.DECISION_NUMBER dec_number,\n" +
                        "                               dec1.AUTHOR_EMP_POS_ID,dec1.DECISION_MAKER_EMP_POS_ID,dec1.note dec_note,  \n" +
                        "                               cast(null as number) reprove_type_combos_id,\n" +
                        "                               cast(null as number) penalty_amount_azn,\n" +
                        "                               dec1.IS_LAST_DECISION1_NOT0 last_dec1_not0\n" +
                        "                               from INZIBATIX.INZ_DECISION_DOC1_STOP_EXEC dec1\n" +
                        "                               where dec1.active=1\n" +
                        "                               union  \n" +
                        "                               select dec2.id,dec2.inz_doc_id,\n" +
                        "                               60316 dec_type_id,\n" +
                        "                               dec2.DECISION_NAME_COMBOS_ID cm_id,dec2.reprove_type_combos_id,dec2.DECISION_ORGANISATION_ID dec_org_id,  \n" +
                        "                               dec2.DECISION_DATE,dec2.DECISION_NUM_SERIAL_COMBOS_ID,dec2.DECISION_NUMBER,\n" +
                        "                              dec2.AUTHOR_EMP_POS_ID,dec2.DECISION_MAKER_EMP_POS_ID,dec2.note,  \n" +
                        "                               cast(dec2.REPROVE_TYPE_COMBOS_ID as number) reprove_type_combos_id,\n" +
                        "                               cast(dec2.penalty_amount_azn as number) penalty_amount_azn,\n" +
                        "                               dec2.IS_LAST_DECISION1_NOT0 last_dec1_not0\n" +
                        "                               from INZIBATIX.INZ_DECISION_DOC2_REPROVE dec2 where dec2.active=1  \n" +
                        "                               union  \n" +
                        "                               select dec3.id,dec3.inz_doc_id,\n" +
                        "                               60317 dec_type_id,\n" +
                        "                               dec3.DECISION_NAME_COMBOS_ID cm_id,cast(null as number),dec3.DECISION_ORGANISATION_ID dec_org_id,  \n" +
                        "                               dec3.DECISION_DATE,dec3.DECISION_NUM_SERIAL_COMBOS_ID,dec3.DECISION_NUMBER\n" +
                        "                               ,dec3.AUTHOR_EMP_POS_ID,dec3.QERARDAD_MAKER_EMP_POS_ID,dec3.note,  \n" +
                        "                               cast(null as number) reprove_type_combos_id,\n" +
                        "                               cast(null as number) penalty_amount_azn,\n" +
                        "                               dec3.IS_LAST_DECISION1_NOT0 last_dec1_not0\n" +
                        "                               from INZIBATIX.INZ_DECISION_DOC3_QERARDAD dec3\n" +
                        "                               where DEC3.ACTIVE=1  \n" +
                        "                               union  \n" +
                        "                               select dec4.id,dec4.inz_doc_id,\n" +
                        "                               60318 dec_type_id,\n" +
                        "                               dec4.DECISION_NAME_COMBOS_ID cm_id,\n" +
                        "                               cast(null as number),\n" +
                        "                               dec4.DECISION_ORGANISATION_ID dec_org_id,  \n" +
                        "                               dec4.DECISION_DATE,dec4.DECISION_NUM_SERIAL_COMBOS_ID,dec4.DECISION_NUMBER\n" +
                        "                               ,dec4.AUTHOR_EMP_POS_ID,dec4.DECISION_MAKER_EMP_POS_ID,dec4.note,  \n" +
                        "                               cast(null as number) reprove_type_combos_id,\n" +
                        "                               cast(null as number) penalty_amount_azn,\n" +
                        "                               dec4.IS_LAST_DECISION1_NOT0 last_dec1_not0\n" +
                        "                               from INZIBATIX.INZ_DECISION_DOC4_XITAM dec4\n" +
                        "                               left outer join INZIBATIX.INZ_ADMINISTRATIVE_CRIME ixm on dec4.ID = ixm.ID\n" +
                        "                               where dec4.active=1\n" +
                        "                       ) decdoc on (DECDOC.INZ_DOC_ID=doc.id and DECDOC.last_dec1_not0='1')\n" +
                        "  left outer join inzibatix.com_combos cm_dec_type on decdoc.dec_type_id=cm_dec_type.id\n" +
                        "  left outer join inzibatix.COM_COMBOS cm_dec_name on ((case when decdoc.cm_id is not null then decdoc.cm_id else DOC.RESOLUTION_ID end)=cm_dec_name.ID)  \n" +
                        "  left outer join inzibatix.com_combos cm_dec_num_serial on decdoc.dec_num_serial_id=cm_dec_num_serial.id  \n" +
                        "  left outer join inzibatix.com_combos cmReproveType on decdoc.reprove_type_id=cmReproveType.id  \n" +
                        "  left outer join INZIBATIX.HR_EMPLOYEE_POSITION emppos_dec_maker on decdoc.DECISION_MAKER_EMP_POS_ID=emppos_dec_maker.ID\n" +
                        "  left outer join INZIBATIX.HR_EMPLOYEE emp_dec_maker on emppos_dec_maker.EMPLOYEE_ID=emp_dec_maker.id\n" +
                        "  left outer join INZIBATIX.HR_STRUCTURE_POSITION strpos_dec_maker on emppos_dec_maker.STRUCTURE_POSITION_ID=strpos_dec_maker.id\n" +
                        "  left outer join INZIBATIX.HR_POSITION pos_dec_maker on strpos_dec_maker.POSITION_ID=pos_dec_maker.id\n" +
                        "  left outer join INZIBATIX.HR_STRUCTURE_TREE str_dec_maker on strpos_dec_maker.STRUCTURE_TREE_ID=str_dec_maker.id\n" +
                        "  left outer join INZIBATIX.HR_STRUCTURE_TREE str_dec_maker_p on str_dec_maker.parent_id=str_dec_maker_p.id\n" +
                        "  left outer join inzibatix.COM_COMBOS dec_org  \n" +
                        "                                     on (case  \n" +
                        "                                        when (DECDOC.DEC_ORG_ID is not null)  \n" +
                        "                                        then (DECDOC.DEC_ORG_ID)  \n" +
                        "                                        else (doc.RESOLUTION_ORGANIZATION_ID)  \n" +
                        "                                        end)=dec_org.ID\n" +
                        "  left outer join INZIBATIX.HR_EMPLOYEE_POSITION decAuthorEmpPos on decDOC.AUTHOR_EMP_POS_ID=decAuthorEmpPos.ID\n" +
                        "  join INZIBATIX.HR_EMPLOYEE decAuthorEmp on decAUTHOREMPPOS.EMPLOYEE_ID=decAuthorEmp.ID\n" +
                        "  join INZIBATIX.HR_STRUCTURE_POSITION decAuthorStrPos on decAUTHOREMPPOS.STRUCTURE_POSITION_ID=decAUTHORSTRPOS.ID\n" +
                        "  join INZIBATIX.HR_POSITION decAuthorPos on decAUTHORSTRPOS.POSITION_ID=decAuthorPos.ID\n" +
                        "  join INZIBATIX.HR_STRUCTURE_TREE decAuthorStrTree on decAUTHORSTRPOS.STRUCTURE_TREE_ID=decAuthorStrTree.id\n" +
                        "  left outer join INZIBATIX.HR_STRUCTURE_TREE decAuthorStrTreeP on decAuthorStrTree.parent_id=decAuthorStrTreeP.id                        \n" +
                        "  where  doc.active='1' and ((decdoc.dec_type_id=60316 and decdoc.reprove_type_id=60320) or (decdoc.dec_id is null and doc.resolution_id=60062))",
                (rs, rowNum) ->
                        new SignData(
                                rs.getString("srpo_name"),
                                rs.getString("pb_name"),
                                rs.getString("protocol_no_serie"),
                                rs.getString("protocol_no"),
                                rs.getDate("protocol_date_time"),
                                rs.getString("constructor_info"),

                                rs.getInt("person_type_id"),

                                rs.getString("per_surname"),
                                rs.getString("per_name"),
                                rs.getString("per_patronymic"),
                                rs.getString("per_citizenship"),
                                rs.getDate("per_birth_date"),
                                rs.getString("idcard_serie"),
                                rs.getString("idcard_no"),

                                rs.getString("jper_voen"),
                                rs.getString("jper_name"),

                                rs.getString("execute_region"),
                                rs.getString("execute_place"),
                                rs.getDate("execute_date"),
                                rs.getString("execute_time"),
                                rs.getString("short_content"),

                                rs.getString("ixm_codes"),

                                rs.getString("dec_serie"),
                                rs.getString("dec_no"),
                                rs.getDate("decision_date"),

                                rs.getString("decision_maker_str"),
                                rs.getString("decision_maker_info"),
                                rs.getString("dec_name"),

                                rs.getFloat("penalty_amount")
                        )
        );
    }
}
