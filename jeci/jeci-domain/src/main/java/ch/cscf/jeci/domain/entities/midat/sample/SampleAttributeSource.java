package ch.cscf.jeci.domain.entities.midat.sample;


import ch.cscf.jeci.domain.entities.base.PersistentEnum;

/**
 * @author: henryp
 */
public enum SampleAttributeSource implements PersistentEnum {

    EXCEL_FILE('X'),
    PERSON_TABLE('P');

    char code;

    SampleAttributeSource(char code){
        this.code=code;
    }

    public char getDbCode(){
        return code;
    }

}
